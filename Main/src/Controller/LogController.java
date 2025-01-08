package Controller;

import Model.Log;

public class LogController {
    private Log log;

    public LogController() {
        log = new Log();
    }

    public void registrarEvento(String mensagem) {
        log.registrarEvento(mensagem);
    }
}
