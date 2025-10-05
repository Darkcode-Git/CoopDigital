public class OperacionesCuenta {
    public double calcularSaldoTotal(List<Cuenta> cuentas) {
        return cuentas.stream()
                     .mapToDouble(Cuenta::getSaldo)
                     .sum();
    }
    
    public List<Cuenta> filtrarCuentasConSaldoMayor(List<Cuenta> cuentas, double montoMinimo) {
        return cuentas.stream()
                     .filter(cuenta -> cuenta.getSaldo() > montoMinimo)
                     .collect(Collectors.toList());
    }
}