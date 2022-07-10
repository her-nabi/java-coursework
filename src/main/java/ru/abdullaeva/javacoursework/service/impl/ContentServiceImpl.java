package ru.abdullaeva.javacoursework.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.abdullaeva.javacoursework.dto.ContentDtoForAms;
import ru.abdullaeva.javacoursework.dto.ContentDtoForCds;
import ru.abdullaeva.javacoursework.dto.GuidContentDto;
import ru.abdullaeva.javacoursework.mappers.ContentMapperForAms;
import ru.abdullaeva.javacoursework.mappers.ContentMapperForCds;
import ru.abdullaeva.javacoursework.model.auth.Users;
import ru.abdullaeva.javacoursework.model.base.Content;
import ru.abdullaeva.javacoursework.model.base.State;
import ru.abdullaeva.javacoursework.repository.ContentRepository;
import ru.abdullaeva.javacoursework.security.jwt.JwtTokenProvider;
import ru.abdullaeva.javacoursework.service.interf.ContentService;
import ru.abdullaeva.javacoursework.service.interf.UsersService;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.web.reactive.function.client.ExchangeFilterFunctions.basicAuthentication;

@Slf4j
@Service
public class ContentServiceImpl implements ContentService {

    private final String URI_FOR_AMS = "http://localhost:8080/microservices/api/v1/content/ams/save";
    private final String URI_FOR_CDS = "http://localhost:8080/microservices/api/v1/content/cds/save";
    private final ContentRepository contentRepository;
    private final RestTemplate restTemplate;
    private final ContentMapperForAms contentMapperForAms;
    private final ContentMapperForCds contentMapperForCds;
    private final JwtTokenProvider jwtTokenProvider;
    private final UsersService usersService;

    @Autowired
    public ContentServiceImpl(ContentRepository contentRepository, RestTemplate restTemplate, ContentMapperForAms contentMapperForAms, ContentMapperForCds contentMapperForCds, JwtTokenProvider jwtTokenProvider, UsersService usersService) {
        this.contentRepository = contentRepository;
        this.restTemplate = restTemplate;
        this.contentMapperForAms = contentMapperForAms;
        this.contentMapperForCds = contentMapperForCds;
        this.jwtTokenProvider = jwtTokenProvider;
        this.usersService = usersService;
    }

    @Override
    @Transactional
    public void changeState(UUID id) {
        Optional<Content> contentOptional = contentRepository.findById(id);
        if (contentOptional.isPresent()) {
            Content currentElement = stateIsPublished(contentOptional.get());
            if (currentElement.getId() != null) {
                contentRepository.save(currentElement);
                log.info("В changeState контент с id: {} сохранен.", id);
                sendDataFromMicroServices(List.of(currentElement));
            } else log.error("Объект с Guid {} уже был опубликован.", id);
        }
    }

    @Override
    @Transactional
    public void changeState(List<GuidContentDto> listGuidContents) {
        List<Content> result = new ArrayList<>();

        for (GuidContentDto item : listGuidContents) {
            Optional<Content> contentOptional = contentRepository.findById(item.getContentGuid());
            if (contentOptional.isPresent()) {
                Content currentElement = stateIsPublished(contentOptional.get());
                if (currentElement.getId() != null) {
                    result.add(currentElement);
                }
                else {
                    log.error("Объект с Guid {} уже был опубликован.", contentOptional.get().getId());
                }
            }
        }
        contentRepository.saveAll(result);
        log.info("В changeState количество контента на публикацию: {}.", result.size());
        sendDataFromMicroServices(result);
    }

    @Transactional
    @Override
    public void changeState() {
        List<Content> contents = contentRepository.findAll();
        List<Content> result = new ArrayList<>();

        for (Content item : contents) {
            Content currentElement = stateIsPublished(item);
            if (currentElement.getId() != null) {
                result.add(currentElement);
            }
            else {
                log.error("Объект с Guid {} уже был опубликован.", item.getId());
            }
        }
        contentRepository.saveAll(result);
        log.info("В changeState количество контента на публикацию: {}.", result.size());
        sendDataFromMicroServices(result);
    }

    @Override
    public List<Content> getAllContent() {
        log.info("Метод getAllContent вернул контент.");
        return contentRepository.findAll();
    }

    @Override
    public void save(List<Content> contents) {
        contentRepository.saveAll(contents);
        log.info("В save было сохранено {} записей", contents.size());
    }

    /**
     * Метод - подготовка контента к публикации. Если обрабатываемый контент
     * еще не был опубликован, то статус меняется на PUBLISHED, иначе
     * контент игнорируется.
     * @param item контент, статус которого необходимо сменить.
     * @return контент с измененным статусом в случае, если статус
     * контента изначально был DRAFT, иначе возвращает пустой объект.
     */
    private Content stateIsPublished(Content item) {
        if (item.getState() == State.DRAFT) {
            item.setState(State.PUBLISHED);
            return item;
        }
        return new Content();
    }

    /**
     * Отправка контента в микросервисы AMS и CDS.
     * @param contents список контента, который необходимо отправить.
     */
    private void sendDataFromMicroServices(List<Content> contents) {

        String login = "admin";
        Users user = usersService.findByLogin(login);
        String token = jwtTokenProvider.createToken(login, user.getRole());

        List<ContentDtoForAms> listDtoForAms = contentMapperForAms.contentListToContentDtoList(contents);
        List<ContentDtoForCds> listDtoForCds = contentMapperForCds.contentListToContentDtoList(contents);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        headers.set("Authorization", "Bearer_"+ token);

        for (ContentDtoForAms contentDtoForAms : listDtoForAms) {

            HttpEntity<ContentDtoForAms> requestEntity = new HttpEntity<>(contentDtoForAms, headers);

            ResponseEntity<ContentDtoForAms> response = restTemplate
                    .postForEntity(URI_FOR_AMS, requestEntity, ContentDtoForAms.class);
            log.info("В sendDataFromMicroServices отправка контента с guid: {} совершена со статусом {} ", contentDtoForAms.getContentGuid(), response.getStatusCode());
        }

        for (ContentDtoForCds contentDtoForCms : listDtoForCds) {
            HttpEntity<ContentDtoForCds> requestEntity = new HttpEntity<>(contentDtoForCms, headers);
            log.info("Отправка контента с guid: {} совершена со статусом ", contentDtoForCms.getContentGuid());
            ResponseEntity<ContentDtoForCds> response = restTemplate
                    .postForEntity(URI_FOR_CDS, requestEntity, ContentDtoForCds.class);
            log.info("В sendDataFromMicroServices отправка контента с guid: {} совершена со статусом {} ", contentDtoForCms.getContentGuid(), response.getStatusCode());
        }
    }
}
