import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;
import java.util.Comparator;

/**
 * Classe ID3Algorithm - Implementa o algoritmo ID3 para construir árvores de decisão
 * 
 * O ALGORITMO ID3 (Iterative Dichotomiser 3):
 * 1. Calcula a ENTROPIA do conjunto de dados (mede a "desordem")
 * 2. Para cada atributo, calcula o GANHO DE INFORMAÇÃO
 * 3. Escolhe o atributo com MAIOR ganho como raiz
 * 4. Divide os dados por esse atributo e REPETE recursivamente
 * 
 * FÓRMULAS PRINCIPAIS:
 * - Entropia: H(S) = -Σ(p * log2(p)) onde p é a proporção de cada classe
 * - Ganho: Gain(S,A) = H(S) - Σ((|Sv|/|S|) * H(Sv))
 */
public class ID3Algorithm {
    
    // ========== CONFIGURAÇÃO DO PROJETO ==========
    
    // Atributo alvo (o que queremos prever)
    public static final String TARGET_ATTRIBUTE = "Aprovar";
    
    // Lista de atributos preditores (características para análise)
    public static final List<String> ATTRIBUTES = List.of(
        "Histórico",  // Histórico de crédito: Ruim, Neutro, Bom
        "Renda",      // Nível de renda: Baixa, Média, Alta
        "Emprego",    // Estabilidade: Instável, Estável
        "Garantia"    // Possui garantia: Sim, Não
    );
    
    // ========== DATASET COMPLETO (14 INSTÂNCIAS) ==========
    
    /**
     * Conjunto de dados de treinamento
     * Cada Map representa uma instância (linha da tabela)
     * com pares chave-valor: {"Atributo" -> "Valor"}
     */
    public static final List<Map<String, String>> DATA = List.of(
        // ID 1: Histórico Ruim, Renda Baixa, Emprego Instável, Sem Garantia -> NÃO Aprovar
        Map.of("Histórico", "Ruim", "Renda", "Baixa", "Emprego", "Instável", "Garantia", "Não", TARGET_ATTRIBUTE, "Não"),
        
        // ID 2: Histórico Ruim, Renda Baixa, Emprego Instável, Com Garantia -> NÃO Aprovar
        Map.of("Histórico", "Ruim", "Renda", "Baixa", "Emprego", "Instável", "Garantia", "Sim", TARGET_ATTRIBUTE, "Não"),
        
        // ID 3: Histórico Neutro, Renda Alta, Emprego Estável, Sem Garantia -> Aprovar
        Map.of("Histórico", "Neutro", "Renda", "Alta", "Emprego", "Estável", "Garantia", "Não", TARGET_ATTRIBUTE, "Sim"),
        
        // ID 4: Histórico Bom, Renda Média, Emprego Estável, Sem Garantia -> Aprovar
        Map.of("Histórico", "Bom", "Renda", "Média", "Emprego", "Estável", "Garantia", "Não", TARGET_ATTRIBUTE, "Sim"),
        
        // ID 5: Histórico Bom, Renda Alta, Emprego Estável, Sem Garantia -> Aprovar
        Map.of("Histórico", "Bom", "Renda", "Alta", "Emprego", "Estável", "Garantia", "Não", TARGET_ATTRIBUTE, "Sim"),
        
        // ID 6: Histórico Bom, Renda Alta, Emprego Estável, Com Garantia -> NÃO Aprovar
        Map.of("Histórico", "Bom", "Renda", "Alta", "Emprego", "Estável", "Garantia", "Sim", TARGET_ATTRIBUTE, "Não"),
        
        // ID 7: Histórico Neutro, Renda Baixa, Emprego Instável, Com Garantia -> Aprovar
        Map.of("Histórico", "Neutro", "Renda", "Baixa", "Emprego", "Instável", "Garantia", "Sim", TARGET_ATTRIBUTE, "Sim"),
        
        // ID 8: Histórico Ruim, Renda Média, Emprego Instável, Sem Garantia -> NÃO Aprovar
        Map.of("Histórico", "Ruim", "Renda", "Média", "Emprego", "Instável", "Garantia", "Não", TARGET_ATTRIBUTE, "Não"),
        
        // ID 9: Histórico Ruim, Renda Alta, Emprego Estável, Sem Garantia -> Aprovar
        Map.of("Histórico", "Ruim", "Renda", "Alta", "Emprego", "Estável", "Garantia", "Não", TARGET_ATTRIBUTE, "Sim"),
        
        // ID 10: Histórico Bom, Renda Média, Emprego Estável, Sem Garantia -> Aprovar
        Map.of("Histórico", "Bom", "Renda", "Média", "Emprego", "Estável", "Garantia", "Não", TARGET_ATTRIBUTE, "Sim"),
        
        // ID 11: Histórico Ruim, Renda Média, Emprego Estável, Com Garantia -> Aprovar
        Map.of("Histórico", "Ruim", "Renda", "Média", "Emprego", "Estável", "Garantia", "Sim", TARGET_ATTRIBUTE, "Sim"),
        
        // ID 12: Histórico Neutro, Renda Média, Emprego Instável, Com Garantia -> Aprovar
        Map.of("Histórico", "Neutro", "Renda", "Média", "Emprego", "Instável", "Garantia", "Sim", TARGET_ATTRIBUTE, "Sim"),
        
        // ID 13: Histórico Neutro, Renda Alta, Emprego Estável, Sem Garantia -> Aprovar
        Map.of("Histórico", "Neutro", "Renda", "Alta", "Emprego", "Estável", "Garantia", "Não", TARGET_ATTRIBUTE, "Sim"),
        
        // ID 14: Histórico Bom, Renda Baixa, Emprego Instável, Com Garantia -> NÃO Aprovar
        Map.of("Histórico", "Bom", "Renda", "Baixa", "Emprego", "Instável", "Garantia", "Sim", TARGET_ATTRIBUTE, "Não")
    );
    
