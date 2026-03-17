package week2;

import java.util.*;

class TokenBucket {
    int tokens;
    int maxTokens;
    long lastRefillTime;
    int refillRate; // tokens per second

    public TokenBucket(int maxTokens, int refillRate) {
        this.tokens = maxTokens;
        this.maxTokens = maxTokens;
        this.refillRate = refillRate;
        this.lastRefillTime = System.currentTimeMillis();
    }

    // Refill tokens based on time
    private void refill() {
        long now = System.currentTimeMillis();
        long elapsedSeconds = (now - lastRefillTime) / 1000;

        if (elapsedSeconds > 0) {
            int tokensToAdd = (int) (elapsedSeconds * refillRate);
            tokens = Math.min(maxTokens, tokens + tokensToAdd);
            lastRefillTime = now;
        }
    }

    // Allow request
    public synchronized boolean allowRequest() {
        refill();

        if (tokens > 0) {
            tokens--;
            return true;
        }
        return false;
    }

    public int getTokens() {
        return tokens;
    }
}

public class Problem6 {

    private Map<String, TokenBucket> clientMap;

    public Problem6() {
        clientMap = new HashMap<>();
    }

    // Get or create bucket
    private TokenBucket getBucket(String clientId) {
        clientMap.putIfAbsent(clientId, new TokenBucket(5, 1)); // demo: 5 max tokens
        return clientMap.get(clientId);
    }

    // Rate limit check
    public String checkRateLimit(String clientId) {
        TokenBucket bucket = getBucket(clientId);

        if (bucket.allowRequest()) {
            return "Allowed → Remaining tokens: " + bucket.getTokens();
        } else {
            return "Denied → Rate limit exceeded";
        }
    }

    public static void main(String[] args) throws InterruptedException {

        Problem6 limiter = new Problem6();
        String client = "user123";

        // Simulate multiple requests
        for (int i = 0; i < 7; i++) {
            System.out.println(limiter.checkRateLimit(client));
        }

        // Wait for refill
        System.out.println("\nWaiting for refill...\n");
        Thread.sleep(4000);

        System.out.println(limiter.checkRateLimit(client));
    }
}