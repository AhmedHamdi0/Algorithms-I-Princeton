package PriorityQueues;

public class HeapMaxPQ<key extends Comparable<key>> {
     private key[] pq;
     private int n;

     public HeapMaxPQ(int capacity){
         pq = (key[]) new Comparable[capacity+1];
     }

     public void insert(key x){
         pq[++n] = x;
         swim(n);
     }

     public key delMax(){
         key max = pq[1];
         exch(1, n--);
         sink(1);
         pq[n++] = null;
         return max;
     }

     private void swim(int k){
         while (k > 1 && less(k/2, k)){
            exch(k/2, k);
            k = k/2;
         }
     }

     private void sink(int k){
         while (2*k <= n){
             int j = 2*k;
             if(less(j, j+1))
                 j++;
             if (!less(k, j))
                 break;
             exch(j, k);
             k = j;
         }
     }

     public boolean isEmpty(){
         return n==0;
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
