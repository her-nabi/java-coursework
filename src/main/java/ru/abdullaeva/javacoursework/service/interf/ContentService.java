package ru.abdullaeva.javacoursework.service.interf;


import ru.abdullaeva.javacoursework.dto.GuidContentDto;
import ru.abdullaeva.javacoursework.model.base.Content;

import java.util.List;
import java.util.UUID;

public interface ContentService {

    /**
     * Метод, который меняет статус контента на PUBLISHED,
     * после чего идет отправка контента в другие микросервисы.
     * @param id контента, статус которого необходимо поменять.
     */
    void changeState(UUID id);

    /**
     * Метод, который меняет статус контента на PUBLISHED,
     * после чего идет отправка контента в другие микросервисы.
     * @param listGuidContents список контента, у которого необходимо поменять статус.
     */
    void changeState(List<GuidContentDto> listGuidContents);

    /**
     * Метод, который меняет статус контента на PUBLISHED,
     * после чего идет отправка контента в другие микросервисы.
     */
    void changeState();

    /**
     * Метод, возвращающий весь контент.
     * @return список контента.
     */
    List<Content> getAllContent();

    /**
     * Сохранение контента.
     * @param contents список контента, который необходимо сохранить
     */
    void save(List<Content> contents);

}
