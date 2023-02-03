package co.com.sofka.questions.usecases;

import co.com.sofka.questions.collections.Answer;
import co.com.sofka.questions.collections.Question;
import co.com.sofka.questions.model.AnswerDTO;
import co.com.sofka.questions.model.QuestionDTO;
import co.com.sofka.questions.reposioties.AnswerRepository;
import co.com.sofka.questions.reposioties.QuestionRepository;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

import java.util.Objects;


@Service
@Validated
public class UpdateUseCase implements SaveQuestion {
    private final QuestionRepository questionRepository;
    private final MapperUtils mapperUtils;
    private final AnswerRepository answerRepository;

    public UpdateUseCase(MapperUtils mapperUtils, QuestionRepository questionRepository,
                         AnswerRepository answerRepository) {
        this.questionRepository = questionRepository;
        this.mapperUtils = mapperUtils;
        this.answerRepository = answerRepository;
    }

    @Override
    public Mono<String> apply(QuestionDTO dto) {
        Objects.requireNonNull(dto.getId(), "Id of the question is required");
        return questionRepository
                .save(mapperUtils.mapperToQuestion(dto.getId()).apply(dto))
                .map(Question::getId);
    }

}
