package Gui;

import javax.swing.*;

import Dao.CompraVendaDAO;
import Dao.ProdutoDAO;
import Entidades.CompraVenda;
import Entidades.Produto;

import java.awt.*;

public class CompraVendaGUI extends JFrame {

    public CompraVendaGUI() {
        setTitle("Registro de Compras e Vendas");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 1, 10, 10));

        JButton btnRegistrarTransacao = new JButton("Registrar Compra/Venda");
        JButton btnListarCompras = new JButton("Listar Todas as Compras");
        JButton btnListarVendas = new JButton("Listar Todas as Vendas");

        btnRegistrarTransacao.addActionListener(e -> abrirFormularioCompraVenda());
        btnListarCompras.addActionListener(e -> abrirListaCompras());
        btnListarVendas.addActionListener(e -> abrirListaVendas());

        panel.add(btnRegistrarTransacao);
        panel.add(btnListarCompras);
        panel.add(btnListarVendas);

        add(panel);
        setVisible(true);
    }

    private void abrirListaCompras() {
        JFrame frameCompras = new JFrame("Lista de Todas as Compras");
        frameCompras.setSize(400, 300);
        frameCompras.setLocationRelativeTo(null);

        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);

        try {
            CompraVendaDAO compraVendaDAO = new CompraVendaDAO();
            java.util.List<CompraVenda> compras = compraVendaDAO.listarTodasCompras();

            StringBuilder sb = new StringBuilder();
            for (CompraVenda compra : compras) {
                sb.append("ID: ").append(compra.getIdTransacao()).append("\n")
                  .append("Produto: ").append(compra.getProduto().getNome()).append("\n")
                  .append("Quantidade: ").append(compra.getQuantidade()).append("\n")
                  .append("Valor Unitário: ").append(compra.getValorUnitario()).append("\n")
                  .append("Data: ").append(compra.getData()).append("\n")
                  .append("ID da Loja: ").append(compra.getIdLoja()).append("\n")
                  .append("------------------------\n");
            }
            textArea.setText(sb.toString());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(frameCompras, "Erro ao listar compras.", "Erro", JOptionPane.ERROR_MESSAGE);
        }

        frameCompras.add(scrollPane);
        frameCompras.setVisible(true);
    }

    private void abrirListaVendas() {
        JFrame frameVendas = new JFrame("Lista de Todas as Vendas");
        frameVendas.setSize(400, 300);
        frameVendas.setLocationRelativeTo(null);

        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);

        try {
            CompraVendaDAO compraVendaDAO = new CompraVendaDAO();
            java.util.List<CompraVenda> vendas = compraVendaDAO.listarTodasVendas();

            StringBuilder sb = new StringBuilder();
            for (CompraVenda venda : vendas) {
                sb.append("ID: ").append(venda.getIdTransacao()).append("\n")
                  .append("Produto: ").append(venda.getProduto().getNome()).append("\n")
                  .append("Quantidade: ").append(venda.getQuantidade()).append("\n")
                  .append("Valor Unitário: ").append(venda.getValorUnitario()).append("\n")
                  .append("Data: ").append(venda.getData()).append("\n")
                  .append("ID da Loja: ").append(venda.getIdLoja()).append("\n")
                  .append("------------------------\n");
            }
            textArea.setText(sb.toString());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(frameVendas, "Erro ao listar vendas.", "Erro", JOptionPane.ERROR_MESSAGE);
        }

        frameVendas.add(scrollPane);
        frameVendas.setVisible(true);
    }

    private void abrirFormularioCompraVenda() {
        JFrame frameTransacao = new JFrame("Registrar Compra/Venda");
        frameTransacao.setSize(400, 300);
        frameTransacao.setLocationRelativeTo(null);
        frameTransacao.setLayout(new GridLayout(7, 2, 10, 10)); // Alterado para 7 linhas

        // Campos para a transação
        JComboBox<String> cbTipo = new JComboBox<>(new String[]{"Compra", "Venda"});
        JTextField txtProdutoId = new JTextField();
        JTextField txtQuantidade = new JTextField();
        JTextField txtLojaId = new JTextField(); // Campo para o ID da loja
        JTextField txtValorUnitario = new JTextField(); // Campo para o valor unitário
        JButton btnRegistrar = new JButton("Registrar");

        // Adicionando os componentes à janela
        frameTransacao.add(new JLabel("Tipo de Transação:"));
        frameTransacao.add(cbTipo);
        frameTransacao.add(new JLabel("ID do Produto:"));
        frameTransacao.add(txtProdutoId);
        frameTransacao.add(new JLabel("Quantidade:"));
        frameTransacao.add(txtQuantidade);
        frameTransacao.add(new JLabel("ID da Loja:"));
        frameTransacao.add(txtLojaId); // Adicionando campo de ID da loja
        frameTransacao.add(new JLabel("Valor Unitário:"));
        frameTransacao.add(txtValorUnitario); // Adicionando campo de valor unitário
        frameTransacao.add(btnRegistrar);

        btnRegistrar.addActionListener(e -> {
            String tipo = (String) cbTipo.getSelectedItem();
            int produtoId;
            int quantidade;
            int idLoja;
            double valorUnitario;

            try {
                produtoId = Integer.parseInt(txtProdutoId.getText());
                quantidade = Integer.parseInt(txtQuantidade.getText());
                idLoja = Integer.parseInt(txtLojaId.getText()); // Obtendo ID da loja
                valorUnitario = Double.parseDouble(txtValorUnitario.getText()); // Obtendo valor unitário
                ProdutoDAO produtoDAO = new ProdutoDAO();
                Produto produto = produtoDAO.getById(produtoId);
                if (produto != null) {
                    // Criando a transação com os novos parâmetros
                    CompraVenda transacao = new CompraVenda(0, produto, idLoja, tipo, quantidade, new java.util.Date(), valorUnitario);

                    CompraVendaDAO compraVendaDAO = new CompraVendaDAO();

                    if ("Compra".equals(tipo)) {
                        compraVendaDAO.registrarCompra(transacao);
                    } else if ("Venda".equals(tipo)) {
                        compraVendaDAO.registrarVenda(transacao);
                    }

                    JOptionPane.showMessageDialog(frameTransacao, "Transação registrada com sucesso!");
                    frameTransacao.dispose();
                } else {
                    JOptionPane.showMessageDialog(frameTransacao, "Produto não encontrado.", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frameTransacao, "ID do Produto, Quantidade, ID da Loja e Valor Unitário devem ser válidos.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        frameTransacao.setVisible(true);
    }
 
}
