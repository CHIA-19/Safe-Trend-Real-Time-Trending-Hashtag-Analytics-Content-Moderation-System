import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.PriorityQueue;

public class StreamProcessor {
    private Trie bannedWordsTrie = new Trie();
    private CountMinSketch cms = new CountMinSketch(1000, 5);
    private PriorityQueue<Trend> topTrends = new PriorityQueue<>(10);

    // FIX: Create an instance of SocialGraph so it can store data
    private SocialGraph socialGraph = new SocialGraph();

    // Summary Counters for your final report
    private int totalProcessed = 0;
    private int blockedPosts = 0;
    private int verifiedBypassCount = 0;
    private int safePosts = 0;

    public void loadRules(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (!line.isEmpty()) {
                    bannedWordsTrie.insert(line.toLowerCase());
                }
            }
            System.out.println("Trie successfully loaded with moderation rules.");
        } catch (IOException e) {
            System.err.println("Error loading rules: " + e.getMessage());
        }
    }

    public void startStreaming(String csvFilePath) {
        String csvSplitBy = ",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)";
        try (BufferedReader br = new BufferedReader(new FileReader(csvFilePath))) {
            br.readLine(); // Skip header
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(csvSplitBy);
                if (data.length >= 6) {
                    NewsPost currentPost = new NewsPost(
                            Long.parseLong(data[0].trim()),
                            data[1].trim(),
                            data[2].trim(),
                            Boolean.parseBoolean(data[3].trim().toLowerCase()),
                            data[4].trim(),
                            data[5].replace("\"", "")
                    );
                    processPost(currentPost);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void processPost(NewsPost post) {
        totalProcessed++;
        System.out.println("--------------------------------------------------");
        System.out.println("ANALYZING POST: " + post.postId + " | USER: " + post.userId);

        // --- STEP 1: GRAPH & BOT DETECTION ---
        for(String tag : post.hashtags) {
            // Use the instance variable 'socialGraph'
            socialGraph.addEdge(post.userId, tag.toLowerCase().replaceAll("[^a-z0-9]", ""));
        }

        boolean isBot = false;
        if (socialGraph.isCoordinated(post.userId) && !post.isVerified) {
            System.out.println(">>> WARNING: [BOT PATTERN DETECTED] for " + post.userId);
            isBot = true;
        }

        // --- STEP 2: TRIE FILTERING ---
        boolean isForbidden = bannedWordsTrie.containsBannedPrefix(post.postText);

        // --- STEP 3: VERIFIED BYPASS LOGIC ---
        if (isForbidden && post.isVerified) {
            System.out.println(">>> STATUS: [SAFE - VERIFIED BYPASS] <<<");
            verifiedBypassCount++;
        }
        else if (isForbidden && !post.isVerified) {
            System.out.println(">>> STATUS: [BLOCKED] <<<");
            blockedPosts++;
            return;
        }
        else {
            System.out.println(">>> STATUS: [SAFE] <<<");
            safePosts++; // Count the normal safe posts here!
        }

        // --- STEP 4: WEIGHT CALCULATION ---
        int weight;
        if (post.isVerified) {
            weight = 10;
        } else if (isBot) {
            weight = 0;
            System.out.println("ACTION: Suppressing counts from suspected bot.");
        } else {
            weight = 1;
        }

        // --- STEP 5: CMS & LEADERBOARD ---
        for (String tag : post.hashtags) {
            String cleanTag = tag.toLowerCase().replaceAll("[^a-z0-9]", "");
            if (!cleanTag.isEmpty() && weight > 0) {
                for (int i = 0; i < weight; i++) {
                    cms.add(cleanTag);
                }
                int estCount = cms.estimateCount(cleanTag);
                updateLeaderboard(cleanTag, estCount);
            }
        }
    }

    private void updateLeaderboard(String tag, int count) {
        topTrends.removeIf(t -> t.hashtag.equals(tag));
        if (topTrends.size() < 10) {
            topTrends.add(new Trend(tag, count));
        } else if (count > topTrends.peek().count) {
            topTrends.poll();
            topTrends.add(new Trend(tag, count));
        }
    }

    public void displayFinalReport() {
        System.out.println("\n\n*****************************************");
        System.out.println("      FINAL SYSTEM ANALYTICS REPORT      ");
        System.out.println("*****************************************");
        System.out.println("Total Posts Processed: " + totalProcessed);
        System.out.println("Total Posts Blocked:   " + blockedPosts);
        System.out.println("Verified Bypasses:     " + verifiedBypassCount);
        System.out.println("Normal Safe Posts:     " + safePosts); // Now they aren't missing!

        // Check if the math is perfect
        int sum = blockedPosts + verifiedBypassCount + safePosts;
        System.out.println("Sum of Categories:     " + sum);

        System.out.println("\n======= TOP 10 TRENDING HASHTAGS =======");
        PriorityQueue<Trend> tempHeap = new PriorityQueue<>(topTrends);
        while (!tempHeap.isEmpty()) {
            Trend t = tempHeap.poll(); // This prints them in order
            System.out.println("#" + t.hashtag + " : " + t.count + " hits");
        }
        System.out.println("*****************************************\n");
    }

    public static void main(String[] args) {
        StreamProcessor engine = new StreamProcessor();
        engine.loadRules("D:\\Amrita School of AI\\3rdSemester\\AdssaEndSem\\PrefixWords.txt");
        engine.startStreaming("D:\\Amrita School of AI\\3rdSemester\\AdssaEndSem\\Dataset.csv");
        engine.displayFinalReport();
    }
}