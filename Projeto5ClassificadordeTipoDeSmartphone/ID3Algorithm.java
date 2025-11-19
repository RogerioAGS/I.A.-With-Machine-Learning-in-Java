import java.util.*;

/**
 * Implementa o algoritmo ID3 para construir a Árvore de Decisão.
 * O código usa diretamente os métodos estáticos de ID3Metrics.
 */
public class ID3Algorithm { // CORREÇÃO: Não precisa estender ID3Metrics

    // Nomes dos atributos para referência e mapeamento (índice -> nome)
    private static final String[] ATTRIBUTE_NAMES = {"SO", "Tela", "Câmera", "Preço"};

    /** Encontra o índice do atributo que oferece o maior Ganho de Informação. */
    private static int findBestAttribute(List<DataPoint> dataset, Set<Integer> availableIndices) {
        double maxGain = -1.0;
        int bestIndex = -1;

        for (int index : availableIndices) {
            // CORREÇÃO: Chamada direta ao método estático em ID3Metrics
            double gain = ID3Metrics.calculateGain(dataset, index); 
            if (gain > maxGain) {
                maxGain = gain;
                bestIndex = index;
            }
        }
        return bestIndex;
    }

    /** Encontra o rótulo majoritário (usado para nós folha impuros). */
    private static String getMajorityLabel(List<DataPoint> dataset) {
        // CORREÇÃO: Chamada direta ao método estático em ID3Metrics
        Map<String, Long> counts = ID3Metrics.countLabels(dataset); 
        String majorityLabel = null;
        long maxCount = -1;

        for (Map.Entry<String, Long> entry : counts.entrySet()) {
            if (entry.getValue() > maxCount) {
                maxCount = entry.getValue();
                majorityLabel = entry.getKey();
            }
        }
        return majorityLabel;
    }

    /** Checa se o dataset é puro (todos os rótulos iguais). */
    private static boolean isPure(List<DataPoint> dataset) {
        if (dataset.isEmpty()) return true;
        String firstLabel = dataset.get(0).getTargetLabel();
        for (DataPoint dp : dataset) {
            if (!dp.getTargetLabel().equals(firstLabel)) return false;
        }
        return true;
    }

    /**
     * Constrói a Árvore de Decisão ID3 recursivamente.
     */
    public static ID3Node buildTree(List<DataPoint> dataset, Set<Integer> availableIndices) {
        
        // CONDIÇÃO DE PARADA 1: Puro ou Vazio (folha pura)
        if (isPure(dataset) || dataset.isEmpty()) {
            String label = dataset.isEmpty() ? "Não Definido" : dataset.get(0).getTargetLabel();
            return new ID3Node(label, true); 
        }

        // CONDIÇÃO DE PARADA 2: Sem Atributos disponíveis (folha impura)
        if (availableIndices.isEmpty()) {
            return new ID3Node(getMajorityLabel(dataset), true);
        }

        // PASSO RECURSIVO: Encontra e usa o melhor atributo
        int bestAttributeIndex = findBestAttribute(dataset, availableIndices);
        
        // Caso de ganho zero para todos, usa a maioria
        if (bestAttributeIndex == -1) { 
             return new ID3Node(getMajorityLabel(dataset), true);
        }

        String bestAttributeName = ATTRIBUTE_NAMES[bestAttributeIndex];
        ID3Node root = new ID3Node(bestAttributeName); 

        // Remove o atributo selecionado para a próxima chamada
        Set<Integer> remainingIndices = new HashSet<>(availableIndices);
        remainingIndices.remove(bestAttributeIndex);

        // Encontra valores únicos para criar as ramificações (filhos)
        Set<String> uniqueValues = new HashSet<>();
        for (DataPoint dp : dataset) {
            uniqueValues.add(dp.getFeature(bestAttributeIndex));
        }

        // Cria nós filhos para cada valor único
        for (String value : uniqueValues) {
            // CORREÇÃO: Chamada direta ao método estático em ID3Metrics
            List<DataPoint> subset = ID3Metrics.filterDataset(dataset, bestAttributeIndex, value); 
            ID3Node childNode = buildTree(subset, remainingIndices); // Chamada recursiva
            root.children.put(value, childNode);
        }

        return root;
    }
}