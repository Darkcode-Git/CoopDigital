package app;

import modelo.Cooperativa;
import modelo.CooperativaManager;
import modelo.CuentaAhorros;
import modelo.Socio;
import transacciones.Transaccion;
import transacciones.TransaccionFactory;

public final class CooperativaApp {
    private static final double SALDO_FILTRO = 500_000;

    private CooperativaApp() {
    }

    public static void main(String[] args) {
        Cooperativa cooperativa = CooperativaManager.getInstancia().getCooperativa();

        Socio ana = new Socio("Ana Gómez", "1001");
        Socio luis = new Socio("Luis Pérez", "1002");
        Socio marta = new Socio("Marta Díaz", "1003");

        cooperativa.registrarSocio(ana);
        cooperativa.registrarSocio(luis);
        cooperativa.registrarSocio(marta);

        CuentaAhorros c1 = new CuentaAhorros("AH-1001", 600_000, 0.02);
        CuentaAhorros c2 = new CuentaAhorros("AH-1002", 400_000, 0.03);
        CuentaAhorros c3 = new CuentaAhorros("AH-1003", 1_200_000, 0.015);

        cooperativa.registrarCuenta(ana, c1);
        cooperativa.registrarCuenta(luis, c2);
        cooperativa.registrarCuenta(marta, c3);

        cooperativa.ejecutarTransaccion(TransaccionFactory.crearDeposito(c2, 250_000));
        cooperativa.ejecutarTransaccion(TransaccionFactory.crearRetiro(c1, 100_000));

        try {
            cooperativa.ejecutarTransaccion(TransaccionFactory.crearRetiro(c2, 2_000_000));
        } catch (IllegalArgumentException e) {
            System.out.println("Error controlado en retiro: " + e.getMessage());
        }

        try {
            cooperativa.registrarCuenta(ana, new CuentaAhorros("AH-1001", 50_000, 0.01));
        } catch (IllegalArgumentException e) {
            System.out.println("Validación de cuenta duplicada: " + e.getMessage());
        }

        cooperativa.getSocios().stream()
                .flatMap(s -> s.getCuentas().stream())
                .filter(CuentaAhorros.class::isInstance)
                .map(CuentaAhorros.class::cast)
                .forEach(CuentaAhorros::aplicarInteres);

        System.out.println("=== Nombres de socios (map + forEach) ===");
        cooperativa.listarNombresSocios().forEach(System.out::println);

        System.out.println("\n=== Cuentas con saldo mayor a 500000 (filter) ===");
        cooperativa.filtrarCuentasConSaldoMayorA(SALDO_FILTRO)
                .forEach(c -> System.out.printf("%s -> %.2f%n", c.getNumeroCuenta(), c.getSaldo()));

        System.out.println("\n=== Total de dinero en la cooperativa (reduce) ===");
        System.out.printf("%.2f%n", cooperativa.totalDineroCooperativa());

        System.out.println("\n=== Historial de transacciones ===");
        cooperativa.getHistorialTransacciones().stream()
                .map(Transaccion::getTipo)
                .forEach(System.out::println);

        System.out.println("""
                
                Patrones aplicados:
                1) Singleton: CooperativaManager garantiza una única instancia compartida.
                2) Factory Method: TransaccionFactory encapsula la creación de depósitos y retiros.
                3) Command: Transaccion + Deposito/Retiro encapsulan operaciones ejecutables.
                """);
    }
}
