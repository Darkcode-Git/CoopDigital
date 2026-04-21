package modelo;

public final class CooperativaManager {
    private static volatile CooperativaManager instancia;
    private final Cooperativa cooperativa;

    private CooperativaManager() {
        this.cooperativa = new Cooperativa("CoopRKC");
    }

    public static CooperativaManager getInstancia() {
        if (instancia == null) {
            synchronized (CooperativaManager.class) {
                if (instancia == null) {
                    instancia = new CooperativaManager();
                }
            }
        }
        return instancia;
    }

    public Cooperativa getCooperativa() {
        return cooperativa;
    }
}
