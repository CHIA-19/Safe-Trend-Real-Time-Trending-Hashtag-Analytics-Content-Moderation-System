# Safe-Trend-Real-Time-Trending-Hashtag-Analytics-Content-Moderation-System
A high-speed, automated data pipeline designed to extract verified trending hashtags from high-velocity social media streams while systematically filtering inappropriate content. This project utilizes advanced, memory-efficient data structures to balance computational performance with information integrity.

### Key Features
1)Prefix-Based Content Filtering: Employs a Trie (Prefix Tree) for $O(L)$ efficiency in identifying and blocking restricted terms and their variations.
2)Probabilistic Trend Tracking: Uses a Count-Min Sketch to monitor hashtag frequencies across millions of data points with a constant, minimal memory footprint.
3)Source-Based Verification Logic: Features a "Verified Bypass" mechanism to ensure legitimate reporting from trusted sources (e.g., news agencies) is not censored.
4)Real-Time Ranking: Maintains a live "Top 10" leaderboard using a Min-Heap, updated dynamically based on trust-weighted engagement.
5)Security & Bot Detection: Integrates a Social Graph to identify "Coordinated Inauthentic Behavior" (CIB) and suppress artificial trend manipulation.

### Tech
StackLanguage: Java 
Data Processing: Stream Simulation (txt-based) 
Data Structures: Trie, Count-Min Sketch, Min-Heap, Adjacency Lis
