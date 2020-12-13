package com.fastcode.timesheetapp5.security.sanitization;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 *
 *
 *         This class extends OncePerRequestFilter and implements
 *         HandlerInterceptor First any post request will come here and then we
 *         filter it out with the json sanitizer and then forward it to the
 *         controller
 */
@Component
public class ProductServiceInterceptor extends OncePerRequestFilter implements HandlerInterceptor {

    @Autowired
    JsonSanitizition sanitizer;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
        throws Exception {
        //	   String requestStr = IOUtils.toString(request.getInputStream());

        CachedBodyHttpServletRequest cachedBodyHttpServletRequest = new CachedBodyHttpServletRequest(request);

        String cachedStr = IOUtils.toString(cachedBodyHttpServletRequest.getInputStream());

        System.err.println("catched string =========" + cachedStr);
        //	   if ("POST".equalsIgnoreCase(request.getMethod()))
        //	   {
        //	      String collect = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        //	   }

        String jsonSanitize = sanitizer.jsonSanitize(cachedStr);
        System.err.println("Pre Handle method is Calling>>>" + request);

        /*
         * request.set
         *
         *
         * public ResettableStreamHttpServletRequest(HttpServletRequest request) {
         * super(request); this.request = request; this.servletStream = new
         * ResettableServletInputStream(); }
         */
        return true;
    }

    //   @Override
    //   public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
    //			@Nullable ModelAndView modelAndView) throws Exception {
    //	   ServletOutputStream outputStream = response.getOutputStream();
    ////	   String finalString = new String(outputStream.toByteArray());
    //
    //	   String string = outputStream.toString();
    //
    //	   String jsonSanitize = sanitizer.jsonSanitize(string);
    //
    //
    ////	   String requestStr = IOUtils.toString(outputStream);
    //	}

    /*
     * Same contract as for doFilter, but guaranteed to bej ust invoked once per
     * request within a single request
     * it do filter the request
     */

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws ServletException, IOException {
        CachedBodyHttpServletRequest cachedBodyHttpServletRequest = new CachedBodyHttpServletRequest(request);
        filterChain.doFilter(cachedBodyHttpServletRequest, response);
    }
}
