package algorithms;


/**
 * Question:
 *
 * You are asked to clean a increasing sorted linked List (see the TODO below)
 * Cleaning the linkedList means keeping only one occurrence of each value.
 *
 * For instance cleaning: 3,3,3,4,5,5,6,6,6,7,9,9,9,9,10,10
 * Gives: 3,4,5,6,7,9,10
 *
 * Your algorithm should execute in Theta(n)
 * where n are the number of elements in the original list
 *
 */
public class CleanLinkedList {

    Node first = null;
    Node last = null;

    public void add(int v) {
        // TODO
        if (first ==null && last==null){
            first = new Node(v,null);
            last = first;
        }
        else {
            last.next = new Node(v,null);
            last=last.next;
        }


    }

    public void add(int ... values) {
        for (int v: values) {
            add(v);
        }
    }


    /**
     * Given the increasingly sorted list, it removes the duplicates
     * @return an increasingly sorted list containing the same set
     *         of elements as list but without duplicates.
     */
    public CleanLinkedList clean() {
        // TODO
        CleanLinkedList res = new CleanLinkedList();
        Node current = first;
        res.add(current.v);
        while(current!=last){
            if (current.next.v == current.v){

                current.next = current.next.next;

                if (current.next == null){
                    last = current;
                }
                else if (current.v != current.next.v){
                    current = current.next;
                    res.add(current.v);
                }
            }
            else {
                current = current.next;
                res.add(current.v);
            }
        }
        return res;

    }


    class Node {
        int v;
        Node next;
        Node(int v, Node next) {
            this.v = v;
            this.next = next;
        }
    }


}

