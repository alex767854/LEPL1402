package algorithms;

public class KnapsackBruteForce {

    public static void main(String[] args) {
        Item[] items = {
                new Item(60, 10),
                new Item(100, 20),
                new Item(120, 30)
        };
        int capacity = 50;

        int maxValue = knapsack(items, capacity);
        System.out.println("Maximum value: " + maxValue);
    }

    static class Item {
        int value;
        int weight;

        Item(int value, int weight) {
            this.value = value;
            this.weight = weight;
        }
    }

    /**
     * Returns the maximum value that can be put in a knapsack with the given capacity.
     * Each item can only be selected once. If you pack an item it consumes its weight in the capacity
     * Your algorithm should implement a brute-force appraoch with a time comlexity
     * of O(2^n) where n is the number of items.
     * @param items
     * @param capacity
     * @return
     */
    public static int knapsack(Item[] items, int capacity) {
        return isSubsetSum(items, 0,0,0,capacity);

    }


    private static int isSubsetSum(Item[] arr, int i, int sum,int sumv, int capacity) {
        // Base cases
        if (i == arr.length) { // did not find it
            return sum;
        }
        int exclude = isSubsetSum(arr,i+1,sum,sumv,capacity);
        int include = 0;
        if ( sumv + arr[i].weight <= capacity) { // found it
            include = isSubsetSum(arr,i+1,sum+arr[i].value,sumv+arr[i].weight,capacity);
        }

        // Check if sum can be obtained by excluding / including the next
        return Math.max(exclude,include);

    }


}
