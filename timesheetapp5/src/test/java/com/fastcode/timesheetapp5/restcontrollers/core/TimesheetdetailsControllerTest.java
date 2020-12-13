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

import com.fastcode.timesheetapp5.application.core.task.TaskAppService;
import com.fastcode.timesheetapp5.application.core.timesheet.TimesheetAppService;
import com.fastcode.timesheetapp5.application.core.timesheetdetails.TimesheetdetailsAppService;
import com.fastcode.timesheetapp5.application.core.timesheetdetails.dto.*;
import com.fastcode.timesheetapp5.commons.logging.LoggingHelper;
import com.fastcode.timesheetapp5.commons.search.SearchUtils;
import com.fastcode.timesheetapp5.domain.core.authorization.users.IUsersRepository;
import com.fastcode.timesheetapp5.domain.core.authorization.users.UsersEntity;
import com.fastcode.timesheetapp5.domain.core.customer.CustomerEntity;
import com.fastcode.timesheetapp5.domain.core.customer.ICustomerRepository;
import com.fastcode.timesheetapp5.domain.core.project.IProjectRepository;
import com.fastcode.timesheetapp5.domain.core.project.ProjectEntity;
import com.fastcode.timesheetapp5.domain.core.task.ITaskRepository;
import com.fastcode.timesheetapp5.domain.core.task.TaskEntity;
import com.fastcode.timesheetapp5.domain.core.timesheet.ITimesheetRepository;
import com.fastcode.timesheetapp5.domain.core.timesheet.TimesheetEntity;
import com.fastcode.timesheetapp5.domain.core.timesheetdetails.ITimesheetdetailsRepository;
import com.fastcode.timesheetapp5.domain.core.timesheetdetails.TimesheetdetailsEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.math.BigDecimal;
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
public class TimesheetdetailsControllerTest {

    @Autowired
    protected SortHandlerMethodArgumentResolver sortArgumentResolver;

    @Autowired
    @Qualifier("timesheetdetailsRepository")
    protected ITimesheetdetailsRepository timesheetdetails_repository;

    @Autowired
    @Qualifier("taskRepository")
    protected ITaskRepository taskRepository;

    @Autowired
    @Qualifier("timesheetRepository")
    protected ITimesheetRepository timesheetRepository;

    @Autowired
    @Qualifier("projectRepository")
    protected IProjectRepository projectRepository;

    @Autowired
    @Qualifier("customerRepository")
    protected ICustomerRepository customerRepository;

    @Autowired
    @Qualifier("usersRepository")
    protected IUsersRepository usersRepository;

    @SpyBean
    @Qualifier("timesheetdetailsAppService")
    protected TimesheetdetailsAppService timesheetdetailsAppService;

    @SpyBean
    @Qualifier("taskAppService")
    protected TaskAppService taskAppService;

    @SpyBean
    @Qualifier("timesheetAppService")
    protected TimesheetAppService timesheetAppService;

    @SpyBean
    protected LoggingHelper logHelper;

    @SpyBean
    protected Environment env;

    @Mock
    protected Logger loggerMock;

    protected TimesheetdetailsEntity timesheetdetails;

    protected MockMvc mvc;

    @Autowired
    EntityManagerFactory emf;

    static EntityManagerFactory emfs;

    int count = 10;

