package ru.abdullaeva.javacoursework.service.interf;

import ru.abdullaeva.javacoursework.dto.ContentDto;
import ru.abdullaeva.javacoursework.dto.ContentDtoForAms;
import ru.abdullaeva.javacoursework.dto.ContentDtoForCds;

import java.util.List;

public interface WebContentService {

    /**
     * Метод является service-прослойкой для преобразования dto в entity.
     * Сохранение контента.
     */
    void save(List<ContentDto> contentDtoList);

    /**
     * Метод является service-прослойкой для преобразования entity в dto.
     * @return список всего контента в виде ContentDto.
     */
    List<ContentDto> getAllContent();

    /**
     * Метод является service-прослойкой для преобразования entity в dto.
     * @return список всего контента в виде ContentDtoForAms.
     */
    List<ContentDtoForAms> getAllContentForAms();

    /**
     * Метод является service-прослойкой для преобразования entity в dto.
     * @return список всего контента в виде ContentDtoForCds.
     */
    List<ContentDtoForCds> getAllContentForCds();

}
