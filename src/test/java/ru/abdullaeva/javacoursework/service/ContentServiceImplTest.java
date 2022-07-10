package ru.abdullaeva.javacoursework.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import ru.abdullaeva.javacoursework.model.base.Content;
import ru.abdullaeva.javacoursework.model.base.State;
import ru.abdullaeva.javacoursework.service.interf.ContentService;

import java.util.HashSet;
import java.util.List;
import java.util.UUID;

@SpringBootTest
@TestPropertySource("/application-test.properties")
@Sql(value = {"/sql/insert.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/sql/delete.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class ContentServiceImplTest {

    private final ContentService contentService;

    @Autowired
    ContentServiceImplTest(ContentService contentService) {
        this.contentService = contentService;
    }

    @Test
    void changeState_GetContentGuid_ThatChangesStateFromDraftToPublishedAndSendToAmsAndCds() {
    }

    @Test
    void changeStateGetContentGuidList_ThatChangesStateFromDraftToPublishedAndSendToAmsAndCds() {
    }

    @Test
    void changeState_GetAllContentAndChangesStateFromDraftToPublishedAndSendToAmsAndCds() {
    }

    @Test
    void save_SaveContent() {
        Content content = Content.builder()
                .id(UUID.fromString("30000000-0000-0000-0000-000000000000"))
                .data("data")
                .state(State.DRAFT)
                .pages(new HashSet<>()).build();
        contentService.save(List.of(content));

        List<Content> allContent = contentService.getAllContent();

        Assertions.assertEquals(4, allContent.size());
        Assertions.assertEquals(content.getId(), allContent.get(allContent.size() - 1).getId());
        Assertions.assertEquals(content.getData(), allContent.get(allContent.size() - 1).getData());
        Assertions.assertEquals(content.getState(), allContent.get(allContent.size() - 1).getState());
        Assertions.assertNotNull( allContent.get(allContent.size() - 1).getPages());
    }

    @Test
    void getAll_ReturnContentList() {
        List<Content> allContent = contentService.getAllContent();
        Assertions.assertEquals(3, allContent.size());
        Assertions.assertEquals("00000000-0000-0000-0000-000000000000", allContent.get(0).getId().toString());
        Assertions.assertEquals("10000000-0000-0000-0000-000000000000", allContent.get(1).getId().toString());
        Assertions.assertEquals("20000000-0000-0000-0000-000000000000", allContent.get(2).getId().toString());
    }
}