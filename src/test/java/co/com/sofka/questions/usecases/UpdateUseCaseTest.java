package co.com.sofka.questions.usecases;

import co.com.sofka.questions.collections.Question;
import co.com.sofka.questions.model.QuestionDTO;
import co.com.sofka.questions.reposioties.QuestionRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UpdateUseCaseTest {

    @SpyBean
    UpdateUseCase updateUseCase;

    @Autowired
    private MapperUtils mapperUtils;

    @Mock
    private QuestionRepository questionRepository;

    @Test
    void updateHappyPass(){

        QuestionDTO questionDTO = new QuestionDTO("xxx", "idUser", "question", "cerrada", "category");
        Question question = mapperUtils.mapperToQuestion("xxx").apply(questionDTO);

        Mockito.when(questionRepository.save(question))
                .thenReturn(Mono.just(question));


        StepVerifier
                .create(updateUseCase.apply(questionDTO))
                .expectNextMatches(questionRes -> {
                    Assertions.assertEquals(questionRes,question.getId());
                    return true;
                })
                .verifyComplete();

    }

}