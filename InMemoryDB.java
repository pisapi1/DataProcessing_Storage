import java.util.*;

public class InMemoryDB {
    private Map<String, Integer> database;
    private Map<String, Integer> transactionCache;
    private boolean transactionStarted;

    public InMemoryDB() {
        database = new HashMap<>();
        transactionCache = new HashMap<>();
        transactionStarted = false;
    }

    public Integer get(String key) {
        if (transactionStarted && transactionCache.containsKey(key)) {
            // Return value from transaction cache if it exists
            return null; // Uncommitted changes should not be visible
        }
        // Otherwise, return value from the main database
        return database.get(key);
    }


    public void put(String key, int val) {
        if (!transactionStarted) {
            throw new IllegalStateException("Transaction is not in progress");
        }
        transactionCache.put(key, val);
    }

    public void begin_transaction() {
        if (transactionStarted) {
            throw new IllegalStateException("Transaction already in progress");
        }
        transactionStarted = true;
        transactionCache.clear();
    }

    public void commit() {
        if (!transactionStarted) {
            throw new IllegalStateException("No transaction in progress");
        }
        database.putAll(transactionCache);
        transactionCache.clear();
        transactionStarted = false;
    }

    public void rollback() {
        if (!transactionStarted) {
            throw new IllegalStateException("No transaction in progress");
        }
        transactionCache.clear();
        transactionStarted = false;
    }

    public static void main(String[] args) {
        InMemoryDB inmemoryDB = new InMemoryDB();

        System.out.println("1. Get non-existent key:");
        System.out.println("A: " + inmemoryDB.get("A"));

        System.out.println("\n2. Put without transaction:");
        try {
            inmemoryDB.put("A", 5);
        } catch (IllegalStateException e) {
            System.out.println("Error: " + e.getMessage());
        }

        System.out.println("\n3. Begin transaction and put:");
        inmemoryDB.begin_transaction();
        inmemoryDB.put("A", 5);
        System.out.println("A (before commit): " + inmemoryDB.get("A"));

       //System.out.println("\n4. Update within transaction:");
        inmemoryDB.put("A", 6);
        //System.out.println("A (before commit): " + inmemoryDB.get("A"));

        System.out.println("\n5. Commit transaction:");
        inmemoryDB.commit();
        System.out.println("A (AFTER COMMIT): " + inmemoryDB.get("A"));

        System.out.println("\n6. Commit without transaction:");
        try {
            inmemoryDB.commit();
        } catch (IllegalStateException e) {
            System.out.println("Error: " + e.getMessage());
        }

        System.out.println("\n7. Rollback without transaction:");
        try {
            inmemoryDB.rollback();
        } catch (IllegalStateException e) {
            System.out.println("Error: " + e.getMessage());
        }

        System.out.println("\n8. New transaction with rollback:");
        //inmemoryDB.begin_transaction();
        //inmemoryDB.put("B", 10);
        //System.out.println("B (before rollback): " + inmemoryDB.get("B"));
        //inmemoryDB.rollback();
        System.out.println("B not in database yet: " + inmemoryDB.get("B"));

        inmemoryDB.begin_transaction();
        inmemoryDB.put("B", 10);
        inmemoryDB.rollback();

        System.out.println("\n9. Final state:");
        System.out.println("A: " + inmemoryDB.get("A"));
        System.out.println("B: " + inmemoryDB.get("B"));
    }
}
