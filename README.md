# I.A.-With-Machine-Learning-in-Java
# Programação Oracle - Java Inteligência artificial com Machine Learning

# Projeto 1 - Projeto: Classificador de Aprovação de Empréstimo Pessoal
# Dataset Utilizado (14 Instâncias)
# O objetivo é classificar se o empréstimo deve ser Aprovado (Sim/Não) com base em quatro atributos de risco:

<img width="366" height="772" alt="Captura de tela 2025-11-05 194922" src="https://github.com/user-attachments/assets/b6ccb9d0-9155-45e4-9871-abca8d4c0697" />

Variáveis Globais do Projeto:
TARGET_ATTRIBUTE: "Aprovar"
ATTRIBUTES: Histórico, Renda, Emprego, Garantia

# Exercício 1: Setup e Representação de Dados
# Foco: Criação de classes e organização do dataset.
# Código de Início (Setup do Projeto)
# O código da classe TreeNode.java é o mesmo (pois a estrutura de árvore não muda).
ID3Algorithm.java (Início):
Java
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;
import java.lang.Math;
import java.util.Comparator;

public class ID3Algorithm {
    
    // VARIÁVEIS GLOBAIS DE SETUP
    public static final String TARGET_ATTRIBUTE = "Aprovar";
    
    // Lista de atributos
    public static final List<String> ATTRIBUTES = List.of("Histórico", "Renda", "Emprego", "Garantia");

    // Conjunto de dados (Dataset) - Deve ser preenchido no Exercício 1
    public static final List<Map<String, String>> DATA = new ArrayList<>();

    public static void main(String[] args) {
        // ------------------ EXERCÍCIO 1: PREENCHER O DATASET AQUI ------------------
        
        // Exemplo: DATA.add(Map.of("Histórico", "Ruim", "Renda", "Baixa", "Emprego", "Instável", "Garantia", "Não", TARGET_ATTRIBUTE, "Não"));

        System.out.println("Setup concluído! Total de Instâncias: " + DATA.size());
    }

    // MÉTODOS A SEREM IMPLEMENTADOS
    public static double calculateEntropy(List<Map<String, String>> data, String targetAttribute) { return 0.0; }
    // ... (assinaturas dos métodos do Ex. 3 e Des. 1/2)
    public static double calculateGain(List<Map<String, String>> data, String attribute, String targetAttribute) { return 0.0; }
    public static TreeNode buildTree(List<Map<String, String>> data, List<String> availableAttributes, String targetAttribute) { return null; }
    public static void printTree(TreeNode node, String prefix) { /* Implementar no Desafio 1 */ }
    public static String classify(TreeNode node, Map<String, String> instance) { return null; }
}


# Código Esperado (Exercício 1)
ID3Algorithm.java (Trecho de DATA preenchido):
Java
// ... (imports)

public class ID3Algorithm {
    // ... (TARGET_ATTRIBUTE e ATTRIBUTES)

    // Conjunto de dados (Dataset) - 14 Instâncias
    public static final List<Map<String, String>> DATA = List.of(
        Map.of("Histórico", "Ruim", "Renda", "Baixa", "Emprego", "Instável", "Garantia", "Não", TARGET_ATTRIBUTE, "Não"),
        Map.of("Histórico", "Ruim", "Renda", "Baixa", "Emprego", "Instável", "Garantia", "Sim", TARGET_ATTRIBUTE, "Não"),
        Map.of("Histórico", "Neutro", "Renda", "Alta", "Emprego", "Estável", "Garantia", "Não", TARGET_ATTRIBUTE, "Sim"),
        Map.of("Histórico", "Bom", "Renda", "Média", "Emprego", "Estável", "Garantia", "Não", TARGET_ATTRIBUTE, "Sim"),
        Map.of("Histórico", "Bom", "Renda", "Alta", "Emprego", "Estável", "Garantia", "Não", TARGET_ATTRIBUTE, "Sim"),
        Map.of("Histórico", "Bom", "Renda", "Alta", "Emprego", "Estável", "Garantia", "Sim", TARGET_ATTRIBUTE, "Não"),
        Map.of("Histórico", "Neutro", "Renda", "Baixa", "Emprego", "Instável", "Garantia", "Sim", TARGET_ATTRIBUTE, "Sim"),
        Map.of("Histórico", "Ruim", "Renda", "Média", "Emprego", "Instável", "Garantia", "Não", TARGET_ATTRIBUTE, "Não"),
        Map.of("Histórico", "Ruim", "Renda", "Alta", "Emprego", "Estável", "Garantia", "Não", TARGET_ATTRIBUTE, "Sim"),
        Map.of("Histórico", "Bom", "Renda", "Média", "Emprego", "Estável", "Garantia", "Não", TARGET_ATTRIBUTE, "Sim"),
        Map.of("Histórico", "Ruim", "Renda", "Média", "Emprego", "Estável", "Garantia", "Sim", TARGET_ATTRIBUTE, "Sim"),
        Map.of("Histórico", "Neutro", "Renda", "Média", "Emprego", "Instável", "Garantia", "Sim", TARGET_ATTRIBUTE, "Sim"),
        Map.of("Histórico", "Neutro", "Renda", "Alta", "Emprego", "Estável", "Garantia", "Não", TARGET_ATTRIBUTE, "Sim"),
        Map.of("Histórico", "Bom", "Renda", "Baixa", "Emprego", "Instável", "Garantia", "Sim", TARGET_ATTRIBUTE, "Não")
    );

    public static void main(String[] args) {
        System.out.println("Setup concluído! Total de Instâncias: " + DATA.size()); // Saída: 14
        // ...
    }
    // ...
}

# Exercício 2: O Coração Matemático – Entropia
# Foco: Implementar a fórmula da Entropia (calculateEntropy).
# Código Esperado (Exercício 2)
# ID3Algorithm.java (Método preenchido e Teste no main):
Java
// ... (imports)

public class ID3Algorithm {
    // ... (DATA e variáveis globais)

    public static void main(String[] args) {
        // ... (Verificação de setup do Ex. 1)

        // Teste do Exercício 2
        double initialEntropy = calculateEntropy(DATA, TARGET_ATTRIBUTE);
        System.out.printf("\nEntropia Inicial (total): %.4f\n", initialEntropy);
    }
    
    /**
     * Calcula a Entropia de um conjunto de dados.
     */
    public static double calculateEntropy(List<Map<String, String>> data, String targetAttribute) {
        if (data.isEmpty()) { return 0.0; }

        Map<String, Long> countByClass = data.stream()
            .collect(Collectors.groupingBy(instance -> instance.get(targetAttribute), Collectors.counting()));

        double entropy = 0.0;
        int totalInstances = data.size();

        for (Long count : countByClass.values()) {
            double probability = (double) count / totalInstances;
            if (probability > 0) {
                // Fórmula: -p * log2(p)
                entropy -= probability * (Math.log(probability) / Math.log(2)); 
            }
        }
        return entropy;
    }
    // ... (Outros métodos)
}

# Saída Esperada (Exercício 2):
 Total de Sim: 8
 Total de Não: 6
 Entropia: $\frac{8}{14}\cdot\log_2(\frac{14}{8}) + \frac{6}{14}\cdot\log_2(\frac{14}{6}) \approx 0.9852$
 Entropia Inicial (total): 0.9852

# Desafio 1: Visualização da Lógica (Testando Recursão)
# Foco: Implementar o método recursivo de percurso em árvore (printTree).
# Código Esperado (Desafio 1)
# ID3Algorithm.java (Método preenchido e Teste no main):
Java
// ... (imports, calculateEntropy, etc.)

public class ID3Algorithm {
    // ... (DATA, calculateEntropy, etc.)

    public static void main(String[] args) {
        // ... (Teste do Ex. 2)
        
        // ------------------ DESAFIO 1: TESTE DA FUNÇÃO RECURSIVA printTree ------------------
        
        // Cria uma micro-árvore manual para teste (Ex: Histórico -> Renda)
        TreeNode rootTest = new TreeNode("Histórico", false);
        TreeNode ruimTest = new TreeNode("Não", true);
        TreeNode neutroTest = new TreeNode("Renda", false);
        TreeNode bomTest = new TreeNode("Emprego", false);

        rootTest.addChild("Ruim", ruimTest);
        rootTest.addChild("Neutro", neutroTest);
        rootTest.addChild("Bom", bomTest);

        neutroTest.addChild("Alta", new TreeNode("Sim", true));
        neutroTest.addChild("Baixa", new TreeNode("Sim", true));
        bomTest.addChild("Estável", new TreeNode("Sim", true));

        System.out.println("\n--- Teste de Percurso em Árvore ---");
        printTree(rootTest, "");
    }
    
    /**
     * Imprime a Árvore de Decisão usando um Percurso em Pré-Ordem.
     */
    public static void printTree(TreeNode node, String prefix) {
        if (node.isLeaf) {
            System.out.println(prefix + "-> DECISÃO: " + node.attribute);
            return;
        }

        System.out.println(prefix + "-> TESTE: " + node.attribute + "?");

        for (Map.Entry<String, TreeNode> entry : node.children.entrySet()) {
            String attributeValue = entry.getKey();
            TreeNode childNode = entry.getValue();

            // Novo prefixo para mostrar o caminho tomado com recuo (indentação)
            String newPrefix = prefix + "   [Se " + node.attribute + " é " + attributeValue + "] ";
            printTree(childNode, newPrefix);
        }
    }
    // ... (Outros métodos)
}

# Saída Esperada (Desafio 1):
--- Teste de Percurso em Árvore ---
-> TESTE: Histórico?
   [Se Histórico é Ruim] -> DECISÃO: Não
   [Se Histórico é Neutro] -> TESTE: Renda?
      [Se Renda é Alta] -> DECISÃO: Sim
      [Se Renda é Baixa] -> DECISÃO: Sim
   [Se Histórico é Bom] -> TESTE: Emprego?
      [Se Emprego é Estável] -> DECISÃO: Sim

#Exercício 3: Seleção do Atributo e Construção Recursiva
#Foco: Implementar Ganho (calculateGain) e o algoritmo ID3 (buildTree).
#Código Esperado (Exercício 3)
#ID3Algorithm.java (Métodos preenchidos e Teste no main):
Java
// ... (imports e métodos anteriores)

public class ID3Algorithm {
    // ... (DATA, calculateEntropy, printTree, etc.)

    /**
     * Calcula o Ganho de Informação para um atributo.
     */
    public static double calculateGain(List<Map<String, String>> data, String attribute, String targetAttribute) {
        double totalEntropy = calculateEntropy(data, targetAttribute);
        int totalInstances = data.size();

        Map<String, List<Map<String, String>>> splitData = data.stream()
            .collect(Collectors.groupingBy(instance -> instance.get(attribute)));

        double weightedEntropy = 0.0;
        for (List<Map<String, String>> subset : splitData.values()) {
            double probability = (double) subset.size() / totalInstances;
            weightedEntropy += probability * calculateEntropy(subset, targetAttribute);
        }

        return totalEntropy - weightedEntropy;
    }

    /**
     * Algoritmo Recursivo ID3 para construir a Árvore de Decisão.
     */
    public static TreeNode buildTree(List<Map<String, String>> data, List<String> availableAttributes, String targetAttribute) {
        // Caso Base 1: Todos os exemplos são da mesma classe (Nó Folha puro)
        String firstClass = data.get(0).get(targetAttribute);
        if (data.stream().allMatch(instance -> instance.get(targetAttribute).equals(firstClass))) {
            return new TreeNode(firstClass, true);
        }

        // Caso Base 2: Não há mais atributos (ou não há dados). Retorna a classe majoritária.
        if (availableAttributes.isEmpty() || data.isEmpty()) {
            String majorityClass = data.stream()
                .collect(Collectors.groupingBy(instance -> instance.get(targetAttribute), Collectors.counting()))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(firstClass); 
            return new TreeNode(majorityClass, true);
        }

        // 1. Encontrar o atributo com o MAIOR Ganho
        String bestAttribute = availableAttributes.stream()
            .max(Comparator.comparingDouble(attribute -> calculateGain(data, attribute, targetAttribute)))
            .orElseThrow(() -> new IllegalStateException("Não foi possível encontrar o melhor atributo."));

        // 2. Criar o nó raiz para o melhor atributo
        TreeNode root = new TreeNode(bestAttribute, false);
        
        // 3. Remover o melhor atributo
        List<String> remainingAttributes = new ArrayList<>(availableAttributes);
        remainingAttributes.remove(bestAttribute);
        
        // 4. Chamada Recursiva para cada valor do melhor atributo
        Map<String, List<Map<String, String>>> splitData = data.stream()
            .collect(Collectors.groupingBy(instance -> instance.get(bestAttribute)));

        for (Map.Entry<String, List<Map<String, String>>> entry : splitData.entrySet()) {
            String attributeValue = entry.getKey();
            List<Map<String, String>> subset = entry.getValue();

            TreeNode childNode = buildTree(subset, remainingAttributes, targetAttribute);
            root.addChild(attributeValue, childNode);
        }

        return root;
    }

