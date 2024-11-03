package Gui;

import javax.swing.*;

import Dao.ProdutoDAO;
import Entidades.Produto;

import java.awt.*;

public class ProdutoGUI extends JFrame {
    private ProdutoDAO produtoDAO;

    public ProdutoGUI() {
        produtoDAO = new ProdutoDAO();
        
        setTitle("Gerenciamento de Produtos");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 1, 10, 10));

        JButton btnAdicionar = new JButton("Adicionar Produto");
        JButton btnAtualizar = new JButton("Atualizar Produto");
        JButton btnDeletar = new JButton("Deletar Produto");
        JButton btnConsultar = new JButton("Consultar Produto");

        btnAdicionar.addActionListener(e -> abrirFormularioAdicionar());
        btnAtualizar.addActionListener(e -> abrirFormularioAtualizar());
        btnDeletar.addActionListener(e -> abrirFormularioDeletar());
        btnConsultar.addActionListener(e -> abrirFormularioConsultar());

        panel.add(btnAdicionar);
        panel.add(btnAtualizar);
        panel.add(btnDeletar);
        panel.add(btnConsultar);

        add(panel);
        setVisible(true);
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
