import java.util.*;

public class Question7 {

    private HashMap<String, Integer> frequency = new HashMap<>();

    public void addQuery(String query) {
        frequency.put(query, frequency.getOrDefault(query, 0) + 1);
    }

    public List<String> search(String prefix) {

        PriorityQueue<Map.Entry<String, Integer>> heap =
                new PriorityQueue<>((a, b) -> a.getValue() - b.getValue());

        for (Map.Entry<String, Integer> entry : frequency.entrySet()) {
            if (entry.getKey().startsWith(prefix)) {
                heap.offer(entry);
                if (heap.size() > 10) {
                    heap.poll();
                }
            }
        }

        List<String> result = new ArrayList<>();

        while (!heap.isEmpty()) {
            Map.Entry<String, Integer> e = heap.poll();
            result.add(0, e.getKey() + " (" + e.getValue() + " searches)");
        }

        return result;
    }

    public void updateFrequency(String query) {
        frequency.put(query, frequency.getOrDefault(query, 0) + 1);
        System.out.println("updateFrequency(\"" + query + "\") → Frequency: " + frequency.get(query));
    }

    public static void main(String[] args) {

        Question7 system = new Question7();

        system.addQuery("java tutorial");
        system.addQuery("javascript");
        system.addQuery("java download");
        system.addQuery("java tutorial");
        system.addQuery("java tutorial");
        system.addQuery("javascript");
        system.addQuery("java 21 features");

        System.out.println("search(\"jav\") →");

        List<String> suggestions = system.search("jav");

        int rank = 1;
        for (String s : suggestions) {
            System.out.println(rank + ". " + s);
            rank++;
        }

        system.updateFrequency("java 21 features");
        system.updateFrequency("java 21 features");
    }
}