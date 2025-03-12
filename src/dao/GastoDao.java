package dao;

import model.Gasto;
import model.Saldo;
import model.Usuario;

import java.io.*;
import java.util.HashSet;
import java.util.Set;

public class GastoDao {
    private File arquivo;

    public GastoDao() {
        arquivo = new File("Gastos");

        if (!arquivo.exists()) {
            try {
                arquivo.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException("Erro ao criar o arquivo de gastos.", e);
            }
        }
    }

    private void atualizarArquivo(Set<Gasto> gastos) throws IOException, ClassNotFoundException{
        try (ObjectOutputStream out = new ObjectOutputStream(
                new FileOutputStream(arquivo))) {
            out.writeObject(gastos);
        }
    }

    public Set<Gasto> getGastos() {
        if (!arquivo.exists() || arquivo.length() == 0) {
            return new HashSet<>();
        }

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(arquivo))) {
            return (Set<Gasto>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Erro ao ler os gastos do arquivo: " + e.getMessage());
            return new HashSet<>();
        }
    }



    public boolean adicionarGasto(Gasto gasto) throws IOException, ClassNotFoundException {
        Set<Gasto> gastos = getGastos();

        if (gastos.add(gasto)) {
            // Atualiza o saldo do usuário
            Usuario usuario = gasto.getUsuario();
            if (usuario != null) {
                Saldo saldo = usuario.getSaldo();
                if (saldo.removerValor(gasto.getValor())) { // Subtrai o valor apenas se possível
                    SaldoDao saldoDao = new SaldoDao();
                    saldoDao.atualizarSaldo(usuario.getId(), saldo); // Persiste a alteração no saldo
                } else {
                    System.err.println("Saldo insuficiente para registrar o gasto.");
                    return false;
                }
            }

            atualizarArquivo(gastos);
            return true;
        }
        return false;
    }

    public boolean deletarGasto(Gasto gasto) throws IOException, ClassNotFoundException {
        Set<Gasto> gastos = getGastos();

        if (gastos.remove(gasto)) {
            Usuario usuario = gasto.getUsuario();
            if (usuario != null) {
                Saldo saldo = usuario.getSaldo();
                saldo.adicionarValor(gasto.getValor()); // Reembolsa o valor removido
                SaldoDao saldoDao = new SaldoDao();
                saldoDao.atualizarSaldo(usuario.getId(), saldo);
            }

            atualizarArquivo(gastos);
            return true;
        }
        return false;
    }

    public boolean atualizarGasto(Gasto gastoAtualizado) throws IOException, ClassNotFoundException {
        Set<Gasto> gastos = getGastos();

        for (Gasto gasto : gastos) {
            if (gasto.getId() == gastoAtualizado.getId()) {
                Usuario usuario = gasto.getUsuario();
                if (usuario != null) {
                    Saldo saldo = usuario.getSaldo();
                    double diferenca = gastoAtualizado.getValor() - gasto.getValor();

                    if (diferenca > 0) {
                        if (!saldo.removerValor(diferenca)) {
                            System.err.println("Saldo insuficiente para aumentar o valor do gasto.");
                            return false;
                        }
                    } else {
                        saldo.adicionarValor(-diferenca); // Se o gasto for reduzido, devolve a diferença
                    }

                    SaldoDao saldoDao = new SaldoDao();
                    saldoDao.atualizarSaldo(usuario.getId(), saldo);
                }

                // Atualiza os atributos do gasto
                gasto.setValor(gastoAtualizado.getValor());
                gasto.setCategoria(gastoAtualizado.getCategoria() != null ? gastoAtualizado.getCategoria() : gasto.getCategoria());
                gasto.setData(gastoAtualizado.getData() != null ? gastoAtualizado.getData() : gasto.getData());
                gasto.setUsuario(gastoAtualizado.getUsuario() != null ? gastoAtualizado.getUsuario() : gasto.getUsuario());

                atualizarArquivo(gastos);
                return true;
            }
        }
        return false;
    }
}