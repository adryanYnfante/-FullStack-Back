package co.com.sofka.questions.usecases;

import co.com.sofka.questions.collections.Answer;
import co.com.sofka.questions.collections.Question;
import co.com.sofka.questions.model.AnswerDTO;
import co.com.sofka.questions.model.QuestionDTO;
import co.com.sofka.questions.reposioties.AnswerRepository;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Service
@Validated
public class UpdateAnswerUseCase {
    private final AnswerRepository answerRepository;
    private final MapperUtils mapperUtils;


    public UpdateAnswerUseCase(MapperUtils mapperUtils ,AnswerRepository answerRepository) {
        this.answerRepository = answerRepository;
        this.mapperUtils = mapperUtils;
    }

    public Mono<String> apply(AnswerDTO dto) {
        Objects.requireNonNull(dto.getId(), "Id of the question is required");
        return answerRepository
                .save(mapperUtils.mapperToAnswer(dto.getId()).apply(dto))
                .map(Answer::getId);
    }

}
