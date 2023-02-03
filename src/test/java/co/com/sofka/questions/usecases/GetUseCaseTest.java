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

        QuestionDTO questionDTO =new QuestionDTO("xxx","idUser","question","type","category");
        Question question = mapperUtils.mapperToQuestion("xxx").apply(questionDTO);

        List<AnswerDTO> answersDTO = new ArrayList<>();


        AnswerDTO    answerDTO= new AnswerDTO("5","pepe1","funciona",5);
        Answer answer = mapperUtils.mapperToAnswer(null).apply(answerDTO);


        answersDTO.add(answerDTO);

        questionDTO.setAnswers(answersDTO);


        Mockito.when(questionRepository.findById(question.getId())).thenReturn(Mono.just(question));
        Mockito.when(answerRepository.findAllByQuestionId(question.getId())).thenReturn(Flux.just(answer));
        StepVerifier.create(getUseCase.apply(questionDTO.getId()))
                .expectNextMatches(MonoQ -> {
                    assert  MonoQ.getId().equals("xxx");
                    assert MonoQ.getUserId().equals("idUser");
                    assert MonoQ.getQuestion().equals("question");
                    assert MonoQ.getAnswers().contains(answerDTO);
                    return true;
                })
                .verifyComplete();
    }

}




