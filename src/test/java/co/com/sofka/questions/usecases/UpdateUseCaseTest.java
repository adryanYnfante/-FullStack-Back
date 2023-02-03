package co.com.sofka.questions.usecases;

import co.com.sofka.questions.collections.Question;
import co.com.sofka.questions.model.QuestionDTO;
import co.com.sofka.questions.reposioties.QuestionRepository;
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
class UpdateUseCaseTest {
    @Mock
    private QuestionRepository questionRepository;
    @SpyBean
    UpdateUseCase updateUseCase;
    @Autowired
    private MapperUtils mapperUtils;
    @Test
    void updateHappyPass(){

        Question question = new Question();

        QuestionDTO questionDTO = new QuestionDTO(
                "sofka",
                "userid",
                "prueba",
                "test",
                "math");

        question.setId(questionDTO.getId());
        question.setUserId(questionDTO.getUserId());
        question.setQuestion(questionDTO.getQuestion());
        question.setType(questionDTO.getType());
        question.setCategory(questionDTO.getCategory());

        // Actualizando ID de la pregunta
        questionDTO.setId("Cambio");

        Mockito.when(questionRepository.save(question))
                .thenReturn(Mono.just(mapperUtils.mapperToQuestion(questionDTO.getId())
                        .apply(questionDTO)));

        StepVerifier
                .create(updateUseCase.apply(questionDTO))
                .expectNextMatches(MonoQuestion -> {
                    System.out.println(MonoQuestion);
                    return true;
                })
                .verifyComplete();
    }
}