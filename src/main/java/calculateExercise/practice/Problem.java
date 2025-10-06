package calculateExercise.practice;

public interface Problem {
    String getQuestion();

    String getCorrectAnswer();

    CheckResult checkAnswer(String userInput);
}
