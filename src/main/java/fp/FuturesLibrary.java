package fp;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 *  ***********
 *  * English *
 *  ***********
 *
 * You are an employee of a public library who must create a software to search the content of the books of the
 * library. Because the library contains many books, your task is to speed up this search thanks to
 * Java threads.
 *
 *  ************
 *  * Français *
 *  ************
 *
 * Vous êtes un employé d'une bibliothèque (library) publique qui doit créer un logiciel
 * pour faire des recherches dans le contenu des livres de la bibliothèque.
 * Comme la bibliothèque contient de nombreux livres,
 * votre tâche est d'accélérer cette recherche grâce à des threads Java.
 */
public class FuturesLibrary {

    /**
     * Interface that represents one book of the public library.
     * You do not have to implement this interface.
     */
    public interface Book {
        /**
         * Method that returns the author of the book.
         *
         * @return The author.
         */
        public String getAuthor();

        /**
         * Method that returns the number of words in the book.
         *
         * @return The number of words.
         */
        public int getNumberOfWords();

        /**
         * Method that returns one word from the book, using the index of the word.
         *
         * @param index The index of the word, which must be smaller than the value of "getNumberOfWords()".
         * @return The word of interest.
         */
        public String getWord(int index);
    }

    /**
     * Interface that represents the content of the public library.
     * You do not have to implement this interface.
     */
    public interface Library {
        /**
         * Method that returns the number of books stored in the library.
         *
         * @return The number of books.
         */
        public int getNumberOfBooks();

        /**
         * Method that returns one book stored in the library, using the index of the book.
         *
         * @param index The index of the book, which must be smaller than the value of "getNumberOfBooks()".
         * @return The book of interest.
         */
        public Book getBook(int index);
    }

    /**
     * Function that counts the number of books that match a given predicate, among a subset of the books stored in
     * the library. The subset of books is specified as a range of book indices in the library. These indices
     * correspond to the argument given to the "Library.getBook()" method. This function must be
     * sequential (i.e. it must **not** use threads).
     *
     * @param library        The library.
     * @param predicate      The predicate that specifies the criteria that the books must match.
     * @param startBookIndex The index of the first book in the range of interest.
     * @param countBooks     The number of books in the range of interest.
     * @return The number of books matching the predicate.
     * @throws IllegalArgumentException If "startBookIndex" or "countBooks" is negative, or if a book whose index is
     * greater or equal to the number of books in the library would have to be accessed.
     */
    public static int countMatchingBooks(Library library,
                                         Predicate<Book> predicate,
                                         int startBookIndex,
                                         int countBooks) {
        // TODO

        if (startBookIndex<0 || countBooks <0 || startBookIndex+countBooks> library.getNumberOfBooks()) throw new IllegalArgumentException();
        if (countBooks==0) return 0;
        return (int) Stream.iterate(startBookIndex,x->x+1)
                .limit(countBooks)
                .map(library::getBook)
                .filter(predicate)
                .count();
        /*
        Stream<Integer> numbers = Stream.iterate(0,x->x+1);
        numbers = numbers.limit(library.getNumberOfBooks());
        Stream<Book> stream = numbers.map(x->library.getBook(x));
        stream = stream.skip(startBookIndex-1);
        stream = stream.limit(countBooks);
        for (int i =0;i<countBooks;i++){
            Stream<Book> element = stream.limit(1);
            if (element.noneMatch(predicate)) stream= stream.skip(1);
        }
         return (int) stream.count();

         */
    }

    /**
     * Function that counts the number of books that match a given predicate, among all the books stored in the
     * library. This function **must** use threads using the **executor given in argument**. The number of threads
     * to be used is given in the arguments.
     *
     * The individual threads should internally use the sequential method "countMatchingBooks()", and each thread
     * must access a number of books that is similar to the number of books that are accessed by the other threads
     * (i.e. the amount of work must be balanced between the different threads).
     *
     * You MUST catch all exceptions related to multithreading. If such an exception is thrown, you can return 0.
     *
     * @param library      The library.
     * @param predicate    The predicate that specifies the criteria that the books must match.
     * @param executor     The thread pool to be used. You must *not* call the method "Executors.newFixedThreadPool()"
     *                     to create the thread pool, neither the method "executor.shutdown()" (this is done for you
     *                     in the unit tests).
     * @param countThreads The number of threads to be used.
     * @return The number of books matching the predicate.
     */
    public static int countMatchingBooksWithThreads(Library library,
                                                    Predicate<Book> predicate,
                                                    ExecutorService executor,
                                                    int countThreads) {
        if (countThreads == 0) {
            throw new IllegalArgumentException();
        }
        // TODO
        int index = library.getNumberOfBooks()/countThreads;
        int lastindex = library.getNumberOfBooks() -((countThreads)*index);
        List<Future<Integer>> futures = new ArrayList<>();
        for (int i =0;i<countThreads;i++){
            final int current = i;
            final int plus;
            if (i==countThreads-1) plus = lastindex;
            else plus = 0;
            futures.add(executor.submit(new Callable<Integer>() {
                @Override
                public Integer call() throws Exception {
                    return countMatchingBooks(library,predicate,current*index,index+plus);
                }
            }));
        }
        int result =0;
        try{
            for (Future<Integer> future : futures){
                result+=future.get();
            }
        } catch (InterruptedException e) {
            return 0;
        } catch (ExecutionException e) {
            return 0;
        }

        return result;
    }
}
