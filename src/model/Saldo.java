package model;

public class Saldo {
    private double saldo;

    public Saldo() {
        this.saldo = 0;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public boolean adicionarValor(double valor) {
        if(valor > 0){
            this.saldo += valor;
            return true;
        }
        return false;
    }
    public boolean removerValor(double valor) {
        if(valor > 0){
            this.saldo -= valor;
            return true;
        }
        return false;
    }
}
