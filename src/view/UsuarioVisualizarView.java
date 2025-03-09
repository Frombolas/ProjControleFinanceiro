package view;

import dao.UsuarioDao;
import model.Usuario;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Map;

public class UsuarioVisualizarView extends JFrame {
    private JTable tabelaUsuarios;
    private DefaultTableModel modeloTabela;
    private UsuarioDao usuarioDao;

    public UsuarioVisualizarView() {
        usuarioDao = new UsuarioDao();
        setTitle("Visualização de Usuários");
        setSize(600, 400);

        ImageIcon icon = new ImageIcon("src/img/money.png");
        setIconImage(icon.getImage());

        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(new Color(245, 245, 245));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Criando o modelo da tabela
        modeloTabela = new DefaultTableModel(new Object[]{"ID", "Nome", "Saldo"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;  // Impede edição direta na tabela
            }
        };

        // Criando a tabela de usuários
        tabelaUsuarios = new JTable(modeloTabela);
        tabelaUsuarios.getTableHeader().setBackground(new Color(70, 130, 180));
        tabelaUsuarios.getTableHeader().setForeground(Color.WHITE);
        tabelaUsuarios.setRowHeight(25);
        tabelaUsuarios.setFont(new Font("SansSerif", Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(tabelaUsuarios);
        add(scrollPane, BorderLayout.CENTER);

        // Botão para fechar a tela
        JPanel panelBotoes = new JPanel(new FlowLayout());
        panelBotoes.setBackground(new Color(245, 245, 245));
        JButton btnFechar = new JButton("Fechar");
        btnFechar.setBackground(new Color(70, 130, 180));
        btnFechar.setForeground(Color.WHITE);
        btnFechar.addActionListener(e -> dispose());
        panelBotoes.add(btnFechar);
        add(panelBotoes, BorderLayout.SOUTH);

        atualizarTabela();
    }

    // Atualiza a tabela com os usuários
    private void atualizarTabela() {
        modeloTabela.setRowCount(0);  // Limpa a tabela antes de adicionar os dados atualizados
        Map<Integer, Usuario> usuarios = usuarioDao.getUsuarios();  // Obtém os usuários do DAO
        for (Usuario usuario : usuarios.values()) {
            modeloTabela.addRow(new Object[]{usuario.getId(), usuario.getNome(), usuario.getSaldo().getSaldo()}); // Altere aqui para getSaldo()
        }
    }
}
