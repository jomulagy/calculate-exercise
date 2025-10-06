package calculateExercise.practice;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.Instant;

@Controller
@RequestMapping("/practice")
public class PracticeController {
    private static final String PRACTICE_SESSION_KEY = "practiceSession";

    private final PracticeService practiceService;

    public PracticeController(PracticeService practiceService) {
        this.practiceService = practiceService;
    }

    @GetMapping
    public String showStartForm() {
        return "practice/start";
    }

    @PostMapping("/start")
    public String startPractice(@RequestParam(name = "timeLimit", defaultValue = "30") long timeLimitSeconds,
                                HttpSession session) {
        if (timeLimitSeconds < 5) {
            timeLimitSeconds = 5;
        }
        session.removeAttribute("lastBatchResult");
        PracticeSession practiceSession = practiceService.startSession(timeLimitSeconds);
        session.setAttribute(PRACTICE_SESSION_KEY, practiceSession);
        return "redirect:/practice/question";
    }

    @GetMapping("/question")
    public String showQuestion(HttpSession session, Model model) {
        PracticeSession practiceSession = getPracticeSession(session);
        if (practiceSession == null) {
            return "redirect:/practice";
        }
        if (practiceSession.isCompleted()) {
            return "redirect:/practice/summary";
        }
        if (practiceSession.getQuestionStart() == null) {
            practiceSession.startQuestion(Instant.now());
        }
        model.addAttribute("problem", practiceSession.getCurrentProblem());
        model.addAttribute("questionNumber", practiceSession.getCurrentIndex() + 1);
        model.addAttribute("totalQuestions", practiceSession.getTotalProblems());
        model.addAttribute("timeLimit", practiceSession.getTimeLimitSeconds());
        return "practice/question";
    }

    @PostMapping("/answer")
    public String submitAnswer(@RequestParam("answer") String answer, HttpSession session, Model model) {
        PracticeSession practiceSession = getPracticeSession(session);
        if (practiceSession == null) {
            return "redirect:/practice";
        }

        ProblemResult result = practiceService.submitAnswer(practiceSession, answer, Instant.now());
        model.addAttribute("result", result);
        model.addAttribute("questionNumber", practiceSession.getResults().size());
        model.addAttribute("totalQuestions", practiceSession.getTotalProblems());
        model.addAttribute("timeLimit", practiceSession.getTimeLimitSeconds());

        if (practiceSession.isCompleted()) {
            PracticeBatchResult batchResult = practiceService.completeSession(practiceSession);
            session.removeAttribute(PRACTICE_SESSION_KEY);
            session.setAttribute("lastBatchResult", batchResult);
            model.addAttribute("completed", true);
            model.addAttribute("batchResult", batchResult);
        } else {
            model.addAttribute("completed", false);
        }

        return "practice/answer";
    }

    @GetMapping("/summary")
    public String showSummary(HttpSession session, Model model) {
        PracticeBatchResult batchResult = (PracticeBatchResult) session.getAttribute("lastBatchResult");
        if (batchResult == null) {
            return "redirect:/practice";
        }
        model.addAttribute("batchResult", batchResult);
        return "practice/summary";
    }

    @GetMapping("/history")
    public String showHistory(Model model) {
        model.addAttribute("history", practiceService.findHistory());
        return "practice/history";
    }

    private PracticeSession getPracticeSession(HttpSession session) {
        Object value = session.getAttribute(PRACTICE_SESSION_KEY);
        if (value instanceof PracticeSession practiceSession) {
            return practiceSession;
        }
        return null;
    }
}
