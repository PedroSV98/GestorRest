package Model;

public class Pedido {
    private String cliente;
    private int mesaId;
    private String estado; // Exemplo: "ENCAMINHADO", "PREPARAR", "CONSUMIDO", "FINALIZADO", "PAGO"
    private int tempoEntrada; // Armazena o tempo em que o cliente chegou
    private int tempoEscolha; //  Novo campo para armazenar o tempo da escolha dos pratos
    private int tempoPagamento; // Inicialmente indefinido
    private int quantidadePessoas;

    private Prato entrada;
    private Prato principal;
    private Prato sobremesa;

    private int quantidadeEntrada;
    private int quantidadePrincipal;
    private int quantidadeSobremesa;

    // Construtor para clientes de reserva e espontâneos
    public Pedido(String cliente, int quantidadePessoas, int tempoEntrada) {
        this.cliente = cliente;
        this.quantidadePessoas = quantidadePessoas;
        this.tempoEntrada = tempoEntrada;
        this.tempoEscolha = -1; //  Inicialmente indefinido
        this.tempoPagamento = -1; // Inicialmente indefenido
        this.estado = "ENCAMINHADO"; // Cliente acabou de ser encaminhado
    }

    public void definirTempoPagamento(int tempo) {
        this.tempoPagamento = tempo;
    }

    public int getTempoPagamento() {
        return tempoPagamento;
    }


    // Getter e Setter para o tempo de entrada
    public int getTempoEntrada() {
        return tempoEntrada;
    }

    public void definirTempoEscolha(int tempoEscolha) {
        this.tempoEscolha = tempoEscolha;
    }

    public int getTempoEscolha() {
        return tempoEscolha;
    }


    // Getter para quantidade de pessoas no pedido
    public int getQtdPessoas() {
        return quantidadePessoas;
    }

    // Setter para quantidade de pessoas
    public void setQtdPessoas(int quantidadePessoas) {
        this.quantidadePessoas = quantidadePessoas;
    }

    // Getters e Setters básicos
    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public int getMesaId() {
        return mesaId;
    }

    public void setMesaId(int mesaId) {
        this.mesaId = mesaId;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Prato getEntrada() {
        return entrada;
    }

    public void setEntrada(Prato entrada, int quantidade) {
        this.entrada = entrada;
        this.quantidadeEntrada = quantidade;
    }

    public Prato getPrincipal() {
        return principal;
    }

    public void setPrincipal(Prato principal, int quantidade) {
        this.principal = principal;
        this.quantidadePrincipal = quantidade;
    }

    public Prato getSobremesa() {
        return sobremesa;
    }

    public void setSobremesa(Prato sobremesa, int quantidade) {
        this.sobremesa = sobremesa;
        this.quantidadeSobremesa = quantidade;
    }

    // Métodos para verificar o estado do pedido
    public boolean isEncaminhado() {
        return "ENCAMINHADO".equalsIgnoreCase(estado);
    }

    public boolean isPreparar() {
        return "PREPARAR".equalsIgnoreCase(estado);
    }

    public boolean isConsumir() {
        return "CONSUMIR".equalsIgnoreCase(estado);
    }

    public boolean isFinalizado() {
        return "FINALIZADO".equalsIgnoreCase(estado);
    }

    public boolean isPago() {
        return "PAGO".equalsIgnoreCase(estado);
    }

    // Cálculo do custo total dos pratos do pedido
    public double calcularPrecoCusto() {
        double total = 0;
        if (entrada != null) {
            total += entrada.getPC() * quantidadeEntrada;
        }
        if (principal != null) {
            total += principal.getPC() * quantidadePrincipal;
        }
        if (sobremesa != null) {
            total += sobremesa.getPC() * quantidadeSobremesa;
        }
        return total;
    }

    // Cálculo do preço total de venda dos pratos do pedido
    public double calcularPrecoTotal() {
        double total = 0;
        if (entrada != null) {
            total += entrada.getPV() * quantidadeEntrada;
        }
        if (principal != null) {
            total += principal.getPV() * quantidadePrincipal;
        }
        if (sobremesa != null) {
            total += sobremesa.getPV() * quantidadeSobremesa;
        }
        return total;
    }

    public int getMaiorTempoPreparacao() {
        int maiorPreparacao = 0;

        if (entrada != null) {
            maiorPreparacao = Math.max(maiorPreparacao, entrada.getTempPrep());
        }
        if (principal != null) {
            maiorPreparacao = Math.max(maiorPreparacao, principal.getTempPrep());
        }
        if (sobremesa != null) {
            maiorPreparacao = Math.max(maiorPreparacao, sobremesa.getTempPrep());
        }

        return maiorPreparacao; // Agora considera apenas os pratos escolhidos pelo cliente
    }

    public int getMaiorTempoConsumo() {
        int maiorConsumo = 0;

        if (entrada != null) {
            maiorConsumo = Math.max(maiorConsumo, entrada.getTempCons());
        }
        if (principal != null) {
            maiorConsumo = Math.max(maiorConsumo, principal.getTempCons());
        }
        if (sobremesa != null) {
            maiorConsumo = Math.max(maiorConsumo, sobremesa.getTempCons());
        }

        return maiorConsumo; // Agora considera apenas os pratos escolhidos pelo cliente
    }
}