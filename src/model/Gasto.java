package model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

public class Gasto implements Serializable {

    private static final long serialVersionUID = 1L;
    private int id;
    private double valor;
    private String categoria;
    private LocalDate data;
    private Usuario usuario;

    public Gasto(int id, double valor, String categoria, LocalDate data, Usuario usuario) {
        if (categoria == null || categoria.trim().isEmpty()) {
            throw new IllegalArgumentException("A categoria não pode ser nula ou vazia!");
        }
        if (data == null) {
            throw new IllegalArgumentException("A data não pode ser nula!");
        }

        this.id = id;
        this.valor = valor;
        this.categoria = categoria;
        this.data = data;
        this.usuario = usuario;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        if (valor < 0) {
            throw new IllegalArgumentException("O valor do gasto não pode ser negativo!");
        }
        this.valor = valor;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public String toString() {
        return String.format("Gasto{id=%d, " +
                        "valor=R$ %.2f, " +
                        "categoria='%s', " +
                        "data=%s, " +
                        "usuario='%s'}",
                id, valor, categoria, data, usuario.getNome());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Gasto gasto = (Gasto) o;
        return id == gasto.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}