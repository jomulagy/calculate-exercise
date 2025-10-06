package calculateExercise.practice;

public class ProblemResult {
    private final Problem problem;
    private final String userAnswer;
    private final CheckResult checkResult;
    private final double elapsedSeconds;
    private final boolean timedOut;

    public ProblemResult(Problem problem, String userAnswer, CheckResult checkResult, double elapsedSeconds, boolean timedOut) {
        this.problem = problem;
        this.userAnswer = userAnswer;
        this.checkResult = checkResult;
        this.elapsedSeconds = elapsedSeconds;
        this.timedOut = timedOut;
    }

    public Problem getProblem() {
        return problem;
    }

    public String getUserAnswer() {
        return userAnswer;
    }

    public CheckResult getCheckResult() {
        return checkResult;
    }

    public double getElapsedSeconds() {
        return elapsedSeconds;
    }

    public boolean isTimedOut() {
        return timedOut;
    }
}
