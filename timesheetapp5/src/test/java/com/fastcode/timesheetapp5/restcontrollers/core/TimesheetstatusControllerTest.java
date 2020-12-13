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
import com.fastcode.timesheetapp5.application.core.timesheet.TimesheetAppService;
import com.fastcode.timesheetapp5.application.core.timesheetstatus.TimesheetstatusAppService;
import com.fastcode.timesheetapp5.application.core.timesheetstatus.dto.*;
import com.fastcode.timesheetapp5.commons.logging.LoggingHelper;
import com.fastcode.timesheetapp5.commons.search.SearchUtils;
import com.fastcode.timesheetapp5.domain.core.authorization.users.IUsersRepository;
import com.fastcode.timesheetapp5.domain.core.authorization.users.UsersEntity;
import com.fastcode.timesheetapp5.domain.core.status.IStatusRepository;
import com.fastcode.timesheetapp5.domain.core.status.StatusEntity;
import com.fastcode.timesheetapp5.domain.core.timesheet.ITimesheetRepository;
import com.fastcode.timesheetapp5.domain.core.timesheet.TimesheetEntity;
import com.fastcode.timesheetapp5.domain.core.timesheetstatus.ITimesheetstatusRepository;
import com.fastcode.timesheetapp5.domain.core.timesheetstatus.TimesheetstatusEntity;
import com.fastcode.timesheetapp5.domain.core.timesheetstatus.TimesheetstatusId;
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
public class TimesheetstatusControllerTest {

    @Autowired
    protected SortHandlerMethodArgumentResolver sortArgumentResolver;

    @Autowired
    @Qualifier("timesheetstatusRepository")
    protected ITimesheetstatusRepository timesheetstatus_repository;

    @Autowired
    @Qualifier("statusRepository")
    protected IStatusRepository statusRepository;

    @Autowired
    @Qualifier("timesheetRepository")
    protected ITimesheetRepository timesheetRepository;

    @Autowired
    @Qualifier("usersRepository")
    protected IUsersRepository usersRepository;

    @SpyBean
    @Qualifier("timesheetstatusAppService")
    protected TimesheetstatusAppService timesheetstatusAppService;

    @SpyBean
    @Qualifier("statusAppService")
    protected StatusAppService statusAppService;

    @SpyBean
    @Qualifier("timesheetAppService")
    protected TimesheetAppService timesheetAppService;

    @SpyBean
    protected LoggingHelper logHelper;

    @SpyBean
    protected Environment env;

    @Mock
    protected Logger loggerMock;

    protected TimesheetstatusEntity timesheetstatus;

    protected MockMvc mvc;

    @Autowired
    EntityManagerFactory emf;

    static EntityManagerFactory emfs;

    int count = 10;

    int countStatus = 10;
    int countTimesheet = 10;
    int countUsers = 10;

    @PostConstruct
    public void init() {
        emfs = emf;
    }

    @AfterClass
    public static void cleanup() {
        EntityManager em = emfs.createEntityManager();
        em.getTransaction().begin();
        em.createNativeQuery("SET REFERENTIAL_INTEGRITY FALSE").executeUpdate();
        em.createNativeQuery("truncate table timesheet.timesheetstatus").executeUpdate();
        em.createNativeQuery("truncate table timesheet.status").executeUpdate();
        em.createNativeQuery("truncate table timesheet.timesheet").executeUpdate();
        em.createNativeQuery("truncate table timesheet.users").executeUpdate();
        em.createNativeQuery("SET REFERENTIAL_INTEGRITY TRUE").executeUpdate();
        em.getTransaction().commit();
    }

    public StatusEntity createStatusEntity() {
        if (countStatus > 99) {
            countStatus = 10;
        }

        StatusEntity statusEntity = new StatusEntity();
        statusEntity.setDescription(String.valueOf(countStatus));
        statusEntity.setId(Long.valueOf(countStatus));
        statusEntity.setVersiono(0L);
        if (!statusRepository.findAll().contains(statusEntity)) {
            statusEntity = statusRepository.save(statusEntity);
        }
        countStatus++;
        return statusEntity;
    }

    public TimesheetEntity createTimesheetEntity() {
        if (countTimesheet > 99) {
            countTimesheet = 10;
        }

        TimesheetEntity timesheetEntity = new TimesheetEntity();
        timesheetEntity.setId(Long.valueOf(countTimesheet));
        timesheetEntity.setNotes(String.valueOf(countTimesheet));
        timesheetEntity.setPeriodendingdate(
            SearchUtils.stringToLocalDateTime("19" + countTimesheet + "-09-01 05:25:22")
        );
        timesheetEntity.setVersiono(0L);
        UsersEntity users = createUsersEntity();
        timesheetEntity.setUsers(users);
        if (!timesheetRepository.findAll().contains(timesheetEntity)) {
            timesheetEntity = timesheetRepository.save(timesheetEntity);
        }
        countTimesheet++;
        return timesheetEntity;
    }

