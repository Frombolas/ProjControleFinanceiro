package model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

public class Gasto implements Serializable {

    private static final long serialVersionUID = 1L;
    private int id;
    private Float valor;
    private String categoria;
    private LocalDate data;
    private Usuario usuario;

    public Gasto(int id,Float valor, String categoria, LocalDate data, Usuario usuario) {
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

    public Float getValor() {
        return valor;
    }

    public void setValor(Float valor) {
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
        return "Gasto{" +
                "id=" + id +
                "valor=" + valor +
                ", categoria='" + categoria + '\'' +
                ", data=" + data +
                ", usuario=" + usuario.getNome() + // Apenas o nome do usuário
                ", saldo=" + usuario.getSaldo() +
                '}';
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