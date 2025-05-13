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
import java.util.Random;

import javax.swing.JOptionPane;

import br.com.project.model.Produto;
import br.com.project.model.ClienteEspecial;
import br.com.project.model.Funcionario;

public class SellDAO {

    public void cadastroProdutos(String nome, int quantidade, String descricao, BigDecimal valor) {
        String sql = "INSERT INTO produto (nome, quantidade, descricao, valor) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(sql)) {
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

 
    public void cadastroCliente(String nome, String sexo, int idade, Date nascimento) {
        String sql = "INSERT INTO cliente (nome, sexo, idade, nascimento) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nome);
            stmt.setString(2, sexo);
            stmt.setInt(3, idade);
            stmt.setDate(4, nascimento);
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Cliente cadastrado!!");
        } catch (SQLException e) { 
            JOptionPane.showMessageDialog(null, "Erro ao realizar cadastro do cliente: " + e.getMessage());
        }
    }

    public void registrarVenda(int idVendedor, int idCliente, int idProduto) {
        String sqlVenda = "INSERT INTO venda (id_vendedor, id_cliente, data) VALUES (?, ?, CURDATE())";
        String sqlVendaItem = "INSERT INTO venda_item (id_venda, id_produto, quantidade, valor_unitario) VALUES (?, ?, 1, (SELECT valor FROM produto WHERE id = ?))";
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

            int vendaId;
            try (PreparedStatement stmtVenda = conn.prepareStatement(sqlVenda, Statement.RETURN_GENERATED_KEYS)) {
                stmtVenda.setInt(1, idVendedor);
                stmtVenda.setInt(2, idCliente);
                stmtVenda.executeUpdate();
                ResultSet rs = stmtVenda.getGeneratedKeys();
                if (rs.next()) {
                    vendaId = rs.getInt(1);
                } else {
                    throw new SQLException("Falha ao obter ID da venda.");
                }
            }

            try (PreparedStatement stmtVendaItem = conn.prepareStatement(sqlVendaItem)) {
                stmtVendaItem.setInt(1, vendaId);
                stmtVendaItem.setInt(2, idProduto);
                stmtVendaItem.setInt(3, idProduto);
                stmtVendaItem.executeUpdate();
            }

            conn.commit();

            verificarBonus(idVendedor, idCliente);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao registrar venda: " + e.getMessage());
        }
    }

    public void verificarBonus(int idVendedor, int idCliente) throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sqlBonus = "SELECT salario FROM funcionario WHERE id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sqlBonus)) {
                stmt.setInt(1, idVendedor);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    BigDecimal salario = rs.getBigDecimal("salario");
                    if (salario.compareTo(new BigDecimal("10000.00")) > 0) {
                        JOptionPane.showMessageDialog(null, "Bônus de 5% aplicado ao vendedor!");
                    }
                }
            }

            String sqlCashback = "SELECT cashback FROM clienteespecial WHERE id_cliente = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sqlCashback)) {
                stmt.setInt(1, idCliente);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    BigDecimal cashback = rs.getBigDecimal("cashback");
                    JOptionPane.showMessageDialog(null, "Cashback de " + cashback + " aplicado ao cliente!");
                }
            }
        }
    }

    public List<Produto> listarProdutos() {
        List<Produto> produtos = new ArrayList<>();
        String sqlLista = "SELECT * FROM view_produtos_quantidade";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sqlLista)) {
            while (rs.next()) {
                produtos.add(new Produto(0, rs.getString("nome"), rs.getInt("quantidade"), "", BigDecimal.ZERO));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao listar produtos: " + e.getMessage() + "!");
        }
        return produtos;
    }

    public List<Funcionario> listarFuncionarios() {
        List<Funcionario> funcionarios = new ArrayList<>();
        String sql = "SELECT id, nome, idade, sexo, cargo, salario, nascimento FROM funcionario";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                funcionarios.add(new Funcionario(
                    rs.getInt("id"),
                    rs.getString("nome"),
                    rs.getInt("idade"),
                    rs.getString("sexo"),
                    rs.getString("cargo"),
                    rs.getBigDecimal("salario"),
                    rs.getDate("nascimento")
                ));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao listar funcionários: " + e.getMessage());
        }
        return funcionarios;
    }

    public ClienteEspecial realizarSorteio() {
        List<ClienteEspecial> clientesEspeciais = new ArrayList<>();
        String sql = "SELECT * FROM clienteespecial";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                clientesEspeciais.add(new ClienteEspecial(
                    rs.getInt("id"),
                    rs.getString("nome"),
                    rs.getString("sexo"),
                    rs.getInt("idade"),
                    rs.getInt("id_cliente"),
                    rs.getBigDecimal("cashback")
                ));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao realizar sorteio: " + e.getMessage());
            return null;
        }

        if (clientesEspeciais.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Nenhum cliente especial disponível para o sorteio!");
            return null;
        }

        Random random = new Random();
        int indiceSorteado = random.nextInt(clientesEspeciais.size());
        ClienteEspecial clienteSorteado = clientesEspeciais.get(indiceSorteado);
        JOptionPane.showMessageDialog(null, "Cliente sorteado: " + clienteSorteado.getNome() + " (ID: " + clienteSorteado.getIdCliente() + ")");
        return clienteSorteado;
    }

    public void reajustarSalario(String categoria, double percentual) {
        String sql = "UPDATE funcionario SET salario = salario * (1 + ?) WHERE cargo = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDouble(1, percentual / 100);
            stmt.setString(2, categoria);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null, "Reajuste de " + percentual + "% aplicado com sucesso a " + rowsAffected + " funcionários da categoria " + categoria + "!");
            } else {
                JOptionPane.showMessageDialog(null, "Nenhum funcionário encontrado na categoria  " + categoria + "!");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao realizar reajuste: " + e.getMessage());
        }
    }

}
