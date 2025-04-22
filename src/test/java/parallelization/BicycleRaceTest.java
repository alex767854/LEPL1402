package parallelization;
import org.javagrader.Allow;
import org.javagrader.Grade;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.concurrent.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Grade
@Allow("java.lang.Thread")
class BicycleRaceTest {
    private static BicycleRace.Checkpoint[] createPath(BicycleRace.Checkpoint p) {
        BicycleRace.Checkpoint[] path = new BicycleRace.Checkpoint[1];
        path[0] = p;
        return path;
    }

    private static BicycleRace.Checkpoint[] createPath(BicycleRace.Checkpoint p1,
                                                       BicycleRace.Checkpoint p2) {
        BicycleRace.Checkpoint[] path = new BicycleRace.Checkpoint[2];
        path[0] = p1;
        path[1] = p2;
        return path;
    }

    private static BicycleRace.Checkpoint[] createPath(BicycleRace.Checkpoint p1,
                                                       BicycleRace.Checkpoint p2,
                                                       BicycleRace.Checkpoint p3) {
        BicycleRace.Checkpoint[] path = new BicycleRace.Checkpoint[3];
        path[0] = p1;
        path[1] = p2;
        path[2] = p3;
        return path;
    }

    private static BicycleRace.Checkpoint[] createSamplePath() {
        return createPath(
                new BicycleRace.Checkpoint(0, 0, 10),
                new BicycleRace.Checkpoint(2, 0, 10 + 5 * 60),
                new BicycleRace.Checkpoint(2, 2, 10 + 10 * 60));
    }

    private static void checkPath(BicycleRace.Checkpoint[] path) {
        for (int i = 1; i < path.length; i++) {
            if (path[i - 1].getTime() >= path[i].getTime()) {
                throw new IllegalArgumentException();
            }
        }
    }

    private static class BasicCyclists implements BicycleRace.Cyclists {
        private List<BicycleRace.Checkpoint[]> paths = new ArrayList<>();

        @Override
        public int size() {
            return paths.size();
        }

        @Override
        public BicycleRace.Checkpoint[] getPath(int index) {
            return paths.get(index);
        }

        public void add(BicycleRace.Checkpoint[] path) {
            checkPath(path);
            paths.add(path);
        }
    }

    static private float EPSILON = 0.0001f;

    @Test
    @Grade(value = 1, cpuTimeout = 1)
    public void testSimple() {
        {
            BicycleRace.Checkpoint[] path = createSamplePath();
            assertEquals(4, BicycleRace.computeLength(path), EPSILON);
            assertEquals(600, BicycleRace.computeDuration(path));
            assertEquals(4 * 6, BicycleRace.computeAverageSpeed(path), EPSILON);  // 4 kilometers in 600 seconds = 10 minutes
        }

        {
            BicycleRace.Checkpoint[] path = createPath(
                    new BicycleRace.Checkpoint(-10, 3, 50),
                    new BicycleRace.Checkpoint(-11, 3, 50 + 60 * 60));  // 1 kilometer along x axis in 1 hour
            assertEquals(1, BicycleRace.computeLength(path), EPSILON);
            assertEquals(3600, BicycleRace.computeDuration(path));
            assertEquals(1, BicycleRace.computeAverageSpeed(path), EPSILON);
        }

        {
            BicycleRace.Checkpoint[] path = createPath(
                    new BicycleRace.Checkpoint(-5, 0.5f, -50),
                    new BicycleRace.Checkpoint(-5, -0.5f, -50 + 60 * 60));  // 1 kilometer along y axis in 1 hour
            assertEquals(1, BicycleRace.computeLength(path), EPSILON);
            assertEquals(3600, BicycleRace.computeDuration(path));
            assertEquals(1, BicycleRace.computeAverageSpeed(path), EPSILON);
        }
    }

    @Test
    @Grade(value = 1, cpuTimeout = 1)
    public void testEdgeCases() {
        {
            BicycleRace.Checkpoint[] empty = new BicycleRace.Checkpoint[0];
            assertEquals(0, BicycleRace.computeLength(empty), EPSILON);
            assertEquals(0, BicycleRace.computeDuration(empty));
            assertEquals(0, BicycleRace.computeAverageSpeed(empty), EPSILON);
        }

        {
            BicycleRace.Checkpoint[] one = createPath(new BicycleRace.Checkpoint(10, 20, 30));
            assertEquals(0, BicycleRace.computeLength(one), EPSILON);
            assertEquals(0, BicycleRace.computeDuration(one));
            assertEquals(0, BicycleRace.computeAverageSpeed(one), EPSILON);
        }
    }

