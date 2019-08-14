/**
 * 
 */
package br.com.alura.Forum.config.validacao;

import javax.persistence.EntityNotFoundException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author geovan.goes
 *
 */
@ResponseStatus(code = HttpStatus.NOT_FOUND)
@RestControllerAdvice
public class EntityNotFoundHandler 
{
	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<?> handle(EntityNotFoundException exception)
	{
		return ResponseEntity.notFound().build();
	}
}
