package com.bitespeed.Services;

import java.util.List;

import com.bitespeed.Dto.IdentifyRequest;
import com.bitespeed.ResponseDto.IdentifyResponseDto;

public interface ContactService {
    public IdentifyResponseDto identifyCustomer(IdentifyRequest request) throws Exception;
    public List<IdentifyResponseDto> getAllIdentifyCustomer() throws Exception;
}