    @Test
    @Grade(value = 1, cpuTimeout = 1)
    public void testSequential() {
        BasicCyclists cyclists = new BasicCyclists();
        for (int i = 0; i < 100; i++) {
            cyclists.add(createPath(
                    new BicycleRace.Checkpoint(0, 0, 10),
                    new BicycleRace.Checkpoint(i, 0, 10 + 60 * 60)));  // 1 hour
        }
        float[] speeds = new float[cyclists.size()];
        BicycleRace.computeAverageSpeedSequential(speeds, cyclists, 0, cyclists.size());
        for (int i = 0; i < cyclists.size(); i++) {
            assertEquals(i, speeds[i], EPSILON);
        }
    }

    @Test
    @Grade(value = 1, cpuTimeout = 3)
    public void testParallel() {
        BasicCyclists cyclists = new BasicCyclists();
        for (int i = 0; i < 100; i++) {
            cyclists.add(createPath(
                    new BicycleRace.Checkpoint(0, 0, 10),
                    new BicycleRace.Checkpoint(0, i, 10 + 60 * 60)));  // 1 hour
        }

        ExecutorService threadPool = Executors.newFixedThreadPool(2 /* numberOfThreads */);
        try {
            float[] speeds = BicycleRace.computeAverageSpeedParallel(cyclists, threadPool);
            assertEquals(speeds.length, cyclists.size());
            for (int i = 0; i < cyclists.size(); i++) {
                assertEquals(i, speeds[i], EPSILON);
            }
        } finally {
            threadPool.shutdown();
        }
    }


    @Test
    @Grade(value = 1, cpuTimeout = 3)
    public void testFewCyclists() {
        {
            BasicCyclists cyclists = new BasicCyclists();
            float[] speeds = new float[0];
            BicycleRace.computeAverageSpeedSequential(speeds, cyclists, 0, cyclists.size());
        }

        {
            BasicCyclists cyclists = new BasicCyclists();
            cyclists.add(createSamplePath());
            float[] speeds = new float[1];
            speeds[0] = -10;
            BicycleRace.computeAverageSpeedSequential(speeds, cyclists, 0, cyclists.size());
            assertEquals(4 * 6, speeds[0], EPSILON);
        }

        ExecutorService threadPool = Executors.newFixedThreadPool(2 /* numberOfThreads */);
        try {
            {
                BasicCyclists cyclists = new BasicCyclists();
                float[] speeds = BicycleRace.computeAverageSpeedParallel(cyclists, threadPool);
                assertEquals(0, speeds.length);
            }

            {
                BasicCyclists cyclists = new BasicCyclists();
                cyclists.add(createSamplePath());
                float[] speeds = BicycleRace.computeAverageSpeedParallel(cyclists, threadPool);
                assertEquals(1, speeds.length);
                assertEquals(4 * 6, speeds[0], EPSILON);
            }
        } finally {
            threadPool.shutdown();
        }
    }

    // COPY-PASTE FROM "StadiumStatisticsTest"
    public static class InstrumentedThreadObject {
        private Set<Long> readerThreads;
        private Set<Long> writerThreads;

        private static Long getCurrentThreadId() {
            return Thread.currentThread().getId();
        }

        public InstrumentedThreadObject() {
            resetThreads();
        }

        public synchronized void logReadAccess() {
            readerThreads.add(getCurrentThreadId());
        }

        public synchronized void logWriteAccess() {
            writerThreads.add(getCurrentThreadId());
        }

        public synchronized void resetThreads() {
            readerThreads = new HashSet<>();
            writerThreads = new HashSet<>();
        }

        public synchronized Set<Long> getReaderThreads() {
            return new HashSet<>(readerThreads);
        }

        public synchronized Set<Long> getWriterThreads() {
            return new HashSet<>(writerThreads);
        }
    }

    private static class InstrumentedPoolThread implements ExecutorService {
        private static class MyThreadFactory implements ThreadFactory {
            private final Set<Long> createdThreadsId = new HashSet<>();

            public Thread newThread(Runnable r) {
                Thread thread = new Thread(r);
                synchronized (this) {
                    createdThreadsId.add(thread.getId());
                }
                return thread;
            }

            public synchronized int getPoolSize() {
                return createdThreadsId.size();
            }

            public synchronized boolean isManagedThread(long threadId) {
                return createdThreadsId.contains(threadId);
            }
        }

        private int countSubmits;
        private long mainThreadId;
        private ExecutorService wrapped;
        private MyThreadFactory factory;

        private static Long getCurrentThreadId() {
            return Thread.currentThread().getId();
        }

