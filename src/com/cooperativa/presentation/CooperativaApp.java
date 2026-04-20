package com.cooperativa.presentation;

import com.cooperativa.facade.ValidacionCreditoFacade;
import com.cooperativa.factory.FabricaProductoEstandar;
import com.cooperativa.factory.ProductoFinancieroFactory;
import com.cooperativa.model.Asociado;
import com.cooperativa.model.Credito;
import com.cooperativa.model.Cuenta;
import com.cooperativa.services.CooperativaService;
import com.cooperativa.services.command.ComandoDeposito;
import com.cooperativa.services.command.ComandoRetiro;
import com.cooperativa.services.command.GestorComandos;
import com.cooperativa.services.observer.NotificacionService;

import java.math.BigDecimal;

public final class CooperativaApp {
    public static void main(String[] args) {
        CooperativaService servicio = new CooperativaService();
        ProductoFinancieroFactory factory = new FabricaProductoEstandar(new BigDecimal("0.01"));
        ValidacionCreditoFacade validacionFacade = new ValidacionCreditoFacade();
        GestorComandos comandos = new GestorComandos();

        Asociado asociado = new Asociado("A001", "Ana Gómez");
        servicio.registrarAsociado(asociado);

        Cuenta cuenta = factory.crearCuentaAhorro("CA-1001", new BigDecimal("1000000"));
        NotificacionService notificador = new NotificacionService();
        cuenta.addObserver(notificador);
        servicio.registrarCuenta(asociado.getId(), cuenta);

        comandos.ejecutar(new ComandoDeposito(cuenta, new BigDecimal("50000")));
        comandos.ejecutar(new ComandoRetiro(cuenta, new BigDecimal("10000")));
        comandos.deshacer();
        comandos.rehacer();

        Credito credito = factory.crearCreditoConsumo("CR-001", asociado.getId(), new BigDecimal("2000000"));
        if (validacionFacade.esSolicitudValida(asociado, credito.getMonto())) {
            credito.aprobar();
            servicio.registrarCredito(credito);
        }

        System.out.println("Saldo actual: " + cuenta.getSaldo());
        System.out.println("Estado crédito: " + credito.getEstado());
        System.out.println("Eventos notificación: " + notificador.getEventos());
    }
}
