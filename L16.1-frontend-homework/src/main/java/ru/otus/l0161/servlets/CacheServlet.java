package ru.otus.l0161.servlets;


import com.google.gson.Gson;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.otus.l0161.AuthUtils;
import ru.otus.l0161.TemplateProcessor;
import ru.otus.l0161.cache.CacheEngineImpl;
import ru.otus.l0161.cache.CacheInformation;
import ru.otus.l0161.frontend.CacheResultDelegate;
import ru.otus.l0161.frontend.FrontendService;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CacheServlet extends AbstractHttpServlet implements CacheResultDelegate {

    private static final String CACHE_ENABLED = "enabled";
    private static final String CACHE_DISABLED = "disabled";
    private final TemplateProcessor templateProcessor;
    private FrontendService frontendService;
    private boolean isComplete = false;
    private CacheInformation cacheInformation;
    private Logger logger = Logger.getLogger(getClass().getSimpleName());

    public CacheServlet() throws IOException {
        this.templateProcessor = new TemplateProcessor();
    }

    public void init() {
        ApplicationContext context = new ClassPathXmlApplicationContext("SpringBeans.xml");
        frontendService = (FrontendService) context.getBean("frontendService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        boolean isAuthorized = AuthUtils.isAuthorized(req);
        String page;
        int status;
        if (isAuthorized) {
            frontendService.getCacheInformation(this);

            synchronized (this) {
                while (!isComplete) {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        isComplete = true;
                    }
                }
            }

            page = templateProcessor.getPage(getPageTemplate(), getCacheMapFromObject(cacheInformation));
            status = HttpServletResponse.SC_OK;
        } else {
            page = templateProcessor.getPage(getUnAuthorizationPage(), null);
            status = HttpServletResponse.SC_UNAUTHORIZED;
        }
        resp.setContentType(TEXT_CONTENT_TYPE);
        resp.getWriter().println(page);
        resp.setStatus(status);


    }

    private Map<String, Object> getCacheMapFromObject(CacheInformation cacheInformation) {
        Map<String, Object> data = new HashMap<>();
        String status;
        if(cacheInformation == null){
            status = CACHE_DISABLED;
            cacheInformation = CacheEngineImpl.emptyInformation();
        }else{
            status = CACHE_ENABLED;
        }

        data.put("requestCount", cacheInformation.getRequestCount());
        data.put("hitCount", cacheInformation.getHitCount());
        data.put("missCount", cacheInformation.getMissCount());
        data.put("cacheSize", cacheInformation.getCacheSize());
        data.put("currentSize", cacheInformation.getCurrentCount());
        data.put("status", status);
        return data;
    }

    @Override
    protected String getPageTemplate() {
        return "cache.html";
    }


    @Override
    public void publishCacheInformation(CacheInformation cacheInformation) {
        logger.log(Level.INFO, "publishCacheInformation "+new Gson().toJson(cacheInformation));
        synchronized (this){
            this.isComplete = true;
            this.cacheInformation = cacheInformation;
            notify();
        }
    }
}
