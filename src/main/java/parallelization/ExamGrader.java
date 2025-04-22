package parallelization;

import java.util.List;
import java.util.concurrent.*;

public class ExamGrader {

    // You can add new methods, inner classes, etc.

    public interface RoundingFunction {
        int roundGrade(double grade);
    }


    public static class ExamQuestion {
        private double pointsObtained;
        private ExamQuestion nextQuestion;

        public ExamQuestion(double points, ExamQuestion next) {
            this.pointsObtained = points;
            this.nextQuestion = next;
        }

        public double getPointsObtained() {
            return pointsObtained;
        }

        public ExamQuestion getNextQuestion() {
            return nextQuestion;
        }
    }

    /**
     *  Write a method calculateExamGrade that calculates the grade
     *  obtained for an exam. The grade is the sum of the points
     *  obtained in all questions. The questions are provided in a
     *  linked list (class ExamQuestion).
     *
     *  However, the points are real numbers, while the exam grade
     *  is a natural number. To round the grade, the caller of this
     *  method provides a rounding function that you have to use
     *  to obtain the final result.
     *
     *  Look at the test "testWithTwoQuestions" to see how users will
     *  call this method.
     *
     *  You can assume that all points are positive numbers and that
     *  the list of questions is not empty.
     */
    public static int calculateExamGrade(ExamQuestion questions, RoundingFunction roundingFunction) {
        double cnt = 0;
        ExamQuestion current = questions;
        while (current != null){
            cnt += current.getPointsObtained();
            current = current.getNextQuestion();
        }
        int note = roundingFunction.roundGrade(cnt);
        return note;
    }

    public static class CalculateExamCallable implements Callable<Integer> {
        private ExamQuestion questions;
        private RoundingFunction roundingFunction;

        CalculateExamCallable (ExamQuestion questions,RoundingFunction roundingFunction){
            this.questions = questions;
            this.roundingFunction = roundingFunction;
        }

        public Integer call(){
            return calculateExamGrade(questions,roundingFunction);
        }
    }


    /**
     * Write a method gradeExams that calculates the grades of two exams
     * using two threads to accelerate the grading (one exam graded per thread).
     * The method must return the two grades in an int array.
     * Like for the method calculateExamGrade, the caller of this method
     * provides a rounding function.
     *
     * Look at the test "testTwoShortExams" to see how users will
     * call this method.
     *
     * You MUST use threads (or a threadpool).
     * Catch (and ignore) all exceptions.
     */
    public static int[] gradeExams(ExamQuestion exam1, ExamQuestion exam2, RoundingFunction roundingFunction) {
        ExecutorService executor = Executors.newFixedThreadPool(2);
        Future<Integer> future1 = executor.submit(new CalculateExamCallable(exam1,roundingFunction));
        Future<Integer> future2 = executor.submit(new CalculateExamCallable(exam2,roundingFunction));
        int[] result = new int[2];
        try{
            result[0] = future1.get();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
        try{
            result[1] = future2.get();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
        return result;
    }
}