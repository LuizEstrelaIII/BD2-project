package br.com.project.view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import br.com.project.dao.SellDAO;
import br.com.project.model.ClienteEspecial;
import br.com.project.model.Funcionario;
import br.com.project.model.Produto;

public class SistemaVendas {
    private JFrame frame;
    private SellDAO vendaDAO;

    public SistemaVendas() {
        vendaDAO = new SellDAO();
        initialize();
    }

    private void initialize() {
        frame = new JFrame("Sistema de Vendas");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);

        JTabbedPane tabbedPane = new JTabbedPane();

        JPanel vendasPanel = new JPanel();
        vendasPanel.setLayout(new GridLayout(4, 2));
        JLabel lblVendedor = new JLabel("Vendedor ID:");
        JTextField txtVendedor = new JTextField();
        JLabel lblCliente = new JLabel("Cliente ID:");
        JTextField txtCliente = new JTextField();
        JLabel lblProduto = new JLabel("Produto ID:");
        JTextField txtProduto = new JTextField();
        JButton btnRegistrar = new JButton("Registrar Venda");
        btnRegistrar.addActionListener(e -> {
            try {
                int idVendedor = Integer.parseInt(txtVendedor.getText());
                int idCliente = Integer.parseInt(txtCliente.getText());
                int idProduto = Integer.parseInt(txtProduto.getText());
                vendaDAO.registrarVenda(idVendedor, idCliente, idProduto);
                txtVendedor.setText("");
                txtCliente.setText("");
                txtProduto.setText("");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Por favor, insira IDs válidos.");
            }
        });
        vendasPanel.add(lblVendedor);
        vendasPanel.add(txtVendedor);
        vendasPanel.add(lblCliente);
        vendasPanel.add(txtCliente);
        vendasPanel.add(lblProduto);
        vendasPanel.add(txtProduto);
        vendasPanel.add(new JLabel());
        vendasPanel.add(btnRegistrar);
        tabbedPane.addTab("Vendas", vendasPanel);

        JPanel estatisticasPanel = new JPanel();
        estatisticasPanel.setLayout(new BorderLayout());
        JTextArea txtEstatisticas = new JTextArea();
        txtEstatisticas.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(txtEstatisticas);
        JButton btnAtualizarEstatisticas = new JButton("Atualizar Estatísticas");
        btnAtualizarEstatisticas.addActionListener(e -> {
            txtEstatisticas.setText("");
            List<String> produtoMaisVendido = vendaDAO.getProdutoMaisVendido();
            List<String> produtoMenosVendido = vendaDAO.getProdutoMenosVendido();
            List<String> valorGanhoMaisVendido = vendaDAO.getValorGanhoProdutoMaisVendido();
            List<String> valorGanhoMenosVendido = vendaDAO.getValorGanhoProdutoMenosVendido();
            List<String> mesesVendasMenosVendido = vendaDAO.getMesesVendasProdutoMenosVendido();
            List<String> vendedorMaisVendido = vendaDAO.getVendedorProdutoMaisVendido();
            List<String> clientesMaisVendas = vendaDAO.getClientesComMaisVendas();

            txtEstatisticas.append("=== Produto Mais Vendido ===\n");
            for (String item : produtoMaisVendido) {
                txtEstatisticas.append(item + "\n");
            }
            txtEstatisticas.append("\n=== Produto Menos Vendido ===\n");
            for (String item : produtoMenosVendido) {
                txtEstatisticas.append(item + "\n");
            }
            txtEstatisticas.append("\n=== Valor Ganho com Produto Mais Vendido ===\n");
            for (String item : valorGanhoMaisVendido) {
                txtEstatisticas.append(item + "\n");
            }
            txtEstatisticas.append("\n=== Valor Ganho com Produto Menos Vendido ===\n");
            for (String item : valorGanhoMenosVendido) {
                txtEstatisticas.append(item + "\n");
            }
            txtEstatisticas.append("\n=== Meses de Vendas do Produto Menos Vendido ===\n");
            for (String item : mesesVendasMenosVendido) {
                txtEstatisticas.append(item + "\n");
            }
            txtEstatisticas.append("\n=== Vendedor do Produto Mais Vendido ===\n");
            for (String item : vendedorMaisVendido) {
                txtEstatisticas.append(item + "\n");
            }
            txtEstatisticas.append("\n=== Clientes com Mais Vendas ===\n");
            for (String item : clientesMaisVendas) {
                txtEstatisticas.append(item + "\n");
            }
        });
        estatisticasPanel.add(scrollPane, BorderLayout.CENTER);
        estatisticasPanel.add(btnAtualizarEstatisticas, BorderLayout.SOUTH);
        tabbedPane.addTab("Estatísticas", estatisticasPanel);

