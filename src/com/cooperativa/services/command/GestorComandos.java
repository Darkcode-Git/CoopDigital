package com.cooperativa.services.command;

import java.util.ArrayDeque;
import java.util.Deque;

public final class GestorComandos {
    private final Deque<ComandoTransaccion> historial = new ArrayDeque<>();
    private final Deque<ComandoTransaccion> rehacer = new ArrayDeque<>();

    public void ejecutar(ComandoTransaccion comando) {
        comando.ejecutar();
        historial.push(comando);
        rehacer.clear();
    }

    public void deshacer() {
        if (historial.isEmpty()) {
            return;
        }
        ComandoTransaccion comando = historial.pop();
        comando.deshacer();
        rehacer.push(comando);
    }

    public void rehacer() {
        if (rehacer.isEmpty()) {
            return;
        }
        ComandoTransaccion comando = rehacer.pop();
        comando.ejecutar();
        historial.push(comando);
    }
}