    public UsersEntity createUsersEntity() {
        if (countUsers > 99) {
            countUsers = 10;
        }

        UsersEntity usersEntity = new UsersEntity();
        usersEntity.setEmailaddress(String.valueOf(countUsers));
        usersEntity.setFirstname(String.valueOf(countUsers));
        usersEntity.setId(Long.valueOf(countUsers));
        usersEntity.setIsactive(false);
        usersEntity.setIsemailconfirmed(false);
        usersEntity.setLastname(String.valueOf(countUsers));
        usersEntity.setPassword(String.valueOf(countUsers));
        usersEntity.setUsername(String.valueOf(countUsers));
        usersEntity.setVersiono(0L);
        if (!usersRepository.findAll().contains(usersEntity)) {
            usersEntity = usersRepository.save(usersEntity);
        }
        countUsers++;
        return usersEntity;
    }

    public TimesheetstatusEntity createEntity() {
        StatusEntity status = createStatusEntity();
        TimesheetEntity timesheet = createTimesheetEntity();

        TimesheetstatusEntity timesheetstatusEntity = new TimesheetstatusEntity();
        timesheetstatusEntity.setNotes("1");
        timesheetstatusEntity.setStatuschangedate(SearchUtils.stringToLocalDateTime("1996-09-01 09:15:22"));
        timesheetstatusEntity.setStatusid(1L);
        timesheetstatusEntity.setTimesheetid(1L);
        timesheetstatusEntity.setVersiono(0L);
        timesheetstatusEntity.setStatus(status);
        timesheetstatusEntity.setStatusid(Long.parseLong(status.getId().toString()));
        timesheetstatusEntity.setTimesheet(timesheet);
        timesheetstatusEntity.setTimesheetid(Long.parseLong(timesheet.getId().toString()));

        return timesheetstatusEntity;
    }

    public CreateTimesheetstatusInput createTimesheetstatusInput() {
        CreateTimesheetstatusInput timesheetstatusInput = new CreateTimesheetstatusInput();
        timesheetstatusInput.setNotes("5");
        timesheetstatusInput.setStatuschangedate(SearchUtils.stringToLocalDateTime("1996-08-10 05:25:22"));
        timesheetstatusInput.setStatusid(5L);
        timesheetstatusInput.setTimesheetid(5L);

        return timesheetstatusInput;
    }

    public TimesheetstatusEntity createNewEntity() {
        TimesheetstatusEntity timesheetstatus = new TimesheetstatusEntity();
        timesheetstatus.setNotes("3");
        timesheetstatus.setStatuschangedate(SearchUtils.stringToLocalDateTime("1996-08-11 05:35:22"));
        timesheetstatus.setStatusid(3L);
        timesheetstatus.setTimesheetid(3L);

        return timesheetstatus;
    }

    public TimesheetstatusEntity createUpdateEntity() {
        TimesheetstatusEntity timesheetstatus = new TimesheetstatusEntity();
        timesheetstatus.setNotes("4");
        timesheetstatus.setStatuschangedate(SearchUtils.stringToLocalDateTime("1996-09-09 05:45:22"));
        timesheetstatus.setStatusid(4L);
        timesheetstatus.setTimesheetid(4L);

        return timesheetstatus;
    }

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        final TimesheetstatusController timesheetstatusController = new TimesheetstatusController(
            timesheetstatusAppService,
            statusAppService,
            timesheetAppService,
            logHelper,
            env
        );
        when(logHelper.getLogger()).thenReturn(loggerMock);
        doNothing().when(loggerMock).error(anyString());

