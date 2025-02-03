package View;

import Controller.LogController;

public class LoginView {
    private final LogController.ScannerLog scannerLog;

    public LoginView(LogController logController) {
        // Inicializando o ScannerLog com a instância de LogController
        this.scannerLog = logController.new ScannerLog(new java.util.Scanner(System.in), logController);
    }

    public void exibirMensagem(String mensagem) {
        System.out.println(mensagem);
    }

    public String inserirSenha() {
        String input;
        do {
            System.out.println("Digite 0 para Voltar");
            System.out.print("Digite a Password: ");
            input = scannerLog.nextLine().trim();  // Usando scannerLog para ler a entrada
            if (input.isEmpty()) {
                System.out.println("O Campo não pode ficar vazio. Tente Novamente: ");
            }
        } while (input.isEmpty());
        return input;
    }
}
