package basics;


/**
 * A class that represents a game of Four in a Row.
 * The game is played on a 6x7 board.
 * A player wins when he has 4 pieces in a row, column or diagonal.
 *
 * ForInARow is a two-player connection rack game, in which the players choose a color and
 * then take turns dropping colored tokens into a six-row, seven-column vertically suspended grid.
 * The pieces fall straight down, occupying the lowest available space within the column.
 *
 * The objective of the game is to be the first to form a horizontal,
 * vertical, or diagonal line of four of one's own tokens.
 *
 * Your taks is to model the game and implement the method hasWon(char player) that returns true.
 * if the player has won the game.
 * We advise you to model the state of the game with an internal 2D array of char.
 */
public class FourInARow {
    private static final int ROWS = 6;
    private static final int COLUMNS = 7;

    private static final char EMPTY = '-';
    private static final char[] PLAYERS = {'X', 'O'};

    // add your own instance variables here

    private static char[][] cadre;

    public FourInARow() {
        // add your own code here
        //initialiser le cadre vide
        cadre = new char[ROWS][COLUMNS];
        for (int i = 0; i<ROWS; i++){
            for (int j = 0 ; j<COLUMNS;j++){
                cadre[i][j] = EMPTY;
            }
        }

    }

    /**
     * Play a piece in column j for the given player.
     * @param j the column index
     * @param player the player (X or O)
     * @throws IllegalArgumentException if j is not a valid column index or if the column is full or if the player is not X or O
     */
    public void play(int j, char player) {
        // add your own code here
        //exceptions
        if (j<0 || j>COLUMNS -1 || player != PLAYERS[0] && player != PLAYERS[1]){
            throw new IllegalArgumentException();
        }
        //faire tomber le jeton
        for (int i =0; i<ROWS;i++){
            if (cadre[ROWS -1 -i][j] == EMPTY){
                cadre[ROWS -1 -i][j] = player ;
                return;
            }
        }
        //si la colonne est pleine
        throw new IllegalArgumentException();
    }


    /**
     * Returns true if the player has won the game.
     * @param player the player (X or O)
     * @return true if the player has won the game
     * @throws IllegalArgumentException if the player is not X or O
     */
    public boolean hasWon(char player) {
        // add your own code here
        if (player != PLAYERS[0] && player != PLAYERS[1]){
            throw new IllegalArgumentException();
        }
        // check lignes
        for (int i = 0;i<ROWS;i++){
            for (int j = 0 ; j < COLUMNS - 3;j++){
                for (int k = 0 ; k<4;k++){
                    if (cadre[i][j+k] !=player){
                        break;
                    }
                    else if (k==3){
                        return true;
                    }
                }

            }
        }
        // check colonnes
        for (int i = 0;i<COLUMNS;i++){
            for (int j = 0 ; j < ROWS - 3;j++){
                for (int k = 0 ; k<4;k++){
                    if (cadre[j+k][i] !=player){
                        break;
                    }
                    else if (k==3){
                        return true;
                    }
                }
            }
        }
        // check diagonales
        for (int i = 0;i<4;i++){
            for (int j = 3 ; j < ROWS;j++){
                for (int k = 0 ; k<4;k++){
                    if (cadre[j-k][i+k] !=player){
                        break;
                    }
                    else if (k==3){
                        return true;
                    }
                }
            }
        }
        for (int i = 0;i<4;i++){
            for (int j = 0 ; j < ROWS-3;j++){
                for (int k = 0 ; k<4;k++){
                    if (cadre[j+k][i+k] !=player){
                        break;
                    }
                    else if (k==3){
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
