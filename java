import java.util.*;

public class RecommendationSystem {

    static Map<String, Map<String, Integer>> userRatings = new HashMap<>();

    public static void main(String[] args) {

        addRating("User1", "Laptop", 5);
        addRating("User1", "Mobile", 3);
        addRating("User1", "Headphones", 4);

        addRating("User2", "Laptop", 4);
        addRating("User2", "Mobile", 2);
        addRating("User2", "Headphones", 5);
        addRating("User2", "Tablet", 4);

        addRating("User3", "Laptop", 2);
        addRating("User3", "Mobile", 5);
        addRating("User3", "Tablet", 5);

        Scanner sc = new Scanner(System.in);
        System.out.println("Enter username (User1/User2/User3): ");
        String targetUser = sc.nextLine();

        recommendItems(targetUser);
    }

    static void addRating(String user, String item, int rating) {
        userRatings.putIfAbsent(user, new HashMap<>());
        userRatings.get(user).put(item, rating);
    }

    static void recommendItems(String targetUser) {

        if (!userRatings.containsKey(targetUser)) {
            System.out.println("User not found!");
            return;
        }

        String mostSimilarUser = null;
        double highestSimilarity = -1;

        for (String otherUser : userRatings.keySet()) {
            if (!otherUser.equals(targetUser)) {
                double similarity = calculateSimilarity(targetUser, otherUser);
                if (similarity > highestSimilarity) {
                    highestSimilarity = similarity;
                    mostSimilarUser = otherUser;
                }
            }
        }

        System.out.println("Most similar user: " + mostSimilarUser);

        Map<String, Integer> targetRatings = userRatings.get(targetUser);
        Map<String, Integer> similarUserRatings = userRatings.get(mostSimilarUser);

        System.out.println("Recommended Items:");

        for (String item : similarUserRatings.keySet()) {
            if (!targetRatings.containsKey(item)) {
                System.out.println(item);
            }
        }
    }

    static double calculateSimilarity(String user1, String user2) {

        Map<String, Integer> ratings1 = userRatings.get(user1);
        Map<String, Integer> ratings2 = userRatings.get(user2);

        double sum = 0;

        for (String item : ratings1.keySet()) {
            if (ratings2.containsKey(item)) {
                sum += Math.pow(ratings1.get(item) - ratings2.get(item), 2);
            }
        }

        return 1 / (1 + Math.sqrt(sum));
    }
}
