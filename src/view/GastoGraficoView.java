package view;

import dao.GastoDao;
import model.Gasto;
import org.knowm.xchart.PieChart;
import org.knowm.xchart.PieChartBuilder;
import org.knowm.xchart.XChartPanel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class GastoGraficoView extends JFrame {
    private JTextField txtUsuario;
    private JTextField txtSaldoInicial;
    private JButton btnGerarGrafico;
    private GastoDao gastoDao;

    public GastoGraficoView() {
        gastoDao = new GastoDao();
        setTitle("Gráfico de Gastos por Categoria");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        ImageIcon icon = new ImageIcon("src/img/money.png");
        setIconImage(icon.getImage());

        setSize(800, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10,10));
        getContentPane().setBackground(new Color(245,245,245));

        // Painel de entrada dos dados do usuário e saldo
        JPanel panelEntrada = new JPanel(new FlowLayout());
        panelEntrada.setBackground(new Color(245,245,245));
        panelEntrada.setBorder(new EmptyBorder(10,10,10,10));
        panelEntrada.add(new JLabel("Usuário:"));
        txtUsuario = new JTextField(15);
        panelEntrada.add(txtUsuario);
        panelEntrada.add(new JLabel("Saldo Inicial:"));
        txtSaldoInicial = new JTextField(10);
        panelEntrada.add(txtSaldoInicial);
        btnGerarGrafico = new JButton("Gerar Gráfico");
        btnGerarGrafico.setBackground(new Color(70,130,180));
        btnGerarGrafico.setForeground(Color.WHITE);
        panelEntrada.add(btnGerarGrafico);
        add(panelEntrada, BorderLayout.NORTH);

        btnGerarGrafico.addActionListener(e -> gerarGrafico());
    }

    private void gerarGrafico() {
        String nomeUsuario = txtUsuario.getText().trim();
        double saldoInicial;
        try {
            saldoInicial = Double.parseDouble(txtSaldoInicial.getText().trim());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Saldo Inicial inválido.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Filtrar os gastos do usuário informado
        Set<Gasto> gastos = gastoDao.getGastos();
        Map<String, Double> categoriaSoma = new HashMap<>();
        double totalGastos = 0;
        for (Gasto gasto : gastos) {
            if (gasto.getUsuario() != null && gasto.getUsuario().getNome().equalsIgnoreCase(nomeUsuario)) {
                String categoria = gasto.getCategoria();
                double valor = gasto.getValor();
                totalGastos += valor;
                categoriaSoma.put(categoria, categoriaSoma.getOrDefault(categoria, 0.0) + valor);
            }
        }

        // Calcular saldo restante
        double saldoRestante = saldoInicial - totalGastos;
        if (saldoRestante < 0) {
            saldoRestante = 0;
        }
        // Adiciona o saldo restante como uma fatia no gráfico
        categoriaSoma.put("Saldo Restante", saldoRestante);

        // Criar o gráfico de pizza
        PieChart chart = new PieChartBuilder().width(600).height(400)
                .title("Gastos por Categoria - " + nomeUsuario).build();

        // Adicionar cada categoria ao gráfico
        for (Map.Entry<String, Double> entry : categoriaSoma.entrySet()) {
            String seriesName = entry.getKey() + " (" + entry.getValue() + ")";
            chart.addSeries(seriesName, entry.getValue());
        }

        // Exibir o gráfico em um painel
        JPanel chartPanel = new XChartPanel<>(chart);
        JFrame frameChart = new JFrame("Gráfico de Gastos");
        frameChart.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frameChart.getContentPane().add(chartPanel);
        frameChart.pack();
        frameChart.setLocationRelativeTo(null);
        frameChart.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GastoGraficoView().setVisible(true));
    }
}
