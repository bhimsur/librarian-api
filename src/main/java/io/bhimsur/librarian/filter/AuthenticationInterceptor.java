package io.bhimsur.librarian.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.bhimsur.librarian.annotation.AdminPermit;
import io.bhimsur.librarian.dto.ErrorDto;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class AuthenticationInterceptor implements HandlerInterceptor {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final String KEY_REQUIRED = "Key is required";
    private static final String INVALID_KEY = "Invalid Key";
    private static final String HEADER_KEY = "X-Key";
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String key = request.getHeader(HEADER_KEY);
        HandlerMethod hm = (HandlerMethod) handler;
        var permit = AnnotationUtils.findAnnotation(hm.getMethod(), AdminPermit.class);
        if (permit == null) {
            return true;
        }
        if (key == null) {
            ErrorDto errorDetail = ErrorDto.builder()
                    .errorCode(403)
                    .errorMessage(KEY_REQUIRED)
                    .traceId(MDC.get("X-B3-TraceId"))
                    .build();

            response.setStatus(HttpStatus.FORBIDDEN.value());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);

            objectMapper.writeValue(response.getWriter(), errorDetail);
            return false;
        } else if (!key.equals("21232f297a57a5a743894a0e4a801fc3")) {
            ErrorDto errorDetail = ErrorDto.builder()
                    .errorCode(401)
                    .errorMessage(INVALID_KEY)
                    .traceId(MDC.get("X-B3-TraceId"))
                    .build();

            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);

            objectMapper.writeValue(response.getWriter(), errorDetail);
            return false;
        }
        return true;
    }
}
