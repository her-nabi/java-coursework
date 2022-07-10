package ru.abdullaeva.javacoursework.controller;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.abdullaeva.javacoursework.dto.ContentDtoForAms;
import ru.abdullaeva.javacoursework.dto.ContentDtoForCds;

/**
 * Контроллер является заглушкой для проверки отправки данных на микросервисы AMS и CDS
 */
@Hidden
@Slf4j
@RestController
@RequestMapping("/microservices/api/v1/content")
public class StubController {

    @PostMapping("/ams/save")
    public void saveContentForAms(@RequestBody ContentDtoForAms contentDtoForAms){
        log.error("В saveContentForAms контент с id: {} сохранен", contentDtoForAms.getContentGuid());
    }
    @PostMapping("/cds/save")
    public void saveContentForCds(@RequestBody ContentDtoForCds contentDtoForCds){
        log.error("В saveContentForCds контент с id: {} сохранен", contentDtoForCds.getContentGuid());
    }

}
