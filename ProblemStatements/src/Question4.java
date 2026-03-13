import java.util.*;

public class Question4 {

    private HashMap<String, Set<String>> index = new HashMap<>();
    private HashMap<String, String> documents = new HashMap<>();
    private int n = 5;

    private List<String> generateNGrams(String text) {
        List<String> grams = new ArrayList<>();
        String[] words = text.toLowerCase().split("\\s+");

        for (int i = 0; i <= words.length - n; i++) {
            StringBuilder sb = new StringBuilder();
            for (int j = 0; j < n; j++) {
                sb.append(words[i + j]).append(" ");
            }
            grams.add(sb.toString().trim());
        }

        return grams;
    }

    public void addDocument(String id, String text) {
        documents.put(id, text);
        List<String> grams = generateNGrams(text);

        for (String g : grams) {
            index.putIfAbsent(g, new HashSet<>());
            index.get(g).add(id);
        }
    }

    public void analyzeDocument(String id) {
        String text = documents.get(id);
        List<String> grams = generateNGrams(text);

        HashMap<String, Integer> matchCount = new HashMap<>();

        for (String g : grams) {
            if (index.containsKey(g)) {
                for (String doc : index.get(g)) {
                    if (!doc.equals(id)) {
                        matchCount.put(doc, matchCount.getOrDefault(doc, 0) + 1);
                    }
                }
            }
        }

        System.out.println("Extracted " + grams.size() + " n-grams");

        for (Map.Entry<String, Integer> entry : matchCount.entrySet()) {
            double similarity = (entry.getValue() * 100.0) / grams.size();
            System.out.println("Found " + entry.getValue() + " matching n-grams with \"" + entry.getKey() + "\"");
            System.out.println("Similarity: " + String.format("%.1f", similarity) + "%");
        }
    }

    public static void main(String[] args) {

        Question4 detector = new Question4();

        detector.addDocument("essay_089.txt",
                "machine learning is a method of data analysis that automates analytical model building");

        detector.addDocument("essay_092.txt",
                "machine learning is a method of data analysis that automates analytical model building and improves accuracy");

        detector.addDocument("essay_123.txt",
                "machine learning is a method of data analysis that automates analytical model building");

        System.out.println("analyzeDocument(\"essay_123.txt\")");
        detector.analyzeDocument("essay_123.txt");
    }
}