package com.example.s3practice1.practice1.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT,reason = "Customer Already Exists")
public class CustomerAlreadyExistsException extends Exception{
}
