package com.cooperativa.model;

import com.cooperativa.exceptions.SaldoInsuficienteException;
import com.cooperativa.services.observer.SaldoObserver;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;

public abstract class Cuenta implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private final String numero;
    private BigDecimal saldo;
    private final List<SaldoObserver> observers;

    protected Cuenta(String numero, BigDecimal saldoInicial) {
        this.numero = validarNumero(numero);
        this.saldo = validarSaldo(saldoInicial);
        this.observers = new CopyOnWriteArrayList<>();
    }

    public synchronized void depositar(BigDecimal monto) {
        saldo = saldo.add(validarMonto(monto));
        notificarSaldo();
    }

    public synchronized void retirar(BigDecimal monto) {
        BigDecimal montoValido = validarMonto(monto);
        if (saldo.compareTo(montoValido) < 0) {
            throw new SaldoInsuficienteException("Saldo insuficiente para retirar " + montoValido);
        }
        saldo = saldo.subtract(montoValido);
        notificarSaldo();
    }

    public synchronized BigDecimal getSaldo() {
        return saldo;
    }

    public String getNumero() {
        return numero;
    }

    public void addObserver(SaldoObserver observer) {
        observers.add(Objects.requireNonNull(observer));
    }

    public void removeObserver(SaldoObserver observer) {
        observers.remove(observer);
    }

    protected void notificarSaldo() {
        for (SaldoObserver observer : observers) {
            observer.onSaldoCambiado(numero, saldo);
        }
    }

    public abstract String getTipo();

    private static String validarNumero(String numero) {
        Objects.requireNonNull(numero, "El número de cuenta no puede ser nulo");
        if (numero.isBlank()) {
            throw new IllegalArgumentException("El número de cuenta no puede estar vacío");
        }
        return numero;
    }

    private static BigDecimal validarSaldo(BigDecimal saldo) {
        Objects.requireNonNull(saldo, "El saldo no puede ser nulo");
        if (saldo.signum() < 0) {
            throw new IllegalArgumentException("El saldo inicial no puede ser negativo");
        }
        return saldo;
    }

    private static BigDecimal validarMonto(BigDecimal monto) {
        Objects.requireNonNull(monto, "El monto no puede ser nulo");
        if (monto.signum() <= 0) {
            throw new IllegalArgumentException("El monto debe ser mayor a cero");
        }
        return monto;
    }
}
