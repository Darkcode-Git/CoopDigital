package com.cooperativa.factory;

import com.cooperativa.model.Credito;
import com.cooperativa.model.Cuenta;
import com.cooperativa.model.CuentaAhorro;

import java.math.BigDecimal;

public final class FabricaProductoEstandar implements ProductoFinancieroFactory {
    private final BigDecimal tasaAhorro;

    public FabricaProductoEstandar(BigDecimal tasaAhorro) {
        this.tasaAhorro = tasaAhorro;
    }

    @Override
    public Cuenta crearCuentaAhorro(String numero, BigDecimal saldoInicial) {
        return new CuentaAhorro(numero, saldoInicial, tasaAhorro);
    }

    @Override
    public Credito crearCreditoConsumo(String idCredito, String asociadoId, BigDecimal monto) {
        return new Credito(idCredito, asociadoId, monto);
    }
}
