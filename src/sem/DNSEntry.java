package sem;

import java.util.*;

class DNSEntry {
    String ip;
    long expiryTime;

    DNSEntry(String ip, int ttlSeconds) {
        this.ip = ip;
        this.expiryTime = System.currentTimeMillis() + ttlSeconds * 1000;
    }

    boolean isExpired() {
        return System.currentTimeMillis() > expiryTime;
    }
}

public class SimpleDNSCache {

    private HashMap<String, DNSEntry> cache = new HashMap<>();
    private int hits = 0;
    private int misses = 0;

    public String resolve(String domain) {
        if (cache.containsKey(domain)) {
            DNSEntry entry = cache.get(domain);
            if (!entry.isExpired()) {
                hits++;
                return entry.ip + " (Cache HIT)";
            } else {
                cache.remove(domain);
            }
        }

        misses++;
        String ip = queryUpstreamDNS(domain);
        cache.put(domain, new DNSEntry(ip, 5)); // TTL = 5 seconds
        return ip + " (Cache MISS)";
    }

    private String queryUpstreamDNS(String domain) {

        return "172.217.14." + new Random().nextInt(255);
    }

    public void getCacheStats() {
        int total = hits + misses;
        double hitRate = total == 0 ? 0 : (hits * 100.0 / total);
        System.out.println("Cache Hit Rate: " + hitRate + "%, Total lookups: " + total);
    }

    public static void main(String[] args) throws InterruptedException {
        SimpleDNSCache dns = new SimpleDNSCache();

        System.out.println(dns.resolve("google.com")); // MISS
        System.out.println(dns.resolve("google.com")); // HIT
        Thread.sleep(6000);
        System.out.println(dns.resolve("google.com")); // MISS again
        dns.getCacheStats();
    }
}
