package oop;

/**
 * A class that represents a game of Pentago.
 *
 * The rules of the game are described in this short video
 * that we advise you to watch now.
 *   [FR] https://youtu.be/bpkpMxqDmjw
 *   [EN] https://youtu.be/VX6-n1Wm5zI?si=QSLC2auuy7Q1DXFZ
 *
 * Pentago is a two-player game on a 6x6 grid board composed of four
 * 3x3 subparts that can be rotated left or right. The board looks
 * like this:
 *
 * ------------------------------
 * |  x   x   x  |  x   x   x   |
 * |  x   x   x  |  x   x   x   |
 * |  x   x   x  |  x   x   x   |
 * ------------------------------
 * |  x   x   x  |  x   x   x   |
 * |  x   x   x  |  x   x   x   |
 * |  x   x   x  |  x   x   x   |
 * ------------------------------
 *
 * with the four subparts being:
 *
 * --------------------------------
 * |              |               |
 * |   TOP_LEFT   |   TOP_RIGHT   |
 * |              |               |
 * -------------------------------
 * |              |               |
 * | BOTTOM_LEFT  | BOTTOM_RIGHT  |
 * |              |               |
 * -------------------------------
 *
 * Two players A and B take turns. Each turn is composed of 2 successive actions:
 * 1) the player places one of his token on the board in a free position, then
 * 2) the player rotates by 90° or -90° one subpart of the board of his choice.
 *
 * A player wins when he has 5 consecutive own tokens in a row, a
 * column, or a diagonal.
 *
 * Your task is to model the game, by implementing:
 * 1) the main method "play()" that registers the move of the player, and
 * 2) the helper methods "rotateMatrix()", "checkWinPlayerVector()" and "checkWinPlayer()".
 *
 * The 3 helper methods are supposed to help you build the code for
 * the "play()" method.  Therefore, you should implement the helper
 * methods first. The helper methods are graded separately.
 *
 * We also provide the "isGridFilled()" helper method that can be
 * useful in your "play()" method.
 *
 * Do not modify the enumerations, the method signatures, or the
 * instance variables. Even if this is not required to succeed this
 * quiz, you can possibly add new methods and import Java classes.
 *
 */
public class Pentago {
    /**
     * An enum that represents the two players playing the game.
     */
    public enum Player {
        A,
        B
    }

    /**
     * An enum that represents the different outcomes of the game.
     * A_WINS is used when player A wins, B_WINS is used when player B wins, and
     * NO_WINNER is used when there is no winner yet, but also when
     * the board is full (which means that there is no winner) or when both
     * players win at the same time.
     */
    public enum Winner {
        A_WINS,
        B_WINS,
        NO_WINNER
    }

    /**
     * An enum that represents the four subparts of the board:
     * --------------------------------
     * |              |               |
     * |   TOP_LEFT   |   TOP_RIGHT   |
     * |              |               |
     * --------------------------------
     * |              |               |
     * | BOTTOM_LEFT  | BOTTOM_RIGHT  |
     * |              |               |
     * --------------------------------
     */
    public enum BoardSubpart {
        TOP_LEFT,
        TOP_RIGHT,
        BOTTOM_LEFT,
        BOTTOM_RIGHT
    }

    /**
     * An enum that represents the direction of the rotation applied on a subpart.
     */
    public enum RotationDirection {
        LEFT,
        RIGHT
    }

    // the top-left cell of the board has coordinates [0][0]
    // the top-right cell of the board has coordinates [0][5]
    // the bottom-left cell of the board has coordinates [5][0]
    // the bottom-right cell of the board has coordinates [5][5]
    Player[][] board;



    private static final int BOARD_SIZE = 6;
    private static final int SUBPART_SIZE = 3;

    public Pentago() {
        this.board = new Player[BOARD_SIZE][BOARD_SIZE];
    }

