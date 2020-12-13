package com.fastcode.timesheetapp5.addons.docmgmt.domain.file;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.versions.LockingAndVersioningRepository;

@Repository("fileRepository")
public interface IFileRepository
    extends
        JpaRepository<FileEntity, Long>,
        QuerydslPredicateExecutor<FileEntity>,
        LockingAndVersioningRepository<FileEntity, String> {}
