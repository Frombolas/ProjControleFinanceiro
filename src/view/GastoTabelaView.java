package view;

import dao.GastoDao;
import model.Gasto;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Set;

public class GastoTabelaView extends JFrame {
    private JTable tabelaGastos;
    private DefaultTableModel modeloTabela;
    private GastoDao gastoDao;

    public GastoTabelaView() {
        gastoDao = new GastoDao();
        setTitle("Visualização de Gastos");

        ImageIcon icon = new ImageIcon("src/img/money.png");
        setIconImage(icon.getImage());

        setSize(600, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(new Color(245, 245, 245));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        modeloTabela = new DefaultTableModel(new Object[]{"ID", "Valor", "Categoria", "Data", "Usuário"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;  // Impede edição direta na tabela
            }
        };

        tabelaGastos = new JTable(modeloTabela);
        tabelaGastos.getTableHeader().setBackground(new Color(70, 130, 180));
        tabelaGastos.getTableHeader().setForeground(Color.WHITE);
        tabelaGastos.setRowHeight(25);
        tabelaGastos.setFont(new Font("SansSerif", Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(tabelaGastos);
        scrollPane.setBorder(new EmptyBorder(10, 10, 10, 10));
        add(scrollPane, BorderLayout.CENTER);

        // Botão para fechar a tela de visualização
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

    private void atualizarTabela() {
        modeloTabela.setRowCount(0);
        Set<Gasto> gastos = gastoDao.getGastos();
        for (Gasto gasto : gastos) {
            String usuarioNome = (gasto.getUsuario() != null) ? gasto.getUsuario().getNome() : "N/A";
            modeloTabela.addRow(new Object[]{gasto.getId(), gasto.getValor(), gasto.getCategoria(), gasto.getData(), usuarioNome});
        }
    }
}
