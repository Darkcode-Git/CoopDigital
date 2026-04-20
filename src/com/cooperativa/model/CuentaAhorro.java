package com.cooperativa.model;

import java.math.BigDecimal;
import java.math.RoundingMode;

public final class CuentaAhorro extends Cuenta {
    private final BigDecimal tasaInteres;

    public CuentaAhorro(String numero, BigDecimal saldoInicial, BigDecimal tasaInteres) {
        super(numero, saldoInicial);
        if (tasaInteres == null || tasaInteres.signum() < 0) {
            throw new IllegalArgumentException("La tasa de interés debe ser positiva");
        }
        this.tasaInteres = tasaInteres;
    }

    public synchronized void aplicarInteres() {
        BigDecimal interes = getSaldo().multiply(tasaInteres).setScale(2, RoundingMode.HALF_UP);
        depositar(interes);
    }

    public BigDecimal getTasaInteres() {
        return tasaInteres;
    }

    @Override
    public String getTipo() {
        return "AHORRO";
    }
}
