package co.com.sofka.questions.usecases;


import co.com.sofka.questions.model.QuestionDTO;
import co.com.sofka.questions.reposioties.QuestionRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Service
@Validated
public class ListPagedUseCase {

        private final QuestionRepository questionRepository;
        private final MapperUtils mapperUtils;

        public ListPagedUseCase(MapperUtils mapperUtils, QuestionRepository questionRepository) {
            this.questionRepository = questionRepository;
            this.mapperUtils = mapperUtils;
        }

    public Flux<QuestionDTO> get(int page) {
        Pageable pageable = PageRequest.of(page,4);
        return questionRepository.findAllByIdNotNullOrderByIdAsc(pageable)
                .map(mapperUtils.mapEntityToQuestion());
    }

    public Mono<Long> getTotalQuestions() {
        return questionRepository.findAll().count();
    }

    public Mono<Integer> getTotalPages(){
         return  questionRepository.count().map(count ->(int) Math.ceil(count/4D));
    }
}
