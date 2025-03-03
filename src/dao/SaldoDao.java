package dao;

import model.Saldo;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class SaldoDao {
    private File arquivo;

    public SaldoDao() {
        arquivo = new File("Saldos");
        try {
            arquivo.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException("Erro ao criar o arquivo de saldos.", e);
        }
    }

    // Salva os saldos no arquivo
    private void atualizarArquivo(Map<Integer, Saldo> saldos) throws IOException {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(arquivo))) {
            out.writeObject(saldos);
        }
    }

    // Recupera os saldos do arquivo
    public Map<Integer, Saldo> getSaldos() throws IOException, ClassNotFoundException {
        if (arquivo.length() == 0) {
            return new HashMap<>();
        }

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(arquivo))) {
            return (Map<Integer, Saldo>) in.readObject();
        }
    }

    // Adiciona ou atualiza o saldo de um usuário
    public boolean adicionarSaldo(int usuarioId, Saldo saldo) throws IOException, ClassNotFoundException {
        Map<Integer, Saldo> saldos = getSaldos();
        saldos.put(usuarioId, saldo); // Associa o saldo ao ID do usuário
        atualizarArquivo(saldos);
        return true;
    }

    // Remove o saldo de um usuário
    public boolean deletarSaldo(int usuarioId) throws IOException, ClassNotFoundException {
        Map<Integer, Saldo> saldos = getSaldos();
        if (saldos.remove(usuarioId) != null) { // Remove o saldo pelo ID do usuário
            atualizarArquivo(saldos);
            return true;
        }
        return false;
    }

    // Atualiza o saldo de um usuário
    public boolean atualizarSaldo(int usuarioId, Saldo saldo) throws IOException, ClassNotFoundException {
        Map<Integer, Saldo> saldos = getSaldos();
        if (saldos.containsKey(usuarioId)) { // Verifica se o usuário existe
            saldos.put(usuarioId, saldo); // Atualiza o saldo
            atualizarArquivo(saldos);
            return true;
        }
        return false;
    }

    // Recupera o saldo de um usuário pelo ID
    public Saldo getSaldoPorUsuarioId(int usuarioId) throws IOException, ClassNotFoundException {
        Map<Integer, Saldo> saldos = getSaldos();
        return saldos.get(usuarioId); // Retorna o saldo do usuário ou null se não existir
    }
}