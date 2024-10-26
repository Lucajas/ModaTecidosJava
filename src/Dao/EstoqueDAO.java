package Dao;

import java.sql.*;

import DbConn.DatabaseConnection;
import Entidades.Estoque;

public class EstoqueDAO {
    public void addEstoque(Estoque estoque) throws SQLException {
        String sql = "INSERT INTO Estoque (idLoja, idProduto, quantidade) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setInt(1, estoque.getLojaId());
            stmt.setInt(2, estoque.getProdutoId());
            stmt.setInt(3, estoque.getQuantidade());
            stmt.executeUpdate();
        }
    }

    // Outros métodos como atualizar, deletar, buscar por ID, etc.
}

