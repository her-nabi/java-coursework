package ru.abdullaeva.javacoursework.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.abdullaeva.javacoursework.dto.ContentDto;
import ru.abdullaeva.javacoursework.dto.ContentDtoForAms;
import ru.abdullaeva.javacoursework.dto.ContentDtoForCds;
import ru.abdullaeva.javacoursework.dto.GuidContentDto;
import ru.abdullaeva.javacoursework.mappers.ContentMapper;
import ru.abdullaeva.javacoursework.mappers.ContentMapperForAms;
import ru.abdullaeva.javacoursework.mappers.ContentMapperForCds;
import ru.abdullaeva.javacoursework.model.base.Content;
import ru.abdullaeva.javacoursework.service.interf.ContentService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/content")
@Tag(name = "Content controller")
public class ContentController {
    private final ContentService contentService;
    private final ContentMapper contentMapper;
    private final ContentMapperForAms contentMapperForAms;
    private final ContentMapperForCds contentMapperForCds;

    @Autowired
    public ContentController(ContentService contentService, ContentMapper contentMapper, ContentMapperForAms contentMapperForAms, ContentMapperForCds contentMapperForCds) {
        this.contentService = contentService;
        this.contentMapper = contentMapper;
        this.contentMapperForAms = contentMapperForAms;
        this.contentMapperForCds = contentMapperForCds;
    }

    @PostMapping("/save")
    public void save(@RequestBody List<ContentDto> contents) {
        List<Content> contents1 = contentMapper.toContentList(contents);
        contentService.save(contents1);

    }

    @GetMapping()
    public List<ContentDto> getAll() {
        List<Content> allContent = contentService.getAllContent();
        return contentMapper.toDtoList(allContent);
    }

    @GetMapping("/ams")
    public List<ContentDtoForAms> getAllForAms() {
        List<Content> allContent = contentService.getAllContent();
        contentMapperForAms.contentListToContentDtoList(allContent);

        return contentMapperForAms.contentListToContentDtoList(allContent);
    }
    @GetMapping("/cds")
    public List<ContentDtoForCds> getAllForCds() {
        List<Content> allContent = contentService.getAllContent();
        contentMapperForCds.contentListToContentDtoList(allContent);

        return contentMapperForCds.contentListToContentDtoList(allContent);
    }

    @PutMapping("/publish")
    public void changeState() {
        contentService.changeState();

    }

    @PutMapping("/publish/list")
    public void changeState(@RequestBody List<GuidContentDto> listGuidContents) {
        contentService.changeState(listGuidContents);
    }

    @PutMapping("/publish/{id}")
    public void changeState(@PathVariable UUID id) {
        contentService.changeState(id);
    }

}
