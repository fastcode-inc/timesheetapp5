package com.fastcode.timesheetapp5.restcontrollers.extended;

import com.fastcode.timesheetapp5.restcontrollers.core.StatusControllerTest;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = "spring.profiles.active=test")
public class StatusControllerTestExtended extends StatusControllerTest {
    //Add your custom code here
}
