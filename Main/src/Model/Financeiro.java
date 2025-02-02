package Model;

public class Financeiro {

    private double totalFaturado = 0.0;
    private double totalGastos = 0.0;
    private int pedidosAtendidos = 0;

    public Financeiro(double totalFaturado, double totalGastos, int pedidosAtendidos, int pedidosNaoAtendidos, double saldoRestaurante) {
        this.totalFaturado = totalFaturado;
        this.totalGastos = totalGastos;
        this.pedidosAtendidos = pedidosAtendidos;
        this.pedidosNaoAtendidos = pedidosNaoAtendidos;
        this.saldoRestaurante = saldoRestaurante;
    }

    public double getTotalFaturado() {
        return totalFaturado;
    }

    public void setTotalFaturado(double totalFaturado) {
        this.totalFaturado = totalFaturado;
    }

    public double getTotalGastos() {
        return totalGastos;
    }

    public void setTotalGastos(double totalGastos) {
        this.totalGastos = totalGastos;
    }

    public int getPedidosAtendidos() {
        return pedidosAtendidos;
    }

    public void setPedidosAtendidos(int pedidosAtendidos) {
        this.pedidosAtendidos = pedidosAtendidos;
    }

    public int getPedidosNaoAtendidos() {
        return pedidosNaoAtendidos;
    }

    public void setPedidosNaoAtendidos(int pedidosNaoAtendidos) {
        this.pedidosNaoAtendidos = pedidosNaoAtendidos;
    }

    public double getSaldoRestaurante() {
        return saldoRestaurante;
    }

    public void setSaldoRestaurante(double saldoRestaurante) {
        this.saldoRestaurante = saldoRestaurante;
    }

    private int pedidosNaoAtendidos = 0;
    private double saldoRestaurante = 0;

}