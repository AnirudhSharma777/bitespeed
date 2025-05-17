package com.bitespeed.ResponseDto;

import java.util.List;

public record ContactDto(
    Long primaryContactId,
    List<String> emails,
    List<String> phoneNumbers,
    List<Long> secondaryContactIds
) {}