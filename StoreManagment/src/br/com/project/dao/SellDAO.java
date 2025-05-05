package br.com.project.dao;

import java.sql.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

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
}
