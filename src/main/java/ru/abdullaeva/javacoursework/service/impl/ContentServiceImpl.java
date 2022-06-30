package ru.abdullaeva.javacoursework.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.abdullaeva.javacoursework.dto.ContentDtoForAms;
import ru.abdullaeva.javacoursework.dto.ContentDtoForCds;
import ru.abdullaeva.javacoursework.dto.GuidContentDto;
import ru.abdullaeva.javacoursework.mappers.ContentMapperForAms;
import ru.abdullaeva.javacoursework.mappers.ContentMapperForCds;
import ru.abdullaeva.javacoursework.model.base.Content;
import ru.abdullaeva.javacoursework.model.base.State;
import ru.abdullaeva.javacoursework.repository.ContentRepository;
import ru.abdullaeva.javacoursework.service.interf.ContentService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class ContentServiceImpl implements ContentService {

    private final String URI_FOR_AMS = "http://localhost:8080/ams/api/v1/content/save";
    private final String URI_FOR_CDS = "http://localhost:8080/cds/api/v1/content/save";
    private final ContentRepository contentRepository;
    private final RestTemplate restTemplate;
    private final ContentMapperForAms contentMapperForAms;
    private final ContentMapperForCds contentMapperForCds;

    @Autowired
    public ContentServiceImpl(ContentRepository contentRepository, RestTemplate restTemplate, ContentMapperForAms contentMapperForAms, ContentMapperForCds contentMapperForCds) {
        this.contentRepository = contentRepository;
        this.restTemplate = restTemplate;
        this.contentMapperForAms = contentMapperForAms;
        this.contentMapperForCds = contentMapperForCds;
    }

    @Override
    public void changeState(UUID id) {
        Optional<Content> contentOptional = contentRepository.findById(id);
        if (contentOptional.isPresent()) {
            Content currentElement = stateIsPublished(contentOptional.get());
            if (currentElement.getId() != null) {
                contentRepository.save(currentElement);
                sendDataFromMicroServices(List.of(currentElement));
            } else log.info("Объект с Guid {} уже был опубликован", id);
        }
    }

    @Override
    public void changeState(List<GuidContentDto> listGuidContents) {
        List<Content> result = new ArrayList<>();

        for (GuidContentDto item : listGuidContents) {
            Optional<Content> contentOptional = contentRepository.findById(item.getContentGuid());
            if (contentOptional.isPresent()) {
                Content currentElement = stateIsPublished(contentOptional.get());
                if (currentElement.getId() != null) {
                    result.add(currentElement);
                }
            }
        }
        contentRepository.saveAll(result);
        sendDataFromMicroServices(result);
    }

    @Override
    public void changeState() {
        List<Content> contents = contentRepository.findAll();
        List<Content> result = new ArrayList<>();

        for (Content item : contents) {
            Content currentElement = stateIsPublished(item);
            if (currentElement.getId() != null) {
                result.add(currentElement);
            }
        }
        contentRepository.saveAll(result);
        sendDataFromMicroServices(result);
    }

    @Override
    public List<Content> getAllContent() {
        return contentRepository.findAll();
    }

    @Override
    public void save(List<Content> contents) {
        contentRepository.saveAll(contents);
    }

    private Content stateIsPublished(Content item) {
        if (item.getState() == State.DRAFT) {
            item.setState(State.PUBLISHED);
            return item;
        }
        return new Content();
    }

    public void sendDataFromMicroServices(List<Content> contents) {
        List<ContentDtoForAms> listDtoForAms = contentMapperForAms.contentListToContentDtoList(contents);
        List<ContentDtoForCds> listDtoForCds = contentMapperForCds.contentListToContentDtoList(contents);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        for (ContentDtoForAms contentDtoForAms : listDtoForAms) {
            HttpEntity<ContentDtoForAms> requestEntity = new HttpEntity<>(contentDtoForAms, headers);
            ResponseEntity<ContentDtoForAms> response = restTemplate
                    .postForEntity(URI_FOR_AMS, requestEntity, ContentDtoForAms.class);
            log.info("Отправка контента с guid: {} совершена со статусом {} ", contentDtoForAms.getContentGuid(), response.getStatusCode());
        }

        for (ContentDtoForCds contentDtoForCms : listDtoForCds) {
            HttpEntity<ContentDtoForCds> requestEntity = new HttpEntity<>(contentDtoForCms, headers);
            log.info("Отправка контента с guid: {} совершена со статусом ", contentDtoForCms.getContentGuid());
            ResponseEntity<ContentDtoForCds> response = restTemplate
                    .postForEntity(URI_FOR_CDS, requestEntity, ContentDtoForCds.class);
            log.info("Отправка контента с guid: {} совершена со статусом {} ", contentDtoForCms.getContentGuid(), response.getStatusCode());
        }
    }
}
