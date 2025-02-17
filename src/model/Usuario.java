package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Usuario implements Serializable {

    private int id;
    private static final long serialVersionUID = 1L;
    private String nome;
    private String tipo;
    private transient List<Gasto> gastos;

    public Usuario(int id,String nome, String tipo) {
        this.id = id;
        this.nome = nome;
        this.tipo = tipo;
        this.gastos = new ArrayList<>();
    }

    public void adicionarGasto(Gasto gasto) {
        gastos.add(gasto);
    }

    public String getNome() {
        return nome;
    }

    public String getTipo() {
        return tipo;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", tipo='" + tipo + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario = (Usuario) o;
        return id == usuario.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}