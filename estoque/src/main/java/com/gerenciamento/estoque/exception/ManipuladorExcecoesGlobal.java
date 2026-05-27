package com.gerenciamento.estoque.exception;

import java.time.LocalDateTime;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice // Diz ao Spring que esta classe vai interceptar erros globalmente
public class ManipuladorExcecoesGlobal {

    // Captura especificamente erros de RuntimeException (como o nosso erro de estoque)
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErroResposta> tratarRuntimeException(RuntimeException ex, HttpServletRequest request) {
        
        // Criamos o nosso objeto de erro customizado
        ErroResposta erro = new ErroResposta(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(), // Muda o status de 500 para 400 (Bad Request)
                "Erro na Operação",
                ex.getMessage(), // Pega a mensagem que escrevemos no Service ("Saldo insuficiente...")
                request.getRequestURI() // Pega o link que o usuário tentou acessar
        );

        // Retorna a resposta formatada em JSON com o status HTTP 400
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
    }
}