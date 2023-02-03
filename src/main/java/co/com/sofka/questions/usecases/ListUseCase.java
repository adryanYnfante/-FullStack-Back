package co.com.sofka.questions.usecases;

import co.com.sofka.questions.model.QuestionDTO;
import co.com.sofka.questions.reposioties.QuestionRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
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

   /* public Flux<QuestionDTO> getByPage(int pageNumber){
            PageRequest pageRequest = PageRequest.of(pageNumber, 3);
            return questionRepository.findAllBy(pageRequest);

}*/
    public  Flux<QuestionDTO> getPages(int pageNumber) {
        PageRequest pageRequest = PageRequest.of(pageNumber, 3);
        return questionRepository.findAllBy(pageRequest);
    }


    @Override
    public Flux<QuestionDTO> get() {
        return questionRepository.findAll()
                .map(mapperUtils.mapEntityToQuestion());
    }

}