    int countProject = 10;
    int countTask = 10;
    int countCustomer = 10;
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
        em.createNativeQuery("truncate table timesheet.timesheetdetails").executeUpdate();
        em.createNativeQuery("truncate table timesheet.task").executeUpdate();
        em.createNativeQuery("truncate table timesheet.timesheet").executeUpdate();
        em.createNativeQuery("truncate table timesheet.project").executeUpdate();
        em.createNativeQuery("truncate table timesheet.customer").executeUpdate();
        em.createNativeQuery("truncate table timesheet.users").executeUpdate();
        em.createNativeQuery("SET REFERENTIAL_INTEGRITY TRUE").executeUpdate();
        em.getTransaction().commit();
    }

    public ProjectEntity createProjectEntity() {
        if (countProject > 99) {
            countProject = 10;
        }

        ProjectEntity projectEntity = new ProjectEntity();
        projectEntity.setDescription(String.valueOf(countProject));
        projectEntity.setEnddate(SearchUtils.stringToLocalDateTime("19" + countProject + "-09-01 05:25:22"));
        projectEntity.setId(Long.valueOf(countProject));
        projectEntity.setName(String.valueOf(countProject));
        projectEntity.setStartdate(SearchUtils.stringToLocalDateTime("19" + countProject + "-09-01 05:25:22"));
        projectEntity.setVersiono(0L);
        CustomerEntity customer = createCustomerEntity();
        projectEntity.setCustomer(customer);
        if (!projectRepository.findAll().contains(projectEntity)) {
            projectEntity = projectRepository.save(projectEntity);
        }
        countProject++;
        return projectEntity;
    }

    public TaskEntity createTaskEntity() {
        if (countTask > 99) {
            countTask = 10;
        }

        TaskEntity taskEntity = new TaskEntity();
        taskEntity.setDescription(String.valueOf(countTask));
        taskEntity.setId(Long.valueOf(countTask));
        taskEntity.setIsactive(false);
        taskEntity.setName(String.valueOf(countTask));
        taskEntity.setVersiono(0L);
        ProjectEntity project = createProjectEntity();
        taskEntity.setProject(project);
        if (!taskRepository.findAll().contains(taskEntity)) {
            taskEntity = taskRepository.save(taskEntity);
        }
        countTask++;
        return taskEntity;
    }

    public CustomerEntity createCustomerEntity() {
        if (countCustomer > 99) {
            countCustomer = 10;
        }

        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setCustomerid(Long.valueOf(countCustomer));
        customerEntity.setDescription(String.valueOf(countCustomer));
        customerEntity.setIsactive(false);
        customerEntity.setName(String.valueOf(countCustomer));
        customerEntity.setVersiono(0L);
        if (!customerRepository.findAll().contains(customerEntity)) {
            customerEntity = customerRepository.save(customerEntity);
        }
        countCustomer++;
        return customerEntity;
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

    public TimesheetdetailsEntity createEntity() {
        TaskEntity task = createTaskEntity();
        TimesheetEntity timesheet = createTimesheetEntity();

        TimesheetdetailsEntity timesheetdetailsEntity = new TimesheetdetailsEntity();
        timesheetdetailsEntity.setHours(new BigDecimal("1"));
        timesheetdetailsEntity.setId(1L);
        timesheetdetailsEntity.setWorkdate(SearchUtils.stringToLocalDateTime("1996-09-01 09:15:22"));
        timesheetdetailsEntity.setVersiono(0L);
        timesheetdetailsEntity.setTask(task);
        timesheetdetailsEntity.setTimesheet(timesheet);

        return timesheetdetailsEntity;
    }

    public CreateTimesheetdetailsInput createTimesheetdetailsInput() {
        CreateTimesheetdetailsInput timesheetdetailsInput = new CreateTimesheetdetailsInput();
        timesheetdetailsInput.setHours(new BigDecimal("5"));
        timesheetdetailsInput.setWorkdate(SearchUtils.stringToLocalDateTime("1996-08-10 05:25:22"));

        return timesheetdetailsInput;
    }

    public TimesheetdetailsEntity createNewEntity() {
        TimesheetdetailsEntity timesheetdetails = new TimesheetdetailsEntity();
        timesheetdetails.setHours(new BigDecimal("3"));
        timesheetdetails.setId(3L);
        timesheetdetails.setWorkdate(SearchUtils.stringToLocalDateTime("1996-08-11 05:35:22"));

        return timesheetdetails;
    }

    public TimesheetdetailsEntity createUpdateEntity() {
        TimesheetdetailsEntity timesheetdetails = new TimesheetdetailsEntity();
        timesheetdetails.setHours(new BigDecimal("3"));
        timesheetdetails.setId(4L);
        timesheetdetails.setWorkdate(SearchUtils.stringToLocalDateTime("1996-09-09 05:45:22"));

        return timesheetdetails;
    }

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        final TimesheetdetailsController timesheetdetailsController = new TimesheetdetailsController(
            timesheetdetailsAppService,
            taskAppService,
            timesheetAppService,
            logHelper,
            env
        );
        when(logHelper.getLogger()).thenReturn(loggerMock);
        doNothing().when(loggerMock).error(anyString());

        this.mvc =
            MockMvcBuilders
                .standaloneSetup(timesheetdetailsController)
                .setCustomArgumentResolvers(sortArgumentResolver)
                .setControllerAdvice()
                .build();
    }

    @Before
    public void initTest() {
        timesheetdetails = createEntity();
        List<TimesheetdetailsEntity> list = timesheetdetails_repository.findAll();
        if (!list.contains(timesheetdetails)) {
            timesheetdetails = timesheetdetails_repository.save(timesheetdetails);
        }
    }

    @Test
    public void FindById_IdIsValid_ReturnStatusOk() throws Exception {
        mvc
            .perform(get("/timesheetdetails/" + timesheetdetails.getId() + "/").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }

    @Test
    public void FindById_IdIsNotValid_ReturnStatusNotFound() {
        org.assertj.core.api.Assertions
            .assertThatThrownBy(
                () ->
                    mvc
                        .perform(get("/timesheetdetails/999").contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
            )
            .hasCause(new EntityNotFoundException("Not found"));
    }

    @Test
    public void CreateTimesheetdetails_TimesheetdetailsDoesNotExist_ReturnStatusOk() throws Exception {
        CreateTimesheetdetailsInput timesheetdetailsInput = createTimesheetdetailsInput();

        TaskEntity task = createTaskEntity();

        timesheetdetailsInput.setTaskid(Long.parseLong(task.getId().toString()));

        TimesheetEntity timesheet = createTimesheetEntity();

        timesheetdetailsInput.setTimesheetid(Long.parseLong(timesheet.getId().toString()));

        ObjectWriter ow = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            .writer()
            .withDefaultPrettyPrinter();

        String json = ow.writeValueAsString(timesheetdetailsInput);

        mvc
            .perform(post("/timesheetdetails").contentType(MediaType.APPLICATION_JSON).content(json))
            .andExpect(status().isOk());
    }

    @Test
    public void DeleteTimesheetdetails_IdIsNotValid_ThrowEntityNotFoundException() {
        doReturn(null).when(timesheetdetailsAppService).findById(999L);
        org.assertj.core.api.Assertions
            .assertThatThrownBy(
                () ->
                    mvc
                        .perform(delete("/timesheetdetails/999").contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
            )
            .hasCause(new EntityNotFoundException("There does not exist a timesheetdetails with a id=999"));
    }

    @Test
    public void Delete_IdIsValid_ReturnStatusNoContent() throws Exception {
        TimesheetdetailsEntity entity = createNewEntity();
        entity.setVersiono(0L);
        TaskEntity task = createTaskEntity();
        entity.setTask(task);
        TimesheetEntity timesheet = createTimesheetEntity();
        entity.setTimesheet(timesheet);
        entity = timesheetdetails_repository.save(entity);

        FindTimesheetdetailsByIdOutput output = new FindTimesheetdetailsByIdOutput();
        output.setId(entity.getId());

        Mockito.doReturn(output).when(timesheetdetailsAppService).findById(entity.getId());

        //    Mockito.when(timesheetdetailsAppService.findById(entity.getId())).thenReturn(output);

        mvc
            .perform(delete("/timesheetdetails/" + entity.getId() + "/").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());
    }

    @Test
    public void UpdateTimesheetdetails_TimesheetdetailsDoesNotExist_ReturnStatusNotFound() throws Exception {
        doReturn(null).when(timesheetdetailsAppService).findById(999L);

        UpdateTimesheetdetailsInput timesheetdetails = new UpdateTimesheetdetailsInput();
        timesheetdetails.setHours(new BigDecimal("999"));
        timesheetdetails.setId(999L);
        timesheetdetails.setWorkdate(SearchUtils.stringToLocalDateTime("1996-09-28 07:15:22"));

        ObjectWriter ow = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            .writer()
            .withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(timesheetdetails);

        org.assertj.core.api.Assertions
            .assertThatThrownBy(
                () ->
                    mvc
                        .perform(put("/timesheetdetails/999").contentType(MediaType.APPLICATION_JSON).content(json))
                        .andExpect(status().isOk())
            )
            .hasCause(new EntityNotFoundException("Unable to update. Timesheetdetails with id=999 not found."));
    }

    @Test
    public void UpdateTimesheetdetails_TimesheetdetailsExists_ReturnStatusOk() throws Exception {
        TimesheetdetailsEntity entity = createUpdateEntity();
        entity.setVersiono(0L);

        TaskEntity task = createTaskEntity();
        entity.setTask(task);
        TimesheetEntity timesheet = createTimesheetEntity();
        entity.setTimesheet(timesheet);
        entity = timesheetdetails_repository.save(entity);
        FindTimesheetdetailsByIdOutput output = new FindTimesheetdetailsByIdOutput();
        output.setHours(entity.getHours());
        output.setId(entity.getId());
        output.setWorkdate(entity.getWorkdate());
        output.setVersiono(entity.getVersiono());

        Mockito.when(timesheetdetailsAppService.findById(entity.getId())).thenReturn(output);

        UpdateTimesheetdetailsInput timesheetdetailsInput = new UpdateTimesheetdetailsInput();
        timesheetdetailsInput.setId(entity.getId());

        timesheetdetailsInput.setTaskid(Long.parseLong(task.getId().toString()));
        timesheetdetailsInput.setTimesheetid(Long.parseLong(timesheet.getId().toString()));

        ObjectWriter ow = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            .writer()
            .withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(timesheetdetailsInput);

        mvc
            .perform(
                put("/timesheetdetails/" + entity.getId() + "/").contentType(MediaType.APPLICATION_JSON).content(json)
            )
            .andExpect(status().isOk());

        TimesheetdetailsEntity de = createUpdateEntity();
        de.setId(entity.getId());
        timesheetdetails_repository.delete(de);
    }

    @Test
    public void FindAll_SearchIsNotNullAndPropertyIsValid_ReturnStatusOk() throws Exception {
        mvc
            .perform(
                get("/timesheetdetails?search=id[equals]=1&limit=10&offset=1").contentType(MediaType.APPLICATION_JSON)
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
                            get("/timesheetdetails?search=timesheetdetailsid[equals]=1&limit=10&offset=1")
                                .contentType(MediaType.APPLICATION_JSON)
                        )
                        .andExpect(status().isOk())
            )
            .hasCause(new Exception("Wrong URL Format: Property timesheetdetailsid not found!"));
    }

    @Test
    public void GetTask_IdIsNotEmptyAndIdDoesNotExist_ReturnNotFound() {
        org.assertj.core.api.Assertions
            .assertThatThrownBy(
                () ->
                    mvc
                        .perform(get("/timesheetdetails/999/task").contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
            )
            .hasCause(new EntityNotFoundException("Not found"));
    }

    @Test
    public void GetTask_searchIsNotEmptyAndPropertyIsValid_ReturnList() throws Exception {
        mvc
            .perform(
                get("/timesheetdetails/" + timesheetdetails.getId() + "/task").contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isOk());
    }

    @Test
    public void GetTimesheet_IdIsNotEmptyAndIdDoesNotExist_ReturnNotFound() {
        org.assertj.core.api.Assertions
            .assertThatThrownBy(
                () ->
                    mvc
                        .perform(get("/timesheetdetails/999/timesheet").contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
            )
            .hasCause(new EntityNotFoundException("Not found"));
    }

    @Test
    public void GetTimesheet_searchIsNotEmptyAndPropertyIsValid_ReturnList() throws Exception {
        mvc
            .perform(
                get("/timesheetdetails/" + timesheetdetails.getId() + "/timesheet")
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isOk());
    }
}
