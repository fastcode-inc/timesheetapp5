package com.fastcode.timesheetapp5.application.core.customer;

import com.fastcode.timesheetapp5.application.core.customer.dto.*;
import com.fastcode.timesheetapp5.domain.core.customer.CustomerEntity;
import java.time.*;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ICustomerMapper {
    CustomerEntity createCustomerInputToCustomerEntity(CreateCustomerInput customerDto);

    CreateCustomerOutput customerEntityToCreateCustomerOutput(CustomerEntity entity);

    CustomerEntity updateCustomerInputToCustomerEntity(UpdateCustomerInput customerDto);

    UpdateCustomerOutput customerEntityToUpdateCustomerOutput(CustomerEntity entity);

    FindCustomerByIdOutput customerEntityToFindCustomerByIdOutput(CustomerEntity entity);
}
