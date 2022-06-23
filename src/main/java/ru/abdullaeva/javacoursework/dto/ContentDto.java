package ru.abdullaeva.javacoursework.dto;

import lombok.Data;
import ru.abdullaeva.javacoursework.model.base.Page;

import java.util.List;
import java.util.UUID;

@Data
public class ContentDto {
    private UUID contentGuid;
    List<Page> pagesList;
}
