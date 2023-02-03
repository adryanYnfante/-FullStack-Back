package co.com.sofka.questions.usecases;


import co.com.sofka.questions.collections.Question;
import co.com.sofka.questions.model.QuestionDTO;
import co.com.sofka.questions.reposioties.AnswerRepository;
import co.com.sofka.questions.reposioties.QuestionRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;


import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class DeleteUseCaseTest {

    @SpyBean
    private DeleteUseCase deleteUseCase;

    @SpyBean
    private GetUseCase getUseCase;

    @Mock
    QuestionRepository questionRepository;

    @Mock
    AnswerRepository answerRepository;

    @Test
    public void DeleteUseCaseTestPass(){

        Question question = new Question();
        QuestionDTO questionDTO = new QuestionDTO("5","A41","5x5","cerrada","matemática");


        Mockito.when(questionRepository.deleteById(questionDTO.getId())).thenReturn(Mono.empty());
        Mockito.when(answerRepository.deleteByQuestionId(questionDTO.getId())).thenReturn(Mono.empty());

        var result = deleteUseCase.apply(questionDTO.getId());

        StepVerifier.create(result).expectNext().verifyComplete();

    }

}