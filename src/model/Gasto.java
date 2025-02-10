package model;

import java.time.LocalDate;

public class Gasto {

    private double valor;
    private String categoria;
    private LocalDate data;
    private Usuario usuario;

    public Gasto(double valor, String categoria, LocalDate data, Usuario usuario) {
        this.valor = valor;
        this.categoria = categoria;
        this.data = data;
        this.usuario = usuario;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }


    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
