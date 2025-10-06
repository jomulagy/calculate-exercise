package calculateExercise.practice;

public class SlopeComparisonProblem implements Problem {
    private final int x1;
    private final int y1;
    private final int x2;
    private final int y2;
    private final int x3;
    private final int y3;
    private final int x4;
    private final int y4;

    public SlopeComparisonProblem(int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.x3 = x3;
        this.y3 = y3;
        this.x4 = x4;
        this.y4 = y4;
    }

    @Override
    public String getQuestion() {
        return "선분 1: (" + x1 + ", " + y1 + ") ~ (" + x2 + ", " + y2 + ")\n" +
                "선분 2: (" + x3 + ", " + y3 + ") ~ (" + x4 + ", " + y4 + ")\n" +
                "기울기가 더 큰 선분의 번호를 입력하세요. (같으면 0 입력)";
    }

    @Override
    public String getCorrectAnswer() {
        int comparison = compareSlopes();
        return switch (comparison) {
            case 1 -> "선분 1";
            case -1 -> "선분 2";
            default -> "두 선분의 기울기가 같습니다";
        };
    }

    @Override
    public CheckResult checkAnswer(String userInput) {
        if (userInput == null || userInput.isBlank()) {
            return new CheckResult(false, "미입력으로 오답 처리됩니다. 정답은 " + getCorrectAnswer() + " 입니다.");
        }

        try {
            int answer = Integer.parseInt(userInput.trim());
            int comparison = compareSlopes();
            boolean correct = (answer == 0 && comparison == 0) || (answer == 1 && comparison == 1) || (answer == 2 && comparison == -1);
            if (correct) {
                return new CheckResult(true, "정답입니다! (" + getCorrectAnswer() + ")");
            }
            return new CheckResult(false, "아쉽습니다. 정답은 " + getCorrectAnswer() + " 입니다.");
        } catch (NumberFormatException ex) {
            return new CheckResult(false, "입력값을 정수로 해석할 수 없습니다. 정답은 " + getCorrectAnswer() + " 입니다.");
        }
    }

    private int compareSlopes() {
        double slope1 = (double) (y2 - y1) / (x2 - x1);
        double slope2 = (double) (y4 - y3) / (x4 - x3);
        double diff = slope1 - slope2;
        double tolerance = 1e-6;
        if (Math.abs(diff) <= tolerance) {
            return 0;
        }
        return diff > 0 ? 1 : -1;
    }
}
