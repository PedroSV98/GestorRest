package Model;

public class Pedido extends Mesa {

    private int idPedido;
    private Prato prato; // Delegação de Prato
    private double precoPedidoCusto;
    private double precoPedidoTotal;
    private Integer qtEntrada;
    private Integer qtPrincipal;
    private Integer qtSobremesa;
    private double lucro;

    // Construtor
    public Pedido(int id, boolean ocupada, int lugares, Prato prato) {
        super(id, ocupada, lugares);
        this.prato = prato;
    }

    // Métodos para idPedido
    public int getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

    // Métodos para precoPedidoCusto e precoPedidoTotal
    public double getPrecoPedidoCusto() {
        return precoPedidoCusto;
    }

    public void setPrecoPedidoCusto(double precoPedidoCusto) {
        this.precoPedidoCusto = precoPedidoCusto;
    }

    public double getPrecoPedidoTotal() {
        return precoPedidoTotal;
    }

    public void setPrecoPedidoTotal(double precoPedidoTotal) {
        this.precoPedidoTotal = precoPedidoTotal;
    }

    // Métodos para quantidades
    public Integer getQtEntrada() {
        return qtEntrada;
    }

    public void setQtEntrada(Integer qtEntrada) {
        this.qtEntrada = qtEntrada;
    }

    public Integer getQtPrincipal() {
        return qtPrincipal;
    }

    public void setQtPrincipal(Integer qtPrincipal) {
        this.qtPrincipal = qtPrincipal;
    }

    public Integer getQtSobremesa() {
        return qtSobremesa;
    }

    public void setQtSobremesa(Integer qtSobremesa) {
        this.qtSobremesa = qtSobremesa;
    }

    // Métodos para lucro
    public double getLucro() {
        return lucro;
    }

    public void setLucro(double lucro) {
        this.lucro = lucro;
    }

    // Métodos delegados de Prato
    public String getNomePrato() {
        return prato.getNome();
    }

    public void setNomePrato(String nome) {
        prato.setNome(nome);
    }

    public String getCategoriaPrato() {
        return prato.getCategoria();
    }

    public void setCategoriaPrato(String categoria) {
        prato.setCategoria(categoria);
    }

    public double getPC() {
        return prato.getPC();
    }

    public void setPC(double PC) {
        prato.setPC(PC);
    }

    public double getPV() {
        return prato.getPV();
    }

    public void setPV(double PV) {
        prato.setPV(PV);
    }

    public int getTempPrepPrato() {
        return prato.getTempPrep();
    }

    public void setTempPrepPrato(int tempPrep) {
        prato.setTempPrep(tempPrep);
    }

    public int getTempConsPrato() {
        return prato.getTempCons();
    }

    public void setTempConsPrato(int tempCons) {
        prato.setTempCons(tempCons);
    }

    public boolean isEstadoPrato() {
        return prato.isEstado();
    }

    public void setEstadoPrato(boolean estado) {
        prato.setEstado(estado);
    }
}
