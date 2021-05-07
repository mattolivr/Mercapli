package sp.senai.br.mercapli.classes;

public class Meta {

    private Double valor;

    public Meta () {
        this.valor = 0.0;
    }

    public Meta(Double valor) {
        this.valor = valor;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Double getValorRestante(Double gastoTotal) {
        return this.valor - gastoTotal;
    }
}
