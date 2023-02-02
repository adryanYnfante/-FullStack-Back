package co.com.sofka.questions.usecases;

import co.com.sofka.questions.collections.Answer;
import co.com.sofka.questions.collections.Question;
import co.com.sofka.questions.model.AnswerDTO;
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

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class GetUseCaseTest {
    @SpyBean
    private AddAnswerUseCase addAnswerUseCase;

    @SpyBean
    private GetUseCase getUseCase;

    @Autowired
    private MapperUtils mapperUtils;

    @Mock
    QuestionRepository questionRepository;

    @Test
    public void addAnswerHappyPass() {
        Question question = new Question();
        QuestionDTO questionDTO = new QuestionDTO("300", "julian2345", "accion", "accion", "accion");
        question.setId(questionDTO.getId());
        question.setUserId(questionDTO.getUserId());
        question.setQuestion(questionDTO.getQuestion());
        question.setType(questionDTO.getType());
        question.setCategory(questionDTO.getCategory());

        List<AnswerDTO> answersDTO = new ArrayList<>();

        Answer answer = new Answer();
        AnswerDTO answerDTO = new AnswerDTO("300", "julian2345", "super peli", 5);

        answer.setQuestionId(answerDTO.getQuestionId());
        answer.setUserId(answerDTO.getUserId());
        answer.setAnswer(answerDTO.getAnswer());
        answer.setPosition(answerDTO.getPosition());

        answersDTO.add(answerDTO);

        questionDTO.setAnswers(answersDTO);

        Mockito.when(getUseCase.apply(answerDTO.getQuestionId())).thenReturn(Mono.just(questionDTO));
        //Mockito.when(questionRepository.save(question)).thenReturn(Mono.just(mapperUtils.mapperToQuestion(questionDTO.getId()).apply(questionDTO)));

        StepVerifier.create(getUseCase.apply(questionDTO.getId()))
                .expectNextMatches(MonoQ -> {

                    System.out.println(MonoQ.toString());
                    return true;
                })
                .verifyComplete();
    }

}