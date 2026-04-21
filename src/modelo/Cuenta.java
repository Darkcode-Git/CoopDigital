package modelo;

import java.util.Objects;

public abstract class Cuenta {
    private final String numeroCuenta;
    protected double saldo;

    protected Cuenta(String numeroCuenta, double saldoInicial) {
        this.numeroCuenta = validarNumeroCuenta(numeroCuenta);
        if (saldoInicial < 0) {
            throw new IllegalArgumentException("El saldo inicial no puede ser negativo.");
        }
        this.saldo = saldoInicial;
    }

    public void depositar(double monto) {
        if (monto <= 0) {
            throw new IllegalArgumentException("El monto del depósito debe ser mayor a 0.");
        }
        saldo += monto;
    }

    public void retirar(double monto) {
        if (monto <= 0) {
            throw new IllegalArgumentException("El monto del retiro debe ser mayor a 0.");
        }
        if (saldo < monto) {
            throw new IllegalArgumentException(
                    String.format("Saldo insuficiente. Saldo actual: %.2f, retiro solicitado: %.2f", saldo, monto));
        }
        saldo -= monto;
    }

    public abstract void aplicarPoliticaMensual();

    public String getNumeroCuenta() {
        return numeroCuenta;
    }

    public double getSaldo() {
        return saldo;
    }

    private static String validarNumeroCuenta(String numeroCuenta) {
        String numero = Objects.requireNonNull(numeroCuenta, "El número de cuenta no puede ser nulo.");
        if (numero.isBlank()) {
            throw new IllegalArgumentException("El número de cuenta no puede ser vacío.");
        }
        return numero;
    }
}
