package calculateExercise.practice;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.util.Locale;
import java.util.Scanner;

@Component
public class GsatPracticeRunner implements CommandLineRunner {

    @Override
    public void run(String... args) {
        Locale.setDefault(Locale.KOREAN);
        try (Scanner scanner = new Scanner(System.in)) {
            ProblemFactory factory = new ProblemFactory(new java.util.Random());

            System.out.println("삼성 GSAT 자료해석 연습 프로그램에 오신 것을 환영합니다!");
            int numberOfProblems = askForInteger(scanner, "풀이할 문제 수를 입력하세요 (기본값 5): ", 5, 1, 50);
            long timeLimitSeconds = askForInteger(scanner, "문제당 제한 시간(초)을 입력하세요 (기본값 30초): ", 30, 5, 300);

            int correctCount = 0;
            for (int i = 1; i <= numberOfProblems; i++) {
                Problem problem = factory.nextProblem();
                System.out.println();
                System.out.println("문제 " + i + "/" + numberOfProblems);
                System.out.println(problem.getQuestion());
                System.out.println("답안을 입력한 뒤 Enter 키를 누르세요.");

                Instant start = Instant.now();
                String userAnswer = scanner.nextLine();
                Instant end = Instant.now();
                long elapsedMillis = Duration.between(start, end).toMillis();

                if (elapsedMillis > timeLimitSeconds * 1000L) {
                    System.out.println(timeLimitSeconds + "초 이내에 답을 제출하지 못했습니다.");
                    System.out.println("정답: " + problem.getCorrectAnswer());
                    continue;
                }

                CheckResult result = problem.checkAnswer(userAnswer);
                System.out.println(result.feedback());
                System.out.printf(Locale.KOREAN, "소요 시간: %.1f초\n", elapsedMillis / 1000.0);
                if (result.correct()) {
                    correctCount++;
                }
            }

            System.out.println();
            System.out.println("채점 완료! 총 " + numberOfProblems + "문제 중 " + correctCount + "문제를 맞혔습니다.");
            double accuracy = (double) correctCount / numberOfProblems * 100.0;
            System.out.printf(Locale.KOREAN, "정답률: %.1f%%\n", accuracy);
            System.out.println("연습을 계속하려면 프로그램을 다시 실행하세요.");
        }
    }

    private int askForInteger(Scanner scanner, String prompt, int defaultValue, int min, int max) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine();
            if (input.isBlank()) {
                return defaultValue;
            }
            try {
                int value = Integer.parseInt(input.trim());
                if (value < min || value > max) {
                    System.out.println(min + " 이상 " + max + " 이하의 값을 입력하세요.");
                } else {
                    return value;
                }
            } catch (NumberFormatException ex) {
                System.out.println("정수를 입력해주세요.");
            }
        }
    }
}
