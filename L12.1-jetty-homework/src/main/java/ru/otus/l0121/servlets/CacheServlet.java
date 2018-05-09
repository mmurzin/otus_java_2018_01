package ru.otus.l0121.servlets;

import ru.otus.l0121.AuthUtils;
import ru.otus.l0121.TemplateProcessor;
import ru.otus.l0121.cache.CacheInformation;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CacheServlet extends AbstractHttpServlet {

    private static final String CACHE_ENABLED = "enabled";
    private static final String CACHE_DISABLED = "disabled";
    private final TemplateProcessor templateProcessor;
    private CacheInformation cacheInformation;

    public CacheServlet(TemplateProcessor templateProcessor,
                        CacheInformation cacheInformation) {
        this.templateProcessor = templateProcessor;
        this.cacheInformation = cacheInformation;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        boolean isAuthorized = AuthUtils.isAuthorized(req);
        String page;
        int status;
        if (isAuthorized) {
            page = templateProcessor.getPage(getPageTemplate(), getCacheMap());
            status = HttpServletResponse.SC_OK;
        } else {
            page = templateProcessor.getPage(getUnAuthorizationPage(), null);
            status = HttpServletResponse.SC_UNAUTHORIZED;
        }
        resp.setContentType(TEXT_CONTENT_TYPE);
        resp.getWriter().println(page);
        resp.setStatus(status);
    }

    private Map<String, Object> getCacheMap() {
        Map<String, Object> data = new HashMap<>();
        String status = CACHE_ENABLED;

        if (cacheInformation == null) {
            status = CACHE_DISABLED;
            cacheInformation = emptyInformation();
        }

        data.put("requestCount", cacheInformation.getRequestCount());
        data.put("hitCount", cacheInformation.getHitCount());
        data.put("missCount", cacheInformation.getMissCount());
        data.put("cacheSize", cacheInformation.getCacheSize());
        data.put("currentSize", cacheInformation.getCurrentCount());
        data.put("status", status);
        return data;
    }

    private CacheInformation emptyInformation() {
        return new CacheInformation() {
            @Override
            public int getRequestCount() {
                return -1;
            }

            @Override
            public int getHitCount() {
                return -1;
            }

            @Override
            public int getMissCount() {
                return -1;
            }

            @Override
            public int getCacheSize() {
                return -1;
            }

            @Override
            public int getCurrentCount() {
                return -1;
            }
        };
    }

    @Override
    protected String getPageTemplate() {
        return "cache.html";
    }
}
