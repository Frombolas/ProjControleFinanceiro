package dao;

import model.Usuario;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class UsuarioDao {
    private File arquivo;

    public UsuarioDao() {
        arquivo = new File("Usuarios"); // Corrigi a extensão do arquivo
        if (!arquivo.exists()) {
            try {
                arquivo.createNewFile();
                atualizarArquivo(new HashMap<>());
            } catch (IOException e) {
                throw new RuntimeException("Erro ao criar o arquivo de usuários.", e);
            }
        }
    }

    private void atualizarArquivo(Map<Integer, Usuario> usuarios) throws IOException {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(arquivo))) {
            out.writeObject(usuarios);
        }
    }

    public Map<Integer, Usuario> getUsuarios() {
        if (!arquivo.exists() || arquivo.length() == 0) {
            return new HashMap<>();
        }
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(arquivo))) {
            return (Map<Integer, Usuario>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return new HashMap<>();
        }
    }

    public boolean adicionarOuAtualizarUsuario(Usuario usuario) throws IOException {
        Map<Integer, Usuario> usuarios = getUsuarios();
        usuarios.put(usuario.getId(), usuario);
        atualizarArquivo(usuarios);
        return true;
    }

    public Usuario getUsuarioPorId(int usuarioId) {
        return getUsuarios().get(usuarioId);
    }

    public boolean deletarUsuario(int usuarioId) throws IOException {
        Map<Integer, Usuario> usuarios = getUsuarios();
        if (usuarios.remove(usuarioId) != null) {
            atualizarArquivo(usuarios);
            return true;
        }
        return false;
    }
}
