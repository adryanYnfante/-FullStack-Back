package co.com.sofka.questions.usecases;

import co.com.sofka.questions.collections.Answer;
import co.com.sofka.questions.model.AnswerDTO;
import co.com.sofka.questions.reposioties.AnswerRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;


import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UpdateAnswerUseCaseTest {

    @SpyBean
    UpdateAnswerUseCase updateAnswerUseCase;

    @Autowired
    private MapperUtils mapperUtils;

    @Mock
    private AnswerRepository answerRepository;
    @Test
    void editAnswer() {

        Answer answer = new Answer();
        AnswerDTO answerDTO = new AnswerDTO("125","41A","5","25", 5);
        answer.setId(answerDTO.getId());
        answer.setQuestionId(answerDTO.getQuestionId());
        answer.setUserId(answerDTO.getUserId());
        answer.setAnswer(answerDTO.getAnswer());
        answer.setPosition(answerDTO.getPosition());

        Mockito.when(answerRepository.save(answer))
                .thenReturn(Mono.just(mapperUtils.mapperToAnswer(answerDTO.getId())
                        .apply(answerDTO)));


        StepVerifier
                .create(updateAnswerUseCase.editAnswer(answerDTO))
                .expectNextMatches(MonoAnswer -> {

                    System.out.println(MonoAnswer.toString());
                    return true;
                })
                .verifyComplete();

    }
}