    public static void main(String[] args) {
        // ... (Teste do Ex. 2)

        // ------------------ EXERCÍCIO 3: CONSTRUÇÃO E IMPRESSÃO DA ÁRVORE ------------------

        System.out.println("\n--- Construção da Árvore ID3 ---");
        TreeNode decisionTree = buildTree(DATA, ATTRIBUTES, TARGET_ATTRIBUTE);
        printTree(decisionTree, ""); // Usa o Desafio 1 para visualizar o resultado!
    }
}


# Saída Esperada (Exercício 3 - Lógica da Árvore):
A árvore mais provável construída com este dataset começa com Histórico.
--- Construção da Árvore ID3 ---
-> TESTE: Histórico?
   [Se Histórico é Ruim] -> TESTE: Renda?
      [Se Renda é Baixa] -> DECISÃO: Não
      [Se Renda é Média] -> TESTE: Emprego?
         [Se Emprego é Instável] -> DECISÃO: Não
         [Se Emprego é Estável] -> DECISÃO: Sim
      [Se Renda é Alta] -> DECISÃO: Sim
   [Se Histórico é Neutro] -> DECISÃO: Sim
   [Se Histórico é Bom] -> TESTE: Garantia?
      [Se Garantia é Não] -> DECISÃO: Sim
      [Se Garantia é Sim] -> DECISÃO: Não



# Desafio 2: Generalização e Avaliação do Modelo
Foco: Implementar a classificação (classify) e calcular a acurácia.
Código Esperado (Desafio 2)
ID3Algorithm.java (Métodos preenchidos e Teste no main):
Java
// ... (imports e todos os métodos anteriores)

public class ID3Algorithm {
    // ... (todos os métodos de Entropia, Ganho, buildTree, printTree)

    /**
     * Usa a Árvore de Decisão para classificar uma nova instância.
     */
    public static String classify(TreeNode node, Map<String, String> instance) {
        if (node.isLeaf) {
            return node.attribute;
        }

        String attributeValue = instance.get(node.attribute);

        if (node.children.containsKey(attributeValue)) {
            return classify(node.children.get(attributeValue), instance);
        } else {
            System.err.println("Aviso: Valor não visto ('" + attributeValue + "') no atributo '" + node.attribute + "'. Não foi possível classificar.");
            return "DESCONHECIDO"; 
        }
    }

    public static void main(String[] args) {
        // ... (Construção da Árvore do Ex. 3)

        // ------------------ DESAFIO 2: TESTE DE INFERÊNCIA E ACURÁCIA ------------------

        // Conjunto de dados de TESTE (Instâncias NUNCA VISTAS)
        List<Map<String, String>> TEST_DATA = List.of(
            // 1. Histórico: Ruim, Renda: Média, Emprego: Estável, Garantia: Não (Esperado: Sim)
            Map.of("Histórico", "Ruim", "Renda", "Média", "Emprego", "Estável", "Garantia", "Não", TARGET_ATTRIBUTE, "Sim"), 
            // 2. Histórico: Neutro, Renda: Baixa, Emprego: Estável, Garantia: Não (Esperado: Sim - Por Histórico=Neutro)
            Map.of("Histórico", "Neutro", "Renda", "Baixa", "Emprego", "Estável", "Garantia", "Não", TARGET_ATTRIBUTE, "Sim"), 
            // 3. Histórico: Bom, Renda: Alta, Emprego: Instável, Garantia: Sim (Esperado: Não)
            Map.of("Histórico", "Bom", "Renda", "Alta", "Emprego", "Instável", "Garantia", "Sim", TARGET_ATTRIBUTE, "Não")
        );
        
        int correctPredictions = 0;
        
        System.out.println("\n--- Avaliação do Modelo ---");

        for (Map<String, String> testInstance : TEST_DATA) {
            String actualClass = testInstance.get(TARGET_ATTRIBUTE);
            
            Map<String, String> instanceToClassify = new HashMap<>(testInstance);
            instanceToClassify.remove(TARGET_ATTRIBUTE); 
            
            String prediction = classify(decisionTree, instanceToClassify);
            
            if (prediction.equals(actualClass)) {
                correctPredictions++;
                System.out.print("✅ ");
            } else {
                System.out.print("❌ ");
            }
            System.out.println("Previsto: " + prediction + " | Real: " + actualClass + " | Dados: " + instanceToClassify);
        }

        double accuracy = (double) correctPredictions / TEST_DATA.size() * 100;
        System.out.printf("\nACURÁCIA (Precisão) do Modelo: %d/%d = %.2f%%\n", 
                          correctPredictions, TEST_DATA.size(), accuracy);
    }
}


# Saída Esperada (Desafio 2 - Teste de Acurácia):
--- Avaliação do Modelo ---
✅ Previsto: Sim | Real: Sim | Dados: {Histórico=Ruim, Renda=Média, Emprego=Estável, Garantia=Não}
✅ Previsto: Sim | Real: Sim | Dados: {Histórico=Neutro, Renda=Baixa, Emprego=Estável, Garantia=Não}
✅ Previsto: Não | Real: Não | Dados: {Histórico=Bom, Renda=Alta, Emprego=Instável, Garantia=Sim}

# ACURÁCIA (Precisão) do Modelo: 3/3 = 100.00%

# Resposta Seção 1 - Projeto: Classificador de Aprovação de Empréstimo Pessoal

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

#Seção 1 - Projeto: Recomendador de Filmes/Séries (ID3 em Java)
#Dataset Utilizado (14 Instâncias)
#O objetivo é classificar se o usuário deve Assistir (Sim/Não) a um conteúdo, baseado em quatro características comuns:

<img width="431" height="813" alt="Captura de tela 2025-11-05 202117" src="https://github.com/user-attachments/assets/419684d1-fe40-42de-9305-2b7586d361c9" />

Variáveis Globais do Projeto:
TARGET_ATTRIBUTE: "Assistir"
ATTRIBUTES: Gênero, Duração, Avaliação, Atores

#Exercício 1: Setup e Representação de Dados
#Foco: Criação de classes (TreeNode.java é idêntica) e carregamento dos dados.
#Código Esperado (Exercício 1)
ID3Algorithm.java (Trecho de DATA preenchido):
Java
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;
import java.lang.Math;
import java.util.Comparator;

public class ID3Algorithm {
    
    // VARIÁVEIS GLOBAIS DE SETUP
    public static final String TARGET_ATTRIBUTE = "Assistir";
    public static final List<String> ATTRIBUTES = List.of("Gênero", "Duração", "Avaliação", "Atores");

    // Conjunto de dados (Dataset) - 14 Instâncias
    public static final List<Map<String, String>> DATA = List.of(
        Map.of("Gênero", "Ação", "Duração", "Curta", "Avaliação", "Baixa", "Atores", "Desconhecidos", TARGET_ATTRIBUTE, "Não"),
        Map.of("Gênero", "Ação", "Duração", "Curta", "Avaliação", "Baixa", "Atores", "Conhecidos", TARGET_ATTRIBUTE, "Não"),
        Map.of("Gênero", "Comédia", "Duração", "Média", "Avaliação", "Alta", "Atores", "Desconhecidos", TARGET_ATTRIBUTE, "Sim"),
        Map.of("Gênero", "Drama", "Duração", "Longa", "Avaliação", "Média", "Atores", "Desconhecidos", TARGET_ATTRIBUTE, "Sim"),
        Map.of("Gênero", "Drama", "Duração", "Média", "Avaliação", "Alta", "Atores", "Desconhecidos", TARGET_ATTRIBUTE, "Sim"),
        Map.of("Gênero", "Drama", "Duração", "Média", "Avaliação", "Alta", "Atores", "Conhecidos", TARGET_ATTRIBUTE, "Não"),
        Map.of("Gênero", "Sci-Fi", "Duração", "Longa", "Avaliação", "Baixa", "Atores", "Conhecidos", TARGET_ATTRIBUTE, "Sim"),
        Map.of("Gênero", "Ação", "Duração", "Média", "Avaliação", "Média", "Atores", "Desconhecidos", TARGET_ATTRIBUTE, "Não"),
        Map.of("Gênero", "Ação", "Duração", "Alta", "Avaliação", "Alta", "Atores", "Conhecidos", TARGET_ATTRIBUTE, "Sim"),
        Map.of("Gênero", "Drama", "Duração", "Média", "Avaliação", "Média", "Atores", "Desconhecidos", TARGET_ATTRIBUTE, "Sim"),
        Map.of("Gênero", "Ação", "Duração", "Longa", "Avaliação", "Média", "Atores", "Conhecidos", TARGET_ATTRIBUTE, "Sim"),
        Map.of("Gênero", "Comédia", "Duração", "Longa", "Avaliação", "Média", "Atores", "Conhecidos", TARGET_ATTRIBUTE, "Sim"),
        Map.of("Gênero", "Comédia", "Duração", "Média", "Avaliação", "Alta", "Atores", "Desconhecidos", TARGET_ATTRIBUTE, "Sim"),
        Map.of("Gênero", "Drama", "Duração", "Curta", "Avaliação", "Baixa", "Atores", "Conhecidos", TARGET_ATTRIBUTE, "Não")
    );

    public static void main(String[] args) {
        System.out.println("Setup concluído! Total de Instâncias: " + DATA.size());
    }

    // MÉTODOS A SEREM IMPLEMENTADOS
    public static double calculateEntropy(List<Map<String, String>> data, String targetAttribute) { return 0.0; }
    public static double calculateGain(List<Map<String, String>> data, String attribute, String targetAttribute) { return 0.0; }
    public static TreeNode buildTree(List<Map<String, String>> data, List<String> availableAttributes, String targetAttribute) { return null; }
    public static void printTree(TreeNode node, String prefix) { /* ... */ }
    public static String classify(TreeNode node, Map<String, String> instance) { return null; }
}



# Exercício 2: O Coração Matemático – Entropia
# Código Esperado (Exercício 2)
# A lógica do calculateEntropy é idêntica à do projeto anterior, focando apenas no novo TARGET_ATTRIBUTE ("Assistir").
Java
// ... (código anterior)

    public static void main(String[] args) {
        // ... (Verificação de setup do Ex. 1)

        double initialEntropy = calculateEntropy(DATA, TARGET_ATTRIBUTE);
        System.out.printf("\nEntropia Inicial (total): %.4f\n", initialEntropy);
    }
    
    public static double calculateEntropy(List<Map<String, String>> data, String targetAttribute) {
        if (data.isEmpty()) { return 0.0; }
        // Lógica de contagem e aplicação da fórmula: -p * log2(p)
        Map<String, Long> countByClass = data.stream()
            .collect(Collectors.groupingBy(instance -> instance.get(targetAttribute), Collectors.counting()));
        double entropy = 0.0;
        int totalInstances = data.size();
        for (Long count : countByClass.values()) {
            double probability = (double) count / totalInstances;
            if (probability > 0) {
                entropy -= probability * (Math.log(probability) / Math.log(2)); 
            }
        }
        return entropy;
    }

// ... (código posterior)

Saída Esperada (Exercício 2):
(9 Sim / 5 Não) $\approx 0.9403$
Entropia Inicial (total): 0.9403

# Desafio 1: Visualização da Lógica (Testando Recursão)
# Código Esperado (Desafio 1)
#A  lógica de teste é a mesma, mas os rótulos de exemplo refletem o novo projeto.
Java
// ... (código anterior)

    public static void main(String[] args) {
        // ... (Testes do Ex. 2)
        
        // ------------------ DESAFIO 1: TESTE DA FUNÇÃO RECURSIVA printTree ------------------
        
        // Cria uma micro-árvore manual para teste (Ex: Teste por Gênero -> Avaliação)
        TreeNode rootTest = new TreeNode("Gênero", false);
        TreeNode acaoTest = new TreeNode("Avaliação", false);
        TreeNode comediaTest = new TreeNode("Sim", true);
        TreeNode dramaTest = new TreeNode("Atores", false);

        rootTest.addChild("Ação", acaoTest);
        rootTest.addChild("Comédia", comediaTest);
        rootTest.addChild("Drama", dramaTest);

        acaoTest.addChild("Alta", new TreeNode("Sim", true));
        acaoTest.addChild("Baixa", new TreeNode("Não", true));
        dramaTest.addChild("Desconhecidos", new TreeNode("Sim", true));

        System.out.println("\n--- Teste de Percurso em Árvore ---");
        printTree(rootTest, "");
    }
    
    public static void printTree(TreeNode node, String prefix) {
        // Lógica recursiva: Caso Base (Folha) e Passo Recursivo (Nó de Teste)
        if (node.isLeaf) {
            System.out.println(prefix + "-> DECISÃO: " + node.attribute);
            return;
        }
        System.out.println(prefix + "-> TESTE: " + node.attribute + "?");
        for (Map.Entry<String, TreeNode> entry : node.children.entrySet()) {
            String attributeValue = entry.getKey();
            TreeNode childNode = entry.getValue();
            String newPrefix = prefix + "   [Se " + node.attribute + " é " + attributeValue + "] ";
            printTree(childNode, newPrefix);
        }
    }
