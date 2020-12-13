package com.fastcode.timesheetapp5.restcontrollers.core;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fastcode.timesheetapp5.application.core.status.StatusAppService;
import com.fastcode.timesheetapp5.application.core.status.dto.*;
import com.fastcode.timesheetapp5.application.core.timesheetstatus.TimesheetstatusAppService;
import com.fastcode.timesheetapp5.commons.logging.LoggingHelper;
import com.fastcode.timesheetapp5.domain.core.status.IStatusRepository;
import com.fastcode.timesheetapp5.domain.core.status.StatusEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.time.*;
import java.util.*;
import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.core.env.Environment;
import org.springframework.data.web.SortHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = "spring.profiles.active=test")
public class StatusControllerTest {

    @Autowired
    protected SortHandlerMethodArgumentResolver sortArgumentResolver;

    @Autowired
    @Qualifier("statusRepository")
    protected IStatusRepository status_repository;

    @SpyBean
    @Qualifier("statusAppService")
    protected StatusAppService statusAppService;

    @SpyBean
    @Qualifier("timesheetstatusAppService")
    protected TimesheetstatusAppService timesheetstatusAppService;

    @SpyBean
    protected LoggingHelper logHelper;

    @SpyBean
    protected Environment env;

    @Mock
    protected Logger loggerMock;

    protected StatusEntity status;

    protected MockMvc mvc;

    @Autowired
    EntityManagerFactory emf;

    static EntityManagerFactory emfs;

    int count = 10;

    @PostConstruct
    public void init() {
        emfs = emf;
    }

    @AfterClass
    public static void cleanup() {
        EntityManager em = emfs.createEntityManager();
        em.getTransaction().begin();
        em.createNativeQuery("SET REFERENTIAL_INTEGRITY FALSE").executeUpdate();
        em.createNativeQuery("truncate table timesheet.status").executeUpdate();
        em.createNativeQuery("SET REFERENTIAL_INTEGRITY TRUE").executeUpdate();
        em.getTransaction().commit();
    }

    public StatusEntity createEntity() {
        StatusEntity statusEntity = new StatusEntity();
        statusEntity.setDescription("1");
        statusEntity.setId(1L);
        statusEntity.setVersiono(0L);

        return statusEntity;
    }

    public CreateStatusInput createStatusInput() {
        CreateStatusInput statusInput = new CreateStatusInput();
        statusInput.setDescription("5");

        return statusInput;
    }

    public StatusEntity createNewEntity() {
        StatusEntity status = new StatusEntity();
        status.setDescription("3");
        status.setId(3L);

        return status;
    }

    public StatusEntity createUpdateEntity() {
        StatusEntity status = new StatusEntity();
        status.setDescription("4");
        status.setId(4L);

        return status;
    }

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        final StatusController statusController = new StatusController(
            statusAppService,
            timesheetstatusAppService,
            logHelper,
            env
        );
        when(logHelper.getLogger()).thenReturn(loggerMock);
        doNothing().when(loggerMock).error(anyString());

