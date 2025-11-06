import java.util.HashMap;
import java.util.Map;

/**
 * Classe TreeNode - Representa um nó na árvore de decisão
 * 
 * ESTRUTURA:
 * - Nó Interno: contém um atributo e aponta para filhos (um por valor do atributo)
 * - Nó Folha: contém a decisão final (Sim/Não para aprovar empréstimo)
 * 
 * EXEMPLO:
 *          [Histórico]           <- Nó Interno
 *         /     |      \
 *      Ruim   Neutro   Bom
 *       /       |        \
 *     [Não]   [Sim]    [Sim]    <- Nós Folha
 */
public class TreeNode {
    
    // Nome do atributo usado para dividir os dados neste nó
    // Exemplo: "Histórico", "Renda", "Emprego", "Garantia"
    private String attribute;
    
    // Valor de decisão para nós folha
    // Exemplo: "Sim" ou "Não" (aprovar ou não o empréstimo)
    private String decision;
    
    // Mapa de filhos: chave = valor do atributo, valor = próximo nó
    // Exemplo: {"Ruim" -> TreeNode1, "Neutro" -> TreeNode2, "Bom" -> TreeNode3}
    private Map<String, TreeNode> children;
    
    /**
     * Construtor para NÓ INTERNO (possui atributo e filhos)
     * Usado quando ainda há decisões a serem tomadas
     */
    public TreeNode(String attribute) {
        this.attribute = attribute;
        this.children = new HashMap<>();
        this.decision = null; // Nó interno não tem decisão
    }
    
    /**
     * Construtor para NÓ FOLHA (possui apenas decisão)
     * Usado quando chegamos a uma conclusão final
     */
    public TreeNode(String decision, boolean isLeaf) {
        this.decision = decision;
        this.attribute = null; // Nó folha não tem atributo
        this.children = null;  // Nó folha não tem filhos
    }
    
    // ========== MÉTODOS GETTERS E SETTERS ==========
    
    public String getAttribute() {
        return attribute;
    }
    
    public String getDecision() {
        return decision;
    }
    
    public Map<String, TreeNode> getChildren() {
        return children;
    }
    
    /**
     * Adiciona um nó filho para um valor específico do atributo
     * @param value Valor do atributo (ex: "Ruim", "Baixa")
     * @param child Nó filho a ser adicionado
     */
    public void addChild(String value, TreeNode child) {
        children.put(value, child);
    }
    
    /**
     * Verifica se este nó é uma folha (decisão final)
     * @return true se for folha, false caso contrário
     */
    public boolean isLeaf() {
        return decision != null;
    }
}