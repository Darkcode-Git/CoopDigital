package com.cooperativa.model;

import com.cooperativa.model.enums.EstadoCredito;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;

public final class Credito implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private final String id;
    private final String asociadoId;
    private final BigDecimal monto;
    private EstadoCredito estado;
    private final Instant fechaCreacion;

    public Credito(String id, String asociadoId, BigDecimal monto) {
        this.id = validar(id, "id");
        this.asociadoId = validar(asociadoId, "asociadoId");
        this.monto = validarMonto(monto);
        this.estado = EstadoCredito.PENDIENTE;
        this.fechaCreacion = Instant.now();
    }

    public void aprobar() {
        if (estado != EstadoCredito.PENDIENTE) {
            throw new IllegalStateException("Solo se puede aprobar un crédito pendiente");
        }
        estado = EstadoCredito.APROBADO;
    }

    public void rechazar() {
        if (estado != EstadoCredito.PENDIENTE) {
            throw new IllegalStateException("Solo se puede rechazar un crédito pendiente");
        }
        estado = EstadoCredito.RECHAZADO;
    }

    public void desembolsar() {
        if (estado != EstadoCredito.APROBADO) {
            throw new IllegalStateException("Solo se puede desembolsar un crédito aprobado");
        }
        estado = EstadoCredito.DESEMBOLSADO;
    }

    public void pagar() {
        if (estado != EstadoCredito.DESEMBOLSADO) {
            throw new IllegalStateException("Solo se puede pagar un crédito desembolsado");
        }
        estado = EstadoCredito.PAGADO;
    }

    public String getId() {
        return id;
    }

    public String getAsociadoId() {
        return asociadoId;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public EstadoCredito getEstado() {
        return estado;
    }

    public Instant getFechaCreacion() {
        return fechaCreacion;
    }

    private static String validar(String valor, String campo) {
        Objects.requireNonNull(valor, "El campo " + campo + " no puede ser nulo");
        if (valor.isBlank()) {
            throw new IllegalArgumentException("El campo " + campo + " no puede estar vacío");
        }
        return valor;
    }

    private static BigDecimal validarMonto(BigDecimal monto) {
        Objects.requireNonNull(monto, "El monto no puede ser nulo");
        if (monto.signum() <= 0) {
            throw new IllegalArgumentException("El monto debe ser mayor a cero");
        }
        return monto;
    }
}