        this.mvc =
            MockMvcBuilders
                .standaloneSetup(timesheetstatusController)
                .setCustomArgumentResolvers(sortArgumentResolver)
                .setControllerAdvice()
                .build();
    }

    @Before
    public void initTest() {
        timesheetstatus = createEntity();
        List<TimesheetstatusEntity> list = timesheetstatus_repository.findAll();
        if (!list.contains(timesheetstatus)) {
            timesheetstatus = timesheetstatus_repository.save(timesheetstatus);
        }
    }

    @Test
    public void FindById_IdIsValid_ReturnStatusOk() throws Exception {
        mvc
            .perform(
                get(
                    "/timesheetstatus/statusid=" +
                    timesheetstatus.getStatusid() +
                    ",timesheetid=" +
                    timesheetstatus.getTimesheetid() +
                    "/"
                )
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isOk());
    }

    @Test
    public void FindById_IdIsNotValid_ReturnStatusNotFound() {
        org.assertj.core.api.Assertions
            .assertThatThrownBy(
                () ->
                    mvc
                        .perform(
                            get("/timesheetstatus/statusid=999,timesheetid=999").contentType(MediaType.APPLICATION_JSON)
                        )
                        .andExpect(status().isOk())
            )
            .hasCause(new EntityNotFoundException("Not found"));
    }

    @Test
    public void CreateTimesheetstatus_TimesheetstatusDoesNotExist_ReturnStatusOk() throws Exception {
        CreateTimesheetstatusInput timesheetstatusInput = createTimesheetstatusInput();

        StatusEntity status = createStatusEntity();

        timesheetstatusInput.setStatusid(Long.parseLong(status.getId().toString()));

        TimesheetEntity timesheet = createTimesheetEntity();

        timesheetstatusInput.setTimesheetid(Long.parseLong(timesheet.getId().toString()));

        ObjectWriter ow = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            .writer()
            .withDefaultPrettyPrinter();

        String json = ow.writeValueAsString(timesheetstatusInput);

        mvc
            .perform(post("/timesheetstatus").contentType(MediaType.APPLICATION_JSON).content(json))
            .andExpect(status().isOk());
    }

    @Test
    public void DeleteTimesheetstatus_IdIsNotValid_ThrowEntityNotFoundException() {
        doReturn(null).when(timesheetstatusAppService).findById(new TimesheetstatusId(999L, 999L));
        org.assertj.core.api.Assertions
            .assertThatThrownBy(
                () ->
                    mvc
                        .perform(
                            delete("/timesheetstatus/statusid=999,timesheetid=999")
                                .contentType(MediaType.APPLICATION_JSON)
                        )
                        .andExpect(status().isOk())
            )
            .hasCause(
                new EntityNotFoundException(
                    "There does not exist a timesheetstatus with a id=statusid=999,timesheetid=999"
                )
            );
    }

    @Test
    public void Delete_IdIsValid_ReturnStatusNoContent() throws Exception {
        TimesheetstatusEntity entity = createNewEntity();
        entity.setVersiono(0L);
        StatusEntity status = createStatusEntity();
        entity.setStatusid(Long.parseLong(status.getId().toString()));
        entity.setStatus(status);
        TimesheetEntity timesheet = createTimesheetEntity();
        entity.setTimesheetid(Long.parseLong(timesheet.getId().toString()));
        entity.setTimesheet(timesheet);
        entity = timesheetstatus_repository.save(entity);

        FindTimesheetstatusByIdOutput output = new FindTimesheetstatusByIdOutput();
        output.setStatusid(entity.getStatusid());
        output.setTimesheetid(entity.getTimesheetid());

        //    Mockito.when(timesheetstatusAppService.findById(new TimesheetstatusId(entity.getStatusid(), entity.getTimesheetid()))).thenReturn(output);
        Mockito
            .doReturn(output)
            .when(timesheetstatusAppService)
            .findById(new TimesheetstatusId(entity.getStatusid(), entity.getTimesheetid()));

        mvc
            .perform(
                delete(
                    "/timesheetstatus/statusid=" +
                    entity.getStatusid() +
                    ",timesheetid=" +
                    entity.getTimesheetid() +
                    "/"
                )
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isNoContent());
    }

    @Test
    public void UpdateTimesheetstatus_TimesheetstatusDoesNotExist_ReturnStatusNotFound() throws Exception {
        doReturn(null).when(timesheetstatusAppService).findById(new TimesheetstatusId(999L, 999L));

        UpdateTimesheetstatusInput timesheetstatus = new UpdateTimesheetstatusInput();
        timesheetstatus.setNotes("999");
        timesheetstatus.setStatuschangedate(SearchUtils.stringToLocalDateTime("1996-09-28 07:15:22"));
        timesheetstatus.setStatusid(999L);
        timesheetstatus.setTimesheetid(999L);

        ObjectWriter ow = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            .writer()
            .withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(timesheetstatus);

        org.assertj.core.api.Assertions
            .assertThatThrownBy(
                () ->
                    mvc
                        .perform(
                            put("/timesheetstatus/statusid=999,timesheetid=999")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json)
                        )
                        .andExpect(status().isOk())
            )
            .hasCause(
                new EntityNotFoundException(
                    "Unable to update. Timesheetstatus with id=statusid=999,timesheetid=999 not found."
                )
            );
    }

    @Test
    public void UpdateTimesheetstatus_TimesheetstatusExists_ReturnStatusOk() throws Exception {
        TimesheetstatusEntity entity = createUpdateEntity();
        entity.setVersiono(0L);

        StatusEntity status = createStatusEntity();
        entity.setStatusid(Long.parseLong(status.getId().toString()));
        entity.setStatus(status);
        TimesheetEntity timesheet = createTimesheetEntity();
        entity.setTimesheetid(Long.parseLong(timesheet.getId().toString()));
        entity.setTimesheet(timesheet);
        entity = timesheetstatus_repository.save(entity);
        FindTimesheetstatusByIdOutput output = new FindTimesheetstatusByIdOutput();
        output.setNotes(entity.getNotes());
        output.setStatuschangedate(entity.getStatuschangedate());
        output.setStatusid(entity.getStatusid());
        output.setTimesheetid(entity.getTimesheetid());
        output.setVersiono(entity.getVersiono());

        Mockito
            .when(
                timesheetstatusAppService.findById(new TimesheetstatusId(entity.getStatusid(), entity.getTimesheetid()))
            )
            .thenReturn(output);

        UpdateTimesheetstatusInput timesheetstatusInput = new UpdateTimesheetstatusInput();
        timesheetstatusInput.setStatusid(entity.getStatusid());
        timesheetstatusInput.setTimesheetid(entity.getTimesheetid());

        ObjectWriter ow = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            .writer()
            .withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(timesheetstatusInput);

        mvc
            .perform(
                put(
                    "/timesheetstatus/statusid=" +
                    entity.getStatusid() +
                    ",timesheetid=" +
                    entity.getTimesheetid() +
                    "/"
                )
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(json)
            )
            .andExpect(status().isOk());

        TimesheetstatusEntity de = createUpdateEntity();
        de.setStatusid(entity.getStatusid());
        de.setTimesheetid(entity.getTimesheetid());
        timesheetstatus_repository.delete(de);
    }

    @Test
    public void FindAll_SearchIsNotNullAndPropertyIsValid_ReturnStatusOk() throws Exception {
        mvc
            .perform(
                get("/timesheetstatus?search=statusid[equals]=1&limit=10&offset=1")
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isOk());
    }

    @Test
    public void FindAll_SearchIsNotNullAndPropertyIsNotValid_ThrowException() throws Exception {
        org.assertj.core.api.Assertions
            .assertThatThrownBy(
                () ->
                    mvc
                        .perform(
                            get("/timesheetstatus?search=timesheetstatusstatusid[equals]=1&limit=10&offset=1")
                                .contentType(MediaType.APPLICATION_JSON)
                        )
                        .andExpect(status().isOk())
            )
            .hasCause(new Exception("Wrong URL Format: Property timesheetstatusstatusid not found!"));
    }

    @Test
    public void GetStatus_IdIsNotEmptyAndIdIsNotValid_ThrowException() {
        org.assertj.core.api.Assertions
            .assertThatThrownBy(
                () ->
                    mvc
                        .perform(get("/timesheetstatus/statusid999/status").contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
            )
            .hasCause(new EntityNotFoundException("Invalid id=statusid999"));
    }

    @Test
    public void GetStatus_IdIsNotEmptyAndIdDoesNotExist_ReturnNotFound() {
        org.assertj.core.api.Assertions
            .assertThatThrownBy(
                () ->
                    mvc
                        .perform(
                            get("/timesheetstatus/statusid=999,timesheetid=999/status")
                                .contentType(MediaType.APPLICATION_JSON)
                        )
                        .andExpect(status().isOk())
            )
            .hasCause(new EntityNotFoundException("Not found"));
    }

    @Test
    public void GetStatus_searchIsNotEmptyAndPropertyIsValid_ReturnList() throws Exception {
        mvc
            .perform(
                get(
                    "/timesheetstatus/statusid=" +
                    timesheetstatus.getStatusid() +
                    ",timesheetid=" +
                    timesheetstatus.getTimesheetid() +
                    "/status"
                )
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isOk());
    }

    @Test
    public void GetTimesheet_IdIsNotEmptyAndIdIsNotValid_ThrowException() {
        org.assertj.core.api.Assertions
            .assertThatThrownBy(
                () ->
                    mvc
                        .perform(get("/timesheetstatus/statusid999/timesheet").contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
            )
            .hasCause(new EntityNotFoundException("Invalid id=statusid999"));
    }

    @Test
    public void GetTimesheet_IdIsNotEmptyAndIdDoesNotExist_ReturnNotFound() {
        org.assertj.core.api.Assertions
            .assertThatThrownBy(
                () ->
                    mvc
                        .perform(
                            get("/timesheetstatus/statusid=999,timesheetid=999/timesheet")
                                .contentType(MediaType.APPLICATION_JSON)
                        )
                        .andExpect(status().isOk())
            )
            .hasCause(new EntityNotFoundException("Not found"));
    }

    @Test
    public void GetTimesheet_searchIsNotEmptyAndPropertyIsValid_ReturnList() throws Exception {
        mvc
            .perform(
                get(
                    "/timesheetstatus/statusid=" +
                    timesheetstatus.getStatusid() +
                    ",timesheetid=" +
                    timesheetstatus.getTimesheetid() +
                    "/timesheet"
                )
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isOk());
    }
}