// ...


# Saída Esperada (Desafio 1):
--- Teste de Percurso em Árvore ---
-> TESTE: Gênero?
   [Se Gênero é Ação] -> TESTE: Avaliação?
      [Se Avaliação é Alta] -> DECISÃO: Sim
      [Se Avaliação é Baixa] -> DECISÃO: Não
   [Se Gênero é Comédia] -> DECISÃO: Sim
   [Se Gênero é Drama] -> TESTE: Atores?
      [Se Atores é Desconhecidos] -> DECISÃO: Sim



# Exercício 3: Seleção do Atributo e Construção Recursiva
# Código Esperado (Exercício 3)
# Os métodos calculateGain e buildTree são exatamente os mesmos do projeto anterior em termos de lógica, utilizando o calculateEntropy recém-criado.
Java
// ... (código anterior)

    public static void main(String[] args) {
        // ... (Testes anteriores)

        // ------------------ EXERCÍCIO 3: CONSTRUÇÃO E IMPRESSÃO DA ÁRVORE ------------------
        System.out.println("\n--- Construção da Árvore ID3 ---");
        TreeNode decisionTree = buildTree(DATA, ATTRIBUTES, TARGET_ATTRIBUTE);
        printTree(decisionTree, ""); 
    }

    public static double calculateGain(List<Map<String, String>> data, String attribute, String targetAttribute) {
        // Lógica de Ganho (Ganho = Entropia Total - Entropia Ponderada)
        double totalEntropy = calculateEntropy(data, targetAttribute);
        int totalInstances = data.size();
        Map<String, List<Map<String, String>>> splitData = data.stream()
            .collect(Collectors.groupingBy(instance -> instance.get(attribute)));
        double weightedEntropy = 0.0;
        for (List<Map<String, String>> subset : splitData.values()) {
            double probability = (double) subset.size() / totalInstances;
            weightedEntropy += probability * calculateEntropy(subset, targetAttribute);
        }
        return totalEntropy - weightedEntropy;
    }

    public static TreeNode buildTree(List<Map<String, String>> data, List<String> availableAttributes, String targetAttribute) {
        // Lógica ID3: Casos Base, Encontrar Maior Ganho (chamando calculateGain), e Recursão
        if (data.stream().allMatch(instance -> instance.get(targetAttribute).equals(data.get(0).get(targetAttribute)))) {
            return new TreeNode(data.get(0).get(targetAttribute), true);
        }
        if (availableAttributes.isEmpty() || data.isEmpty()) {
            // Retorna a classe majoritária
            String majorityClass = data.stream().collect(Collectors.groupingBy(instance -> instance.get(targetAttribute), Collectors.counting()))
                .entrySet().stream().max(Map.Entry.comparingByValue()).map(Map.Entry::getKey).orElse("Não");
            return new TreeNode(majorityClass, true);
        }

        String bestAttribute = availableAttributes.stream()
            .max(Comparator.comparingDouble(attribute -> calculateGain(data, attribute, targetAttribute)))
            .orElseThrow(() -> new IllegalStateException("Erro: Não foi possível encontrar o melhor atributo."));

        TreeNode root = new TreeNode(bestAttribute, false);
        List<String> remainingAttributes = new ArrayList<>(availableAttributes);
        remainingAttributes.remove(bestAttribute);
        
        Map<String, List<Map<String, String>>> splitData = data.stream()
            .collect(Collectors.groupingBy(instance -> instance.get(bestAttribute)));

        for (Map.Entry<String, List<Map<String, String>>> entry : splitData.entrySet()) {
            root.addChild(entry.getKey(), buildTree(entry.getValue(), remainingAttributes, targetAttribute));
        }

        return root;
    }
}


# Saída Esperada (Exercício 3 - Lógica da Árvore):
A árvore mais provável construída com este dataset começa com Avaliação.
--- Construção da Árvore ID3 ---
-> TESTE: Avaliação?
   [Se Avaliação é Baixa] -> TESTE: Atores?
      [Se Atores é Desconhecidos] -> DECISÃO: Não
      [Se Atores é Conhecidos] -> TESTE: Gênero?
         [Se Gênero é Ação] -> DECISÃO: Não
         [Se Gênero é Sci-Fi] -> DECISÃO: Sim
         [Se Gênero é Drama] -> DECISÃO: Não
   [Se Avaliação é Média] -> DECISÃO: Sim
   [Se Avaliação é Alta] -> TESTE: Atores?
      [Se Atores é Desconhecidos] -> DECISÃO: Sim
      [Se Atores é Conhecidos] -> DECISÃO: Não



# Desafio 2: Generalização e Avaliação do Modelo
# Código Esperado (Desafio 2 - Final do Projeto)
# Java
// ... (código anterior)

    public static void main(String[] args) {
        // ... (Construção da Árvore do Ex. 3)
        TreeNode decisionTree = buildTree(DATA, ATTRIBUTES, TARGET_ATTRIBUTE);
        // ... (printTree)

        // ------------------ DESAFIO 2: TESTE DE INFERÊNCIA E ACURÁCIA ------------------

        // Dados de TESTE (Instâncias NUNCA VISTAS)
        List<Map<String, String>> TEST_DATA = List.of(
            // 1. Gênero: Ação, Duração: Média, Avaliação: Baixa, Atores: Conhecidos (Esperado: Não)
            Map.of("Gênero", "Ação", "Duração", "Média", "Avaliação", "Baixa", "Atores", "Conhecidos", TARGET_ATTRIBUTE, "Não"), 
            // 2. Gênero: Comédia, Duração: Longa, Avaliação: Alta, Atores: Desconhecidos (Esperado: Sim)
            Map.of("Gênero", "Comédia", "Duração", "Longa", "Avaliação", "Alta", "Atores", "Desconhecidos", TARGET_ATTRIBUTE, "Sim"), 
            // 3. Gênero: Drama, Duração: Curta, Avaliação: Média, Atores: Desconhecidos (Esperado: Sim)
            Map.of("Gênero", "Drama", "Duração", "Curta", "Avaliação", "Média", "Atores", "Desconhecidos", TARGET_ATTRIBUTE, "Sim")
        );
        
        int correctPredictions = 0;
        
        System.out.println("\n--- Avaliação do Modelo (Testando Recomendações) ---");

        for (Map<String, String> testInstance : TEST_DATA) {
            String actualClass = testInstance.get(TARGET_ATTRIBUTE);
            Map<String, String> instanceToClassify = new HashMap<>(testInstance);
            instanceToClassify.remove(TARGET_ATTRIBUTE); 
            
            String prediction = classify(decisionTree, instanceToClassify);
            
            if (prediction.equals(actualClass)) {
                correctPredictions++;
                System.out.print("✅ ");
            } else {
                System.out.print("❌ ");
            }
            System.out.println("Previsto: " + prediction + " | Real: " + actualClass);
        }

        double accuracy = (double) correctPredictions / TEST_DATA.size() * 100;
        System.out.printf("\nACURÁCIA (Precisão) do Modelo: %d/%d = %.2f%%\n", 
                          correctPredictions, TEST_DATA.size(), accuracy);
    }

    public static String classify(TreeNode node, Map<String, String> instance) {
        // Lógica de Classificação: Percorre a árvore recursivamente
        if (node.isLeaf) {
            return node.attribute;
        }
        String attributeValue = instance.get(node.attribute);
        if (node.children.containsKey(attributeValue)) {
            return classify(node.children.get(attributeValue), instance);
        } else {
            System.err.println("Aviso: Valor não visto ('" + attributeValue + "') no atributo '" + node.attribute + "'. Não foi possível classificar.");
            return "DESCONHECIDO"; 
        }
    }
}


# Saída Esperada (Desafio 2 - Teste de Acurácia):
--- Avaliação do Modelo (Testando Recomendações) ---
✅ Previsto: Não | Real: Não
✅ Previsto: Sim | Real: Sim
✅ Previsto: Sim | Real: Sim

ACURÁCIA (Precisão) do Modelo: 3/3 = 100.00%

# Resposta do Projeto 2 da Seção 1: Projeto: Recomendador de Filmes/Séries (ID3 em Java).

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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;
import java.lang.Math;
import java.util.Comparator;

/**
 * ============================================================================
 * PROJETO: CLASSIFICADOR DE APROVAÇÃO DE EMPRÉSTIMO PESSOAL
 * Algoritmo ID3 (Iterative Dichotomiser 3) para Árvore de Decisão
 * ============================================================================
 * 
 * OBJETIVO:
 * Construir uma árvore de decisão que classifica se um empréstimo deve ser
 * aprovado (Sim/Não) com base em 4 atributos de risco do solicitante.
 * 
 * ATRIBUTOS PREDITORES:
 * 1. Histórico: Ruim, Neutro, Bom
 * 2. Renda: Baixa, Média, Alta
 * 3. Emprego: Instável, Estável
 * 4. Garantia: Não, Sim
 * 
 * ATRIBUTO ALVO:
 * - Aprovar: Sim, Não
 * 
 * ALGORITMO ID3:
 * 1. Calcula a entropia do conjunto de dados
 * 2. Para cada atributo, calcula o ganho de informação
 * 3. Escolhe o atributo com maior ganho como raiz
 * 4. Divide os dados por esse atributo
 * 5. Aplica recursivamente para cada subconjunto
 * 
 * ============================================================================
 * 
 * @author Rogerio
 * @version 1.0
 */

public class ID3Algorithm {
    
    // VARIÁVEIS GLOBAIS DE SETUP (Etapa 2)
    public static final String TARGET_ATTRIBUTE = "Assistir";
    public static final List<String> ATTRIBUTES = List.of("Gênero", "Duração", "Avaliação", "Atores");

    // Conjunto de dados (Dataset) - 14 Instâncias (Etapa 2)
    public static final List<Map<String, String>> DATA = List.of(
        Map.of("Gênero", "Ação", "Duração", "Curta", "Avaliação", "Baixa", "Atores", "Desconhecidos", TARGET_ATTRIBUTE, "Não"),
        Map.of("Gênero", "Ação", "Duração", "Curta", "Avaliação", "Baixa", "Atores", "Conhecidos", TARGET_ATTRIBUTE, "Não"),
        Map.of("Gênero", "Comédia", "Duração", "Média", "Avaliação", "Alta", "Atores", "Desconhecidos", TARGET_ATTRIBUTE, "Sim"),
        Map.of("Gênero", "Drama", "Duração", "Longa", "Avaliação", "Média", "Atores", "Desconhecidos", TARGET_ATTRIBUTE, "Sim"),
        Map.of("Gênero", "Drama", "Duração", "Média", "Avaliação", "Alta", "Atores", "Desconhecidos", TARGET_ATTRIBUTE, "Sim"),
        Map.of("Gênero", "Drama", "Duração", "Média", "Avaliação", "Alta", "Atores", "Conhecidos", TARGET_ATTRIBUTE, "Não"),
        Map.of("Gênero", "Sci-Fi", "Duração", "Longa", "Avaliação", "Baixa", "Atores", "Conhecidos", TARGET_ATTRIBUTE, "Sim"),
        Map.of("Gênero", "Ação", "Duração", "Média", "Avaliação", "Média", "Atores", "Desconhecidos", TARGET_ATTRIBUTE, "Não"),
        Map.of("Gênero", "Ação", "Duração", "Alta", "Avaliação", "Alta", "Atores", "Conhecidos", TARGET_ATTRIBUTE, "Sim"),
        Map.of("Gênero", "Drama", "Duração", "Média", "Avaliação", "Média", "Atores", "Desconhecidos", TARGET_ATTRIBUTE, "Sim"),
        Map.of("Gênero", "Ação", "Duração", "Longa", "Avaliação", "Média", "Atores", "Conhecidos", TARGET_ATTRIBUTE, "Sim"),
        Map.of("Gênero", "Comédia", "Duração", "Longa", "Avaliação", "Média", "Atores", "Conhecidos", TARGET_ATTRIBUTE, "Sim"),
        Map.of("Gênero", "Comédia", "Duração", "Média", "Avaliação", "Alta", "Atores", "Desconhecidos", TARGET_ATTRIBUTE, "Sim"),
        Map.of("Gênero", "Drama", "Duração", "Curta", "Avaliação", "Baixa", "Atores", "Conhecidos", TARGET_ATTRIBUTE, "Não")
    );

    /**
     * Calcula a Entropia de um conjunto de dados. (Etapa 3)
     */
    public static double calculateEntropy(List<Map<String, String>> data, String targetAttribute) {
        if (data.isEmpty()) { return 0.0; }
        Map<String, Long> countByClass = data.stream()
            .collect(Collectors.groupingBy(instance -> instance.get(targetAttribute), Collectors.counting()));
        double entropy = 0.0;
        int totalInstances = data.size();
        for (Long count : countByClass.values()) {
            double probability = (double) count / totalInstances;
            if (probability > 0) {
                entropy -= probability * (Math.log(probability) / Math.log(2));
            }
        }
        return entropy;
    }

