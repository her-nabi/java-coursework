package ru.abdullaeva.javacoursework.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.abdullaeva.javacoursework.mappers.ContentMapper;
import ru.abdullaeva.javacoursework.model.base.Content;
import ru.abdullaeva.javacoursework.service.interf.ContentService;

import java.util.List;

@RestController
@RequestMapping("/content")
@Tag(name = "Content controller")
public class ContentController {
    private final ContentService contentService;
    private final ContentMapper contentMapper;

   @Autowired
   public ContentController(ContentService contentService, ContentMapper contentMapper) {
      this.contentService = contentService;
      this.contentMapper = contentMapper;
   }

   @GetMapping("/")
   public void changeState(@RequestBody List<Content> contents) {

   }



}
