package co.com.sofka.questions.usecases;

import co.com.sofka.questions.collections.Question;
import co.com.sofka.questions.model.QuestionDTO;
import co.com.sofka.questions.reposioties.QuestionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.SpyBean;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

class CreateUseCaseTest {
    @Mock
    QuestionRepository repository;

    @Autowired
    MapperUtils mapperUtils;
    @SpyBean
    CreateUseCase createUseCase;

    @Test
    void getValidationTest() {
        //arrange
        var question = new Question();
        question.setId("aaaa-aaaa");
        question.setUserId("xxxx-xxxx");
        question.setType("tech");
        question.setCategory("software");
        question.setQuestion("¿Que es java?");
        var questionDTO = new QuestionDTO(question.getId(),question.getUserId(), question.getQuestion(), question.getType(), question.getCategory());
        Mockito.when(repository.save(question)).thenReturn(Mono.just(mapperUtils.mapperToQuestion(question.getId()).apply(questionDTO)));

        //assert
        StepVerifier.create(createUseCase.apply(questionDTO))
                .expectNextMatches( x -> {
                    assert x.equals("aaaa-aaaa");
                    return true;
                }).verifyComplete();


        //act
        Mockito.verify(repository).save(question);
  }
}