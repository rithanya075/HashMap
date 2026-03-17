package week1;

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

public class Problem5 {

    private Map<String, Integer> pageViews;              // url → count
    private Map<String, Set<String>> uniqueVisitors;     // url → userIds
    private Map<String, Integer> trafficSources;         // source → count

    public Problem5() {
        pageViews = new HashMap<>();
        uniqueVisitors = new HashMap<>();
        trafficSources = new HashMap<>();
    }

    // Process incoming event
    public void processEvent(Event event) {

        // Page views
        pageViews.put(event.url,
                pageViews.getOrDefault(event.url, 0) + 1);

        // Unique visitors
        uniqueVisitors.putIfAbsent(event.url, new HashSet<>());
        uniqueVisitors.get(event.url).add(event.userId);

        // Traffic sources
        trafficSources.put(event.source,
                trafficSources.getOrDefault(event.source, 0) + 1);
    }

    // Get top pages
    public void getTopPages() {

        List<Map.Entry<String, Integer>> list =
                new ArrayList<>(pageViews.entrySet());

        list.sort((a, b) -> b.getValue() - a.getValue());

        System.out.println("Top Pages:");

        int count = 0;
        for (Map.Entry<String, Integer> entry : list) {
            String url = entry.getKey();
            int views = entry.getValue();
            int unique = uniqueVisitors.get(url).size();

            System.out.println((count + 1) + ". " + url +
                    " - " + views + " views (" + unique + " unique)");

            count++;
            if (count == 5) break; // top 5
        }
    }

    // Show traffic sources
    public void getTrafficSources() {

        System.out.println("\nTraffic Sources:");

        int total = trafficSources.values().stream().mapToInt(i -> i).sum();

        for (Map.Entry<String, Integer> entry : trafficSources.entrySet()) {
            double percent = (entry.getValue() * 100.0) / total;

            System.out.println(entry.getKey() + ": " + percent + "%");
        }
    }

    public static void main(String[] args) {

        Problem5 dashboard = new Problem5();

        // Simulated events
        dashboard.processEvent(new Event("/news", "user1", "google"));
        dashboard.processEvent(new Event("/news", "user2", "facebook"));
        dashboard.processEvent(new Event("/sports", "user3", "google"));
        dashboard.processEvent(new Event("/news", "user1", "google")); // repeat user
        dashboard.processEvent(new Event("/tech", "user4", "direct"));

        dashboard.getTopPages();
        dashboard.getTrafficSources();
    }
}