package sem;
import java.util.*;

public class UsernameService {

    private HashMap<String, String> users = new HashMap<>();
    private HashMap<String, Integer> attempts = new HashMap<>();

    public boolean checkAvailability(String username) {

        if (attempts.containsKey(username)) {
            attempts.put(username, attempts.get(username) + 1);
        } else {
            attempts.put(username, 1);
        }

        if (users.containsKey(username)) {
            return false;
        } else {
            return true;
        }
    }

    public void registerUser(String username, String userId) {
        users.put(username, userId);
    }

    public ArrayList<String> suggestAlternatives(String username) {
        ArrayList<String> suggestions = new ArrayList<>();

        if (!users.containsKey(username)) {
            return suggestions;
        }

        for (int i = 1; i <= 3; i++) {
            String newName = username + i;
            if (!users.containsKey(newName)) {
                suggestions.add(newName);
            }
        }

        String dotVersion = username.replace("_", ".");
        if (!users.containsKey(dotVersion)) {
            suggestions.add(dotVersion);
        }

        return suggestions;
    }

    public String getMostAttempted() {
        String maxUser = "";
        int maxCount = 0;

        for (String key : attempts.keySet()) {
            int count = attempts.get(key);
            if (count > maxCount) {
                maxCount = count;
                maxUser = key;
            }
        }

        return maxUser;
    }

    public static void main(String[] args) {

        UsernameService service = new UsernameService();

        service.registerUser("ash", "U1");

        System.out.println(service.checkAvailability("ash"));
        System.out.println(service.checkAvailability("ash_khan"));
        System.out.println(service.suggestAlternatives("ash"));
        System.out.println(service.getMostAttempted());
    }
}

