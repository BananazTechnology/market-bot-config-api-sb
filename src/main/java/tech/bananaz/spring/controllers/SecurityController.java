package tech.bananaz.spring.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.bananaz.exceptions.BadRequestException;
import tech.bananaz.exceptions.InternalServerError;
import tech.bananaz.models.Field;
import tech.bananaz.spring.services.SecurityService;

@RestController
@RequestMapping(path = "/security", produces = "application/json")
public class SecurityController {
	
	@Autowired
	SecurityService securityService;
	
	@PostMapping(path = "/encrypt")
	public Field encrypt(@RequestHeader("X-Security-Key") Optional<String> key, @RequestBody Field dataIn) throws BadRequestException, InternalServerError {
		// Process response
		return securityService.doEncrypt(key, dataIn);
	}
	
	@PostMapping(path = "/decrypt")
	public Field decrypt(@RequestHeader("X-Security-Key") Optional<String> key, @RequestBody Field dataIn) throws BadRequestException, InternalServerError {
		if(key.isEmpty()) throw new BadRequestException("Required request header 'X-Security-Key' is not present");
		// Process response
		return securityService.doDecrypt(key.get(), dataIn);
	}
}