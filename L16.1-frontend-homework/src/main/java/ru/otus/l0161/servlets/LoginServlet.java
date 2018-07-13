package ru.otus.l0161.servlets;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.otus.l0161.AuthUtils;
import ru.otus.l0161.TemplateProcessor;
import ru.otus.l0161.UserCredentials;
import ru.otus.l0161.frontend.FrontendService;
import ru.otus.l0161.frontend.LoginResultDelegate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import static ru.otus.l0161.App.DEFAULT_LOGIN;
import static ru.otus.l0161.App.DEFAULT_PASSWORD;

public class LoginServlet extends AbstractHttpServlet implements LoginResultDelegate {

    private static final String KEY_LOGIN = "login";
    private static final String KEY_PASSWORD = "password";


    private static final String AUTHORIZED = "Авторизован";
    private static final String UNAUTHORIZED = "Не авторизован";
    private final TemplateProcessor templateProcessor;
    private FrontendService frontendService;

    private boolean isComplete = false;
    private boolean isLoginSuccessfully = false;

    @SuppressWarnings("WeakerAccess")
    public LoginServlet() throws IOException {
        templateProcessor = new TemplateProcessor();
    }

    public void init() {
        ApplicationContext context = new ClassPathXmlApplicationContext("SpringBeans.xml");
        frontendService = (FrontendService) context.getBean("frontendService");
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

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String login = req.getParameter(KEY_LOGIN);
        String password = req.getParameter(KEY_PASSWORD);
        frontendService.doLogin(new UserCredentials(login, password), this);

        synchronized (this) {
            while (!isComplete) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    isComplete = true;
                }
            }
        }

        AuthUtils.authorized(req.getSession(), isLoginSuccessfully);
        if (isLoginSuccessfully) {
            resp.sendRedirect("/cache");
        } else {
            doGet(req, resp);
        }
    }


    private Map<String, Object> getAuthData(HttpServletRequest req) {
        Map<String, Object> data = new HashMap<>(3);
        data.put("login", DEFAULT_LOGIN);
        data.put("password", DEFAULT_PASSWORD);
        data.put("status", AuthUtils.isAuthorized(req) ? AUTHORIZED : UNAUTHORIZED);
        return data;
    }

    @Override
    public void publishResult(boolean isLoginSuccessfully){
        synchronized (this){
            this.isComplete = true;
            this.isLoginSuccessfully = isLoginSuccessfully;
            notify();
        }
    }
}
