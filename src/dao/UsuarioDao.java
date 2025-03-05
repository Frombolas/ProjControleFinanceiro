package dao;

import model.Usuario;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class UsuarioDao {
    private File arquivo;

    public UsuarioDao() {
        arquivo = new File("Usuarios");

        if (!arquivo.exists()) {
            try {
                arquivo.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException("Erro ao criar o arquivo de usuários.", e);
            }
        }
    }

    // Salva os usuários no arquivo
    private void atualizarArquivo(Map<Integer, Usuario> usuarios) throws IOException {
        if (!arquivo.exists()) {
            arquivo.createNewFile(); // Cria o arquivo somente se necessário
        }
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(arquivo))) {
            out.writeObject(usuarios);
        }
    }
    // Recupera os usuários do arquivo
    public Map<Integer, Usuario> getUsuarios() {
        if (!arquivo.exists() || arquivo.length() == 0) {
            return new HashMap<>();
        }

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(arquivo))) {
            return (Map<Integer, Usuario>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Erro ao ler os usuários do arquivo: " + e.getMessage());
            return new HashMap<>();
        }
    }

    // Adiciona ou atualiza um usuário
    public boolean adicionarOuAtualizarUsuario(Usuario usuario) throws IOException, ClassNotFoundException {
        Map<Integer, Usuario> usuarios = getUsuarios();
        boolean atualizado = usuarios.containsKey(usuario.getId()); // Verifica se o usuário já existia
        usuarios.put(usuario.getId(), usuario);
        atualizarArquivo(usuarios);
        return atualizado;
    }

    // Recupera um usuário pelo ID
    public Usuario getUsuarioPorId(int usuarioId) throws IOException, ClassNotFoundException {
        Map<Integer, Usuario> usuarios = getUsuarios();
        return usuarios.get(usuarioId);
    }

    // Remove um usuário pelo ID
    public boolean deletarUsuario(int usuarioId) throws IOException, ClassNotFoundException {
        Map<Integer, Usuario> usuarios = getUsuarios();
        if (!usuarios.containsKey(usuarioId)) {
            return false; // O usuário não existe
        }
        usuarios.remove(usuarioId);
        atualizarArquivo(usuarios);
        return true;
    }
}
