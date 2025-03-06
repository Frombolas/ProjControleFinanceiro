package view;

import dao.GastoDao;
import model.Gasto;
import model.Saldo;
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
    // Campos do formulário para adicionar gasto
    private JTextField txtValor, txtCategoria, txtUsuario;
    private JSpinner spnData;
    // Campo para exclusão
    private JTextField txtExcluirId;

    // Botões
    private JButton btnAdicionar, btnExcluir, btnVisualizar;
    private int proximoId = 1;

    public GastoCadastroView() {
        gastoDao = new GastoDao();
        setTitle("Cadastro de Gastos");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(450, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(new Color(245, 245, 245));

        // Painel de formulário para adicionar gasto (Valor, Categoria, Data, Usuário)
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

        panelFormulario.add(new JLabel("Usuário:"));
        txtUsuario = new JTextField();
        panelFormulario.add(txtUsuario);

        add(panelFormulario, BorderLayout.NORTH);

        // Painel Sul composto por dois subpainéis: campo para exclusão e botões
        JPanel panelSul = new JPanel();
        panelSul.setLayout(new BoxLayout(panelSul, BoxLayout.Y_AXIS));
        panelSul.setBackground(new Color(245, 245, 245));

        // Painel para o campo de exclusão
        JPanel panelExclusao = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelExclusao.setBackground(new Color(245, 245, 245));
        panelExclusao.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
        panelExclusao.add(new JLabel("ID para exclusão:"));
        txtExcluirId = new JTextField(8);
        panelExclusao.add(txtExcluirId);
        panelSul.add(panelExclusao);

        // Painel para os botões
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

        // Ações
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
            String nomeUsuario = txtUsuario.getText();
            LocalDate data = ((SpinnerDateModel) spnData.getModel()).getDate()
                    .toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();

            // Validações
            if (valor < 0) {
                JOptionPane.showMessageDialog(this, "O valor não pode ser negativo.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (categoria.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "A categoria não pode estar vazia.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (nomeUsuario.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "O nome do usuário não pode estar vazio.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Cria o usuário com saldo padrão (ex: 1000)
            Usuario usuario = new Usuario(1, nomeUsuario, new Saldo(1000));
            Gasto gasto = new Gasto(proximoId, valor, categoria, data, usuario);

            if (gastoDao.adicionarGasto(gasto)) {
                JOptionPane.showMessageDialog(this, "Gasto adicionado com sucesso.");
                atualizarProximoId();
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao adicionar o gasto.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Valor inválido.", "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (IOException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(this, "Erro ao acessar os dados: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void excluirGasto() {
        try {
            int id = Integer.parseInt(txtExcluirId.getText());
            Set<Gasto> gastos = gastoDao.getGastos();
            Gasto gastoParaExcluir = gastos.stream().filter(g -> g.getId() == id).findFirst().orElse(null);
            if (gastoParaExcluir != null) {
                if (gastoDao.deletarGasto(gastoParaExcluir)) {
                    JOptionPane.showMessageDialog(this, "Gasto excluído com sucesso.");
                    atualizarProximoId();
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GastoCadastroView().setVisible(true));
    }
}
