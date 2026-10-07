package com.bookrats.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Configuracao do tema visual, lida do prefixo {@code app.theme} em
 * {@code application.properties}.
 *
 * <p>Isso permite "preparar o sistema para receber temas visuais
 * personalizados" sem recompilar HTML/CSS: basta trocar estes valores
 * (ou as variaveis de ambiente correspondentes) e o layout inteiro
 * (cores, nome exibido) muda, porque as paginas usam as variaveis CSS
 * geradas a partir deste objeto (veja {@code fragments/head.html}).</p>
 */
@ConfigurationProperties(prefix = "app.theme")
public class ThemeProperties {

    private String name = "BookRats";
    private String primaryColor = "#6b4ea6";
    private String secondaryColor = "#9b7fd4";
    private String accentColor = "#f4c95d";
    private String backgroundColor = "#f6f1fb";
    private String textColor = "#2b2240";
    private String logoText = "BookRats";

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrimaryColor() {
        return primaryColor;
    }

    public void setPrimaryColor(String primaryColor) {
        this.primaryColor = primaryColor;
    }

    public String getSecondaryColor() {
        return secondaryColor;
    }

    public void setSecondaryColor(String secondaryColor) {
        this.secondaryColor = secondaryColor;
    }

    public String getAccentColor() {
        return accentColor;
    }

    public void setAccentColor(String accentColor) {
        this.accentColor = accentColor;
    }

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public String getTextColor() {
        return textColor;
    }

    public void setTextColor(String textColor) {
        this.textColor = textColor;
    }

    public String getLogoText() {
        return logoText;
    }

    public void setLogoText(String logoText) {
        this.logoText = logoText;
    }
}
