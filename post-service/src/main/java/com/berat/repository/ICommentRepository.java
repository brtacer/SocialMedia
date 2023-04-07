package com.berat.repository;

import com.berat.model.Comment;
import com.berat.model.Post;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICommentRepository extends MongoRepository<Comment,String> {
}
