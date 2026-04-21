package modelo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import transacciones.Transaccion;

public final class Cooperativa {
    private final String nombre;
    private final List<Socio> socios;
    private final List<Transaccion> historialTransacciones;

    public Cooperativa(String nombre) {
        this.nombre = Objects.requireNonNull(nombre, "El nombre de la cooperativa no puede ser nulo.");
        this.socios = new ArrayList<>();
        this.historialTransacciones = new ArrayList<>();
    }

    public void registrarSocio(Socio socio) {
        Objects.requireNonNull(socio, "El socio no puede ser nulo.");
        boolean existe = socios.stream().anyMatch(s -> s.getCedula().equals(socio.getCedula()));
        if (existe) {
            throw new IllegalArgumentException("Ya existe un socio registrado con cédula: " + socio.getCedula());
        }
        socios.add(socio);
    }

    public void registrarCuenta(Socio socio, Cuenta cuenta) {
        Objects.requireNonNull(socio, "El socio no puede ser nulo.");
        Objects.requireNonNull(cuenta, "La cuenta no puede ser nula.");
        if (buscarCuenta(cuenta.getNumeroCuenta()).isPresent()) {
            throw new IllegalArgumentException("Ya existe una cuenta con número: " + cuenta.getNumeroCuenta());
        }
        socio.abrirCuenta(cuenta);
    }

    public Optional<Cuenta> buscarCuenta(String numeroCuenta) {
        return socios.stream()
                .flatMap(socio -> socio.getCuentas().stream())
                .filter(c -> c.getNumeroCuenta().equals(numeroCuenta))
                .findFirst();
    }

    public void ejecutarTransaccion(Transaccion transaccion) {
        Objects.requireNonNull(transaccion, "La transacción no puede ser nula.");
        transaccion.ejecutar();
        historialTransacciones.add(transaccion);
    }

    public List<String> listarNombresSocios() {
        return socios.stream().map(Socio::getNombre).toList();
    }

    public List<Cuenta> filtrarCuentasConSaldoMayorA(double minimo) {
        return socios.stream()
                .flatMap(socio -> socio.getCuentas().stream())
                .filter(cuenta -> cuenta.getSaldo() > minimo)
                .toList();
    }

    public double totalDineroCooperativa() {
        return socios.stream()
                .flatMap(socio -> socio.getCuentas().stream())
                .map(Cuenta::getSaldo)
                .reduce(0.0, Double::sum);
    }

    public List<Socio> getSocios() {
        return Collections.unmodifiableList(socios);
    }

    public List<Transaccion> getHistorialTransacciones() {
        return Collections.unmodifiableList(historialTransacciones);
    }

    public String getNombre() {
        return nombre;
    }
}
