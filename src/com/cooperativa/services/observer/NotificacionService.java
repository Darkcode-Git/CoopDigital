package com.cooperativa.services.observer;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class NotificacionService implements SaldoObserver {
    private final List<String> eventos = new ArrayList<>();

    @Override
    public void onSaldoCambiado(String numeroCuenta, BigDecimal nuevoSaldo) {
        eventos.add("Cuenta " + numeroCuenta + " nuevo saldo " + nuevoSaldo);
    }

    public List<String> getEventos() {
        return Collections.unmodifiableList(eventos);
    }
}
