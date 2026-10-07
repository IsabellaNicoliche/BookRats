package com.bookrats.service.exception;

/**
 * Lancada quando o cadastro tenta usar um username ou e-mail que ja existe.
 * Capturada no controller para ser exibida como erro de validacao no formulario.
 */
public class UsuarioJaExisteException extends RuntimeException {

    public UsuarioJaExisteException(String message) {
        super(message);
    }
}
