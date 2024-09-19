package gui;

import conexao.ConexaoBD;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MainFrame extends JFrame {

    private JTextField campoUsuario;
    private JPasswordField campoSenha;
    private JButton botaoLogin;

    public MainFrame() {
        telaLogin();  // Inicializa a tela de login
    }

    private void telaLogin() {
        setTitle("Login - Controle de Frota");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        JLabel labelUsuario = new JLabel("Usuário:");
        labelUsuario.setBounds(30, 30, 80, 25);
        add(labelUsuario);

        campoUsuario = new JTextField(20);
        campoUsuario.setBounds(100, 30, 160, 25);
        add(campoUsuario);

        JLabel labelSenha = new JLabel("Senha:");
        labelSenha.setBounds(30, 70, 80, 25);
        add(labelSenha);

        campoSenha = new JPasswordField(20);
        campoSenha.setBounds(100, 70, 160, 25);
        add(campoSenha);

        botaoLogin = new JButton("Entrar");
        botaoLogin.setBounds(100, 110, 80, 25);
        add(botaoLogin);

        botaoLogin.addActionListener(e -> autenticarUsuario());
    }

    // Correção 1: Ajuste no login padrão
    private void autenticarUsuario() {
        String usuario = campoUsuario.getText();
        String senha = new String(campoSenha.getPassword());

        // Login padrão "admin" e senha "12345"
        if (usuario.equals("admin") && senha.equals("12345")) {
            JOptionPane.showMessageDialog(null, "Login bem-sucedido!");
            abrirMenuPrincipal();
        } else {
            try {
                try (Connection conn = ConexaoBD.getConnection()) {
                    String query = "SELECT * FROM usuarios WHERE nome = ? AND senha = ?";
                    try (PreparedStatement pst = conn.prepareStatement(query)) {
                        pst.setString(1, usuario);
                        pst.setString(2, senha);
                        
                        try (ResultSet rs = pst.executeQuery()) {
                            if (rs.next()) {
                                JOptionPane.showMessageDialog(null, "Login bem-sucedido!");
                                abrirMenuPrincipal();  // Abre a tela principal
                            } else {
                                JOptionPane.showMessageDialog(null, "Usuário ou senha incorretos.");
                            }
                        }
                    }
                }
            } catch (HeadlessException | SQLException e) {
                JOptionPane.showMessageDialog(null, "Erro ao conectar com o banco de dados.");
            }
        }
    }

        private void abrirMenuPrincipal() {
        dispose();
        JFrame framePrincipal = new JFrame("Menu Principal - Controle de Frota");
        framePrincipal.setSize(500, 400);
        framePrincipal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JButton btnCadastrarUsuario = new JButton("Cadastrar Usuário");
        JButton btnCadastrarVeiculo = new JButton("Cadastrar Veículo");
        JButton btnCadastrarFornecedor = new JButton("Cadastrar Fornecedor");
        JButton btnInserirServico = new JButton("Inserir Serviço");
        JButton btnConsultarVeiculo = new JButton("Consultar Veículo");
        JButton btnExcluirDados = new JButton("Excluir Dados");
        JButton btnSair = new JButton("Sair");

        JPanel painel = new JPanel();
        painel.setLayout(new BoxLayout(painel, BoxLayout.Y_AXIS));

        painel.add(btnCadastrarUsuario);
        painel.add(btnCadastrarVeiculo);
        painel.add(btnCadastrarFornecedor);
        painel.add(btnInserirServico);
        painel.add(btnConsultarVeiculo);
        painel.add(btnExcluirDados);
        painel.add(btnSair);

        framePrincipal.add(painel);
        framePrincipal.setLocationRelativeTo(null);
        framePrincipal.setVisible(true);

        btnCadastrarUsuario.addActionListener(e -> new TelaCadastroUsuario().setVisible(true));
        btnCadastrarVeiculo.addActionListener(e -> new TelaCadastroVeiculo().setVisible(true));
        btnCadastrarFornecedor.addActionListener(e -> new TelaCadastroFornecedor().setVisible(true));
        btnInserirServico.addActionListener(e -> new TelaInsercaoServico().setVisible(true));
        btnConsultarVeiculo.addActionListener(e -> new TelaConsultaVeiculo().setVisible(true));
        btnExcluirDados.addActionListener(e -> new TelaExclusaoDados().setVisible(true));
        btnSair.addActionListener(e -> System.exit(0));
    }
    
    
        public class TelaCadastroUsuario extends JFrame {
        public TelaCadastroUsuario() {
            setTitle("Cadastro de Usuário");
            setSize(300, 200);
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setLocationRelativeTo(null);

            // Criando componentes
            JLabel labelNome = new JLabel("Nome:");
            JTextField campoNome = new JTextField(20);
            JLabel labelSenha = new JLabel("Senha:");
            JPasswordField campoSenha = new JPasswordField(20);
            JButton botaoSalvar = new JButton("Salvar");

            // Criando um painel para adicionar os componentes
            JPanel painel = new JPanel(new GridLayout(3, 2));
            painel.add(labelNome);
            painel.add(campoNome);
            painel.add(labelSenha);
            painel.add(campoSenha);
            painel.add(botaoSalvar);

            // Adicionando o painel ao frame
            add(painel);

            // Ação do botão "Salvar"
            botaoSalvar.addActionListener(e -> {
                String nome = campoNome.getText();
                String senha = new String(campoSenha.getPassword());

                // Aqui você pode adicionar a lógica para salvar o usuário no banco de dados
                JOptionPane.showMessageDialog(null, "Usuário salvo: " + nome);
            });

            // Tornar a tela visível
            setVisible(true);
        }
    }


        public class TelaCadastroVeiculo extends JFrame {
        public TelaCadastroVeiculo() {
            setTitle("Cadastro de Veículo");
            setSize(300, 200);
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setLocationRelativeTo(null);

            // Criando componentes
            JLabel labelPlaca = new JLabel("Placa:");
            JTextField campoPlaca = new JTextField(10);
            JLabel labelModelo = new JLabel("Modelo:");
            JTextField campoModelo = new JTextField(20);
            JButton botaoSalvar = new JButton("Salvar");

            // Criando um painel para adicionar os componentes
            JPanel painel = new JPanel(new GridLayout(3, 2));
            painel.add(labelPlaca);
            painel.add(campoPlaca);
            painel.add(labelModelo);
            painel.add(campoModelo);
            painel.add(botaoSalvar);

            // Adicionando o painel ao frame
            add(painel);

            // Ação do botão "Salvar"
            botaoSalvar.addActionListener(e -> {
                String placa = campoPlaca.getText();
                String modelo = campoModelo.getText();
                JOptionPane.showMessageDialog(null, "Veículo salvo: " + placa + " - " + modelo);
            });

            // Tornar a tela visível
            setVisible(true);
        }
    }

        public class TelaCadastroFornecedor extends JFrame {
        public TelaCadastroFornecedor() {
            setTitle("Cadastro de Fornecedor");
            setSize(300, 200);
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setLocationRelativeTo(null);

            // Criando componentes
            JLabel labelNome = new JLabel("Nome:");
            JTextField campoNome = new JTextField(20);
            JButton botaoSalvar = new JButton("Salvar");

            // Criando um painel para adicionar os componentes
            JPanel painel = new JPanel(new GridLayout(2, 2));
            painel.add(labelNome);
            painel.add(campoNome);
            painel.add(botaoSalvar);

            // Adicionando o painel ao frame
            add(painel);

            // Ação do botão "Salvar"
            botaoSalvar.addActionListener(e -> {
                String nome = campoNome.getText();
                JOptionPane.showMessageDialog(null, "Fornecedor salvo: " + nome);
            });

            // Tornar a tela visível
            setVisible(true);
        }
    }


        public class TelaInsercaoServico extends JFrame {
        private JComboBox<String> comboBoxVeiculo;

        public TelaInsercaoServico() {
            setTitle("Inserção de Serviço");
            setSize(300, 200);
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setLocationRelativeTo(null);

            // Criando componentes
            JLabel labelVeiculo = new JLabel("Veículo:");
            comboBoxVeiculo = new JComboBox<>();

            JLabel labelDescricao = new JLabel("Descrição:");
            JTextField campoDescricao = new JTextField(20);
            JButton botaoSalvar = new JButton("Salvar");

            // Criando um painel para adicionar os componentes
            JPanel painel = new JPanel(new GridLayout(3, 2));
            painel.add(labelVeiculo);
            painel.add(comboBoxVeiculo);
            painel.add(labelDescricao);
            painel.add(campoDescricao);
            painel.add(new JLabel()); // Espaço vazio para alinhar o botão
            painel.add(botaoSalvar);

            // Adicionando o painel ao frame
            add(painel);

            // Preenchendo o comboBox com os veículos cadastrados
            carregarVeiculos();

            // Ação do botão "Salvar"
            botaoSalvar.addActionListener(e -> {
                String veiculo = (String) comboBoxVeiculo.getSelectedItem();
                String descricao = campoDescricao.getText();
                if (veiculo == null || descricao.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Preencha todos os campos.");
                    return;
                }

                // Aqui você pode adicionar a lógica para salvar o serviço no banco de dados
                JOptionPane.showMessageDialog(null, "Serviço salvo: " + descricao + " no veículo " + veiculo);
            });

            // Tornar a tela visível
            setVisible(true);
        }

            private void carregarVeiculos() {
            try {
                try (Connection conn = ConexaoBD.getConnection()) {
                    String query = "SELECT placa FROM veiculos";
                    try (PreparedStatement pst = conn.prepareStatement(query); ResultSet rs = pst.executeQuery()) {
                        
                        while (rs.next()) {
                            comboBoxVeiculo.addItem(rs.getString("placa"));
                        }
                        
                    }
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Erro ao carregar veículos.");
            }
        }
    }

    // Correção 2: Consulta de serviços por veículo
        public class TelaConsultaVeiculo extends JFrame {
        public TelaConsultaVeiculo() {
            setTitle("Consulta de Veículo");
            setSize(300, 200);
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setLocationRelativeTo(null);

            // Criando componentes
            JLabel labelPlaca = new JLabel("Placa:");
            JTextField campoPlaca = new JTextField(10);
            JButton botaoConsultar = new JButton("Consultar");

            // Criando um painel para adicionar os componentes
            JPanel painel = new JPanel(new GridLayout(2, 2));
            painel.add(labelPlaca);
            painel.add(campoPlaca);
            painel.add(botaoConsultar);

            // Adicionando o painel ao frame
            add(painel);

            // Ação do botão "Consultar"
            botaoConsultar.addActionListener(e -> {
                String placa = campoPlaca.getText();
                consultarServicosVeiculo(placa);
            });

            // Tornar a tela visível
            setVisible(true);
        }

        // Correção 3: Exibir serviços realizados no veículo
        private void consultarServicosVeiculo(String placa) {
            try {
                try (Connection conn = ConexaoBD.getConnection()) {
                    String query = "SELECT descricao FROM servicos WHERE veiculo_placa = ?";
                    try (PreparedStatement pst = conn.prepareStatement(query)) {
                        pst.setString(1, placa);
                        try (ResultSet rs = pst.executeQuery()) {
                            StringBuilder servicos = new StringBuilder("Serviços realizados:\n");
                            while (rs.next()) {
                                servicos.append(rs.getString("descricao")).append("\n");
                            }
                            
                            JOptionPane.showMessageDialog(null, servicos.length() > 0 ? servicos.toString() : "Nenhum serviço encontrado para este veículo.");
                        }
                    }
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Erro ao consultar serviços.");
            }
        }
    }

        public class TelaExclusaoDados extends JFrame {
        private JComboBox<String> comboBoxTipo;
        private JComboBox<String> comboBoxItem;
        private JButton botaoExcluir;

        public TelaExclusaoDados() {
            setTitle("Exclusão de Dados");
            setSize(300, 200);
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setLocationRelativeTo(null);

            // Criando componentes
            JLabel labelTipo = new JLabel("Tipo de Dado:");
            String[] tiposDados = {"Usuário", "Veículo", "Fornecedor", "Serviço"};
            comboBoxTipo = new JComboBox<>(tiposDados);

            JLabel labelItem = new JLabel("Item:");
            comboBoxItem = new JComboBox<>();
            botaoExcluir = new JButton("Excluir");

            // Criando um painel para adicionar os componentes
            JPanel painel = new JPanel(new GridLayout(3, 2));
            painel.add(labelTipo);
            painel.add(comboBoxTipo);
            painel.add(labelItem);
            painel.add(comboBoxItem);
            painel.add(new JLabel()); // Espaço vazio
            painel.add(botaoExcluir);

            // Adicionando o painel ao frame
            add(painel);

            // Ação do comboBox "Tipo de Dado"
            comboBoxTipo.addActionListener(e -> carregarItens());

            // Ação do botão "Excluir"
            botaoExcluir.addActionListener(e -> {
                String tipo = (String) comboBoxTipo.getSelectedItem();
                String item = (String) comboBoxItem.getSelectedItem();
                if (item == null) {
                    JOptionPane.showMessageDialog(null, "Nenhum item selecionado.");
                    return;
                }

                // Aqui você pode adicionar a lógica para excluir o item do banco de dados
                JOptionPane.showMessageDialog(null, tipo + " excluído: " + item);
            });

            // Tornar a tela visível
            setVisible(true);
        }

        // Correção 4: Exibir itens para exclusão
        private void carregarItens() {
            String tipoSelecionado = (String) comboBoxTipo.getSelectedItem();
            comboBoxItem.removeAllItems();

            String query = "";
            String campo = "";

            switch (tipoSelecionado) {
                case "Usuário" -> {
                    query = "SELECT nome FROM usuarios";
                    campo = "nome";
                }
                case "Veículo" -> {
                    query = "SELECT placa FROM veiculos";
                    campo = "placa";
                }
                case "Fornecedor" -> {
                    query = "SELECT nome FROM fornecedores";
                    campo = "nome";
                }
                case "Serviço" -> {
                    query = "SELECT descricao FROM servicos";
                    campo = "descricao";
                }
            }

            try {
                try (Connection conn = ConexaoBD.getConnection(); PreparedStatement pst = conn.prepareStatement(query); ResultSet rs = pst.executeQuery()) {
                    
                    while (rs.next()) {
                        comboBoxItem.addItem(rs.getString(campo));
                    }
                    
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Erro ao carregar itens.");
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainFrame().setVisible(true));
    }
}
