import java.util.*;
import static java.lang.Math.*;

/**
 * Classe utilitária para calcular as métricas do algoritmo ID3:
 * Entropia e Ganho de Informação.
 */
@SuppressWarnings("unused")
public class ID3Metrics {

    /** Auxiliar: Implementa log na base 2, tratando log(0) como 0 (para evitar NaN na entropia). */
    public static double log2(double x) {
        return (x <= 0) ? 0.0 : Math.log(x) / Math.log(2);
    }

    /** 2.1. Contagem de Rótulos: Conta a frequência de cada classe. */
    public static Map<String, Long> countLabels(List<DataPoint> dataset) {
        Map<String, Long> counts = new HashMap<>();
        for (DataPoint dp : dataset) {
            counts.put(dp.getTargetLabel(), counts.getOrDefault(dp.getTargetLabel(), 0L) + 1);
        }
        return counts;
    }

    /** 2.2. Cálculo da Entropia: Mede a impureza de um conjunto de dados S. */
    public static double calculateEntropy(List<DataPoint> dataset) {
        double entropy = 0.0;
        double total = dataset.size();
        if (total == 0) return 0.0;

        Map<String, Long> counts = countLabels(dataset);

        // Fórmula da Entropia: E(S) = - sum(p_i * log2(p_i))
        for (Long count : counts.values()) {
            double p = count / total; // Proporção da classe i
            entropy -= p * log2(p);
        }
        return entropy;
    }

    /** 2.3. Cálculo do Ganho de Informação: Ganho(A) = E(S) - E_ponderada(A) */
    public static double calculateGain(List<DataPoint> dataset, int attributeIndex) {
        double initialEntropy = calculateEntropy(dataset);
        double total = dataset.size();
        double weightedEntropy = 0.0;

        // 1. Encontrar valores únicos para ramificação
        Set<String> uniqueValues = new HashSet<>();
        for (DataPoint dp : dataset) {
            uniqueValues.add(dp.getFeature(attributeIndex));
        }

        // 2. Calcular a Entropia Ponderada
        for (String value : uniqueValues) {
            // Chamada ao método de filtragem de dados
            List<DataPoint> subset = filterDataset(dataset, attributeIndex, value);
            
            if (!subset.isEmpty()) {
                double subsetWeight = subset.size() / total; // |Sv| / |S|
                double subsetEntropy = calculateEntropy(subset); // Chamada recursiva da Entropia
                weightedEntropy += subsetWeight * subsetEntropy; // Soma ponderada
            }
        }

        return initialEntropy - weightedEntropy; // Ganho
    }

    /** Auxiliar: Filtra o dataset para criar um subconjunto de dados. 
     * Mudamos o modificador de acesso para 'public' para ser acessível 
     * diretamente em ID3Algorithm e SmartphoneClassifier, mantendo o design modular.
     */
    public static List<DataPoint> filterDataset(
            List<DataPoint> dataset,
            int attributeIndex,
            String value) {

        List<DataPoint> filteredDataset = new ArrayList<>();
        for (DataPoint dp : dataset) {
            if (dp.getFeature(attributeIndex).equals(value)) {
                filteredDataset.add(dp);
            }
        }
        return filteredDataset;
    }
}