    /**
     * Calcula o Ganho de Informação para um atributo. (Etapa 4)
     */
    public static double calculateGain(List<Map<String, String>> data, String attribute, String targetAttribute) {
        double totalEntropy = calculateEntropy(data, targetAttribute);
        int totalInstances = data.size();

        Map<String, List<Map<String, String>>> splitData = data.stream()
            .collect(Collectors.groupingBy(instance -> instance.get(attribute)));

        double weightedEntropy = 0.0;
        for (List<Map<String, String>> subset : splitData.values()) {
            double probability = (double) subset.size() / totalInstances;
            weightedEntropy += probability * calculateEntropy(subset, targetAttribute);
        }

        return totalEntropy - weightedEntropy;
    }

    /**
     * Algoritmo Recursivo ID3 para construir a Árvore de Decisão. (Etapa 4)
     */
    public static TreeNode buildTree(List<Map<String, String>> data, List<String> availableAttributes, String targetAttribute) {
        // Caso Base 1 (Pureza)
        String firstClass = data.get(0).get(targetAttribute);
        if (data.stream().allMatch(instance -> instance.get(targetAttribute).equals(firstClass))) {
            return new TreeNode(firstClass, true);
        }

        // Caso Base 2 (Parada)
        if (availableAttributes.isEmpty() || data.isEmpty()) {
            String majorityClass = data.stream()
                .collect(Collectors.groupingBy(instance -> instance.get(targetAttribute), Collectors.counting()))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(firstClass);
            return new TreeNode(majorityClass, true);
        }

        // 1. Encontrar o atributo com o MAIOR Ganho
        String bestAttribute = availableAttributes.stream()
            .max(Comparator.comparingDouble(attribute -> calculateGain(data, attribute, targetAttribute)))
            .orElseThrow(() -> new IllegalStateException("Erro: Não foi possível encontrar o melhor atributo."));

        // 2. Criar o nó raiz e 3. Remover o atributo
        TreeNode root = new TreeNode(bestAttribute, false);
        List<String> remainingAttributes = new ArrayList<>(availableAttributes);
        remainingAttributes.remove(bestAttribute);
        
        // 4. Chamada Recursiva para cada valor
        Map<String, List<Map<String, String>>> splitData = data.stream()
            .collect(Collectors.groupingBy(instance -> instance.get(bestAttribute)));

        for (Map.Entry<String, List<Map<String, String>>> entry : splitData.entrySet()) {
            TreeNode childNode = buildTree(entry.getValue(), remainingAttributes, targetAttribute);
            root.addChild(entry.getKey(), childNode);
        }

        return root;
    }
    
    /**
     * Imprime a Árvore de Decisão usando um Percurso em Pré-Ordem. (Etapa 3)
     */
    public static void printTree(TreeNode node, String prefix) {
        if (node.isLeaf) {
            System.out.println(prefix + "-> DECISÃO: " + node.attribute);
            return;
        }
        System.out.println(prefix + "-> TESTE: " + node.attribute + "?");
        for (Map.Entry<String, TreeNode> entry : node.children.entrySet()) {
            String attributeValue = entry.getKey();
            TreeNode childNode = entry.getValue();
            String newPrefix = prefix + "    [Se " + node.attribute + " é " + attributeValue + "] ";
            printTree(childNode, newPrefix);
        }
    }

    /**
     * Usa a Árvore de Decisão para classificar uma nova instância (Inferência). (Etapa 5)
     */
    public static String classify(TreeNode node, Map<String, String> instance) {
        if (node.isLeaf) {
            return node.attribute;
        }
        String attributeValue = instance.get(node.attribute);
        if (node.children.containsKey(attributeValue)) {
            return classify(node.children.get(attributeValue), instance);
        } else {
            System.err.println("Aviso: Valor não visto ('" + attributeValue + "') no atributo '" + node.attribute + "'. Não foi possível classificar.");
            return "DESCONHECIDO"; 
        }
    }

    public static void main(String[] args) {
        System.out.println("Setup concluído! Total de Instâncias de Treino: " + DATA.size());
        
        // --- TESTE DE ENTROPIA INICIAL (Etapa 3) ---
        double initialEntropy = calculateEntropy(DATA, TARGET_ATTRIBUTE);
        System.out.printf("\nEntropia Inicial (total): %.4f\n", initialEntropy);

        // --- CONSTRUÇÃO DA ÁRVORE ID3 (Etapa 4) ---
        System.out.println("\n--- Construção da Árvore ID3 ---");
        TreeNode decisionTree = buildTree(DATA, ATTRIBUTES, TARGET_ATTRIBUTE);
        printTree(decisionTree, "");

        // --- AVALIAÇÃO DO MODELO (Etapa 5) ---
        List<Map<String, String>> TEST_DATA = List.of(
            Map.of("Gênero", "Ação", "Duração", "Média", "Avaliação", "Baixa", "Atores", "Conhecidos", TARGET_ATTRIBUTE, "Não"),
            Map.of("Gênero", "Comédia", "Duração", "Longa", "Avaliação", "Alta", "Atores", "Desconhecidos", TARGET_ATTRIBUTE, "Sim"),
            Map.of("Gênero", "Drama", "Duração", "Curta", "Avaliação", "Média", "Atores", "Desconhecidos", TARGET_ATTRIBUTE, "Sim")
        );
        
        int correctPredictions = 0;
        
        System.out.println("\n--- Avaliação do Modelo (Testando Recomendações) ---");

        for (Map<String, String> testInstance : TEST_DATA) {
            String actualClass = testInstance.get(TARGET_ATTRIBUTE);
            Map<String, String> instanceToClassify = new HashMap<>(testInstance);
            instanceToClassify.remove(TARGET_ATTRIBUTE);
            
            String prediction = classify(decisionTree, instanceToClassify);
            
            if (prediction.equals(actualClass)) {
                correctPredictions++;
                System.out.print("✅ ");
            } else {
                System.out.print("❌ ");
            }
            System.out.println("Previsto: " + prediction + " | Real: " + actualClass);
        }

        double accuracy = (double) correctPredictions / TEST_DATA.size() * 100;
        System.out.printf("\nACURÁCIA (Precisão) do Modelo: %d/%d = %.2f%%\n",
                             correctPredictions, TEST_DATA.size(), accuracy);
    }
}

# Projeto: Classificador de Tipo de Clima para Passeio
# Dataset Utilizado (14 Instâncias)
# O objetivo é classificar se o usuário deve Ir (Passear) ou Ficar em Casa (Não Passear) com base nas condições climáticas:

<img width="439" height="766" alt="Captura de tela 2025-11-06 194033" src="https://github.com/user-attachments/assets/c7f9362a-cc31-4d31-968c-136c2b91d72b" />

Variáveis Globais do Projeto:
TARGET_ATTRIBUTE: "Decisão"
ATTRIBUTES: Temperatura, Céu, Vento, Humidade

▶️ Exercício 1: Setup e Representação de Dados
Foco: Carregamento dos dados na estrutura Java (List<Map<String, String>>).
Código Esperado (Exercício 1)
ID3Algorithm.java (Trecho de DATA preenchido):
Java
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;
import java.lang.Math;
import java.util.Comparator;

public class ID3Algorithm {
    
    // VARIÁVEIS GLOBAIS DE SETUP
    public static final String TARGET_ATTRIBUTE = "Decisão";
    public static final List<String> ATTRIBUTES = List.of("Temperatura", "Céu", "Vento", "Humidade");

    // Conjunto de dados (Dataset) - 14 Instâncias
    public static final List<Map<String, String>> DATA = List.of(
        Map.of("Temperatura", "Quente", "Céu", "Ensolarado", "Vento", "Fraco", "Humidade", "Alta", TARGET_ATTRIBUTE, "Ir"),
        Map.of("Temperatura", "Quente", "Céu", "Ensolarado", "Vento", "Forte", "Humidade", "Alta", TARGET_ATTRIBUTE, "Ir"),
        Map.of("Temperatura", "Quente", "Céu", "Nublado", "Vento", "Fraco", "Humidade", "Alta", TARGET_ATTRIBUTE, "Ir"),
        Map.of("Temperatura", "Fria", "Céu", "Chuvoso", "Vento", "Fraco", "Humidade", "Alta", TARGET_ATTRIBUTE, "Ficar em Casa"),
        Map.of("Temperatura", "Fria", "Céu", "Chuvoso", "Vento", "Forte", "Humidade", "Normal", TARGET_ATTRIBUTE, "Ficar em Casa"),
        Map.of("Temperatura", "Fria", "Céu", "Chuvoso", "Vento", "Forte", "Humidade", "Baixa", TARGET_ATTRIBUTE, "Ficar em Casa"),
        Map.of("Temperatura", "Amena", "Céu", "Ensolarado", "Vento", "Forte", "Humidade", "Normal", TARGET_ATTRIBUTE, "Ir"),
        Map.of("Temperatura", "Quente", "Céu", "Chuvoso", "Vento", "Fraco", "Humidade", "Alta", TARGET_ATTRIBUTE, "Ficar em Casa"),
        Map.of("Temperatura", "Fria", "Céu", "Ensolarado", "Vento", "Fraco", "Humidade", "Baixa", TARGET_ATTRIBUTE, "Ir"),
        Map.of("Temperatura", "Quente", "Céu", "Nublado", "Vento", "Forte", "Humidade", "Normal", TARGET_ATTRIBUTE, "Ir"),
        Map.of("Temperatura", "Amena", "Céu", "Nublado", "Vento", "Forte", "Humidade", "Normal", TARGET_ATTRIBUTE, "Ir"),
        Map.of("Temperatura", "Amena", "Céu", "Chuvoso", "Vento", "Forte", "Humidade", "Alta", TARGET_ATTRIBUTE, "Ficar em Casa"),
        Map.of("Temperatura", "Fria", "Céu", "Nublado", "Vento", "Fraco", "Humidade", "Baixa", TARGET_ATTRIBUTE, "Ir"),
        Map.of("Temperatura", "Amena", "Céu", "Chuvoso", "Vento", "Fraco", "Humidade", "Normal", TARGET_ATTRIBUTE, "Ir")
    );

    public static void main(String[] args) {
        System.out.println("Setup concluído! Total de Instâncias: " + DATA.size());
    }
    
    // MÉTODOS DE ML (IDÊNTICOS AOS PROJETOS ANTERIORES NA LÓGICA)
    // ...
}



# Exercício 2: Entropia e Desafio 1: Recursão (printTree)
# Estes métodos seguem exatamente a mesma lógica dos projetos anteriores, focando na Entropia do novo alvo ("Decisão") e na capacidade de fazer percursos recursivos.
# Código Esperado (Ex. 2 e Des. 1)
Java
// ... (código anterior)

    public static void main(String[] args) {
        // ... (Verificação de setup do Ex. 1)
        
        // Teste do Exercício 2
        double initialEntropy = calculateEntropy(DATA, TARGET_ATTRIBUTE);
        System.out.printf("\nEntropia Inicial (total): %.4f\n", initialEntropy); // Saída esperada: 0.9403

        // ------------------ DESAFIO 1: TESTE DA FUNÇÃO RECURSIVA printTree ------------------
        
        // Cria uma micro-árvore manual para teste (Ex: Céu -> Temperatura)
        TreeNode rootTest = new TreeNode("Céu", false);
        TreeNode chuvosoTest = new TreeNode("Temperatura", false);
        TreeNode ensolaradoTest = new TreeNode("Ir", true);
        TreeNode nubladoTest = new TreeNode("Ir", true);

        rootTest.addChild("Chuvoso", chuvosoTest);
        rootTest.addChild("Ensolarado", ensolaradoTest);
        rootTest.addChild("Nublado", nubladoTest);

        chuvosoTest.addChild("Quente", new TreeNode("Ficar em Casa", true));
        chuvosoTest.addChild("Fria", new TreeNode("Ficar em Casa", true));

        System.out.println("\n--- Teste de Percurso em Árvore ---");
        printTree(rootTest, "");
    }
    
    /** Calcula a Entropia */
    public static double calculateEntropy(List<Map<String, String>> data, String targetAttribute) {
        if (data.isEmpty()) { return 0.0; }
        Map<String, Long> countByClass = data.stream()
            .collect(Collectors.groupingBy(instance -> instance.get(targetAttribute), Collectors.counting()));
        double entropy = 0.0;
        int totalInstances = data.size();
        for (Long count : countByClass.values()) {
            double probability = (double) count / totalInstances;
            if (probability > 0) {
                entropy -= probability * (Math.log(probability) / Math.log(2)); 
            }
        }
        return entropy;
    }
    
    /** Imprime a Árvore de Decisão usando Recursão */
    public static void printTree(TreeNode node, String prefix) {
        if (node.isLeaf) {
            System.out.println(prefix + "-> DECISÃO: " + node.attribute);
            return;
        }
        System.out.println(prefix + "-> TESTE: " + node.attribute + "?");
        for (Map.Entry<String, TreeNode> entry : node.children.entrySet()) {
            String newPrefix = prefix + "   [Se " + node.attribute + " é " + entry.getKey() + "] ";
            printTree(entry.getValue(), newPrefix);
        }
    }
    
    // ... (assinaturas dos métodos de Ganho e Construção)



