package Gui;

import java.awt.*;
import java.math.BigDecimal;
import java.sql.SQLException;
import javax.swing.*;

import Dao.CompraVendaDAO;
import Dao.ProdutoDAO;
import Entidades.CompraVenda;
import Entidades.Produto;

public class ProdutoGUI extends JFrame {
    private ProdutoDAO produtoDAO;

    public ProdutoGUI() {
        produtoDAO = new ProdutoDAO();

        // Configurações da Janela Principal
        setTitle("Controle de estoque - Moda Tecidos");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Criação do painel principal
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 1, 10, 10)); // Ajuste para 5 linhas

        // Botões do menu
        JButton btnCompraVenda = new JButton("Registrar Compra/Venda");
        JButton btnAdicionar = new JButton("Adicionar Produto");
        JButton btnAtualizar = new JButton("Atualizar Produto");
        JButton btnDeletar = new JButton("Deletar Produto");
        JButton btnConsultar = new JButton("Consultar Produto");
        JButton btnListarCompras = new JButton("Listar Todas as Compras");
        JButton btnListarVendas = new JButton("Listar Todas as Vendas");
        JButton btnSair = new JButton("Sair");

        // Adicionando ações aos botões
        btnCompraVenda.addActionListener(e -> abrirFormularioCompraVenda());
        btnAdicionar.addActionListener(e -> abrirFormularioAdicionar());
        btnAtualizar.addActionListener(e -> abrirFormularioAtualizar());
        btnDeletar.addActionListener(e -> abrirFormularioDeletar());
        btnConsultar.addActionListener(e -> abrirFormularioConsultar());
        btnListarCompras.addActionListener(e -> abrirListaCompras());
        btnListarVendas.addActionListener(e -> abrirListaVendas());
        btnSair.addActionListener(e -> System.exit(0));

        // Adicionando botões ao painel
        panel.add(btnCompraVenda);
        panel.add(btnAdicionar);
        panel.add(btnAtualizar);
        panel.add(btnDeletar);
        panel.add(btnConsultar);
        panel.add(btnListarCompras);
        panel.add(btnListarVendas);
        panel.add(btnSair);

        // Adicionando o painel à janela
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
 
    private void abrirFormularioAdicionar() {
        JFrame frameAdicionar = new JFrame("Adicionar Produto");
        frameAdicionar.setSize(400, 300);
        frameAdicionar.setLocationRelativeTo(null);
        frameAdicionar.setLayout(new GridLayout(6, 2, 10, 10));

        // Campos para adicionar produto
        JTextField txtNome = new JTextField();
        JComboBox<String> cbTipo = new JComboBox<>(new String[]{"Cama", "Mesa", "Banho", "Tecido"});
        JTextField txtSubTipo = new JTextField();
        JTextField txtLojaId = new JTextField();
        JTextField txtEstoque = new JTextField();
        JButton btnSalvar = new JButton("Salvar");

        // Adicionando campos ao frame
        frameAdicionar.add(new JLabel("Nome:"));
        frameAdicionar.add(txtNome);
        frameAdicionar.add(new JLabel("Tipo:"));
        frameAdicionar.add(cbTipo);
        frameAdicionar.add(new JLabel("Subtipo:"));
        frameAdicionar.add(txtSubTipo);
        frameAdicionar.add(new JLabel("Loja ID:"));
        frameAdicionar.add(txtLojaId);
        frameAdicionar.add(new JLabel("Estoque:"));
        frameAdicionar.add(txtEstoque);
        frameAdicionar.add(btnSalvar);

        btnSalvar.addActionListener(e -> {
            String nome = txtNome.getText();
            String tipo = (String) cbTipo.getSelectedItem();
            String subTipo = txtSubTipo.getText();
            int lojaId;
            int estoque;

            try {
                lojaId = Integer.parseInt(txtLojaId.getText());
                estoque = Integer.parseInt(txtEstoque.getText());

                Produto novoProduto = new Produto(0, nome, tipo, subTipo, lojaId, estoque);
                produtoDAO.addProduto(novoProduto);
                JOptionPane.showMessageDialog(frameAdicionar, "Produto adicionado com sucesso!");
                frameAdicionar.dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frameAdicionar, "Loja ID e Estoque devem ser números inteiros.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        frameAdicionar.setVisible(true);
    }

    private void abrirFormularioAtualizar() {
        JFrame frameAtualizar = new JFrame("Atualizar Produto");
        frameAtualizar.setSize(400, 300);
        frameAtualizar.setLocationRelativeTo(null);
        frameAtualizar.setLayout(new GridLayout(2, 2, 10, 10));

        JTextField txtId = new JTextField();
        JButton btnBuscar = new JButton("Buscar");

        frameAtualizar.add(new JLabel("ID do Produto:"));
        frameAtualizar.add(txtId);
        frameAtualizar.add(btnBuscar);

        btnBuscar.addActionListener(e -> {
            try {
                int id = Integer.parseInt(txtId.getText());
                Produto produto = produtoDAO.getById(id);

                if (produto != null) {
                    JFrame frameEdicao = new JFrame("Editar Produto");
                    frameEdicao.setSize(400, 300);
                    frameEdicao.setLocationRelativeTo(null);
                    frameEdicao.setLayout(new GridLayout(6, 2, 10, 10));

                    JTextField txtNome = new JTextField(produto.getNome());
                    JComboBox<String> cbTipo = new JComboBox<>(new String[]{"Cama", "Mesa", "Banho", "Tecido"});
                    cbTipo.setSelectedItem(produto.getTipo());
                    JTextField txtSubTipo = new JTextField(produto.getSubTipo());
                    JTextField txtLojaId = new JTextField(String.valueOf(produto.getLojaId()));
                    JTextField txtEstoque = new JTextField(String.valueOf(produto.getEstoque()));
                    JButton btnSalvar = new JButton("Salvar");

                    frameEdicao.add(new JLabel("Nome:"));
                    frameEdicao.add(txtNome);
                    frameEdicao.add(new JLabel("Tipo:"));
                    frameEdicao.add(cbTipo);
                    frameEdicao.add(new JLabel("Subtipo:"));
                    frameEdicao.add(txtSubTipo);
                    frameEdicao.add(new JLabel("Loja ID:"));
                    frameEdicao.add(txtLojaId);
                    frameEdicao.add(new JLabel("Estoque:"));
                    frameEdicao.add(txtEstoque);
                    frameEdicao.add(btnSalvar);

                    btnSalvar.addActionListener(ev -> {
                        try {
                            String nome = txtNome.getText();
                            String tipo = (String) cbTipo.getSelectedItem();
                            String subTipo = txtSubTipo.getText();
                            int lojaId = Integer.parseInt(txtLojaId.getText());
                            int estoque = Integer.parseInt(txtEstoque.getText());

                            produto.setNome(nome);
                            produto.setTipo(tipo);
                            produto.setSubTipo(subTipo);
                            produto.setLojaId(lojaId);
                            produto.setEstoque(estoque);

                            produtoDAO.updateProduto(produto);
                            JOptionPane.showMessageDialog(frameEdicao, "Produto atualizado com sucesso!");
                            frameEdicao.dispose();
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(frameEdicao, "Loja ID e Estoque devem ser números inteiros.", "Erro", JOptionPane.ERROR_MESSAGE);
                        }
                    });

                    frameEdicao.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(frameAtualizar, "Produto não encontrado.", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frameAtualizar, "ID deve ser um número inteiro.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        frameAtualizar.setVisible(true);
    }
    private void abrirFormularioDeletar() {
        JFrame frameDeletar = new JFrame("Deletar Produto");
        frameDeletar.setSize(300, 150);
        frameDeletar.setLocationRelativeTo(null);
        frameDeletar.setLayout(new GridLayout(2, 2, 10, 10));

        JTextField txtId = new JTextField();
        JButton btnDeletar = new JButton("Deletar");

        frameDeletar.add(new JLabel("ID do Produto:"));
        frameDeletar.add(txtId);
        frameDeletar.add(btnDeletar);

        btnDeletar.addActionListener(e -> {
            try {
                int id = Integer.parseInt(txtId.getText());
                produtoDAO.deleteProduto(id);
                JOptionPane.showMessageDialog(frameDeletar, "Produto deletado com sucesso!");
                frameDeletar.dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frameDeletar, "ID deve ser um número inteiro.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        frameDeletar.setVisible(true);
    }
    private void abrirFormularioConsultar() {
        JFrame frameConsultar = new JFrame("Consultar Produto");
        frameConsultar.setSize(400, 300);
        frameConsultar.setLocationRelativeTo(null);
        frameConsultar.setLayout(new GridLayout(6, 2, 10, 10));

        // Campos de consulta
        JTextField txtId = new JTextField();
        JTextField txtNome = new JTextField();
        JComboBox<String> cbTipo = new JComboBox<>(new String[]{"", "Cama", "Mesa", "Banho", "Tecido"});
        JTextField txtSubTipo = new JTextField();
        JButton btnBuscar = new JButton("Buscar");

        // Adicionando campos ao frame
        frameConsultar.add(new JLabel("ID do Produto:"));
        frameConsultar.add(txtId);
        frameConsultar.add(new JLabel("Nome do Produto:"));
        frameConsultar.add(txtNome);
        frameConsultar.add(new JLabel("Tipo:"));
        frameConsultar.add(cbTipo);
        frameConsultar.add(new JLabel("Subtipo:"));
        frameConsultar.add(txtSubTipo);
        frameConsultar.add(new JLabel("")); // Espaço vazio para alinhamento
        frameConsultar.add(btnBuscar);

        btnBuscar.addActionListener(e -> {
            try {
                String nome = txtNome.getText().trim();
                String tipo = (String) cbTipo.getSelectedItem();
                String subTipo = txtSubTipo.getText().trim();
                String idStr = txtId.getText().trim();

                // Verificar qual campo foi preenchido para consulta
                if (!idStr.isEmpty()) {
                    int id = Integer.parseInt(idStr);
                    Produto produto = produtoDAO.getById(id);
                    if (produto != null) {
                        exibirProdutoDetalhado(frameConsultar, produto);
                    } else {
                        JOptionPane.showMessageDialog(frameConsultar, "Produto não encontrado.", "Erro", JOptionPane.ERROR_MESSAGE);
                    }
                } else if (!nome.isEmpty()) {
                    java.util.List<Produto> produtos = produtoDAO.getByName(nome);
                    exibirProdutosDetalhados(frameConsultar, produtos);
                } else if (!tipo.isEmpty()) {
                    java.util.List<Produto> produtos = produtoDAO.getByTipo(tipo);
                    exibirProdutosDetalhados(frameConsultar, produtos);
                } else if (!subTipo.isEmpty()) {
                    java.util.List<Produto> produtos = produtoDAO.getBySubTipo(subTipo);
                    exibirProdutosDetalhados(frameConsultar, produtos);
                } else {
                    JOptionPane.showMessageDialog(frameConsultar, "Por favor, preencha pelo menos um campo para consulta.", "Erro", JOptionPane.WARNING_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frameConsultar, "ID deve ser um número inteiro.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        frameConsultar.setVisible(true);
    }

    // Método auxiliar para exibir detalhes de um único produto
    private void exibirProdutoDetalhado(JFrame frame, Produto produto) {
        String detalhes = "ID: " + produto.getId() + "\n" +
                          "Nome: " + produto.getNome() + "\n" +
                          "Tipo: " + produto.getTipo() + "\n" +
                          "Subtipo: " + produto.getSubTipo() + "\n" +
                          "Estoque: " + produto.getEstoque();
                        

        JTextArea textArea = new JTextArea(detalhes);
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(400, 200));
        JOptionPane.showMessageDialog(frame, scrollPane, "Detalhes do Produto", JOptionPane.INFORMATION_MESSAGE);
    }

    // Método auxiliar para exibir detalhes de múltiplos produtos
    private void exibirProdutosDetalhados(JFrame frame, java.util.List<Produto> produtos) {
        if (produtos.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Nenhum produto encontrado.", "Erro", JOptionPane.ERROR_MESSAGE);
        } else {
            StringBuilder sb = new StringBuilder();
            for (Produto produto : produtos) {
                sb.append("ID: ").append(produto.getId()).append("\n")
                  .append("Nome: ").append(produto.getNome()).append("\n")
                  .append("Tipo: ").append(produto.getTipo()).append("\n")
                  .append("Subtipo: ").append(produto.getSubTipo()).append("\n")
                  .append("Estoque: ").append(produto.getEstoque()).append("\n")
                  .append("------------------------\n"); // Adicione mais campos conforme necessário
            }

            JTextArea textArea = new JTextArea(sb.toString());
            textArea.setEditable(false);
            JScrollPane scrollPane = new JScrollPane(textArea);
            scrollPane.setPreferredSize(new Dimension(400, 200));
            JOptionPane.showMessageDialog(frame, scrollPane, "Resultados da Consulta", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}