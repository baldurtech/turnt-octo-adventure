package com.baldurtech.turnt.octo.adventure.action;

import java.util.Map;
import java.util.HashMap;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.baldurtech.turnt.octo.adventure.domain.Contact;
import com.baldurtech.turnt.octo.adventure.manager.ContactManager;
import com.baldurtech.turnt.octo.adventure.manager.ContactManagerImpl;

import static com.baldurtech.turnt.octo.adventure.utils.StringUtils.isNotBlank;

public class ContactAction extends Action {
    final private ContactManager contactManager;

    public ContactAction(ActionContext actionContext) {
        this(actionContext, new ContactManagerImpl());
    }

    public ContactAction(ActionContext actionContext, ContactManager contactManager) {
        super(actionContext);
        this.contactManager = contactManager;
    }

    public ContactAction(ServletContext servletContext, HttpServletRequest request, HttpServletResponse response) {
        this(servletContext, request, response, new ContactManagerImpl());
    }

    public ContactAction(ServletContext servletContext, HttpServletRequest request, HttpServletResponse response
                         , ContactManager contactManager) {
        super(servletContext, request, response);
        this.contactManager = contactManager;
    }

    public void index() {
        actionContext.redirectAction("contact/list");
    }

    Long getParameterLong(String param) {
        String paramLong = actionContext.getParameter(param);
        if(isNotBlank(paramLong)) {
            return Long.parseLong(paramLong);
        }
        return null;
    }

    public Map<String, Object> show() {
        final Contact contact = contactManager.getById(getParameterLong("id"));
        if(contact == null) {
            actionContext.redirectAction("contact/list");
            return null;
        }
        return new HashMap<String, Object>(){{
                put("contact", contact);
            }};
    }

    public void save() {
        final Contact contact = new Contact();
        contact.setName(actionContext.getParameter("name"));
        contact.setMobile(actionContext.getParameter("mobile"));
        contact.setVpmn(actionContext.getParameter("vpmn"));
        contact.setEmail(actionContext.getParameter("email"));
        contact.setHomeAddress(actionContext.getParameter("homeAddress"));
        contact.setOfficeAddress(actionContext.getParameter("officeAddress"));
        contact.setMemo(actionContext.getParameter("memo"));
        contact.setJob(actionContext.getParameter("job"));
        String paramJobLevel = actionContext.getParameter("jobLevel");
        if(paramJobLevel != null && paramJobLevel.trim().length() > 0) {
            contact.setJobLevel(Integer.parseInt(paramJobLevel));
        }

        Contact savedContact = contactManager.save(contact);
        if(savedContact.isPersistent()) {
            actionContext.redirectAction("contact/show?id=" + savedContact.getId());
        } else {
            actionContext.flashMessage("Cannot save Contact!");
            actionContext.forwardAction("contact/create", new HashMap<String, Object>(){{
                        put("contact", contact);
                    }});
        }
    }

    public Contact create() {
        return new Contact();
    }
}
