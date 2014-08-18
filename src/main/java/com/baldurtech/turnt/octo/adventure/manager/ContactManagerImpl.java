package com.baldurtech.turnt.octo.adventure.manager;

import com.baldurtech.turnt.octo.adventure.dao.ContactRepository;
import com.baldurtech.turnt.octo.adventure.dao.ContactRepositoryImpl;
import com.baldurtech.turnt.octo.adventure.domain.Contact;

public class ContactManagerImpl implements ContactManager {

    final private ContactRepository contactRepository;

    public ContactManagerImpl() {
        this(new ContactRepositoryImpl());
    }

    public ContactManagerImpl(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    public Contact getById(Long id) {
        return contactRepository.getById(id);
    }

    public Contact save(Contact contact) {

        return contact;
    }
}
