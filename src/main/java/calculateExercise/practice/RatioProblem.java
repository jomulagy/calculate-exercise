package calculateExercise.practice;

public class RatioProblem extends NumericProblem {
    public RatioProblem(int part, int whole) {
        super(
                String.format("두 부서의 매출이 각각 %d와 %d 입니다. 후자의 매출이 전자의 몇 배인지 소수점 둘째 자리까지 구하세요.", part, whole),
                calculateAnswer(part, whole),
                0.005,
                "0.00",
                "배"
        );
    }

    private static double calculateAnswer(int part, int whole) {
        return (double) whole / part;
    }
}
