package co.com.sofka.questions.usecases;


import co.com.sofka.questions.model.AnswerDTO;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@FunctionalInterface
public interface UpdateAnswer {

    Mono<String> apply(@Valid AnswerDTO answerDTO);
}
