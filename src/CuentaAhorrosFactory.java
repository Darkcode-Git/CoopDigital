public interface CuentaFactory {
    Cuenta crearCuenta(String numero, double saldoInicial);
}

public class CuentaAhorrosFactory implements CuentaFactory {
    @Override
    public Cuenta crearCuenta(String numero, double saldoInicial) {
        return new CuentaAhorros(numero, saldoInicial);
    }
}