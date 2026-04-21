package transacciones;

public interface Transaccion {
    void ejecutar();
    double getMonto();
    String getTipo();
}
