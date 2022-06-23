package ru.abdullaeva.javacoursework.service.interf;


import ru.abdullaeva.javacoursework.model.base.Content;

import java.util.List;
import java.util.UUID;

public interface ContentService  {

    void changeState(UUID id);

    List<Content> getAllContent();

}
