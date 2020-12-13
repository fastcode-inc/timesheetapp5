package com.fastcode.timesheetapp5.restcontrollers.extended;

import com.fastcode.timesheetapp5.application.extended.customer.ICustomerAppServiceExtended;
import com.fastcode.timesheetapp5.application.extended.project.IProjectAppServiceExtended;
import com.fastcode.timesheetapp5.commons.logging.LoggingHelper;
import com.fastcode.timesheetapp5.restcontrollers.core.CustomerController;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customer/extended")
public class CustomerControllerExtended extends CustomerController {

    public CustomerControllerExtended(
        ICustomerAppServiceExtended customerAppServiceExtended,
        IProjectAppServiceExtended projectAppServiceExtended,
        LoggingHelper helper,
        Environment env
    ) {
        super(customerAppServiceExtended, projectAppServiceExtended, helper, env);
    }
    //Add your custom code here

}
