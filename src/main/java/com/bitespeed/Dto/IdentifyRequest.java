package com.bitespeed.Dto;

import jakarta.validation.constraints.Email;

public record IdentifyRequest(

    @Email(message = "Invalid email format")
    String email,

    
    String phoneNumber
) {}
