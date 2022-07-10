package ru.abdullaeva.javacoursework.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.abdullaeva.javacoursework.dto.ContentDtoForAms;
import ru.abdullaeva.javacoursework.model.base.Content;

import java.util.List;

@Mapper(componentModel = "spring", uses = PageMapper.class)
public interface ContentMapperForAms {

    @Mapping(source = "id", target = "contentGuid")
    ContentDtoForAms contentToContentDto(Content content);

    @Mapping(source = "id", target = "contentGuid")
    List<ContentDtoForAms> contentListToContentDtoList(List<Content> content);

}

