package fp;

import java.util.ArrayList;
import java.util.List;
import java.util.Observer;
import java.util.function.Consumer;


/**
 * ObservableCounter is a counter that can be
 * incremented with the increment() method.
 * The method value() returns the number of calls
 * to increment() since the creation of the counter.
 *
 * The counter can be observed with a functional programming
 * interface.
 *
 * Complete and modify the class if necessary
 */
public class ObservableCounter {

    // TODO add instance variables
    public int value=0;
    public List<Consumer<Integer>> observers = new ArrayList<>();

    /**
     * Return the current value of the counter
     *
     * @return the current value of the counter
     */
    public synchronized int value() {
        // TODO
        return value;
    }

    /**
     * Increments the counter and notifies all the registered observers
     *
     * @return the new value of the counter
     */
    public synchronized int increment() {
        // TODO
        value++;
        for(Consumer<Integer> observer : observers)observer.accept(value);
        return value;
    }


    /**
     * Adds an observer that will listen and be notified
     * with the new value when the counter value has changed.
     *
     * @param o the observer that is added to the list of observers
     */
    public synchronized void onChange(Consumer<Integer> o) {
        // TODO
        observers.add(o);
    }

    public static void main(String[] args) {

        // initialize a counter at 0
        ObservableCounter c = new ObservableCounter();

        // augment the counter value
        c.increment();

        // the counter is now at 1
        assert(c.value() == 1);

        // register a code that will prints the value of the counter each time it modified with increment()
        c.onChange(v -> System.out.println("observer:"+v));

        // increment the counter, since one observer listen to the change, he should be notified
        // as a consequence, the message "observer:2" should be printed
        c.increment();

    }


}
