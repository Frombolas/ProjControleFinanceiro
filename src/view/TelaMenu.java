package view;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TelaMenu extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton Usuario;
    private JButton Gastos;
    private JButton visualizarGráficoButton;
    private JLabel simbolo;

    public TelaMenu() {
        setContentPane(contentPane);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getRootPane().setDefaultButton(buttonOK);
        setTitle("Debt control");

        ImageIcon icon = new ImageIcon("img/money.png");
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
                
            }
        });

        visualizarGráficoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }

    public static void main(String[] args) {
        TelaMenu dialog = new TelaMenu();
        //dialog.pack();
        dialog.setVisible(true);
    }

    //private void createUIComponents() {
    //    // TODO: place custom component creation code here
    //}
}
