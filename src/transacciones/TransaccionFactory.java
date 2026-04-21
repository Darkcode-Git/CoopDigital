package transacciones;

import modelo.Cuenta;

public final class TransaccionFactory {
    private TransaccionFactory() {
    }

    public static Transaccion crearDeposito(Cuenta cuenta, double monto) {
        return new Deposito(cuenta, monto);
    }

    public static Transaccion crearRetiro(Cuenta cuenta, double monto) {
        return new Retiro(cuenta, monto);
    }
}
