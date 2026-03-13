import java.util.*;

public class Question2 {

    private HashMap<String, Integer> stock = new HashMap<>();
    private HashMap<String, LinkedHashMap<Integer, Integer>> waitingList = new HashMap<>();

    public void addProduct(String productId, int quantity) {
        stock.put(productId, quantity);
        waitingList.put(productId, new LinkedHashMap<>());
    }

    public String checkStock(String productId) {
        int count = stock.getOrDefault(productId, 0);
        return count + " units available";
    }

    public synchronized String purchaseItem(String productId, int userId) {
        int count = stock.getOrDefault(productId, 0);

        if (count > 0) {
            stock.put(productId, count - 1);
            return "Success, " + (count - 1) + " units remaining";
        } else {
            LinkedHashMap<Integer, Integer> queue = waitingList.get(productId);
            int position = queue.size() + 1;
            queue.put(userId, position);
            return "Added to waiting list, position #" + position;
        }
    }

    public static void main(String[] args) {

        Question2 system = new Question2();

        system.addProduct("IPHONE15_256GB", 100);

        System.out.println("checkStock(\"IPHONE15_256GB\") → " + system.checkStock("IPHONE15_256GB"));

        System.out.println("purchaseItem(\"IPHONE15_256GB\", 12345) → " +
                system.purchaseItem("IPHONE15_256GB", 12345));

        System.out.println("purchaseItem(\"IPHONE15_256GB\", 67890) → " +
                system.purchaseItem("IPHONE15_256GB", 67890));

        for (int i = 0; i < 98; i++) {
            system.purchaseItem("IPHONE15_256GB", 10000 + i);
        }

        System.out.println("purchaseItem(\"IPHONE15_256GB\", 99999) → " +
                system.purchaseItem("IPHONE15_256GB", 99999));
    }
}