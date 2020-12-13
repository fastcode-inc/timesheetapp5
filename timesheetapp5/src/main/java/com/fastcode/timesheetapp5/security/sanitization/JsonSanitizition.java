package com.fastcode.timesheetapp5.security.sanitization;

import com.google.json.JsonSanitizer;
import org.springframework.stereotype.Component;

/**
 *
 *
 *         This class performs the Owasp json sanitization which is provided by
 *         google this accepts the string and return string
 *
 */
@Component
public class JsonSanitizition {

    public static String jsonSanitize(String jsonString) {
        return JsonSanitizer.sanitize(jsonString);
        //		return jsonString;
    }
}
