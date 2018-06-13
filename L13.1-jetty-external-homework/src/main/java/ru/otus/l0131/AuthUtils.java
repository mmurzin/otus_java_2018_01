package ru.otus.l0131;

import javax.servlet.http.HttpServletRequest;

public class AuthUtils {
    private static final String AUTHORIZED_KEY = "authorized";

    private AuthUtils() {
    }

    public static void authorized(HttpServletRequest request, boolean state) {
        request.getSession().setAttribute(AUTHORIZED_KEY, state);
    }

    public static boolean isAuthorized(HttpServletRequest request) {
        Object isAuthorized = request.getSession().getAttribute(AUTHORIZED_KEY);
        return isAuthorized != null &&
                isAuthorized instanceof Boolean &&
                ((boolean) isAuthorized);
    }

}
