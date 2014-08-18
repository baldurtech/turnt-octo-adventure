package com.baldurtech.turnt.octo.adventure.dao;

import com.baldurtech.turnt.octo.adventure.domain.Contact;

public class ContactRepositoryMock implements ContactRepository {

    public Long getByIdActualParamId;
    public Contact getByIdShouldReturn;

    public Long deleteByIdActualParamId;

    public Contact updateParamContact;
    public Object[] saveParams;
    public Boolean saveHasInvoked = false;

    public Contact getById(Long id) {
        getByIdActualParamId = id;
        return getByIdShouldReturn;
    }

    public void deleteById(Long id) {
        deleteByIdActualParamId = id;
    }

    public void update(Contact contact) {
        updateParamContact = contact;
    }

    public void save(Contact contact) {
        saveHasInvoked = true;
    }
}
