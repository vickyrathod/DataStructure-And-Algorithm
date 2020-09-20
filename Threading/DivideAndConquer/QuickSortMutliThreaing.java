package org.example.thread.divideandconcur;

import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class QuickSortMutliThreaing extends RecursiveTask<Integer> {

    int start, end;
    int[] arr;
    /**
     * Finding random pivote and partion array on pivote
     * @param start
     * @param end
     * @param arr
     * @return
     */
    private int partion(int start, int end, int[] arr){
        int i = start, j = end;
        int pivote = new Random().nextInt(j - i) + i;

        // Swap the pivote with end element of array;
        int t = arr[j];
        arr[j] = arr[pivote];
        arr[pivote] = t;
        j --;

        // Start partioning
        while(i <= j){
            if(arr[i] <= arr[end]){
                i ++;
                continue;
            }
            if(arr[j] >= arr[end]) {
                j --;
                continue;
            }

            t = arr[j];
            arr[j] = arr[i];
            arr[i] = t;
            j --;
            i ++;
        }

        // Swap pivote to its correct position
        t = arr[j + 1];
        arr[j + 1] = arr[end];
        arr[end] = t;
        return j + 1;
    }

    public QuickSortMutliThreaing(int start, int end, int[] arr) {
        this.arr = arr;
        this.start = start;
        this.end = end;
    }

    @Override
    protected Integer compute() {
        // Base case
        if(start >= end)
            return null;
        // Find partion
        int p = partion(start, end, arr);

        QuickSortMutliThreaing left = new QuickSortMutliThreaing(start, p - 1, arr);
        QuickSortMutliThreaing right = new QuickSortMutliThreaing(p + 1, end, arr);
        left.fork();
        right.compute();
        left.join();
        return null;
    }

    public static void main(String args[]){
        QuickSortSequential sort = new QuickSortSequential();
        Random random = new Random();
        int n = 10;
        int[] arr = new int[n];
        ForkJoinPool pool = ForkJoinPool.commonPool();

        for(int i = 0; i < n; i ++)
            arr[i] = random.nextInt(100_000_0);

        pool.invoke(new QuickSortMutliThreaing(0, n - 1, arr));
        pool.shutdown();
        for(int i = 0; i < n; i ++)
            System.out.print(arr[i] + " ");
    }
}
