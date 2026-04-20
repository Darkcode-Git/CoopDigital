package com.cooperativa.services.command;

import com.cooperativa.model.Cuenta;

import java.math.BigDecimal;
import java.util.Objects;

public final class ComandoRetiro implements ComandoTransaccion {
    private final Cuenta cuenta;
    private final BigDecimal monto;

    public ComandoRetiro(Cuenta cuenta, BigDecimal monto) {
        this.cuenta = Objects.requireNonNull(cuenta);
        this.monto = Objects.requireNonNull(monto);
    }

    @Override
    public void ejecutar() {
        cuenta.retirar(monto);
    }

    @Override
    public void deshacer() {
        cuenta.depositar(monto);
    }

    @Override
    public String descripcion() {
        return "Retiro " + monto + " de cuenta " + cuenta.getNumero();
    }
}
