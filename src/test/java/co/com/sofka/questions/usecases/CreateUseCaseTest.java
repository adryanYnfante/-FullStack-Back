package co.com.sofka.questions.usecases;

import co.com.sofka.questions.collections.Question;
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
import static org.mockito.Mockito.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CreateUseCaseTest {
    @SpyBean
    QuestionRepository repository;

    @SpyBean
    CreateUseCase createUseCase;

    @Autowired
    private MapperUtils mapperUtils;

    @Mock
    QuestionRepository questionRepository;

    @BeforeEach
    public void setup(){
        MapperUtils mapperUtils = new MapperUtils();
        repository = mock(QuestionRepository.class);

    }

    @Test
    void getValidationTest(){
        Question question = new Question();
        QuestionDTO questionDTO =new QuestionDTO("300","julian2345","accion","accion","accion");
        question.setId(questionDTO.getId());
        question.setUserId(questionDTO.getUserId());
        question.setQuestion(questionDTO.getQuestion());
        question.setType(questionDTO.getType());
        question.setCategory(questionDTO.getCategory());

        Mockito.when(questionRepository.save(question)).thenReturn(Mono.just(mapperUtils.mapperToQuestion(questionDTO.getId()).apply(questionDTO)));

        StepVerifier.create(createUseCase.apply(questionDTO))
                .expectNextMatches(MonoQ -> {
                    equals("300");
                    System.out.println(MonoQ);
                    return true;
                })
                .verifyComplete();


    }

}