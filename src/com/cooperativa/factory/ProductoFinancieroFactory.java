package com.cooperativa.factory;

import com.cooperativa.model.Credito;
import com.cooperativa.model.Cuenta;

import java.math.BigDecimal;

public interface ProductoFinancieroFactory {
    Cuenta crearCuentaAhorro(String numero, BigDecimal saldoInicial);
    Credito crearCreditoConsumo(String idCredito, String asociadoId, BigDecimal monto);
}
