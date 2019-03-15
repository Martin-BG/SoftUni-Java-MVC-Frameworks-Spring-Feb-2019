package org.softuni.exodia.web.interceptors;

import org.softuni.exodia.annotations.Layout;
import org.springframework.util.Assert;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Modifies view according to {@link Layout} annotation by rendering it into a layout template.
 * <ul>Default values (can be changed during interceptor's instance build):
 * <li>Layout file: {@value #DEFAULT_LAYOUT}</li>
 * <li>View attribute name in layout file, where the view content
 * to be inserted to: {@value #DEFAULT_VIEW_ATTRIBUTE_NAME}</li>
 * <li>View file prefix (path to view files, added to incoming view): {@value #DEFAULT_VIEW_PREFIX}</li>
 * </ul>
 * <hr>
 * Inspired by kolorobot's <a href="https://github.com/kolorobot/thymeleaf-custom-layout">Thymeleaf Custom Layout</a>
 */

public final class ThymeleafLayoutInterceptor extends HandlerInterceptorAdapter {

    private static final String DEFAULT_LAYOUT = "/layouts/default";
    private static final String DEFAULT_VIEW_ATTRIBUTE_NAME = "view";
    private static final String DEFAULT_VIEW_PREFIX = "/views/";

    private String defaultLayout;
    private String viewPrefix;
    private String viewAttribute;

    private ThymeleafLayoutInterceptor() {
        defaultLayout = DEFAULT_LAYOUT;
        viewAttribute = DEFAULT_VIEW_ATTRIBUTE_NAME;
        viewPrefix = DEFAULT_VIEW_PREFIX;
    }

    private static boolean isRedirectOrForward(String viewName) {
        return viewName.startsWith("redirect:") || viewName.startsWith("forward:");
    }

    private static Layout getMethodOrTypeAnnotation(Object handler) {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Layout layout = handlerMethod.getMethodAnnotation(Layout.class);
            if (layout == null) {
                layout = handlerMethod.getBeanType().getAnnotation(Layout.class);
            }
            return layout;
        }
        return null;
    }

    public static Builder builder() {
        return new Builder();
    }

    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response,
                           Object handler,
                           ModelAndView modelAndView) {
        if (modelAndView == null || !modelAndView.hasView()) {
            return;
        }

        String originalViewName = modelAndView.getViewName();
        if (originalViewName == null || isRedirectOrForward(originalViewName)) {
            return;
        }

        Layout layout = getMethodOrTypeAnnotation(handler);
        if (layout == null) {
            return;
        }

        String layoutName = getLayoutName(layout);
        if (Layout.NONE.equals(layoutName)) {
            return;
        }

        if (layoutName.isBlank()) {
            layoutName = defaultLayout;
        }

        modelAndView.setViewName(layoutName);
        modelAndView.addObject(viewAttribute, getView(originalViewName));
    }

    private String getView(String viewName) {
        return viewPrefix + viewName;
    }

    private String getLayoutName(Layout layout) {
        if (layout != null) {
            return layout.value();
        }
        return defaultLayout;
    }

    public static final class Builder {
        private ThymeleafLayoutInterceptor toBuild;

        private Builder() {
            toBuild = new ThymeleafLayoutInterceptor();
        }

        public Builder withDefaultLayout(String defaultLayout) {
            toBuild.defaultLayout = defaultLayout;
            return this;
        }

        public Builder withViewAttribute(String viewAttribute) {
            toBuild.viewAttribute = viewAttribute;
            return this;
        }

        public Builder withViewPrefix(String viewPrefix) {
            toBuild.viewPrefix = viewPrefix;
            return this;
        }

        public ThymeleafLayoutInterceptor build() {
            Assert.notNull(toBuild, "Reuse of builder is not allowed");
            Assert.hasLength(toBuild.defaultLayout, "Default layout cannot be empty or null");
            Assert.hasLength(toBuild.viewAttribute, "View attribute name cannot be empty or null");
            Assert.notNull(toBuild.viewPrefix, "View prefix cannot be null");

            ThymeleafLayoutInterceptor built = toBuild;
            toBuild = null;
            return built;
        }
    }
}
