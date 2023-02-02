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
public class UpdateAnswerUseCase{

    private final AnswerRepository answerRepository;
    private final MapperUtils mapperUtils;

    public UpdateAnswerUseCase(AnswerRepository answerRepository, MapperUtils mapperUtils) {
        this.answerRepository = answerRepository;
        this.mapperUtils = mapperUtils;
    }

/*    @Override
    public Mono<AnswerDTO> apply(AnswerDTO answerDTO) {

        //Objects.requireNonNull(answerDTO.getQuestionId(), "Id of the question is required");
        return answerRepository.findById(answerDTO.getId())
                .flatMap(
                        oldAnswer -> {
                            oldAnswer.setAnswer(answerDTO.getAnswer());
                            oldAnswer.setPosition(answerDTO.getPosition());
                             answerRepository.save(oldAnswer);
                             return answerDTO;

                        }
                );
    }*/
}
