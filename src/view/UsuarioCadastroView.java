package view;

import dao.UsuarioDao;
import model.Usuario;
import model.Saldo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;

public class UsuarioCadastroView extends JFrame {
    private JTextField txtId;
    private JTextField txtNome;
    private JTextField txtSaldo;
    private JButton btnSalvar;
    private JButton btnExcluir;
    private UsuarioDao usuarioDao;

    public UsuarioCadastroView() {
        usuarioDao = new UsuarioDao();

        setTitle("Cadastro de Usuário");
        setSize(400, 300);

        ImageIcon icon = new ImageIcon("src/img/money.png");
        setIconImage(icon.getImage());

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new FlowLayout());

        txtId = new JTextField(20);
        txtNome = new JTextField(20);
        txtSaldo = new JTextField(20);
        btnSalvar = new JButton("Salvar");
        btnExcluir = new JButton("Excluir");

        add(new JLabel("ID:"));
        add(txtId);
        add(new JLabel("Nome:"));
        add(txtNome);
        add(new JLabel("Saldo Inicial:"));
        add(txtSaldo);
        add(btnSalvar);
        add(btnExcluir);

        btnSalvar.addActionListener(this::salvarUsuario);
        btnExcluir.addActionListener(this::excluirUsuario);
    }

    private void salvarUsuario(ActionEvent e) {
        try {
            int id = Integer.parseInt(txtId.getText());
            String nome = txtNome.getText();
            double saldoInicial = Double.parseDouble(txtSaldo.getText());

            Usuario usuario = new Usuario(id, nome, new Saldo(saldoInicial));
            boolean sucesso = usuarioDao.adicionarOuAtualizarUsuario(usuario);

            JOptionPane.showMessageDialog(this, sucesso ? "Usuário salvo com sucesso!" : "Erro ao salvar usuário.");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Por favor, insira valores válidos.", "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar usuário: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void excluirUsuario(ActionEvent e) {
        try {
            int id = Integer.parseInt(txtId.getText());
            boolean sucesso = usuarioDao.deletarUsuario(id);

            JOptionPane.showMessageDialog(this, sucesso ? "Usuário excluído com sucesso!" : "Usuário não encontrado.");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "ID inválido.", "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao excluir usuário: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
