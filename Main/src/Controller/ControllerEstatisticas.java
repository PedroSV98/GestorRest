package Controller;

import Controller.ControllerGestaoDiaADia;
import Model.Prato;

public class ControllerEstatisticas {
    private final ControllerGestaoDiaADia controllerGestao;

    public ControllerEstatisticas(ControllerGestaoDiaADia controllerGestao) {
        this.controllerGestao = controllerGestao;
    }

    public Prato getPratoMaisPedido() {
        return controllerGestao.obterPratoMaisVendido();
    }

    public int getTotalClientesAtendidos() {
        return controllerGestao.getTotalClientesAtendidos();
    }

    public double getTempoMedioEspera() {
        return controllerGestao.getTempoMedioEspera();
    }

    public double getTempoMedioParaServirMesa() {
        return controllerGestao.getTempoMedioParaServirMesa();
    }

    public void exibirEstatisticasGerais() {
        controllerGestao.exibirEstatisticasGerais();
    }
}
