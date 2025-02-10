package model;

import java.time.LocalDate;
import java.util.Arrays;

public class Usuario {

    private String nome;
    private String tipo;
    private Gasto[] gastos;

    public Usuario(String nome, String tipo) {
        this.nome = nome;
        this.tipo = tipo;
        gastos = new Gasto[1];
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setGastos(Gasto[] gastos) {
        this.gastos = gastos;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "nome='" + nome + '\'' +
                ", tipo='" + tipo + '\'' +
                ", gastos=" + Arrays.toString(gastos) +
                '}';
    }
}
