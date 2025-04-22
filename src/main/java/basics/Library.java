package basics;

/**
 * You must implement a class that represents a library (fr. bibliothèque).
 * The library is made of several bookshelves (fr. étagères).
 * Each bookshelf contains a number of places. Each place can contain one book.
 * You must implement a method to add a book over a bookshelf, and a method
 * to find the bookshelf containing a book with a given title.
 *
 * In this question, complexity is *not* important. This question can be
 * solved without introducing any additional member variable.
 *
 * Still, if you really want to, you can add new variables, methods, and imports,
 * but you must not modify the types of the existing variables and methods.
 */

public class Library {
    /**
     * Class that represents a book.
     */
    public static class Book {
        String title;
        /**
         * Construct a book with the given title.
         */
        public Book(String title) {
            // TODO
            this.title = title;
        }

        /**
         * Returns the title of the book.
         */
        public String getTitle() {
            // TODO
            return title;
        }
    }

    /**
     * "shelves" is the two-dimensional array that must store the library.
     * The first dimension of this array corresponds to the bookshelves (fr. étagères).
     * The second dimension corresponds to the places on the bookshelf.
     *
     * If bookshelf "s" contains a book in its place "j", then "shelves[s][j]" contains
     * a "Book" object. If place "j" of bookshelf "s" does not contain a book, the
     * member variable "shelves[i][j]" must contain "null".
     */
    private Book[][] shelves;

    /**
     * Construct a library with "numS" bookshelves (fr. étagères).
     * Each bookshelf has "numP" places.
     * You can assume that numS>0 and numP>0.
     *
     * @param numS number of bookshelves
     * @param numP number of places per bookshelf
     */
    public Library(int numS, int numP) {
        shelves = new Book[numS][numP];
    }

    /**
     * Add a book "b" to the library. The method must try to put the book
     * in bookshelf "s". You are free to decide the place where the book is put.
     *
     * If bookshelf "s" is full (because it already contains "numP" books), the
     * method must try to put the book in the next bookshelf "s+1".
     * If bookshelf "s+1" is also full, then it must try to put the book in bookshelf "s+2", etc.
     * But the book must never be put in a bookshelf before
     * bookshelf "s", i.e., not in bookshelf "s-1", "s-2", etc.
     * If no bookshelf with space for the book can be found, the
     * method must return false, otherwise it must return true.
     *
     * The method must throw an IllegalArgumentException if "s" is not
     * a valid bookshelf. For a library with "numS" bookshelves, the
     * valid bookshelves are 0 to numS-1.
     *
     * @param b the book to put in the library
     * @param s the bookshelf where the book should be put
     * @return true if book was successfully added to the library, or
     *         false if no space could be found for the book.
     * @throws IllegalArgumentException if s is not a valid bookshelf number.
     */
    boolean addBook(Book b, int s) {
        // TODO

        if (s>=shelves.length || s<0) throw new IllegalArgumentException();
        for (int i =s;i< shelves.length;i++){
            for (int j =0;j<shelves[0].length;j++){
                if (shelves[i][j]== null){
                    shelves[i][j]=b;
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Find the bookshelf where the book with the given title is located.
     * The method must return -1 if there is no book with the given title.
     * You can assume that all books in the library have different titles.
     *
     * HINT: Remember that to compare two strings "s1" and "s2", you *must* use "s1.equals(s2)".
     * Do *not* use "s1 == s2".
     *
     * @param title the title of the book to find
     * @return the index of the bookshelf where the book is located,
     *      or -1 if the book is not in the library.
     */
    int findBook(String title) {
        // TODO
        for (int i=0;i<shelves.length;i++){
            for (int j=0;j<shelves[0].length;j++){
                if(shelves[i][j]!= null && shelves[i][j].getTitle().equals(title)){
                    return i;
                }
            }
        }
        return -1;
    }
}
