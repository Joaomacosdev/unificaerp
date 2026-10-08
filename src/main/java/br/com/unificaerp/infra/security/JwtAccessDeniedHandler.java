package br.com.unificaerp.infra.security;

import br.com.unificaerp.infra.exception.ResponseApi;
import br.com.unificaerp.util.ObjectMapperUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;

@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler {

    private final ObjectMapperUtil objectMapperUtil;

    public JwtAccessDeniedHandler(ObjectMapperUtil objectMapperUtil) {
        this.objectMapperUtil = objectMapperUtil;
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException, ServletException {


        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");

        ResponseApi responseApi = new ResponseApi(new Date(),
                403,
                "Forbidden",
                "Você não possui permissão para acessar este recurso ou url",
                request.getRequestURI());

        response.getWriter().write(objectMapperUtil.objetoParaJson(responseApi));

    }

}
