package co.com.sofka.questions.usecases;

import co.com.sofka.questions.collections.Question;
import co.com.sofka.questions.model.QuestionDTO;
import co.com.sofka.questions.reposioties.QuestionRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CreateUseCaseTest {

    QuestionRepository repository;
    CreateUseCase createUseCase;


    @BeforeEach
    public void setup(){
        MapperUtils mapperUtils = new MapperUtils();
        repository = mock(QuestionRepository.class);
        createUseCase = new CreateUseCase(mapperUtils, repository);
    }

    @Test
    void getValidationTest() {
        var question = new Question();
        question.setId("x1x1");
        question.setUserId("pepe1");
        question.setType("cerrada");
        question.setQuestion("¿la tierra es redonda?");

        when(repository.save(any(Question.class))).thenReturn(Mono.just(question));

        StepVerifier.create(createUseCase.apply(new QuestionDTO()))
                .expectNextMatches(questionDTO -> {
                    Assertions.assertEquals(questionDTO,question.getId());
                    return true;
                })
                .verifyComplete();

    }
}