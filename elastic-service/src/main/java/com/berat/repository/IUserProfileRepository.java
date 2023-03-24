package com.berat.repository;

import com.berat.model.UserProfile;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUserProfileRepository extends ElasticsearchRepository<UserProfile,String> {
    Optional<UserProfile> findByAuthId(Long authId);
    Optional<UserProfile> findByUsernameIgnoreCase(String username);
}
