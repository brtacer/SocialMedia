package com.berat.manager;

import com.berat.dto.request.CreateUserRequest;
import com.berat.model.UserProfile;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.berat.constant.EndPoints.*;

@FeignClient(url = "http://localhost:7072/api/v1/user",decode404 = true,name = "elastic-userprofile")
public interface IUserManager {

    @GetMapping(FINDALL)
    public ResponseEntity<List<UserProfile>> findAll();

}
