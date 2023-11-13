/*
 Same as Union-Find but with improvements
 [1] Weighted quick-union ==> Link root of smaller tree to root of larger tree.
 [2] Quick union with path compression ==> Just after computing the root of p, set the id of each examined node to point to that root.
*/

public class QuickUnion {
    private final int[] id;
    private int[] sz;

    // Set id of each object to itself (N array accesses)
    public QuickUnion(int N){
        id = new int[N];
        for (int i = 0; i < N; i++) {
            id[i] = i;
        }
    }

    // Check if p and q have same root (depth of p and q array accesses)
    public boolean connected(int p, int q){
        return root(p) == root(q);
    }

    // Change root of p to point to root of q (depth of p and q array accesses)
    public void union(int p, int q){
        int i = root(p);
        int j = root(q);
        if (i == j)
            return;

        if (sz[i] < sz[j]) {
            id[i] = j;
            sz[j] += sz[i];
        }
        else {
            id[j] = i;
            sz[i] += sz[j];
        }
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

    // Chase parent pointers until reach root (depth of i array accesses)
    private int root(int i){
        while (i != id[i]){
            id[i] = id[id[i]];
            i = id[i];
        }
        return i;
    }

    public static void main(String[] args) {
        int N = 10; // Number of objects

        QuickUnion quickUnion = new QuickUnion(N);

        quickUnion.union(0, 1);
        quickUnion.union(2, 3);
        quickUnion.union(4, 5);
        quickUnion.union(6, 7);
        quickUnion.union(8, 9);
        quickUnion.union(1, 9);

        System.out.println("Component count: " + quickUnion.count()); // Expected output: 4
        System.out.println("Component identifier: " + quickUnion.find(0));

        System.out.println("Connected(2, 3): " + quickUnion.connected(2, 3)); // Expected output: true
        System.out.println("Connected(4, 5): " + quickUnion.connected(4, 5)); // Expected output: true
        System.out.println("Connected(6, 7): " + quickUnion.connected(6, 7)); // Expected output: true
        System.out.println("Connected(8, 9): " + quickUnion.connected(8, 9)); // Expected output: true
        System.out.println("Connected(0, 9): " + quickUnion.connected(0, 9)); // Expected output: true

        System.out.println("Connected(1, 3): " + quickUnion.connected(1, 3)); // Expected output: false
        System.out.println("Connected(4, 7): " + quickUnion.connected(4, 7)); // Expected output: false
        System.out.println("Connected(8, 0): " + quickUnion.connected(8, 0)); // Expected output: false
    }
}
