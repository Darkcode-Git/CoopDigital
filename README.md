# CoopDigital

Implementación de referencia para una cooperativa digital con arquitectura Java por capas y patrones de diseño OO2.

## Estructura
- `src/com/cooperativa/model`: entidades del dominio.
- `src/com/cooperativa/services`: lógica de negocio y concurrencia.
- `src/com/cooperativa/persistence`: acceso JDBC.
- `src/com/cooperativa/infrastructure`: singleton de DataSource y serialización.
- `src/com/cooperativa/factory`: Abstract Factory de productos financieros.
- `src/com/cooperativa/facade`: Facade de validación crediticia.
- `src/com/cooperativa/services/command`: Command con undo/redo.
- `src/com/cooperativa/services/observer`: Observer para notificaciones.
- `src/com/cooperativa/tests`: pruebas de validación técnica.
- `src/com/cooperativa/documentation/PlanImplementacion.md`: alcance, UML textual y roadmap.

## Ejecución
Compilar:
```bash
javac $(find src -name '*.java')
```

Ejecutar demo:
```bash
java -cp src com.cooperativa.presentation.CooperativaApp
```

Ejecutar pruebas:
```bash
java -cp src com.cooperativa.tests.CooperativaTestSuite
```

## Configuración JDBC
- `coop.jdbc.url` (default: `jdbc:h2:mem:coopdb`)
- `coop.jdbc.user` (default: `sa`)
- `coop.jdbc.password` (default: vacío)

## Configuración de crédito
- `coop.credito.monto.maximo` (default: `50000000`)
