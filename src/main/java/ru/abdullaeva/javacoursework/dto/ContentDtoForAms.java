package ru.abdullaeva.javacoursework.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.Set;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ContentDtoForAms {

    private UUID contentGuid;
    private Set<PageDto> pages;

}
