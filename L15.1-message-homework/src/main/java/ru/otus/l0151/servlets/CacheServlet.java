package ru.otus.l0151.servlets;


import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.otus.l0151.AuthUtils;
import ru.otus.l0151.ImitationUtils;
import ru.otus.l0151.TemplateProcessor;
import ru.otus.l0151.cache.CacheInformation;
import ru.otus.l0151.db.DBService;
import ru.otus.l0151.db.DBServiceImpl;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CacheServlet extends AbstractHttpServlet {

    private static final String CACHE_ENABLED = "enabled";
    private static final String CACHE_DISABLED = "disabled";
    private final TemplateProcessor templateProcessor;
    private DBService dbService;

    public CacheServlet()  throws IOException {
        this.templateProcessor = new TemplateProcessor();
    }

    public void init() {
        ApplicationContext context = new ClassPathXmlApplicationContext("SpringBeans.xml");

        dbService = (DBService) context.getBean("dbService");
        dbService.truncateTables(DBServiceImpl.getDBModels());
        ImitationUtils.imitateActions(dbService);
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

        CacheInformation cacheInformation = null;
        if(dbService != null){
            cacheInformation = dbService.getCacheEngine().getCacheInformation();
        }

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
