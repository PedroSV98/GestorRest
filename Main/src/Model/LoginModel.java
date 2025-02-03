package Model;

import Controller.LogController;

public class LoginModel {
    private Configuracoes configuracoes;
    private LogController logController;

    // Construtor que recebe tanto o Configuracoes quanto o LogController
    public LoginModel(Configuracoes configuracoes, LogController logController){
        this.configuracoes = configuracoes;
        this.logController = logController;  // Inicializando o LogController corretamente
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

    public LogController getLogController() {
        return logController;
    }
}
