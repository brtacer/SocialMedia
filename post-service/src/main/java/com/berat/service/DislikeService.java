package com.berat.service;

import com.berat.model.Dislike;
import com.berat.model.Like;
import com.berat.repository.IDislikeRepository;
import com.berat.repository.ILikeRepository;
import com.berat.utility.ServiceManager;
import org.springframework.stereotype.Service;

@Service
public class DislikeService extends ServiceManager<Dislike,String> {
    private final IDislikeRepository dislikeRepository;

    public DislikeService(IDislikeRepository dislikeRepository) {
        super(dislikeRepository);
        this.dislikeRepository = dislikeRepository;
    }
}
