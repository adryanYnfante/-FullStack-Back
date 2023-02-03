package co.com.sofka.questions.usecases;

import co.com.sofka.questions.collections.Answer;
import co.com.sofka.questions.collections.Question;
import co.com.sofka.questions.model.AnswerDTO;
import co.com.sofka.questions.model.QuestionDTO;
import co.com.sofka.questions.reposioties.AnswerRepository;
import co.com.sofka.questions.reposioties.QuestionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UpdateAnswerUseCaseTest {

    @SpyBean
    AnswerRepository answerRepository;

    @SpyBean
    UpdateAnswerUseCase updateAnswerUseCase;

    @Autowired
    private MapperUtils mapperUtils;



    @BeforeEach
    public void setup(){
        MapperUtils mapperUtils = new MapperUtils();
        answerRepository = mock(AnswerRepository.class);

    }

    @Test
    void getValidationTest(){
        Answer answer = new Answer();
        AnswerDTO answerDTO= new AnswerDTO("123","300","132838","ok",5);

        answer.setId(answerDTO.getId());
        answer.setQuestionId(answerDTO.getQuestionId());
        answer.setUserId(answerDTO.getUserId());
        answer.setAnswer(answerDTO.getAnswer());
        answer.setPosition(answerDTO.getPosition());


        Mockito.when(answerRepository.save(answer)).thenReturn(Mono.just(mapperUtils.mapperToAnswer(answerDTO.getId()).apply(answerDTO)));

        StepVerifier.create(updateAnswerUseCase.apply(answerDTO))
                .expectNextMatches(MonoQ -> {
                    equals("123");

                    return true;
                })
                .verifyComplete();


    }
}