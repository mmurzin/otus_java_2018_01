package ru.otus.l0151;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class AuthUtils {
    private static final String AUTHORIZED_KEY = "authorized";

    private AuthUtils() {
    }

    public static void authorized(HttpSession httpSession, boolean state) {
        if(httpSession != null){
            httpSession.setAttribute(AUTHORIZED_KEY, state);
        }
    }

    public static boolean isAuthorized(HttpServletRequest request) {
        Object isAuthorized = request.getSession().getAttribute(AUTHORIZED_KEY);
        return isAuthorized != null &&
                isAuthorized instanceof Boolean &&
                ((boolean) isAuthorized);
    }

}
