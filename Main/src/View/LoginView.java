package View;

import Controller.InputHelper;
import java.util.Scanner;

public class LoginView {

    public void exibirMensagem(String mensagem) {
        System.out.println(mensagem);
    }

    public String inserirSenha() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Digite 0 para Voltar");
        // Utiliza o InputHelper para ler uma string (password) não vazia
        return InputHelper.lerString(scanner, "Digite a Password: ");
    }
}
