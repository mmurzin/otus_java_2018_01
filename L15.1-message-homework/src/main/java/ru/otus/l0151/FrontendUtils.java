package ru.otus.l0151;

import ru.otus.l0151.frontend.FrontendService;

public class FrontendUtils {
    private static FrontendService frontendService;

    public static FrontendService getFrontendService() {
        return frontendService;
    }

    public static void setFrontendService(FrontendService frontendService) {
        FrontendUtils.frontendService = frontendService;
    }
}
