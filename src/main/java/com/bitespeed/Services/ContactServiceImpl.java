package com.bitespeed.Services;

import java.time.LocalDateTime;
import java.util.*;

import org.springframework.stereotype.Service;

import com.bitespeed.Dto.IdentifyRequest;
import com.bitespeed.Entities.Contact;
import com.bitespeed.Entities.LinkPrecedence;
import com.bitespeed.Exceptions.IllegalArgumentException;
import com.bitespeed.Repositories.ContactRepository;
import com.bitespeed.ResponseDto.ContactDto;
import com.bitespeed.ResponseDto.IdentifyResponseDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ContactServiceImpl implements ContactService {

    private final ContactRepository contactRepository;

    @Override
    public IdentifyResponseDto identifyCustomer(IdentifyRequest request) {

        String email = request.email();
        String phoneNumber = request.phoneNumber();

        if (email == null && phoneNumber == null) {
            throw new IllegalArgumentException("Either email or phoneNumber must be provided.");
        }

        Set<Contact> matchContacts = new HashSet<>(
                contactRepository.findByEmailOrPhoneNumber(email, phoneNumber));

        if (matchContacts.isEmpty()) {
            Contact newContact = Contact.builder()
                    .email(email)
                    .phoneNumber(phoneNumber)
                    .linkPrecedence(LinkPrecedence.PRIMARY)
                    .createdAt(LocalDateTime.now())
                    .build();

            contactRepository.save(newContact);

            ContactDto contactDto = new ContactDto(
                    newContact.getId(),
                    List.of(newContact.getEmail()),
                    List.of(newContact.getPhoneNumber()),
                    List.of());

            return new IdentifyResponseDto(contactDto);
        }

        Set<Contact> allRelated = getAllLinkedContacts(matchContacts);

        Contact primary = allRelated.stream()
                .filter(c -> c.getLinkPrecedence() == LinkPrecedence.PRIMARY)
                .min(Comparator.comparing(Contact::getCreatedAt))
                .orElseThrow();

        for (Contact c : allRelated) {
            if (!c.getId().equals(primary.getId()) &&
                    (c.getLinkPrecedence() != LinkPrecedence.SECONDARY ||
                            !Objects.equals(c.getLinkedId(), primary.getId()))) {
                c.setLinkPrecedence(LinkPrecedence.SECONDARY);
                c.setLinkedId(primary.getId());
                c.setUpdatedAt(LocalDateTime.now());
                contactRepository.save(c);
            }
        }

        boolean alreadyExists = allRelated.stream()
                .anyMatch(c -> Objects.equals(c.getEmail(), email) &&
                        Objects.equals(c.getPhoneNumber(), phoneNumber));

        if (!alreadyExists) {
            Contact newSecondary = Contact.builder()
                    .email(email)
                    .phoneNumber(phoneNumber)
                    .linkPrecedence(LinkPrecedence.SECONDARY)
                    .linkedId(primary.getId())
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build();

            contactRepository.save(newSecondary);
            allRelated.add(newSecondary);
        }

        return formatResponse(primary, allRelated);
    }

    private Set<Contact> getAllLinkedContacts(Set<Contact> matchContacts) {
        Set<Contact> result = new HashSet<>(matchContacts);
        Queue<Contact> queue = new LinkedList<>(matchContacts);

        while (!queue.isEmpty()) {
            Contact curr = queue.poll();
            if (curr.getEmail() == null && curr.getPhoneNumber() == null)
                continue;

            List<Contact> linked = contactRepository.findByEmailOrPhoneNumber(
                    curr.getEmail(), curr.getPhoneNumber());

            for (Contact contact : linked) {
                if (result.add(contact)) {
                    queue.add(contact);
                }
            }
        }
        return result;
    }

    private IdentifyResponseDto formatResponse(Contact primary, Set<Contact> allRelated) {

        // just for debugging
        // for (Contact c : allRelated) {
        //     System.out.println(c);
        // }

        Set<String> emails = new LinkedHashSet<>();
        Set<String> phoneNumbers = new LinkedHashSet<>();
        List<Long> secondaryIds = new ArrayList<>();

        for (Contact c : allRelated) {
            if (c.getEmail() != null)
                emails.add(c.getEmail());
            if (c.getPhoneNumber() != null)
                phoneNumbers.add(c.getPhoneNumber());
            if (!c.getId().equals(primary.getId()))
                secondaryIds.add(c.getId());
        }

        ContactDto cr = new ContactDto(
                primary.getId(),
                new ArrayList<>(emails),
                new ArrayList<>(phoneNumbers),
                secondaryIds);

        return new IdentifyResponseDto(cr);
    }

}
