package week2;

import java.util.*;

class Transaction {
    int id;
    int amount;
    String merchant;

    Transaction(int id, int amount, String merchant) {
        this.id = id;
        this.amount = amount;
        this.merchant = merchant;
    }

    public String toString() {
        return "(id:" + id + ", amount:" + amount + ")";
    }
}

public class Problem9 {

    // Two-Sum
    public static List<String> findTwoSum(List<Transaction> transactions, int target) {

        Map<Integer, Transaction> map = new HashMap<>();
        List<String> result = new ArrayList<>();

        for (Transaction t : transactions) {
            int complement = target - t.amount;

            if (map.containsKey(complement)) {
                result.add(map.get(complement) + " + " + t);
            }

            map.put(t.amount, t);
        }

        return result;
    }

    // Duplicate detection
    public static List<String> detectDuplicates(List<Transaction> transactions) {

        Map<String, List<Integer>> map = new HashMap<>();
        List<String> duplicates = new ArrayList<>();

        for (Transaction t : transactions) {
            String key = t.amount + "-" + t.merchant;

            map.putIfAbsent(key, new ArrayList<>());
            map.get(key).add(t.id);
        }

        for (Map.Entry<String, List<Integer>> entry : map.entrySet()) {
            if (entry.getValue().size() > 1) {
                duplicates.add(entry.getKey() + " → " + entry.getValue());
            }
        }

        return duplicates;
    }

    public static void main(String[] args) {

        List<Transaction> transactions = new ArrayList<>();

        transactions.add(new Transaction(1, 500, "StoreA"));
        transactions.add(new Transaction(2, 300, "StoreB"));
        transactions.add(new Transaction(3, 200, "StoreC"));
        transactions.add(new Transaction(4, 500, "StoreA")); // duplicate

        // Two-Sum
        System.out.println("Two Sum (target = 500):");
        System.out.println(findTwoSum(transactions, 500));

        // Duplicate Detection
        System.out.println("\nDuplicates:");
        System.out.println(detectDuplicates(transactions));
    }
}