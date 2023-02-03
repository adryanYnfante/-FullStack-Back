package co.com.sofka.questions.usecases;

import co.com.sofka.questions.collections.Answer;
import co.com.sofka.questions.model.AnswerDTO;
import co.com.sofka.questions.model.QuestionDTO;
import co.com.sofka.questions.reposioties.AnswerRepository;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Service
@Validated
public class AddAnswerUseCase implements SaveAnswer {
    private final AnswerRepository answerRepository;
    private final MapperUtils mapperUtils;
    private final GetUseCase getUseCase;

    public AddAnswerUseCase(MapperUtils mapperUtils, GetUseCase getUseCase, AnswerRepository answerRepository) {
        this.answerRepository = answerRepository;
        this.getUseCase = getUseCase;
        this.mapperUtils = mapperUtils;
    }

    public Mono<QuestionDTO> apply(AnswerDTO dto) {
        Objects.requireNonNull(dto.getQuestionId(), "Id of the answer is required");
        return getUseCase.apply(dto.getQuestionId()).flatMap(question ->
                answerRepository.save(mapperUtils.mapperToAnswer(dto.getId()).apply(dto))
                        .map(answer -> {
                            question.getAnswers().add(dto);
                            return question;
                        })
        );
    }

    public Mono<String> editAnswer(AnswerDTO dto){
        Objects.requireNonNull(dto.getId(), "Id of de answer is required");
        return answerRepository
                .save(mapperUtils.mapperToAnswer(dto.getId()).apply(dto))
                .map(Answer::getId);
    }

}
