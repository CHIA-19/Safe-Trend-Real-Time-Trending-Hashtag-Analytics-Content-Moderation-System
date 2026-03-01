import java.util.*;

public class SocialGraph {
    // Maps a user to the set of hashtags they have used
    private Map<String, Set<String>> adjList = new HashMap<>();

    public void addEdge(String userId, String hashtag) {
        adjList.computeIfAbsent(userId, k -> new HashSet<>()).add(hashtag);
    }

    // Detects if a user is "Spammy" (using too many different trending tags)
    public boolean isCoordinated(String userId) {
        if (!adjList.containsKey(userId)) return false;
        // If one user is spamming 10+ different hashtags, flag them
        return adjList.get(userId).size() > 10;
    }
}