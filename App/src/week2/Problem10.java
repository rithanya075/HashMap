package week2;

import java.util.*;

// LRU Cache using LinkedHashMap
class LRUCache<K, V> extends LinkedHashMap<K, V> {
    private int capacity;

    public LRUCache(int capacity) {
        super(capacity, 0.75f, true); // access order
        this.capacity = capacity;
    }

    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        return size() > capacity;
    }
}

public class Problem10 {

    private LRUCache<String, String> L1; // fast cache
    private Map<String, String> L2;      // medium cache
    private Map<String, String> L3;      // database

    private int l1Hits = 0, l2Hits = 0, l3Hits = 0;

    public Problem10() {
        L1 = new LRUCache<>(3);  // small for demo
        L2 = new HashMap<>();
        L3 = new HashMap<>();

        // preload database
        L3.put("video1", "Data1");
        L3.put("video2", "Data2");
        L3.put("video3", "Data3");
    }

    public String getVideo(String videoId) {

        // L1 check
        if (L1.containsKey(videoId)) {
            l1Hits++;
            return "L1 HIT → " + L1.get(videoId);
        }

        // L2 check
        if (L2.containsKey(videoId)) {
            l2Hits++;
            String data = L2.get(videoId);

            L1.put(videoId, data); // promote to L1
            return "L2 HIT → Promoted to L1";
        }

        // L3 (database)
        if (L3.containsKey(videoId)) {
            l3Hits++;
            String data = L3.get(videoId);

            L2.put(videoId, data); // store in L2
            return "L3 HIT → Added to L2";
        }

        return "Video not found";
    }

    public void getStats() {
        System.out.println("\nCache Stats:");
        System.out.println("L1 Hits: " + l1Hits);
        System.out.println("L2 Hits: " + l2Hits);
        System.out.println("L3 Hits: " + l3Hits);
    }

    public static void main(String[] args) {

        Problem10 system = new Problem10();

        System.out.println(system.getVideo("video1")); // L3
        System.out.println(system.getVideo("video1")); // L2
        System.out.println(system.getVideo("video1")); // L1

        System.out.println(system.getVideo("video2")); // L3
        System.out.println(system.getVideo("video3")); // L3

        system.getStats();
    }
}
