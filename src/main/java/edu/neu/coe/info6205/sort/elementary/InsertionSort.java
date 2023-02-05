/*
  (c) Copyright 2018, 2019 Phasmid Software
 */
package edu.neu.coe.info6205.sort.elementary;

import edu.neu.coe.info6205.sort.BaseHelper;
import edu.neu.coe.info6205.sort.Helper;
import edu.neu.coe.info6205.sort.SortWithHelper;
import edu.neu.coe.info6205.util.Config;
import edu.neu.coe.info6205.util.Timer;

import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class InsertionSort<X extends Comparable<X>> extends SortWithHelper<X> {

    /**
     * Constructor for any sub-classes to use.
     *
     * @param description the description.
     * @param N           the number of elements expected.
     * @param config      the configuration.
     */
    protected InsertionSort(String description, int N, Config config) {
        super(description, N, config);
    }

    /**
     * Constructor for InsertionSort
     *
     * @param N      the number elements we expect to sort.
     * @param config the configuration.
     */
    public InsertionSort(int N, Config config) {
        this(DESCRIPTION, N, config);
    }

    public InsertionSort(Config config) {
        this(new BaseHelper<>(DESCRIPTION, config));
    }

    /**
     * Constructor for InsertionSort
     *
     * @param helper an explicit instance of Helper to be used.
     */
    public InsertionSort(Helper<X> helper) {
        super(helper);
    }

    public InsertionSort() {
        this(BaseHelper.getHelper(InsertionSort.class));
    }

    /**
     * Sort the sub-array xs:from:to using insertion sort.
     *
     * @param xs   sort the array xs from "from" to "to".
     * @param from the index of the first element to sort
     * @param to   the index of the first element not to sort
     */
    public void sort(X[] xs, int from, int to) {
        final Helper<X> helper = getHelper();

        for (int i =  1; i < to; i++) {
            int j = i;
            while (j > 0 && helper.less(xs[j], xs[j - 1])) {
                helper.swap(xs, j - 1, j--);
            }
        }

    }

    public static final String DESCRIPTION = "Insertion sort";

    public static <T extends Comparable<T>> void sort(T[] ts) {
        new InsertionSort<T>().mutatingSort(ts);
    }

    public static void main(String args[]) {

        Random random = new Random();
        for (int k = 1; k <= 4; k++) {
            int n = 1000;
            for (int i = 0; i < 5; i++) {
                Integer[] arr = new Integer[n];
                for (int j = 0; j < n; j++) {
                    arr[j] = random.nextInt();
                }

                if(k==2){
                    Arrays.sort(arr);
                    System.out.print("Mean time for sorted array of size " + n + " is ");
                }
                else if(k==3){
                    Arrays.sort(arr, n/2, n);
                    System.out.print("Mean time for partially sorted array of size " + n + " is " );
                }
                else if(k==4){
                    Arrays.sort(arr, Collections.reverseOrder());
                    System.out.print("Mean time for a reverse ordered array of size " + n + " is " );
                }
                else{
                    System.out.print("Mean time for a randomly sorted array of size " + n + " is " );
                }
                int temp = n;
                Timer timer = new Timer();
                final double time = timer.repeat(50, () -> 5, t -> {
                    new InsertionSort().sort(arr, 0, temp);
                    return null;
                });

                System.out.println(time);

                n = n * 2;
            }

        }

    }
}
