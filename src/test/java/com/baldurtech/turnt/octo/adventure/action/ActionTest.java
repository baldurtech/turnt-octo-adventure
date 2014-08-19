package com.baldurtech.turnt.octo.adventure.action;

import com.baldurtech.unit.MiniatureSpiceTestCase;
import static org.mockito.Mockito.*;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ActionTest extends MiniatureSpiceTestCase {

    ServletContext servletContext;
    HttpServletRequest request;
    HttpServletResponse response;

    Action action;

    public void setup() {
        servletContext = mock(ServletContext.class);
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);

        when(request.getContextPath()).thenReturn("");

        action = new ContactAction(servletContext, request, response);
    }

    public void test_toRealUri_不带参数的actionUri() {
        assertEquals("/contact/list.do", action.toRealUri("contact/list"));
    }

    public void test_toRealUri_contact_show带参数的actionUri() {
        assertEquals("/contact/show.do?id=1", action.toRealUri("contact/show?id=1"));
    }
}
