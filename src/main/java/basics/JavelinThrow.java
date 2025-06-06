package basics;

/*
    Javelin throw = (fr) Lancer du javelot
    
    This is the field of a javelin throw competition seen from above:
   
                ........xxxxxx
                .....xxx......
                ..xxx.........
                xx......#.....    Valid throw
       Thrower  ..............
                xx............
                ..xxx.........
                .....xxx......
                ........xxxxxx
    
    The thrower stands on the left side of the field and throws the javelin
    to the right. The "x" mark the boundaries of the valid landing area.
    The throw is valid only if the javelin (represented by "#") lands inside
    the boundaries. The throw is invalid if the javelin lands outside or on
    the boundaries.

    The next example shows an invalid throw. The javelin landed outside
    the boundaries of the valid area:

                ...#....xxxxxx    Invalid throw
                .....xxx......
                ..xxx.........
                xx............
       Thrower  ..............
                xx............
                ..xxx.........
                .....xxx......
                ........xxxxxx

    The following example also shows an invalid javelin throw. The javelin
    landed on one of the boundary markers:

                ........xxxxxx
                .....x#x......    Invalid throw
                ..xxx.........
                xx............
       Thrower  ..............
                xx............
                ..xxx.........
                .....xxx......
                ........xxxxxx

    The rule is simple: if you look at the vertical line where the javelin landed,
    the throw is valid if the javelin is between two boundary markers.
 */


// You can add new fields, methods and imports to this code.
// However, you must not modify the types and parameters of the existing
// methods.

public class JavelinThrow {

    public static class JavelinPosition {
        int x;
        int y;

        JavelinPosition(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    /**
     * This method finds the javelin in the field.
     * It returns an object of the class JavelinPosition where x and y
     * mean that the javelin is in the element field[y][x] of the field.
     * Here, x indicates the position on the horizontal axis and y indicates
     * the position on the vertical axis. In the below picture, the
     * javelin is at position x=3, y=0, i.e., in element field[0][3]:
     *
     *              ...#.............
     *              .................
     *    Thrower   ....the field....
     *              .................
     *              .................
     *
     * The field is always rectangular.
     * The javelin is indicated by a "#" character. The boundaries
     * are indicated by "x" characters. Look at the examples in the test.
     * There is always exactly one javelin on the field.
     *
     * @param field The field. It's never null.
     * @return the position of the javelin in the field
     */
    public static JavelinPosition findJavelin(char[][] field) {
        for (int i = 0; i<field.length;i++){
            for (int j =0; j<field[0].length;j++){
                if (field[i][j] == '#'){
                    return new JavelinPosition(j,i);
                }
            }
        }
        return null;
    }

    /**
     * This method takes a two-dimensional array representing the javelin throw
     * field and returns true if the javelin throw was valid. Otherwise, it
     * returns false.
     *
     * The field boundaries are indicated by "x" characters. The position of the
     * landed javelin is indicated by "#". All other characters can be ignored.
     *
     * The field is always rectangular and has two boundary lines, similar to
     * those shown in the above example. Look at the examples in the tests.
     * There is always exactly one javelin on the field.
     *
     * @param field The field. It's never null.
     * @return true if throw is valid, false if the throw is invalid.
     */
    public static boolean isThrowValid(char[][] field) {
        int x = findJavelin(field).x ;
        int y = findJavelin(field).y ;
        int sum1 = 0;
        for (int i =0;i<y;i++){
            if (field[i][x]=='x'){
                sum1++;
            }
        }
        int sum2 = 0;
        for (int i = y+1;i<field.length;i++){
            if (field[i][x]=='x'){
                sum2++;
            }
        }
        if (sum1==1 && sum2 ==1 || sum1==0 && sum2==0 ){
            return true;
        }
        else {
            return false;
        }
    }
}
