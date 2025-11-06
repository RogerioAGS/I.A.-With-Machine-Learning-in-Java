import java.util.HashMap;
import java.util.Map;

/**
 * Representa um nó na Árvore de Decisão.
 * Pode ser um Nó de Teste (isLeaf = false) ou um Nó de Decisão/Folha (isLeaf = true).
 */
public class TreeNode {
    // Para nós de teste, armazena o nome do atributo (ex: "Céu").
    // Para nós folha, armazena a decisão final (ex: "Ir" ou "Ficar em Casa").
    public String attribute;
    
    // Indica se é um nó folha (Decisão) ou um nó interno (Teste).
    public boolean isLeaf;
    
    // Mapeia o valor do atributo (ex: "Ensolarado") para o próximo TreeNode.
    public Map<String, TreeNode> children;

    /**
     * Construtor para um novo nó.
     * @param attribute Nome do atributo (Teste) ou a Decisão (Folha).
     * @param isLeaf Se o nó é uma folha de decisão.
     */
    public TreeNode(String attribute, boolean isLeaf) {
        this.attribute = attribute;
        this.isLeaf = isLeaf;
        // Inicializa o mapa de filhos apenas se não for uma folha.
        if (!isLeaf) {
            this.children = new HashMap<>();
        }
    }

    /**
     * Adiciona um filho ao nó atual.
     * @param value O valor do atributo que leva ao nó filho (ex: "Nublado").
     * @param child O nó filho correspondente.
     */
    public void addChild(String value, TreeNode child) {
        if (!isLeaf && children != null) {
            this.children.put(value, child);
        }
    }
}