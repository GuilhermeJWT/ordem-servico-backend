package br.com.systemsgs.ordem_servico_backend.enums;

public enum Role {

    ROLE_ADMIN("Administrador"),
    ROLE_TECNICO("Técnico"),
    ROLE_USUARIO_COMUM("Usuário Comum"),
    ROLE_VENDEDOR("Vendedor");

    private String role;

    Role(String role){
        this.role = role;
    }

    public String getRole(){
        return role;
    }
}
