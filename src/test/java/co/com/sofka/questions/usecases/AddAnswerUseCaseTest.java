package co.com.sofka.questions.usecases;

import co.com.sofka.questions.collections.Answer;
import co.com.sofka.questions.collections.Question;
import co.com.sofka.questions.model.AnswerDTO;
import co.com.sofka.questions.model.QuestionDTO;
import co.com.sofka.questions.reposioties.AnswerRepository;
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
        QuestionDTO questionDTO =new QuestionDTO("300","132838","Es buena","abierta","peliculas");
        question.setId(questionDTO.getId());
        question.setUserId(questionDTO.getUserId());
        question.setQuestion(questionDTO.getQuestion());
        question.setType(questionDTO.getType());
        question.setCategory(questionDTO.getCategory());

        List<AnswerDTO> answersDTO = new ArrayList<>();

        Answer answer = new Answer();
        AnswerDTO    answerDTO= new AnswerDTO("300","132838","ok",5);

        answer.setQuestionId(answerDTO.getQuestionId());
        answer.setUserId(answerDTO.getUserId());
        answer.setAnswer(answerDTO.getAnswer());
        answer.setPosition(answerDTO.getPosition());

        answersDTO.add(answerDTO);

        questionDTO.setAnswers(answersDTO);

        Mockito.when(getUseCase.apply(answerDTO.getQuestionId())).thenReturn(Mono.just(questionDTO));
        Mockito.when(answerRepository.save(answer)).thenReturn(Mono.just(mapperUtils.mapperToAnswer(answer.getId()).apply(answerDTO)));
        StepVerifier.create(addAnswerUseCase.apply(answerDTO))
                .expectNextMatches(MonoQ -> {
                    assert  MonoQ.getId().equals("300");
                    assert MonoQ.getUserId().equals("132838");
                    assert MonoQ.getCategory().equals("peliculas");
                    assert MonoQ.getQuestion().equals("Es buena");
                    assert MonoQ.getType().equals("abierta");
                    assert MonoQ.getAnswers().contains(answerDTO);
                    System.out.println(MonoQ.toString());
                    System.out.println(MonoQ.getAnswers().toString());
                    return true;
                })
                .verifyComplete();

    }

}