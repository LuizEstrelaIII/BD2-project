package br.com.project.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import br.com.project.model.Produto;

public class SellDAO {

	
	public void cadastroProdutos(String nome, int quantidade, String descricao, BigDecimal valor) {
		String sql = "INSERT INTO produto (nome, quantidade, descricao, valor) VALUES (?, ?, ?, ?)";
		
		try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)){
			stmt.setString(1, nome);
			stmt.setInt(2, quantidade);
			stmt.setString(3, descricao);
			stmt.setBigDecimal(4, valor);
			stmt.executeUpdate();
			JOptionPane.showMessageDialog(null, "Produto cadastrado!!");
			
		} catch (SQLException e) { 
			JOptionPane.showMessageDialog(null, "Erro ao realizar cadastro do produto: " + e.getMessage());
		}
	}
	
	public void cadastroFuncionario(String nome, int idade, String cargo, BigDecimal salario, Date nascimento) {
		String sql = "INSERT INTO produto (nome, idade, cargo, salario, nascimento) VALUES (?, ?, ?, ?)";
		
		try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)){
			stmt.setString(1, nome);
			stmt.setInt(2, idade);
			stmt.setString(3, cargo);
			stmt.setBigDecimal(4, salario);
			stmt.setDate(5, nascimento);
			stmt.executeUpdate();
			JOptionPane.showMessageDialog(null, "Produto cadastrado!!");
			
		} catch (SQLException e) { 
			JOptionPane.showMessageDialog(null, "Erro ao realizar cadastro do produto: " + e.getMessage());
		}
		
	}
	
	public void registrarVenda(int idVendedor, int idCliente, int idProduto) {
        String sqlVenda = "INSERT INTO venda (id_vendedor, id_cliente, data) VALUES (?, ?, CURDATE())";
        String sqlUpdateQuantidade = "UPDATE produto SET quantidade = quantidade - 1 WHERE id = ? AND quantidade > 0";

        try (Connection conn = DatabaseConnection.getConnection()) {
     
            conn.setAutoCommit(false);

            try (PreparedStatement stmtProduto = conn.prepareStatement(sqlUpdateQuantidade)) {
                stmtProduto.setInt(1, idProduto);
                int rowsAffected = stmtProduto.executeUpdate();
                if (rowsAffected == 0) {
                    throw new SQLException("Produto fora de estoque!");
                }
            }

       
            try (PreparedStatement stmtVenda = conn.prepareStatement(sqlVenda)) {
                stmtVenda.setInt(1, idVendedor);
                stmtVenda.setInt(2, idCliente);
                stmtVenda.executeUpdate();
            }

            conn.commit();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao registrar venda: " + e.getMessage());
        }
    }
	
	
	public void verificarBonus(int idVendedor, int idCliente) throws SQLException {
		try(Connection conn = DatabaseConnection.getConnection()){
			
			String sqlBonus = "SELECT salario FROM funcionario WHERE id = ?";
			try(PreparedStatement stmt = conn.prepareStatement(sqlBonus)){
				stmt.setInt(1 , idVendedor);
				ResultSet rs = stmt.executeQuery();
				if(rs.next()) {
					BigDecimal salario = rs.getBigDecimal("salario");
					if (salario.compareTo(new BigDecimal("10000.00")) > 0) {
                        JOptionPane.showMessageDialog(null, "Bônus de 5% aplicado ao vendedor!");
					}
				
				}
			}
			
			String sqlCashback = "SELECT cashback FROIM clienteespecial WHERE id_cliente ?";
			try(PreparedStatement stmt = conn.prepareStatement(sqlCashback)){
				stmt.setInt(1, idCliente);
				ResultSet rs = stmt.executeQuery();
				if(rs.next()) {
					BigDecimal cashback = rs.getBigDecimal("cashback");
					JOptionPane.showMessageDialog(null, "Cashback de " + cashback + "aplicado ao cliente!");
				}
			}
			
		}
		
	}
	
	
	public List<Produto> listarProdutos(){
		List<Produto> produtos = new ArrayList<>();
		String sqlLista = "SELECT * FROM view_produtos_quantidade*";
		
		try(Connection conn = DatabaseConnection.getConnection()){
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sqlLista);
			while (rs.next()) {
                produtos.add(new Produto(0, rs.getString("nome"), rs.getInt("quantidade"), "", BigDecimal.ZERO));
			}
		}
		catch(SQLException e) {
			JOptionPane.showMessageDialog(null, "Erro ao listar produtos:" + e.getMessage() + "!");
		}
		return produtos;
	}
	
	
}	


