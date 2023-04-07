package com.berat.service;

import com.berat.model.Comment;
import com.berat.model.Like;
import com.berat.repository.ICommentRepository;
import com.berat.repository.ILikeRepository;
import com.berat.utility.ServiceManager;
import org.springframework.stereotype.Service;

@Service
public class LikeService extends ServiceManager<Like,String> {
    private final ILikeRepository likeRepository;

    public LikeService(ILikeRepository likeRepository) {
        super(likeRepository);
        this.likeRepository = likeRepository;
    }
}
