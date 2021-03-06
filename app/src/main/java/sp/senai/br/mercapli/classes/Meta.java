package sp.senai.br.mercapli.classes;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.fragment.app.DialogFragment;

import sp.senai.br.mercapli.exceptions.MetaException;
import sp.senai.br.mercapli.exceptions.MetaInputException;

import static sp.senai.br.mercapli.GlobalVariables.GASTO_LOCAL;
import static sp.senai.br.mercapli.GlobalVariables.GASTO_TOTAL;
import static sp.senai.br.mercapli.GlobalVariables.META_GASTOS;

public class Meta {

    private Double valor;
    private Double gastoTotal;
    private long dataCriacao;
    private long dataExclusao;

    public Meta () {
        this.valor = 0.0;
        this.dataCriacao = System.currentTimeMillis();
        this.dataExclusao = 0;
        this.gastoTotal = 0.0;
    }

    public Meta(Double valor, Double gastoTotal, long dataCriacao, long dataExclusao){
        this.valor = valor;
        this.gastoTotal = gastoTotal;
        this.dataCriacao = dataCriacao;
        this.dataExclusao = dataExclusao;
    }

    public Meta(Double valor) throws MetaInputException {
        if(valor < 0)
            throw new MetaInputException("O valor não pode ser negativo!");
        else if (valor < 300)
            throw new MetaInputException("O valor deve ser igual ou acima de 300!");
        else if (valor > 999999)
            throw new MetaInputException("Valor máximo excedido!\n Por favor, insira um valor menor que 1.000.000");
        else
            this.valor = valor;

        this.dataCriacao = System.currentTimeMillis();
        this.dataExclusao = 0;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Double getGastoTotal(){
        return gastoTotal;
    }

    public void setGastoTotal(Double gastoTotal){
        this.gastoTotal = gastoTotal;
    }

    public long getDataCriacao(){
        return dataCriacao;
    }

    private void setDataCriacao(long dataCriacao){
        this.dataCriacao = dataCriacao;
    }

    public long getDataExclusao() {
        return dataExclusao;
    }

    public void setDataExclusao(long dataExclusao) {
        this.dataExclusao = dataExclusao;
    }

    public String getStatus(){
        Double valorTotal = GASTO_TOTAL + GASTO_LOCAL;

        if(valorTotal > 0){
            if(valorTotal > (META_GASTOS.getValor() - META_GASTOS.getValor() * 0.3) && valorTotal < META_GASTOS.getValor()){
                return "Você está quase excedendo sua meta de gastos!";
            } else if (valorTotal >= META_GASTOS.getValor()) {
                return "Você excedeu sua meta de gastos!";
            } else {
                return "";
            }
        } else
            return "";
    }

    public int getValorRestantePorcentagem(){
        int value = (int) Math.round(((GASTO_TOTAL + GASTO_LOCAL) * 100) / META_GASTOS.getValor());
        System.out.println("Progresso: " + value);
        return (value < 100)? value: 100;
    }

    public void salvarMeta(SQLiteDatabase database){
        final Cursor metasIguais;

        ContentValues insertMeta = new ContentValues();

        insertMeta.put("meta_valor", this.getValor());
        insertMeta.put("meta_cria", this.getDataCriacao());
        insertMeta.put("meta_excl", this.getDataExclusao());
        insertMeta.put("meta_gasto", GASTO_TOTAL);

        metasIguais = database.query("meta", null, "meta_cria = " + this.getDataCriacao(), null, null, null, null);

        if(metasIguais.getCount() == 0)
            database.insertOrThrow("meta", null, insertMeta);
        else
            database.update("meta", insertMeta, "meta_cria = " + this.getDataCriacao(), null);
    }

    public void atualizarMeta(SQLiteDatabase database) throws MetaException {
        final Cursor cursorMetas;

        cursorMetas = database.query("meta", null, null, null, null, null, null);

        if(cursorMetas.getCount() > 0){
            cursorMetas.moveToLast();
            if(cursorMetas.getLong(cursorMetas.getColumnIndexOrThrow("meta_excl")) == 0){
                this.setValor(cursorMetas.getDouble(cursorMetas.getColumnIndexOrThrow("meta_valor")));
                this.setDataCriacao(cursorMetas.getLong(cursorMetas.getColumnIndexOrThrow("meta_cria")));
                this.setDataExclusao(cursorMetas.getLong(cursorMetas.getColumnIndexOrThrow("meta_excl")));
            } else {
                throw new MetaException("A Meta de Gastos foi fechada");
            }
        } else {
            throw new MetaException("Não foi encontrada nenhuma meta de gastos");
        }
    }

    public void finalizarMetaAnterior(SQLiteDatabase database) {
        final Cursor cursorMetas;

        ContentValues metaValues = new ContentValues();

        cursorMetas = database.query("meta", null, null, null, null, null, null);

        if(cursorMetas.getCount() > 0){
            cursorMetas.moveToLast();
            cursorMetas.moveToPrevious();
            if(!cursorMetas.isBeforeFirst()){
                metaValues.put("meta_excl", System.currentTimeMillis());
                database.update("meta", metaValues, "meta_cria = " + cursorMetas.getLong(cursorMetas.getColumnIndexOrThrow("meta_cria")), null);
            }
        }
    }
}
