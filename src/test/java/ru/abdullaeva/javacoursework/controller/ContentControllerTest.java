package ru.abdullaeva.javacoursework.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.abdullaeva.javacoursework.dto.ContentDto;
import ru.abdullaeva.javacoursework.dto.GuidContentDto;
import ru.abdullaeva.javacoursework.dto.PageDto;
import ru.abdullaeva.javacoursework.model.base.State;
import ru.abdullaeva.javacoursework.service.interf.ContentService;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
@Sql(value = {"/sql/insert.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/sql/delete.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class ContentControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ContentService contentService;
    @Autowired
    private ObjectMapper objectMapper;
    private static List<ContentDto> contentDtoList;

    @BeforeAll
    static void createContent() {
        contentDtoList = new ArrayList<>(List.of(
                ContentDto.builder()
                        .contentGuid(UUID.fromString("30000000-0000-0000-0000-000000000000"))
                        .state(State.DRAFT)
                        .data("data")
                        .pages(Set.of(
                                PageDto.builder()
                                        .pageName("HOME_PAGE")
                                        .build(),
                                PageDto.builder()
                                        .pageName("MAIN_PAGE")
                                        .build())
                        )
                        .build(),
                ContentDto.builder()
                        .contentGuid(UUID.fromString("40000000-0000-0000-0000-000000000000"))
                        .state(State.DRAFT)
                        .data("data 2")
                        .pages(Set.of(
                                PageDto.builder()
                                        .pageName("OK_PAGE")
                                        .build(),
                                PageDto.builder()
                                        .pageName("MAIN_PAGE")
                                        .build())
                        )
                        .build()));
    }

    @Test
    @WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
    void save_SaveContent() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/content/save")
                        .content(objectMapper.writeValueAsString(contentDtoList))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
    void getAll_ReturnContentListWithRoleAdmin() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/content"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(hasSize(3)))
                .andExpect(jsonPath("$[0].contentGuid")
                        .value("00000000-0000-0000-0000-000000000000"))
                .andExpect(jsonPath("$[0].pages.[0].pageName").value("HOME_PAGE"))
                .andExpect(jsonPath("$[0].data").value("{\"header\": \"Header 0\", \"backgroundImg\": \"0.jpeq\",\"text\": \"text 0\" }"))
                .andExpect(jsonPath("$[0].state").value(State.DRAFT.toString()));
    }

    @Test
    @WithMockUser(username = "user", password = "user", roles = "USER")
    void getAll_GetHttpStatus403() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/content"))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
    void getAllForAms_ReturnContentListForAms() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/content/ams"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(hasSize(3)))
                .andExpect(jsonPath("$[0].contentGuid")
                        .value("00000000-0000-0000-0000-000000000000"))
                .andExpect(jsonPath("$[0].pages.[0].pageName").value("HOME_PAGE"));
    }

    @Test
    @WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
    void getAllForCds_ReturnContentListForCds() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/content/cds"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(hasSize(3)))
                .andExpect(jsonPath("$[1].contentGuid")
                        .value("10000000-0000-0000-0000-000000000000"))
                .andExpect(jsonPath("$[1].data")
                        .value("{\"header\": \"Header 1\", \"backgroundImg\": \"1.jpeq\",\"text\": \"text 1\" }"));
    }

//    @Test
//    @WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
//    void changeState_GetContentGuid_ThatChangesStateFromDraftToPublishedAndSendToAmsAndCds() throws Exception {
//        UUID uuid = UUID.fromString("00000000-0000-0000-0000-000000000000");
//        mockMvc.perform(MockMvcRequestBuilders
//                        .put("/api/v1/content/publish/" + uuid)
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    @WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
//    void changeStateGetContentGuidList_ThatChangesStateFromDraftToPublishedAndSendToAmsAndCds() throws Exception {
//        GuidContentDto guid = GuidContentDto.builder()
//                .contentGuid(UUID.fromString("00000000-0000-0000-0000-000000000000"))
//                .build();
//        mockMvc.perform(MockMvcRequestBuilders
//                        .put("/api/v1/content/publish/list")
//                        .content(objectMapper.writeValueAsString(List.of(guid)))
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$[0].state").value(State.PUBLISHED.toString()));
//    }
//
//    @Test
//    @WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
//    void changeState_GetAllContentAndChangesStateFromDraftToPublishedAndSendToAmsAndCds() throws Exception {
//        mockMvc.perform(MockMvcRequestBuilders
//                        .put("/api/v1/content/publish"))
//                .andDo(print())
//                .andExpect(status().isOk());
//    }
}