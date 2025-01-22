package Model;

public class LoginModel {
    private Configuracoes configuracoes;

    public LoginModel(Configuracoes configuracoes){
        this.configuracoes = configuracoes;
    }

    public boolean validarSenha(String senha) {
        return senha != null && senha.equals(configuracoes.getPassword());
    }

    public void alterarSenha(String novaSenha) {
        configuracoes.setSenhaEmMemoria(novaSenha);
    }

    public Configuracoes getConfiguracoes() {
        return configuracoes;
    }
}

