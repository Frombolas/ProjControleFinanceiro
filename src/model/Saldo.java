package model;

import java.io.Serializable;

public class Saldo implements Serializable {
    private double saldo;
    private static final long serialVersionUID = 1L;

    public Saldo(double saldo) {
        if (saldo < 0) {
            throw new IllegalArgumentException("O saldo não pode ser negativo!");
        }
        this.saldo = saldo;
    }

    public double getSaldo() {
        return saldo;
    }

    public boolean adicionarValor(double valor) {
        if (valor > 0) {
            this.saldo += valor;
            return true;
        }
        return false;
    }

    public boolean removerValor(double valor) {
        if (valor > 0 && valor <= this.saldo) {
            this.saldo -= valor;
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return String.format("Saldo: R$ %.2f", saldo); // Formata o saldo como moeda
    }

}