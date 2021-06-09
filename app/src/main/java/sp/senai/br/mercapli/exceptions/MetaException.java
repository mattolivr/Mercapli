package sp.senai.br.mercapli.exceptions;

public class MetaException extends Exception {

    private String errorMessage;

    public MetaException(String errorMessage) { this.errorMessage = errorMessage;}

    @Override
    public String toString() {
        return errorMessage;
    }
}
