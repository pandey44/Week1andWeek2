import java.util.*;

class DNSEntry {
    String domain;
    String ipAddress;
    long expiryTime;

    DNSEntry(String domain, String ipAddress, int ttl) {
        this.domain = domain;
        this.ipAddress = ipAddress;
        this.expiryTime = System.currentTimeMillis() + ttl * 1000;
    }

    boolean isExpired() {
        return System.currentTimeMillis() > expiryTime;
    }
}

public class Question3 {

    private int capacity = 100;
    private LinkedHashMap<String, DNSEntry> cache;
    private int hits = 0;
    private int misses = 0;

    public Question3() {
        cache = new LinkedHashMap<String, DNSEntry>(capacity, 0.75f, true) {
            protected boolean removeEldestEntry(Map.Entry<String, DNSEntry> eldest) {
                return size() > capacity;
            }
        };
    }

    private String queryUpstream(String domain) {
        if (domain.equals("google.com")) return "172.217.14.206";
        if (domain.equals("facebook.com")) return "157.240.22.35";
        return "192.168.1.1";
    }

    public String resolve(String domain) {
        long start = System.nanoTime();

        if (cache.containsKey(domain)) {
            DNSEntry entry = cache.get(domain);

            if (!entry.isExpired()) {
                hits++;
                double time = (System.nanoTime() - start) / 1000000.0;
                return "Cache HIT → " + entry.ipAddress + " (retrieved in " + String.format("%.2f", time) + "ms)";
            } else {
                cache.remove(domain);
            }
        }

        misses++;
        String ip = queryUpstream(domain);
        cache.put(domain, new DNSEntry(domain, ip, 300));
        return "Cache MISS → Query upstream → " + ip + " (TTL: 300s)";
    }

    public String getCacheStats() {
        int total = hits + misses;
        double hitRate = total == 0 ? 0 : ((double) hits / total) * 100;
        return "Hit Rate: " + String.format("%.2f", hitRate) + "%";
    }

    public static void main(String[] args) {

        Question3 dns = new Question3();

        System.out.println("resolve(\"google.com\") → " + dns.resolve("google.com"));
        System.out.println("resolve(\"google.com\") → " + dns.resolve("google.com"));
        System.out.println("resolve(\"facebook.com\") → " + dns.resolve("facebook.com"));

        System.out.println("getCacheStats() → " + dns.getCacheStats());
    }
}