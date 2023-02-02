package co.com.sofka.questions.usecases;

import co.com.sofka.questions.collections.Question;
import co.com.sofka.questions.model.AnswerDTO;
import co.com.sofka.questions.model.QuestionDTO;
import co.com.sofka.questions.reposioties.AnswerRepository;
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
import static org.mockito.ArgumentMatchers.anyString;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class GetUseCaseTest {

    @SpyBean
    GetUseCase getUseCase;
    @Autowired
    MapperUtils mapperUtils;
    @Mock
    private QuestionRepository questionRepository;



    @Test
    public void getUseCaseTest(){
        QuestionDTO questionDto = new QuestionDTO("idQuestion","300","daniel","acción","acción");


        AnswerDTO answerDTO = new AnswerDTO("idAnswer","300","idUser","answer",5);


        List<AnswerDTO> answerDTOs = new ArrayList<>();
        answerDTOs.add(answerDTO);

        questionDto.setAnswers(answerDTOs);

        Question question = mapperUtils.mapperToQuestion("300").apply(questionDto);


        Mono<Question> questionDTOMono = Mono.just(question);

        Mockito.when(questionRepository.findById(anyString())).thenReturn(questionDTOMono);

        StepVerifier.create(getUseCase.apply("idQuestion"))
                .expectNextMatches(questDTO -> {
                   return questDTO.getUserId().equals("300") ;
                })
                .verifyComplete();
    }

}