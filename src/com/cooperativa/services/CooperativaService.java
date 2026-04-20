package com.cooperativa.services;

import com.cooperativa.model.Asociado;
import com.cooperativa.model.Credito;
import com.cooperativa.model.Cuenta;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public final class CooperativaService {
    private final Map<String, Asociado> asociados = new ConcurrentHashMap<>();
    private final Map<String, Cuenta> cuentas = new ConcurrentHashMap<>();
    private final Map<String, Credito> creditos = new ConcurrentHashMap<>();

    public void registrarAsociado(Asociado asociado) {
        asociados.putIfAbsent(asociado.getId(), asociado);
    }

    public void registrarCuenta(String asociadoId, Cuenta cuenta) {
        Asociado asociado = asociados.get(asociadoId);
        if (asociado == null) {
            throw new IllegalArgumentException("Asociado no encontrado");
        }
        asociado.agregarCuenta(cuenta);
        cuentas.putIfAbsent(cuenta.getNumero(), cuenta);
    }

    public void registrarCredito(Credito credito) {
        creditos.putIfAbsent(credito.getId(), credito);
    }

    public Optional<Asociado> buscarAsociado(String id) {
        return Optional.ofNullable(asociados.get(id));
    }

    public Optional<Cuenta> buscarCuenta(String numero) {
        return Optional.ofNullable(cuentas.get(numero));
    }

    public Optional<Credito> buscarCredito(String id) {
        return Optional.ofNullable(creditos.get(id));
    }

    public Collection<Asociado> listarAsociados() {
        return asociados.values();
    }
}
