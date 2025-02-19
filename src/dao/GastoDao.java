package dao;

import model.Gasto;

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

    public boolean atualizarGasto(Gasto gasto) throws IOException, ClassNotFoundException {
        Set<Gasto> gastos = getGastos();
        if (gastos.contains(gasto)) {
            if(gastos.remove(gasto) && gastos.add(gasto)) {
                atualizarArquivo(gastos);
                return true;
            }
        }
        return false;
    }
}