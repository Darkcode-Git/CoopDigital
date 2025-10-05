public class ValidacionCuenta {
    public void validarTransaccion(Cuenta cuenta, double monto) {
        try {
            if (monto <= 0) {
                throw new MontoInvalidoException("El monto debe ser positivo");
            }
            if (cuenta.getSaldo() < monto) {
                throw new SaldoInsuficienteException("Saldo insuficiente para realizar la operación");
            }
        } catch (MontoInvalidoException | SaldoInsuficienteException e) {
            Logger.getLogger(ValidacionCuenta.class.getName()).log(Level.WARNING, e.getMessage());
            throw e;
        }
    }
}