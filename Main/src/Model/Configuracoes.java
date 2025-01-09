package Model;

public class Configuracoes {
    private String caminhoFicheiros;
    private String separador;
    private int unidadesTempoDia;
    private int tempoEsperaAcao;
    private double custoClienteNaoAtendido;
    private String password;

    // Construtor
    public Configuracoes() {
        // Configurações padrão
        this.caminhoFicheiros = "C:/restaurante";
        this.separador = ";";
        this.unidadesTempoDia = 24;
        this.tempoEsperaAcao = 5;
        this.custoClienteNaoAtendido = 10.0;
        this.password = "admin123"; // Senha padrão
    }

    // Getters e Setters
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

    // Método para exibir as configurações
    public void exibirConfiguracoes() {
        System.out.println("=== Configurações Atuais ===");
        System.out.println("Caminho dos Ficheiros: " + caminhoFicheiros);
        System.out.println("Separador: " + separador);
        System.out.println("Unidades de Tempo por Dia: " + unidadesTempoDia);
        System.out.println("Tempo de Espera para Ação: " + tempoEsperaAcao);
        System.out.println("Custo por Cliente Não Atendido: " + custoClienteNaoAtendido);
        System.out.println("Senha: " + password);  // Para fins de teste, senão, remova a senha da exibição
    }
}

