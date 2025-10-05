public class CooperativaManager {
    private static CooperativaManager instancia;
    private List<Socio> socios;
    private List<Cuenta> cuentas;
    
    private CooperativaManager() {
        socios = new ArrayList<>();
        cuentas = new ArrayList<>();
    }
    
    public static CooperativaManager getInstancia() {
        if (instancia == null) {
            synchronized(CooperativaManager.class) {
                if (instancia == null) {
                    instancia = new CooperativaManager();
                }
            }
        }
        return instancia;
    }
}