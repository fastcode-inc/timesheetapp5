package com.fastcode.timesheetapp5.application.extended.customer;

import com.fastcode.timesheetapp5.application.core.customer.CustomerAppService;
import com.fastcode.timesheetapp5.commons.logging.LoggingHelper;
import com.fastcode.timesheetapp5.domain.extended.customer.ICustomerRepositoryExtended;
import org.springframework.stereotype.Service;

@Service("customerAppServiceExtended")
public class CustomerAppServiceExtended extends CustomerAppService implements ICustomerAppServiceExtended {

    public CustomerAppServiceExtended(
        ICustomerRepositoryExtended customerRepositoryExtended,
        ICustomerMapperExtended mapper,
        LoggingHelper logHelper
    ) {
        super(customerRepositoryExtended, mapper, logHelper);
    }
    //Add your custom code here

}
