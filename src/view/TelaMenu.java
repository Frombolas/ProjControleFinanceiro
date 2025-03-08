package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TelaMenu extends JFrame {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton Usuario;
    private JButton Gastos;
    private JButton visualizarGraficoButton;
    private JButton visualizarUsuariosButton; // Novo botão
    private JLabel simbolo;

    public TelaMenu() {
        setTitle("Debt Control");
        setSize(600, 600);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        contentPane = new JPanel(); // Inicializa o painel manualmente
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout());

        ImageIcon icon = new ImageIcon("src/img/money.png");
        setIconImage(icon.getImage());

        // Adicionando o título centralizado
        JLabel titulo = new JLabel("Debt Control", SwingConstants.CENTER);
        titulo.setFont(new Font("Montserrat Bold", Font.BOLD, 24));
        titulo.setBorder(BorderFactory.createEmptyBorder(50, 0, 20, 0)); // Ajusta o espaçamento
        contentPane.add(titulo, BorderLayout.NORTH);

        // Painel para os botões
        JPanel panelBotoes = new JPanel();
        panelBotoes.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 10)); // Alinhando botões na horizontal

        Usuario = new JButton("Usuário");
        panelBotoes.add(Usuario);

        Gastos = new JButton("Gastos");
        panelBotoes.add(Gastos);

        visualizarGraficoButton = new JButton("Visualizar Gráfico");
        panelBotoes.add(visualizarGraficoButton);

        visualizarUsuariosButton = new JButton("Visualizar Usuários");
        panelBotoes.add(visualizarUsuariosButton);

        contentPane.add(panelBotoes, BorderLayout.SOUTH);
        panelBotoes.setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 0)); // Ajuste para subir um pouco os botões

        // Ação do botão de Usuário - abre a tela de criação de usuário
        Usuario.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UsuarioCadastroView cadastroView = new UsuarioCadastroView();
                cadastroView.setVisible(true);
            }
        });

        // Ação do botão de Gastos - abre a tela de cadastro de gastos
        Gastos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GastoCadastroView gastoCadastro = new GastoCadastroView();
                gastoCadastro.pack();
                gastoCadastro.setLocationRelativeTo(null);
                gastoCadastro.setVisible(true);
            }
        });

        // Ação do botão de Gráficos - abre a tela de gráficos de gastos
        visualizarGraficoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GastoGraficoView gastoGrafico = new GastoGraficoView();
                gastoGrafico.pack();
                gastoGrafico.setLocationRelativeTo(null);
                gastoGrafico.setVisible(true);
            }
        });

        // Ação do botão de Visualizar Usuários
        visualizarUsuariosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UsuarioVisualizarView visualizarView = new UsuarioVisualizarView(new dao.UsuarioDao());
                visualizarView.setVisible(true);
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TelaMenu menu = new TelaMenu();
            menu.setVisible(true);
        });
    }
}
