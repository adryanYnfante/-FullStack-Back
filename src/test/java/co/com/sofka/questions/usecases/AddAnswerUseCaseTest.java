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
class AddAnswerUseCaseTest {
    @SpyBean
    AddAnswerUseCase addAnswerUseCase;

    @Mock
    private AnswerRepository answerRepository;

    @Autowired
    MapperUtils mapperUtils;


    @Test
    public void addAnswerUseCaseTest(){
        AnswerDTO answerDTO = new AnswerDTO("idAnswer","300","idUser","answer",5);
        Answer answer = mapperUtils.mapperToAnswer(null).apply(answerDTO);

        Answer answer1 = Mockito.mock(Answer.class);

        Mono<Answer> answerMono = Mono.just(answer);

        Mockito.when(answerRepository.save(answer1)).thenReturn(answerMono);

        StepVerifier.create(addAnswerUseCase.apply(answerDTO))
                .expectNextMatches(answerDTO1 -> {
                    assert  answerDTO1.getAnswer().equals("answer");
                    return true;
                });



    }

}