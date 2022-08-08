package tech.bananaz.spring.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import tech.bananaz.exceptions.BadRequestException;
import tech.bananaz.exceptions.InternalServerError;
import tech.bananaz.models.Field;
import static tech.bananaz.encryptors.Security.decrypt;
import static tech.bananaz.encryptors.Security.encrypt;

import java.util.Optional;

import static java.util.Objects.nonNull;

@Service
public class SecurityService {
	
	@Value("${bot.encryptionKey}")
	private String key;
	private final String NO_KEY_SET = "This API has no internal encryption key set and one was not provided in the header as X-Security-Key";
	
	public Field doEncrypt(Optional<String> keyOverwrite, Field dataIn) throws BadRequestException, InternalServerError {
		Field dataOut = new Field();
		String psswd = (keyOverwrite.isPresent()) ? keyOverwrite.get() : key; 
		if(nonNull(psswd)) {
			try {
				String encryptedData = encrypt(psswd, dataIn.getData());
				dataOut.setData(encryptedData);
			} catch (Exception e) {
				throw new BadRequestException(e.getMessage());
			}
		} else {
			throw new InternalServerError(NO_KEY_SET);
		}
		return dataOut;
	}
	
	public Field doDecrypt(String keyOverwrite, Field dataIn) throws BadRequestException, InternalServerError {
		Field dataOut = new Field();
		try {
			String decryptedData = decrypt(keyOverwrite, dataIn.getData());
			dataOut.setData(decryptedData);
		} catch (Exception e) {
			throw new BadRequestException(e.getMessage());
		}
		return dataOut;
	}

}
