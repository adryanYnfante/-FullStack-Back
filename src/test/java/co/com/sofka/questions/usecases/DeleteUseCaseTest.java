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

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class DeleteUseCaseTest {
    @SpyBean
    private DeleteUseCase deleteUseCase;
    @Mock
    QuestionRepository questionRepository;

    @Mock
    AnswerRepository answerRepository;
    @Test
    void setDeleteHappyPass(){
        Question question = new Question();
        QuestionDTO questionDTO =new QuestionDTO("300","julian2345","accion","accion","accion");
        question.setId(questionDTO.getId());
        question.setUserId(questionDTO.getUserId());
        question.setQuestion(questionDTO.getQuestion());
        question.setType(questionDTO.getType());
        question.setCategory(questionDTO.getCategory());

        Mockito.when(questionRepository.deleteById(question.getId()))
                                .thenReturn(
                                   Mono.empty());
        Answer answer = new Answer();
        AnswerDTO answerDTO= new AnswerDTO("300","132838","ok",5);

        answer.setQuestionId(answerDTO.getQuestionId());
        answer.setUserId(answerDTO.getUserId());
        answer.setAnswer(answerDTO.getAnswer());
        answer.setPosition(answerDTO.getPosition());
        List<AnswerDTO> answersDTO = new ArrayList<>();
        answersDTO.add(answerDTO);

        questionDTO.setAnswers(answersDTO);
        Mockito.when(answerRepository.deleteByQuestionId(question.getId())).thenReturn(Mono.empty());
            StepVerifier.create(deleteUseCase.apply(questionDTO.getId()))
                    .expectNext()
                    .verifyComplete();
    }
}