package no.example;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.sql.Connection;

@Slf4j
public class ExampleFilter implements Filter {
    private final ExampleResourceConfig config;

    public ExampleFilter(ExampleResourceConfig config) {
        this.config = config;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        Connection connection;
        try {
            HttpServletRequest req = (HttpServletRequest) servletRequest;
            HttpServletResponse res = (HttpServletResponse) servletResponse;

            if (!req.getRequestURI().contains("/api/v1/") && !req.getRequestURI().contains("/assets") && !req.getRequestURI().equals("/")) {
                log.info("Request  Method: {} \"{}\"", req.getMethod(), req.getRequestURI());
                req.getRequestDispatcher("/index.html").forward(req, res);
                log.info("Response Code from Server: {}", res.getStatus());

            } else {
                connection = config.createConnectionForRequest();
                filterChain.doFilter(servletRequest, servletResponse);
                log.info("Request  Method: {} \"{}\"", req.getMethod(), req.getRequestURI());

                connection.close();
                config.cleanRequestConnection();
                log.info("Response Code from Server: {}", res.getStatus());
            }

        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

}
