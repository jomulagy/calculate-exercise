package calculateExercise.practice;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;
import java.util.function.Supplier;

@Component
public class ProblemFactory {
    private final Random random;
    private final List<Supplier<Problem>> generators;

    public ProblemFactory(Random random) {
        this.random = random;
        this.generators = List.of(
                this::createPercentageChangeProblem,
                this::createRatioProblem,
                this::createSlopeComparisonProblem
        );
    }

    public Problem nextProblem() {
        int index = random.nextInt(generators.size());
        return generators.get(index).get();
    }

    private Problem createPercentageChangeProblem() {
        int oldValue = randomValue(50, 200);
        int newValue = randomValue(50, 200);
        return new PercentageChangeProblem(oldValue, newValue);
    }

    private Problem createRatioProblem() {
        int first = randomValue(30, 150);
        int second = randomValue(30, 150);
        return new RatioProblem(first, second);
    }

    private Problem createSlopeComparisonProblem() {
        int x1 = randomValue(-5, 5);
        int x2 = x1 + randomNonZero(1, 5);
        int y1 = randomValue(-10, 10);
        int y2 = y1 + randomNonZero(-10, 10);

        int x3 = randomValue(-5, 5);
        int x4 = x3 + randomNonZero(1, 5);
        int y3 = randomValue(-10, 10);
        int y4 = y3 + randomNonZero(-10, 10);

        return new SlopeComparisonProblem(x1, y1, x2, y2, x3, y3, x4, y4);
    }

    private int randomValue(int minInclusive, int maxInclusive) {
        return random.nextInt(maxInclusive - minInclusive + 1) + minInclusive;
    }

    private int randomNonZero(int minInclusive, int maxInclusive) {
        int value;
        do {
            value = randomValue(minInclusive, maxInclusive);
        } while (value == 0);
        return value;
    }
}
