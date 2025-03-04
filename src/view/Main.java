package view;

import dao.GastoDao;
import dao.SaldoDao;
import model.Gasto;
import model.Saldo;
import model.Usuario;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Set;

public class Main {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        GastoDao gastoDao = new GastoDao();
        SaldoDao saldoDao = new SaldoDao();

        // Cria um usuário com saldo inicial
        Usuario gustavo = new Usuario(1, "Gustavo", new Saldo(500));

        saldoDao.adicionarSaldo(gustavo.getId(), gustavo.getSaldo());

        // Cria alguns gastos
        Gasto gasto1 = new Gasto(1, 110.0f, "Alimentação", LocalDate.now(), gustavo);
        Gasto gasto2 = new Gasto(2, 50.0f, "Transporte", LocalDate.now(), gustavo);
        Gasto gasto3 = new Gasto(3, 40.0f, "Transporte", LocalDate.of(2025, 1, 12), gustavo);

        try {
            // Adiciona os gastos
            gastoDao.adicionarGasto(gasto1);
            gastoDao.adicionarGasto(gasto2);
            gastoDao.adicionarGasto(gasto3);
            saldoDao.atualizarSaldo(gustavo.getId(), gustavo.getSaldo());

            // Atualiza o valor do gasto com id = 2 (de 50.0 para 48.2)
            Gasto gastoAtualizado = new Gasto(2, 48.2f, null, null, null); // Apenas o valor é fornecido
            gastoDao.atualizarGasto(gastoAtualizado);

            // Exibe todos os gastos
            Set<Gasto> gastos = gastoDao.getGastos();
            for (Gasto gasto : gastos) {
                System.out.println(gasto);
            }

            // Exibe o saldo atualizado do usuário
            System.out.println("\nSaldo atualizado do usuário " + gustavo.getNome() + ": " + gustavo.getSaldo());
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}