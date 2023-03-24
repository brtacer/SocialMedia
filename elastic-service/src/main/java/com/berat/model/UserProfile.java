package com.berat.model;

import com.berat.model.enums.EStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;


@SuperBuilder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "user_profile")
public class UserProfile extends BaseEntity {

    @Id
    private String id;
    private Long authId;
    private String username;
    private String email;
    private String phone;
    private String avatar;
    private String address;
    private String about;
    @Builder.Default
    private EStatus status=EStatus.PENDING;
}