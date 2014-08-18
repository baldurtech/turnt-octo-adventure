package com.baldurtech.turnt.octo.adventure.servlet;

import java.util.Map;

import java.io.IOException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;

import com.baldurtech.turnt.octo.adventure.action.Action;
import com.baldurtech.turnt.octo.adventure.action.ActionContext;
import com.baldurtech.turnt.octo.adventure.action.ActionContextImpl;
import com.baldurtech.turnt.octo.adventure.template.TemplateEngine;
import com.baldurtech.turnt.octo.adventure.template.JspTemplateEngine;

public class DispatchServlet extends HttpServlet {

    public void service(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException {
        try {
            String uri = getUri(request);
            ActionContext actionContext = new ActionContextImpl(getServletContext(), request, response);
            Class actionClass = getActionByUri(uri);
            @SuppressWarnings("unchecked")
            Constructor actionConstructor = actionClass.getDeclaredConstructor(ActionContext.class);
            Action actionInstance = (Action) actionConstructor.newInstance(actionContext);
            @SuppressWarnings("unchecked")
            Method method = actionClass.getDeclaredMethod(getMethodNameByUri(uri));
            Object returnValue = method.invoke(actionInstance);

            if(null != returnValue) {
                TemplateEngine template = new JspTemplateEngine(getServletContext(), request, response);
                template.merge(getViewPage(uri), returnValue);
            }
        } catch(NoSuchMethodException me) {
            me.printStackTrace();
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        } catch(ClassNotFoundException fe) {
            fe.printStackTrace();
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        } catch(IllegalAccessException ae) {
            ae.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } catch(InstantiationException ie) {
            ie.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } catch(InvocationTargetException te) {
            te.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    public String defaultPackageName = "com.baldurtech.turnt.octo.adventure.action";
    public String defaultSuffix = ".do";

    public String getUri(HttpServletRequest request) {
        return request.getRequestURI().replaceFirst(request.getContextPath(), "");
    }

    public Class getActionByUri(String uri) throws ClassNotFoundException {
        return Class.forName(getActionClassNameByUri(uri));
    }

    public String getActionClassNameByUri(String uri) {
        String[] uriParts = splitBySlash(uri);
        Integer indexOfActionClassName = 1;
        String actionClassName = capitalize(removeDefaultSuffix(uriParts[indexOfActionClassName]));
        return defaultPackageName + "." + actionClassName + "Action";
    }

    public String getMethodNameByUri(String uri) {
        String[] uriParts = splitBySlash(uri);
        Integer indexOfMethodName = 2;
        if(uriParts.length <= indexOfMethodName) {
            return "index";
        }
        return removeDefaultSuffix(uriParts[indexOfMethodName]);
    }

    public String getViewPage(String uri) {
        return "/WEB-INF/jsp" + removeDefaultSuffix(uri) + ".jsp" ;
    }

    public String[] splitBySlash(String uri) {
        return uri.split("/");
    }

    public String removeDefaultSuffix(String str) {
        return str.replace(defaultSuffix, "");
    }

    public String capitalize(String str) {
        return str.substring(0, 1).toUpperCase()
            + str.substring(1);
    }
}
