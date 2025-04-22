package fp;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.*;

public class LambdaExpressions {
    /**
     * Return a binary operator that computes the sum of two Integer objects.
     */
    public static Object sumOfIntegers() {
        // TODO
        return (BinaryOperator<Integer>) (a,b)->a+b;
    }

    /**
     * Return a predicate that tests whether a String is empty.
     */
    public static Object isEmptyString() {
        // TODO
        return (Predicate<String>) s->s.length()==0;
    }

    /**
     * Return a predicate that tests whether an Integer is an odd number.
     */
    public static Object isOddNumber() {
        // TODO
        return (Predicate<Integer>) a -> a%2!=0;
    }

    /**
     * Return a function that computes the mean of a List of Double objects.
     * If the list is empty, an IllegalArgumentException must be thrown.
     */
    public static Object computeMeanOfListOfDoubles() {
        // TODO
        return (Function<List<Double>,Double>) list -> {
            if (list.isEmpty()) throw new IllegalArgumentException();
            else{
                double sum = 0;
                for (double n : list){
                    sum+=n;
                }
                return sum/ list.size();
            }
        };
    }

    /**
     * Remove the even numbers from a list of Integer objects.
     */
    public static void removeEvenNumbers(List<Integer> lst) {
        // TODO
        lst.removeIf( a->a%2==0);
    }

    /**
     * Return a function that computes the factorial of an Integer.
     * If the number is zero, the factorial equals 1 by convention.
     * If the number is negative, an IllegalArgumentException must be thrown.
     */
    public static Object computeFactorial() {
        // TODO

        return (Function<Integer,Integer>) fact -> {
            if(fact<0)throw new IllegalArgumentException();
            else if (fact==0 || fact==1) return 1;
            else {
                int result = fact;
                int mult = fact-1;
                while (mult!=1){
                    result = result*mult;
                    mult--;
                }
                return result;
            }
        };
    }

    /**
     * Return a function that converts a list of String objects to lower case.
     */
    public static Object listOfStringsToLowerCase() {
        // TODO
        return (Function<List<String>,List<String>>) s -> {
            List<String> result = new ArrayList<>();
            for (String string :s)result.add(string.toLowerCase());
            return result;
        };
    }

    /**
     * Return a function that concatenates two String objects.
     */
    public static Object concatenateStrings() {
        // TODO
        return (BiFunction<String,String,String>) (a,b)->a+b;
    }

    public static class MinMaxResult {
        private int minValue;
        private int maxValue;

        MinMaxResult(int minValue,
                     int maxValue) {
            this.minValue = minValue;
            this.maxValue = maxValue;
        }

        int getMinValue() {
            return minValue;
        }

        int getMaxValue() {
            return maxValue;
        }
    }

    /**
     * Return a function that computes the minimum and maximum values in a list.
     * The content of the Optional must be present if and only if the list is non-empty.
     */
    public static Function<List<Integer>, Optional<MinMaxResult>> computeMinMax() {
        // TODO
        return (Function<List<Integer>, Optional<MinMaxResult>>) list->{
            if (list==null||list.isEmpty())return Optional.empty();
            int minvalue = list.stream().min(Integer::compareTo).orElseThrow(()->new IllegalArgumentException());
            int maxvalue = list.stream().max(Integer::compareTo).orElseThrow(()->new IllegalArgumentException());
            return Optional.of(new MinMaxResult(minvalue,maxvalue));
        };
    }

    /**
     * Return a function that, given a String object and a character, counts
     * the number of occurrences of the character inside the string.
     */
    public static Object countInstancesOfLetter() {
        // TODO
        return (BiFunction<String,Character,Integer>)(s,chara)->{
            int cnt =0;
            for (int i =0;i<s.length();i++){
                if(s.charAt(i)==chara)cnt++;
            }
            return cnt;
        };
    }
}
