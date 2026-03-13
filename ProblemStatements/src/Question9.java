import java.util.*;

class Transaction {
    int id;
    int amount;
    String merchant;
    String account;
    int time;

    Transaction(int id, int amount, String merchant, String account, int time) {
        this.id = id;
        this.amount = amount;
        this.merchant = merchant;
        this.account = account;
        this.time = time;
    }
}

public class Question9 {

    private List<Transaction> transactions = new ArrayList<>();

    public void addTransaction(Transaction t) {
        transactions.add(t);
    }

    public List<String> findTwoSum(int target) {
        HashMap<Integer, Transaction> map = new HashMap<>();
        List<String> result = new ArrayList<>();

        for (Transaction t : transactions) {
            int complement = target - t.amount;

            if (map.containsKey(complement)) {
                Transaction other = map.get(complement);
                result.add("(id:" + other.id + ", id:" + t.id + ")");
            }

            map.put(t.amount, t);
        }

        return result;
    }

    public List<String> detectDuplicates() {

        HashMap<String, List<String>> map = new HashMap<>();
        List<String> result = new ArrayList<>();

        for (Transaction t : transactions) {
            String key = t.amount + "-" + t.merchant;

            map.putIfAbsent(key, new ArrayList<>());
            map.get(key).add(t.account);
        }

        for (Map.Entry<String, List<String>> e : map.entrySet()) {
            if (e.getValue().size() > 1) {
                String[] parts = e.getKey().split("-");
                result.add("{amount:" + parts[0] + ", merchant:\"" + parts[1] + "\", accounts:" + e.getValue() + "}");
            }
        }

        return result;
    }

    public List<List<Integer>> findKSum(int k, int target) {
        List<List<Integer>> result = new ArrayList<>();
        backtrack(0, k, target, new ArrayList<>(), result);
        return result;
    }

    private void backtrack(int start, int k, int target, List<Integer> path, List<List<Integer>> result) {

        if (path.size() == k) {
            int sum = 0;
            for (int id : path) {
                for (Transaction t : transactions) {
                    if (t.id == id) sum += t.amount;
                }
            }

            if (sum == target) {
                result.add(new ArrayList<>(path));
            }
            return;
        }

        for (int i = start; i < transactions.size(); i++) {
            path.add(transactions.get(i).id);
            backtrack(i + 1, k, target, path, result);
            path.remove(path.size() - 1);
        }
    }

    public static void main(String[] args) {

        Question9 system = new Question9();

        system.addTransaction(new Transaction(1, 500, "Store A", "acc1", 1000));
        system.addTransaction(new Transaction(2, 300, "Store B", "acc2", 1015));
        system.addTransaction(new Transaction(3, 200, "Store C", "acc3", 1030));
        system.addTransaction(new Transaction(4, 500, "Store A", "acc4", 1045));

        System.out.println("findTwoSum(target=500) → " + system.findTwoSum(500));
        System.out.println("detectDuplicates() → " + system.detectDuplicates());
        System.out.println("findKSum(k=3, target=1000) → " + system.findKSum(3, 1000));
    }
}