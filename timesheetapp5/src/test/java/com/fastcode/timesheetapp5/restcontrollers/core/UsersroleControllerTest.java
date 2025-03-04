package com.fastcode.timesheetapp5.restcontrollers.core;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fastcode.timesheetapp5.application.core.authorization.role.RoleAppService;
import com.fastcode.timesheetapp5.application.core.authorization.users.UsersAppService;
import com.fastcode.timesheetapp5.application.core.authorization.usersrole.UsersroleAppService;
import com.fastcode.timesheetapp5.application.core.authorization.usersrole.dto.*;
import com.fastcode.timesheetapp5.commons.logging.LoggingHelper;
import com.fastcode.timesheetapp5.commons.search.SearchUtils;
import com.fastcode.timesheetapp5.domain.core.authorization.role.IRoleRepository;
import com.fastcode.timesheetapp5.domain.core.authorization.role.RoleEntity;
import com.fastcode.timesheetapp5.domain.core.authorization.users.IUsersRepository;
import com.fastcode.timesheetapp5.domain.core.authorization.users.UsersEntity;
import com.fastcode.timesheetapp5.domain.core.authorization.usersrole.IUsersroleRepository;
import com.fastcode.timesheetapp5.domain.core.authorization.usersrole.UsersroleEntity;
import com.fastcode.timesheetapp5.domain.core.authorization.usersrole.UsersroleId;
import com.fastcode.timesheetapp5.domain.core.timesheet.ITimesheetRepository;
import com.fastcode.timesheetapp5.domain.core.timesheet.TimesheetEntity;
import com.fastcode.timesheetapp5.security.JWTAppService;
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
public class UsersroleControllerTest {

    @Autowired
    protected SortHandlerMethodArgumentResolver sortArgumentResolver;

    @Autowired
    @Qualifier("usersroleRepository")
    protected IUsersroleRepository usersrole_repository;

    @Autowired
    @Qualifier("roleRepository")
    protected IRoleRepository roleRepository;

    @Autowired
    @Qualifier("usersRepository")
    protected IUsersRepository usersRepository;

    @Autowired
    @Qualifier("timesheetRepository")
    protected ITimesheetRepository timesheetRepository;

    @SpyBean
    @Qualifier("usersroleAppService")
    protected UsersroleAppService usersroleAppService;

    @SpyBean
    @Qualifier("roleAppService")
    protected RoleAppService roleAppService;

    @SpyBean
    @Qualifier("usersAppService")
    protected UsersAppService usersAppService;

    @SpyBean
    protected JWTAppService jwtAppService;

    @SpyBean
    protected LoggingHelper logHelper;

    @SpyBean
    protected Environment env;

    @Mock
    protected Logger loggerMock;

    protected UsersroleEntity usersrole;

    protected MockMvc mvc;

    @Autowired
    EntityManagerFactory emf;

    static EntityManagerFactory emfs;

    int count = 10;

