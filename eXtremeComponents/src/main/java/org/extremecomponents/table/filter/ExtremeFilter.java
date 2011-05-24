package org.extremecomponents.table.filter;

import org.apache.commons.lang.StringUtils;
import org.extremecomponents.table.context.Context;
import org.extremecomponents.table.context.HttpServletRequestContext;
import org.extremecomponents.table.core.Preferences;
import org.extremecomponents.table.core.TableConstants;
import org.extremecomponents.table.core.TableModelUtils;
import org.extremecomponents.table.core.TableProperties;
import org.extremecomponents.util.MimeUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.IOException;

/**
 * Created by jeff
 */
public class ExtremeFilter implements Filter {
    private boolean responseHeadersSetBeforeDoFilter;

    public void init(FilterConfig filterConfig) throws ServletException {
        String responseHeadersSetBeforeDoFilter = filterConfig.getInitParameter("responseHeadersSetBeforeDoFilter");
        if (StringUtils.isNotBlank(responseHeadersSetBeforeDoFilter)) {
            this.responseHeadersSetBeforeDoFilter = new Boolean(responseHeadersSetBeforeDoFilter).booleanValue();
        }
    }

    @Override
    public void destroy() {
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        Context context = new HttpServletRequestContext((HttpServletRequest) request);
        boolean isExported = ExtremeFilterUtils.isExported(context);
        boolean isAjax = ExtremeFilterUtils.isAjax(context);
        if (isExported) {
            String exportFileName = ExtremeFilterUtils.getExportFileName(context);
            doExportFilterInternal(request, response, chain, exportFileName);
            handleExport((HttpServletRequest) request, (HttpServletResponse) response, context);
        } else if (isAjax) {
            doAjaxFilterInternal(request, response, chain);
            handleAjax((HttpServletRequest) request, (HttpServletResponse) response, context);
        } else {
            chain.doFilter(request, response);
        }
    }

    protected void handleAjax(HttpServletRequest request, HttpServletResponse response, Context context) {
        try {
            String viewData = (String) request.getAttribute(TableConstants.AJAX_VIEW_DATA);
            if (viewData != null) {
                String encoding = request.getCharacterEncoding();
                byte[] contents = viewData.getBytes();
                if (encoding == null) {
                    encoding = "utf-8";
                }
                response.setDateHeader("Expires", (System.currentTimeMillis() + 1000));
                response.setContentType("text/html");
                response.setCharacterEncoding(encoding);
                response.setContentLength(contents.length);
                response.getOutputStream().write(contents);
                response.getOutputStream().flush();
                response.getOutputStream().close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void handleExport(HttpServletRequest request, HttpServletResponse response, Context context) {
        try {
            Object viewData = request.getAttribute(TableConstants.VIEW_DATA);
            if (viewData != null) {
                Preferences preferences = TableProperties.getInstance(context, TableModelUtils.getPreferencesLocation(context));

                String viewResolver = (String) request.getAttribute(TableConstants.VIEW_RESOLVER);
                Class classDefinition = Class.forName(viewResolver);
                ViewResolver handleExportViewResolver = (ViewResolver) classDefinition.newInstance();
                handleExportViewResolver.resolveView(request, response, preferences, viewData);
                response.getOutputStream().flush();
                response.getOutputStream().close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void doAjaxFilterInternal(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        chain.doFilter(request, new ExtremeResponseWrapper((HttpServletResponse) response));
    }

    protected void setResponseHeaders(HttpServletResponse response, String exportFileName) {
        String mimeType = MimeUtils.getFileMimeType(exportFileName);
        if (StringUtils.isNotBlank(mimeType)) {
            response.setContentType(mimeType);
        }

        response.setHeader("Content-Disposition", "attachment;filename=\"" + exportFileName + "\"");
        response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
        response.setHeader("Pragma", "public");
        response.setDateHeader("Expires", (System.currentTimeMillis() + 1000));
    }

    protected void doExportFilterInternal(ServletRequest request, ServletResponse response, FilterChain chain, String exportFileName) throws IOException, ServletException {
        if (responseHeadersSetBeforeDoFilter) {
            setResponseHeaders((HttpServletResponse) response, exportFileName);
        }
        chain.doFilter(request, new ExtremeResponseWrapper((HttpServletResponse) response));
        if (!responseHeadersSetBeforeDoFilter) {
            setResponseHeaders((HttpServletResponse) response, exportFileName);
        }
    }
}
