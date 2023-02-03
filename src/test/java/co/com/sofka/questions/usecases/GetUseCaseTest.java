package co.com.sofka.questions.usecases;

import co.com.sofka.questions.collections.Answer;
import co.com.sofka.questions.collections.Question;
import co.com.sofka.questions.model.AnswerDTO;
import co.com.sofka.questions.model.QuestionDTO;
import co.com.sofka.questions.reposioties.AnswerRepository;
import co.com.sofka.questions.reposioties.QuestionRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class GetUseCaseTest {

    @SpyBean
    private GetUseCase getUseCase;
    @MockBean
    QuestionRepository questionRepository;
    @MockBean
    AnswerRepository answerRepository;
    @Autowired
    private MapperUtils mapperUtils;

    @Test
    public void GetUseCaseTestHappyPass() {

        Question question = new Question();

        QuestionDTO questionDTO =new QuestionDTO(
                "sofka",
                "userid",
                "prueba",
                "test",
                "math");

        question.setId(questionDTO.getId());
        question.setUserId(questionDTO.getUserId());
        question.setQuestion(questionDTO.getQuestion());
        question.setType(questionDTO.getType());
        question.setCategory(questionDTO.getCategory());

        List<AnswerDTO> answersDTO = new ArrayList<>();

        Answer answer = new Answer();

        AnswerDTO    answerDTO= new AnswerDTO(
                "answerid",
                "sofka",
                "userid",
                "bueno",
                5);

        answer.setQuestionId(answerDTO.getQuestionId());
        answer.setUserId(answerDTO.getUserId());
        answer.setAnswer(answerDTO.getAnswer());
        answer.setPosition(answerDTO.getPosition());

        answersDTO.add(answerDTO);

        questionDTO.setAnswers(answersDTO);

        Mockito.when(questionRepository.findById(question.getId())).thenReturn(Mono.just(question));
        Mockito.when(answerRepository.findAllByQuestionId(question.getId())).thenReturn(Flux.just(answer));

        StepVerifier.create(getUseCase.apply(questionDTO.getId()))
                .expectNextMatches(questionMono -> {
                    assert questionMono.getId().equals("sofka");
                    assert questionMono.getUserId().equals("userid");
                    assert questionMono.getCategory().equals("math");
                    assert questionMono.getQuestion().equals("prueba");
                    assert questionMono.getType().equals("test");
                    assert questionMono.getAnswers().contains(answerDTO);
                    return true;
                })
                .verifyComplete();
    }
}