# Exercício 3: Ganho de Informação e ID3
# A lógica ID3 deve identificar Céu como o atributo mais informativo para o nó raiz.
# Código Esperado (Exercício 3)
Java
// ... (imports, calculateEntropy, printTree)

    public static void main(String[] args) {
        // ... (Testes anteriores)

        // ------------------ EXERCÍCIO 3: CONSTRUÇÃO E IMPRESSÃO DA ÁRVORE ------------------
        System.out.println("\n--- Construção da Árvore ID3 ---");
        TreeNode decisionTree = buildTree(DATA, ATTRIBUTES, TARGET_ATTRIBUTE);
        printTree(decisionTree, ""); 
    }

    /** Calcula o Ganho de Informação */
    public static double calculateGain(List<Map<String, String>> data, String attribute, String targetAttribute) {
        double totalEntropy = calculateEntropy(data, targetAttribute);
        int totalInstances = data.size();
        Map<String, List<Map<String, String>>> splitData = data.stream()
            .collect(Collectors.groupingBy(instance -> instance.get(attribute)));
        double weightedEntropy = 0.0;
        for (List<Map<String, String>> subset : splitData.values()) {
            double probability = (double) subset.size() / totalInstances;
            weightedEntropy += probability * calculateEntropy(subset, targetAttribute);
        }
        return totalEntropy - weightedEntropy;
    }

    /** Algoritmo Recursivo ID3 */
    public static TreeNode buildTree(List<Map<String, String>> data, List<String> availableAttributes, String targetAttribute) {
        // Casos Base (Pureza e Parada)
        String firstClass = data.get(0).get(targetAttribute);
        if (data.stream().allMatch(instance -> instance.get(targetAttribute).equals(firstClass))) {
            return new TreeNode(firstClass, true);
        }
        if (availableAttributes.isEmpty() || data.isEmpty()) {
            // Retorna a classe majoritária
            String majorityClass = data.stream().collect(Collectors.groupingBy(instance -> instance.get(targetAttribute), Collectors.counting()))
                .entrySet().stream().max(Map.Entry.comparingByValue()).map(Map.Entry::getKey).orElse(firstClass); 
            return new TreeNode(majorityClass, true);
        }

        // Encontrar o atributo com o MAIOR Ganho
        String bestAttribute = availableAttributes.stream()
            .max(Comparator.comparingDouble(attribute -> calculateGain(data, attribute, targetAttribute)))
            .orElseThrow(() -> new IllegalStateException("Erro: Não foi possível encontrar o melhor atributo."));

        TreeNode root = new TreeNode(bestAttribute, false);
        List<String> remainingAttributes = new ArrayList<>(availableAttributes);
        remainingAttributes.remove(bestAttribute);
        
        // Chamada Recursiva
        Map<String, List<Map<String, String>>> splitData = data.stream()
            .collect(Collectors.groupingBy(instance -> instance.get(bestAttribute)));

        for (Map.Entry<String, List<Map<String, String>>> entry : splitData.entrySet()) {
            root.addChild(entry.getKey(), buildTree(entry.getValue(), remainingAttributes, targetAttribute));
        }

        return root;
    }
}


# Saída Esperada (Lógica da Árvore ID3):
--- Construção da Árvore ID3 ---
-> TESTE: Céu?
   [Se Céu é Ensolarado] -> TESTE: Vento?
      [Se Vento é Fraco] -> DECISÃO: Ir
      [Se Vento é Forte] -> DECISÃO: Ir
   [Se Céu é Nublado] -> DECISÃO: Ir
   [Se Céu é Chuvoso] -> TESTE: Vento?
      [Se Vento é Fraco] -> DECISÃO: Ficar em Casa
      [Se Vento é Forte] -> TESTE: Humidade?
         [Se Humidade é Normal] -> DECISÃO: Ficar em Casa
         [Se Humidade é Baixa] -> DECISÃO: Ficar em Casa
         [Se Humidade é Alta] -> DECISÃO: Ficar em Casa



# Desafio 2: Classificação e Acurácia
Código Esperado (Desafio 2 - Final do Projeto)
Java
// ... (código anterior)

    public static void main(String[] args) {
        // ... (Construção da Árvore do Ex. 3)
        TreeNode decisionTree = buildTree(DATA, ATTRIBUTES, TARGET_ATTRIBUTE);
        // ... (printTree)

        // ------------------ DESAFIO 2: TESTE DE INFERÊNCIA E ACURÁCIA ------------------

        // Dados de TESTE (Instâncias NUNCA VISTAS)
        List<Map<String, String>> TEST_DATA = List.of(
            // 1. Fria, Chuvoso, Fraco, Baixa (Esperado: Ficar em Casa)
            Map.of("Temperatura", "Fria", "Céu", "Chuvoso", "Vento", "Fraco", "Humidade", "Baixa", TARGET_ATTRIBUTE, "Ficar em Casa"), 
            // 2. Quente, Nublado, Fraco, Baixa (Esperado: Ir)
            Map.of("Temperatura", "Quente", "Céu", "Nublado", "Vento", "Fraco", "Humidade", "Baixa", TARGET_ATTRIBUTE, "Ir"), 
            // 3. Amena, Ensolarado, Fraco, Alta (Esperado: Ir)
            Map.of("Temperatura", "Amena", "Céu", "Ensolarado", "Vento", "Fraco", "Humidade", "Alta", TARGET_ATTRIBUTE, "Ir")
        );
        
        int correctPredictions = 0;
        
        System.out.println("\n--- Avaliação do Modelo (Testando Decisões de Passeio) ---");

        for (Map<String, String> testInstance : TEST_DATA) {
            String actualClass = testInstance.get(TARGET_ATTRIBUTE);
            Map<String, String> instanceToClassify = new HashMap<>(testInstance);
            instanceToClassify.remove(TARGET_ATTRIBUTE); 
            
            String prediction = classify(decisionTree, instanceToClassify);
            
            if (prediction.equals(actualClass)) {
                correctPredictions++;
                System.out.print("✅ ");
            } else {
                System.out.print("❌ ");
            }
            System.out.println("Previsto: " + prediction + " | Real: " + actualClass);
        }

        double accuracy = (double) correctPredictions / TEST_DATA.size() * 100;
        System.out.printf("\nACURÁCIA (Precisão) do Modelo: %d/%d = %.2f%%\n", 
                          correctPredictions, TEST_DATA.size(), accuracy);
    }

    /** Usa a Árvore para Classificar */
    public static String classify(TreeNode node, Map<String, String> instance) {
        if (node.isLeaf) {
            return node.attribute;
        }
        String attributeValue = instance.get(node.attribute);
        if (node.children.containsKey(attributeValue)) {
            return classify(node.children.get(attributeValue), instance);
        } else {
            System.err.println("Aviso: Valor não visto ('" + attributeValue + "') no atributo '" + node.attribute + "'. Não foi possível classificar.");
            return "DESCONHECIDO"; 
        }
    }
}


# Saída Esperada (Desafio 2 - Teste de Acurácia):
--- Avaliação do Modelo (Testando Decisões de Passeio) ---
✅ Previsto: Ficar em Casa | Real: Ficar em Casa
✅ Previsto: Ir | Real: Ir
✅ Previsto: Ir | Real: Ir

ACURÁCIA (Precisão) do Modelo: 3/3 = 100.00%

# Resposta do Projeto 3. Projeto: Classificador de Tipo de Clima para Passeio.

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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;
import java.lang.Math;
import java.util.Comparator;

public class ID3Algorithm {

    // --- VARIÁVEIS GLOBAIS DE SETUP (Exercício 1) ---
    // Atributo alvo para classificação (o que a árvore deve prever).
    public static final String TARGET_ATTRIBUTE = "Decisão"; //
    
    // Lista de atributos de entrada (features) a serem testados.
    public static final List<String> ATTRIBUTES = List.of("Temperatura", "Céu", "Vento", "Humidade"); //

    // Conjunto de dados (Dataset) - 14 Instâncias (Exercício 1)
    public static final List<Map<String, String>> DATA = List.of(
        Map.of("Temperatura", "Quente", "Céu", "Ensolarado", "Vento", "Fraco", "Humidade", "Alta", TARGET_ATTRIBUTE, "Ir"), // 1
        Map.of("Temperatura", "Quente", "Céu", "Ensolarado", "Vento", "Forte", "Humidade", "Alta", TARGET_ATTRIBUTE, "Ir"), // 2
        Map.of("Temperatura", "Quente", "Céu", "Nublado", "Vento", "Fraco", "Humidade", "Alta", TARGET_ATTRIBUTE, "Ir"), // 3
        Map.of("Temperatura", "Fria", "Céu", "Chuvoso", "Vento", "Fraco", "Humidade", "Alta", TARGET_ATTRIBUTE, "Ficar em Casa"), // 4
        Map.of("Temperatura", "Fria", "Céu", "Chuvoso", "Vento", "Forte", "Humidade", "Normal", TARGET_ATTRIBUTE, "Ficar em Casa"), // 5
        Map.of("Temperatura", "Fria", "Céu", "Chuvoso", "Vento", "Forte", "Humidade", "Baixa", TARGET_ATTRIBUTE, "Ficar em Casa"), // 6
        Map.of("Temperatura", "Amena", "Céu", "Ensolarado", "Vento", "Forte", "Humidade", "Normal", TARGET_ATTRIBUTE, "Ir"), // 7
        Map.of("Temperatura", "Quente", "Céu", "Chuvoso", "Vento", "Fraco", "Humidade", "Alta", TARGET_ATTRIBUTE, "Ficar em Casa"), // 8
        Map.of("Temperatura", "Fria", "Céu", "Ensolarado", "Vento", "Fraco", "Humidade", "Baixa", TARGET_ATTRIBUTE, "Ir"), // 9
        Map.of("Temperatura", "Quente", "Céu", "Nublado", "Vento", "Forte", "Humidade", "Normal", TARGET_ATTRIBUTE, "Ir"), // 10
        Map.of("Temperatura", "Amena", "Céu", "Nublado", "Vento", "Forte", "Humidade", "Normal", TARGET_ATTRIBUTE, "Ir"), // 11
        Map.of("Temperatura", "Amena", "Céu", "Chuvoso", "Vento", "Forte", "Humidade", "Alta", TARGET_ATTRIBUTE, "Ficar em Casa"), // 12
        Map.of("Temperatura", "Fria", "Céu", "Nublado", "Vento", "Fraco", "Humidade", "Baixa", TARGET_ATTRIBUTE, "Ir"), // 13
        Map.of("Temperatura", "Amena", "Céu", "Chuvoso", "Vento", "Fraco", "Humidade", "Normal", TARGET_ATTRIBUTE, "Ir") // 14
    );

    // Dados de TESTE (Instâncias NUNCA VISTAS) (Desafio 2)
    public static final List<Map<String, String>> TEST_DATA = List.of(
        // 1. Fria, Chuvoso, Fraco, Baixa (Esperado: Ficar em Casa)
        Map.of("Temperatura", "Fria", "Céu", "Chuvoso", "Vento", "Fraco", "Humidade", "Baixa", TARGET_ATTRIBUTE, "Ficar em Casa"),
        // 2. Quente, Nublado, Fraco, Baixa (Esperado: Ir)
        Map.of("Temperatura", "Quente", "Céu", "Nublado", "Vento", "Fraco", "Humidade", "Baixa", TARGET_ATTRIBUTE, "Ir"),
        // 3. Amena, Ensolarado, Fraco, Alta (Esperado: Ir)
        Map.of("Temperatura", "Amena", "Céu", "Ensolarado", "Vento", "Fraco", "Humidade", "Alta", TARGET_ATTRIBUTE, "Ir")
    );


    // --- MÉTODOS DE ML ---

    /** * Calcula a Entropia de um conjunto de dados. 
     * Entropia = - SUM [ P(c) * log2(P(c)) ]
     * @param data O subconjunto de dados.
     * @param targetAttribute O nome do atributo alvo.
     * @return O valor da Entropia.
     */
    public static double calculateEntropy(List<Map<String, String>> data, String targetAttribute) {
        if (data.isEmpty()) { return 0.0; } // Entropia 0 para um conjunto vazio
        
        // Conta a frequência de cada classe alvo (ex: "Ir", "Ficar em Casa")
        Map<String, Long> countByClass = data.stream()
            .collect(Collectors.groupingBy(instance -> instance.get(targetAttribute), Collectors.counting())); //
        
        double entropy = 0.0;
        int totalInstances = data.size(); //

        // Aplica a fórmula da Entropia
        for (Long count : countByClass.values()) { //
            double probability = (double) count / totalInstances; // P(c)
            if (probability > 0) { // Garante que log(0) não seja calculado
                // -(P(c) * log2(P(c)))
                entropy -= probability * (Math.log(probability) / Math.log(2)); //
            }
        }
        return entropy; //
    }

