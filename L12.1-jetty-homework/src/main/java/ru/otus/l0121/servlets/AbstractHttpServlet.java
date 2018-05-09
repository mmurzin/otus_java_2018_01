package ru.otus.l0121.servlets;

import javax.servlet.http.HttpServlet;

public abstract class AbstractHttpServlet extends HttpServlet {
    final String TEXT_CONTENT_TYPE = "text/html;charset=utf-8";
    protected abstract String getPageTemplate();

    String getUnAuthorizationPage(){
        return "page_401.html";
    }
}
