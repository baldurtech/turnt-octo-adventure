package com.baldurtech.turnt.octo.adventure.manager;

import com.baldurtech.unit.MiniatureSpiceTestCase;

import com.baldurtech.turnt.octo.adventure.dao.ContactRepositoryMock;
import com.baldurtech.turnt.octo.adventure.domain.Contact;

public class ContactManagerImplTest extends MiniatureSpiceTestCase {

    public void test_getById_当id对应的Contact存在就直接返回() {
        ContactRepositoryMock contactRepository = new ContactRepositoryMock();

        ContactManagerImpl contactManager = new ContactManagerImpl(contactRepository);

        Contact contact = new Contact();
        contact.setId(1L);

        contactRepository.getByIdShouldReturn = contact;

        assertEquals(1L, contactManager.getById(1L).getId());

        assertEquals(1L, contactRepository.getByIdActualParamId);
    }

    public void test_getById_当id对应的Contact不存在就返回null() {
        Long idContactNotExist = 1L;

        ContactRepositoryMock contactRepository = new ContactRepositoryMock();

        ContactManagerImpl contactManager = new ContactManagerImpl(contactRepository);

        contactRepository.getByIdShouldReturn = null;

        assertNull(contactManager.getById(idContactNotExist));

        assertEquals(idContactNotExist, contactRepository.getByIdActualParamId);
    }
}
