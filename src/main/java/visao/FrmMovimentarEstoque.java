package visao;

import dao.ProdutoDAO;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import modelo.Produto;

/**
 * Tela responsavel por adicionar e remover quantidades do estoque.
 * Unifica as antigas telas de adicionar e remover estoque em uma unica janela.
 */
public class FrmMovimentarEstoque extends JFrame {

    private JTable tabelaProdutos;
    private JTextField campoQuantidade;
    private JLabel labelProduto;
    private JButton botaoAdicionar;
    private JButton botaoRemover;
    private JButton botaoSair;

    public FrmMovimentarEstoque() {
        initComponents();
        carregaTabela();
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        tabelaProdutos = new JTable();
        campoQuantidade = new JTextField();
        labelProduto = new JLabel("Selecione um produto");
        botaoAdicionar = new JButton("Adicionar");
        botaoRemover = new JButton("Remover");
        botaoSair = new JButton("Sair");

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("Movimentar Estoque");

        tabelaProdutos.setModel(new DefaultTableModel(
                new Object[][]{},
                new String[]{"ID", "Nome", "Estoque atual", "Estoque minimo", "Estoque maximo"}
        ));

        tabelaProdutos.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                selecionarProduto();
            }
        });

        botaoAdicionar.addActionListener(evt -> movimentarEstoque(true));
        botaoRemover.addActionListener(evt -> movimentarEstoque(false));
        botaoSair.addActionListener(evt -> dispose());

        JScrollPane scroll = new JScrollPane(tabelaProdutos);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(scroll, javax.swing.GroupLayout.PREFERRED_SIZE, 520, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(labelProduto, javax.swing.GroupLayout.DEFAULT_SIZE, 230, Short.MAX_VALUE)
                                .addComponent(campoQuantidade)
                                .addComponent(botaoAdicionar)
                                .addComponent(botaoRemover)
                                .addComponent(botaoSair))
                        .addContainerGap()
        );
        layout.setVerticalGroup(
                layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(scroll, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(layout.createSequentialGroup()
                                        .addComponent(labelProduto)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(campoQuantidade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(botaoAdicionar)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(botaoRemover)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(botaoSair)))
                        .addContainerGap()
        );

        pack();
    }

    public void carregaTabela() {
        DefaultTableModel modelo = (DefaultTableModel) tabelaProdutos.getModel();
        modelo.setNumRows(0);

        ProdutoDAO dao = new ProdutoDAO();
        for (Produto produto : dao.getListaProdutos()) {
            modelo.addRow(new Object[]{
                produto.getId(),
                produto.getNome(),
                produto.getQuantidade(),
                produto.getMin(),
                produto.getMax()
            });
        }
    }

    private void selecionarProduto() {
        if (tabelaProdutos.getSelectedRow() != -1) {
            String nome = tabelaProdutos.getValueAt(tabelaProdutos.getSelectedRow(), 1).toString();
            String quantidadeAtual = tabelaProdutos.getValueAt(tabelaProdutos.getSelectedRow(), 2).toString();
            labelProduto.setText(nome + " (Atual: " + quantidadeAtual + ")");
        }
    }

    private void movimentarEstoque(boolean adicionar) {
        try {
            int linhaSelecionada = tabelaProdutos.getSelectedRow();
            if (linhaSelecionada == -1) {
                throw new Exception("Selecione um produto na tabela.");
            }

            String texto = campoQuantidade.getText().trim();
            if (texto.isEmpty()) {
                throw new NumberFormatException();
            }

            int quantidadeInformada = Integer.parseInt(texto);
            if (quantidadeInformada <= 0) {
                throw new Exception("Informe uma quantidade maior que zero.");
            }

            int estoqueAtual = Integer.parseInt(tabelaProdutos.getValueAt(linhaSelecionada, 2).toString());
            int estoqueMinimo = Integer.parseInt(tabelaProdutos.getValueAt(linhaSelecionada, 3).toString());
            int estoqueMaximo = Integer.parseInt(tabelaProdutos.getValueAt(linhaSelecionada, 4).toString());
            int novoEstoque;

            if (adicionar) {
                novoEstoque = estoqueAtual + quantidadeInformada;
                if (novoEstoque > estoqueMaximo) {
                    throw new Exception("Estoque nao pode ultrapassar o limite maximo (" + estoqueMaximo + ").");
                }
            } else {
                novoEstoque = estoqueAtual - quantidadeInformada;
                if (novoEstoque < estoqueMinimo) {
                    throw new Exception("Estoque nao pode ser menor que o minimo permitido (" + estoqueMinimo + ").");
                }
            }

            ProdutoDAO dao = new ProdutoDAO();
            Produto produto = dao.getListaProdutos().get(linhaSelecionada);
            produto.setQuantidade(novoEstoque);
            dao.atualizarProduto(produto);

            campoQuantidade.setText("");
            carregaTabela();
            selecionarProduto();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Digite um valor numerico valido.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }
}
