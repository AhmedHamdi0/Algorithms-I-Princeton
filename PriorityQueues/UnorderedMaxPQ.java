package PriorityQueues;

public class UnorderedMaxPQ <key extends Comparable<key>>{
    private key[] pq;
    private int n;

    public UnorderedMaxPQ(int capacity){
        pq = (key[]) new Comparable[capacity];
    }

    public boolean isEmpty(){
        return n == 0;
    }

    public void insert(key x){
        pq[n++] = x;
    }

    public key delMax(){
        int max = 0;
        for(int i=1 ; i<n ; i++){
            if(less(max, i))
                max = i;
        }
        exch(max, n-1);
        return pq[--n];
    }

    private boolean less(int i, int j){
        return pq[i].compareTo(pq[j]) < 0;
    }

    private void exch(int i, int j){
        key temp = pq[i];
        pq[i] = pq[j];
        pq[j] = temp;
    }
}
