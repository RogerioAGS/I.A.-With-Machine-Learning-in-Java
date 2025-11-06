import java.util.HashMap;
import java.util.Map;

/**
 * Representa cada ponto de decisão ou resultado final (folha) na árvore.
 */
public class TreeNode {
    public String attribute;
    public Map<String, TreeNode> children = new HashMap<>();
    public boolean isLeaf;

    public TreeNode(String attribute, boolean isLeaf) {
        this.attribute = attribute;
        this.isLeaf = isLeaf;
    }

    public void addChild(String value, TreeNode node) {
        this.children.put(value, node);
    }
}
