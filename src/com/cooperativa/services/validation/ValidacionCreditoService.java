package com.cooperativa.services.validation;

import com.cooperativa.model.Asociado;

import java.math.BigDecimal;

public final class ValidacionCreditoService {
    private static final BigDecimal MONTO_MAXIMO_CREDITO = new BigDecimal("50000000");

    public boolean validarMonto(BigDecimal monto) {
        return monto != null && monto.signum() > 0 && monto.compareTo(MONTO_MAXIMO_CREDITO) <= 0;
    }

    public boolean validarAsociado(Asociado asociado) {
        return asociado != null && !asociado.getCuentas().isEmpty();
    }
}
