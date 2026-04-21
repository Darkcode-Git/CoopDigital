package modelo;

public final class CuentaAhorros extends Cuenta {
    private final double tasaInteres;

    public CuentaAhorros(String numeroCuenta, double saldoInicial, double tasaInteres) {
        super(numeroCuenta, saldoInicial);
        if (tasaInteres < 0) {
            throw new IllegalArgumentException("La tasa de interés no puede ser negativa.");
        }
        this.tasaInteres = tasaInteres;
    }

    public void aplicarInteres() {
        saldo += saldo * tasaInteres;
    }

    @Override
    public void aplicarPoliticaMensual() {
        aplicarInteres();
    }

    public double getTasaInteres() {
        return tasaInteres;
    }

    @Override
    public String toString() {
        return String.format("CuentaAhorros{numero='%s', saldo=%.2f, interes=%.2f%%}",
                getNumeroCuenta(), getSaldo(), tasaInteres * 100);
    }
}
