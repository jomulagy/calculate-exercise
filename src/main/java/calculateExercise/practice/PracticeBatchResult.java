package calculateExercise.practice;

import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class PracticeBatchResult {
    private final UUID id;
    private final Instant completedAt;
    private final long timeLimitSeconds;
    private final List<ProblemResult> problemResults;

    public PracticeBatchResult(UUID id, Instant completedAt, long timeLimitSeconds, List<ProblemResult> problemResults) {
        this.id = id;
        this.completedAt = completedAt;
        this.timeLimitSeconds = timeLimitSeconds;
        this.problemResults = List.copyOf(problemResults);
    }

    public UUID getId() {
        return id;
    }

    public Instant getCompletedAt() {
        return completedAt;
    }

    public long getTimeLimitSeconds() {
        return timeLimitSeconds;
    }

    public List<ProblemResult> getProblemResults() {
        return Collections.unmodifiableList(problemResults);
    }

    public int getTotalProblems() {
        return problemResults.size();
    }

    public long getCorrectCount() {
        return problemResults.stream().filter(result -> result.getCheckResult().correct()).count();
    }

    public double getAccuracy() {
        if (problemResults.isEmpty()) {
            return 0;
        }
        return (double) getCorrectCount() / problemResults.size() * 100.0;
    }
}
