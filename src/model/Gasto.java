package model;

import java.time.LocalDate;
import java.util.Objects;

public class Gasto {

    private final long serialVersionUID;
    private double valor;
    private String categoria;
    private LocalDate data;
    private Usuario usuario;

    public Gasto(long serialVersionUID, double valor, String categoria, LocalDate data, Usuario usuario) {
        this.serialVersionUID = 1L;
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

    @Override
    public String toString() {
        return "Gasto{" +
                "serialVersionUID=" + serialVersionUID +
                ", valor=" + valor +
                ", categoria='" + categoria + '\'' +
                ", data=" + data +
                ", usuario=" + usuario +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Gasto gasto = (Gasto) o;
        return serialVersionUID == gasto.serialVersionUID;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(serialVersionUID);
    }
}
