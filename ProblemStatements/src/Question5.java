import java.util.*;

class Event {
    String url;
    String userId;
    String source;

    Event(String url, String userId, String source) {
        this.url = url;
        this.userId = userId;
        this.source = source;
    }
}

public class Question5 {

    private HashMap<String, Integer> pageViews = new HashMap<>();
    private HashMap<String, Set<String>> uniqueVisitors = new HashMap<>();
    private HashMap<String, Integer> trafficSources = new HashMap<>();

    public void processEvent(Event e) {
        pageViews.put(e.url, pageViews.getOrDefault(e.url, 0) + 1);

        uniqueVisitors.putIfAbsent(e.url, new HashSet<>());
        uniqueVisitors.get(e.url).add(e.userId);

        trafficSources.put(e.source, trafficSources.getOrDefault(e.source, 0) + 1);
    }

    public void getDashboard() {

        List<Map.Entry<String, Integer>> pages = new ArrayList<>(pageViews.entrySet());
        pages.sort((a, b) -> b.getValue() - a.getValue());

        System.out.println("Top Pages:");

        int limit = Math.min(10, pages.size());

        for (int i = 0; i < limit; i++) {
            String url = pages.get(i).getKey();
            int views = pages.get(i).getValue();
            int unique = uniqueVisitors.get(url).size();

            System.out.println((i + 1) + ". " + url + " - " + views + " views (" + unique + " unique)");
        }

        System.out.println("\nTraffic Sources:");

        for (Map.Entry<String, Integer> entry : trafficSources.entrySet()) {
            System.out.println(entry.getKey() + " - " + entry.getValue());
        }
    }

    public static void main(String[] args) {

        Question5 analytics = new Question5();

        analytics.processEvent(new Event("/article/breaking-news", "user_123", "google"));
        analytics.processEvent(new Event("/article/breaking-news", "user_456", "facebook"));
        analytics.processEvent(new Event("/sports/championship", "user_111", "google"));
        analytics.processEvent(new Event("/sports/championship", "user_222", "direct"));
        analytics.processEvent(new Event("/article/breaking-news", "user_123", "google"));
        analytics.processEvent(new Event("/sports/championship", "user_333", "facebook"));

        System.out.println("getDashboard() →");
        analytics.getDashboard();
    }
}