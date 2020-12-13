package com.fastcode.timesheetapp5.security.sanitization;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 *
 *         This class performs the getting of request from input stream and
 *         convert to string and pass it to the Owasp json sanitizer and then
 *         response is converted to the byte of string and return the input
 *         stream after sanitization
 *
 */
public class CachedBodyServletInputStream extends ServletInputStream {

    @Autowired
    JsonSanitizition sanitizer;

    private InputStream cachedBodyInputStream;

    /*
     * here we take the request Input Streamand process it sanitize it and return
     * the InputStream
     */

    public CachedBodyServletInputStream(byte[] cachedBody) throws IOException {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(cachedBody);
        System.out.println(">>>>>>CachedBodyServletInputStream<<<<<" + cachedBodyInputStream);

        String requestStr = IOUtils.toString(byteArrayInputStream);

        String jsonSanitize = sanitizer.jsonSanitize(requestStr);

        System.err.println("sanitized output stream>>>>>>" + jsonSanitize);

        byte[] StringByte = IOUtils.toByteArray(jsonSanitize);

        InputStream bais = new ByteArrayInputStream(StringByte);

        System.err.println("sanitized input stream " + bais);
        this.cachedBodyInputStream = bais;
        System.out.println(">>>>>>CachedBodyServletInputStream<<<<<" + cachedBodyInputStream);
    }

    @Override
    public boolean isFinished() {
        // TODO Auto-generated method stub
        try {
            return cachedBodyInputStream.available() == 0;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean isReady() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public void setReadListener(ReadListener listener) {
        // TODO Auto-generated method stub

    }

    @Override
    public int read() throws IOException {
        // TODO Auto-generated method stub
        return cachedBodyInputStream.read();
    }
}