        this.mvc =
            MockMvcBuilders
                .standaloneSetup(statusController)
                .setCustomArgumentResolvers(sortArgumentResolver)
                .setControllerAdvice()
                .build();
    }

    @Before
    public void initTest() {
        status = createEntity();
        List<StatusEntity> list = status_repository.findAll();
        if (!list.contains(status)) {
            status = status_repository.save(status);
        }
    }

    @Test
    public void FindById_IdIsValid_ReturnStatusOk() throws Exception {
        mvc
            .perform(get("/status/" + status.getId() + "/").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }

    @Test
    public void FindById_IdIsNotValid_ReturnStatusNotFound() {
        org.assertj.core.api.Assertions
            .assertThatThrownBy(
                () -> mvc.perform(get("/status/999").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
            )
            .hasCause(new EntityNotFoundException("Not found"));
    }

    @Test
    public void CreateStatus_StatusDoesNotExist_ReturnStatusOk() throws Exception {
        CreateStatusInput statusInput = createStatusInput();

        ObjectWriter ow = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            .writer()
            .withDefaultPrettyPrinter();

        String json = ow.writeValueAsString(statusInput);

        mvc.perform(post("/status").contentType(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isOk());
    }

    @Test
    public void DeleteStatus_IdIsNotValid_ThrowEntityNotFoundException() {
        doReturn(null).when(statusAppService).findById(999L);
        org.assertj.core.api.Assertions
            .assertThatThrownBy(
                () ->
                    mvc
                        .perform(delete("/status/999").contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
            )
            .hasCause(new EntityNotFoundException("There does not exist a status with a id=999"));
    }

    @Test
    public void Delete_IdIsValid_ReturnStatusNoContent() throws Exception {
        StatusEntity entity = createNewEntity();
        entity.setVersiono(0L);
        entity = status_repository.save(entity);

        FindStatusByIdOutput output = new FindStatusByIdOutput();
        output.setId(entity.getId());

        Mockito.doReturn(output).when(statusAppService).findById(entity.getId());

        //    Mockito.when(statusAppService.findById(entity.getId())).thenReturn(output);

        mvc
            .perform(delete("/status/" + entity.getId() + "/").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());
    }

    @Test
    public void UpdateStatus_StatusDoesNotExist_ReturnStatusNotFound() throws Exception {
        doReturn(null).when(statusAppService).findById(999L);

        UpdateStatusInput status = new UpdateStatusInput();
        status.setDescription("999");
        status.setId(999L);

        ObjectWriter ow = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            .writer()
            .withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(status);

        org.assertj.core.api.Assertions
            .assertThatThrownBy(
                () ->
                    mvc
                        .perform(put("/status/999").contentType(MediaType.APPLICATION_JSON).content(json))
                        .andExpect(status().isOk())
            )
            .hasCause(new EntityNotFoundException("Unable to update. Status with id=999 not found."));
    }

    @Test
    public void UpdateStatus_StatusExists_ReturnStatusOk() throws Exception {
        StatusEntity entity = createUpdateEntity();
        entity.setVersiono(0L);

        entity = status_repository.save(entity);
        FindStatusByIdOutput output = new FindStatusByIdOutput();
        output.setDescription(entity.getDescription());
        output.setId(entity.getId());
        output.setVersiono(entity.getVersiono());

        Mockito.when(statusAppService.findById(entity.getId())).thenReturn(output);

        UpdateStatusInput statusInput = new UpdateStatusInput();
        statusInput.setId(entity.getId());

        ObjectWriter ow = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            .writer()
            .withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(statusInput);

        mvc
            .perform(put("/status/" + entity.getId() + "/").contentType(MediaType.APPLICATION_JSON).content(json))
            .andExpect(status().isOk());

        StatusEntity de = createUpdateEntity();
        de.setId(entity.getId());
        status_repository.delete(de);
    }

    @Test
    public void FindAll_SearchIsNotNullAndPropertyIsValid_ReturnStatusOk() throws Exception {
        mvc
            .perform(get("/status?search=id[equals]=1&limit=10&offset=1").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }

    @Test
    public void FindAll_SearchIsNotNullAndPropertyIsNotValid_ThrowException() throws Exception {
        org.assertj.core.api.Assertions
            .assertThatThrownBy(
                () ->
                    mvc
                        .perform(
                            get("/status?search=statusid[equals]=1&limit=10&offset=1")
                                .contentType(MediaType.APPLICATION_JSON)
                        )
                        .andExpect(status().isOk())
            )
            .hasCause(new Exception("Wrong URL Format: Property statusid not found!"));
    }

    @Test
    public void GetTimesheetstatus_searchIsNotEmptyAndPropertyIsNotValid_ThrowException() {
        Map<String, String> joinCol = new HashMap<String, String>();
        joinCol.put("id", "1");

        Mockito.when(statusAppService.parseTimesheetstatusJoinColumn("statusid")).thenReturn(joinCol);
        org.assertj.core.api.Assertions
            .assertThatThrownBy(
                () ->
                    mvc
                        .perform(
                            get("/status/1/timesheetstatus?search=abc[equals]=1&limit=10&offset=1")
                                .contentType(MediaType.APPLICATION_JSON)
                        )
                        .andExpect(status().isOk())
            )
            .hasCause(new Exception("Wrong URL Format: Property abc not found!"));
    }

    @Test
    public void GetTimesheetstatus_searchIsNotEmptyAndPropertyIsValid_ReturnList() throws Exception {
        Map<String, String> joinCol = new HashMap<String, String>();
        joinCol.put("id", "1");

        Mockito.when(statusAppService.parseTimesheetstatusJoinColumn("statusid")).thenReturn(joinCol);
        mvc
            .perform(
                get("/status/1/timesheetstatus?search=statusid[equals]=1&limit=10&offset=1")
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isOk());
    }

    @Test
    public void GetTimesheetstatus_searchIsNotEmpty() {
        Mockito.when(statusAppService.parseTimesheetstatusJoinColumn(anyString())).thenReturn(null);

        org.assertj.core.api.Assertions
            .assertThatThrownBy(
                () ->
                    mvc
                        .perform(
                            get("/status/1/timesheetstatus?search=statusid[equals]=1&limit=10&offset=1")
                                .contentType(MediaType.APPLICATION_JSON)
                        )
                        .andExpect(status().isOk())
            )
            .hasCause(new EntityNotFoundException("Invalid join column"));
    }
}
