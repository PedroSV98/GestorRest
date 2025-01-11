package Model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Configuracoes {
    // Variáveis de instância que correspondem aos campos no ficheiro.
    private String caminhoFicheiros;
    private String separador;
    private int unidadesTempoDia;
    private int tempoEsperaAcao;
    private double custoClienteNaoAtendido;
    private String password;

    // Caminho absoluto do ficheiro de configurações
    private final String caminhoFicheiro;

    public Configuracoes(String caminhoFicheiro) {
        this.caminhoFicheiro = caminhoFicheiro;
    }

    /**
     * Carrega as configurações do ficheiro para as variáveis de instância.
     */
    public void carregarConfiguracoes() throws IOException {
        BufferedReader leitor = new BufferedReader(new FileReader(caminhoFicheiro));
        String linha;

        while ((linha = leitor.readLine()) != null) {
            // Exemplo de linha: "caminhoFicheiros: data/"
            String[] partes = linha.split(":");
            // Verifica se a linha tem, pelo menos, 2 partes (chave e valor).
            if (partes.length == 2) {
                String chave = partes[0].trim();   // Ex: "caminhoFicheiros"
                String valor = partes[1].trim();  // Ex: "data/"

                // Atribui o valor à variável de instância correspondente
                switch (chave) {
                    case "caminhoFicheiros":
                        this.caminhoFicheiros = valor;
                        break;
                    case "separador":
                        this.separador = valor;
                        break;
                    case "unidadesTempoDia":
                        this.unidadesTempoDia = Integer.parseInt(valor);
                        break;
                    case "tempoEsperaAcao":
                        this.tempoEsperaAcao = Integer.parseInt(valor);
                        break;
                    case "custoClienteNaoAtendido":
                        this.custoClienteNaoAtendido = Double.parseDouble(valor);
                        break;
                    case "password":
                        this.password = valor;
                        break;
                    default:
                        // Caso exista alguma chave desconhecida
                        System.out.println("Chave desconhecida no ficheiro: " + chave);
                }
            }
        }
        leitor.close();
    }

    /**
     * Guarda as configurações (das variáveis de instância) no ficheiro de configuração.
     */
    public void guardarConfiguracoes() throws IOException {
        BufferedWriter escritor = new BufferedWriter(new FileWriter(caminhoFicheiro));
        escritor.write("caminhoFicheiros: " + caminhoFicheiros);
        escritor.newLine();
        escritor.write("separador: " + separador);
        escritor.newLine();
        escritor.write("unidadesTempoDia: " + unidadesTempoDia);
        escritor.newLine();
        escritor.write("tempoEsperaAcao: " + tempoEsperaAcao);
        escritor.newLine();
        escritor.write("custoClienteNaoAtendido: " + custoClienteNaoAtendido);
        escritor.newLine();
        escritor.write("password: " + password);
        escritor.newLine();

        escritor.close();
    }

    // Métodos de acesso (Getters e Setters)
    public String getCaminhoFicheiros() {
        return caminhoFicheiros;
    }

    public void setCaminhoFicheiros(String caminhoFicheiros) {
        this.caminhoFicheiros = caminhoFicheiros;
    }

    public String getSeparador() {
        return separador;
    }

    public void setSeparador(String separador) {
        this.separador = separador;
    }

    public int getUnidadesTempoDia() {
        return unidadesTempoDia;
    }

    public void setUnidadesTempoDia(int unidadesTempoDia) {
        this.unidadesTempoDia = unidadesTempoDia;
    }

    public int getTempoEsperaAcao() {
        return tempoEsperaAcao;
    }

    public void setTempoEsperaAcao(int tempoEsperaAcao) {
        this.tempoEsperaAcao = tempoEsperaAcao;
    }

    public double getCustoClienteNaoAtendido() {
        return custoClienteNaoAtendido;
    }

    public void setCustoClienteNaoAtendido(double custoClienteNaoAtendido) {
        this.custoClienteNaoAtendido = custoClienteNaoAtendido;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
