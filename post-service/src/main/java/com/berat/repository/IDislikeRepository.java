package com.berat.repository;

import com.berat.model.Dislike;
import com.berat.model.Post;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IDislikeRepository extends MongoRepository<Dislike,String> {
}
