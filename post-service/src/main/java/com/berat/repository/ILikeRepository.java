package com.berat.repository;

import com.berat.model.Like;
import com.berat.model.Post;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ILikeRepository extends MongoRepository<Like,String> {
}
