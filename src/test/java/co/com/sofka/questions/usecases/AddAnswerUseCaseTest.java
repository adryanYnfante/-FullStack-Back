package co.com.sofka.questions.usecases;

import co.com.sofka.questions.collections.Answer;
import co.com.sofka.questions.collections.Question;
import co.com.sofka.questions.model.AnswerDTO;
import co.com.sofka.questions.model.QuestionDTO;
import co.com.sofka.questions.reposioties.AnswerRepository;

import org.junit.jupiter.api.Test;
;
import org.mockito.*;

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
        QuestionDTO questionDTO =new QuestionDTO("idQuestion","idUser","question","type","category");

        List<AnswerDTO> answerDtos = new ArrayList<>();
        AnswerDTO answerDTO= new AnswerDTO("idQuestion","xxx","res",3);
        Answer answer = mapperUtils.mapperToAnswer(null).apply(answerDTO);

        answerDtos.add(answerDTO);
        questionDTO.setAnswers(answerDtos);

        Mockito.when(getUseCase.apply(answerDTO.getQuestionId())).thenReturn(Mono.just(questionDTO));
        Mockito.when(answerRepository.save(answer)).thenReturn(Mono.just(mapperUtils.mapperToAnswer(null).apply(answerDTO)));

        StepVerifier.create(addAnswerUseCase.apply(answerDTO))
                .expectNextMatches(questionDto -> {
                    assert  questionDto.getId().equals("idQuestion");
                    assert questionDto.getQuestion().equals("question");
                    assert questionDto.getAnswers().contains(answerDTO);
                    return true;
                })
                .verifyComplete();

    }
}