    /** * Calcula o Ganho de Informação para um atributo. 
     * Ganho(S, A) = Entropia(S) - SUM [ (|Sv| / |S|) * Entropia(Sv) ]
     * @param data O conjunto de dados atual.
     * @param attribute O atributo a ser avaliado.
     * @param targetAttribute O atributo alvo.
     * @return O valor do Ganho de Informação.
     */
    public static double calculateGain(List<Map<String, String>> data, String attribute, String targetAttribute) {
        // Entropia Total (antes da divisão)
        double totalEntropy = calculateEntropy(data, targetAttribute); 
        int totalInstances = data.size(); //

        // Divide o conjunto de dados em subconjuntos (Sv) com base nos valores do atributo
        Map<String, List<Map<String, String>>> splitData = data.stream()
            .collect(Collectors.groupingBy(instance -> instance.get(attribute)));
        
        double weightedEntropy = 0.0; // Entropia ponderada (o segundo termo da fórmula)

        // Calcula a Entropia Ponderada
        for (List<Map<String, String>> subset : splitData.values()) { // Para cada subconjunto (Sv)
            double probability = (double) subset.size() / totalInstances; // (|Sv| / |S|)
            // Soma: (|Sv| / |S|) * Entropia(Sv)
            weightedEntropy += probability * calculateEntropy(subset, targetAttribute); 
        }
        
        // Ganho = Entropia Total - Entropia Ponderada
        return totalEntropy - weightedEntropy;
    }

    /** * Algoritmo Recursivo ID3 para Construir a Árvore. 
     * @param data Conjunto de dados atual.
     * @param availableAttributes Atributos que ainda podem ser usados.
     * @param targetAttribute Atributo alvo.
     * @return O nó raiz (ou folha) da sub-árvore.
     */
    public static TreeNode buildTree(List<Map<String, String>> data, List<String> availableAttributes, String targetAttribute) {
        
        // CASO BASE 1: Conjunto Vazio. Retorna folha da classe majoritária (ou nulo, aqui é tratado no caso 2 com fallback)
        if (data.isEmpty()) { 
            // Neste exemplo, esta condição é tratada junto com availableAttributes.isEmpty()
            // para garantir um retorno, embora com o dataset de treino isso não deva ocorrer no nó raiz.
            // Para garantir segurança, caso o subset fique vazio (ex: dados de teste não vistos):
            return new TreeNode("N/A - Conjunto Vazio", true); // Um rótulo seguro
        }

        // Casos Base (Pureza e Parada)
        
        // CASO BASE 2: Pureza (Todos os exemplos têm a mesma classe). Retorna uma FOLHA com essa classe.
        String firstClass = data.get(0).get(targetAttribute); //
        if (data.stream().allMatch(instance -> instance.get(targetAttribute).equals(firstClass))) { //
            return new TreeNode(firstClass, true); //
        }
        
        // CASO BASE 3: Não há mais atributos disponíveis para testar. Retorna uma FOLHA com a CLASSE MAJORITÁRIA.
        if (availableAttributes.isEmpty()) { //
            // Retorna a classe majoritária
            String majorityClass = data.stream()
                .collect(Collectors.groupingBy(instance -> instance.get(targetAttribute), Collectors.counting())) // Conta as classes
                .entrySet().stream().max(Map.Entry.comparingByValue()) // Encontra a mais frequente
                .map(Map.Entry::getKey).orElse(firstClass); //
            return new TreeNode(majorityClass, true); //
        }

        // LÓGICA RECURSIVA ID3
        
        // 1. Encontrar o atributo com o MAIOR Ganho de Informação
        String bestAttribute = availableAttributes.stream()
            // Compara os atributos pelo Ganho de Informação
            .max(Comparator.comparingDouble(attribute -> calculateGain(data, attribute, targetAttribute))) 
            .orElseThrow(() -> new IllegalStateException("Erro: Não foi possível encontrar o melhor atributo.")); //

        // 2. Criar o Nó Raiz (Teste) com o melhor atributo
        TreeNode root = new TreeNode(bestAttribute, false); 
        
        // 3. Remover o melhor atributo da lista de atributos disponíveis para o próximo nível
        List<String> remainingAttributes = new ArrayList<>(availableAttributes);
        remainingAttributes.remove(bestAttribute);

        // 4. Dividir o conjunto de dados com base nos valores do melhor atributo
        Map<String, List<Map<String, String>>> splitData = data.stream()
            .collect(Collectors.groupingBy(instance -> instance.get(bestAttribute)));

        // 5. Chamada Recursiva: Constrói a sub-árvore para cada valor do atributo
        for (Map.Entry<String, List<Map<String, String>>> entry : splitData.entrySet()) {
            // Adiciona o nó filho resultante da recursão
            root.addChild(entry.getKey(), buildTree(entry.getValue(), remainingAttributes, targetAttribute));
        }

        return root; //
    }

    /** * Imprime a Árvore de Decisão usando Recursão (Desafio 1). 
     * @param node O nó atual a ser impresso.
     * @param prefix String para indentação e contexto.
     */
    public static void printTree(TreeNode node, String prefix) {
        if (node.isLeaf) { // Caso Base: Nó Folha
            System.out.println(prefix + "-> DECISÃO: " + node.attribute); //
            return; //
        }
        
        // Nó Interno (Teste)
        System.out.println(prefix + "-> TESTE: " + node.attribute + "?"); //
        
        // Percorre os filhos (ramos)
        for (Map.Entry<String, TreeNode> entry : node.children.entrySet()) {
            // Cria um novo prefixo com a regra (ex: "[Se Céu é Ensolarado] ")
            String newPrefix = prefix + "   [Se " + node.attribute + " é " + entry.getKey() + "] ";
            // Chamada recursiva para o nó filho
            printTree(entry.getValue(), newPrefix);
        } //
    }

    /** * Usa a Árvore para Classificar uma nova instância (Desafio 2). 
     * @param node O nó atual da árvore.
     * @param instance A instância (Map de atributos) a ser classificada.
     * @return A classe prevista ("Ir" ou "Ficar em Casa").
     */
    public static String classify(TreeNode node, Map<String, String> instance) {
        if (node.isLeaf) { // Caso Base: Nó Folha - Retorna a decisão
            return node.attribute;
        }
        
        // Pega o valor do atributo que está sendo testado no nó atual
        String attributeValue = instance.get(node.attribute); 
        
        // Se a árvore tem um ramo para esse valor (vista no treino)
        if (node.children.containsKey(attributeValue)) {
            // Chamada recursiva para o nó filho correspondente ao valor
            return classify(node.children.get(attributeValue), instance);
        } else {
            // Caso encontre um valor de atributo não visto no treino
            System.err.println("Aviso: Valor não visto ('" + attributeValue + "') no atributo '" + node.attribute + "'. Não foi possível classificar.");
            return "DESCONHECIDO"; //
        }
    }

    public static void main(String[] args) {
        // --- EXERCÍCIO 1: SETUP E VERIFICAÇÃO ---
        System.out.println("Setup concluído! Total de Instâncias de Treino: " + DATA.size()); //

        // --- EXERCÍCIO 2: ENTROPIA ---
        double initialEntropy = calculateEntropy(DATA, TARGET_ATTRIBUTE); //
        // Saída esperada: 0.9403
        System.out.printf("\nEntropia Inicial (total): %.4f\n", initialEntropy); 

        // --- EXERCÍCIO 3: CONSTRUÇÃO DA ÁRVORE ID3 ---
        System.out.println("\n--- Construção da Árvore ID3 ---"); //
        // Chamada principal do algoritmo ID3
        TreeNode decisionTree = buildTree(DATA, ATTRIBUTES, TARGET_ATTRIBUTE); 
        
        // Impressão da Árvore (Desafio 1 e Ex. 3)
        printTree(decisionTree, ""); 

        // --- DESAFIO 2: TESTE DE INFERÊNCIA E ACURÁCIA ---
        System.out.println("\n--- Avaliação do Modelo (Testando Decisões de Passeio) ---"); //
        
        int correctPredictions = 0; //

        for (Map<String, String> testInstance : TEST_DATA) { //
            String actualClass = testInstance.get(TARGET_ATTRIBUTE); // A classe real
            Map<String, String> instanceToClassify = new HashMap<>(testInstance);
            instanceToClassify.remove(TARGET_ATTRIBUTE); // Remove o alvo para classificar apenas com os atributos

            String prediction = classify(decisionTree, instanceToClassify); //

            if (prediction.equals(actualClass)) { //
                correctPredictions++; //
                System.out.print( "✅ " ); //
            } else {
                System.out.print( "❌ " ); //
            }
            System.out.println("Previsto: " + prediction + " | Real: " + actualClass); //
        }

        // Cálculo e Impressão da Acurácia
        double accuracy = (double) correctPredictions / TEST_DATA.size() * 100;
        System.out.printf("\nACURÁCIA (Precisão) do Modelo: %d/%d = %.2f%%\n",
            correctPredictions, TEST_DATA.size(), accuracy);
    }
}

# Seção 2 - Projeto: Detector de Spam de E-mail (ID3 em Java)
# Dataset Utilizado (14 Instâncias)
# O objetivo é classificar se o e-mail é Spam ou Não Spam, baseado em quatro características comuns:

<img width="435" height="769" alt="image" src="https://github.com/user-attachments/assets/7d2fac03-047e-4373-a390-d9d2bdd3a236" />

Variáveis Globais do Projeto:
TARGET_ATTRIBUTE: "Decisão"
ATTRIBUTES: Assunto, Remetente, Anexos, Pontuação

 Exercício 1: Setup e Representação de Dados
Foco: Criação de classes e carregamento dos dados.
Código Esperado (Exercício 1)
ID3Algorithm.java (Trecho de DATA preenchido):
Java
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;
import java.lang.Math;
import java.util.Comparator;

public class ID3Algorithm {
    
    // VARIÁVEIS GLOBAIS DE SETUP
    public static final String TARGET_ATTRIBUTE = "Decisão";
    public static final List<String> ATTRIBUTES = List.of("Assunto", "Remetente", "Anexos", "Pontuação");

    // Conjunto de dados (Dataset) - 14 Instâncias
    public static final List<Map<String, String>> DATA = List.of(
        Map.of("Assunto", "Com Oferta", "Remetente", "Desconhecido", "Anexos", "Não", "Pontuação", "Exclamações", TARGET_ATTRIBUTE, "Spam"),
        Map.of("Assunto", "Com Oferta", "Remetente", "Desconhecido", "Anexos", "Sim", "Pontuação", "Exclamações", TARGET_ATTRIBUTE, "Spam"),
        Map.of("Assunto", "Sem Oferta", "Remetente", "Conhecido", "Anexos", "Não", "Pontuação", "Normal", TARGET_ATTRIBUTE, "Não Spam"),
        Map.of("Assunto", "Sem Oferta", "Remetente", "Desconhecido", "Anexos", "Não", "Pontuação", "Normal", TARGET_ATTRIBUTE, "Não Spam"),
        Map.of("Assunto", "Sem Oferta", "Remetente", "Conhecido", "Anexos", "Não", "Pontuação", "Normal", TARGET_ATTRIBUTE, "Não Spam"),
        Map.of("Assunto", "Sem Oferta", "Remetente", "Conhecido", "Anexos", "Sim", "Pontuação", "Exclamações", TARGET_ATTRIBUTE, "Spam"),
        Map.of("Assunto", "Com Oferta", "Remetente", "Desconhecido", "Anexos", "Não", "Pontuação", "Normal", TARGET_ATTRIBUTE, "Spam"),
        Map.of("Assunto", "Com Oferta", "Remetente", "Conhecido", "Anexos", "Não", "Pontuação", "Exclamações", TARGET_ATTRIBUTE, "Spam"),
        Map.of("Assunto", "Sem Oferta", "Remetente", "Desconhecido", "Anexos", "Não", "Pontuação", "Normal", TARGET_ATTRIBUTE, "Não Spam"),
        Map.of("Assunto", "Sem Oferta", "Remetente", "Conhecido", "Anexos", "Não", "Pontuação", "Normal", TARGET_ATTRIBUTE, "Não Spam"),
        Map.of("Assunto", "Sem Oferta", "Remetente", "Desconhecido", "Anexos", "Sim", "Pontuação", "Exclamações", TARGET_ATTRIBUTE, "Não Spam"),
        Map.of("Assunto", "Com Oferta", "Remetente", "Desconhecido", "Anexos", "Sim", "Pontuação", "Normal", TARGET_ATTRIBUTE, "Spam"),
        Map.of("Assunto", "Sem Oferta", "Remetente", "Conhecido", "Anexos", "Sim", "Pontuação", "Normal", TARGET_ATTRIBUTE, "Não Spam"),
        Map.of("Assunto", "Com Oferta", "Remetente", "Conhecido", "Anexos", "Sim", "Pontuação", "Exclamações", TARGET_ATTRIBUTE, "Spam")
    );

