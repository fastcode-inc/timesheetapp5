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

import com.fastcode.timesheetapp5.application.core.authorization.users.UsersAppService;
import com.fastcode.timesheetapp5.application.core.task.TaskAppService;
import com.fastcode.timesheetapp5.application.core.usertask.UsertaskAppService;
import com.fastcode.timesheetapp5.application.core.usertask.dto.*;
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
import com.fastcode.timesheetapp5.domain.core.usertask.IUsertaskRepository;
import com.fastcode.timesheetapp5.domain.core.usertask.UsertaskEntity;
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
public class UsertaskControllerTest {

    @Autowired
    protected SortHandlerMethodArgumentResolver sortArgumentResolver;

    @Autowired
    @Qualifier("usertaskRepository")
    protected IUsertaskRepository usertask_repository;

    @Autowired
    @Qualifier("taskRepository")
    protected ITaskRepository taskRepository;

    @Autowired
    @Qualifier("usersRepository")
    protected IUsersRepository usersRepository;

    @Autowired
    @Qualifier("projectRepository")
    protected IProjectRepository projectRepository;

    @Autowired
    @Qualifier("customerRepository")
    protected ICustomerRepository customerRepository;

    @Autowired
    @Qualifier("timesheetRepository")
    protected ITimesheetRepository timesheetRepository;

    @SpyBean
    @Qualifier("usertaskAppService")
    protected UsertaskAppService usertaskAppService;

    @SpyBean
    @Qualifier("taskAppService")
    protected TaskAppService taskAppService;

    @SpyBean
    @Qualifier("usersAppService")
    protected UsersAppService usersAppService;

    @SpyBean
    protected LoggingHelper logHelper;

    @SpyBean
    protected Environment env;

    @Mock
    protected Logger loggerMock;

    protected UsertaskEntity usertask;

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
        em.createNativeQuery("truncate table timesheet.usertask").executeUpdate();
        em.createNativeQuery("truncate table timesheet.task").executeUpdate();
        em.createNativeQuery("truncate table timesheet.users").executeUpdate();
        em.createNativeQuery("truncate table timesheet.project").executeUpdate();
        em.createNativeQuery("truncate table timesheet.customer").executeUpdate();
        em.createNativeQuery("truncate table timesheet.timesheet").executeUpdate();
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

    public UsertaskEntity createEntity() {
        TaskEntity task = createTaskEntity();
        UsersEntity users = createUsersEntity();

        UsertaskEntity usertaskEntity = new UsertaskEntity();
        usertaskEntity.setId(1L);
        usertaskEntity.setVersiono(0L);
        usertaskEntity.setTask(task);
        usertaskEntity.setUsers(users);

        return usertaskEntity;
    }

    public CreateUsertaskInput createUsertaskInput() {
        CreateUsertaskInput usertaskInput = new CreateUsertaskInput();
        usertaskInput.setId(5L);

        return usertaskInput;
    }

    public UsertaskEntity createNewEntity() {
        UsertaskEntity usertask = new UsertaskEntity();
        usertask.setId(3L);

        return usertask;
    }

    public UsertaskEntity createUpdateEntity() {
        UsertaskEntity usertask = new UsertaskEntity();
        usertask.setId(4L);

        return usertask;
    }

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        final UsertaskController usertaskController = new UsertaskController(
            usertaskAppService,
            taskAppService,
            usersAppService,
            logHelper,
            env
        );
        when(logHelper.getLogger()).thenReturn(loggerMock);
        doNothing().when(loggerMock).error(anyString());

