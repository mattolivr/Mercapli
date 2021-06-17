package sp.senai.br.mercapli.exceptions;

public class ItemException extends Exception {

    String error;

    public ItemException (String error) {
        this.error = error;
    }

    @Override
    public String toString() {
        return error;
    }
}
