import java.util.Map;
import java.util.HashMap;

/**
 * Representa um Nó na Árvore de Decisão ID3.
 * Pode ser um Nó Interno (Decisão) ou um Nó Folha (Rótulo final).
 */
public class ID3Node {
    public String attributeName; // Nome do atributo testado (ex: "SO") [cite: 24]
    public String label;         // Rótulo de decisão final (ex: "Trocar") [cite: 25]
    public Map<String, ID3Node> children; // Mapeia valor do atributo para nó filho (ex: "iOS" -> Nó Filho) [cite: 26]

    /**
     * Construtor para Nó Interno (Decisão).
     */
    public ID3Node(String attributeName) {
        this.attributeName = attributeName;
        this.children = new HashMap<>(); // Inicializa o mapa de ramificações
        this.label = null;               // Não é um nó folha
    }

    /**
     * Construtor para Nó Folha (Decisão final).
     */
    public ID3Node(String label, boolean isLeaf) {
        this.label = label;
        this.attributeName = null; // Não testa atributo
        this.children = null;      // Não tem ramificações
    }
}