    /**
     * A helper method that verifies whether the grid is completely filled by player tokens, or not.
     *
     * @return true if the board is full and false otherwise
     */
    private boolean isGridFilled() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (this.board[i][j] == null) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Implementation of the two steps move and computation of the winner:
     * 1) Plays a piece in the given position for the given player, then
     * 2) Rotates the given subpart in the given direction.
     *
     * The method returns the winner (A_WINS / B_WINS) if only one player is in a victory state.
     * Otherwise, NO_WINNER is returned. It means that NO_WINNER is also returned if both players
     * have aligned 5 pieces in a row, column or diagonal.
     *
     * <p>
     * The methods you should have implemented before are "rotateMatrix", "checkWinPlayerVector" and "checkWinPlayer"
     * Those methods should help you build the code for the play method.
     * Notice that rotating a matrix to the left three times is equivalent to rotating it once the right. 💡
     *
     * @param i         the row index
     * @param j         the column index
     * @param player    the player (A or B)
     * @param subpart   the subpart to rotate by +- 90°
     * @param direction the direction to rotate the subpart (LEFT for -90° or RIGHT for +90°)
     * @throws IllegalArgumentException if i or j is not a valid index or if the position is not empty
     * @return Winner   the winning status of the game after the play (cf. above)
     */
    public Winner play(int i, int j, Player player, BoardSubpart subpart, RotationDirection direction) {
        // TODO
        if (i>5 || j>5 || i<0 || j<0 || this.board[i][j]!=null){
            throw new IllegalArgumentException();
        }
        else{
            this.board[i][j] = player;
        }
        int d = 0;
        if (direction == RotationDirection.RIGHT){
            d = 1;
        }
        else {
            d=3;
        }
        int[] s;
        if(subpart==BoardSubpart.TOP_LEFT){
            s = new int[]{0,0};
        }
        else if (subpart == BoardSubpart.TOP_RIGHT){
            s = new int[]{0,3};
        }
        else if (subpart == BoardSubpart.BOTTOM_LEFT){
            s = new int[]{3,0};
        }
        else{
            s = new int[]{3,3};
        }
        Player[][] matrix = new Player[3][3];
        for (int k = 0;k<3;k++){
            for (int l = 0;l<3;l++){
                matrix[k][l]=this.board[k+s[0]][l+s[1]];
            }
        }
        Player[][] rmatrix =  rotateMatrix(matrix);
        if (d==3){
            rmatrix = rotateMatrix(rmatrix);
            rmatrix = rotateMatrix(rmatrix);
        }
        for (int k = 0;k<3;k++){
            for (int l = 0;l<3;l++){
                this.board[k+s[0]][l+s[1]]= rmatrix[k][l];
            }
        }
        if (checkWinPlayer(Player.A)&&checkWinPlayer(Player.B)){
            return Winner.NO_WINNER;
        }
        else if (checkWinPlayer(Player.A)){
            return Winner.A_WINS;
        }
        else if (checkWinPlayer(Player.B)){
            return Winner.B_WINS;
        }
        else{
            return Winner.NO_WINNER;
        }

    }



    /**
     * Rotate the given matrix 90° to the right (i.e., clockwise).
     *
     * Beware that the matrix may have an arbitrary n x m shape (i.e.,
     * do NOT assume that the matrix has the 3 x 3 shape of the
     * subparts of the Pentago game).
     *
     * @param matrix   the n x m matrix to rotate
     * @return A version of the input matrix rotated to the right having thus an m x n shape.
     *         The input matrix is left unchanged.
     */
    public Player[][] rotateMatrix(Player[][] matrix) {
        // TODO
        Player[][] result = new Player[matrix[0].length][matrix.length];
        for(int i = 0;i<matrix.length;i++){
            for(int j = 0 ; j<matrix[0].length;j++){
                result[j][matrix.length - i-1] = matrix[i][j];
            }
        }
        return result;
    }

    /**
     * Returns if five consecutive positions of the given player are present in the array.
     * This  array can represent a row, a column or a diagonal of the board.
     *
     * @param vector    An array.
     * @param player    The player for which we want to find the possible win.
     * @return true if and only if the given player has five consecutive positions in the array.
     */
    public boolean checkWinPlayer(Player[] vector, Player player){
        // TODO
        for (int i =0; i<vector.length-4;i++){
            int sum = 0;
            for(int j = 0; j<5;j++){
                if (vector[i+j]==player){
                    sum++;
                }
                else{
                    break;
                }
            }
            if(sum==5){
                return true;
            }
        }
        return false;
    }

    /**
     * Indicates if the given player has won.
     *
     * @return true if the given player has won and false otherwise.
     */
    private boolean checkWinPlayer(Player player) {
        // TODO
        for (int i = 0;i<this.board.length;i++){
            if (checkWinPlayer(this.board[i],player)){
                return true;
            }
        }
        Player[][] columns = rotateMatrix(this.board);
        for (int i =0;i<columns.length;i++){
            if (checkWinPlayer(columns[i],player)){
                return true;
            }
        }
        Player[] diag1 = new Player[6];
        Player[] diag2 = new Player[6];
        for (int i = 0 ; i< this.board.length;i++){
            diag1[i]=this.board[i][i];
            diag2[i]=this.board[i][this.board.length -1 - i];
        }
        if (checkWinPlayer(diag1,player) || checkWinPlayer(diag2,player)){
            return true;
        }
        int sum1 = 0;
        int sum2 = 0;
        int sum3 = 0;
        int sum4 = 0;
        for (int i = 0; i<5;i++){
            if (this.board[1+i][i]==player){
                sum1++;
            }
            else if (this.board[i][i+1]==player){
                sum2++;
            }
            else if (this.board[i][4-i]==player){
                sum3++;
            }
            else if (this.board[i+1][5-i]==player){
                sum4++;
            }
        }
        if (sum1==5||sum2==5||sum3==5||sum4==5){
            return true;
        }
        return false;
    }
}
