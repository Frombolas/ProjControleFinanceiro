package view;

import dao.GastoDao;
import model.Gasto;
import model.Usuario;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Set;

public class Main {
    public static void main(String[] args) throws IOException, ClassNotFoundException {

        GastoDao gastoDao = new GastoDao();

        Usuario joao = new Usuario("João", "Comum");
        Usuario maria = new Usuario("Maria", "Comum");

        Gasto gasto1 = new Gasto(100.0, "Alimentação", LocalDate.now(), joao);
        Gasto gasto2 = new Gasto(50.0, "Transporte", LocalDate.now(), joao);
        Gasto gasto3 = new Gasto(40.0, "Transporte", LocalDate.of(2025,01,12), joao);
        Gasto gasto4 = new Gasto(120,"Beleza", LocalDate.of(2025,01,28), maria);

        gastoDao.atualizarGasto(gasto4);

        try {
            gastoDao.adicionarGasto(gasto1);
            gastoDao.adicionarGasto(gasto2);
            gastoDao.adicionarGasto(gasto3);
            gastoDao.adicionarGasto(gasto4);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        Set<Gasto> gastos = gastoDao.getGastos();
        try {
            gastos = gastoDao.getGastos();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        for (Gasto gasto : gastos) {
            System.out.println(gasto);
        }
    }
}