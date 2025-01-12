package View;

import java.util.Scanner;

public class LoginView {
    public void exibirMensagem(String mensagem) {
        System.out.println(mensagem);
    }

    public String inserirSenha() {
        Scanner scanner = new Scanner(System.in);
        String input;
        do {
            System.out.println("Digite 0 para Voltar");
            System.out.print("Digite a Password: ");
            input = scanner.nextLine().trim();
            if (input.isEmpty()) {
                System.out.println("O Campo não pode ficar vazio. Tente Novamente: ");
            }
        }
        while (input.isEmpty());
        return input;
    }
}
