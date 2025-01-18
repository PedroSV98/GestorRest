package Model;

public class Reserva {

    private String nomeReserva;
    private Integer qtdPessoas;
    private Integer tempoEntrada;

    public String getNomeReserva() {
        return nomeReserva;
    }

    public void setNomeReserva(String nomeReserva) {
        this.nomeReserva = nomeReserva;
    }

    public Integer getQtdPessoas() {
        return qtdPessoas;
    }

    public void setQtdPessoas(Integer qtdPessoas) {
        this.qtdPessoas = qtdPessoas;
    }

    public Integer getTempoEntrada() {
        return tempoEntrada;
    }

    public void setTempoEntrada(Integer tempoEntrada) {
        this.tempoEntrada = tempoEntrada;
    }
}
