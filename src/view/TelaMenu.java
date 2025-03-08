package view;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TelaMenu extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton Usuario;
    private JButton Gastos;
    private JButton visualizarGraficoButton;
    private JButton usuariosButton;
    private JButton visualizarUsuariosButton;

    public TelaMenu() {
        setContentPane(contentPane);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getRootPane().setDefaultButton(buttonOK);
        setTitle("Debt control");

        ImageIcon icon = new ImageIcon("src/img/money.png");
        setIconImage(icon.getImage());

        setSize(600,600);
        setResizable(false);
        setLocationRelativeTo(null);


        Usuario.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        Gastos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GastoCadastroView gastocadastro = new GastoCadastroView();
                dispose();
                gastocadastro.pack();
                gastocadastro.setLocationRelativeTo(null);

                gastocadastro.setVisible(true);
                setVisible(false);
            }
        });

        visualizarGraficoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GastoGraficoView gastografico = new GastoGraficoView();
                dispose();
                gastografico.pack();
                gastografico.setLocationRelativeTo(null);

                gastografico.setVisible(true);
                setVisible(false);
            }
        });

        usuariosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UsuarioCadastroView usuarioCadastro = new UsuarioCadastroView();
                dispose();
                usuarioCadastro.pack();
                usuarioCadastro.setLocationRelativeTo(null);

                usuarioCadastro.setVisible(true);
                setVisible(false);
            }
        });
        visualizarUsuariosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UsuarioVisualizarView usuarioVisualizar = new UsuarioVisualizarView();
                dispose();
                usuarioVisualizar.pack();
                usuarioVisualizar.setLocationRelativeTo(null);

                usuarioVisualizar.setVisible(true);
                setVisible(false);
            }
        });
    }

    public static void main(String[] args) {
        TelaMenu dialog = new TelaMenu();
        dialog.setVisible(true);
    }
    
}