        private void checkIsMainThread() {
            if (getCurrentThreadId() != mainThreadId) {
                throw new IllegalStateException("This method can only be called from the main thread");
            }
        }

        public synchronized void resetCountSubmits() {
            countSubmits = 0;
        }

        public synchronized int getCountSubmits() {
            return countSubmits;
        }

        public InstrumentedPoolThread(int nThreads) {
            mainThreadId = getCurrentThreadId();
            factory = new MyThreadFactory();
            wrapped = Executors.newFixedThreadPool(nThreads, factory);
        }

        @Override
        public void shutdown() {
            checkIsMainThread();
            wrapped.shutdown();
        }

        @Override
        public List<Runnable> shutdownNow() {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean isShutdown() {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean isTerminated() {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean awaitTermination(long l, TimeUnit timeUnit) throws InterruptedException {
            throw new UnsupportedOperationException();
        }

        @Override
        public <T> Future<T> submit(Callable<T> callable) {
            checkIsMainThread();
            synchronized (this) {
                countSubmits++;
            }
            return wrapped.submit(callable);
        }

        @Override
        public <T> Future<T> submit(Runnable runnable, T t) {
            throw new UnsupportedOperationException();
        }

        @Override
        public Future<?> submit(Runnable runnable) {
            checkIsMainThread();
            synchronized (this) {
                countSubmits++;
            }
            return wrapped.submit(runnable);
        }

        @Override
        public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> collection) throws InterruptedException {
            throw new UnsupportedOperationException();
        }

        @Override
        public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> collection, long l, TimeUnit timeUnit) throws InterruptedException {
            throw new UnsupportedOperationException();
        }

        @Override
        public <T> T invokeAny(Collection<? extends Callable<T>> collection) throws InterruptedException,
                ExecutionException {
            throw new UnsupportedOperationException();
        }

        @Override
        public <T> T invokeAny(Collection<? extends Callable<T>> collection, long l, TimeUnit timeUnit) throws InterruptedException, ExecutionException, TimeoutException {
            throw new UnsupportedOperationException();
        }

        @Override
        public void execute(Runnable runnable) {
            throw new UnsupportedOperationException();
        }

        private static void addAccessesPerThread(Map<Long, Long> accesses,
                                                 Set<Long> threadIds) {
            for (Long threadId : threadIds) {
                if (accesses.containsKey(threadId)) {
                    accesses.put(threadId, accesses.get(threadId) + 1);
                } else {
                    accesses.put(threadId, 1L);
                }
            }
        }

        private static Map<Long, Long> countReadAccessesPerThread(Collection<InstrumentedThreadObject> objects) {
            Map<Long, Long> accesses = new HashMap<>();
            for (InstrumentedThreadObject o : objects) {
                addAccessesPerThread(accesses, o.getReaderThreads());
            }
            return accesses;
        }

        private static Map<Long, Long> countWriteAccessesPerThread(Collection<InstrumentedThreadObject> objects) {
            Map<Long, Long> accesses = new HashMap<>();
            for (InstrumentedThreadObject o : objects) {
                addAccessesPerThread(accesses, o.getWriterThreads());
            }
            return accesses;
        }

        public void checkOnlyAccessedFromThreadPool(Collection<InstrumentedThreadObject> objects) {
            checkIsMainThread();
            for (InstrumentedThreadObject o : objects) {
                boolean ok = true;
                for (Long threadId : o.getReaderThreads()) {
                    if (!factory.isManagedThread(threadId)) {
                        ok = false;
                    }
                }
                for (Long threadId : o.getWriterThreads()) {
                    if (!factory.isManagedThread(threadId)) {
                        ok = false;
                    }
                }
                if (!ok) {
                    throw new IllegalStateException("You are accessing an object from a thread that is not part of the provided thread pool");
                }
            }
        }

        public void checkAccessedByOneSingleThread(Collection<InstrumentedThreadObject> objects) {
            checkIsMainThread();
            for (InstrumentedThreadObject o : objects) {
                Set<Long> threadIs = new HashSet<>();
                threadIs.addAll(o.getReaderThreads());
                threadIs.addAll(o.getWriterThreads());
                if (threadIs.size() == 0) {
                    throw new IllegalStateException("One of the provided objects has never been accessed");
                } else if (threadIs.size() > 1) {
                    throw new IllegalStateException("One of the provided objects has been access by more than 1 thread");
                }
            }
        }

        private static long integerCeiling(long a,
                                           long b) {
            if (a % b == 0) {
                return a / b;
            } else {
                return a / b + 1;
            }
        }

        private void checkBalancedAccesses(Collection<InstrumentedThreadObject> objects,
                                           int numberOfThreads,
                                           Map<Long, Long> accesses,
                                           String typeOfThreads) {
            if (objects.size() > numberOfThreads &&
                    getCountSubmits() < numberOfThreads) {
                throw new IllegalStateException("You have not created enough " + typeOfThreads + " threads");
            }

            long totalAccesses = 0;
            for (Long threadId : accesses.keySet()) {
                totalAccesses += accesses.get(threadId);
            }
            if (totalAccesses < objects.size()) {
                throw new IllegalStateException("Your " + typeOfThreads + " threads have not access all the objects in the collection");
            }
            if (totalAccesses > objects.size()) {
                throw new IllegalStateException("Your " + typeOfThreads + " threads have access all the same objects in the collection multiple times");
            }

            long minimumAccesses = objects.size() / numberOfThreads;
            long maximumAccesses = integerCeiling(objects.size(), numberOfThreads);
            for (Long threadId : accesses.keySet()) {
                long v = accesses.get(threadId);
                if (v < minimumAccesses ||
                        v > maximumAccesses) {
                    throw new IllegalStateException("Your accesses are not balanced between the " + typeOfThreads + " threads");
                }
            }
        }

        public void checkBalancedReads(Collection<InstrumentedThreadObject> objects,
                                       int numberOfThreads) {
            checkBalancedAccesses(objects, numberOfThreads, countReadAccessesPerThread(objects), "reader");
        }

        public void checkBalancedWrites(Collection<InstrumentedThreadObject> objects,
                                        int numberOfThreads) {
            checkBalancedAccesses(objects, numberOfThreads, countWriteAccessesPerThread(objects), "writer");
        }

        public void checkNoRead(Collection<InstrumentedThreadObject> objects) {
            checkIsMainThread();
            Map<Long, Long> accesses = countReadAccessesPerThread(objects);
            if (accesses.size() > 0) {
                throw new IllegalStateException("Your threads are doing read accesses, which is not allowed");
            }
        }

        public void checkNoWrite(Collection<InstrumentedThreadObject> objects) {
            checkIsMainThread();
            Map<Long, Long> accesses = countWriteAccessesPerThread(objects);
            if (accesses.size() > 0) {
                throw new IllegalStateException("Your threads are doing write accesses, which is not allowed");
            }
        }
    }
    // END OF COPY-PASTE FROM "StadiumStatisticsTest"


