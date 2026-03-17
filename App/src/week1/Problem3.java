package week1;

import java.util.*;

class DNSEntry {
    String ip;
    long expiryTime;

    DNSEntry(String ip, long ttlSeconds) {
        this.ip = ip;
        this.expiryTime = System.currentTimeMillis() + (ttlSeconds * 1000);
    }

    boolean isExpired() {
        return System.currentTimeMillis() > expiryTime;
    }
}

public class Problem3 {

    private Map<String, DNSEntry> cache;
    private int hits;
    private int misses;

    public Problem3() {
        cache = new HashMap<>();
        hits = 0;
        misses = 0;
    }

    // Resolve domain
    public String resolve(String domain) {

        if (cache.containsKey(domain)) {
            DNSEntry entry = cache.get(domain);

            if (!entry.isExpired()) {
                hits++;
                return "Cache HIT → " + entry.ip;
            } else {
                cache.remove(domain); // remove expired
            }
        }

        // Cache miss → simulate DNS lookup
        misses++;
        String ip = fetchFromDNS(domain);

        // Store with TTL (5 seconds for demo)
        cache.put(domain, new DNSEntry(ip, 5));

        return "Cache MISS → " + ip;
    }

    // Simulated DNS fetch
    private String fetchFromDNS(String domain) {
        return "192.168." + new Random().nextInt(255) + "." + new Random().nextInt(255);
    }

    // Stats
    public void getStats() {
        int total = hits + misses;
        double hitRate = total == 0 ? 0 : (hits * 100.0 / total);

        System.out.println("Hits: " + hits);
        System.out.println("Misses: " + misses);
        System.out.println("Hit Rate: " + hitRate + "%");
    }

    public static void main(String[] args) throws InterruptedException {

        Problem3 dns = new Problem3();

        System.out.println(dns.resolve("google.com")); // MISS
        System.out.println(dns.resolve("google.com")); // HIT

        Thread.sleep(6000); // wait for expiry

        System.out.println(dns.resolve("google.com")); // EXPIRED → MISS

        dns.getStats();
    }
}