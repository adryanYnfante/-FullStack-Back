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

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CreateUseCaseTest {
    @Mock
    QuestionRepository repository;
    @SpyBean
    CreateUseCase createUseCase;
    @Autowired
    MapperUtils mapperUtils = new MapperUtils();

    @Test
    public void CreateUseCaseTestPass() {

        Question question = new Question();

        QuestionDTO dto = new QuestionDTO(
                "myback",
                "prueba",
                "dasdasdf",
                "sdadasd",
                "math");

        question.setId(dto.getId());
        question.setUserId(dto.getUserId());
        question.setQuestion(dto.getQuestion());
        question.setType(dto.getType());
        question.setCategory(dto.getCategory());

        Mockito.when(repository.save(question))
                .thenReturn(Mono.just(mapperUtils
                        .mapperToQuestion(question.getId())
                        .apply(dto)));

        StepVerifier.create(createUseCase.apply(dto))
                .equals("myback");
    }
}