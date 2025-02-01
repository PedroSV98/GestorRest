package Model;

public class Pedido {
    private String cliente;
    private int mesaId;
    private String estado; // Exemplo: "ENCAMINHADO", "PREPARAR", "CONSUMIDO","FINALIZADO" "PAGO"
    private int tempoInicioPreparacao;

    private Prato entrada;
    private Prato principal;
    private Prato sobremesa;

    private int quantidadeEntrada;
    private int quantidadePrincipal;
    private int quantidadeSobremesa;

    // Construtor padrão
    public Pedido(String cliente) {
        this.cliente = cliente;
        this.estado = "NOVO";
        this.tempoInicioPreparacao = 0;
    }

    // Construtor adicional
    public Pedido(String cliente, int mesaId, int tempoInicioPreparacao) {
        this.cliente = cliente;
        this.mesaId = mesaId;
        this.tempoInicioPreparacao = tempoInicioPreparacao;
        this.estado = "NOVO";
    }

    // Getters e Setters...
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

    public int getTempoInicioPreparacao() {
        return tempoInicioPreparacao;
    }

    public void setTempoInicioPreparacao(int tempoInicioPreparacao) {
        this.tempoInicioPreparacao = tempoInicioPreparacao;
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


    // Verifica o estado do pedido
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

        return maiorPreparacao;
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

        return maiorConsumo;
    }
}