        JPanel cadastrarProdutoPanel = new JPanel();
        cadastrarProdutoPanel.setLayout(new GridLayout(6, 2));
        JLabel lblNomeProduto = new JLabel("Nome:");
        JTextField txtNomeProduto = new JTextField();
        JLabel lblQuantidadeProduto = new JLabel("Quantidade:");
        JTextField txtQuantidadeProduto = new JTextField();
        JLabel lblDescricaoProduto = new JLabel("Descrição:");
        JTextField txtDescricaoProduto = new JTextField();
        JLabel lblValorProduto = new JLabel("Valor:");
        JTextField txtValorProduto = new JTextField();
        JButton btnCadastrarProduto = new JButton("Cadastrar Produto");
        btnCadastrarProduto.addActionListener(e -> {
            try {
                String nome = txtNomeProduto.getText();
                int quantidade = Integer.parseInt(txtQuantidadeProduto.getText());
                String descricao = txtDescricaoProduto.getText();
                BigDecimal valor = new BigDecimal(txtValorProduto.getText());
                vendaDAO.cadastroProdutos(nome, quantidade, descricao, valor);
                txtNomeProduto.setText("");
                txtQuantidadeProduto.setText("");
                txtDescricaoProduto.setText("");
                txtValorProduto.setText("");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Quantidade e Valor devem ser números válidos.");
            }
        });
        cadastrarProdutoPanel.add(lblNomeProduto);
        cadastrarProdutoPanel.add(txtNomeProduto);
        cadastrarProdutoPanel.add(lblQuantidadeProduto);
        cadastrarProdutoPanel.add(txtQuantidadeProduto);
        cadastrarProdutoPanel.add(lblDescricaoProduto);
        cadastrarProdutoPanel.add(txtDescricaoProduto);
        cadastrarProdutoPanel.add(lblValorProduto);
        cadastrarProdutoPanel.add(txtValorProduto);
        cadastrarProdutoPanel.add(new JLabel());
        cadastrarProdutoPanel.add(btnCadastrarProduto);
        tabbedPane.addTab("Cadastrar Produto", cadastrarProdutoPanel);

