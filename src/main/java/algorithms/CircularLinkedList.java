package algorithms;
import javax.swing.text.html.Option;
import java.util.Optional;

/**
 * In this exercise, you will implement some methods for a circular linked-list.
 * A circular linked-list is a linked-list for which the last element has a successor: the
 * first element.
 * For example, if the list is 4 -> 5 -> 2, then 4 is the first element, and 2 points towards 4.
 *                             ^         |
 *                             |         |
 *                             -----------
 *
 * We ask you to implement two methods; enqueue and remove which, respectively, add an element at the end of the queue, and
 * removes an element at a given index. The time complexity of each method is note in their specifications.
 */
public class CircularLinkedList {

    public static class Node {
        public int value;
        public Optional<Node> next;

        public Node(int value) {
            this.value = value;
            this.next = Optional.empty();
        }

        public void setNext(Node next) {
            this.next = Optional.of(next);
        }

        public boolean hasNext() {
            return this.next.isPresent();
        }
    }

    public Optional<Node> first;
    public Optional<Node> last;
    public int size;

    public CircularLinkedList() {
        this.first = Optional.empty();
        this.last = Optional.empty();
        this.size = 0;
    }

    public boolean isEmpty() {
        return this.size == 0;
    }

    public Optional<Node> getFirst() {
        return this.first;
    }

    public Optional<Node> getLast() {
        return this.last;
    }

    public void enqueue(int value) {
        Node node1 = new Node(value);
        if (isEmpty()){
            this.first = Optional.of( node1);
            this.last = Optional.of(node1);
            node1.setNext(node1);
            this.size ++;
        }
        else {
            getLast().get().setNext(node1);
            this.last = Optional.of(node1);
            getLast().get().setNext(this.first.get());
            this.size++;
        }
    }

    public int remove(int index) {
        if (index == 0 && this.size == 1){
            int value = getFirst().get().value;
            this.first = Optional.empty();
            this.last = Optional.empty();
            this.size = 0;
            return value;
        }
        else if (index==0){
            int value = getFirst().get().value;
            Node node0 = getFirst().get().next.get();
            getLast().get().setNext(node0);
            this.first = Optional.of(node0);
            this.size--;
            return value;
        }
        else {
            Node current = getLast().get();
            for (int i = 0; i<index; i++){
                current = current.next.get();
            }
            int value = current.next.get().value;
            current.setNext(current.next.get().next.get());
            if (index == this.size-1){
                this.last = Optional.of(current);
            }
            this.size--;
            return value;
        }
    }
}
