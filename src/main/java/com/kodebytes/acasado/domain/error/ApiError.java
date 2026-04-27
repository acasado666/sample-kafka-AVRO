package com.kodebytes.acasado.domain.error;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.List;


@Getter
@Setter
@Builder(access = AccessLevel.PUBLIC)
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ApiError {
    private String title;
    private Integer status;
    private String detail;
    private List<String> errors;
}