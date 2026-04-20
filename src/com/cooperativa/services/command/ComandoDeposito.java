package com.cooperativa.services.command;

import com.cooperativa.model.Cuenta;

import java.math.BigDecimal;
import java.util.Objects;

public final class ComandoDeposito implements ComandoTransaccion {
    private final Cuenta cuenta;
    private final BigDecimal monto;

    public ComandoDeposito(Cuenta cuenta, BigDecimal monto) {
        this.cuenta = Objects.requireNonNull(cuenta);
        this.monto = Objects.requireNonNull(monto);
    }

    @Override
    public void ejecutar() {
        cuenta.depositar(monto);
    }

    @Override
    public void deshacer() {
        cuenta.retirar(monto);
    }

    @Override
    public String descripcion() {
        return "Depósito " + monto + " en cuenta " + cuenta.getNumero();
    }
}
