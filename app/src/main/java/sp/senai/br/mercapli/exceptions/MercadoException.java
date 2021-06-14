package sp.senai.br.mercapli.exceptions;


public class MercadoException extends Exception {

    private String error;

    public MercadoException(String error) {
        this.error = error;
    }

    @Override
    public String toString() {
        return error;
    }
}
