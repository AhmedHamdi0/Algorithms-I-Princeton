public class QuickFind {
    private final int[] id;

    // Initialize union-find data structure with N objects (0 to N – 1)
    // set id of each object to itself (N array accesses)
    public QuickFind(int N){
        id = new int[N];
        for(int i = 0; i < N; i++){
            id[i] = i;
        }
    }

    // Add connection between p and q
    // change all entries with id[p] to id[q] (at most 2N + 2 array accesses)
    public void union(int p, int q){
        int pid = id[p];
        int qid = id[q];
        for (int i = 0; i < id.length; i++) {
            if (id[i] == pid)
                id[i] = qid;
        }
    }

    // Are p and q in the same component?
    // check whether p and q are in the same component (2 array accesses)
    public boolean connected(int p, int q) {
        return id[p] == id[q];
    }

    // Component identifier for p (0 to N – 1)
    public int find(int p) {
        return id[p];
    }

    // Number of components
    // If the element is equal to its index i, it indicates that a new component has been found.
    public int count(){
        int count = 0;
        for (int i = 0; i < id.length; i++) {
            if (id[i] == i)
                count++;
        }
        return count;
    }

    public static void main(String[] args) {
        int N = 10; // Number of objects

        QuickFind quickFind = new QuickFind(N);

        quickFind.union(0, 1);
        quickFind.union(2, 3);
        quickFind.union(4, 5);
        quickFind.union(6, 7);
        quickFind.union(8, 9);
        quickFind.union(0,9);

        System.out.println("Component count: " + quickFind.count()); // Expected output: 5
        System.out.println("Component identifier: " + quickFind.find(0));

        System.out.println("Connected(0, 1): " + quickFind.connected(0, 1)); // Expected output: true
        System.out.println("Connected(2, 3): " + quickFind.connected(2, 3)); // Expected output: true
        System.out.println("Connected(4, 5): " + quickFind.connected(4, 5)); // Expected output: true
        System.out.println("Connected(6, 7): " + quickFind.connected(6, 7)); // Expected output: true
        System.out.println("Connected(8, 9): " + quickFind.connected(8, 9)); // Expected output: true
        System.out.println("Connected(1, 8): " + quickFind.connected(1, 8)); // Expected output: true
        System.out.println("Connected(1, 3): " + quickFind.connected(1, 3)); // Expected output: false

    }
}
