package com.fastcode.timesheetapp5.domain.extended.status;

import com.fastcode.timesheetapp5.domain.core.status.IStatusRepository;
import org.javers.spring.annotation.JaversSpringDataAuditable;
import org.springframework.stereotype.Repository;

@JaversSpringDataAuditable
@Repository("statusRepositoryExtended")
public interface IStatusRepositoryExtended extends IStatusRepository {
    //Add your custom code here
}
