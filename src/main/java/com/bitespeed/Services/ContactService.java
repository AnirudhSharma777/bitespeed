package com.bitespeed.Services;

import com.bitespeed.Dto.IdentifyRequest;
import com.bitespeed.ResponseDto.IdentifyResponseDto;

public interface ContactService {
    public IdentifyResponseDto identifyCustomer(IdentifyRequest request) throws Exception;
}
