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

    public Set<Gasto> getGastos() throws IOException, ClassNotFoundException {
        if (arquivo.length() == 0) {
            return new HashSet<>();
        }

        try (ObjectInputStream in = new ObjectInputStream(
                new FileInputStream(arquivo))) {
            return (Set<Gasto>) in.readObject();
        }
    }



    public boolean adicionarGasto(Gasto gasto) throws IOException, ClassNotFoundException {
        Set<Gasto> gastos = getGastos();
        if (gastos.add(gasto)) {
            // Atualiza o saldo do usuário
            Usuario usuario = gasto.getUsuario();
            Saldo saldo = usuario.getSaldo();
            saldo.removerValor(gasto.getValor()); // Subtrai o valor do gasto do saldo

            atualizarArquivo(gastos);
            return true;
        }
        return false;
    }

    public boolean deletarGasto(Gasto gasto) throws IOException, ClassNotFoundException {
        Set<Gasto> gastos = getGastos();
        if (gastos.remove(gasto)) {
            atualizarArquivo(gastos);
            return true;
        }
        return false;
    }

    public boolean atualizarGasto(Gasto gastoAtualizado) throws IOException, ClassNotFoundException {
        Set<Gasto> gastos = getGastos();
        for (Gasto gasto : gastos) {
            if (gasto.getId() == gastoAtualizado.getId()) {
                // Verifica se o valor foi alterado
                if (gastoAtualizado.getValor() != 0 && gastoAtualizado.getValor() != gasto.getValor()) {
                    // Calcula a diferença entre o novo valor e o valor antigo
                    float diferenca = gastoAtualizado.getValor() - gasto.getValor();

                    // Atualiza o saldo do usuário
                    Usuario usuario = gasto.getUsuario();
                    Saldo saldo = usuario.getSaldo();
                    saldo.removerValor(diferenca); // Subtrai a diferença do saldo
                }

                // Atualiza os campos não nulos ou não zero
                if (gastoAtualizado.getValor() != 0) {
                    gasto.setValor(gastoAtualizado.getValor());
                }
                if (gastoAtualizado.getCategoria() != null) {
                    gasto.setCategoria(gastoAtualizado.getCategoria());
                }
                if (gastoAtualizado.getData() != null) {
                    gasto.setData(gastoAtualizado.getData());
                }
                if (gastoAtualizado.getUsuario() != null) {
                    gasto.setUsuario(gastoAtualizado.getUsuario());
                }

                // Salva as alterações no arquivo
                atualizarArquivo(gastos);
                return true;
            }
        }
        return false; // Gasto não encontrado
    }
}