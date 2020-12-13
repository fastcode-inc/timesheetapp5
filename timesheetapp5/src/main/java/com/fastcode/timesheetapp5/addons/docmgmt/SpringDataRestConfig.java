package com.fastcode.timesheetapp5.addons.docmgmt;

import com.fastcode.timesheetapp5.addons.docmgmt.domain.file.FileEntity;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;

@Configuration
public class SpringDataRestConfig {

    // Ensure that Spring Data ReST returns the Ids of created entities
    @Bean
    public RepositoryRestConfigurer repositoryRestConfigurer() {
        return RepositoryRestConfigurer.withConfig(
            config -> {
                config.exposeIdsFor(FileEntity.class);
            }
        );
    }
}
