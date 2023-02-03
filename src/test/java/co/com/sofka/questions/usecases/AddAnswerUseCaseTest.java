package co.com.sofka.questions.usecases;

import co.com.sofka.questions.collections.Answer;
import co.com.sofka.questions.model.AnswerDTO;
import co.com.sofka.questions.model.QuestionDTO;
import co.com.sofka.questions.reposioties.AnswerRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import co.com.sofka.questions.collections.Question;
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
class AddAnswerUseCaseTest {
    @Mock
    AnswerRepository repository;
    @SpyBean
    private AddAnswerUseCase addAnswerUseCase;
    @SpyBean
    private GetUseCase getUseCase;
    @Autowired
    private MapperUtils mapperUtils;
    @Test
    public void addAnswerHappyPass(){
        Question question = new Question();
        QuestionDTO questionDTO =new QuestionDTO("dasda","userid","hola","suma","math");
        question.setId(questionDTO.getId());
        question.setUserId(questionDTO.getUserId());
        question.setQuestion(questionDTO.getQuestion());
        question.setType(questionDTO.getType());
        question.setCategory(questionDTO.getCategory());

        List<AnswerDTO> answersDTO = new ArrayList<>();

        Answer answer = new Answer();
        AnswerDTO    answerDTO= new AnswerDTO(
                "dasda",
                "myback",
                "userid",
                "dasdas",
                4);

        answer.setQuestionId(answerDTO.getQuestionId());
        answer.setUserId(answerDTO.getUserId());
        answer.setAnswer(answerDTO.getAnswer());
        answer.setPosition(answerDTO.getPosition());

        answersDTO.add(answerDTO);

        questionDTO.setAnswers(answersDTO);

        Mockito.when(getUseCase.apply(answerDTO.getQuestionId())).thenReturn(Mono.just(questionDTO));
        Mockito.when(repository.save(answer)).thenReturn(Mono.just(mapperUtils.mapperToAnswer(answer.getId()).apply(answerDTO)));

        StepVerifier.create(addAnswerUseCase.apply(answerDTO))
                .expectNextMatches(MonoQ -> {
                    assert MonoQ.getId().equals("dasda");
                    assert MonoQ.getUserId().equals("userid");
                    assert MonoQ.getCategory().equals("math");
                    assert MonoQ.getQuestion().equals("hola");
                    assert MonoQ.getType().equals("suma");
                    assert MonoQ.getAnswers().contains(answerDTO);
                    System.out.println(MonoQ.toString());
                    System.out.println(MonoQ.getAnswers().toString());
                    return true;
                })
                .verifyComplete();

    }
}