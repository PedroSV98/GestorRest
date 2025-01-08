package Model;

public class Prato {
    private String nome;
    private String categoria;
    private double PC;
    private double PV;
    private int tempPrep;
    private boolean estado;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public double getPC() {
        return PC;
    }

    public void setPC(double PC) {
        this.PC = PC;
    }

    public double getPV() {
        return PV;
    }

    public void setPV(double PV) {
        this.PV = PV;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public int getTempPrep() {
        return tempPrep;
    }

    public void setTempPrep(int tempPrep) {
        this.tempPrep = tempPrep;
    }
}
