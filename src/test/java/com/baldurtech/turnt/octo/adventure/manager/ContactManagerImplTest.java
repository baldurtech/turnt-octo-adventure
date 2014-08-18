package com.baldurtech.turnt.octo.adventure.manager;

import com.baldurtech.unit.MiniatureSpiceTestCase;

import com.baldurtech.turnt.octo.adventure.dao.ContactRepositoryMock;
import com.baldurtech.turnt.octo.adventure.domain.Contact;

public class ContactManagerImplTest extends MiniatureSpiceTestCase {

    ContactRepositoryMock contactRepository;
    ContactManagerImpl contactManager;

    public void setup() {
        contactRepository = new ContactRepositoryMock();

        contactManager = new ContactManagerImpl(contactRepository);
    }

    public void test_getById_当id对应的Contact存在就直接返回() {
        Long idContactExist = 1L;

        whenContactWithIdExist(idContactExist);

        assertEquals(idContactExist, contactManager.getById(idContactExist).getId());
        assertEquals(idContactExist, contactRepository.getByIdActualParamId);
    }

    public void test_getById_当id对应的Contact不存在就返回null() {
        Long idContactNotExist = 1L;

        whenContactWithIdNotExist(idContactNotExist);

        assertNull(contactManager.getById(idContactNotExist));
        assertEquals(idContactNotExist, contactRepository.getByIdActualParamId);
    }

    public void test_save_当Contact不合法时不应该被保存() {
        Contact invalidContact = new Contact();

        contactManager.save(invalidContact);

        assertFalse(contactRepository.saveHasInvoked);
    }

    private void whenContactWithIdExist(Long idContactExist) {
        Contact contact = new Contact();
        contact.setId(idContactExist);

        contactRepository.getByIdShouldReturn = contact;
    }

    private void whenContactWithIdNotExist(Long idContactNotExist) {
        contactRepository.getByIdShouldReturn = null;
    }
}
