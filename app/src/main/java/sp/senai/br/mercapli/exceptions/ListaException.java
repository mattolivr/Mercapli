package sp.senai.br.mercapli.exceptions;

public class ListaException extends Exception {

    private String error;

    public ListaException(String error){
        this.error = error;
    }

    @Override
    public String toString() {
        return error;
    }
}
