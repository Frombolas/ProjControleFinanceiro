package view;

import dao.GastoDao;
import dao.UsuarioDao;
import model.Gasto;
import model.Usuario;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Set;

public class GastoCadastroView extends JFrame {
    private GastoDao gastoDao;
    private UsuarioDao usuarioDao;
    private JTextField txtValor, txtCategoria, txtUsuarioId;
    private JSpinner spnData;
    private JTextField txtExcluirId;
    private JButton btnAdicionar, btnExcluir, btnVisualizar;
    private int proximoId = 1;

    public GastoCadastroView() {
        gastoDao = new GastoDao();
        usuarioDao = new UsuarioDao();
        setTitle("Cadastro de Gastos");

        ImageIcon icon = new ImageIcon("src/img/money.png");
        setIconImage(icon.getImage());

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(450, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(new Color(245, 245, 245));

        JPanel panelFormulario = new JPanel(new GridLayout(4, 2, 10, 10));
        panelFormulario.setBackground(new Color(245, 245, 245));
        panelFormulario.setBorder(new EmptyBorder(10, 10, 10, 10));

        panelFormulario.add(new JLabel("Valor:"));
        txtValor = new JTextField();
        panelFormulario.add(txtValor);

        panelFormulario.add(new JLabel("Categoria:"));
        txtCategoria = new JTextField();
        panelFormulario.add(txtCategoria);

        panelFormulario.add(new JLabel("Data:"));
        spnData = new JSpinner(new SpinnerDateModel());
        panelFormulario.add(spnData);

        panelFormulario.add(new JLabel("ID Usuário:"));
        txtUsuarioId = new JTextField();
        panelFormulario.add(txtUsuarioId);

        add(panelFormulario, BorderLayout.NORTH);

        JPanel panelSul = new JPanel();
        panelSul.setLayout(new BoxLayout(panelSul, BoxLayout.Y_AXIS));
        panelSul.setBackground(new Color(245, 245, 245));

        JPanel panelExclusao = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelExclusao.setBackground(new Color(245, 245, 245));
        panelExclusao.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
        panelExclusao.add(new JLabel("ID para exclusão:"));
        txtExcluirId = new JTextField(8);
        panelExclusao.add(txtExcluirId);
        panelSul.add(panelExclusao);

        JPanel panelBotoes = new JPanel(new FlowLayout());
        panelBotoes.setBackground(new Color(245, 245, 245));
        btnAdicionar = new JButton("Adicionar Gasto");
        btnAdicionar.setBackground(new Color(70, 130, 180));
        btnAdicionar.setForeground(Color.WHITE);
        btnExcluir = new JButton("Excluir Gasto");
        btnExcluir.setBackground(new Color(220, 20, 60));
        btnExcluir.setForeground(Color.WHITE);
        btnVisualizar = new JButton("Visualizar Gastos");
        btnVisualizar.setBackground(new Color(34, 139, 34));
        btnVisualizar.setForeground(Color.WHITE);

        panelBotoes.add(btnAdicionar);
        panelBotoes.add(btnExcluir);
        panelBotoes.add(btnVisualizar);
        panelSul.add(panelBotoes);

        add(panelSul, BorderLayout.SOUTH);

        btnAdicionar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                adicionarGasto();
            }
        });

        btnExcluir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                excluirGasto();
            }
        });

        btnVisualizar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new GastoTabelaView().setVisible(true);
            }
        });

        atualizarProximoId();
    }

    private void atualizarProximoId() {
        Set<Gasto> gastos = gastoDao.getGastos();
        proximoId = gastos.stream().mapToInt(Gasto::getId).max().orElse(0) + 1;
    }

    private void adicionarGasto() {
        try {
            double valor = Double.parseDouble(txtValor.getText());
            String categoria = txtCategoria.getText();
            int usuarioId = Integer.parseInt(txtUsuarioId.getText());
            LocalDate data = ((SpinnerDateModel) spnData.getModel()).getDate()
                    .toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();

            Usuario usuario = usuarioDao.getUsuarioPorId(usuarioId);
            if (usuario == null) {
                JOptionPane.showMessageDialog(this, "Usuário não encontrado.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Gasto gasto = new Gasto(proximoId, valor, categoria, data, usuario);

            if (gastoDao.adicionarGasto(gasto)) {
                JOptionPane.showMessageDialog(this, "Gasto adicionado com sucesso.");
                atualizarProximoId();
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao adicionar o gasto.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Valor ou ID do usuário inválido.", "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (IOException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(this, "Erro ao acessar os dados: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    private void excluirGasto() {
        try {
            int id = Integer.parseInt(txtExcluirId.getText());
            Set<Gasto> gastos = gastoDao.getGastos();

            Gasto gastoParaExcluir = gastos.stream()
                    .filter(g -> g.getId() == id)
                    .findFirst()
                    .orElse(null);

            if (gastoParaExcluir != null) {
                if (gastoDao.deletarGasto(gastoParaExcluir)) {
                    JOptionPane.showMessageDialog(this, "Gasto excluído com sucesso.");
                } else {
                    JOptionPane.showMessageDialog(this, "Erro ao excluir o gasto.", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Gasto com o ID informado não foi encontrado.", "Aviso", JOptionPane.WARNING_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "ID inválido.", "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (IOException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(this, "Erro ao acessar os dados: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
