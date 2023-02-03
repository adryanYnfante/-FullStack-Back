package co.com.sofka.questions.usecases;

import co.com.sofka.questions.collections.Answer;
import co.com.sofka.questions.collections.Question;
import co.com.sofka.questions.model.AnswerDTO;
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

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class DeleteUseCaseTest {
    @Mock
    QuestionRepository questionRepository;
    @Mock
    AnswerRepository answerRepository;
    @SpyBean
    private DeleteUseCase deleteUseCase;
    @Test
    public void DeleteUseCaseTestPass() {

        Question question = new Question();

        QuestionDTO qDTO = new QuestionDTO(
                "sofka",
                "userid",
                "prueba",
                "pregunta",
                "math");

        question.setId(qDTO.getId());
        question.setUserId(qDTO.getUserId());
        question.setQuestion(qDTO.getQuestion());
        question.setType(qDTO.getType());
        question.setCategory(qDTO.getCategory());

        List<AnswerDTO> answersDTO = new ArrayList<>();

        Answer answer = new Answer();

        AnswerDTO aDTO = new AnswerDTO(
                "answerid",
                "sofka",
                "userid",
                "divertido",
                5);

        answer.setQuestionId(aDTO.getQuestionId());
        answer.setUserId(aDTO.getUserId());
        answer.setAnswer(aDTO.getAnswer());
        answer.setPosition(aDTO.getPosition());

        answersDTO.add(aDTO);

        qDTO.setAnswers(answersDTO);

        Mockito.when(questionRepository.deleteById(question.getId())).thenReturn(Mono.empty());
        Mockito.when(answerRepository.deleteByQuestionId(question.getId())).thenReturn(Mono.empty());

        StepVerifier.create(deleteUseCase.apply(question.getId()))
                .expectNext()
                .verifyComplete();
    }
}