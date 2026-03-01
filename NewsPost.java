public class NewsPost {
    long timestamp;
    String postId;
    String userId;
    boolean isVerified;
    String[] hashtags;
    String postText;

    public NewsPost(long timestamp, String postId, String userId, boolean isVerified, String hashtags, String postText) {
        this.timestamp = timestamp;
        this.postId = postId;
        this.userId = userId;
        this.isVerified = isVerified;
        this.hashtags = hashtags.split(" ");
        this.postText = postText;
    }
}