package com.cooperativa.services;

import com.cooperativa.model.Cuenta;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public final class TransaccionConcurrenteService implements AutoCloseable {
    private final ExecutorService executor;
    private final Map<String, Lock> locksPorCuenta;
    private final Set<String> tokensProcesados;

    public TransaccionConcurrenteService(int hilos) {
        this.executor = Executors.newFixedThreadPool(hilos);
        this.locksPorCuenta = new ConcurrentHashMap<>();
        this.tokensProcesados = ConcurrentHashMap.newKeySet();
    }

    public Future<Void> depositarAsync(Cuenta cuenta, BigDecimal monto, String idempotencyToken) {
        return ejecutarOperacion(cuenta, idempotencyToken, () -> cuenta.depositar(monto));
    }

    public Future<Void> retirarAsync(Cuenta cuenta, BigDecimal monto, String idempotencyToken) {
        return ejecutarOperacion(cuenta, idempotencyToken, () -> cuenta.retirar(monto));
    }

    private Future<Void> ejecutarOperacion(Cuenta cuenta, String token, Runnable operacion) {
        return executor.submit(() -> {
            Lock lock = locksPorCuenta.computeIfAbsent(cuenta.getNumero(), key -> new ReentrantLock());
            lock.lock();
            try {
                // Si el token ya fue procesado, la operación se omite para garantizar idempotencia.
                if (!tokensProcesados.add(token)) {
                    return null;
                }
                operacion.run();
                return null;
            } finally {
                lock.unlock();
            }
        });
    }

    @Override
    public void close() {
        executor.shutdown();
    }
}
