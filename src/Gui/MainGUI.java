package Gui;

import javax.swing.*;
import java.awt.*;

public class MainGUI extends JFrame {

    public MainGUI() {
        setTitle("Controle de Estoque - Moda Tecidos");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 1, 10, 10));

        JButton btnProduto = new JButton("Gerenciar Produtos");
        JButton btnCompraVenda = new JButton("Gerenciar Compras/Vendas");
        JButton btnSair = new JButton("Sair");

        btnProduto.addActionListener(e -> new ProdutoGUI());
        btnCompraVenda.addActionListener(e -> new CompraVendaGUI());
        btnSair.addActionListener(e -> System.exit(0));

        panel.add(btnProduto);
        panel.add(btnCompraVenda);
        panel.add(btnSair);

        add(panel);
        setVisible(true);
    }

    public static void main(String[] args) {
        new MainGUI();
    }
}
