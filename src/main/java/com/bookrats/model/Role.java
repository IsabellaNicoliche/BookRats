package com.bookrats.model;

/**
 * Perfis de acesso do sistema.
 *
 * <p>O sistema exige no minimo 3 perfis distintos. Para o tema BookRats:</p>
 * <ul>
 *   <li>{@link #LEITOR} - usuario comum, acessa sua propria area/leitura.</li>
 *   <li>{@link #MODERADOR} - alem do acesso de LEITOR, modera conteudo
 *       gerado por usuarios (ex.: resenhas, sorteios).</li>
 *   <li>{@link #ADMIN} - acesso total, inclusive gestao de usuarios e perfis.</li>
 * </ul>
 *
 * <p>Para reaproveitar este projeto em outro tema (ex.: o PFC, trocando
 * para ALUNO/PROFESSOR/COORDENADOR/ADMIN), basta alterar os valores deste
 * enum e os labels em {@link #getLabel()} - o restante da logica de
 * autenticacao/autorizacao em {@code SecurityConfig} e nos controllers
 * trabalha com estes nomes sem precisar de outras mudancas estruturais.</p>
 */
public enum Role {

    LEITOR("Leitor"),
    MODERADOR("Moderador"),
    ADMIN("Administrador");

    private final String label;

    Role(String label) {
        this.label = label;
    }

    /** Nome amigavel do perfil, usado nas telas. */
    public String getLabel() {
        return label;
    }

    /**
     * Nome da authority usada pelo Spring Security (prefixo "ROLE_").
     * Mantido aqui para nao espalhar essa regra pelo codigo.
     */
    public String getAuthority() {
        return "ROLE_" + name();
    }
}