    private static class InstrumentedCyclists implements BicycleRace.Cyclists {
        private static class InstrumentedPath extends InstrumentedThreadObject {
            private BicycleRace.Checkpoint[] path;

            public InstrumentedPath(BicycleRace.Checkpoint[] path) {
                this.path = path;
            }

            public BicycleRace.Checkpoint[] getPath() {
                logReadAccess();
                return path;
            }
        }

        private List<InstrumentedPath> paths = new ArrayList<>();

        @Override
        public int size() {
            return paths.size();
        }

        @Override
        public BicycleRace.Checkpoint[] getPath(int index) {
            return paths.get(index).getPath();
        }

        public void add(BicycleRace.Checkpoint[] path) {
            checkPath(path);
            paths.add(new InstrumentedPath(path));
        }

        public InstrumentedThreadObject getInstrumentedPath(int index) {
            return paths.get(index);
        }
    }

    @Test
    @Grade(value = 1, cpuTimeout = 3)
    public void testBalancedThreads() {
        InstrumentedCyclists cyclists = new InstrumentedCyclists();
        for (int i = 0; i < 27; i++) {
            cyclists.add(createPath(
                    new BicycleRace.Checkpoint(10, 20, 30),
                    new BicycleRace.Checkpoint(10 + i, 20, 30 + 60 * 60)));
        }

        List<InstrumentedThreadObject> instrumentedPaths = new LinkedList<>();
        for (int i = 0; i < cyclists.size(); i++) {
            instrumentedPaths.add(cyclists.getInstrumentedPath(i));
        }

        InstrumentedPoolThread executorService = new InstrumentedPoolThread(2);

        try {
            float[] speeds = BicycleRace.computeAverageSpeedParallel(cyclists, executorService);
            executorService.checkOnlyAccessedFromThreadPool(instrumentedPaths);
            executorService.checkAccessedByOneSingleThread(instrumentedPaths);
            executorService.checkBalancedReads(instrumentedPaths, 2);
            executorService.checkNoWrite(instrumentedPaths);

            assertEquals(speeds.length, cyclists.size());
            for (int i = 0; i < cyclists.size(); i++) {
                assertEquals(i, speeds[i], EPSILON);
            }
        } finally {
            executorService.shutdown();
        }
    }


}
