package view;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TelaMenu extends JDialog {
    private JPanel contentPane;
    private JButton Gastos;
    private JButton visualizarGraficoButton;
    private JButton usuariosButton;
    private JButton visualizarUsuariosButton;

    public TelaMenu() {
        setContentPane(contentPane);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setTitle("Debt control");

        ImageIcon icon = new ImageIcon("src/img/money.png");
        setIconImage(icon.getImage());

        setSize(700,400);
        setResizable(false);
        setLocationRelativeTo(null);

        Gastos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GastoCadastroView gastoCadastro = new GastoCadastroView();
                dispose();
                gastoCadastro.setLocationRelativeTo(null); //Centraliza a janela na tela.

                gastoCadastro.setVisible(true);//Exibe a nova tela
                setVisible(false); //Para a janela atual fechar

                // Adiciona um listener para quando a nova janela for fechada, o menu reaparecer
                gastoCadastro.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                        setVisible(true); // Reexibe o menu
                    }
                });
            }
        });

        visualizarGraficoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GastoGraficoView gastografico = new GastoGraficoView();

                gastografico.pack();
                gastografico.setLocationRelativeTo(null);
                dispose();
                gastografico.setVisible(true);
                gastografico.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                        setVisible(true);
                    }
                });
            }
        });

        usuariosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UsuarioCadastroView usuarioCadastro = new UsuarioCadastroView();
                dispose();

                usuarioCadastro.setLocationRelativeTo(null);

                usuarioCadastro.setVisible(true);

                usuarioCadastro.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                        setVisible(true);
                    }
                });
            }
        });
        visualizarUsuariosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                dispose();
                UsuarioVisualizarView usuarioVisualizar = new UsuarioVisualizarView();

                usuarioVisualizar.pack();
                usuarioVisualizar.setLocationRelativeTo(null);
                usuarioVisualizar.setVisible(true);

                usuarioVisualizar.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                        setVisible(true);
                    }
                });
            }
        });
    }

    public static void main(String[] args) {
        TelaMenu dialog = new TelaMenu();
        dialog.setVisible(true);
    }

}