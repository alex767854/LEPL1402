package fp;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Various exercises with streams.
 **/
public class VariousStreams {

    /**
     * Count the number of strings in a stream that start with the provided character.
     */
    static public long countStringsWithFirstLetter(Stream<String> stream,
                                                   char firstLetter) {
        // TODO
        return stream.filter(s -> s.charAt(0)==firstLetter)
                .count();
    }


    /**
     * Convert a stream of strings either to uppercase or to
     * lowercase, depending on the value of the "uppercase" argument.
     */
    static public Stream<String> changeCase(Stream<String> stream,
                                            boolean uppercase) {
        // TODO
        if (uppercase)return stream.map(s->s.toUpperCase());
        else return stream.map(s->s.toLowerCase());
    }


    /**
     * Compute the sum of all the even numbers inside the provided
     * stream if "isEven" is "true", or the sum of all the odd numbers
     * inside the provided stream if "isEven" is "false".
     */
    static public int getSumOfEvenOrOddNumbers(Stream<Integer> stream,
                                               boolean isEven) {
        // TODO
        if (isEven) {
            return stream.filter(i->i%2==0)
                    .reduce(0,(a,b)->(a+b));
        }
        else {
            return stream.filter(i->i%2!=0)
                    .reduce(0,(a,b)->(a+b));
        }
    }


    /**
     * Remove the duplicates out of a stream of integers.
     *
     * Hint: Check out the JavaDoc of "Stream<T>", there is one method
     * that is especially well suited!
     */
    static public Stream<Integer> removeDuplicates(Stream<Integer> stream) {
        // TODO
        return stream.distinct();
    }


    /**
     * Sort a stream of strings, either in ascending order (if
     * "isAscending" is "true"), or in descending order (if
     * "isAscending" is "false").
     */
    static public Stream<String> sortAscendingOrDescending(Stream<String> stream,
                                                           boolean isAscending) {
        // TODO
        if (isAscending) return stream.sorted();
        else return stream.sorted(Comparator.reverseOrder());
    }


    /**
     * Compute the average value of a stream of integer numbers. If
     * the stream is empty, return 0.0.
     *
     * Hint: Check out the "average()" method of specialized class
     * DoubleStream, and of the "orElse()" method of OptionalDouble
     * class.
     */
    static public double computeAverage(Stream<Integer> stream) {
        // TODO
        return stream.mapToDouble(x-> (double) x).average().orElse(0.0);
    }


    /**
     * Class that wraps a pair of integers corresponding to a minimum
     * value and to a maximum value.
     */
    static public class MinMaxValue {
        private int minValue;
        private int maxValue;

        public MinMaxValue(int minValue,
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
     * Compute the minimum and the maximum value in a stream of
     * integers.  If the stream is empty, the resulting Optional must
     * have "isPresent()" answer "false".
     *
     * Hint: Use "map()" to create "MinMaxValue", then use "reduce()".
     */
    static public Optional<MinMaxValue> computeMinMaxValue(Stream<Integer> stream) {
        // TODO
        List<Integer> liste = stream.collect(Collectors.toList());
        if (liste.isEmpty())return Optional.empty();
        int minvalue = liste.stream().min(Integer::compareTo).orElseThrow(()->new RuntimeException());
        int maxvalue = liste.stream().max(Integer::compareTo).orElseThrow(()->new RuntimeException());
        return Optional.of(new MinMaxValue(minvalue,maxvalue));
    }


    /**
     * Generate the infinite stream of Fibonacci numbers, starting at 2.
     * This sequence corresponds to: [ 2, 3, 5, 8, 13, 21, 34, 55, 89, 144... ]
     *
     * Hint: Use the "generator()" method of "Stream<T>" with a supplier.
     */
    public static Stream<Integer> generateFibonacci() {
        // TODO
        Stream<Integer> stream = Stream.generate(new Supplier<Integer>() {
            int cnt = 0;
            int before = 3;
            int twobefore = 2;
            @Override
            public Integer get() {

                if (cnt ==0){
                    cnt++;
                    return 2;
                }
                else if (cnt==1){
                    cnt++;
                    return 3;
                }
                else {
                    cnt++;
                    int result = before+twobefore;
                    twobefore = before;
                    before = result;
                    return result;
                }

            }
        });
        return stream;
    }
}
