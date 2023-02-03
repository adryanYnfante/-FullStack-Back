package co.com.sofka.questions.usecases;

import co.com.sofka.questions.collections.Answer;
import co.com.sofka.questions.collections.Question;
import co.com.sofka.questions.model.AnswerDTO;
import co.com.sofka.questions.model.QuestionDTO;
import co.com.sofka.questions.reposioties.AnswerRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UpdateAnswerUseCaseTest {
    @Autowired
    MapperUtils mapperUtils;
    @Mock
    AnswerRepository answerRepository;
    @Mock
    UpdateAnswerUseCase updateAnswerUseCase;
    @Test
    void apply() {
        Answer answer = new Answer();
        AnswerDTO answerDTO =new AnswerDTO("300","132838","Es buena","abierta",3);
        answer.setId(answerDTO.getIdAnswer());
        answer.setUserId(answerDTO.getUserId());
        answer.setQuestionId(answerDTO.getQuestionId());
        answer.setAnswer(answerDTO.getAnswer());
        answer.setPosition(answerDTO.getPosition());

        Mockito.when(answerRepository.save(answer))
                .thenReturn(
                        Mono.just(
                                mapperUtils.mapperToAnswer(
                                                answerDTO.getIdAnswer()
                                        )
                                        .apply(answerDTO)
                        )
                );
        StepVerifier.create(updateAnswerUseCase.apply(answerDTO))
                .expectNextMatches(MonoQ -> {
                    equals("300");
                    return true;
                });
    }
}