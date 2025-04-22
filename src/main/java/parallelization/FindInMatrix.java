package parallelization;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class FindInMatrix {
    // You are allowed to add methods or class members, but do not change the signature
    // of the existing methods and class members.

    public static class Result {
        private int row;
        private List<Integer> columns;

        Result(int row,
               List<Integer> columns) {
            this.row = row;
            this.columns = columns;
        }

        public int getRow() {
            return row;
        }

        public List<Integer> getColumns() {
            return columns;
        }
    }

    public static class CountValuesCallable implements Callable<List<Integer>>{
        private int[] row;
        private int value;

        CountValuesCallable (int[] row,int value){
            this.row = row;
            this.value = value;
        }

        public List<Integer> call() {
            List<Integer> index = new ArrayList<>();
            for (int i = 0; i< row.length;i++){
                if (row[i]==value) index.add(i);
            }
            return index;
        }
    }

    /**
     * This method finds the row that contains the most number of occurrences of a
     * certain value and returns the row index of that row and the column indexes
     * (in increasing order) where the value occurs in that row.
     * The matrix is represented by a two-dimensional array.
     *
     * Example: if the method is called with the value "3" and the following matrix
     *     (1   3  2  -4)          // <- there is a "3" in column 1
     *     (-3  4  5  -2)
     *     (3   0  3   2)          // <- there is a "3" in column 0 and column 2
     * then the result of the search is:
     *      row=2 , columns=[0,2]
     * because row 2 contains the most number of occurrences of "3" (in columns 0 and 2).
     *
     * Your solution MUST use a thread pool to accelerate the operation.
     *
     * @param matrix a rectangular matrix
     * @param value the value to find
     * @param poolSize the thread pool size
     * @return the result of the search
     *
     * You can assume that there is exactly one row in the matrix with the
     * most number of occurrences of the value.
     * Catch exceptions and ignore them.
     */

    public static Result findValue(int[][] matrix, int value, int poolSize) {
        // TODO
        // Hint:
        // One row of the matrix -> One future.
        ExecutorService executor = Executors.newFixedThreadPool(poolSize);
        List<Future<List<Integer>>> futures = new ArrayList<>();
        for (int i = 0;i< matrix.length;i++){
            futures.add(executor.submit(new CountValuesCallable(matrix[i],value)));
        }
        int cnt = 0;
        int index = 0;
        for (int i = 0; i< futures.size();i++){
            try{
                if (futures.get(i).get().size()>cnt){
                    cnt = futures.get(i).get().size();
                    index = i;
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            }

        }
        Result result;
        try {
            result = new Result(index,futures.get(index).get());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
        executor.shutdown();
        return result;
    }
}