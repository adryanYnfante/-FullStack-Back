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
class CreateUseCaseTest {
    @SpyBean
    private CreateUseCase createUseCase;
    @Autowired
    private MapperUtils mapperUtils;
    @Mock
    QuestionRepository questionRepository;
    @Test
    public void CreateUseCaseTestPass(){
        Question question = new Question();
        QuestionDTO questioDto = new QuestionDTO("1","¿Color favorito?","any","any");
        question.setId(questioDto.getId());
        question.setUserId(questioDto.getUserId());
        question.setQuestion(questioDto.getQuestion());
        question.setType(questioDto.getType());
        question.setCategory(questioDto.getCategory());

        Mockito.when(questionRepository.save(question))
                .thenReturn(Mono.just(
                        mapperUtils.mapperToQuestion(question.getId())
                                .apply(questioDto)
                ));

        StepVerifier.create(createUseCase.apply(questioDto))
                .equals("1");

    }

}