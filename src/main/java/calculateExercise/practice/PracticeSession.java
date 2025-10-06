package calculateExercise.practice;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PracticeSession {
    private final List<Problem> problems;
    private final long timeLimitSeconds;
    private final List<ProblemResult> results = new ArrayList<>();
    private int currentIndex = 0;
    private Instant questionStart;

    public PracticeSession(List<Problem> problems, long timeLimitSeconds) {
        this.problems = List.copyOf(problems);
        this.timeLimitSeconds = timeLimitSeconds;
    }

    public List<Problem> getProblems() {
        return Collections.unmodifiableList(problems);
    }

    public long getTimeLimitSeconds() {
        return timeLimitSeconds;
    }

    public List<ProblemResult> getResults() {
        return Collections.unmodifiableList(results);
    }

    public int getCurrentIndex() {
        return currentIndex;
    }

    public int getTotalProblems() {
        return problems.size();
    }

    public boolean isCompleted() {
        return currentIndex >= problems.size();
    }

    public Problem getCurrentProblem() {
        if (isCompleted()) {
            throw new IllegalStateException("연습 세션이 이미 종료되었습니다.");
        }
        return problems.get(currentIndex);
    }

    public void startQuestion(Instant startTime) {
        this.questionStart = startTime;
    }

    public Instant getQuestionStart() {
        return questionStart;
    }

    public void recordResult(ProblemResult result) {
        if (isCompleted()) {
            throw new IllegalStateException("모든 문제가 이미 제출되었습니다.");
        }
        results.add(result);
        currentIndex++;
        questionStart = null;
    }
}
