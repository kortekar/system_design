import java.util.*;

class Main {
    private static final String BASE62 = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final String BASE_HOST = "http://short.url/";
    private static final int BASE = BASE62.length();
    private static long counter = 1;

    private Map<String, String> shortToLongMap = new HashMap<>();
    private Map<String, String> longToShortMap = new HashMap<>();

    public String shortenUrl(String longUrl) {
        if (longToShortMap.containsKey(longUrl)) {
            return BASE_HOST + longToShortMap.get(longUrl);
        }
        String shortCode = encode(counter++);
        shortToLongMap.put(shortCode, longUrl);
        longToShortMap.put(longUrl, shortCode);
        return BASE_HOST + shortCode;
    }

    public String expandUrl(String shortUrl) {
        if (!shortUrl.startsWith(BASE_HOST)) return null;
        String code = shortUrl.substring(BASE_HOST.length());
        return shortToLongMap.getOrDefault(code, null);
    }

    public String encode(long num) {
        StringBuilder sb = new StringBuilder();
        while (num > 0) {
            sb.append(BASE62.charAt((int) (num % BASE)));
            num /= BASE;
        }
        return sb.reverse().toString();
    }

    public static void main(String[] args) {
        Main shortner = new Main();

        String longUrl1 = "https://example.com/some/very/long/url";
        String shortUrl1 = shortner.shortenUrl(longUrl1);
        System.out.println("Short URL: " + shortUrl1);

        String expandedUrl1 = shortner.expandUrl(shortUrl1);
        System.out.println("Expanded URL: " + expandedUrl1);

        String longUrl2 = "https://openai.com/gpt/";
        String shortUrl2 = shortner.shortenUrl(longUrl2);
        System.out.println("Short URL: " + shortUrl2);

        String expandedUrl2 = shortner.expandUrl(shortUrl2);
        System.out.println("Expanded URL: " + expandedUrl2);
    }
}
