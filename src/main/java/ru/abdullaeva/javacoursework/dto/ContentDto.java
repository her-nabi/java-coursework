package ru.abdullaeva.javacoursework.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.abdullaeva.javacoursework.model.base.State;

import java.util.Set;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ContentDto {
    private UUID contentGuid;
    private Set<PageDto> pages;
    private String data;
    private State state;
}
