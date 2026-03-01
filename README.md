SafeTrend: Real-Time Trending Hashtag Analytics & Content Moderation System. 
A high-speed, automated data pipeline designed to extract verified trending hashtags from high-velocity social media streams while systematically filtering inappropriate content. This project utilizes advanced, memory-efficient data structures to balance computational performance with information integrity.

Key Features:
1)Prefix-Based Content Filtering: Employs a Trie (Prefix Tree) for $O(L)$ efficiency in identifying and blocking restricted terms and their variations.
2)Probabilistic Trend Tracking: Uses a Count-Min Sketch to monitor hashtag frequencies across millions of data points with a constant, minimal memory footprint.
3)Source-Based Verification Logic: Features a "Verified Bypass" mechanism to ensure legitimate reporting from trusted sources (e.g., news agencies) is not censored.
4)Real-Time Ranking: Maintains a live "Top 10" leaderboard using a Min-Heap, updated dynamically in $O(log\ K)$ time based on trust-weighted engagement.
5)Security & Bot Detection: Integrates a Social Graph to identify "Coordinated Inauthentic Behavior" (CIB) and suppress artificial trend manipulation.

Tech 
1)StackLanguage: Java 
2)Data Processing: Stream Simulation (CSV-based "Firehose") 
3)Core Data Structures:
4)Trie: For $O(L)$ content moderation.
5)Count-Min Sketch: For memory-efficient frequency estimation.
6)Min-Heap: For $O(log\ K)$ real-time ranking.
7)Adjacency List: For $O(1)$ social graph edge mapping.

System Methodology:
The pipeline processes data through four distinct phases:
1)Ingestion & Normalization: Reads raw CSV streams and standardizes text to lowercase.
2)Security Layer: Performs Trie-based prefix searches and applies Verified Bypass logic.
3)Behavioral Analysis: Uses an Adjacency List to map user-hashtag relationships and detect bot clusters.
4)Analytics & Ranking: Updates the Count-Min Sketch with weighted frequencies (Verified: 10, Standard: 1, Bot: 0) and refreshes the leaderboard.
