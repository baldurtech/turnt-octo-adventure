package com.baldurtech.turnt.octo.adventure.action;

import java.io.PrintWriter;
import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class Action {

    final ServletContext servletContext;
    final HttpServletRequest request;
    final HttpServletResponse response;

    final ActionContext actionContext;

    public Action(ActionContext actionContext) {
        this.servletContext = null;
        this.request = null;
        this.response = null;

        this.actionContext = actionContext;
    }

    public Action(ServletContext servletContext, HttpServletRequest request, HttpServletResponse response) {
        this.servletContext = servletContext;
        this.request = request;
        this.response = response;

        this.actionContext = new ActionContextImpl(servletContext, request, response);
    }

    public void setAttribute(String key, Object value) {
        actionContext.setAttribute(key, value);
    }

    public void println(String str) {
        try {
            actionContext.getOut().println(str);
        } catch(IOException e) {
        }
    }

    public String toRealUri(String actionUri) {
        String contextPath = request.getContextPath();
        if(actionUri.indexOf("?") > 0) {
            return contextPath + "/" + actionUri.replace("?", ".do?");
        }
        return contextPath + "/" + actionUri + ".do";
    }

    public void flashMessage(String msg) {
        request.setAttribute("flash.message", msg);
    }

}
