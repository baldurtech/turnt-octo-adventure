package com.baldurtech.turnt.octo.adventure.dao;

import com.baldurtech.turnt.octo.adventure.domain.Contact;

public interface ContactRepository {

    public Contact getById(Long id);

    public void deleteById(Long id);

    public void update(Contact contact);

    public void save(Contact contact);

}
