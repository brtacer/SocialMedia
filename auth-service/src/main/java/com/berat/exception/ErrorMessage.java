package com.berat.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
public class ErrorMessage {

    private int code;
    private String message;
    private List<String> field;
    @Builder.Default
    private LocalDateTime dateTime=LocalDateTime.now();
}
