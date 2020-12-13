package com.fastcode.timesheetapp5.application.core.status;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.fastcode.timesheetapp5.application.core.status.dto.*;
import com.fastcode.timesheetapp5.commons.logging.LoggingHelper;
import com.fastcode.timesheetapp5.commons.search.*;
import com.fastcode.timesheetapp5.domain.core.status.*;
import com.fastcode.timesheetapp5.domain.core.status.QStatusEntity;
import com.fastcode.timesheetapp5.domain.core.status.StatusEntity;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import java.time.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.slf4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
public class StatusAppServiceTest {

    @InjectMocks
    @Spy
    protected StatusAppService _appService;

    @Mock
    protected IStatusRepository _statusRepository;

    @Mock
    protected IStatusMapper _mapper;

    @Mock
    protected Logger loggerMock;

    @Mock
    protected LoggingHelper logHelper;

    protected static Long ID = 15L;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(_appService);
        when(logHelper.getLogger()).thenReturn(loggerMock);
        doNothing().when(loggerMock).error(anyString());
    }

    @Test
    public void findStatusById_IdIsNotNullAndIdDoesNotExist_ReturnNull() {
        Optional<StatusEntity> nullOptional = Optional.ofNullable(null);
        Mockito.when(_statusRepository.findById(anyLong())).thenReturn(nullOptional);
        Assertions.assertThat(_appService.findById(ID)).isEqualTo(null);
    }

    @Test
    public void findStatusById_IdIsNotNullAndIdExists_ReturnStatus() {
        StatusEntity status = mock(StatusEntity.class);
        Optional<StatusEntity> statusOptional = Optional.of((StatusEntity) status);
        Mockito.when(_statusRepository.findById(anyLong())).thenReturn(statusOptional);

        Assertions.assertThat(_appService.findById(ID)).isEqualTo(_mapper.statusEntityToFindStatusByIdOutput(status));
    }

    @Test
    public void createStatus_StatusIsNotNullAndStatusDoesNotExist_StoreStatus() {
        StatusEntity statusEntity = mock(StatusEntity.class);
        CreateStatusInput statusInput = new CreateStatusInput();

        Mockito.when(_mapper.createStatusInputToStatusEntity(any(CreateStatusInput.class))).thenReturn(statusEntity);
        Mockito.when(_statusRepository.save(any(StatusEntity.class))).thenReturn(statusEntity);

        Assertions
            .assertThat(_appService.create(statusInput))
            .isEqualTo(_mapper.statusEntityToCreateStatusOutput(statusEntity));
    }

    @Test
    public void updateStatus_StatusIdIsNotNullAndIdExists_ReturnUpdatedStatus() {
        StatusEntity statusEntity = mock(StatusEntity.class);
        UpdateStatusInput status = mock(UpdateStatusInput.class);

        Mockito.when(_mapper.updateStatusInputToStatusEntity(any(UpdateStatusInput.class))).thenReturn(statusEntity);
        Mockito.when(_statusRepository.save(any(StatusEntity.class))).thenReturn(statusEntity);
        Assertions
            .assertThat(_appService.update(ID, status))
            .isEqualTo(_mapper.statusEntityToUpdateStatusOutput(statusEntity));
    }

    @Test
    public void deleteStatus_StatusIsNotNullAndStatusExists_StatusRemoved() {
        StatusEntity status = mock(StatusEntity.class);
        Optional<StatusEntity> statusOptional = Optional.of((StatusEntity) status);
        Mockito.when(_statusRepository.findById(anyLong())).thenReturn(statusOptional);

        _appService.delete(ID);
        verify(_statusRepository).delete(status);
    }

    @Test
    public void find_ListIsEmpty_ReturnList() throws Exception {
        List<StatusEntity> list = new ArrayList<>();
        Page<StatusEntity> foundPage = new PageImpl(list);
        Pageable pageable = mock(Pageable.class);
        List<FindStatusByIdOutput> output = new ArrayList<>();
        SearchCriteria search = new SearchCriteria();
        //		search.setType(1);
        //		search.setValue("xyz");
        //		search.setOperator("equals");

        Mockito.when(_appService.search(any(SearchCriteria.class))).thenReturn(new BooleanBuilder());
        Mockito.when(_statusRepository.findAll(any(Predicate.class), any(Pageable.class))).thenReturn(foundPage);
        Assertions.assertThat(_appService.find(search, pageable)).isEqualTo(output);
    }

    @Test
    public void find_ListIsNotEmpty_ReturnList() throws Exception {
        List<StatusEntity> list = new ArrayList<>();
        StatusEntity status = mock(StatusEntity.class);
        list.add(status);
        Page<StatusEntity> foundPage = new PageImpl(list);
        Pageable pageable = mock(Pageable.class);
        List<FindStatusByIdOutput> output = new ArrayList<>();
        SearchCriteria search = new SearchCriteria();
        //		search.setType(1);
        //		search.setValue("xyz");
        //		search.setOperator("equals");
        output.add(_mapper.statusEntityToFindStatusByIdOutput(status));

        Mockito.when(_appService.search(any(SearchCriteria.class))).thenReturn(new BooleanBuilder());
        Mockito.when(_statusRepository.findAll(any(Predicate.class), any(Pageable.class))).thenReturn(foundPage);
        Assertions.assertThat(_appService.find(search, pageable)).isEqualTo(output);
    }

    @Test
    public void searchKeyValuePair_PropertyExists_ReturnBooleanBuilder() {
        QStatusEntity status = QStatusEntity.statusEntity;
        SearchFields searchFields = new SearchFields();
        searchFields.setOperator("equals");
        searchFields.setSearchValue("xyz");
        Map<String, SearchFields> map = new HashMap<>();
        map.put("description", searchFields);
        Map<String, String> searchMap = new HashMap<>();
        searchMap.put("xyz", String.valueOf(ID));
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(status.description.eq("xyz"));
        Assertions.assertThat(_appService.searchKeyValuePair(status, map, searchMap)).isEqualTo(builder);
    }

    @Test(expected = Exception.class)
    public void checkProperties_PropertyDoesNotExist_ThrowException() throws Exception {
        List<String> list = new ArrayList<>();
        list.add("xyz");
        _appService.checkProperties(list);
    }

    @Test
    public void checkProperties_PropertyExists_ReturnNothing() throws Exception {
        List<String> list = new ArrayList<>();
        list.add("description");
        _appService.checkProperties(list);
    }

    @Test
    public void search_SearchIsNotNullAndSearchContainsCaseThree_ReturnBooleanBuilder() throws Exception {
        Map<String, SearchFields> map = new HashMap<>();
        QStatusEntity status = QStatusEntity.statusEntity;
        List<SearchFields> fieldsList = new ArrayList<>();
        SearchFields fields = new SearchFields();
        SearchCriteria search = new SearchCriteria();
        search.setType(3);
        search.setValue("xyz");
        search.setOperator("equals");
        fields.setFieldName("description");
        fields.setOperator("equals");
        fields.setSearchValue("xyz");
        fieldsList.add(fields);
        search.setFields(fieldsList);
        BooleanBuilder builder = new BooleanBuilder();
        builder.or(status.description.eq("xyz"));
        Mockito.doNothing().when(_appService).checkProperties(any(List.class));
        Mockito
            .doReturn(builder)
            .when(_appService)
            .searchKeyValuePair(any(QStatusEntity.class), any(HashMap.class), any(HashMap.class));

        Assertions.assertThat(_appService.search(search)).isEqualTo(builder);
    }

    @Test
    public void search_StringIsNull_ReturnNull() throws Exception {
        Assertions.assertThat(_appService.search(null)).isEqualTo(null);
    }

    @Test
    public void ParsetimesheetstatusJoinColumn_KeysStringIsNotEmptyAndKeyValuePairDoesNotExist_ReturnNull() {
        Map<String, String> joinColumnMap = new HashMap<String, String>();
        String keyString = "15";
        joinColumnMap.put("statusid", keyString);
        Assertions.assertThat(_appService.parseTimesheetstatusJoinColumn(keyString)).isEqualTo(joinColumnMap);
    }
}
