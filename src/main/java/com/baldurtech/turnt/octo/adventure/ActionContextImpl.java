package com.baldurtech.turnt.octo.adventure;

import java.io.PrintWriter;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ActionContextImpl implements ActionContext {

    final HttpServletRequest request;
    final HttpServletResponse response;

    public ActionContextImpl(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
    }

    public PrintWriter getOut() throws IOException {
        return response.getWriter();
    }

    public void setAttribute(String key, Object value) {
        request.setAttribute(key, value);
    }
}
