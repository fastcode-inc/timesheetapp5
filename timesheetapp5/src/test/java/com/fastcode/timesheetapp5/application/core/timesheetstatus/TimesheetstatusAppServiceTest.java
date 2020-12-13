package com.fastcode.timesheetapp5.application.core.timesheetstatus;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.fastcode.timesheetapp5.application.core.timesheetstatus.dto.*;
import com.fastcode.timesheetapp5.commons.logging.LoggingHelper;
import com.fastcode.timesheetapp5.commons.search.*;
import com.fastcode.timesheetapp5.domain.core.status.IStatusRepository;
import com.fastcode.timesheetapp5.domain.core.status.StatusEntity;
import com.fastcode.timesheetapp5.domain.core.timesheet.ITimesheetRepository;
import com.fastcode.timesheetapp5.domain.core.timesheet.TimesheetEntity;
import com.fastcode.timesheetapp5.domain.core.timesheetstatus.*;
import com.fastcode.timesheetapp5.domain.core.timesheetstatus.QTimesheetstatusEntity;
import com.fastcode.timesheetapp5.domain.core.timesheetstatus.TimesheetstatusEntity;
import com.fastcode.timesheetapp5.domain.core.timesheetstatus.TimesheetstatusId;
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
public class TimesheetstatusAppServiceTest {

    @InjectMocks
    @Spy
    protected TimesheetstatusAppService _appService;

    @Mock
    protected ITimesheetstatusRepository _timesheetstatusRepository;

    @Mock
    protected IStatusRepository _statusRepository;

    @Mock
    protected ITimesheetRepository _timesheetRepository;

    @Mock
    protected ITimesheetstatusMapper _mapper;

    @Mock
    protected Logger loggerMock;

    @Mock
    protected LoggingHelper logHelper;

    @Mock
    protected TimesheetstatusId timesheetstatusId;

