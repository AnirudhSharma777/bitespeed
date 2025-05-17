package com.bitespeed.ResponseDto;


public record ErrorResponse(
    String errorCode,
    String errorMessage
) {

}