package com.cooperativa.services.command;

public interface ComandoTransaccion {
    void ejecutar();
    void deshacer();
    String descripcion();
}
