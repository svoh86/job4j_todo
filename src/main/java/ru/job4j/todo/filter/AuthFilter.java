package ru.job4j.todo.filter;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse res = (HttpServletResponse) servletResponse;
        String uri = req.getRequestURI();
        if (uri.endsWith("index")
            || uri.endsWith("tasks")
            || uri.endsWith("user/loginPage")
            || uri.endsWith("user/login")
            || uri.endsWith("user/add")
            || uri.endsWith("user/create")) {
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
