package view;

import dao.GastoDao;
import dao.SaldoDao;
import dao.UsuarioDao;
import model.Gasto;
import model.Saldo;
import model.Usuario;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Set;

public class Main {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        // Inicializa os DAOs
        GastoDao gastoDao = new GastoDao();
        SaldoDao saldoDao = new SaldoDao();
        UsuarioDao usuarioDao = new UsuarioDao();

        // Cria um usuário com saldo inicial
        Usuario gustavo = new Usuario(1, "Gustavo", new Saldo(500));

        try {
            // Adiciona o usuário ao UsuarioDao
            usuarioDao.adicionarOuAtualizarUsuario(gustavo);

            // Adiciona o saldo do usuário ao SaldoDao
            saldoDao.adicionarSaldo(gustavo.getId(), gustavo.getSaldo());

            // Cria alguns gastos
            Gasto gasto1 = new Gasto(1, 110.0f, "Alimentação", LocalDate.now(), gustavo);
            Gasto gasto2 = new Gasto(2, 50.0f, "Transporte", LocalDate.now(), gustavo);
            Gasto gasto3 = new Gasto(3, 40.0f, "Transporte", LocalDate.of(2025, 1, 12), gustavo);

            // Adiciona os gastos
            gastoDao.adicionarGasto(gasto1);
            gastoDao.adicionarGasto(gasto2);
            gastoDao.adicionarGasto(gasto3);

            // Recupera e exibe todos os gastos
            Set<Gasto> gastos = gastoDao.getGastos();
            System.out.println("Gastos registrados:");
            for (Gasto gasto : gastos) {
                System.out.println(gasto);
            }

            // Recupera o usuário e exibe seu saldo
            Usuario usuarioRecuperado = usuarioDao.getUsuarioPorId(gustavo.getId());
            System.out.println("\nSaldo atualizado do usuário " + usuarioRecuperado.getNome() + ": " + usuarioRecuperado.getSaldo());

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
    }
}
}