package week1;

import java.util.*;

public class Problem4 {

    private Map<String, Set<String>> ngramMap; // ngram → document IDs
    private int n = 3; // size of n-gram

    public Problem4() {
        ngramMap = new HashMap<>();
    }

    // Generate n-grams
    private List<String> generateNGrams(String text) {
        List<String> ngrams = new ArrayList<>();

        String[] words = text.toLowerCase().split("\\s+");

        for (int i = 0; i <= words.length - n; i++) {
            StringBuilder sb = new StringBuilder();

            for (int j = 0; j < n; j++) {
                sb.append(words[i + j]).append(" ");
            }

            ngrams.add(sb.toString().trim());
        }

        return ngrams;
    }

    // Add document to database
    public void addDocument(String docId, String content) {

        List<String> ngrams = generateNGrams(content);

        for (String gram : ngrams) {
            ngramMap.putIfAbsent(gram, new HashSet<>());
            ngramMap.get(gram).add(docId);
        }
    }

    // Analyze plagiarism
    public void analyzeDocument(String docId, String content) {

        List<String> ngrams = generateNGrams(content);

        Map<String, Integer> matchCount = new HashMap<>();

        for (String gram : ngrams) {
            if (ngramMap.containsKey(gram)) {
                for (String existingDoc : ngramMap.get(gram)) {
                    matchCount.put(existingDoc,
                            matchCount.getOrDefault(existingDoc, 0) + 1);
                }
            }
        }

        System.out.println("Analysis for " + docId + ":");

        for (Map.Entry<String, Integer> entry : matchCount.entrySet()) {
            double similarity = (entry.getValue() * 100.0) / ngrams.size();

            System.out.println("Matched with " + entry.getKey()
                    + " → Similarity: " + similarity + "%");
        }
    }

    public static void main(String[] args) {

        Problem4 detector = new Problem4();

        // Existing documents
        detector.addDocument("doc1",
                "this is a sample text for plagiarism detection");

        detector.addDocument("doc2",
                "plagiarism detection system using hashing techniques");

        // New document to check
        detector.analyzeDocument("doc3",
                "this is a sample text for plagiarism");
    }
}