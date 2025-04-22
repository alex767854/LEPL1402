package basics;

import org.javagrader.Allow;
import org.javagrader.Grade;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

@Grade
public class LibraryTest {
    @Test
    @Grade(value = 1, cpuTimeout = 1)
    public void testTwoBooks() {
        Library library = new Library(10,5);
        library.addBook(new Library.Book("The book 1"), 2);
        library.addBook(new Library.Book("The book 2"), 5);

        assertEquals(2, library.findBook("The book "+Integer.toString(1)));
        assertEquals(5, library.findBook("The book 2"));
    }

    @Test
    @Grade(value = 1, cpuTimeout = 1)
    public void testNonExistingBook() {
        Library library = new Library(10,5);
        library.addBook(new Library.Book("The book 1"), 2);
        library.addBook(new Library.Book("The book 2"), 5);

        assertEquals(-1, library.findBook("The book 3"));
    }

    @Test
    @Grade(value = 1, cpuTimeout = 1)
    public void testFullShelf() {
        Library library = new Library(10,3);
        library.addBook(new Library.Book("The book 1"), 1);
        library.addBook(new Library.Book("The book 2"), 1);
        library.addBook(new Library.Book("The book 3"), 1);
        library.addBook(new Library.Book("The book 4"), 1);
        library.addBook(new Library.Book("The book 5"), 1);
        library.addBook(new Library.Book("The book 6"), 1);
        library.addBook(new Library.Book("The book 7"), 1);

        assertEquals(1, library.findBook("The book 1"));
        assertEquals(1, library.findBook("The book 2"));
        assertEquals(1, library.findBook("The book 3"));
        assertEquals(2, library.findBook("The book 4"));
        assertEquals(2, library.findBook("The book 5"));
        assertEquals(2, library.findBook("The book 6"));
        assertEquals(3, library.findBook("The book 7"));
    }

    @Test
    @Grade(value = 1, cpuTimeout = 1)
    public void testFullLibrary() {
        Library library = new Library(10,3);
        library.addBook(new Library.Book("The book 1"), 9);
        library.addBook(new Library.Book("The book 2"), 9);
        library.addBook(new Library.Book("The book 3"), 9);
        library.addBook(new Library.Book("The book 4"), 9);

        assertEquals(9, library.findBook("The book 1"));
        assertEquals(9, library.findBook("The book 2"));
        assertEquals(9, library.findBook("The book 3"));
        assertEquals(-1, library.findBook("The book 4"));
    }

    @Test
    @Grade(value = 1, cpuTimeout = 1)
    public void testIllegalShelf() {
        Library library = new Library(10,5);
        Library.Book b = new Library.Book("Hello book");

        library.addBook(b, 0);
        library.addBook(b, 9);

        try {
            library.addBook(b, -1);
            fail("Illegal shelf number"); // if you come here, something is wrong
        }
        catch(IllegalArgumentException e) {
            // good
        }
        try {
            library.addBook(b, 10);
            fail("Illegal shelf number"); // if you come here, something is wrong
        }
        catch(IllegalArgumentException e) {
            // good
        }
    }
}
