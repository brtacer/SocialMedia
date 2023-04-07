package com.berat.repository;

import com.berat.model.Follow;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IFollowRepository extends MongoRepository<Follow,String> {
    Optional<Follow> findByUserIdAndFollowerId(String userId,String followerId);
}
