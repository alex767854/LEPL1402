package algorithms;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;


/**
 * Question:
 *
 * You are asked to compute the median of a list of pair (see the TODO below)
 *
 * Once it is done, copy-paste the complete class in Inginious also with the imports
 *
 *
 * Feel free to add methods or fields in the class but do not modify
 * the signature and behavior of existing code
 *
 */
public class MedianOfPairs {

    /**
     * Return the median (=middle) element
     * @param list, a list containing a odd number of distinct pairs
     * @return the pair of the list such that
     *      list.size/2 elements smaller, and list.size/2 element are greater
     *      than the returned element using the increasing lexicographic ordering
     * Example: for List((4,1),(3,8),(4,3),(9,1),(3,6)), the median is (4,1)
     *
     * Warning: your algorithm should execute in a time complexity strictly better than O(n^2)
     * where n is the size of the list
     *
     */
    public static Pair median(List<Pair> list) {
        // TODO
        for (int i =0; i<list.size()-1;i++){
            for(int j = 0;j<list.size()-i-1;j++){
                if (list.get(j).first==list.get(j+1).first && list.get(j).second>list.get(j+1).second || list.get(j).first>list.get(j+1).first){
                    Collections.swap(list,j,j+1);
                }
            }
        }
        return list.get(list.size()/2);
    }

    public static class Pair {
        int first, second;
        public Pair(int first, int second) {
            this.first = first;
            this.second = second;
        }
    }



}

