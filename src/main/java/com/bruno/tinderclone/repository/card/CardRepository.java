package com.bruno.tinderclone.repository.card;

import com.bruno.tinderclone.model.Cards;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface CardRepository extends PagingAndSortingRepository<Cards, String> {

    Page<Cards> findAllByIdNot(String id, Pageable pageable);
    //@Query(value = "{'cars.name': ?0, 'employees.salary' : { $gt: ?1 }}")
    @Query(value = "{'_id' : ?0}", fields = "{'likes': 1}")
    Cards findAllLiked(ObjectId id);
    Optional<Cards> findById(String id);
    @Query(value = "{'_id' : {$nin : ?0}}")
    Page<Cards> fetchAvailableCards(List<String> filteredIds, Pageable pageable);
    @Query(value = "{'_id': {$in: ?0}, 'likes': {$in: ?1}}")
    List<Cards> fetchMatches(List<String> likedIds, List<String> userId);

}
