package com.cooperativa.facade;

import com.cooperativa.model.Asociado;
import com.cooperativa.services.validation.ValidacionCreditoService;

import java.math.BigDecimal;

public final class ValidacionCreditoFacade {
    private final ValidacionCreditoService servicio;

    public ValidacionCreditoFacade() {
        this.servicio = new ValidacionCreditoService();
    }

    public boolean esSolicitudValida(Asociado asociado, BigDecimal monto) {
        return servicio.validarAsociado(asociado) && servicio.validarMonto(monto);
    }
}
