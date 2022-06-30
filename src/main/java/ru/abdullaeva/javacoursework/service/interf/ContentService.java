package ru.abdullaeva.javacoursework.service.interf;


import ru.abdullaeva.javacoursework.dto.GuidContentDto;
import ru.abdullaeva.javacoursework.model.base.Content;

import java.util.List;
import java.util.UUID;

public interface ContentService {

    void changeState(UUID id);

    void changeState(List<GuidContentDto> listGuidContents);

    void changeState();

    List<Content> getAllContent();

    void save(List<Content> contents);

}
