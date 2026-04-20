package com.cooperativa.services.validation;

import com.cooperativa.model.Asociado;

import java.math.BigDecimal;

public final class ValidacionCreditoService {
    public boolean validarMonto(BigDecimal monto) {
        return monto != null && monto.signum() > 0 && monto.compareTo(new BigDecimal("50000000")) <= 0;
    }

    public boolean validarAsociado(Asociado asociado) {
        return asociado != null && !asociado.getCuentas().isEmpty();
    }
}
