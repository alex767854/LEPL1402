package parallelization; // ATTENTION POUR INGINIOUS FAUT METTRE package exam;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

/**
 * You are the organizer of a bicycle race in the Ardennes forest.
 * You must compute the average speed of each racing cyclist.
 * However, this race is highly successful, so many racing cyclists will be taking part.
 * As a consequence, you must use multithreading to speed up the computations.
 */
public class BicycleRace {

    /**
     * This class encodes one checkpoint for a racer. A checkpoint is recorded
     * periodically by the bike as a GPS coordinate (x,y) expressed in kilometers,
     * together with a timestamp expressed in seconds.
     */
    public static class Checkpoint {
        private float x;
        private float y;
        private int time;

        public Checkpoint(float x,
                          float y,
                          int time) {
            this.x = x;
            this.y = y;
            this.time = time;
        }

        /**
         * Return the x dimension of the GPS coordinate, expressed in kilometers.
         */
        public float getX() {
            return x;
        }

        /**
         * Return the y dimension of the GPS coordinate, expressed in kilometers.
         */
        public float getY() {
            return y;
        }

        /**
         * Return the timestamp at which the checkpoint was recorded, expressed in seconds.
         * Note that the timestamp associated with a racer's first checkpoint is *not* guaranteed
         * to be zero, as racers will start in multiple waves.
         */
        public int getTime() {
            return time;
        }
    }

    /**
     * This class stores the path travelled by each racing cyclist.
     */
    public interface Cyclists {
        /**
         * Get the number of cyclists who take part in the race.
         */
        public int size();

        /**
         * Get the path associated with the cyclist whose index is provided in argument.
         * A path is an array of successive checkpoints, with increasing timestamps.
         */
        public Checkpoint[] getPath(int index);
    }

    /**
     * Compute the length of a path, expressed in kilometers. The length of a path is defined as
     * the sum of the Euclidean distances between two successive checkpoints. If there
     * is zero or one checkpoint in the path, the returned length must be zero, by convention.
     *
     * HINT 1: Given two points (x1,y1) and (x2,y2), their Euclidean distance is
     * given by the square root of "(x1-x2)^2 + (y1-y2)^2".
     * HINT 2: In Java, "Math.sqrt(x)" returns the square root of "x".
     */
    public static float computeLength(Checkpoint[] path) {
        float sum = 0;
        for(int i =0;i< path.length-1;i++){
            sum += Math.sqrt( (path[i].getX()-path[i+1].getX())*(path[i].getX()-path[i+1].getX())  + (path[i].getY()-path[i+1].getY())*(path[i].getY()-path[i+1].getY()));
        }

        return sum;  // TODO
    }

    /**
     * Compute the duration of a path, expressed in seconds. It is defined as the
     * difference between the last recorded timestamp and the first recorded timestamp. If there
     * is zero or one checkpoint in the path, the returned duration must be zero, by convention.
     */
    public static int computeDuration(Checkpoint[] path) {
        if (path.length == 0 || path.length==1) return 0;
        else {
            return path[path.length-1].getTime()-path[0].getTime();
        }
        // TODO
    }

    /**
     * Compute the average speed of the racer along a path, expressed in kilometers per hour.
     * If the duration of the path is zero second, the returned speed must be zero, by convention.
     * You are invited to use the "computeLength()" and "computeDuration()" methods.
     *
     * HINT 1: Beware of the time units!
     * HINT 2: Beware that "computeDuration()" returns "int" values: You might have to manually
     * convert "int" to "float" during the conversion of the time units to avoid round-off errors.
     */
    public static float computeAverageSpeed(Checkpoint[] path) {
        if (computeDuration(path)==0) return 0;
        else {
            return (computeLength(path)*3600)/ (float) computeDuration(path);
        }
        // TODO
    }

    /**
     * Sequential algorithm to compute the average speed of each racer, given a set of recorded paths.
     * <p>
     * In order to enable further multithreading, this algorithm must consider only a subarray of the
     * provided paths. More precisely, you are given two parameters "startIndex" and "endIndex",
     * and you must only consider the paths from "cyclists.get(startIndex)" to "cyclists.get(endIndex - 1)".
     * In the case where "start == end", there is no path to consider.
     * <p>
     * Hint: You are encouraged to use "computeAverageSpeed()" in this method.
     *
     * @param speeds     Output array where to store the computed speeds. The index in the "speeds"
     *                   array must correspond to the index in the "cyclists" collection.
     * @param cyclists   Input collection of the paths recorded for each racer.
     * @param startIndex Start index of the subarray of paths (inclusive).
     * @param endIndex   End index of the subarray of paths (exclusive).
     */
    public static void computeAverageSpeedSequential(float[] speeds,
                                                     Cyclists cyclists,
                                                     int startIndex,
                                                     int endIndex) {
        if (speeds.length != cyclists.size()) {
            throw new IllegalArgumentException();
        }
        if (startIndex==endIndex || endIndex<startIndex || startIndex<0 || endIndex>cyclists.size())return;

        for (int i =startIndex;i<endIndex;i++){
            synchronized(speeds){
                speeds[i]=computeAverageSpeed(cyclists.getPath(i));
            }

        }
        // TODO
    }



    /**
     * Parallel algorithm to compute the average speed of each racer, given a set of recorded paths.
     * <p>
     * You *must* use two threads to carry on this computation, and you *must* use the provided thread pool.
     * The amount of work must be balanced between the two threads (i.e., the two threads must read
     * roughly the same number of racers). You are encouraged to use "computeAverageSpeedSequential()" in this method.
     * <p>
     * You MUST catch all exceptions related to multithreading. If such an exception is thrown, you must return "null".
     *
     * @param cyclists   Input collection of the paths recorded for each racer.
     * @param threadPool The thread pool to be used. You must *not* call the method "Executors.newFixedThreadPool()"
     *                   to create the thread pool, neither the method "executor.shutdown()" (this is done for you
     *                   in the unit tests).
     * @return An array giving for each input recorded path, the average speed along this path.
     * The indices in the output array must correspond to the indices in the input "cyclists" collection.
     */
    public static float[] computeAverageSpeedParallel(Cyclists cyclists,
                                                      ExecutorService threadPool) {
        float[] result = new float[cyclists.size()];
        int half = cyclists.size() / 2;

        try {
            Future<?> f1 = threadPool.submit(() -> computeAverageSpeedSequential(result, cyclists, 0, half));
            Future<?> f2 = threadPool.submit(() -> computeAverageSpeedSequential(result, cyclists, half, cyclists.size()));

            // Wait for both threads to finish
            f1.get();
            f2.get();
        } catch (InterruptedException | ExecutionException e) {
            return null;
        }

        return result;
    }

}
