package week1;

import java.util.*;

public class Problem2 {

    private Map<String, Integer> stockMap;          // product → stock
    private Map<String, Queue<Integer>> waitList;   // product → waiting users

    public Problem2() {
        stockMap = new HashMap<>();
        waitList = new HashMap<>();
    }

    // Add product
    public void addProduct(String productId, int stock) {
        stockMap.put(productId, stock);
        waitList.put(productId, new LinkedList<>());
    }

    // Check stock
    public int checkStock(String productId) {
        return stockMap.getOrDefault(productId, 0);
    }

    // Purchase item (thread-safe)
    public synchronized String purchaseItem(String productId, int userId) {

        int stock = stockMap.getOrDefault(productId, 0);

        if (stock > 0) {
            stockMap.put(productId, stock - 1);
            return "Success: User " + userId + " purchased. Remaining: " + (stock - 1);
        } else {
            waitList.get(productId).add(userId);
            return "Out of stock. User " + userId + " added to waiting list. Position: "
                    + waitList.get(productId).size();
        }
    }

    // View waiting list
    public Queue<Integer> getWaitingList(String productId) {
        return waitList.get(productId);
    }

    public static void main(String[] args) {

        Problem2 system = new Problem2();

        system.addProduct("IPHONE15_256GB", 2);

        System.out.println(system.purchaseItem("IPHONE15_256GB", 101));
        System.out.println(system.purchaseItem("IPHONE15_256GB", 102));
        System.out.println(system.purchaseItem("IPHONE15_256GB", 103)); // goes to waitlist
        System.out.println(system.purchaseItem("IPHONE15_256GB", 104)); // goes to waitlist

        System.out.println("Waiting List: " + system.getWaitingList("IPHONE15_256GB"));
    }
}