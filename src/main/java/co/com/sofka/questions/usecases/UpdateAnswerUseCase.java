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
public class UpdateAnswerUseCase {
    private final AnswerRepository answerRepository;
    private final MapperUtils mapperUtils;

    public UpdateAnswerUseCase(MapperUtils mapperUtils, AnswerRepository answerRepository) {
        this.answerRepository = answerRepository;
        this.mapperUtils = mapperUtils;
    }

    public Mono<String> apply(AnswerDTO answerDTO) {
        Objects.requireNonNull(answerDTO.getIdAnswer(), "Id of the question is required");
        return  answerRepository
                .save(mapperUtils.mapperToAnswer(answerDTO.getIdAnswer()).apply(answerDTO))
                .map(Answer::getId);
    }
}