package ru.abdullaeva.javacoursework.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.abdullaeva.javacoursework.dto.ContentDto;
import ru.abdullaeva.javacoursework.dto.ContentDtoForAms;
import ru.abdullaeva.javacoursework.dto.ContentDtoForCds;
import ru.abdullaeva.javacoursework.dto.GuidContentDto;
import ru.abdullaeva.javacoursework.model.base.Content;
import ru.abdullaeva.javacoursework.service.interf.ContentService;
import ru.abdullaeva.javacoursework.service.interf.WebContentService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/content")
@Tag(name = "Content controller")
public class ContentController {

    private final ContentService contentService;
    private final WebContentService webContentService;

    @Autowired
    public ContentController(ContentService contentService, WebContentService webContentService) {
        this.contentService = contentService;
        this.webContentService = webContentService;
    }

    @Operation(summary = "Сохранить контент", description = "Сохранение в базу данных контента, полученного с фронта")
    @PostMapping("/save")
    public void save(@RequestBody List<ContentDto> contents) {
        webContentService.save(contents);
    }

    @Operation(summary = "Просмотр контента", description = "Получмть весь контент из базы данных")
    @GetMapping()
    public List<ContentDto> getAll() {
       return webContentService.getAllContent();
    }

    @Operation(summary = "Просмотр контента для ams", description = "Просмотр контента в формате для микросерфиса ams")
    @GetMapping("/ams")
    public List<ContentDtoForAms> getAllForAms() {
        return webContentService.getAllContentForAms();
    }

    @Operation(summary = "Просмотр контента для cds", description = "Просмотр контента в формате для микросервиса cds")
    @GetMapping("/cds")
    public List<ContentDtoForCds> getAllForCds() {
        return webContentService.getAllContentForCds();
    }

    @Operation(summary = "Публикация контента", description = "Публикует весь контент из базы данных, который еще не было опубликован")
    @PutMapping("/publish")
    public void changeState() {
        contentService.changeState();
    }

    @Operation(summary = "Публикация контента", description = "Публикует контент по списку с id")
    @PutMapping("/publish/list")
    public void changeState(@RequestBody List<GuidContentDto> listGuidContents) {
        contentService.changeState(listGuidContents);
    }

    @Operation(summary = "Публикация контента", description = "Публикует одну запись по id")
    @PutMapping("/publish/{id}")
    public void changeState(@PathVariable UUID id) {
        contentService.changeState(id);
    }

}
