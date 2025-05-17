package com.bitespeed.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bitespeed.Entities.Contact;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {

    List<Contact> findByEmailOrPhoneNumber(String email, String phoneNumber);
}