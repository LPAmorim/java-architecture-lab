package school.sptech.se_vira.dto;

import school.sptech.se_vira.model.TaskStatus;

public record StatusCountDto(
    TaskStatus status,
    Long count
) {
} 
