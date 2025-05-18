package com.bitespeed.Controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bitespeed.Dto.IdentifyRequest;
import com.bitespeed.ResponseDto.IdentifyResponseDto;
import com.bitespeed.Services.ContactServiceImpl;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/v1")
@RequiredArgsConstructor
public class ContactController {

    private final ContactServiceImpl contactService;

    @PostMapping("/identify")
    public ResponseEntity<IdentifyResponseDto> identify(@Valid @RequestBody IdentifyRequest request) throws Exception{
        return ResponseEntity.ok(contactService.identifyCustomer(request));
    }

    @GetMapping("/identify")
    public ResponseEntity<List<IdentifyResponseDto>> getIdentify() throws Exception{
        return ResponseEntity.ok(contactService.getAllIdentifyCustomer());
    }
}
