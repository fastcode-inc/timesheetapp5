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

import com.fastcode.timesheetapp5.application.core.customer.CustomerAppService;
import com.fastcode.timesheetapp5.application.core.project.ProjectAppService;
import com.fastcode.timesheetapp5.application.core.project.dto.*;
import com.fastcode.timesheetapp5.application.core.task.TaskAppService;
import com.fastcode.timesheetapp5.commons.logging.LoggingHelper;
import com.fastcode.timesheetapp5.commons.search.SearchUtils;
import com.fastcode.timesheetapp5.domain.core.customer.CustomerEntity;
import com.fastcode.timesheetapp5.domain.core.customer.ICustomerRepository;
import com.fastcode.timesheetapp5.domain.core.project.IProjectRepository;
import com.fastcode.timesheetapp5.domain.core.project.IProjectRepository;
import com.fastcode.timesheetapp5.domain.core.project.ProjectEntity;
import com.fastcode.timesheetapp5.domain.core.project.ProjectEntity;
import com.fastcode.timesheetapp5.domain.core.task.ITaskRepository;
import com.fastcode.timesheetapp5.domain.core.task.TaskEntity;
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
public class ProjectControllerTest {

    @Autowired
    protected SortHandlerMethodArgumentResolver sortArgumentResolver;

    @Autowired
    @Qualifier("projectRepository")
    protected IProjectRepository project_repository;

    @Autowired
    @Qualifier("customerRepository")
    protected ICustomerRepository customerRepository;

    @Autowired
    @Qualifier("projectRepository")
    protected IProjectRepository projectRepository;

    @Autowired
    @Qualifier("taskRepository")
    protected ITaskRepository taskRepository;

    @SpyBean
    @Qualifier("projectAppService")
    protected ProjectAppService projectAppService;

    @SpyBean
    @Qualifier("customerAppService")
    protected CustomerAppService customerAppService;

    @SpyBean
    @Qualifier("taskAppService")
    protected TaskAppService taskAppService;

    @SpyBean
    protected LoggingHelper logHelper;

    @SpyBean
    protected Environment env;

    @Mock
    protected Logger loggerMock;

    protected ProjectEntity project;

    protected MockMvc mvc;

    @Autowired
    EntityManagerFactory emf;

    static EntityManagerFactory emfs;

    int count = 10;

    int countProject = 10;
    int countTask = 10;
    int countCustomer = 10;

    @PostConstruct
    public void init() {
        emfs = emf;
    }

    @AfterClass
    public static void cleanup() {
        EntityManager em = emfs.createEntityManager();
        em.getTransaction().begin();
        em.createNativeQuery("SET REFERENTIAL_INTEGRITY FALSE").executeUpdate();
        em.createNativeQuery("truncate table timesheet.project").executeUpdate();
        em.createNativeQuery("truncate table timesheet.customer").executeUpdate();
        em.createNativeQuery("truncate table timesheet.project").executeUpdate();
        em.createNativeQuery("truncate table timesheet.task").executeUpdate();
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

    public ProjectEntity createEntity() {
        CustomerEntity customer = createCustomerEntity();

        ProjectEntity projectEntity = new ProjectEntity();
        projectEntity.setDescription("1");
        projectEntity.setEnddate(SearchUtils.stringToLocalDateTime("1996-09-01 09:15:22"));
        projectEntity.setId(1L);
        projectEntity.setName("1");
        projectEntity.setStartdate(SearchUtils.stringToLocalDateTime("1996-09-01 09:15:22"));
        projectEntity.setVersiono(0L);
        projectEntity.setCustomer(customer);

        return projectEntity;
    }

    public CreateProjectInput createProjectInput() {
        CreateProjectInput projectInput = new CreateProjectInput();
        projectInput.setDescription("5");
        projectInput.setEnddate(SearchUtils.stringToLocalDateTime("1996-08-10 05:25:22"));
        projectInput.setName("5");
        projectInput.setStartdate(SearchUtils.stringToLocalDateTime("1996-08-10 05:25:22"));

        return projectInput;
    }

    public ProjectEntity createNewEntity() {
        ProjectEntity project = new ProjectEntity();
        project.setDescription("3");
        project.setEnddate(SearchUtils.stringToLocalDateTime("1996-08-11 05:35:22"));
        project.setId(3L);
        project.setName("3");
        project.setStartdate(SearchUtils.stringToLocalDateTime("1996-08-11 05:35:22"));

        return project;
    }

    public ProjectEntity createUpdateEntity() {
        ProjectEntity project = new ProjectEntity();
        project.setDescription("4");
        project.setEnddate(SearchUtils.stringToLocalDateTime("1996-09-09 05:45:22"));
        project.setId(4L);
        project.setName("4");
        project.setStartdate(SearchUtils.stringToLocalDateTime("1996-09-09 05:45:22"));

        return project;
    }

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        final ProjectController projectController = new ProjectController(
            projectAppService,
            customerAppService,
            taskAppService,
            logHelper,
            env
        );
        when(logHelper.getLogger()).thenReturn(loggerMock);
        doNothing().when(loggerMock).error(anyString());

        this.mvc =
            MockMvcBuilders
                .standaloneSetup(projectController)
                .setCustomArgumentResolvers(sortArgumentResolver)
                .setControllerAdvice()
                .build();
    }

