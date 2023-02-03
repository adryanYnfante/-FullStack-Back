package co.com.sofka.questions.usecases;

import co.com.sofka.questions.collections.Question;
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

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UpdateUseCaseTest {

    @SpyBean
    private UpdateUseCase updateUseCase;
    @Autowired
    private MapperUtils mapperUtils;
    @Mock
    QuestionRepository questionRepository;

    @Test
    public void updateUseCaseTest(){
        QuestionDTO questionDto = new QuestionDTO("aaaa-aaaa","xxxx-xxxx","¿Que es java?","tech","software");
        Question question = mapperUtils.mapperToQuestion(questionDto.getId()).apply(questionDto);
        Mono<Question> questionMono = Mono.just(question);
        when(questionRepository.save(question)).thenReturn(questionMono);

        StepVerifier.create(updateUseCase.apply(questionDto))
                .expectNextMatches(quest->{
                    assert quest.equals(questionDto.getId());
                    return true;
                })
                .verifyComplete();

    }

}