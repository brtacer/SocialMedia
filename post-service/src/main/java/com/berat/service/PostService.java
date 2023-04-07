package com.berat.service;

import com.berat.dto.request.CreatePostRequest;
import com.berat.mapper.IPostMapper;
import com.berat.model.Post;
import com.berat.repository.IPostRepository;
import com.berat.utility.ServiceManager;
import org.springframework.stereotype.Service;

@Service
public class PostService extends ServiceManager<Post,String> {
    private final IPostRepository postRepository;

    public PostService(IPostRepository postRepository) {
        super(postRepository);
        this.postRepository = postRepository;
    }

    public Post createPost(CreatePostRequest dto) {
        return save(IPostMapper.INSTANCE.toPost(dto));
    }
}
