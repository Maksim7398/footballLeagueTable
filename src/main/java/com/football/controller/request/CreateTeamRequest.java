package com.football.controller.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateTeamRequest {
    @NotBlank(message = "name must not be blank")
    private String name;
}
