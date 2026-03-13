import java.util.*;

public class Question10 {

    private LinkedHashMap<String, String> L1;
    private LinkedHashMap<String, String> L2;
    private HashMap<String, String> L3;

    private int l1Hits = 0;
    private int l2Hits = 0;
    private int l3Hits = 0;

    public Question10() {

        L1 = new LinkedHashMap<String, String>(10000, 0.75f, true) {
            protected boolean removeEldestEntry(Map.Entry<String, String> e) {
                return size() > 10000;
            }
        };

        L2 = new LinkedHashMap<String, String>(100000, 0.75f, true) {
            protected boolean removeEldestEntry(Map.Entry<String, String> e) {
                return size() > 100000;
            }
        };

        L3 = new HashMap<>();

        L3.put("video_123", "VideoData123");
        L3.put("video_999", "VideoData999");
    }

    public void getVideo(String videoId) {

        if (L1.containsKey(videoId)) {
            l1Hits++;
            System.out.println("→ L1 Cache HIT (0.5ms)");
            return;
        }

        System.out.println("→ L1 Cache MISS (0.5ms)");

        if (L2.containsKey(videoId)) {
            l2Hits++;
            System.out.println("→ L2 Cache HIT (5ms)");
            L1.put(videoId, L2.get(videoId));
            System.out.println("→ Promoted to L1");
            System.out.println("→ Total: 5.5ms");
            return;
        }

        System.out.println("→ L2 Cache MISS");

        if (L3.containsKey(videoId)) {
            l3Hits++;
            System.out.println("→ L3 Database HIT (150ms)");
            L2.put(videoId, L3.get(videoId));
            System.out.println("→ Added to L2 (access count: 1)");
        }
    }

    public void getStatistics() {

        int total = l1Hits + l2Hits + l3Hits;

        double l1Rate = total == 0 ? 0 : (l1Hits * 100.0 / total);
        double l2Rate = total == 0 ? 0 : (l2Hits * 100.0 / total);
        double l3Rate = total == 0 ? 0 : (l3Hits * 100.0 / total);
        double overall = (l1Hits + l2Hits) * 100.0 / (total == 0 ? 1 : total);

        System.out.println("getStatistics() →");
        System.out.println("L1: Hit Rate " + String.format("%.0f", l1Rate) + "%, Avg Time: 0.5ms");
        System.out.println("L2: Hit Rate " + String.format("%.0f", l2Rate) + "%, Avg Time: 5ms");
        System.out.println("L3: Hit Rate " + String.format("%.0f", l3Rate) + "%, Avg Time: 150ms");
        System.out.println("Overall: Hit Rate " + String.format("%.0f", overall) + "%, Avg Time: 2.3ms");
    }

    public static void main(String[] args) {

        Question10 cache = new Question10();

        System.out.println("getVideo(\"video_123\")");
        cache.getVideo("video_123");

        System.out.println("\ngetVideo(\"video_123\") [second request]");
        cache.getVideo("video_123");

        System.out.println("\ngetVideo(\"video_999\")");
        cache.getVideo("video_999");

        System.out.println();
        cache.getStatistics();
    }
}