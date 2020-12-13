package com.fastcode.timesheetapp5.security.sanitization;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.NonTypedScalarSerializerBase;
import java.io.IOException;
import org.apache.commons.lang3.StringEscapeUtils;

public class SanitizedStringSerializer extends NonTypedScalarSerializerBase<String> {

    public SanitizedStringSerializer() {
        super(String.class);
        System.out.println("output sanitization called");
    }

    @Override
    public void serialize(String value, JsonGenerator jgen, SerializerProvider provider)
        throws IOException, JsonGenerationException {
        jgen.writeRawValue("\"" + StringEscapeUtils.escapeHtml4(value) + "\"");
    }
}
