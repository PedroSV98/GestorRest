package Model;

public class Pedido extends Mesa {
    private double totalCusto;
    private double precoTotal;
    private double lucro;

    public Pedido(int id, boolean ocupada, int lugares) {
        super(id, ocupada, lugares);
    }

    public double getTotalCusto() {
        return totalCusto;
    }
    public void setTotalCusto(double totalCusto) {
        this.totalCusto = totalCusto;
    }
    public double getPrecoTotal() {
        return precoTotal;
    }
    public void setPrecoTotal(double precoTotal) {
        this.precoTotal = precoTotal;
    }
    public double getLucro() {
        return lucro;
    }
    public void setLucro(double lucro) {
        this.lucro = lucro;
    }
    
}
