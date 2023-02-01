package co.com.sofka.questions.reposioties;

import co.com.sofka.questions.collections.Question;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.data.repository.reactive.ReactiveSortingRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface QuestionRepository extends ReactiveSortingRepository<Question, String> {
    Flux<Question> findByUserId(String userId);

    Flux<Question> findAllBy(final Pageable page);
}
