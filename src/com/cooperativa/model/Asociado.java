package com.cooperativa.model;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public final class Asociado implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private final String id;
    private final String nombre;
    private final List<Cuenta> cuentas;

    public Asociado(String id, String nombre) {
        this.id = validarTexto(id, "id");
        this.nombre = validarTexto(nombre, "nombre");
        this.cuentas = new ArrayList<>();
    }

    public void agregarCuenta(Cuenta cuenta) {
        Objects.requireNonNull(cuenta, "La cuenta no puede ser nula");
        boolean duplicada = cuentas.stream().anyMatch(c -> c.getNumero().equals(cuenta.getNumero()));
        if (duplicada) {
            throw new IllegalArgumentException("La cuenta ya existe para el asociado");
        }
        cuentas.add(cuenta);
    }

    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public List<Cuenta> getCuentas() {
        return Collections.unmodifiableList(cuentas);
    }

    private static String validarTexto(String valor, String nombreCampo) {
        Objects.requireNonNull(valor, "El campo " + nombreCampo + " no puede ser nulo");
        if (valor.isBlank()) {
            throw new IllegalArgumentException("El campo " + nombreCampo + " no puede estar vacío");
        }
        return valor;
    }
}
