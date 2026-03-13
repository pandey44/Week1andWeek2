import java.util.*;

public class Question1 {

    private HashMap<String, Integer> users = new HashMap<>();
    private HashMap<String, Integer> attempts = new HashMap<>();

    public boolean checkAvailability(String username) {
        attempts.put(username, attempts.getOrDefault(username, 0) + 1);
        return !users.containsKey(username);
    }

    public List<String> suggestAlternatives(String username) {
        List<String> suggestions = new ArrayList<>();

        String s1 = username + "1";
        String s2 = username + "2";
        String s3 = username.replace("_", ".");

        if (!users.containsKey(s1)) suggestions.add(s1);
        if (!users.containsKey(s2)) suggestions.add(s2);
        if (!users.containsKey(s3)) suggestions.add(s3);

        return suggestions;
    }

    public String getMostAttempted() {
        int max = 0;
        String popular = "";

        for (Map.Entry<String, Integer> entry : attempts.entrySet()) {
            if (entry.getValue() > max) {
                max = entry.getValue();
                popular = entry.getKey();
            }
        }

        return popular + " (" + max + " attempts)";
    }

    public void registerUser(String username, int userId) {
        users.put(username, userId);
    }

    public static void main(String[] args) {

        Question1 system = new Question1();

        system.registerUser("john_doe", 101);

        for (int i = 0; i < 10543; i++) {
            system.checkAvailability("admin");
        }

        System.out.println("checkAvailability(\"john_doe\") → " + system.checkAvailability("john_doe"));
        System.out.println("checkAvailability(\"jane_smith\") → " + system.checkAvailability("jane_smith"));
        System.out.println("suggestAlternatives(\"john_doe\") → " + system.suggestAlternatives("john_doe"));
        System.out.println("getMostAttempted() → " + system.getMostAttempted());
    }
}