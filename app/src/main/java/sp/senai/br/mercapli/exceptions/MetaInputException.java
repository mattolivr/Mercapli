package sp.senai.br.mercapli.exceptions;

public class MetaInputException extends Exception {

    private String errorMessage;

    public MetaInputException (String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public String toString() {
        return errorMessage;
    }
}
