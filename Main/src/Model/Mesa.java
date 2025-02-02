package Model;

public class Mesa {
    private int id;
    private int lugares;
    private boolean ocupada;

    private int tempoEntradaCliente;
    private int tempoLiberacao;  // Quando a mesa foi desocupada


    public Mesa(int id, boolean ocupada, int lugares) {
        this.id = id;
        this.ocupada = ocupada;
        this.lugares = lugares;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getLugares() {
        return lugares;
    }
    public void setLugares(int lugares) {
        this.lugares = lugares;
    }
    public boolean isOcupada() {
        return ocupada;
    }
    public void setOcupada(boolean ocupada) {
        this.ocupada = ocupada;
    }

    public int getTempoEspera() {
        return tempoLiberacao - tempoEntradaCliente;
    }

    public void setTempoEntradaCliente(int tempoEntrada) {
        this.tempoEntradaCliente = tempoEntrada;
    }

    public void setTempoLiberacao(int tempoLiberacao) {
        this.tempoLiberacao = tempoLiberacao;
    }

}