    public static void main(String[] args) {
        System.out.println("Setup concluído! Total de Instâncias: " + DATA.size());
    }

    // MÉTODOS DE ML (IDÊNTICOS AOS PROJETOS ANTERIORES NA LÓGICA)
    // ...
}



# Exercício 2: Entropia e Desafio 1: Recursão (printTree)
# A lógica de Entropia e Recursão é idêntica, apenas mudando o conjunto de dados.
# Código Esperado (Ex. 2 e Des. 1)
Java
// ... (código anterior)

    public static void main(String[] args) {
        // ... (Verificação de setup do Ex. 1)
        
        // Teste do Exercício 2
        double initialEntropy = calculateEntropy(DATA, TARGET_ATTRIBUTE);
        System.out.printf("\nEntropia Inicial (total): %.4f\n", initialEntropy); // Saída esperada: 0.9403

        // ------------------ DESAFIO 1: TESTE DA FUNÇÃO RECURSIVA printTree ------------------
        
        // Cria uma micro-árvore manual para teste (Ex: Assunto -> Remetente)
        TreeNode rootTest = new TreeNode("Assunto", false);
        TreeNode comOfertaTest = new TreeNode("Spam", true);
        TreeNode semOfertaTest = new TreeNode("Remetente", false);

        rootTest.addChild("Com Oferta", comOfertaTest);
        rootTest.addChild("Sem Oferta", semOfertaTest);
        
        semOfertaTest.addChild("Conhecido", new TreeNode("Não Spam", true));
        semOfertaTest.addChild("Desconhecido", new TreeNode("Não Spam", true));

        System.out.println("\n--- Teste de Percurso em Árvore ---");
        printTree(rootTest, "");
    }
    
    /** Calcula a Entropia */
    public static double calculateEntropy(List<Map<String, String>> data, String targetAttribute) {
        if (data.isEmpty()) { return 0.0; }
        // Implementação idêntica...
        Map<String, Long> countByClass = data.stream()
            .collect(Collectors.groupingBy(instance -> instance.get(targetAttribute), Collectors.counting()));
        double entropy = 0.0;
        int totalInstances = data.size();
        for (Long count : countByClass.values()) {
            double probability = (double) count / totalInstances;
            if (probability > 0) {
                entropy -= probability * (Math.log(probability) / Math.log(2)); 
            }
        }
        return entropy;
    }
    
    /** Imprime a Árvore de Decisão usando Recursão */
    public static void printTree(TreeNode node, String prefix) {
        // Implementação idêntica...
        if (node.isLeaf) {
            System.out.println(prefix + "-> DECISÃO: " + node.attribute);
            return;
        }
        System.out.println(prefix + "-> TESTE: " + node.attribute + "?");
        for (Map.Entry<String, TreeNode> entry : node.children.entrySet()) {
            String newPrefix = prefix + "   [Se " + node.attribute + " é " + entry.getKey() + "] ";
            printTree(entry.getValue(), newPrefix);
        }
    }
    
    // ... (assinaturas dos métodos de Ganho e Construção)

# Exercício 3: Ganho de Informação e ID3
A lógica ID3 deve identificar o Assunto ou a Pontuação como o atributo mais informativo para o nó raiz.
Código Esperado (Exercício 3)
Os métodos calculateGain e buildTree são exatamente os mesmos do projeto anterior em termos de lógica, garantindo que os alunos possam reutilizar o código principal e focar na análise do resultado.
Lógica da Árvore ID3 (Saída Esperada):
--- Construção da Árvore ID3 ---
-> TESTE: Assunto?
   [Se Assunto é Com Oferta] -> DECISÃO: Spam
   [Se Assunto é Sem Oferta] -> TESTE: Pontuação?
      [Se Pontuação é Normal] -> TESTE: Remetente?
         [Se Remetente é Conhecido] -> DECISÃO: Não Spam
         [Se Remetente é Desconhecido] -> DECISÃO: Não Spam
      [Se Pontuação é Exclamações] -> TESTE: Remetente?
         [Se Remetente é Conhecido] -> DECISÃO: Spam
         [Se Remetente é Desconhecido] -> DECISÃO: Não Spam



# Desafio 2: Classificação e Acurácia
O método classify() e a lógica de avaliação no main são idênticos, apenas usando um novo conjunto de teste.
Código Esperado (Desafio 2 - Final do Projeto)
Java
// ... (código anterior)

    public static void main(String[] args) {
        // ... (Construção da Árvore do Ex. 3)
        TreeNode decisionTree = buildTree(DATA, ATTRIBUTES, TARGET_ATTRIBUTE);
        // ... (printTree)

        // ------------------ DESAFIO 2: TESTE DE INFERÊNCIA E ACURÁCIA ------------------

        // Dados de TESTE (Instâncias NUNCA VISTAS)
        List<Map<String, String>> TEST_DATA = List.of(
            // 1. Sem Oferta, Conhecido, Sim, Exclamações (Esperado: Spam)
            Map.of("Assunto", "Sem Oferta", "Remetente", "Conhecido", "Anexos", "Sim", "Pontuação", "Exclamações", TARGET_ATTRIBUTE, "Spam"), 
            // 2. Com Oferta, Conhecido, Não, Normal (Esperado: Spam)
            Map.of("Assunto", "Com Oferta", "Remetente", "Conhecido", "Anexos", "Não", "Pontuação", "Normal", TARGET_ATTRIBUTE, "Spam"), 
            // 3. Sem Oferta, Desconhecido, Não, Exclamações (Esperado: Não Spam)
            Map.of("Assunto", "Sem Oferta", "Remetente", "Desconhecido", "Anexos", "Não", "Pontuação", "Exclamações", TARGET_ATTRIBUTE, "Não Spam")
        );
        
        // ... (Lógica de loop e cálculo de acurácia)
    }

    /** Usa a Árvore para Classificar */
    public static String classify(TreeNode node, Map<String, String> instance) {
        // Implementação idêntica...
        if (node.isLeaf) {
            return node.attribute;
        }
        String attributeValue = instance.get(node.attribute);
        if (node.children.containsKey(attributeValue)) {
            return classify(node.children.get(attributeValue), instance);
        } else {
            System.err.println("Aviso: Valor não visto ('" + attributeValue + "') no atributo '" + node.attribute + "'. Não foi possível classificar.");
            return "DESCONHECIDO"; 
        }
    }
}

Resposta: Projeto 4 - Projeto: Detector de Spam de E-mail (ID3 em Java)

import java.util.HashMap;
import java.util.Map;

/**
 * Representa um nó na Árvore de Decisão ID3.
 * O nó pode ser um Nó de Teste (interno) ou um Nó de Decisão/Folha (final).
 */
public class TreeNode {
    // Para nós de teste, armazena o nome do atributo (ex: "Assunto").
    // Para nós folha, armazena a decisão final (ex: "Spam").
    public String attribute;
    
    // Indica se é um nó folha (Decisão) ou um nó interno (Teste).
    public boolean isLeaf;
    
    // Mapeia o valor do atributo (ex: "Com Oferta") para o próximo TreeNode.
    public Map<String, TreeNode> children;

    /**
     * Construtor para um novo nó.
     */
    public TreeNode(String attribute, boolean isLeaf) {
        this.attribute = attribute;
        this.isLeaf = isLeaf;
        
        // Inicializa o mapa de filhos apenas se for um nó de teste (não-folha).
        if (!isLeaf) {
            this.children = new HashMap<>();
        }
    }

    /**
     * Adiciona um filho ao nó atual.
     */
    public void addChild(String value, TreeNode child) {
        if (!isLeaf && children != null) {
            this.children.put(value, child);
        }
    }
}

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;
import java.lang.Math;
import java.util.Comparator;
import java.util.Arrays; // Importação necessária para compatibilidade

/**
 * Implementação do Algoritmo ID3 para o projeto "Detector de Spam de E-mail".
 */
public class ID3Algorithm {

    // --- VARIÁVEIS GLOBAIS DE SETUP ---
    public static final String TARGET_ATTRIBUTE = "Decisão"; // Atributo alvo: "Spam" ou "Não Spam"
    // Correção: Usando Arrays.asList() + ArrayList para compatibilidade
    public static final List<String> ATTRIBUTES = new ArrayList<>(Arrays.asList("Assunto", "Remetente", "Anexos", "Pontuação")); 

    /**
     * Método auxiliar para criar uma instância de dado (Map) de forma compatível (substitui Map.of).
     */
    private static Map<String, String> createMap(String assunto, String remetente, String anexos, String pontuacao, String decisao) {
        Map<String, String> map = new HashMap<>();
        map.put("Assunto", assunto);
        map.put("Remetente", remetente);
        map.put("Anexos", anexos);
        map.put("Pontuação", pontuacao);
        map.put(TARGET_ATTRIBUTE, decisao);
        return map;
    }
    
    // Conjunto de dados (Dataset) de TREINO - 14 Instâncias do projeto "Detector de Spam"
    public static final List<Map<String, String>> DATA = new ArrayList<>(Arrays.asList(
        createMap("Com Oferta", "Desconhecido", "Não", "Exclamações", "Spam"), // 1
        createMap("Com Oferta", "Desconhecido", "Sim", "Exclamações", "Spam"), // 2
        createMap("Sem Oferta", "Conhecido", "Não", "Normal", "Não Spam"), // 3
        createMap("Sem Oferta", "Desconhecido", "Não", "Normal", "Não Spam"), // 4
        createMap("Sem Oferta", "Conhecido", "Não", "Normal", "Não Spam"), // 5
        createMap("Sem Oferta", "Conhecido", "Sim", "Exclamações", "Spam"), // 6
        createMap("Com Oferta", "Desconhecido", "Não", "Normal", "Spam"), // 7
        createMap("Com Oferta", "Conhecido", "Não", "Exclamações", "Spam"), // 8
        createMap("Sem Oferta", "Desconhecido", "Não", "Normal", "Não Spam"), // 9
        createMap("Sem Oferta", "Conhecido", "Não", "Normal", "Não Spam"), // 10
        createMap("Sem Oferta", "Desconhecido", "Sim", "Exclamações", "Não Spam"), // 11
        createMap("Com Oferta", "Desconhecido", "Sim", "Normal", "Spam"), // 12
        createMap("Sem Oferta", "Conhecido", "Sim", "Normal", "Não Spam"), // 13
        createMap("Com Oferta", "Conhecido", "Sim", "Exclamações", "Spam") // 14
    ));

    // Dados de TESTE (Instâncias NUNCA VISTAS) do projeto
    public static final List<Map<String, String>> TEST_DATA = new ArrayList<>(Arrays.asList(
        // 1. Sem Oferta, Conhecido, Sim, Exclamações (Esperado: Spam)
        createMap("Sem Oferta", "Conhecido", "Sim", "Exclamações", "Spam"),
        // 2. Com Oferta, Conhecido, Não, Normal (Esperado: Spam)
        createMap("Com Oferta", "Conhecido", "Não", "Normal", "Spam"),
        // 3. Sem Oferta, Desconhecido, Não, Exclamações (Esperado: Não Spam)
        createMap("Sem Oferta", "Desconhecido", "Não", "Exclamações", "Não Spam")
    ));


    // --- MÉTODOS DE CÁLCULO ---

    /** * Calcula a Entropia de um conjunto de dados. */
    public static double calculateEntropy(List<Map<String, String>> data, String targetAttribute) {
        if (data.isEmpty()) { return 0.0; }
        
        Map<String, Long> countByClass = data.stream()
            .collect(Collectors.groupingBy(instance -> instance.get(targetAttribute), Collectors.counting()));
        
        double entropy = 0.0;
        int totalInstances = data.size();

        for (Long count : countByClass.values()) {
            double probability = (double) count / totalInstances;
            if (probability > 0) { 
                entropy -= probability * (Math.log(probability) / Math.log(2)); 
            }
        }
        return entropy;
    }

    /** * Calcula o Ganho de Informação para um atributo. */
    public static double calculateGain(List<Map<String, String>> data, String attribute, String targetAttribute) {
        double totalEntropy = calculateEntropy(data, targetAttribute); 
        int totalInstances = data.size();

        Map<String, List<Map<String, String>>> splitData = data.stream()
            .collect(Collectors.groupingBy(instance -> instance.get(attribute)));
        
        double weightedEntropy = 0.0; 

        for (List<Map<String, String>> subset : splitData.values()) {
            double probability = (double) subset.size() / totalInstances; 
            weightedEntropy += probability * calculateEntropy(subset, targetAttribute); 
        }
        
        return totalEntropy - weightedEntropy;
    }


    // --- MÉTODOS DE CONSTRUÇÃO E CLASSIFICAÇÃO ---

