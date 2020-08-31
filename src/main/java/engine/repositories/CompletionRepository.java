package engine.repositories;

import engine.models.Completion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompletionRepository extends PagingAndSortingRepository<Completion, Long> {

    Page<Completion> findAllByUserId(Long userId, Pageable pageable);

}
