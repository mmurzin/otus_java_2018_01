package ru.otus.l0131.servlets;

import ru.otus.l0131.AuthUtils;
import ru.otus.l0131.TemplateProcessor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class LoginServlet extends AbstractHttpServlet {

    private static final String KEY_LOGIN = "login";
    private static final String KEY_PASSWORD = "password";

    private static final String DEFAULT_LOGIN = "admin";
    private static final String DEFAULT_PASSWORD = "123456";
    private static final String AUTHORIZED = "Авторизован";
    private static final String UNAUTHORIZED = "Не авторизован";
    private final TemplateProcessor templateProcessor;


    @SuppressWarnings("WeakerAccess")
    public LoginServlet() throws IOException {
        templateProcessor = new TemplateProcessor();
    }

    @Override
    protected String getPageTemplate() {
        return "login.html";
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String page = templateProcessor.getPage(getPageTemplate(), getAuthData(req));
        resp.setContentType(TEXT_CONTENT_TYPE);
        resp.getWriter().println(page);
        resp.setStatus(HttpServletResponse.SC_OK);
    }

    private Map<String, Object> getAuthData(HttpServletRequest req) {
        Map<String, Object> data = new HashMap<>(3);
        data.put("login", DEFAULT_LOGIN);
        data.put("password", DEFAULT_PASSWORD);
        data.put("status", AuthUtils.isAuthorized(req) ? AUTHORIZED : UNAUTHORIZED);
        return data;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String login = req.getParameter(KEY_LOGIN);
        String password = req.getParameter(KEY_PASSWORD);

        if (DEFAULT_LOGIN.equals(login) &&
                DEFAULT_PASSWORD.equals(password)) {
            AuthUtils.authorized(req, true);
            resp.sendRedirect("/cache");
        } else {
            AuthUtils.authorized(req, false);
            doGet(req, resp);
        }

    }
}
