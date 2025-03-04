package com.fastcode.timesheetapp5.application.core.customer;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.fastcode.timesheetapp5.application.core.customer.dto.*;
import com.fastcode.timesheetapp5.commons.logging.LoggingHelper;
import com.fastcode.timesheetapp5.commons.search.*;
import com.fastcode.timesheetapp5.domain.core.customer.*;
import com.fastcode.timesheetapp5.domain.core.customer.CustomerEntity;
import com.fastcode.timesheetapp5.domain.core.customer.QCustomerEntity;
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
public class CustomerAppServiceTest {

    @InjectMocks
    @Spy
    protected CustomerAppService _appService;

    @Mock
    protected ICustomerRepository _customerRepository;

    @Mock
    protected ICustomerMapper _mapper;

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
    public void findCustomerById_IdIsNotNullAndIdDoesNotExist_ReturnNull() {
        Optional<CustomerEntity> nullOptional = Optional.ofNullable(null);
        Mockito.when(_customerRepository.findById(anyLong())).thenReturn(nullOptional);
        Assertions.assertThat(_appService.findById(ID)).isEqualTo(null);
    }

    @Test
    public void findCustomerById_IdIsNotNullAndIdExists_ReturnCustomer() {
        CustomerEntity customer = mock(CustomerEntity.class);
        Optional<CustomerEntity> customerOptional = Optional.of((CustomerEntity) customer);
        Mockito.when(_customerRepository.findById(anyLong())).thenReturn(customerOptional);

        Assertions
            .assertThat(_appService.findById(ID))
            .isEqualTo(_mapper.customerEntityToFindCustomerByIdOutput(customer));
    }

    @Test
    public void createCustomer_CustomerIsNotNullAndCustomerDoesNotExist_StoreCustomer() {
        CustomerEntity customerEntity = mock(CustomerEntity.class);
        CreateCustomerInput customerInput = new CreateCustomerInput();

        Mockito
            .when(_mapper.createCustomerInputToCustomerEntity(any(CreateCustomerInput.class)))
            .thenReturn(customerEntity);
        Mockito.when(_customerRepository.save(any(CustomerEntity.class))).thenReturn(customerEntity);

        Assertions
            .assertThat(_appService.create(customerInput))
            .isEqualTo(_mapper.customerEntityToCreateCustomerOutput(customerEntity));
    }

    @Test
    public void updateCustomer_CustomerIdIsNotNullAndIdExists_ReturnUpdatedCustomer() {
        CustomerEntity customerEntity = mock(CustomerEntity.class);
        UpdateCustomerInput customer = mock(UpdateCustomerInput.class);

        Mockito
            .when(_mapper.updateCustomerInputToCustomerEntity(any(UpdateCustomerInput.class)))
            .thenReturn(customerEntity);
        Mockito.when(_customerRepository.save(any(CustomerEntity.class))).thenReturn(customerEntity);
        Assertions
            .assertThat(_appService.update(ID, customer))
            .isEqualTo(_mapper.customerEntityToUpdateCustomerOutput(customerEntity));
    }

    @Test
    public void deleteCustomer_CustomerIsNotNullAndCustomerExists_CustomerRemoved() {
        CustomerEntity customer = mock(CustomerEntity.class);
        Optional<CustomerEntity> customerOptional = Optional.of((CustomerEntity) customer);
        Mockito.when(_customerRepository.findById(anyLong())).thenReturn(customerOptional);

        _appService.delete(ID);
        verify(_customerRepository).delete(customer);
    }

    @Test
    public void find_ListIsEmpty_ReturnList() throws Exception {
        List<CustomerEntity> list = new ArrayList<>();
        Page<CustomerEntity> foundPage = new PageImpl(list);
        Pageable pageable = mock(Pageable.class);
        List<FindCustomerByIdOutput> output = new ArrayList<>();
        SearchCriteria search = new SearchCriteria();
        //		search.setType(1);
        //		search.setValue("xyz");
        //		search.setOperator("equals");

        Mockito.when(_appService.search(any(SearchCriteria.class))).thenReturn(new BooleanBuilder());
        Mockito.when(_customerRepository.findAll(any(Predicate.class), any(Pageable.class))).thenReturn(foundPage);
        Assertions.assertThat(_appService.find(search, pageable)).isEqualTo(output);
    }

    @Test
    public void find_ListIsNotEmpty_ReturnList() throws Exception {
        List<CustomerEntity> list = new ArrayList<>();
        CustomerEntity customer = mock(CustomerEntity.class);
        list.add(customer);
        Page<CustomerEntity> foundPage = new PageImpl(list);
        Pageable pageable = mock(Pageable.class);
        List<FindCustomerByIdOutput> output = new ArrayList<>();
        SearchCriteria search = new SearchCriteria();
        //		search.setType(1);
        //		search.setValue("xyz");
        //		search.setOperator("equals");
        output.add(_mapper.customerEntityToFindCustomerByIdOutput(customer));

        Mockito.when(_appService.search(any(SearchCriteria.class))).thenReturn(new BooleanBuilder());
        Mockito.when(_customerRepository.findAll(any(Predicate.class), any(Pageable.class))).thenReturn(foundPage);
        Assertions.assertThat(_appService.find(search, pageable)).isEqualTo(output);
    }

    @Test
    public void searchKeyValuePair_PropertyExists_ReturnBooleanBuilder() {
        QCustomerEntity customer = QCustomerEntity.customerEntity;
        SearchFields searchFields = new SearchFields();
        searchFields.setOperator("equals");
        searchFields.setSearchValue("xyz");
        Map<String, SearchFields> map = new HashMap<>();
        map.put("description", searchFields);
        Map<String, String> searchMap = new HashMap<>();
        searchMap.put("xyz", String.valueOf(ID));
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(customer.description.eq("xyz"));
        Assertions.assertThat(_appService.searchKeyValuePair(customer, map, searchMap)).isEqualTo(builder);
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
        list.add("name");
        _appService.checkProperties(list);
    }

    @Test
    public void search_SearchIsNotNullAndSearchContainsCaseThree_ReturnBooleanBuilder() throws Exception {
        Map<String, SearchFields> map = new HashMap<>();
        QCustomerEntity customer = QCustomerEntity.customerEntity;
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
        builder.or(customer.description.eq("xyz"));
        Mockito.doNothing().when(_appService).checkProperties(any(List.class));
        Mockito
            .doReturn(builder)
            .when(_appService)
            .searchKeyValuePair(any(QCustomerEntity.class), any(HashMap.class), any(HashMap.class));

        Assertions.assertThat(_appService.search(search)).isEqualTo(builder);
    }

    @Test
    public void search_StringIsNull_ReturnNull() throws Exception {
        Assertions.assertThat(_appService.search(null)).isEqualTo(null);
    }

    @Test
    public void ParseprojectsJoinColumn_KeysStringIsNotEmptyAndKeyValuePairDoesNotExist_ReturnNull() {
        Map<String, String> joinColumnMap = new HashMap<String, String>();
        String keyString = "15";
        joinColumnMap.put("customerid", keyString);
        Assertions.assertThat(_appService.parseProjectsJoinColumn(keyString)).isEqualTo(joinColumnMap);
    }
}
