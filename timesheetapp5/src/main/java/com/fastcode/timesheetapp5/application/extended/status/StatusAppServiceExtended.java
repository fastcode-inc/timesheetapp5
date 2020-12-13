package com.fastcode.timesheetapp5.application.extended.status;

import com.fastcode.timesheetapp5.application.core.status.StatusAppService;
import com.fastcode.timesheetapp5.commons.logging.LoggingHelper;
import com.fastcode.timesheetapp5.domain.extended.status.IStatusRepositoryExtended;
import org.springframework.stereotype.Service;

@Service("statusAppServiceExtended")
public class StatusAppServiceExtended extends StatusAppService implements IStatusAppServiceExtended {

    public StatusAppServiceExtended(
        IStatusRepositoryExtended statusRepositoryExtended,
        IStatusMapperExtended mapper,
        LoggingHelper logHelper
    ) {
        super(statusRepositoryExtended, mapper, logHelper);
    }
    //Add your custom code here

}
