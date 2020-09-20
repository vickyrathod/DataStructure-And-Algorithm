package org.example.thread.divideandconcur;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.ForkJoinWorkerThread;
import java.util.concurrent.RecursiveTask;

public class MergeSortMultipleThread {

    /**
     * This code is to implement merge sort using multiple threads.
     * Here ForkJoinPool is used to limit threads creation.
     */
    public static class MergeSortImplematation extends RecursiveTask<int[]> {

        int start, end;
        int[] arr;

        /**
         * Creates a ForkJoinWorkerThread operating in the given pool.
         */
        public MergeSortImplematation(int start, int end, int[] arr) {
            this.start = start;
            this.end = end;
            this.arr = arr;
        }


        @Override
        protected int[] compute() {
            // Base condition
            if(start >= end) {
                int[] a = new int[1];
                a[0] = arr[start];
                return a;
            }

            int mid = (start + end) / 2;

            //devide start
            // left call
            MergeSortImplematation left = new MergeSortImplematation(start, mid, arr);
            // right call
            MergeSortImplematation right = new MergeSortImplematation(mid + 1, end, arr);
            ForkJoinTask<int[]> leftList = right.fork();
            // divide end

            // concure
            // merge
            return merge(left.compute(), leftList.join());
        }

        /**
         * @param a
         * @param b
         * @return
         */
        private int[] merge(int[] a, int[] b) {

            int m = a.length;
            int n = b.length;

            int[] temp = new int[n + m ];

            int i = 0;
            int j = 0;
            int k = 0;

            while(i < m && j < n) {
                if(a[i] < b[j]){
                    temp[k] = a[i];
                    i ++;
                } else {
                    temp[k] = b[j];
                    j ++;
                }
                k ++;
            }

            while(i < m)
                temp[k ++ ] = a[i ++];

            while(j < n)
                temp[k ++ ] = b[j ++];

            return temp;
        }
    }

    public static void main(String args[]) {
        Random random = new Random();
        int n = 10;
        int[] arr = new int[n];

        for(int i = 0; i < n; i++)
            arr[i] = random.nextInt(100_000_0);

        ForkJoinPool pool = ForkJoinPool.commonPool();

        MergeSortImplematation sort = new MergeSortImplematation(0, n - 1, arr);

        // start executing
        int[] ans = pool.invoke(sort);
        for(int i = 0; i < n; i++)
            System.out.print(ans[i] + " ");
    }
}
