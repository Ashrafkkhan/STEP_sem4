package sem;
import java.util.*;

public class InventoryManager {

    private HashMap<String, Integer> stock = new HashMap<>();
    private LinkedHashMap<String, Queue<Integer>> waitingList = new LinkedHashMap<>();

    public InventoryManager() {
        stock.put("IPHONE15_256GB", 100);
        waitingList.put("IPHONE15_256GB", new LinkedList<>());
    }

    public synchronized String purchaseItem(String productId, int userId) {
        int available = stock.getOrDefault(productId, 0);
        if (available > 0) {
            stock.put(productId, available - 1);
            return "Success, " + (available - 1) + " units remaining";
        } else {
            waitingList.get(productId).add(userId);
            return "Added to waiting list, position #" + waitingList.get(productId).size();
        }
    }

    public int checkStock(String productId) {
        return stock.getOrDefault(productId, 0);
    }

    public List<Integer> getWaitingList(String productId) {
        return new ArrayList<>(waitingList.getOrDefault(productId, new LinkedList<>()));
    }

    public static void main(String[] args) {
        InventoryManager manager = new InventoryManager();

        System.out.println(manager.checkStock("IPHONE15_256GB"));
        System.out.println(manager.purchaseItem("IPHONE15_256GB", 12345));
        System.out.println(manager.purchaseItem("IPHONE15_256GB", 67890));
        System.out.println(manager.getWaitingList("IPHONE15_256GB"));
    }
}
