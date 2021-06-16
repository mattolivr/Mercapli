package sp.senai.br.mercapli.exceptions;

public class UsernameException extends Exception {

    private String error;

    public UsernameException (String error) {
        this.error = error;
    }

    public String toString() {
        return error;
    }
}
