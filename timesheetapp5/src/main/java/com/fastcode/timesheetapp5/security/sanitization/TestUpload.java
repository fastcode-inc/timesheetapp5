package com.fastcode.timesheetapp5.security.sanitization;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class TestUpload {

    @PostMapping(value = "/upload-file")
    public String testUpload(@RequestParam("file") MultipartFile multipartFile) throws Exception {
        return (
            "file named: " +
            multipartFile.getOriginalFilename() +
            " and size: " +
            multipartFile.getSize() +
            " has been uploaded successfully"
        );
    }

    @PostMapping(value = "/sanitize-it")
    public String sanitizerTest(@RequestBody TestDto dto) throws Exception {
        return dto.getName() + " age " + dto.getAge();
    }
}
