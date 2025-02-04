package Controller;
import java.util.Scanner;

public class InputHelper {
    /**
     * Lê um número inteiro do utilizador, solicitar novamente caso o valor inserido não seja um número inteiro.
     * @param scanner O objeto Scanner a ser utilizado para a leitura.
     * @param mensagem A mensagem a ser exibida ao utilizador.
     * @return O número inteiro inserido.
     */
    public static int lerInteiro(Scanner scanner, String mensagem) {
        while (true) {
            System.out.print(mensagem);
            String input = scanner.nextLine();
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Por favor, insira um número inteiro.");
            }
        }
    }

    /**
     * Lê uma cadeia de caracteres (String) do utilizador, solicitar novamente caso a entrada seja inválida (vazia).
     * @param scanner O objeto Scanner a ser utilizado para a leitura.
     * @param mensagem A mensagem a ser exibida ao utilizador.
     * @return A cadeia de caracteres inserida.
     */
    public static String lerString(Scanner scanner, String mensagem) {
        while (true) {
            System.out.print(mensagem);
            String input = scanner.nextLine();
            if (input != null && !input.trim().isEmpty()) {
                return input;
            } else {
                System.out.println("Entrada inválida. Por favor, insira um texto válido.");
            }
        }
    }
    /**
     * Lê um valor booleano do utilizador, solicitar novamente caso a entrada não seja "true" ou "false".
     * @param scanner O objeto Scanner a ser utilizado para a leitura.
     * @param mensagem A mensagem a ser exibida ao utilizador.
     * @return O valor booleano inserido.
     */
    /**
     * Lê um valor do tipo double do utilizador, solicitar novamente caso a entrada seja inválida.
     * Aceita tanto ponto quanto vírgula como separador decimal.
     * @param scanner O objeto Scanner a ser utilizado para a leitura.
     * @param mensagem A mensagem a ser exibida ao utilizador.
     * @return O valor double inserido.
     */
    public static double lerDouble(Scanner scanner, String mensagem) {
        while (true) {
            System.out.print(mensagem);
            String input = scanner.nextLine().replace(",", ".");
            try {
                return Double.parseDouble(input);
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Por favor, insira um número válido.");
            }
        }
    }
    public static boolean lerBoolean(Scanner scanner, String mensagem) {
        while (true) {
            System.out.print(mensagem);
            String input = scanner.nextLine().trim();
            if (input.equalsIgnoreCase("true") || input.equalsIgnoreCase("false")) {
                return Boolean.parseBoolean(input);
            } else {
                System.out.println("Entrada inválida. Por favor, insira 'true' ou 'false'.");
            }
        }
    }
}
