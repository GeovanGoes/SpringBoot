 /**
 * 
 */
package br.com.alura.Forum.config.validacao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


/**
 * @author geovan.goes
 *
 */
@ResponseStatus(code = HttpStatus.BAD_REQUEST)
@RestControllerAdvice
public class ErroValidacaoHandler 
{
	
	@Autowired
	private MessageSource messageSource;
	
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public List<ErroDeFormularioDto> handler(MethodArgumentNotValidException exception)
	{
		List<ErroDeFormularioDto> result = new ArrayList<>();
		exception.getBindingResult().getFieldErrors().forEach(field -> {
			
			String mensagem = messageSource.getMessage(field, LocaleContextHolder.getLocale());
			ErroDeFormularioDto dto = new ErroDeFormularioDto(field.getField(), mensagem);
			result.add(dto);
		});
		
		return result;
	}
}
