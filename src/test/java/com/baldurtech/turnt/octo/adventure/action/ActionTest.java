package com.baldurtech.turnt.octo.adventure.action;

import junit.framework.*;
import static org.mockito.Mockito.*;

import java.util.Map;
import java.util.HashMap;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.ServletContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ActionTest extends TestCase {

    ServletContext servletContext;
    HttpServletRequest request;
    HttpServletResponse response;

    Action action;

    public void setUp() {
        servletContext = mock(ServletContext.class);
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);

        when(request.getContextPath()).thenReturn("/calabash");

        action = new ContactAction(servletContext, request, response);
    }

    public void test_toRealUri_不带参数的actionUri() {
        assertEquals("/calabash/contact/list.do", action.toRealUri("contact/list"));
    }

    public void test_toRealUri_contact_show带参数的actionUri() {
        assertEquals("/calabash/contact/show.do?id=1", action.toRealUri("contact/show?id=1"));
    }

    public void test_flashMessage_应该会把消息放入flash_message中() {
        action.flashMessage("test message");

        verify(request).setAttribute("flash.message", "test message");
    }

    public void test_forwardAction_空数据仅仅转到对应的do上() throws Exception {
        RequestDispatcher requestDispatcher = mock(RequestDispatcher.class);
        when(servletContext.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);

        action.forwardAction("contact/list", new HashMap<String, Object>());

        verify(request, never()).setAttribute(anyString(), anyObject());
        verify(servletContext).getRequestDispatcher("/contact/list.do");
        verify(requestDispatcher).forward(request, response);
    }

    public void test_forwardAction_转到对应的do上并且把数据放入request() throws Exception {
        RequestDispatcher requestDispatcher = mock(RequestDispatcher.class);
        when(servletContext.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);

        Map<String, Object> data = new HashMap<String, Object>(){{
                put("name", "Tom");
                put("jobLevel", 5);
                put("vpmn", "123123");
            }};
        action.forwardAction("contact", data);

        verify(request).setAttribute("name", "Tom");
        verify(request).setAttribute("jobLevel", 5);
        verify(request).setAttribute("vpmn", "123123");
        verify(servletContext).getRequestDispatcher("/contact.do");
        verify(requestDispatcher).forward(request, response);
    }
}
