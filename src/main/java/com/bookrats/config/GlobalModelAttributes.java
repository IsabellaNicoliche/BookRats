package com.bookrats.config;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

/**
 * Injeta o tema visual configurado em {@link ThemeProperties} em TODAS as
 * paginas renderizadas, sem precisar que cada controller adicione isso
 * manualmente ao Model. E assim que o design fica realmente separado da
 * logica de negocio: nenhum controller precisa saber de cores ou nome do
 * tema, apenas os templates leem o atributo "theme".
 */
@ControllerAdvice
public class GlobalModelAttributes {

    private final ThemeProperties themeProperties;

    public GlobalModelAttributes(ThemeProperties themeProperties) {
        this.themeProperties = themeProperties;
    }

    @ModelAttribute("theme")
    public ThemeProperties theme() {
        return themeProperties;
    }
}
