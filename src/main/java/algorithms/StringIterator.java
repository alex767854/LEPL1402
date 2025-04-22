package algorithms;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;


/**
 * Question:
 *
 * You are asked to implement an IterableString allowing
 * to iterate on the successive chars of a given string
 *
 * Once it is done, copy-paste the complete class in Inginious also with the imports
 *
 *
 * Feel free to add methods or fields in the class but do not modify
 * the signature and behavior of existing code
 *
 */
public class StringIterator {

    /**
     * Factory method
     * @param s the string on which to iterate
     * @return a new instance of your implementation of an IterableString allowing to iterate on s
     */
    public static IterableString makeIterableString(String s) {
        // TODO return an instance of your class that implements the interface
        return new IterableString1(s);
    }

    /**
     * An IterableString permit to iterate on each character of a
     * string one by one from left to right
     */
    public interface IterableString extends Iterable<Character> {
    }

    // TODO implement the interface IterableString as an (inner) class

    public static class IterableString1 implements IterableString {
        public String s;

        public IterableString1 (String s){
            this.s = s;
        }

        @Override
        public Iterator<Character> iterator() {
            return new BaseIterator();
        }

        public class BaseIterator implements Iterator<Character> {
            private int index = 0;

            @Override
            public boolean hasNext(){
                return index < s.length();
            }

            @Override
            public Character next(){
                if (!hasNext()){
                    throw new NoSuchElementException();
                }
                return s.charAt(index++);
            }
        }
    }




}
