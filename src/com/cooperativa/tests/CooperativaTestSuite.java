package com.cooperativa.tests;

import com.cooperativa.factory.FabricaProductoEstandar;
import com.cooperativa.factory.ProductoFinancieroFactory;
import com.cooperativa.model.Asociado;
import com.cooperativa.model.Credito;
import com.cooperativa.model.Cuenta;
import com.cooperativa.model.enums.EstadoCredito;
import com.cooperativa.services.CooperativaService;
import com.cooperativa.services.TransaccionConcurrenteService;
import com.cooperativa.services.command.ComandoDeposito;
import com.cooperativa.services.command.GestorComandos;
import com.cooperativa.services.observer.NotificacionService;

import java.math.BigDecimal;
import java.util.concurrent.Future;

public final class CooperativaTestSuite {
    public static void main(String[] args) throws Exception {
        pruebaUnitariaObserverYCommand();
        pruebaIntegracionFlujoCredito();
        pruebaConcurrencia();
        System.out.println("OK - pruebas unitarias, integración y concurrencia ejecutadas");
    }

    private static void pruebaUnitariaObserverYCommand() {
        ProductoFinancieroFactory factory = new FabricaProductoEstandar(new BigDecimal("0.02"));
        Cuenta cuenta = factory.crearCuentaAhorro("CA-T1", new BigDecimal("1000"));
        NotificacionService notificador = new NotificacionService();
        cuenta.addObserver(notificador);

        GestorComandos gestor = new GestorComandos();
        gestor.ejecutar(new ComandoDeposito(cuenta, new BigDecimal("100")));
        gestor.deshacer();

        if (notificador.getEventos().size() < 2) {
            throw new AssertionError("Observer no registró eventos esperados");
        }
        if (cuenta.getSaldo().compareTo(new BigDecimal("1000")) != 0) {
            throw new AssertionError("Command undo no restauró saldo");
        }
    }

    private static void pruebaIntegracionFlujoCredito() {
        CooperativaService service = new CooperativaService();
        Asociado asociado = new Asociado("A002", "Carlos Pérez");
        service.registrarAsociado(asociado);

        ProductoFinancieroFactory factory = new FabricaProductoEstandar(new BigDecimal("0.02"));
        Cuenta cuenta = factory.crearCuentaAhorro("CA-T2", new BigDecimal("1200"));
        service.registrarCuenta(asociado.getId(), cuenta);

        Credito credito = factory.crearCreditoConsumo("CR-T1", asociado.getId(), new BigDecimal("5000"));
        credito.aprobar();
        credito.desembolsar();

        if (credito.getEstado() != EstadoCredito.DESEMBOLSADO) {
            throw new AssertionError("Estado de crédito inválido");
        }
    }

    private static void pruebaConcurrencia() throws Exception {
        ProductoFinancieroFactory factory = new FabricaProductoEstandar(new BigDecimal("0.02"));
        Cuenta cuenta = factory.crearCuentaAhorro("CA-T3", new BigDecimal("1000"));

        try (TransaccionConcurrenteService concurrente = new TransaccionConcurrenteService(4)) {
            Future<Void> f1 = concurrente.depositarAsync(cuenta, new BigDecimal("100"), "tx-1");
            Future<Void> f2 = concurrente.depositarAsync(cuenta, new BigDecimal("100"), "tx-1");
            Future<Void> f3 = concurrente.retirarAsync(cuenta, new BigDecimal("50"), "tx-2");
            f1.get();
            f2.get();
            f3.get();
        }

        if (cuenta.getSaldo().compareTo(new BigDecimal("1050")) != 0) {
            throw new AssertionError("Control de concurrencia/idempotencia inválido: " + cuenta.getSaldo());
        }
    }
}
