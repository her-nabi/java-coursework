package ru.abdullaeva.javacoursework.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.abdullaeva.javacoursework.dto.ContentDto;
import ru.abdullaeva.javacoursework.dto.ContentDtoForAms;
import ru.abdullaeva.javacoursework.dto.ContentDtoForCds;
import ru.abdullaeva.javacoursework.mappers.ContentMapper;
import ru.abdullaeva.javacoursework.mappers.ContentMapperForAms;
import ru.abdullaeva.javacoursework.mappers.ContentMapperForCds;
import ru.abdullaeva.javacoursework.model.base.Content;
import ru.abdullaeva.javacoursework.service.interf.ContentService;
import ru.abdullaeva.javacoursework.service.interf.WebContentService;

import java.util.List;

@Service
@Slf4j
public class WebContentServiceImpl implements WebContentService {
    private final ContentService contentService;
    private final ContentMapper contentMapper;
    private final ContentMapperForAms contentMapperForAms;
    private final ContentMapperForCds contentMapperForCds;

    @Autowired
    public WebContentServiceImpl(ContentService contentService, ContentMapper contentMapper, ContentMapperForAms contentMapperForAms, ContentMapperForCds contentMapperForCds) {
        this.contentService = contentService;
        this.contentMapper = contentMapper;
        this.contentMapperForAms = contentMapperForAms;
        this.contentMapperForCds = contentMapperForCds;
    }

    @Override
    public void save(List<ContentDto> contentDtoList) {
         contentService.save(contentMapper.toContentList(contentDtoList));
    }

    @Override
    public List<ContentDto> getAllContent() {
        return contentMapper.toDtoList(contentService.getAllContent());
    }

    @Override
    public List<ContentDtoForAms> getAllContentForAms() {
        List<Content> contents = contentService.getAllContent();
        return contentMapperForAms.contentListToContentDtoList(contents);
    }

    @Override
    public List<ContentDtoForCds> getAllContentForCds() {
        List<Content> contents = contentService.getAllContent();
        return contentMapperForCds.contentListToContentDtoList(contents);
    }
}
