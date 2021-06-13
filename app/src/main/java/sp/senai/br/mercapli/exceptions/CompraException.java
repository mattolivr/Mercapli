package sp.senai.br.mercapli.exceptions;


public class CompraException extends Exception {

    private String error;

    public CompraException (String error) {
        this.error = error;
    }

    @Override
    public String toString() {
        return error;
    }
}
