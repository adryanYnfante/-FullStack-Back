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

    @SpyBean
    private CreateUseCase createUseCase;
    @Autowired
    private MapperUtils mapperUtils;

    @Mock
    QuestionRepository questionRepository;


    @Test
    public void CreateUseCaseTestPass(){
        Question question = new Question();
        QuestionDTO questionDTO =new QuestionDTO("300","julian2345","accion","accion","accion");
        question.setId(questionDTO.getId());
        question.setUserId(questionDTO.getUserId());
        question.setQuestion(questionDTO.getQuestion());
        question.setType(questionDTO.getType());
        question.setCategory(questionDTO.getCategory());

        Mockito.when(questionRepository.save(question))
                .thenReturn(Mono.just(mapperUtils
                        .mapperToQuestion(question.getId())
                        .apply(questionDTO)));

        StepVerifier.create(createUseCase.apply(questionDTO))
                .equals("300");

}}