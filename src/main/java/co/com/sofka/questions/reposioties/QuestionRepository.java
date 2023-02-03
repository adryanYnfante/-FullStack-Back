package co.com.sofka.questions.reposioties;

import co.com.sofka.questions.collections.Question;
<<<<<<< HEAD
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.data.repository.reactive.ReactiveSortingRepository;
=======
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
>>>>>>> 48dfb9c47282822683662b4a55e09805a5855c33
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
<<<<<<< HEAD
public interface QuestionRepository extends ReactiveSortingRepository<Question, String> {
    Flux<Question> findByUserId(String userId);

    Flux<Question> findAllBy(final Pageable page);
=======
public interface QuestionRepository extends ReactiveCrudRepository<Question, String> {
    Flux<Question> findByUserId(String userId);
>>>>>>> 48dfb9c47282822683662b4a55e09805a5855c33
}
