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