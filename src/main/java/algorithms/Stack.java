package algorithms;

import java.util.Iterator;

/**
 * Complete the implementation of the methods below in the DynamicArrayStack class.
 * It implements a stack abstract data type.
 * Remember: A dynamic array is an array that resizes itself as needed
 *           (double its size when full, halve its size when one-quarter full).
 *
 * - You cannot use any Java collections, only arrays are allowed in your implementation.
 * - Leave the interface unchanged but feel free to modify DynamicArrayStack.
 * - In DynamicArrayStack, you can add fields, methods, and nested classes if you want.
 *
 * Notice that the iterator should iterate over the elements in the stack from top to bottom.
 *
 * The amortized time complexity of the methods push/pop is O(1),
 * although in some cases it can be O(n).
 */
public interface Stack<T> extends Iterable<T> {

    /**
     * @param item the item to be pushed onto this stack
     */
    void push(T item);

    /**
     * @return the item at the top of this stack and removes it
     */
    T pop();

    /**
     * @return the item at the top of this stack without removing it
     */
    T peek();

    /**
     * @return true if this stack is empty
     */
    boolean isEmpty();

    /**
     * @return the number of items in this stack
     */
    public int size();
}

/**
 * Implementation of a stack using a dynamic array.
 * The size of the array is doubled when the stack is full
 * and halved when the stack is one-quarter full.
 */
class DynamicArrayStack<T> implements Stack<T> {

    // TODO: add the missing fields
    T[] mystack ;
    int capacity ;
    int cnt = 0;

    /**
     * Construct the data structure with the given initial capacity.
     *
     * HINT: To create an array of generic type T you should write:
     *   T[] myvar = (T[]) new Object[initialCapacity];
     */
    public DynamicArrayStack(int initialCapacity) {
        // TODO
        mystack = (T[]) new Object[initialCapacity];
        capacity = initialCapacity;
    }

    @Override
    public void push(T item) {
        // TODO

        if (cnt==capacity){
            T[] current = mystack.clone();
            mystack = (T[]) new Object[capacity*2];
            for (int i =0;i< current.length;i++){
                mystack[i]=current[i];
            }
            mystack[current.length]=item;
            capacity = capacity*2;
            cnt++;
        }
        else if (cnt==capacity/4){
            T[] current = mystack.clone();
            mystack = (T[]) new Object[capacity/2];
            for (int i = 0; i<cnt;i++){
                mystack[i]=current[i];
            }
            mystack[cnt]=item;
            capacity = capacity/2;
            cnt++;
        }
        else {
            mystack[cnt]=item;
            cnt++;
        }
    }

    @Override
    public T pop() {
        // TODO
        T current = mystack[cnt-1];
        mystack[cnt-1]=null;
        cnt--;
        return current;
    }

    @Override
    public T peek() {
        // TODO
        return mystack[cnt-1];
    }

    @Override
    public boolean isEmpty() {
        if (cnt==0)return true;
        return false;
    }

    @Override
    public int size() {
        return cnt;
    }


    // Don't forget that the iterator should iterate
    // over the elements in the stack from top to bottom.
    //
    // You can assume that there will be no modification
    // of the stack while it is iterated over.
    @Override
    public java.util.Iterator<T> iterator() {
        // TODO
        return new Iterator<T>() {
            int index = cnt-1;
            @Override
            public boolean hasNext() {
                return index>=0;
            }

            @Override
            public T next() {
                return mystack[index--];
            }
        };
    }
}
