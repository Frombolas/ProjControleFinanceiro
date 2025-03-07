package view;

import dao.GastoDao;
import dao.SaldoDao;
import dao.UsuarioDao;
import model.Gasto;
import model.Saldo;
import model.Usuario;

import java.io.IOException;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        try {
            // Criando um saldo para o usuário
            Saldo saldo = new Saldo(500.0); // R$ 500,00 de saldo inicial

            // Criando um usuário com saldo
            Usuario usuario = new Usuario(1, "João Silva", saldo);

            // Salvando usuário no DAO
            UsuarioDao usuarioDao = new UsuarioDao();
            usuarioDao.adicionarOuAtualizarUsuario(usuario);

            // Salvando saldo no DAO
            SaldoDao saldoDao = new SaldoDao();
            saldoDao.adicionarSaldo(usuario.getId(), saldo);

            // Criando um GastoDao
            GastoDao gastoDao = new GastoDao();

            // Adicionando três gastos
            Gasto gasto1 = new Gasto(1, 50.0f, "Alimentação", LocalDate.now(), usuario);
            Gasto gasto2 = new Gasto(2, 100.0f, "Transporte", LocalDate.now(), usuario);
            Gasto gasto3 = new Gasto(3, 80.0f, "Lazer", LocalDate.now(), usuario);

            gastoDao.adicionarGasto(gasto1);
            gastoDao.adicionarGasto(gasto2);
            gastoDao.adicionarGasto(gasto3);

            // Atualizar o saldo no DAO após os gastos
            saldoDao.atualizarSaldo(usuario.getId(), usuario.getSaldo());

            // Exibir saldo atualizado
            Saldo saldoAtualizado = saldoDao.getSaldoPorUsuarioId(usuario.getId());
            usuarioDao.adicionarOuAtualizarUsuario(new Usuario(1,"Gustavo", saldoAtualizado));
            System.out.println(usuarioDao.getUsuarioPorId(1));

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
