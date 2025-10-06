package calculateExercise.practice;

import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;

@Service
public class PracticeService {
    private static final int PROBLEMS_PER_BATCH = 5;

    private final ProblemFactory problemFactory;
    private final PracticeHistoryService historyService;

    public PracticeService(ProblemFactory problemFactory, PracticeHistoryService historyService) {
        this.problemFactory = problemFactory;
        this.historyService = historyService;
    }

    public PracticeSession startSession(long timeLimitSeconds) {
        List<Problem> problems = IntStream.range(0, PROBLEMS_PER_BATCH)
                .mapToObj(i -> problemFactory.nextProblem())
                .toList();
        return new PracticeSession(problems, timeLimitSeconds);
    }

    public ProblemResult submitAnswer(PracticeSession session, String userAnswer, Instant submittedAt) {
        if (session.isCompleted()) {
            throw new IllegalStateException("모든 문제가 이미 제출되었습니다.");
        }

        Problem problem = session.getCurrentProblem();
        Instant start = session.getQuestionStart();
        if (start == null) {
            start = submittedAt;
        }
        double elapsedSeconds = Duration.between(start, submittedAt).toMillis() / 1000.0;

        boolean timedOut = elapsedSeconds > session.getTimeLimitSeconds();
        CheckResult checkResult;
        if (timedOut) {
            checkResult = new CheckResult(false,
                    session.getTimeLimitSeconds() + "초 이내에 답을 제출하지 못했습니다. 정답은 " + problem.getCorrectAnswer() + " 입니다.");
        } else {
            checkResult = problem.checkAnswer(userAnswer);
        }

        ProblemResult result = new ProblemResult(
                problem,
                userAnswer,
                checkResult,
                elapsedSeconds,
                timedOut
        );
        session.recordResult(result);
        return result;
    }

    public PracticeBatchResult completeSession(PracticeSession session) {
        if (!session.isCompleted()) {
            throw new IllegalStateException("아직 모든 문제가 제출되지 않았습니다.");
        }
        PracticeBatchResult batchResult = new PracticeBatchResult(
                UUID.randomUUID(),
                Instant.now(),
                session.getTimeLimitSeconds(),
                session.getResults()
        );
        historyService.save(batchResult);
        return batchResult;
    }

    public List<PracticeBatchResult> findHistory() {
        return historyService.findAll();
    }
}
