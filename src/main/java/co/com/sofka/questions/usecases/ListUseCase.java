package co.com.sofka.questions.usecases;

import co.com.sofka.questions.model.QuestionDTO;
import co.com.sofka.questions.reposioties.QuestionRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.Supplier;

@Service
@Validated
public class ListUseCase implements Supplier<Flux<QuestionDTO>> {
    private final QuestionRepository questionRepository;
    private final MapperUtils mapperUtils;

    public ListUseCase(MapperUtils mapperUtils, QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
        this.mapperUtils = mapperUtils;
    }

    @Override
    public Flux<QuestionDTO> get() {
        return questionRepository.findAll()
                .map(mapperUtils.mapEntityToQuestion());
    }

    public Flux<QuestionDTO> getPage(int page){
        Pageable pageable = PageRequest.of(page, 10);
        return questionRepository.findAllByIdNotNullOrderByIdAsc(pageable)
                .map(mapperUtils.mapEntityToQuestion());
    }

    public Mono<Long> getTotalQuestions() {
        return questionRepository.findAll().count();
    }

    public Mono<Integer> getTotalPages() {
        return questionRepository.count().map(count ->(int) Math.ceil(count/10D));
    }

}
