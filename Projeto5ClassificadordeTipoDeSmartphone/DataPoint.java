import java.util.List;

/**
 * Representa uma única instância de dados (um smartphone) no dataset.
 * Contém a lista de features e o rótulo de destino (Trocar/Não Trocar).
 */
public class DataPoint {
    // 0: SO, 1: Tela, 2: Câmera, 3: Preço [cite: 12]
    public List<String> features; 
    public String targetLabel;     // Rótulo de destino [cite: 14]

    /**
     * Construtor para inicializar o ponto de dados.
     */
    public DataPoint(List<String> features, String targetLabel) {
        this.features = features;
        this.targetLabel = targetLabel;
    }

    /** Retorna o valor da feature em um índice específico. [cite: 19] */
    public String getFeature(int index) { 
        return features.get(index); 
    }
    
    /** Retorna o rótulo de destino. [cite: 20] */
    public String getTargetLabel() { 
        return targetLabel; 
    }
}