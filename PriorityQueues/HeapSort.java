package PriorityQueues;

public class HeapSort{

    public static void sort(Comparable[] a){
        int n = a.length;
        buildHeap(a);
        while (n > 1){
            exch(a, 1, n);
            sink(a, 1, --n);
        }
    }

    private static void buildHeap(Comparable[] a){
        int n= a.length;
        for(int i =n/2; i>=1; i--)
            sink(a,i,n);
    }
    private static void sink(Comparable[] a, int k, int n){
        while (k*2 <= n){
            int j = k * 2;
            if(less(a, j, j+1))
                j++;
            if(!less(a, k, j))
                break;
            exch(a, k, j);
            k = j;
        }
    }
    private static boolean less(Comparable[] a, int i, int j){
        return a[i].compareTo(a[j]) < 0;
    }
    private static void exch(Comparable[] a, int i, int j){
        Comparable temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }
}
