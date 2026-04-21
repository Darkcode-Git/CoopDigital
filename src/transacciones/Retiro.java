package transacciones;

import java.util.Objects;
import modelo.Cuenta;

public final class Retiro implements Transaccion {
    private final Cuenta cuenta;
    private final double monto;

    public Retiro(Cuenta cuenta, double monto) {
        this.cuenta = Objects.requireNonNull(cuenta, "La cuenta no puede ser nula.");
        if (monto <= 0) {
            throw new IllegalArgumentException("El monto del retiro debe ser mayor a 0.");
        }
        this.monto = monto;
    }

    @Override
    public void ejecutar() {
        cuenta.retirar(monto);
    }

    @Override
    public double getMonto() {
        return monto;
    }

    @Override
    public String getTipo() {
        return "RETIRO";
    }
}
