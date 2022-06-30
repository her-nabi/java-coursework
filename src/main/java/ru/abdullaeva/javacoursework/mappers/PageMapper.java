package ru.abdullaeva.javacoursework.mappers;

import org.apache.catalina.LifecycleState;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.abdullaeva.javacoursework.dto.PageDto;
import ru.abdullaeva.javacoursework.model.base.Page;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PageMapper {
    @Mapping(source = "name", target = "pageName")
    PageDto pageToPageDto(Page page);

    @Mapping(source = "pageName" , target = "name")
    Page pageDtoToPage(PageDto page);

    @Mapping(source = "name", target = "pageName")
    List<PageDto> pageListToPageDtoList(List<Page> list);

    @Mapping(source = "pageName" , target = "name")
    List<Page> pageDtoListToPageList (List<PageDto> list);
}
