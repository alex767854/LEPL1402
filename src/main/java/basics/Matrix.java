package basics;

public class Matrix {

    /**
     * Create a matrix from a String.
     *
     * The string if formatted as follow:
     *  - Each row of the matrix is separated by a newline
     *    character \n
     *  - Each element of the rows are separated by a space
     *
     *  @param s The input String
     *  @return The matrix represented by the String
     */
    public static int[][] buildFrom(String s) {

        String[] lines =  s.split("\n");
        int[][] matrix = new int[lines.length][];
        for (int i = 0; i<lines.length;i++) {
            String[] linestring =  lines[i].split(" ");
            int[] line = new int[linestring.length];
            for (int j = 0; j<linestring.length;j++) {
                line[j] = Integer.parseInt(linestring[j]);
            }
            matrix[i] = line;
        }
        return matrix ;
    }


    /**
     * Compute the sum of the element in a matrix
     *
     * @param matrix The input matrix
     * @return The sum of the element in matrix
     */
    public static int sum(int[][] matrix) {
        int somme = 0 ;
        for (int i=0;i<matrix.length;i++) {
            for (int j = 0 ; j< matrix[i].length;j++) {
                somme += matrix[i][j];
            }
        }
        return somme ;
    }

    /**
     * Compute the transpose of a matrix
     *
     * @param matrix The input matrix
     * @return A new matrix that is the transpose of matrix
     */
    public static int[][] transpose(int[][] matrix) {
        int[][] matrix1 = new int[matrix[0].length][matrix.length];
        for (int i=0;i<matrix.length;i++) {
            for (int j = 0 ; j< matrix[i].length;j++) {
                matrix1[j][i]= matrix[i][j];
            }
        }
        return matrix1 ;
    }

    /**
     * Compute the product of two matrix
     *
     * @param matrix1 A n x m matrix
     * @param matrix2 A m x k matrix
     * @return The n x k matrix product of matrix1 and matrix2
     */
    public static int[][] product(int[][] matrix1, int[][] matrix2) {
        int[][] matrix3 = new int[matrix1.length][matrix2[0].length] ;
        for (int i = 0 ; i<matrix3.length ; i++) {
            for (int j = 0 ; j<matrix3[0].length ; j++) {
                int somme = 0 ;
                for (int k = 0 ; k<matrix2.length ; k++) {
                    somme += matrix1[i][k]*matrix2[k][j] ;
                }
                matrix3[i][j] = somme ;

            }
        }
        return matrix3 ;
    }
}