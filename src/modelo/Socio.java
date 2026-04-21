package modelo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public final class Socio {
    private final String nombre;
    private final String cedula;
    private final List<Cuenta> cuentas;

    public Socio(String nombre, String cedula) {
        this.nombre = validarTexto(nombre, "nombre");
        this.cedula = validarTexto(cedula, "cédula");
        this.cuentas = new ArrayList<>();
    }

    public void abrirCuenta(Cuenta cuenta) {
        Objects.requireNonNull(cuenta, "La cuenta no puede ser nula.");
        boolean existe = cuentas.stream()
                .anyMatch(c -> c.getNumeroCuenta().equals(cuenta.getNumeroCuenta()));
        if (existe) {
            throw new IllegalArgumentException("El socio ya tiene una cuenta con número: " + cuenta.getNumeroCuenta());
        }
        cuentas.add(cuenta);
    }

    public String getNombre() {
        return nombre;
    }

    public String getCedula() {
        return cedula;
    }

    public List<Cuenta> getCuentas() {
        return Collections.unmodifiableList(cuentas);
    }

    private static String validarTexto(String valor, String campo) {
        String limpio = Objects.requireNonNull(valor, "El campo " + campo + " no puede ser nulo.");
        if (limpio.isBlank()) {
            throw new IllegalArgumentException("El campo " + campo + " no puede estar vacío.");
        }
        return limpio;
    }
}
