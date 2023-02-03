package co.com.sofka.questions.usecases;

import co.com.sofka.questions.collections.Answer;
import co.com.sofka.questions.collections.Question;
import co.com.sofka.questions.model.AnswerDTO;
import co.com.sofka.questions.model.QuestionDTO;
import co.com.sofka.questions.reposioties.AnswerRepository;
import co.com.sofka.questions.reposioties.QuestionRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.ArrayList;
import java.util.List;

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
    void editAnswerHappyPass() {



        Answer answer = new Answer();
        AnswerDTO    answerDTO= new AnswerDTO("123","300",
                "julian2345","super peli",5);
        answer.setQuestionId(answerDTO.getQuestionId());
        answer.setUserId(answerDTO.getUserId());
        answer.setAnswer(answerDTO.getAnswer());
        answer.setPosition(answerDTO.getPosition());

        Mockito.when(answerRepository.save(answer))
                .thenReturn(Mono.just(mapperUtils.mapperToAnswer(answerDTO.getId())
                        .apply(answerDTO)));


        StepVerifier
                .create(updateAnswerUseCase.editAnswer(answerDTO))
                .expectNextMatches(MonoA -> {
                    assert MonoA.equals("123");
                    System.out.println(MonoA);
                    return true;
                })
                .verifyComplete();
    }
}