    int countRole = 10;
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
        em.createNativeQuery("truncate table timesheet.usersrole").executeUpdate();
        em.createNativeQuery("truncate table timesheet.role").executeUpdate();
        em.createNativeQuery("truncate table timesheet.users").executeUpdate();
        em.createNativeQuery("truncate table timesheet.timesheet").executeUpdate();
        em.createNativeQuery("SET REFERENTIAL_INTEGRITY TRUE").executeUpdate();
        em.getTransaction().commit();
    }

    public RoleEntity createRoleEntity() {
        if (countRole > 99) {
            countRole = 10;
        }

        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setDisplayName(String.valueOf(countRole));
        roleEntity.setId(Long.valueOf(countRole));
        roleEntity.setName(String.valueOf(countRole));
        roleEntity.setVersiono(0L);
        if (!roleRepository.findAll().contains(roleEntity)) {
            roleEntity = roleRepository.save(roleEntity);
        }
        countRole++;
        return roleEntity;
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
        usersEntity.setEmailaddress("bbc" + countUsers + "@d.c");
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

    public UsersroleEntity createEntity() {
        RoleEntity role = createRoleEntity();
        UsersEntity users = createUsersEntity();

        UsersroleEntity usersroleEntity = new UsersroleEntity();
        usersroleEntity.setRoleId(1L);
        usersroleEntity.setUsersId(1L);
        usersroleEntity.setVersiono(0L);
        usersroleEntity.setRole(role);
        usersroleEntity.setRoleId(Long.parseLong(role.getId().toString()));
        usersroleEntity.setUsers(users);
        usersroleEntity.setUsersId(Long.parseLong(users.getId().toString()));

        return usersroleEntity;
    }

    public CreateUsersroleInput createUsersroleInput() {
        CreateUsersroleInput usersroleInput = new CreateUsersroleInput();
        usersroleInput.setRoleId(5L);
        usersroleInput.setUsersId(5L);

        return usersroleInput;
    }

    public UsersroleEntity createNewEntity() {
        UsersroleEntity usersrole = new UsersroleEntity();
        usersrole.setRoleId(3L);
        usersrole.setUsersId(3L);

        return usersrole;
    }

    public UsersroleEntity createUpdateEntity() {
        UsersroleEntity usersrole = new UsersroleEntity();
        usersrole.setRoleId(4L);
        usersrole.setUsersId(4L);

        return usersrole;
    }

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        final UsersroleController usersroleController = new UsersroleController(
            usersroleAppService,
            roleAppService,
            usersAppService,
            jwtAppService,
            logHelper,
            env
        );
        when(logHelper.getLogger()).thenReturn(loggerMock);
        doNothing().when(loggerMock).error(anyString());

        this.mvc =
            MockMvcBuilders
                .standaloneSetup(usersroleController)
                .setCustomArgumentResolvers(sortArgumentResolver)
                .setControllerAdvice()
                .build();
    }

    @Before
    public void initTest() {
        usersrole = createEntity();
        List<UsersroleEntity> list = usersrole_repository.findAll();
        if (!list.contains(usersrole)) {
            usersrole = usersrole_repository.save(usersrole);
        }
    }

    @Test
    public void FindById_IdIsValid_ReturnStatusOk() throws Exception {
        mvc
            .perform(
                get("/usersrole/roleId=" + usersrole.getRoleId() + ",usersId=" + usersrole.getUsersId() + "/")
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
                        .perform(get("/usersrole/roleId=999,usersId=999").contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
            )
            .hasCause(new EntityNotFoundException("Not found"));
    }

    @Test
    public void CreateUsersrole_UsersroleDoesNotExist_ReturnStatusOk() throws Exception {
        CreateUsersroleInput usersroleInput = createUsersroleInput();

        RoleEntity role = createRoleEntity();

        usersroleInput.setRoleId(Long.parseLong(role.getId().toString()));

        UsersEntity users = createUsersEntity();

        usersroleInput.setUsersId(Long.parseLong(users.getId().toString()));

        doNothing().when(jwtAppService).deleteAllUserTokens(any(String.class));
        ObjectWriter ow = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            .writer()
            .withDefaultPrettyPrinter();

        String json = ow.writeValueAsString(usersroleInput);

        mvc
            .perform(post("/usersrole").contentType(MediaType.APPLICATION_JSON).content(json))
            .andExpect(status().isOk());
    }

    @Test
    public void CreateUsersrole_roleDoesNotExists_ThrowEntityNotFoundException() throws Exception {
        CreateUsersroleInput usersrole = createUsersroleInput();
        ObjectWriter ow = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            .writer()
            .withDefaultPrettyPrinter();

        String json = ow.writeValueAsString(usersrole);

        org.assertj.core.api.Assertions.assertThatThrownBy(
            () ->
                mvc
                    .perform(post("/usersrole").contentType(MediaType.APPLICATION_JSON).content(json))
                    .andExpect(status().isNotFound())
        );
    }

    @Test
    public void CreateUsersrole_usersDoesNotExists_ThrowEntityNotFoundException() throws Exception {
        CreateUsersroleInput usersrole = createUsersroleInput();
        ObjectWriter ow = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            .writer()
            .withDefaultPrettyPrinter();

        String json = ow.writeValueAsString(usersrole);

        org.assertj.core.api.Assertions.assertThatThrownBy(
            () ->
                mvc
                    .perform(post("/usersrole").contentType(MediaType.APPLICATION_JSON).content(json))
                    .andExpect(status().isNotFound())
        );
    }

    @Test
    public void DeleteUsersrole_IdIsNotValid_ThrowEntityNotFoundException() {
        doReturn(null).when(usersroleAppService).findById(new UsersroleId(999L, 999L));
        org.assertj.core.api.Assertions
            .assertThatThrownBy(
                () ->
                    mvc
                        .perform(delete("/usersrole/roleId=999,usersId=999").contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
            )
            .hasCause(new EntityNotFoundException("There does not exist a usersrole with a id=roleId=999,usersId=999"));
    }

    @Test
    public void Delete_IdIsValid_ReturnStatusNoContent() throws Exception {
        UsersroleEntity entity = createNewEntity();
        entity.setVersiono(0L);
        RoleEntity role = createRoleEntity();
        entity.setRoleId(Long.parseLong(role.getId().toString()));
        entity.setRole(role);
        UsersEntity users = createUsersEntity();
        entity.setUsersId(Long.parseLong(users.getId().toString()));
        entity.setUsers(users);
        entity = usersrole_repository.save(entity);

        FindUsersroleByIdOutput output = new FindUsersroleByIdOutput();
        output.setRoleId(entity.getRoleId());
        output.setUsersId(entity.getUsersId());

        //    Mockito.when(usersroleAppService.findById(new UsersroleId(entity.getRoleId(), entity.getUsersId()))).thenReturn(output);
        Mockito
            .doReturn(output)
            .when(usersroleAppService)
            .findById(new UsersroleId(entity.getRoleId(), entity.getUsersId()));

        doNothing().when(jwtAppService).deleteAllUserTokens(any(String.class));
        mvc
            .perform(
                delete("/usersrole/roleId=" + entity.getRoleId() + ",usersId=" + entity.getUsersId() + "/")
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isNoContent());
    }

    @Test
    public void UpdateUsersrole_UsersroleDoesNotExist_ReturnStatusNotFound() throws Exception {
        doReturn(null).when(usersroleAppService).findById(new UsersroleId(999L, 999L));

        UpdateUsersroleInput usersrole = new UpdateUsersroleInput();
        usersrole.setRoleId(999L);
        usersrole.setUsersId(999L);

        ObjectWriter ow = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            .writer()
            .withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(usersrole);

        org.assertj.core.api.Assertions
            .assertThatThrownBy(
                () ->
                    mvc
                        .perform(
                            put("/usersrole/roleId=999,usersId=999")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json)
                        )
                        .andExpect(status().isOk())
            )
            .hasCause(
                new EntityNotFoundException("Unable to update. Usersrole with id=roleId=999,usersId=999 not found.")
            );
    }

    @Test
    public void UpdateUsersrole_UsersroleExists_ReturnStatusOk() throws Exception {
        UsersroleEntity entity = createUpdateEntity();
        entity.setVersiono(0L);

        RoleEntity role = createRoleEntity();
        entity.setRoleId(Long.parseLong(role.getId().toString()));
        entity.setRole(role);
        UsersEntity users = createUsersEntity();
        entity.setUsersId(Long.parseLong(users.getId().toString()));
        entity.setUsers(users);
        entity = usersrole_repository.save(entity);
        FindUsersroleByIdOutput output = new FindUsersroleByIdOutput();
        output.setRoleId(entity.getRoleId());
        output.setUsersId(entity.getUsersId());
        output.setVersiono(entity.getVersiono());

        Mockito
            .when(usersroleAppService.findById(new UsersroleId(entity.getRoleId(), entity.getUsersId())))
            .thenReturn(output);

        UpdateUsersroleInput usersroleInput = new UpdateUsersroleInput();
        usersroleInput.setRoleId(entity.getRoleId());
        usersroleInput.setUsersId(entity.getUsersId());

        doNothing().when(jwtAppService).deleteAllUserTokens(any(String.class));

        ObjectWriter ow = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            .writer()
            .withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(usersroleInput);

        mvc
            .perform(
                put("/usersrole/roleId=" + entity.getRoleId() + ",usersId=" + entity.getUsersId() + "/")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(json)
            )
            .andExpect(status().isOk());

        UsersroleEntity de = createUpdateEntity();
        de.setRoleId(entity.getRoleId());
        de.setUsersId(entity.getUsersId());
        usersrole_repository.delete(de);
    }

    @Test
    public void FindAll_SearchIsNotNullAndPropertyIsValid_ReturnStatusOk() throws Exception {
        mvc
            .perform(
                get("/usersrole?search=roleId[equals]=1&limit=10&offset=1").contentType(MediaType.APPLICATION_JSON)
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
                            get("/usersrole?search=usersroleroleId[equals]=1&limit=10&offset=1")
                                .contentType(MediaType.APPLICATION_JSON)
                        )
                        .andExpect(status().isOk())
            )
            .hasCause(new Exception("Wrong URL Format: Property usersroleroleId not found!"));
    }

    @Test
    public void GetRole_IdIsNotEmptyAndIdIsNotValid_ThrowException() {
        org.assertj.core.api.Assertions
            .assertThatThrownBy(
                () ->
                    mvc
                        .perform(get("/usersrole/roleId999/role").contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
            )
            .hasCause(new EntityNotFoundException("Invalid id=roleId999"));
    }

    @Test
    public void GetRole_IdIsNotEmptyAndIdDoesNotExist_ReturnNotFound() {
        org.assertj.core.api.Assertions
            .assertThatThrownBy(
                () ->
                    mvc
                        .perform(get("/usersrole/roleId=999,usersId=999/role").contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
            )
            .hasCause(new EntityNotFoundException("Not found"));
    }

    @Test
    public void GetRole_searchIsNotEmptyAndPropertyIsValid_ReturnList() throws Exception {
        mvc
            .perform(
                get("/usersrole/roleId=" + usersrole.getRoleId() + ",usersId=" + usersrole.getUsersId() + "/role")
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isOk());
    }

    @Test
    public void GetUsers_IdIsNotEmptyAndIdIsNotValid_ThrowException() {
        org.assertj.core.api.Assertions
            .assertThatThrownBy(
                () ->
                    mvc
                        .perform(get("/usersrole/roleId999/users").contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
            )
            .hasCause(new EntityNotFoundException("Invalid id=roleId999"));
    }

    @Test
    public void GetUsers_IdIsNotEmptyAndIdDoesNotExist_ReturnNotFound() {
        org.assertj.core.api.Assertions
            .assertThatThrownBy(
                () ->
                    mvc
                        .perform(get("/usersrole/roleId=999,usersId=999/users").contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
            )
            .hasCause(new EntityNotFoundException("Not found"));
    }

    @Test
    public void GetUsers_searchIsNotEmptyAndPropertyIsValid_ReturnList() throws Exception {
        mvc
            .perform(
                get("/usersrole/roleId=" + usersrole.getRoleId() + ",usersId=" + usersrole.getUsersId() + "/users")
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isOk());
    }
}
