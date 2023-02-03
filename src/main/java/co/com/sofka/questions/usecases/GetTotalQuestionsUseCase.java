package co.com.sofka.questions.usecases;

import co.com.sofka.questions.reposioties.QuestionRepository;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

@Service
@Validated
public class GetTotalQuestionsUseCase {
    private final QuestionRepository questionRepository;

    public GetTotalQuestionsUseCase(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public Mono<Long> getTotalQuestions() {
        return questionRepository.count();
    }
}
