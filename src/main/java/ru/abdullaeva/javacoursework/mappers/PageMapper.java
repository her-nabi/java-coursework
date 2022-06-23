package ru.abdullaeva.javacoursework.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import org.mapstruct.factory.Mappers;
import ru.abdullaeva.javacoursework.dto.PageDto;
import ru.abdullaeva.javacoursework.model.base.Page;

@Mapper(componentModel = "spring")
public interface PageMapper {

    @Mapping(source = "name", target = "pageName")
    PageDto pageToPageDto(Page page);

    @Mapping(source = "pageName", target = "name")
    Page pageDtoToPage(PageDto dto);
}
