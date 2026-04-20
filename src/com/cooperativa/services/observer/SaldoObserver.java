package com.cooperativa.services.observer;

import java.math.BigDecimal;

@FunctionalInterface
public interface SaldoObserver {
    void onSaldoCambiado(String numeroCuenta, BigDecimal nuevoSaldo);
}
