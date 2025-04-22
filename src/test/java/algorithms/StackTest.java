package algorithms;

import org.javagrader.Grade;
import org.javagrader.GradeFeedback;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.EmptyStackException;
import java.util.Iterator;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

@Grade
public class StackTest {

    @Test
    @Grade(value = 1, cpuTimeout = 1000)
    public void testPushAndPop() {
        Stack<Integer> stack = new DynamicArrayStack<>(2);
        stack.push(5);
        assertEquals(1, stack.size());
        stack.push(7);
        assertEquals(2, stack.size());

        assertEquals(7, stack.pop());
        assertEquals(1, stack.size());
        assertEquals(5, stack.pop());
        assertTrue(stack.isEmpty());
    }

    @Test
    @Grade(value = 1, cpuTimeout = 1000)
    public void testResizeOnPush() {
        Stack<Integer> stack = new DynamicArrayStack<>(2);
        stack.push(1);
        stack.push(5);
        stack.push(9); // triggers resize
        assertEquals(3, stack.size());

        assertEquals(9, stack.pop());
        assertEquals(5, stack.pop());
        assertEquals(1, stack.pop());
        assertTrue(stack.isEmpty());
    }


    @Test
    @Grade(value = 1, cpuTimeout = 1000)
    public void testPeek() {
        Stack<String> stack = new DynamicArrayStack<>(2);
        stack.push("A");
        stack.push("B");
        stack.push("C");

        assertEquals("C", stack.peek());
        assertEquals(3, stack.size());
        assertEquals("C", stack.peek());
        assertEquals(3, stack.size());
        assertEquals("C", stack.pop());
        assertEquals("B", stack.peek());
        assertEquals("B", stack.peek());
        assertEquals(2, stack.size());
    }

    @Test
    @Grade(value = 1, cpuTimeout = 1000)
    public void testIsEmpty() {
        Stack<Double> stack = new DynamicArrayStack<>(2);
        assertTrue(stack.isEmpty());

        stack.push(3.14);
        assertFalse(stack.isEmpty());

        stack.pop();
        assertTrue(stack.isEmpty());
    }

    @Test
    @Grade(value = 1, cpuTimeout = 1000)
    public void testResizeOnPop() {
        Stack<Integer> stack = new DynamicArrayStack<>(4);
        stack.push(1);
        stack.push(2);
        stack.push(3);
        stack.push(4);
        stack.push(5);
        stack.push(6);
        stack.push(7);
        stack.push(8);

        stack.pop();
        stack.pop();
        stack.pop();
        stack.pop();
        stack.pop();
        stack.pop();
        assertEquals(2, stack.size());

        assertEquals(2, stack.pop());
        assertEquals(1, stack.pop());

        assertTrue(stack.isEmpty());
    }

    @Test
    @Grade(value = 1, cpuTimeout = 1000)
    public void testIterator() {
        Stack<Integer> stack = new DynamicArrayStack<>(3);
        stack.push(1);
        stack.push(2);
        stack.push(3);
        stack.push(5);
        stack.push(7);

        int[] expected = {7, 5, 3, 2, 1};
        int index = 0;
        for (int value : stack) {
            assertEquals(expected[index++], value);
        }
        assertEquals(5, index); // ensure all elements were iterated
    }

    @Test
    @Grade(value = 1, cpuTimeout = 1000)
    public void testTwoIterators() {
        Stack<Integer> stack = new DynamicArrayStack<>(3);
        stack.push(1);
        stack.push(2);
        stack.push(3);
        stack.push(5);
        stack.push(7);

        int[] expected = {7, 5, 3, 2, 1};
        int index = 0;
        Iterator<Integer> it1 = stack.iterator();
        Iterator<Integer> it2 = stack.iterator();
        while (it1.hasNext() && it2.hasNext()) {
            assertEquals(expected[index], it1.next());
            assertEquals(expected[index], it2.next());
            index++;
        }
        assertEquals(!it1.hasNext(), !it2.hasNext());
        assertEquals(5, index); // ensure all elements were iterated
    }

    @Test
    @Grade(value = 1, cpuTimeout = 1, threadMode = Timeout.ThreadMode.SEPARATE_THREAD)
    @GradeFeedback(message = "It seems you don't have an amortized complexity of O(1) for push/pop operations. Hint: do you do a resize operation too often?")
    public void testComplexityPushPop() {
        int n = 10000;
        Stack<Integer> stack = new DynamicArrayStack<>(10);
        for (int iter = 0; iter < 100; iter++) {
            for (int i = 0; i < n; i++) {
                stack.push(i);
                stack.peek();
            }
            assertEquals(n, stack.size());
            for (int i = 0; i < n; i++) {
                stack.pop();
            }
            assertEquals(0, stack.size());
        }

        stack.push(1);
        stack.push(2);
        stack.push(3);
        stack.push(5);
        stack.push(7);

        int[] expected = {7, 5, 3, 2, 1};
        int index = 0;
        for (int value : stack) {
            assertEquals(expected[index++], value);
        }
        assertEquals(5, index); // ensure all elements were iterated
    }

    @Test
    @Grade(value = 1, cpuTimeout = 1, threadMode = Timeout.ThreadMode.SEPARATE_THREAD)
    @GradeFeedback(message = "Sorry, something is wrong with your algorithm. Hint: debug on a small example or try to reduce the complexity of your algorithm")
    public void testComplexityIterator() {
        int n = 10000;
        Stack<Integer> stack = new DynamicArrayStack<>(10);
        for (int i = 0; i < n; i++) {
            stack.push(i);
        }
        Iterator it1 = stack.iterator();
        Iterator it2 = stack.iterator();
        int value = n-1;
        while (it1.hasNext() && it2.hasNext()) {
            assertEquals(value, it1.next());
            assertEquals(value, it2.next());
            value--;
        }
        assertEquals(!it1.hasNext(), !it2.hasNext());
    }


}
