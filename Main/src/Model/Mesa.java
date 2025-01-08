package Model;

public class Mesa {
    private int id;
    private int lugares;
    private boolean ocupada;

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
}
