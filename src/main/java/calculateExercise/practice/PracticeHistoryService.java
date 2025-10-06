package calculateExercise.practice;

import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class PracticeHistoryService {
    private final CopyOnWriteArrayList<PracticeBatchResult> history = new CopyOnWriteArrayList<>();

    public void save(PracticeBatchResult result) {
        history.add(result);
    }

    public List<PracticeBatchResult> findAll() {
        return Collections.unmodifiableList(history);
    }
}
