package basics;

import java.util.Arrays;

/**
 * Make sure that the equal method consider
 * two QR codes as identical if they have been flipped
 * by 0,1,2 or 3 quarter rotations
 *
 * For instance the two following matrices should be
 * considered equals if you flip your head by 180 degrees
 *
 *         boolean [][] t0 = new boolean[][] {
 *                 {false,true,false,false},
 *                 {false,false,true,true},
 *                 {true,false,false,true},
 *                 {true,true,false,true}
 *         };
 *
 *         // t2 is a version of t2 with two quarter rotations
 *         boolean [][] t2 = new boolean[][] {
 *                 {true,false,true,true},
 *                 {true,false,false,true},
 *                 {true,true,false,false},
 *                 {false,false,true,false}
 *         };
 */
public class QRcode {

    protected boolean [][] data;

    /**
     * Create a QR code object
     * @param data is a n x n square matrix
     */
    public QRcode(boolean [][] data) {
        this.data = data;
    }

    /**
     * Return true if the other matrix is identical up to
     * 0, 1, 2 or 3 number of rotations
     * @param o the other matrix to compare to
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass() ){
            return false ;
        }
        QRcode cast = (QRcode) o ;
        boolean[][] temp = cast.data ;
        if (Arrays.deepEquals(this.data, temp)){return true;}
        for (int i =0;i<3;i++){
            if (Arrays.deepEquals(this.data, temp = rotate(temp))){
                return true;
            }
        }
        return false;
    }
    public static boolean[][] rotate(boolean[][] matrix) {
        boolean[][] m = new boolean[matrix.length][matrix.length];
        for (int i = 0 ; i< matrix.length;i++){
            for (int j = 0 ; j< matrix.length;j++){
                m[i][j]= matrix[matrix.length -1 - j][i];
            }
        }
        return m;
    }



}
