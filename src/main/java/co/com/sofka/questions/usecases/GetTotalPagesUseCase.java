package co.com.sofka.questions.usecases;

import co.com.sofka.questions.reposioties.QuestionRepository;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

@Service
@Validated
public class GetTotalPagesUseCase {

    private final QuestionRepository questionRepository;

    public GetTotalPagesUseCase(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public Mono<Integer> getPage(){
        return questionRepository.count().map(count -> (int) Math.ceil(count/10)+1);
    }
}
