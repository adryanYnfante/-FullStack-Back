package co.com.sofka.questions.usecases;

import co.com.sofka.questions.collections.Answer;
import co.com.sofka.questions.collections.Question;
import co.com.sofka.questions.model.AnswerDTO;
import co.com.sofka.questions.model.QuestionDTO;
import co.com.sofka.questions.reposioties.AnswerRepository;
import co.com.sofka.questions.reposioties.QuestionRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.ArrayList;
import java.util.List;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AddAnswerUseCaseTest {
    @SpyBean
    private AddAnswerUseCase addAnswerUseCase;

    @SpyBean
    private GetUseCase getUseCase;

    @Autowired
    private MapperUtils mapperUtils;

    @Mock
    AnswerRepository answerRepository;

    @Test
    public void addAnswerHappyPass(){
        Question question = new Question();
        QuestionDTO questionDTO =new QuestionDTO("300","julian2345","accion","accion","accion");
        question.setId(questionDTO.getId());
        question.setUserId(questionDTO.getUserId());
        question.setQuestion(questionDTO.getQuestion());
        question.setType(questionDTO.getType());
        question.setCategory(questionDTO.getCategory());

        List<AnswerDTO> answersDTO = new ArrayList<>();

        Answer answer = new Answer();
        AnswerDTO    answerDTO= new AnswerDTO("300","julian2345","super peli",5);

        answer.setQuestionId(answerDTO.getQuestionId());
        answer.setUserId(answerDTO.getUserId());
        answer.setAnswer(answerDTO.getAnswer());
        answer.setPosition(answerDTO.getPosition());

        answersDTO.add(answerDTO);

        questionDTO.setAnswers(answersDTO);

        Mockito.when(getUseCase.apply(answerDTO.getQuestionId())).thenReturn(Mono.just(questionDTO));
        Mockito.when(answerRepository.save(answer)).thenReturn(Mono.just(mapperUtils.mapperToAnswer().apply(answerDTO)));
        StepVerifier.create(addAnswerUseCase.apply(answerDTO))
                .expectNextMatches(MonoQ -> {
                    assert  MonoQ.getId().equals("300");
                    assert MonoQ.getUserId().equals("julian2345");
                    assert MonoQ.getCategory().equals("accion");
                    assert MonoQ.getQuestion().equals("accion");
                    assert MonoQ.getType().equals("accion");
                    assert MonoQ.getAnswers().contains(answerDTO);
                    System.out.println(MonoQ.toString());
                    System.out.println(MonoQ.getAnswers().toString());
                    return true;
                })
                .verifyComplete();

    }
}