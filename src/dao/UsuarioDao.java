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
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(arquivo))) {
            out.writeObject(usuarios);
        }
    }

    // Recupera os usuários do arquivo
    public Map<Integer, Usuario> getUsuarios() throws IOException, ClassNotFoundException {
        if (arquivo.length() == 0) {
            return new HashMap<>();
        }

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(arquivo))) {
            return (Map<Integer, Usuario>) in.readObject();
        }
    }

    // Adiciona ou atualiza um usuário
    public boolean adicionarOuAtualizarUsuario(Usuario usuario) throws IOException, ClassNotFoundException {
        Map<Integer, Usuario> usuarios = getUsuarios();
        usuarios.put(usuario.getId(), usuario); // Usa o ID do usuário como chave
        atualizarArquivo(usuarios);
        return true;
    }

    // Recupera um usuário pelo ID
    public Usuario getUsuarioPorId(int usuarioId) throws IOException, ClassNotFoundException {
        Map<Integer, Usuario> usuarios = getUsuarios();
        return usuarios.get(usuarioId);
    }

    // Remove um usuário pelo ID
    public boolean deletarUsuario(int usuarioId) throws IOException, ClassNotFoundException {
        Map<Integer, Usuario> usuarios = getUsuarios();
        if (usuarios.remove(usuarioId) != null) { // Remove o usuário pelo ID
            atualizarArquivo(usuarios);
            return true;
        }
        return false;
    }
}