    @Before
    public void initTest() {
        project = createEntity();
        List<ProjectEntity> list = project_repository.findAll();
        if (!list.contains(project)) {
            project = project_repository.save(project);
        }
    }

    @Test
    public void FindById_IdIsValid_ReturnStatusOk() throws Exception {
        mvc
            .perform(get("/project/" + project.getId() + "/").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }

    @Test
    public void FindById_IdIsNotValid_ReturnStatusNotFound() {
        org.assertj.core.api.Assertions
            .assertThatThrownBy(
                () ->
                    mvc.perform(get("/project/999").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
            )
            .hasCause(new EntityNotFoundException("Not found"));
    }

    @Test
    public void CreateProject_ProjectDoesNotExist_ReturnStatusOk() throws Exception {
        CreateProjectInput projectInput = createProjectInput();

        CustomerEntity customer = createCustomerEntity();

        projectInput.setCustomerid(Long.parseLong(customer.getCustomerid().toString()));

        ObjectWriter ow = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            .writer()
            .withDefaultPrettyPrinter();

        String json = ow.writeValueAsString(projectInput);

        mvc.perform(post("/project").contentType(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isOk());
    }

    @Test
    public void DeleteProject_IdIsNotValid_ThrowEntityNotFoundException() {
        doReturn(null).when(projectAppService).findById(999L);
        org.assertj.core.api.Assertions
            .assertThatThrownBy(
                () ->
                    mvc
                        .perform(delete("/project/999").contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
            )
            .hasCause(new EntityNotFoundException("There does not exist a project with a id=999"));
    }

    @Test
    public void Delete_IdIsValid_ReturnStatusNoContent() throws Exception {
        ProjectEntity entity = createNewEntity();
        entity.setVersiono(0L);
        CustomerEntity customer = createCustomerEntity();
        entity.setCustomer(customer);
        entity = project_repository.save(entity);

        FindProjectByIdOutput output = new FindProjectByIdOutput();
        output.setId(entity.getId());
        output.setName(entity.getName());

        Mockito.doReturn(output).when(projectAppService).findById(entity.getId());

        //    Mockito.when(projectAppService.findById(entity.getId())).thenReturn(output);

        mvc
            .perform(delete("/project/" + entity.getId() + "/").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());
    }

    @Test
    public void UpdateProject_ProjectDoesNotExist_ReturnStatusNotFound() throws Exception {
        doReturn(null).when(projectAppService).findById(999L);

        UpdateProjectInput project = new UpdateProjectInput();
        project.setDescription("999");
        project.setEnddate(SearchUtils.stringToLocalDateTime("1996-09-28 07:15:22"));
        project.setId(999L);
        project.setName("999");
        project.setStartdate(SearchUtils.stringToLocalDateTime("1996-09-28 07:15:22"));

        ObjectWriter ow = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            .writer()
            .withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(project);

        org.assertj.core.api.Assertions
            .assertThatThrownBy(
                () ->
                    mvc
                        .perform(put("/project/999").contentType(MediaType.APPLICATION_JSON).content(json))
                        .andExpect(status().isOk())
            )
            .hasCause(new EntityNotFoundException("Unable to update. Project with id=999 not found."));
    }

    @Test
    public void UpdateProject_ProjectExists_ReturnStatusOk() throws Exception {
        ProjectEntity entity = createUpdateEntity();
        entity.setVersiono(0L);

        CustomerEntity customer = createCustomerEntity();
        entity.setCustomer(customer);
        entity = project_repository.save(entity);
        FindProjectByIdOutput output = new FindProjectByIdOutput();
        output.setDescription(entity.getDescription());
        output.setEnddate(entity.getEnddate());
        output.setId(entity.getId());
        output.setName(entity.getName());
        output.setStartdate(entity.getStartdate());
        output.setVersiono(entity.getVersiono());

        Mockito.when(projectAppService.findById(entity.getId())).thenReturn(output);

        UpdateProjectInput projectInput = new UpdateProjectInput();
        projectInput.setId(entity.getId());
        projectInput.setName(entity.getName());

        projectInput.setCustomerid(Long.parseLong(customer.getCustomerid().toString()));

        ObjectWriter ow = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            .writer()
            .withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(projectInput);

        mvc
            .perform(put("/project/" + entity.getId() + "/").contentType(MediaType.APPLICATION_JSON).content(json))
            .andExpect(status().isOk());

        ProjectEntity de = createUpdateEntity();
        de.setId(entity.getId());
        project_repository.delete(de);
    }

    @Test
    public void FindAll_SearchIsNotNullAndPropertyIsValid_ReturnStatusOk() throws Exception {
        mvc
            .perform(get("/project?search=id[equals]=1&limit=10&offset=1").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }

    @Test
    public void FindAll_SearchIsNotNullAndPropertyIsNotValid_ThrowException() throws Exception {
        org.assertj.core.api.Assertions
            .assertThatThrownBy(
                () ->
                    mvc
                        .perform(
                            get("/project?search=projectid[equals]=1&limit=10&offset=1")
                                .contentType(MediaType.APPLICATION_JSON)
                        )
                        .andExpect(status().isOk())
            )
            .hasCause(new Exception("Wrong URL Format: Property projectid not found!"));
    }

    @Test
    public void GetCustomer_IdIsNotEmptyAndIdDoesNotExist_ReturnNotFound() {
        org.assertj.core.api.Assertions
            .assertThatThrownBy(
                () ->
                    mvc
                        .perform(get("/project/999/customer").contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
            )
            .hasCause(new EntityNotFoundException("Not found"));
    }

    @Test
    public void GetCustomer_searchIsNotEmptyAndPropertyIsValid_ReturnList() throws Exception {
        mvc
            .perform(get("/project/" + project.getId() + "/customer").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }

    @Test
    public void GetTasks_searchIsNotEmptyAndPropertyIsNotValid_ThrowException() {
        Map<String, String> joinCol = new HashMap<String, String>();
        joinCol.put("id", "1");

        Mockito.when(projectAppService.parseTasksJoinColumn("projectid")).thenReturn(joinCol);
        org.assertj.core.api.Assertions
            .assertThatThrownBy(
                () ->
                    mvc
                        .perform(
                            get("/project/1/tasks?search=abc[equals]=1&limit=10&offset=1")
                                .contentType(MediaType.APPLICATION_JSON)
                        )
                        .andExpect(status().isOk())
            )
            .hasCause(new Exception("Wrong URL Format: Property abc not found!"));
    }

    @Test
    public void GetTasks_searchIsNotEmptyAndPropertyIsValid_ReturnList() throws Exception {
        Map<String, String> joinCol = new HashMap<String, String>();
        joinCol.put("id", "1");

        Mockito.when(projectAppService.parseTasksJoinColumn("projectid")).thenReturn(joinCol);
        mvc
            .perform(
                get("/project/1/tasks?search=projectid[equals]=1&limit=10&offset=1")
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isOk());
    }

    @Test
    public void GetTasks_searchIsNotEmpty() {
        Mockito.when(projectAppService.parseTasksJoinColumn(anyString())).thenReturn(null);

        org.assertj.core.api.Assertions
            .assertThatThrownBy(
                () ->
                    mvc
                        .perform(
                            get("/project/1/tasks?search=projectid[equals]=1&limit=10&offset=1")
                                .contentType(MediaType.APPLICATION_JSON)
                        )
                        .andExpect(status().isOk())
            )
            .hasCause(new EntityNotFoundException("Invalid join column"));
    }
}
