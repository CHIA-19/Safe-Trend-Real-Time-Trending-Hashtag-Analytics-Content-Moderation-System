import java.util.HashMap;
import java.util.Map;

class TrieNode {
    Map<Character, TrieNode> children = new HashMap<>();
    boolean isEndOfWord = false;
}
public class Trie {
    private final TrieNode root = new TrieNode();
    public void insert(String word) {
        TrieNode current = root;
        for (char l : word.toLowerCase().toCharArray()) {
            current = current.children.computeIfAbsent(l, c -> new TrieNode());
        }
        current.isEndOfWord = true;
    }
    public boolean containsBannedPrefix(String text) {
        if (text == null) return false;
        String[] tokens = text.toLowerCase().split("\\s+");

        for (String token : tokens) {
            String cleanToken = token.replaceAll("[^a-z0-9]", "");
            TrieNode current = root;
            for (char l : cleanToken.toCharArray()) {
                current = current.children.get(l);
                if (current == null) break;
                if (current.isEndOfWord) return true;
            }
        }
        return false;
    }
}
