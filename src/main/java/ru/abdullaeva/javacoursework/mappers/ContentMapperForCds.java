package ru.abdullaeva.javacoursework.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.abdullaeva.javacoursework.dto.ContentDtoForCds;
import ru.abdullaeva.javacoursework.model.base.Content;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ContentMapperForCds {
    @Mapping(source = "id", target = "contentGuid")
    ContentDtoForCds contentToContentDto(Content content);

    @Mapping(source = "contentGuid", target = "id")
    Content contentDtoToContent(ContentDtoForCds contentDto);

    @Mapping(source = "id", target = "contentGuid")
    List<ContentDtoForCds> contentListToContentDtoList(List<Content> content);

    @Mapping(source = "contentGuid", target = "id")
    List<Content> contentDtoListToContent(List<ContentDtoForCds> contentDto);


}
