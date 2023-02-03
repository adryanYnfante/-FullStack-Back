package co.com.sofka.questions.usecases;

import co.com.sofka.questions.collections.Question;
import co.com.sofka.questions.model.AnswerDTO;
import co.com.sofka.questions.model.QuestionDTO;
import co.com.sofka.questions.reposioties.QuestionRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UpdateUseCaseTest {

    @SpyBean
    private UpdateUseCase updateUseCase;
    @Autowired
    private MapperUtils mapperUtils;
    @Mock
    QuestionRepository questionRepository;

    @Test
    public void updateUseCaseTest(){
        QuestionDTO questionDto = new QuestionDTO("idQuestion","300","daniel","acción","acción");

        Question question = mapperUtils.mapperToQuestion("idQuestion").apply(questionDto);

        Mono<Question> questionMono = Mono.just(question);



        Question question1 = Mockito.mock(Question.class);

        Mockito.when(questionRepository.save(question1)).thenReturn(questionMono);

        StepVerifier.create(updateUseCase.apply(questionDto))
                .expectNextMatches(q->{

                    assert  q.equals("idQuestion");
                    return true;
                })
                .verifyComplete();

    }

}