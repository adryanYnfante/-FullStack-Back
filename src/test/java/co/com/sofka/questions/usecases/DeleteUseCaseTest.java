package co.com.sofka.questions.usecases;



import co.com.sofka.questions.collections.Answer;
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

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class DeleteUseCaseTest {
    @SpyBean
    DeleteUseCase deleteUseCase;

    @Autowired
    private MapperUtils mapperUtils;

    @Mock
    QuestionRepository questionRepository;
    @Mock
    AnswerRepository answerRepository;
    @Test
    public void deleteUseCaseTest(){

        QuestionDTO questioDto = new QuestionDTO("xxx"
                ,"usu2"
                ,"question"
                ,"type"
                ,"category");


        AnswerDTO answerDTO = new AnswerDTO("idAnswer"
                ,"xxx"
                ,"idUser"
                ,"answer",
                3);


        List<AnswerDTO> answerDTOs = new ArrayList<>();
        answerDTOs.add(answerDTO);

        questioDto.setAnswers(answerDTOs);

        Question question = mapperUtils.mapperToQuestion("xxx").apply(questioDto);


        Mockito.when(questionRepository.deleteById(question.getId())).thenReturn(Mono.empty());
        Mockito.when(answerRepository.deleteByQuestionId(question.getId())).thenReturn(Mono.empty());

        StepVerifier.create(deleteUseCase.apply(question.getId()))
                .expectNext()
                .verifyComplete();


    }

}