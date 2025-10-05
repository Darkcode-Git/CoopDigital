// Clase base Usuario
public abstract class Usuario {
    private String nombre;
    private String identificacion;
    
    public Usuario(String nombre, String identificacion) {
        this.nombre = nombre;
        this.identificacion = identificacion;
    }
    
    // Getters y setters
}

// Subclase Socio que hereda de Usuario
public class Socio extends Usuario {
    private List<Cuenta> cuentas;
    
    public Socio(String nombre, String identificacion) {
        super(nombre, identificacion);
        this.cuentas = new ArrayList<>();
    }
    
    public void agregarCuenta(Cuenta cuenta) {
        cuentas.add(cuenta);
    }
}

// Clase Cuenta
public abstract class Cuenta {
    private String numero;
    private double saldo;
    
    public Cuenta(String numero, double saldoInicial) {
        this.numero = numero;
        this.saldo = saldoInicial;
    }
    
    public abstract void calcularIntereses();
}