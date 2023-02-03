package co.com.sofka.questions.usecases;

import co.com.sofka.questions.collections.Question;
import co.com.sofka.questions.model.AnswerDTO;
import co.com.sofka.questions.model.QuestionDTO;
import co.com.sofka.questions.reposioties.QuestionRepository;
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

import static org.junit.jupiter.api.Assertions.*;

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
        QuestionDTO questionDto = new QuestionDTO("aaaa-aaaa","xxxx-xxxx","¿Que es java?","tech","software");
        AnswerDTO answerDTO = new AnswerDTO("bbbb-bbbb","aaaa-aaaa","xxxx-xxxx","super peli",5);
        List<AnswerDTO> answerDTOs = new ArrayList<>();
        answerDTOs.add(answerDTO);
        questionDto.setAnswers(answerDTOs);

        Question question = mapperUtils.mapperToQuestion(questionDto.getId()).apply(questionDto);
        Mono<Question> questionDTOMono = Mono.just(question);
        when(questionRepository.findById(question.getId())).thenReturn(questionDTOMono);

        StepVerifier.create(getUseCase.apply(questionDto.getId()))
                .expectNextMatches(questDTO -> {
                    assert questDTO.getId().equals("aaaa-aaaa") ;
                    return true ;
                })
                .verifyComplete();
    }

}