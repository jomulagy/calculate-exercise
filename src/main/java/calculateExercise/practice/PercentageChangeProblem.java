package calculateExercise.practice;

public class PercentageChangeProblem extends NumericProblem {
    public PercentageChangeProblem(int oldValue, int newValue) {
        super(
                String.format("어떤 지표가 %d에서 %d로 변했습니다. 증감률(%%)을 소수점 첫째 자리까지 구하세요.", oldValue, newValue),
                calculateAnswer(oldValue, newValue),
                0.05,
                "0.0",
                "%"
        );
    }

    private static double calculateAnswer(int oldValue, int newValue) {
        return ((double) newValue - oldValue) / oldValue * 100.0;
    }
}
