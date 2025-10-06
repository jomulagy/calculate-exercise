package calculateExercise.practice;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public abstract class NumericProblem implements Problem {
    private final double answer;
    private final double tolerance;
    private final String question;
    private final String unit;
    private final DecimalFormat decimalFormat;

    protected NumericProblem(String question, double answer, double tolerance, String pattern, String unit) {
        this.question = question;
        this.answer = answer;
        this.tolerance = tolerance;
        this.unit = unit;
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.KOREAN);
        symbols.setDecimalSeparator('.');
        this.decimalFormat = new DecimalFormat(pattern, symbols);
    }

    @Override
    public String getQuestion() {
        return question;
    }

    @Override
    public String getCorrectAnswer() {
        return decimalFormat.format(answer) + unit;
    }

    @Override
    public CheckResult checkAnswer(String userInput) {
        if (userInput == null || userInput.isBlank()) {
            return new CheckResult(false, "미입력으로 오답 처리됩니다. 정답은 " + getCorrectAnswer() + " 입니다.");
        }

        try {
            double userAnswer = Double.parseDouble(userInput.trim());
            if (Math.abs(userAnswer - answer) <= tolerance) {
                return new CheckResult(true, "정답입니다! (기댓값: " + getCorrectAnswer() + ")");
            }
            return new CheckResult(false, "아쉽습니다. 정답은 " + getCorrectAnswer() + " 입니다.");
        } catch (NumberFormatException ex) {
            return new CheckResult(false, "입력값을 숫자로 해석할 수 없습니다. 정답은 " + getCorrectAnswer() + " 입니다.");
        }
    }
}
