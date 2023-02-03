package co.com.sofka.questions.usecases;

import co.com.sofka.questions.collections.Question;
import co.com.sofka.questions.model.AnswerDTO;
import co.com.sofka.questions.model.QuestionDTO;
import co.com.sofka.questions.reposioties.AnswerRepository;
import co.com.sofka.questions.reposioties.QuestionRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class DeleteUseCaseTest {
    @SpyBean
    DeleteUseCase deleteUseCase;

    @Autowired
    MapperUtils mapperUtils;
    @Mock
    QuestionRepository questionRepository;
    @Mock
    AnswerRepository answerRepository;

    @Test
    public void deleteUseCaseTest(){

        QuestionDTO questionDto = new QuestionDTO("1","oscardev","¿Color favorito?","any","any");
        AnswerDTO answerDTO = new AnswerDTO("1","1","oscardev","Azul",5);
        List<AnswerDTO> answerDTOs = new ArrayList<>();
        answerDTOs.add(answerDTO);
        questionDto.setAnswers(answerDTOs);
        Question question = mapperUtils.mapperToQuestion(questionDto.getId()).apply(questionDto);
        when(questionRepository.deleteById(question.getId())).thenReturn(Mono.empty());

        StepVerifier.create(deleteUseCase.apply(question.getId()))
                .expectNext()
                .verifyComplete();
    }

}