import java.util.*;

class TokenBucket {
    int tokens;
    int maxTokens;
    long lastRefillTime;

    TokenBucket(int maxTokens) {
        this.maxTokens = maxTokens;
        this.tokens = maxTokens;
        this.lastRefillTime = System.currentTimeMillis();
    }

    void refill() {
        long now = System.currentTimeMillis();
        if (now - lastRefillTime >= 3600000) {
            tokens = maxTokens;
            lastRefillTime = now;
        }
    }
}

public class Question6 {

    private HashMap<String, TokenBucket> clients = new HashMap<>();
    private int limit = 1000;

    public synchronized String checkRateLimit(String clientId) {

        clients.putIfAbsent(clientId, new TokenBucket(limit));
        TokenBucket bucket = clients.get(clientId);

        bucket.refill();

        if (bucket.tokens > 0) {
            bucket.tokens--;
            return "Allowed (" + bucket.tokens + " requests remaining)";
        } else {
            long retry = (3600000 - (System.currentTimeMillis() - bucket.lastRefillTime)) / 1000;
            return "Denied (0 requests remaining, retry after " + retry + "s)";
        }
    }

    public String getRateLimitStatus(String clientId) {
        TokenBucket bucket = clients.get(clientId);
        int used = limit - bucket.tokens;
        long reset = bucket.lastRefillTime + 3600000;
        return "{used: " + used + ", limit: " + limit + ", reset: " + reset + "}";
    }

    public static void main(String[] args) {

        Question6 limiter = new Question6();

        System.out.println("checkRateLimit(\"abc123\") → " + limiter.checkRateLimit("abc123"));
        System.out.println("checkRateLimit(\"abc123\") → " + limiter.checkRateLimit("abc123"));
        System.out.println("checkRateLimit(\"abc123\") → " + limiter.checkRateLimit("abc123"));

        for (int i = 0; i < 997; i++) {
            limiter.checkRateLimit("abc123");
        }

        System.out.println("checkRateLimit(\"abc123\") → " + limiter.checkRateLimit("abc123"));
        System.out.println("getRateLimitStatus(\"abc123\") → " + limiter.getRateLimitStatus("abc123"));
    }
}