        JPanel cadastrarClientePanel = new JPanel();
        cadastrarClientePanel.setLayout(new GridLayout(5, 2));
        JLabel lblNomeCliente = new JLabel("Nome:");
        JTextField txtNomeCliente = new JTextField();
        JLabel lblSexoCliente = new JLabel("Sexo (m/f/o):");
        JTextField txtSexoCliente = new JTextField();
        JLabel lblIdadeCliente = new JLabel("Idade:");
        JTextField txtIdadeCliente = new JTextField();
        JLabel lblNascimentoCliente = new JLabel("Nascimento (yyyy-MM-dd):");
        JTextField txtNascimentoCliente = new JTextField();
        JButton btnCadastrarCliente = new JButton("Cadastrar Cliente");
        btnCadastrarCliente.addActionListener(e -> {
            try {
                String nome = txtNomeCliente.getText();
                String sexo = txtSexoCliente.getText();
                if (!sexo.matches("[mfo]")) {
                    JOptionPane.showMessageDialog(frame, "Sexo deve ser 'm', 'f' ou 'o'.");
                    return;
                }
                int idade = Integer.parseInt(txtIdadeCliente.getText());
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date nascimentoUtil = sdf.parse(txtNascimentoCliente.getText());
                java.sql.Date nascimentoSql = new java.sql.Date(nascimentoUtil.getTime());
                vendaDAO.cadastroCliente(nome, sexo, idade, nascimentoSql);
                txtNomeCliente.setText("");
                txtSexoCliente.setText("");
                txtIdadeCliente.setText("");
                txtNascimentoCliente.setText("");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Idade deve ser um número válido.");
            } catch (ParseException ex) {
                JOptionPane.showMessageDialog(frame, "Data inválida. Use o formato yyyy-MM-dd.");
            }
        });
        cadastrarClientePanel.add(lblNomeCliente);
        cadastrarClientePanel.add(txtNomeCliente);
        cadastrarClientePanel.add(lblSexoCliente);
        cadastrarClientePanel.add(txtSexoCliente);
        cadastrarClientePanel.add(lblIdadeCliente);
        cadastrarClientePanel.add(txtIdadeCliente);
        cadastrarClientePanel.add(lblNascimentoCliente);
        cadastrarClientePanel.add(txtNascimentoCliente);
        cadastrarClientePanel.add(new JLabel());
        cadastrarClientePanel.add(btnCadastrarCliente);
        tabbedPane.addTab("Cadastrar Cliente", cadastrarClientePanel);

     
        JPanel sorteioPanel = new JPanel();
        sorteioPanel.setLayout(new BorderLayout());
        JTextArea txtSorteio = new JTextArea();
        txtSorteio.setEditable(false);
        JScrollPane scrollSorteio = new JScrollPane(txtSorteio);
        JButton btnRealizarSorteio = new JButton("Realizar Sorteio");
        btnRealizarSorteio.addActionListener(e -> {
            txtSorteio.setText("");
            ClienteEspecial clienteSorteado = vendaDAO.realizarSorteio();
            if (clienteSorteado != null) {
                txtSorteio.append("Cliente Sorteado:\n");
                txtSorteio.append("ID: " + clienteSorteado.getIdCliente() + "\n");
                txtSorteio.append("Nome: " + clienteSorteado.getNome() + "\n");
                txtSorteio.append("Cashback: " + clienteSorteado.getCashback() + "\n");
            }
        });
        sorteioPanel.add(scrollSorteio, BorderLayout.CENTER);
        sorteioPanel.add(btnRealizarSorteio, BorderLayout.SOUTH);
        tabbedPane.addTab("Sorteio", sorteioPanel);

     
        JPanel reajustePanel = new JPanel();
        reajustePanel.setLayout(new GridLayout(3, 2));
        JLabel lblCategoria = new JLabel("Categoria:");
        JTextField txtCategoria = new JTextField();
        JLabel lblPercentual = new JLabel("Percentual (%):");
        JTextField txtPercentual = new JTextField();
        JButton btnReajustar = new JButton("Aplicar Reajuste");
        btnReajustar.addActionListener(e -> {
            try {
                String categoria = txtCategoria.getText();
                double percentual = Double.parseDouble(txtPercentual.getText());
                if (percentual < 0) {
                    JOptionPane.showMessageDialog(frame, "O percentual deve ser um número positivo!");
                    return;
                }
                vendaDAO.reajustarSalario(categoria, percentual);
                txtCategoria.setText("");
                txtPercentual.setText("");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "O percentual deve ser um número válido!");
            }
        });
        reajustePanel.add(lblCategoria);
        reajustePanel.add(txtCategoria);
        reajustePanel.add(lblPercentual);
        reajustePanel.add(txtPercentual);
        reajustePanel.add(new JLabel());
        reajustePanel.add(btnReajustar);
        tabbedPane.addTab("Reajuste", reajustePanel);

     
        JPanel listarFuncionariosPanel = new JPanel();
        listarFuncionariosPanel.setLayout(new BorderLayout());
        JTextArea txtFuncionarios = new JTextArea();
        txtFuncionarios.setEditable(false);
        JScrollPane scrollFuncionarios = new JScrollPane(txtFuncionarios);
        JButton btnAtualizarFuncionarios = new JButton("Atualizar Lista de Funcionários");
        btnAtualizarFuncionarios.addActionListener(e -> {
            txtFuncionarios.setText("");
            List<Funcionario> funcionarios = vendaDAO.listarFuncionarios();
            for (Funcionario funcionario : funcionarios) {
                txtFuncionarios.append("ID: " + funcionario.getId() + ", Nome: " + funcionario.getNome() +
                                      ", Salário: " + funcionario.getSalario() + "\n");
            }
        });
        listarFuncionariosPanel.add(scrollFuncionarios, BorderLayout.CENTER);
        listarFuncionariosPanel.add(btnAtualizarFuncionarios, BorderLayout.SOUTH);
        tabbedPane.addTab("Listar Funcionários", listarFuncionariosPanel);

   
        JPanel listarProdutosPanel = new JPanel();
        listarProdutosPanel.setLayout(new BorderLayout());
        JTextArea txtProdutos = new JTextArea();
        txtProdutos.setEditable(false);
        JScrollPane scrollProdutos = new JScrollPane(txtProdutos);
        JButton btnAtualizarProdutos = new JButton("Atualizar Lista de Produtos");
        btnAtualizarProdutos.addActionListener(e -> {
            txtProdutos.setText("");
            List<Produto> produtos = vendaDAO.listarProdutos();
            for (Produto produto : produtos) {
                txtProdutos.append("Nome: " + produto.getNome() + ", Quantidade: " + produto.getQuantidade() + "\n");
            }
        });
        listarProdutosPanel.add(scrollProdutos, BorderLayout.CENTER);
        listarProdutosPanel.add(btnAtualizarProdutos, BorderLayout.SOUTH);
        tabbedPane.addTab("Listar Produtos", listarProdutosPanel);

        JPanel gerenciarBancoPanel = new JPanel();
        gerenciarBancoPanel.setLayout(new GridLayout(2, 1));
        JButton btnDeletarBanco = new JButton("Deletar Banco de Dados");
        JButton btnCriarBanco = new JButton("Criar Banco de Dados");
        btnDeletarBanco.addActionListener(e -> vendaDAO.deletarBancoDados());
        btnCriarBanco.addActionListener(e -> vendaDAO.criarBancoDados());
        gerenciarBancoPanel.add(btnDeletarBanco);
        gerenciarBancoPanel.add(btnCriarBanco);
        tabbedPane.addTab("Gerenciar Banco", gerenciarBancoPanel);

        frame.add(tabbedPane);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(SistemaVendas::new);
    }
}
