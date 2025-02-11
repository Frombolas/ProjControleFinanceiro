package model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

public class Gasto implements Serializable {
    private static final long serialVersionUID = 1L;
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

    @Override
    public String toString() {
        return "Gasto{" +
                "valor=" + valor +
                ", categoria='" + categoria + '\'' +
                ", data=" + data +
                ", usuario=" + usuario.getNome() + // Apenas o nome do usuário
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Gasto gasto = (Gasto) o;
        return Double.compare(valor, gasto.valor) == 0 && Objects.equals(categoria, gasto.categoria) && Objects.equals(data, gasto.data) && Objects.equals(usuario, gasto.usuario);
    }

    @Override
    public int hashCode() {
        return Objects.hash(valor, categoria, data, usuario);
    }
}