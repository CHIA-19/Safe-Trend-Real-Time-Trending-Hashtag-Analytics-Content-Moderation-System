public class Trend implements Comparable<Trend> {
    String hashtag;
    int count;

    public Trend(String hashtag, int count) {
        this.hashtag = hashtag;
        this.count = count;
    }

    @Override
    public int compareTo(Trend other) {
        // This makes it a Min-Heap (smallest count stays at the top)
        return Integer.compare(this.count, other.count);
    }
}
