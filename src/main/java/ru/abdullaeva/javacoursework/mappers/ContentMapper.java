package ru.abdullaeva.javacoursework.mappers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.abdullaeva.javacoursework.dto.ContentDto;
import ru.abdullaeva.javacoursework.dto.PageDto;
import ru.abdullaeva.javacoursework.exception.NoSuchPageException;
import ru.abdullaeva.javacoursework.model.base.Content;
import ru.abdullaeva.javacoursework.model.base.Page;
import ru.abdullaeva.javacoursework.model.base.State;
import ru.abdullaeva.javacoursework.repository.PageRepository;

import java.util.*;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ContentMapper {

    private final PageRepository pageRepository;

    public List<ContentDto> toDtoList(List<Content> content) {
        if (content == null) {
            return null;
        }

        List<ContentDto> result = new ArrayList<>(content.size());

        for (var item : content) {
            result.add(toDto(item));
        }

        return result;
    }

    public List<Content> toContentList(List<ContentDto> contentDto) {

        if (contentDto == null) {
            return null;
        }

        List<Content> result = new ArrayList<>(contentDto.size());

        for (var currentContent : contentDto) {
            Content content = new Content();
            Set<Page> pages = new HashSet<>();

            for (var pageDto : currentContent.getPages()) {
                List<Content> contents = new ArrayList<>();
                Optional<Page> pageFindByName = pageRepository.findByName(pageDto.getPageName());

                Page page;
                if (pageFindByName.isPresent()) {
                    page = pageFindByName.get();
                    contents.addAll(page.getContents());
                } else {
                    throw new NoSuchPageException();
                }

                contents.add(content);
                page.setContents(contents);
                pages.add(page);
            }

            content.setId(currentContent.getContentGuid());
            content.setPages(pages);
            content.setData(currentContent.getData());
            content.setState(State.DRAFT);
            result.add(content);
        }

        return result;
    }

    private ContentDto toDto(Content content) {
        if (content == null) {
            return null;
        }

        return ContentDto.builder()
                .contentGuid(content.getId())
                .pages(content.getPages().stream().map(page -> PageDto.builder()
                                .pageName(page.getName())
                                .build())
                        .collect(Collectors.toSet())
                )
                .data(content.getData())
                .state(content.getState())
                .build();
    }

}
