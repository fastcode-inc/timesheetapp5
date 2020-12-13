package com.fastcode.timesheetapp5.domain.extended.timesheetstatus;

import com.fastcode.timesheetapp5.domain.core.timesheetstatus.ITimesheetstatusRepository;
import org.javers.spring.annotation.JaversSpringDataAuditable;
import org.springframework.stereotype.Repository;

@JaversSpringDataAuditable
@Repository("timesheetstatusRepositoryExtended")
public interface ITimesheetstatusRepositoryExtended extends ITimesheetstatusRepository {
    //Add your custom code here
}
