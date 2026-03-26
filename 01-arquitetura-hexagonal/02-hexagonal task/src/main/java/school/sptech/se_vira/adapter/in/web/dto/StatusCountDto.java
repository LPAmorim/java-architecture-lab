package school.sptech.se_vira.adapter.in.web.dto;

import school.sptech.se_vira.domain.model.TaskStatus;

public record StatusCountDto(TaskStatus status, Long count) {
}