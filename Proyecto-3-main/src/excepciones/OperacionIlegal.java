package excepciones;

public class OperacionIlegal extends Exception {
    public OperacionIlegal(String mensaje) {
        super(mensaje);
    }
}