        this.mvc =
            MockMvcBuilders
                .standaloneSetup(usertaskController)
                .setCustomArgumentResolvers(sortArgumentResolver)
                .setControllerAdvice()
                .build();
    }

    @Before
    public void initTest() {
        usertask = createEntity();
        List<UsertaskEntity> list = usertask_repository.findAll();
        if (!list.contains(usertask)) {
            usertask = usertask_repository.save(usertask);
        }
    }

    @Test
    public void FindById_IdIsValid_ReturnStatusOk() throws Exception {
        mvc
            .perform(get("/usertask/" + usertask.getId() + "/").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }

    @Test
    public void FindById_IdIsNotValid_ReturnStatusNotFound() {
        org.assertj.core.api.Assertions
            .assertThatThrownBy(
                () ->
                    mvc.perform(get("/usertask/999").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
            )
            .hasCause(new EntityNotFoundException("Not found"));
    }

    @Test
    public void CreateUsertask_UsertaskDoesNotExist_ReturnStatusOk() throws Exception {
        CreateUsertaskInput usertaskInput = createUsertaskInput();

        TaskEntity task = createTaskEntity();

        usertaskInput.setTaskid(Long.parseLong(task.getId().toString()));

        UsersEntity users = createUsersEntity();

        usertaskInput.setUserid(Long.parseLong(users.getId().toString()));

        ObjectWriter ow = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            .writer()
            .withDefaultPrettyPrinter();

        String json = ow.writeValueAsString(usertaskInput);

        mvc.perform(post("/usertask").contentType(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isOk());
    }

    @Test
    public void DeleteUsertask_IdIsNotValid_ThrowEntityNotFoundException() {
        doReturn(null).when(usertaskAppService).findById(999L);
        org.assertj.core.api.Assertions
            .assertThatThrownBy(
                () ->
                    mvc
                        .perform(delete("/usertask/999").contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
            )
            .hasCause(new EntityNotFoundException("There does not exist a usertask with a id=999"));
    }

    @Test
    public void Delete_IdIsValid_ReturnStatusNoContent() throws Exception {
        UsertaskEntity entity = createNewEntity();
        entity.setVersiono(0L);
        TaskEntity task = createTaskEntity();
        entity.setTask(task);
        UsersEntity users = createUsersEntity();
        entity.setUsers(users);
        entity = usertask_repository.save(entity);

        FindUsertaskByIdOutput output = new FindUsertaskByIdOutput();
        output.setId(entity.getId());

        Mockito.doReturn(output).when(usertaskAppService).findById(entity.getId());

        //    Mockito.when(usertaskAppService.findById(entity.getId())).thenReturn(output);

        mvc
            .perform(delete("/usertask/" + entity.getId() + "/").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());
    }

    @Test
    public void UpdateUsertask_UsertaskDoesNotExist_ReturnStatusNotFound() throws Exception {
        doReturn(null).when(usertaskAppService).findById(999L);

        UpdateUsertaskInput usertask = new UpdateUsertaskInput();
        usertask.setId(999L);

        ObjectWriter ow = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            .writer()
            .withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(usertask);

        org.assertj.core.api.Assertions
            .assertThatThrownBy(
                () ->
                    mvc
                        .perform(put("/usertask/999").contentType(MediaType.APPLICATION_JSON).content(json))
                        .andExpect(status().isOk())
            )
            .hasCause(new EntityNotFoundException("Unable to update. Usertask with id=999 not found."));
    }

    @Test
    public void UpdateUsertask_UsertaskExists_ReturnStatusOk() throws Exception {
        UsertaskEntity entity = createUpdateEntity();
        entity.setVersiono(0L);

        TaskEntity task = createTaskEntity();
        entity.setTask(task);
        UsersEntity users = createUsersEntity();
        entity.setUsers(users);
        entity = usertask_repository.save(entity);
        FindUsertaskByIdOutput output = new FindUsertaskByIdOutput();
        output.setId(entity.getId());
        output.setVersiono(entity.getVersiono());

        Mockito.when(usertaskAppService.findById(entity.getId())).thenReturn(output);

        UpdateUsertaskInput usertaskInput = new UpdateUsertaskInput();
        usertaskInput.setId(entity.getId());

        usertaskInput.setTaskid(Long.parseLong(task.getId().toString()));
        usertaskInput.setUserid(Long.parseLong(users.getId().toString()));

        ObjectWriter ow = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            .writer()
            .withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(usertaskInput);

        mvc
            .perform(put("/usertask/" + entity.getId() + "/").contentType(MediaType.APPLICATION_JSON).content(json))
            .andExpect(status().isOk());

        UsertaskEntity de = createUpdateEntity();
        de.setId(entity.getId());
        usertask_repository.delete(de);
    }

    @Test
    public void FindAll_SearchIsNotNullAndPropertyIsValid_ReturnStatusOk() throws Exception {
        mvc
            .perform(get("/usertask?search=id[equals]=1&limit=10&offset=1").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }

    @Test
    public void FindAll_SearchIsNotNullAndPropertyIsNotValid_ThrowException() throws Exception {
        org.assertj.core.api.Assertions
            .assertThatThrownBy(
                () ->
                    mvc
                        .perform(
                            get("/usertask?search=usertaskid[equals]=1&limit=10&offset=1")
                                .contentType(MediaType.APPLICATION_JSON)
                        )
                        .andExpect(status().isOk())
            )
            .hasCause(new Exception("Wrong URL Format: Property usertaskid not found!"));
    }

    @Test
    public void GetTask_IdIsNotEmptyAndIdDoesNotExist_ReturnNotFound() {
        org.assertj.core.api.Assertions
            .assertThatThrownBy(
                () ->
                    mvc
                        .perform(get("/usertask/999/task").contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
            )
            .hasCause(new EntityNotFoundException("Not found"));
    }

    @Test
    public void GetTask_searchIsNotEmptyAndPropertyIsValid_ReturnList() throws Exception {
        mvc
            .perform(get("/usertask/" + usertask.getId() + "/task").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }

    @Test
    public void GetUsers_IdIsNotEmptyAndIdDoesNotExist_ReturnNotFound() {
        org.assertj.core.api.Assertions
            .assertThatThrownBy(
                () ->
                    mvc
                        .perform(get("/usertask/999/users").contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
            )
            .hasCause(new EntityNotFoundException("Not found"));
    }

    @Test
    public void GetUsers_searchIsNotEmptyAndPropertyIsValid_ReturnList() throws Exception {
        mvc
            .perform(get("/usertask/" + usertask.getId() + "/users").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }
}