    private static final Long ID = 15L;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(_appService);
        when(logHelper.getLogger()).thenReturn(loggerMock);
        doNothing().when(loggerMock).error(anyString());
    }

    @Test
    public void findTimesheetstatusById_IdIsNotNullAndIdDoesNotExist_ReturnNull() {
        Optional<TimesheetstatusEntity> nullOptional = Optional.ofNullable(null);
        Mockito.when(_timesheetstatusRepository.findById(any(TimesheetstatusId.class))).thenReturn(nullOptional);
        Assertions.assertThat(_appService.findById(timesheetstatusId)).isEqualTo(null);
    }

    @Test
    public void findTimesheetstatusById_IdIsNotNullAndIdExists_ReturnTimesheetstatus() {
        TimesheetstatusEntity timesheetstatus = mock(TimesheetstatusEntity.class);
        Optional<TimesheetstatusEntity> timesheetstatusOptional = Optional.of((TimesheetstatusEntity) timesheetstatus);
        Mockito
            .when(_timesheetstatusRepository.findById(any(TimesheetstatusId.class)))
            .thenReturn(timesheetstatusOptional);

        Assertions
            .assertThat(_appService.findById(timesheetstatusId))
            .isEqualTo(_mapper.timesheetstatusEntityToFindTimesheetstatusByIdOutput(timesheetstatus));
    }

    @Test
    public void createTimesheetstatus_TimesheetstatusIsNotNullAndTimesheetstatusDoesNotExist_StoreTimesheetstatus() {
        TimesheetstatusEntity timesheetstatusEntity = mock(TimesheetstatusEntity.class);
        CreateTimesheetstatusInput timesheetstatusInput = new CreateTimesheetstatusInput();

        StatusEntity status = mock(StatusEntity.class);
        Optional<StatusEntity> statusOptional = Optional.of((StatusEntity) status);
        timesheetstatusInput.setStatusid(15L);

        Mockito.when(_statusRepository.findById(any(Long.class))).thenReturn(statusOptional);

        TimesheetEntity timesheet = mock(TimesheetEntity.class);
        Optional<TimesheetEntity> timesheetOptional = Optional.of((TimesheetEntity) timesheet);
        timesheetstatusInput.setTimesheetid(15L);

        Mockito.when(_timesheetRepository.findById(any(Long.class))).thenReturn(timesheetOptional);

        Mockito
            .when(_mapper.createTimesheetstatusInputToTimesheetstatusEntity(any(CreateTimesheetstatusInput.class)))
            .thenReturn(timesheetstatusEntity);
        Mockito
            .when(_timesheetstatusRepository.save(any(TimesheetstatusEntity.class)))
            .thenReturn(timesheetstatusEntity);

        Assertions
            .assertThat(_appService.create(timesheetstatusInput))
            .isEqualTo(_mapper.timesheetstatusEntityToCreateTimesheetstatusOutput(timesheetstatusEntity));
    }

    @Test
    public void createTimesheetstatus_TimesheetstatusIsNotNullAndTimesheetstatusDoesNotExistAndChildIsNullAndChildIsNotMandatory_StoreTimesheetstatus() {
        TimesheetstatusEntity timesheetstatusEntity = mock(TimesheetstatusEntity.class);
        CreateTimesheetstatusInput timesheetstatus = mock(CreateTimesheetstatusInput.class);

        Mockito
            .when(_mapper.createTimesheetstatusInputToTimesheetstatusEntity(any(CreateTimesheetstatusInput.class)))
            .thenReturn(timesheetstatusEntity);
        Mockito
            .when(_timesheetstatusRepository.save(any(TimesheetstatusEntity.class)))
            .thenReturn(timesheetstatusEntity);
        Assertions
            .assertThat(_appService.create(timesheetstatus))
            .isEqualTo(_mapper.timesheetstatusEntityToCreateTimesheetstatusOutput(timesheetstatusEntity));
    }

    @Test
    public void updateTimesheetstatus_TimesheetstatusIsNotNullAndTimesheetstatusDoesNotExistAndChildIsNullAndChildIsNotMandatory_ReturnUpdatedTimesheetstatus() {
        TimesheetstatusEntity timesheetstatusEntity = mock(TimesheetstatusEntity.class);
        UpdateTimesheetstatusInput timesheetstatus = mock(UpdateTimesheetstatusInput.class);

        Mockito
            .when(_mapper.updateTimesheetstatusInputToTimesheetstatusEntity(any(UpdateTimesheetstatusInput.class)))
            .thenReturn(timesheetstatusEntity);
        Mockito
            .when(_timesheetstatusRepository.save(any(TimesheetstatusEntity.class)))
            .thenReturn(timesheetstatusEntity);
        Assertions
            .assertThat(_appService.update(timesheetstatusId, timesheetstatus))
            .isEqualTo(_mapper.timesheetstatusEntityToUpdateTimesheetstatusOutput(timesheetstatusEntity));
    }

    @Test
    public void updateTimesheetstatus_TimesheetstatusIdIsNotNullAndIdExists_ReturnUpdatedTimesheetstatus() {
        TimesheetstatusEntity timesheetstatusEntity = mock(TimesheetstatusEntity.class);
        UpdateTimesheetstatusInput timesheetstatus = mock(UpdateTimesheetstatusInput.class);

        Mockito
            .when(_mapper.updateTimesheetstatusInputToTimesheetstatusEntity(any(UpdateTimesheetstatusInput.class)))
            .thenReturn(timesheetstatusEntity);
        Mockito
            .when(_timesheetstatusRepository.save(any(TimesheetstatusEntity.class)))
            .thenReturn(timesheetstatusEntity);
        Assertions
            .assertThat(_appService.update(timesheetstatusId, timesheetstatus))
            .isEqualTo(_mapper.timesheetstatusEntityToUpdateTimesheetstatusOutput(timesheetstatusEntity));
    }

    @Test
    public void deleteTimesheetstatus_TimesheetstatusIsNotNullAndTimesheetstatusExists_TimesheetstatusRemoved() {
        TimesheetstatusEntity timesheetstatus = mock(TimesheetstatusEntity.class);
        Optional<TimesheetstatusEntity> timesheetstatusOptional = Optional.of((TimesheetstatusEntity) timesheetstatus);
        Mockito
            .when(_timesheetstatusRepository.findById(any(TimesheetstatusId.class)))
            .thenReturn(timesheetstatusOptional);

        _appService.delete(timesheetstatusId);
        verify(_timesheetstatusRepository).delete(timesheetstatus);
    }

    @Test
    public void find_ListIsEmpty_ReturnList() throws Exception {
        List<TimesheetstatusEntity> list = new ArrayList<>();
        Page<TimesheetstatusEntity> foundPage = new PageImpl(list);
        Pageable pageable = mock(Pageable.class);
        List<FindTimesheetstatusByIdOutput> output = new ArrayList<>();
        SearchCriteria search = new SearchCriteria();
        //		search.setType(1);
        //		search.setValue("xyz");
        //		search.setOperator("equals");

        Mockito.when(_appService.search(any(SearchCriteria.class))).thenReturn(new BooleanBuilder());
        Mockito
            .when(_timesheetstatusRepository.findAll(any(Predicate.class), any(Pageable.class)))
            .thenReturn(foundPage);
        Assertions.assertThat(_appService.find(search, pageable)).isEqualTo(output);
    }

    @Test
    public void find_ListIsNotEmpty_ReturnList() throws Exception {
        List<TimesheetstatusEntity> list = new ArrayList<>();
        TimesheetstatusEntity timesheetstatus = mock(TimesheetstatusEntity.class);
        list.add(timesheetstatus);
        Page<TimesheetstatusEntity> foundPage = new PageImpl(list);
        Pageable pageable = mock(Pageable.class);
        List<FindTimesheetstatusByIdOutput> output = new ArrayList<>();
        SearchCriteria search = new SearchCriteria();
        //		search.setType(1);
        //		search.setValue("xyz");
        //		search.setOperator("equals");
        output.add(_mapper.timesheetstatusEntityToFindTimesheetstatusByIdOutput(timesheetstatus));

        Mockito.when(_appService.search(any(SearchCriteria.class))).thenReturn(new BooleanBuilder());
        Mockito
            .when(_timesheetstatusRepository.findAll(any(Predicate.class), any(Pageable.class)))
            .thenReturn(foundPage);
        Assertions.assertThat(_appService.find(search, pageable)).isEqualTo(output);
    }

    @Test
    public void searchKeyValuePair_PropertyExists_ReturnBooleanBuilder() {
        QTimesheetstatusEntity timesheetstatus = QTimesheetstatusEntity.timesheetstatusEntity;
        SearchFields searchFields = new SearchFields();
        searchFields.setOperator("equals");
        searchFields.setSearchValue("xyz");
        Map<String, SearchFields> map = new HashMap<>();
        map.put("notes", searchFields);
        Map<String, String> searchMap = new HashMap<>();
        searchMap.put("xyz", String.valueOf(ID));
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(timesheetstatus.notes.eq("xyz"));
        Assertions.assertThat(_appService.searchKeyValuePair(timesheetstatus, map, searchMap)).isEqualTo(builder);
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
        list.add("notes");
        _appService.checkProperties(list);
    }

    @Test
    public void search_SearchIsNotNullAndSearchContainsCaseThree_ReturnBooleanBuilder() throws Exception {
        Map<String, SearchFields> map = new HashMap<>();
        QTimesheetstatusEntity timesheetstatus = QTimesheetstatusEntity.timesheetstatusEntity;
        List<SearchFields> fieldsList = new ArrayList<>();
        SearchFields fields = new SearchFields();
        SearchCriteria search = new SearchCriteria();
        search.setType(3);
        search.setValue("xyz");
        search.setOperator("equals");
        fields.setFieldName("notes");
        fields.setOperator("equals");
        fields.setSearchValue("xyz");
        fieldsList.add(fields);
        search.setFields(fieldsList);
        BooleanBuilder builder = new BooleanBuilder();
        builder.or(timesheetstatus.notes.eq("xyz"));
        Mockito.doNothing().when(_appService).checkProperties(any(List.class));
        Mockito
            .doReturn(builder)
            .when(_appService)
            .searchKeyValuePair(any(QTimesheetstatusEntity.class), any(HashMap.class), any(HashMap.class));

        Assertions.assertThat(_appService.search(search)).isEqualTo(builder);
    }

    @Test
    public void search_StringIsNull_ReturnNull() throws Exception {
        Assertions.assertThat(_appService.search(null)).isEqualTo(null);
    }

    //Status
    @Test
    public void GetStatus_IfTimesheetstatusIdAndStatusIdIsNotNullAndTimesheetstatusExists_ReturnStatus() {
        TimesheetstatusEntity timesheetstatus = mock(TimesheetstatusEntity.class);
        Optional<TimesheetstatusEntity> timesheetstatusOptional = Optional.of((TimesheetstatusEntity) timesheetstatus);
        StatusEntity statusEntity = mock(StatusEntity.class);

        Mockito
            .when(_timesheetstatusRepository.findById(any(TimesheetstatusId.class)))
            .thenReturn(timesheetstatusOptional);
        Mockito.when(timesheetstatus.getStatus()).thenReturn(statusEntity);
        Assertions
            .assertThat(_appService.getStatus(timesheetstatusId))
            .isEqualTo(_mapper.statusEntityToGetStatusOutput(statusEntity, timesheetstatus));
    }

    @Test
    public void GetStatus_IfTimesheetstatusIdAndStatusIdIsNotNullAndTimesheetstatusDoesNotExist_ReturnNull() {
        Optional<TimesheetstatusEntity> nullOptional = Optional.ofNullable(null);
        Mockito.when(_timesheetstatusRepository.findById(any(TimesheetstatusId.class))).thenReturn(nullOptional);
        Assertions.assertThat(_appService.getStatus(timesheetstatusId)).isEqualTo(null);
    }

    //Timesheet
    @Test
    public void GetTimesheet_IfTimesheetstatusIdAndTimesheetIdIsNotNullAndTimesheetstatusExists_ReturnTimesheet() {
        TimesheetstatusEntity timesheetstatus = mock(TimesheetstatusEntity.class);
        Optional<TimesheetstatusEntity> timesheetstatusOptional = Optional.of((TimesheetstatusEntity) timesheetstatus);
        TimesheetEntity timesheetEntity = mock(TimesheetEntity.class);

        Mockito
            .when(_timesheetstatusRepository.findById(any(TimesheetstatusId.class)))
            .thenReturn(timesheetstatusOptional);
        Mockito.when(timesheetstatus.getTimesheet()).thenReturn(timesheetEntity);
        Assertions
            .assertThat(_appService.getTimesheet(timesheetstatusId))
            .isEqualTo(_mapper.timesheetEntityToGetTimesheetOutput(timesheetEntity, timesheetstatus));
    }

    @Test
    public void GetTimesheet_IfTimesheetstatusIdAndTimesheetIdIsNotNullAndTimesheetstatusDoesNotExist_ReturnNull() {
        Optional<TimesheetstatusEntity> nullOptional = Optional.ofNullable(null);
        Mockito.when(_timesheetstatusRepository.findById(any(TimesheetstatusId.class))).thenReturn(nullOptional);
        Assertions.assertThat(_appService.getTimesheet(timesheetstatusId)).isEqualTo(null);
    }

    @Test
    public void ParseTimesheetstatusKey_KeysStringIsNotEmptyAndKeyValuePairExists_ReturnTimesheetstatusId() {
        String keyString = "statusid=15,timesheetid=15";

        TimesheetstatusId timesheetstatusId = new TimesheetstatusId();
        timesheetstatusId.setStatusid(15L);
        timesheetstatusId.setTimesheetid(15L);

        Assertions
            .assertThat(_appService.parseTimesheetstatusKey(keyString))
            .isEqualToComparingFieldByField(timesheetstatusId);
    }

    @Test
    public void ParseTimesheetstatusKey_KeysStringIsEmpty_ReturnNull() {
        String keyString = "";
        Assertions.assertThat(_appService.parseTimesheetstatusKey(keyString)).isEqualTo(null);
    }

    @Test
    public void ParseTimesheetstatusKey_KeysStringIsNotEmptyAndKeyValuePairDoesNotExist_ReturnNull() {
        String keyString = "statusid";

        Assertions.assertThat(_appService.parseTimesheetstatusKey(keyString)).isEqualTo(null);
    }
}