    // ========== MÉTODO PRINCIPAL ==========
    
    /**
     * Método main - Executa o algoritmo completo
     * PASSOS:
     * 1. Valida o dataset
     * 2. Constrói a árvore de decisão
     * 3. Imprime a árvore
     * 4. Testa com novos casos
     */
    public static void main(String[] args) {
        System.out.println("=".repeat(80));
        System.out.println("   ALGORITMO ID3 - CLASSIFICADOR DE APROVAÇÃO DE EMPRÉSTIMOS");
        System.out.println("=".repeat(80));
        
        // PASSO 1: Validar Dataset
        System.out.println("\n[1] VALIDAÇÃO DO DATASET");
        System.out.println("Total de instâncias: " + DATA.size());
        System.out.println("Atributos preditores: " + ATTRIBUTES);
        System.out.println("Atributo alvo: " + TARGET_ATTRIBUTE);
        
        // Contar distribuição de classes
        long aprovados = DATA.stream()
            .filter(instance -> instance.get(TARGET_ATTRIBUTE).equals("Sim"))
            .count();
        long rejeitados = DATA.size() - aprovados;
        System.out.println("Distribuição de classes: " + aprovados + " Aprovados, " + rejeitados + " Rejeitados");
        
        // PASSO 2: Construir Árvore de Decisão
        System.out.println("\n[2] CONSTRUÇÃO DA ÁRVORE DE DECISÃO");
        System.out.println("Iniciando algoritmo ID3...\n");
        
        TreeNode decisionTree = buildTree(DATA, new ArrayList<>(ATTRIBUTES), TARGET_ATTRIBUTE);
        
        System.out.println("Árvore construída com sucesso!");
        
        // PASSO 3: Imprimir Árvore
        System.out.println("\n[3] ESTRUTURA DA ÁRVORE DE DECISÃO");
        System.out.println("-".repeat(80));
        printTree(decisionTree, "");
        System.out.println("-".repeat(80));
        
        // PASSO 4: Testar Classificação
        System.out.println("\n[4] TESTES DE CLASSIFICAÇÃO");
        System.out.println("-".repeat(80));
        
        // Teste 1: Caso positivo claro
        Map<String, String> teste1 = Map.of(
            "Histórico", "Bom",
            "Renda", "Alta",
            "Emprego", "Estável",
            "Garantia", "Não"
        );
        String resultado1 = classify(decisionTree, teste1);
        System.out.println("TESTE 1:");
        System.out.println("  Perfil: " + teste1);
        System.out.println("  Decisão: " + resultado1 + " ✓");
        
        // Teste 2: Caso negativo claro
        Map<String, String> teste2 = Map.of(
            "Histórico", "Ruim",
            "Renda", "Baixa",
            "Emprego", "Instável",
            "Garantia", "Não"
        );
        String resultado2 = classify(decisionTree, teste2);
        System.out.println("\nTESTE 2:");
        System.out.println("  Perfil: " + teste2);
        System.out.println("  Decisão: " + resultado2 + " ✓");
        
        // Teste 3: Caso intermediário
        Map<String, String> teste3 = Map.of(
            "Histórico", "Neutro",
            "Renda", "Média",
            "Emprego", "Estável",
            "Garantia", "Sim"
        );
        String resultado3 = classify(decisionTree, teste3);
        System.out.println("\nTESTE 3:");
        System.out.println("  Perfil: " + teste3);
        System.out.println("  Decisão: " + resultado3 + " ✓");
        
        System.out.println("-".repeat(80));
        
        // Estatísticas Finais
        System.out.println("\n[5] ESTATÍSTICAS FINAIS");
        double entropiaInicial = calculateEntropy(DATA, TARGET_ATTRIBUTE);
        System.out.printf("Entropia inicial do dataset: %.4f\n", entropiaInicial);
        
        // Calcular ganho de cada atributo
        System.out.println("\nGanho de Informação por Atributo:");
        for (String attr : ATTRIBUTES) {
            double ganho = calculateGain(DATA, attr, TARGET_ATTRIBUTE);
            System.out.printf("  %s: %.4f\n", attr, ganho);
        }
        
        System.out.println("\n" + "=".repeat(80));
        System.out.println("   EXECUÇÃO CONCLUÍDA COM SUCESSO!");
        System.out.println("=".repeat(80));
    }

    private static double calculateGain(List<Map<String,String>> data2, String attr, String targetAttribute) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'calculateGain'");
    }

    private static double calculateEntropy(List<Map<String,String>> data2, String targetAttribute) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'calculateEntropy'");
    }

    private static String classify(TreeNode decisionTree, Map<String,String> teste1) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'classify'");
    }

    private static void printTree(TreeNode decisionTree, String string) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'printTree'");
    }

    private static TreeNode buildTree(List<Map<String,String>> data2, ArrayList arrayList, String targetAttribute) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'buildTree'");
    }
}