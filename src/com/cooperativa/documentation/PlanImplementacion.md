# Cooperativa Digital - Diseño y Plan de Implementación

## 1) Alcance funcional y reglas de negocio
- Gestión de asociados: registro, consulta y vínculo con productos.
- Aportes sociales: depósito/retiro sobre cuentas de ahorro.
- Créditos: solicitud, aprobación/rechazo, desembolso y pago.
- Notificaciones: eventos de cambio de saldo en tiempo real.

Reglas clave:
- No se permiten montos nulos ni no positivos.
- No se permiten cuentas duplicadas por asociado.
- Un crédito solo puede transitar por estados válidos.
- Operaciones concurrentes por cuenta se serializan con lock.
- Se aplica idempotencia por token de transacción.

## 2) UML
### Casos de uso (texto)
Actores:
- Asociado
- Analista de crédito
- Sistema de notificaciones

Casos:
- Realizar Aporte
- Retirar Aporte
- Solicitar Crédito
- Aprobar/Rechazar Crédito
- Desembolsar Crédito
- Consultar Estado de Crédito
- Recibir Notificación de Saldo

### Diagrama de clases (resumen)
- `Asociado` 1..* `Cuenta`
- `Cuenta` (abstracta) <- `CuentaAhorro`
- `Credito` con `EstadoCredito`
- `CooperativaService` coordina agregados
- `ValidacionCreditoFacade` simplifica validaciones
- `ProductoFinancieroFactory` / `FabricaProductoEstandar`
- `ComandoTransaccion` / `ComandoDeposito` / `ComandoRetiro` / `GestorComandos`
- `SaldoObserver` / `NotificacionService`

### Secuencia (resumen: Solicitar crédito)
1. Asociado envía solicitud.
2. `CooperativaService` crea crédito vía factory.
3. `ValidacionCreditoFacade` valida asociado+monto.
4. Se aprueba/rechaza y se persiste por repositorio JDBC.

### Estado de crédito
`PENDIENTE -> APROBADO -> DESEMBOLSADO -> PAGADO`
`PENDIENTE -> RECHAZADO`

## 3) Arquitectura por capas y paquetes
- `com.cooperativa.model`: entidades de dominio.
- `com.cooperativa.services`: casos de uso y concurrencia.
- `com.cooperativa.persistence`: repositorios JDBC.
- `com.cooperativa.infrastructure`: datasource singleton y serialización.
- `com.cooperativa.facade`: simplificación de subsistemas.
- `com.cooperativa.factory`: familias de productos financieros.
- `com.cooperativa.presentation`: punto de entrada.
- `com.cooperativa.tests`: validación técnica.

## 4) Patrones aplicados
- Singleton: `DataSourceSingleton`.
- Abstract Factory: `ProductoFinancieroFactory` + `FabricaProductoEstandar`.
- Facade: `ValidacionCreditoFacade`.
- Observer: `SaldoObserver` + `NotificacionService`.
- Command: comandos de depósito/retiro con deshacer/rehacer.

## 5) Robustez y errores
- Excepciones de dominio: `CooperativaException`, `SaldoInsuficienteException`, `MontoInvalidoException`, `PersistenciaException`.
- Validaciones defensivas en constructores y operaciones.
- Reglas de propagación: dominio -> servicio -> presentación.
  - Dominio: lanza excepciones de negocio específicas.
  - Servicio: captura excepciones técnicas y las envuelve en excepciones de aplicación cuando aplique.
  - Presentación: registra el error y traduce la respuesta a mensaje funcional para el usuario.

## 6) Concurrencia y consistencia
- `ExecutorService` en `TransaccionConcurrenteService`.
- `ReentrantLock` por número de cuenta.
- `tokensProcesados` para idempotencia.

## 7) Persistencia
Esquema inicial:
- `asociado(id PK, nombre)`
- `credito(id PK, asociado_id FK, monto, estado)`

Integración:
- JDBC + `DataSource` gestionado por singleton.
- Propiedades de configuración soportadas: `coop.jdbc.url`, `coop.jdbc.user`, `coop.jdbc.password`.

## 8) Serialización y sesión
- Entidades principales serializables (`Asociado`, `Cuenta`, `Credito`).
- `serialVersionUID` explícito para compatibilidad.
- `SessionSerializer` para persistencia ligera de sesión.

## 9) Validación técnica
- Unitarias: observer + command.
- Integración: flujo asociado/cuenta/crédito.
- Concurrencia: idempotencia + lock por cuenta.

## 10) Roadmap incremental
1. Dominio + excepciones.
2. Patrones y servicios base.
3. Persistencia JDBC.
4. Concurrencia e idempotencia.
5. Observabilidad/notificaciones.
6. Pruebas y endurecimiento final.
