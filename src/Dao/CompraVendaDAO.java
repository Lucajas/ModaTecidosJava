package Dao;

import DbConn.DatabaseConnection;
import Entidades.CompraVenda;
import Entidades.Produto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CompraVendaDAO {
	public void registrarCompra(CompraVenda compra) {
	    try (Connection conexao = DatabaseConnection.getConnection()) {
	        // Primeiro, capturamos a quantidade atual de estoque do produto
	        String sqlSelectEstoque = "SELECT estoque FROM produtos WHERE id = ?";
	        PreparedStatement stmtSelectEstoque = conexao.prepareStatement(sqlSelectEstoque);
	        stmtSelectEstoque.setInt(1, compra.getProduto().getId());

	        ResultSet rsEstoque = stmtSelectEstoque.executeQuery();
	        int estoqueAtual = 0;
	        if (rsEstoque.next()) {
	            estoqueAtual = rsEstoque.getInt("estoque");
	        }

	        // Depois, inserimos a transação de compra na tabela compravenda
	        String sqlCompra = "INSERT INTO compravenda (idProduto, idLoja, tipoTransacao, quantidade, dataTransacao, valorUnitario) " +
	                "VALUES (?, ?, ?, ?, NOW(), ?)";
	        PreparedStatement stmtCompra = conexao.prepareStatement(sqlCompra);

	        stmtCompra.setInt(1, compra.getProduto().getId());
	        stmtCompra.setInt(2, compra.getProduto().getLojaId());
	        stmtCompra.setString(3, "COMPRA");
	        stmtCompra.setInt(4, compra.getQuantidade());
	        stmtCompra.setDouble(5, compra.getValorUnitario());
	        stmtCompra.executeUpdate();

	        // Atualizando o estoque do produto com a nova quantidade
	        String sqlUpdateProduto = "UPDATE produtos SET estoque = ? WHERE id = ?";
	        PreparedStatement stmtUpdateProduto = conexao.prepareStatement(sqlUpdateProduto);
	        stmtUpdateProduto.setInt(1, estoqueAtual + compra.getQuantidade());
	        stmtUpdateProduto.setInt(2, compra.getProduto().getId());
	        stmtUpdateProduto.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}


	public void registrarVenda(CompraVenda venda) {
	    try (Connection conexao = DatabaseConnection.getConnection()) {
	        // Inserindo a transação de venda
	        String sqlVenda = "INSERT INTO compravenda (idProduto, idLoja, tipoTransacao, quantidade, dataTransacao, valorUnitario) " +
	                          "VALUES (?, ?, ?, ?, NOW(), ?)";
	        PreparedStatement stmtVenda = conexao.prepareStatement(sqlVenda);

	        stmtVenda.setInt(1, venda.getProduto().getId()); 
	        stmtVenda.setInt(2, venda.getProduto().getLojaId()); // Ajustado para pegar o id da loja corretamente
	        stmtVenda.setString(3, "VENDA"); 
	        stmtVenda.setInt(4, venda.getQuantidade()); 
	        stmtVenda.setDouble(5, venda.getValorUnitario()); 
	        stmtVenda.executeUpdate();

	        // Obtendo o estoque atual do produto
	        String sqlSelectEstoque = "SELECT estoque FROM lojas.produtos WHERE id = ?";
	        PreparedStatement stmtSelectEstoque = conexao.prepareStatement(sqlSelectEstoque);
	        stmtSelectEstoque.setInt(1, venda.getProduto().getId());
	        
	        ResultSet rs = stmtSelectEstoque.executeQuery();
	        int estoqueAtual = 0;
	        if (rs.next()) {
	            estoqueAtual = rs.getInt("estoque");
	        }

	        // Atualizando o estoque do produto (subtração da quantidade vendida)
	        String sqlUpdateProduto = "UPDATE produtos SET estoque = ? WHERE id = ?";
	        PreparedStatement stmtProduto = conexao.prepareStatement(sqlUpdateProduto);

	        stmtProduto.setInt(1, estoqueAtual - venda.getQuantidade()); // Subtrai a quantidade vendida do estoque atual
	        stmtProduto.setInt(2, venda.getProduto().getId());

	        stmtProduto.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	public List<CompraVenda> listarTodasCompras() {
	    List<CompraVenda> compras = new ArrayList<>();

	    try (Connection conexao = DatabaseConnection.getConnection()) {
	        String sql = "SELECT compravenda.*, produtos.nome AS nomeProduto " +
	                     "FROM compravenda " +
	                     "INNER JOIN produtos ON compravenda.idProduto = produtos.id " +
	                     "WHERE tipoTransacao = 'COMPRA'";
	        PreparedStatement stmt = conexao.prepareStatement(sql);
	        ResultSet rs = stmt.executeQuery();

	        while (rs.next()) {
	            Produto produto = new Produto(rs.getInt("idProduto"));
	            produto.setNome(rs.getString("nomeProduto")); // Atribui o nome do produto
	            int idLoja = rs.getInt("idLoja");
	            String tipoTransacao = rs.getString("tipoTransacao");
	            int quantidade = rs.getInt("quantidade");
	            double valorUnitario = rs.getDouble("valorUnitario");
	            java.util.Date dataTransacao = rs.getDate("dataTransacao");

	            CompraVenda compra = new CompraVenda(rs.getInt("idTransacao"), produto, idLoja, tipoTransacao, quantidade, dataTransacao, valorUnitario);
	            compras.add(compra);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return compras;
	}

	    // Método para listar todas as vendas
	public List<CompraVenda> listarTodasVendas() {
	    List<CompraVenda> vendas = new ArrayList<>();

	    try (Connection conexao = DatabaseConnection.getConnection()) {
	        String sql = "SELECT compravenda.*, produtos.nome AS nomeProduto " +
	                     "FROM compravenda " +
	                     "INNER JOIN produtos ON compravenda.idProduto = produtos.id " +
	                     "WHERE tipoTransacao = 'VENDA'";
	        PreparedStatement stmt = conexao.prepareStatement(sql);
	        ResultSet rs = stmt.executeQuery();

	        while (rs.next()) {
	            Produto produto = new Produto(rs.getInt("idProduto"));
	            produto.setNome(rs.getString("nomeProduto")); // Atribui o nome do produto
	            int idLoja = rs.getInt("idLoja");
	            String tipoTransacao = rs.getString("tipoTransacao");
	            int quantidade = rs.getInt("quantidade");
	            double valorUnitario = rs.getDouble("valorUnitario");
	            java.util.Date dataTransacao = rs.getDate("dataTransacao");

	            CompraVenda venda = new CompraVenda(rs.getInt("idTransacao"), produto, idLoja, tipoTransacao, quantidade, dataTransacao, valorUnitario);
	            vendas.add(venda);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return vendas;
	}

}
