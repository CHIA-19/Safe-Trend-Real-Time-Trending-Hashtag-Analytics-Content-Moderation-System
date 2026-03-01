public class CountMinSketch {
    private int[][] table;
    private int width;
    private int depth;
    private int[] seeds;

    public CountMinSketch(int width, int depth) {
        this.width = width;
        this.depth = depth;
        this.table = new int[depth][width];
        this.seeds = new int[]{3, 7, 11, 13, 17}; // Prime seeds for hashing
    }

    // Hash function to map a string to a column in the grid
    private int hash(String item, int seed) {
        long hash = 0;
        for (char c : item.toCharArray()) {
            hash = (hash * seed) + c;
        }
        return Math.abs((int) (hash % width));
    }

    // Increment the count of a hashtag
    public void add(String hashtag) {
        for (int i = 0; i < depth; i++) {
            int col = hash(hashtag, seeds[i]);
            table[i][col]++;
        }
    }

    // Estimate how many times we've seen this hashtag
    public int estimateCount(String hashtag) {
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < depth; i++) {
            int col = hash(hashtag, seeds[i]);
            min = Math.min(min, table[i][col]);
        }
        return min;
    }
}