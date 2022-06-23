package ru.abdullaeva.javacoursework.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.abdullaeva.javacoursework.dto.ContentDto;
import ru.abdullaeva.javacoursework.model.base.Content;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ContentMapper {

    @Mapping(source = "id", target = "contentGuid")
    List<ContentDto> contentToContentDto (List<Content> content);

    @Mapping(source = "id", target = "contentGuid")
    List<Content> contentDtoToContent (List<ContentDto> dto);
}
