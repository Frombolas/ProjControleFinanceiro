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
        if (!arquivo.exists()) {
            arquivo.createNewFile(); // Cria o arquivo apenas se não existir
        }
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(arquivo))) {
            out.writeObject(saldos);
        }
    }

    // Recupera os saldos do arquivo
    public Map<Integer, Saldo> getSaldos() {
        if (!arquivo.exists() || arquivo.length() == 0) {
            return new HashMap<>();
        }

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(arquivo))) {
            return (Map<Integer, Saldo>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Erro ao ler os saldos do arquivo: " + e.getMessage());
            return new HashMap<>();
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
        if (!saldos.containsKey(usuarioId)) {
            return false; // Usuário não tem saldo registrado
        }
        saldos.remove(usuarioId);
        atualizarArquivo(saldos);
        return true;
    }

    // Atualiza o saldo de um usuário
    public boolean atualizarSaldo(int usuarioId, Saldo saldo) throws IOException, ClassNotFoundException {
        Map<Integer, Saldo> saldos = getSaldos();
        if (!saldos.containsKey(usuarioId)) {
            return false; // Não há saldo para atualizar
        }
        saldos.put(usuarioId, saldo);
        atualizarArquivo(saldos);
        return true;
    }

    // Recupera o saldo de um usuário pelo ID
    public Saldo getSaldoPorUsuarioId(int usuarioId) throws IOException, ClassNotFoundException {
        Map<Integer, Saldo> saldos = getSaldos();
        return saldos.getOrDefault(usuarioId, new Saldo(0.0)); // Retorna saldo zerado caso não exista
    }
}