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

        Usuario joao = new Usuario(1,"João", "Comum");
        Usuario maria = new Usuario(2,"Maria", "Comum");

        Gasto gasto1 = new Gasto(1,100.0f, "Alimentação", LocalDate.now(), joao);
        Gasto gasto2 = new Gasto(2,50.0f, "Transporte", LocalDate.now(), joao);
        Gasto gasto3 = new Gasto(3,40.0f, "Transporte", LocalDate.of(2025,01,12), joao);
        Gasto gasto4 = new Gasto(4, 120.0f,"Beleza", LocalDate.of(2025,01,28), maria);



        try {
            gastoDao.adicionarGasto(gasto1);
            gastoDao.adicionarGasto(gasto2);
            gastoDao.atualizarGasto(gasto4);
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