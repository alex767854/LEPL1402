package basics;

import java.util.HashSet;

public class MagicSquare {


    /**
     * A magic square is an (n x n) matrix such that:
     *
     * - all the positive numbers 1,2, ..., n*n are present (thus each number appears exactly once)
     * - the sums of the numbers in each row, each column and both main diagonals are the same
     *
     *   For instance a 3 x 3 magic square is the following
     *
     *   2 7 6
     *   9 5 1
     *   4 3 8
     *
     *   You have to implement the method that verifies if a matrix is a valid magic square
     */

    /**
     *
     * @param matrix a square matrix of size n x n
     * @return true if matrix is a n x n magic square, false otherwise
     */
    public static boolean isMagicSquare(int [][] matrix) {
        // TODO Implement the body of this method, feel free to add other methods but do not change the signature of isMagiSquare
        if (matrix.length == 0 || matrix.length != matrix[0].length){
            return false;
        }
        int esum = 0;
        for (int i = 0 ; i<matrix.length;i++){
            esum += matrix[i][i];
        }
        if (!everychar(matrix)){
            return false;
        }
        if (!columnline(matrix,esum)){
            return false;
        }
        if (!diag(matrix,esum)){
            return false;
        }
        return true;
    }

    public static boolean everychar(int [][] matrix){
        HashSet<Integer> alr = new HashSet<>();
        int max = matrix.length * matrix.length;
        for (int i = 0; i<matrix.length;i++){
            for (int j = 0 ; j< matrix.length; j++){
                int num = matrix[i][j];
                if (num < 1 || num > max || alr.contains(num)){
                    return false;
                }
                alr.add(num);
            }

        }
        return true;
    }

    public static boolean columnline(int [][] matrix, int esum){
        int sumc = 0;
        int suml = 0;
        for (int i = 0; i<matrix.length;i++){
            sumc = 0;
            suml = 0;
            for (int j = 0; j<matrix.length;j++){
                sumc += matrix[j][i];
                suml += matrix[i][j];
            }
            if (sumc == esum && sumc == suml){
                continue;
            }
            else {
                return false;
            }
        }
        return true;
    }
    public static boolean diag(int [][] matrix, int esum){
        int sumd1 = 0;
        int sumd2 = 0;
        for (int i = 0 ; i<matrix.length;i++){
            sumd1 += matrix[i][i];
            sumd2 += matrix[i][matrix.length -1 -i];
        }
        if (sumd1 == sumd2 && sumd1 == esum){
            return true;
        }
        else{
            return false;
        }
    }
}
