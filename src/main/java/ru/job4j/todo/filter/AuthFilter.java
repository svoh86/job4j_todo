package ru.job4j.todo.filter;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

/**
 * Класс фильтр.
 * Если запрос идет к адресам index или user/loginPage и т.д., то мы их пропускаем сразу.
 * Если запросы идут к другим адресам, то проверяем наличие пользователя в HttpSession.
 * Если его нет, то мы переходим на страницу авторизации.
 *
 * @author Svistunov Mikhail
 * @version 1.0
 */
@Component
public class AuthFilter implements Filter {
    private final Set<String> uriSet = Set.of(
            "index", "tasks", "user/loginPage", "user/login", "user/add", "user/create");

    private boolean matchUri(String uri) {
        return uriSet.stream().anyMatch(uri::endsWith);
    }

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse res = (HttpServletResponse) servletResponse;
        String uri = req.getRequestURI();
        if (matchUri(uri)) {
            filterChain.doFilter(req, res);
            return;
        }
        if (req.getSession().getAttribute("user") == null) {
            res.sendRedirect(req.getContextPath() + "/user/loginPage");
            return;
        }
        filterChain.doFilter(req, res);
    }
}
