package dao;

import model.Gasto;

import java.io.*;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CancellationException;

public class GastoDao {
    private File arquivo;

    public GastoDao() {
        arquivo = new File("Gastos");

        if(!arquivo.exists()) {
            try {
                arquivo.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }


    private void atualizarArquivo(Set<Gasto> gasto) throws IOException {
        try (ObjectOutputStream out = new ObjectOutputStream(
                new FileOutputStream(arquivo))) {
            out.writeObject(gasto);
        }
    }

    public Set<Gasto> getGastos() throws IOException, ClassNotFoundException {
        if(arquivo.length()== 0){
            return new HashSet<>();
        }

        try(ObjectInputStream in = new ObjectInputStream(
                new FileInputStream(arquivo))) {
            return (Set<Gasto>) in.readObject();
        }
    }

    public boolean adicionarGasto(Gasto gasto) throws IOException,ClassNotFoundException {
        Set<Gasto> gastos = getGastos();
        if(gastos.add(gasto)){
            atualizarArquivo(gastos);
            return true;
        }
        return false;
    }


}
