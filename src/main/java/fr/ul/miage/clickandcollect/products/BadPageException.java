package fr.ul.miage.clickandcollect.products;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadPageException extends RuntimeException {

	public BadPageException(int page) {
		super("Page " + page + "n'existe pas");
	}
	
}
