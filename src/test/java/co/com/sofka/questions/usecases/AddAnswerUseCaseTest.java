package co.com.sofka.questions.usecases;

import co.com.sofka.questions.collections.Answer;
import co.com.sofka.questions.collections.Question;
import co.com.sofka.questions.model.AnswerDTO;
import co.com.sofka.questions.model.QuestionDTO;
import co.com.sofka.questions.reposioties.AnswerRepository;
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

        QuestionDTO questionDTO =new QuestionDTO("aaaa-aaaa","xxxx-xxxx","¿Que es java?","tech","software");
        List<AnswerDTO> answersDTO = new ArrayList<>();
        Answer answer = new Answer();
        AnswerDTO answerDTO= new AnswerDTO("bbbb-bbbb","aaaa-aaaa","xxxx-xxxx","super peli",5);
        answer = mapperUtils.mapperToAnswer(answerDTO.getId()).apply(answerDTO);
        answersDTO.add(answerDTO);
        questionDTO.setAnswers(answersDTO);

        when(getUseCase.apply(answerDTO.getQuestionId())).thenReturn(Mono.just(questionDTO));
        when(answerRepository.save(answer)).thenReturn(Mono.just(mapperUtils.mapperToAnswer(answerDTO.getId()).apply(answerDTO)));
        StepVerifier.create(addAnswerUseCase.apply(answerDTO))
                .expectNextMatches(MonoQ -> {
                    assert  MonoQ.getId().equals("aaaa-aaaa");
                    assert MonoQ.getUserId().equals("xxxx-xxxx");
                    assert MonoQ.getCategory().equals("software");
                    assert MonoQ.getQuestion().equals("¿Que es java?");
                    assert MonoQ.getType().equals("tech");
                    assert MonoQ.getAnswers().contains(answerDTO);
                    return true;
                })
                .verifyComplete();
    }
}