    /** * Algoritmo Recursivo ID3 para Construir a Árvore. */
    public static TreeNode buildTree(List<Map<String, String>> data, List<String> availableAttributes, String targetAttribute) {
        
        // CASO BASE 1: Pureza (Todos os exemplos têm a mesma classe).
        String firstClass = data.get(0).get(targetAttribute);
        if (data.stream().allMatch(instance -> instance.get(targetAttribute).equals(firstClass))) {
            return new TreeNode(firstClass, true); 
        }
        
        // CASO BASE 2: Não há mais atributos ou conjunto vazio.
        if (availableAttributes.isEmpty() || data.isEmpty()) {
            String majorityClass = data.stream().collect(Collectors.groupingBy(instance -> instance.get(targetAttribute), Collectors.counting()))
                .entrySet().stream().max(Map.Entry.comparingByValue()).map(Map.Entry::getKey).orElse(firstClass);
            return new TreeNode(majorityClass, true);
        }

        // LÓGICA RECURSIVA: Encontrar o atributo com o MAIOR Ganho (Nó Raiz)
        String bestAttribute = availableAttributes.stream()
            .max(Comparator.comparingDouble(attribute -> calculateGain(data, attribute, targetAttribute))) 
            .orElseThrow(() -> new IllegalStateException("Erro: Não foi possível encontrar o melhor atributo."));

        TreeNode root = new TreeNode(bestAttribute, false);
        List<String> remainingAttributes = new ArrayList<>(availableAttributes);
        remainingAttributes.remove(bestAttribute);

        // Chamada Recursiva
        Map<String, List<Map<String, String>>> splitData = data.stream()
            .collect(Collectors.groupingBy(instance -> instance.get(bestAttribute)));

        for (Map.Entry<String, List<Map<String, String>>> entry : splitData.entrySet()) {
            root.addChild(entry.getKey(), buildTree(entry.getValue(), remainingAttributes, targetAttribute));
        }

        return root;
    }

    /** * Imprime a Árvore de Decisão usando Recursão (Visualização). */
    public static void printTree(TreeNode node, String prefix) {
        if (node.isLeaf) { // Caso Base: Nó Folha
            System.out.println(prefix + "-> DECISÃO: " + node.attribute); 
            return;
        }
        
        System.out.println(prefix + "-> TESTE: " + node.attribute + "?"); 
        
        for (Map.Entry<String, TreeNode> entry : node.children.entrySet()) {
            String newPrefix = prefix + "   [Se " + node.attribute + " é " + entry.getKey() + "] ";
            printTree(entry.getValue(), newPrefix);
        }
    }

    /** * Usa a Árvore para Classificar uma nova instância. */
    public static String classify(TreeNode node, Map<String, String> instance) {
        if (node.isLeaf) {
            return node.attribute;
        }
        
        String attributeValue = instance.get(node.attribute);
        
        if (node.children.containsKey(attributeValue)) {
            return classify(node.children.get(attributeValue), instance);
        } else {
            System.err.println("Aviso: Valor não visto ('" + attributeValue + "') no atributo '" + node.attribute + "'. Não foi possível classificar.");
            return "DESCONHECIDO"; 
        }
    }


    public static void main(String[] args) {
        // --- ETAPA 1: SETUP ---
        System.out.println("Setup concluído! Total de Instâncias de Treino: " + DATA.size());

        // --- ETAPA 2: ENTROPIA INICIAL ---
        double initialEntropy = calculateEntropy(DATA, TARGET_ATTRIBUTE);
        System.out.printf("\nEntropia Inicial (total): %.4f\n", initialEntropy);

        // --- ETAPA 3: CONSTRUÇÃO E IMPRESSÃO DA ÁRVORE ID3 ---
        System.out.println("\n--- Construção da Árvore ID3 ---");
        
        TreeNode decisionTree = buildTree(DATA, ATTRIBUTES, TARGET_ATTRIBUTE);
        
        printTree(decisionTree, ""); 

        // --- ETAPA 4: AVALIAÇÃO DO MODELO E ACURÁCIA ---
        System.out.println("\n--- Avaliação do Modelo (Testando Classificação de Spam) ---");
        
        int correctPredictions = 0;

        for (Map<String, String> testInstance : TEST_DATA) {
            String actualClass = testInstance.get(TARGET_ATTRIBUTE);
            Map<String, String> instanceToClassify = new HashMap<>(testInstance);
            instanceToClassify.remove(TARGET_ATTRIBUTE);

            String prediction = classify(decisionTree, instanceToClassify);

            if (prediction.equals(actualClass)) {
                correctPredictions++;
                System.out.print( "✅ " );
            } else {
                System.out.print( "❌ " );
            }
            System.out.println("Previsto: " + prediction + " | Real: " + actualClass);
        }

        double accuracy = (double) correctPredictions / TEST_DATA.size() * 100;
        System.out.printf("\nACURÁCIA (Precisão) do Modelo: %d/%d = %.2f%%\n",
            correctPredictions, TEST_DATA.size(), accuracy);
    }
}

#Seção 3 - Projeto 5: Classificador de Tipo de Smartphone (Consumo)

Código está no Projeto: Projeto5ClassificadordeTipoDeSmartphone

#O objetivo principal do projeto é construir um Classificador de Tipo de Smartphone usando o algoritmo de Árvore de Decisão ID3.

A finalidade prática é prever a decisão binária de um consumidor (se ele deve "Trocar" ou "Não Trocar" de smartphone) com base em suas características (Sistema Operacional, Tela, Câmera e Preço).

O objetivo acadêmico é demonstrar e implementar os conceitos fundamentais do aprendizado de máquina supervisionado, especificamente a construção de uma árvore de decisão baseada no critério de Ganho de Informação.

#Objetivo de Cada Programa (Módulos)
#O projeto foi dividido em cinco arquivos (programas/classes) seguindo o princípio da Modularidade, onde cada um tem uma responsabilidade clara no processo de construção do classificador.

#1. DataPoint.java
#Objetivo: Servir como a estrutura de dados fundamental (modelo).

Função: Representar uma única instância (linha) do conjunto de dados, ou seja, um smartphone. Ele armazena as características (features: SO, Tela, Câmera, Preço) e o resultado conhecido (targetLabel: Trocar/Não Trocar).

Papel no ID3: É o bloco de construção que o algoritmo usa para treinar e classificar.

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

#2. ID3Node.java
#Objetivo: Definir a estrutura hierárquica da árvore.

Função: Representar um nó na árvore de decisão. Um nó pode ser:

Um Nó de Decisão (Nó Interno), que testa um atributo (ex: "SO") e tem ramificações (children).

Um Nó Folha, que contém o rótulo final de classificação (ex: "Trocar").

Papel no ID3: Modelar a estrutura de IF/THEN que o algoritmo constrói recursivamente.

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

#3. ID3Metrics.java
#Objetivo: Fornecer as ferramentas matemáticas para o ID3 tomar decisões.

Função: Contém os métodos estáticos para calcular:

Entropia: Mede a impureza de um conjunto de dados (a mistura entre os rótulos "Trocar" e "Não Trocar").

Ganho de Informação: Calcula a redução esperada da impureza se um determinado atributo for escolhido para a divisão.

Papel no ID3: O Ganho de Informação é a métrica crucial usada em cada etapa para decidir qual é o melhor atributo para dividir o conjunto de dados.

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

#4. ID3Algorithm.java
#Objetivo: Implementar o processo recursivo de construção da árvore.
    
Função: Contém o método principal buildTree, que:
    
Identifica o melhor atributo usando o ID3Metrics.
    
Cria um nó de decisão com esse atributo.
    
Divide o conjunto de dados (usando o filterDataset de ID3Metrics) para cada valor do atributo.
    
Chama-se recursivamente para construir as subárvores.
    
Papel no ID3: É o "motor" do algoritmo, responsável por treinar o modelo a partir dos dados.
    
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

#5. SmartphoneClassifier.java (Classe Principal)
#Objetivo: Executar o projeto, treinar o modelo e testá-lo.

Função:

Define e carrega o conjunto de dados inicial.

Chama os métodos de ID3Metrics para analisar o Ganho inicial e mostrar a lógica de escolha da raiz.

Chama ID3Algorithm.buildTree para construir a árvore.

Implementa o método classify para atravessar a árvore e fazer a previsão para novos smartphones.

Papel no ID3: É o ponto de entrada que une todos os módulos e demonstra o resultado final do classificador.


import java.util.*;

/**
 * Ponto de entrada do programa.
 * Carrega o dataset, calcula o Ganho de Informação, constrói a árvore ID3 e
 * executa testes de classificação.
 */
public class SmartphoneClassifier { // CORREÇÃO: Não precisa estender ID3Algorithm

    private static final String[] ATTRIBUTE_NAMES = {"SO", "Tela", "Câmera", "Preço"};

    /**
     * Atravessa a árvore de decisão para classificar um novo ponto de dados.
     */
    public static String classify(ID3Node tree, DataPoint newDevice) {
        // 1. Nó Folha: Retorna o rótulo
        if (tree.label != null) {
            return tree.label; 
        }

        // 2. Nó Interno: Encontra o índice do atributo de decisão
        int attrIndex = -1;
        for (int i = 0; i < ATTRIBUTE_NAMES.length; i++) {
            if (ATTRIBUTE_NAMES[i].equals(tree.attributeName)) {
                attrIndex = i;
                break;
            }
        }

        if (attrIndex == -1) return "Erro de Atributo";

        // 3. Obtém o valor do atributo no novo dispositivo
        String value = newDevice.getFeature(attrIndex);

        // 4. Navega para o nó filho
        if (tree.children.containsKey(value)) {
            return classify(tree.children.get(value), newDevice);
        } else {
            // Caso em que o valor não foi visto no treinamento
            return "Decisão Indefinida (Valor não encontrado: " + value + ")";
        }
    }

    public static void main(String[] args) {
        // Dataset de 8 pontos (4 Trocar, 4 Não Trocar)
        List<DataPoint> dataset = Arrays.asList(
            new DataPoint(Arrays.asList("iOS", "Média", "Alta", "Caro"), "Não Trocar"),       
            new DataPoint(Arrays.asList("Android", "Grande", "Alta", "Razoável"), "Não Trocar"), 
            new DataPoint(Arrays.asList("iOS", "Pequena", "Média", "Razoável"), "Trocar"),    
            new DataPoint(Arrays.asList("Outro", "Média", "Baixa", "Barato"), "Trocar"),        
            new DataPoint(Arrays.asList("Android", "Grande", "Média", "Barato"), "Trocar"),   
            new DataPoint(Arrays.asList("iOS", "Média", "Baixa", "Caro"), "Não Trocar"),       
            new DataPoint(Arrays.asList("Android", "Grande", "Alta", "Razoável"), "Não Trocar"), 
            new DataPoint(Arrays.asList("Android", "Pequena", "Alta", "Caro"), "Trocar")      
        );

        // I. Foco na Discussão: Ganho de Informação Inicial (Raiz)
        // CORREÇÃO: Chamada direta aos métodos estáticos em ID3Metrics
        double initialEntropy = ID3Metrics.calculateEntropy(dataset); 
        System.out.printf("Entropia Inicial (E(S)): %.4f (Esperado: 1.0000)%n", initialEntropy);
        System.out.println("------------------------------------");

        Set<Integer> allIndices = new HashSet<>(Arrays.asList(0, 1, 2, 3)); 
        double maxGain = -1.0;
        String bestAttr = "";

        // Calcula o Ganho para cada atributo
        for (int i = 0; i < ATTRIBUTE_NAMES.length; i++) {
            // CORREÇÃO: Chamada direta aos métodos estáticos em ID3Metrics
            double gain = ID3Metrics.calculateGain(dataset, i); 
            System.out.printf("Ganho(%s): %.4f%n", ATTRIBUTE_NAMES[i], gain);
            if (gain > maxGain) {
                maxGain = gain;
                bestAttr = ATTRIBUTE_NAMES[i];
            }
        }
        System.out.println("------------------------------------");
        System.out.println("Melhor Atributo para Raiz: **" + bestAttr + "** (Ganho: " + String.format("%.4f", maxGain) + ")");

        // II. Construção e Classificação
        System.out.println("\n--- Construindo Árvore de Decisão ID3 ---");
        // CORREÇÃO: Chamada direta ao método estático em ID3Algorithm
        ID3Node decisionTree = ID3Algorithm.buildTree(dataset, allIndices); 
        System.out.println("Árvore construída. Raiz: " + decisionTree.attributeName);

        System.out.println("\n--- Testes de Classificação ---");

        // Teste 1: Dispositivo que deve levar a 'Não Trocar'
        DataPoint test1 = new DataPoint(Arrays.asList("iOS", "Média", "Alta", "Caro"), null);
        System.out.println("Teste 1 (iOS, Alta Câmera): Decisão -> " + classify(decisionTree, test1)); 

        // Teste 2: Dispositivo que deve levar a 'Trocar'
        DataPoint test2 = new DataPoint(Arrays.asList("Outro", "Pequena", "Baixa", "Barato"), null);
        System.out.println("Teste 2 (Baixa Câmera, Barato): Decisão -> " + classify(decisionTree, test2));
    }
}
