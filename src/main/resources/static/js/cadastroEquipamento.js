// Aguarda o carregamento completo do conteúdo do DOM antes de executar o código
document.addEventListener('DOMContentLoaded', function() {
    // Obtém referências para os elementos de seleção dos regionais, comarcas e unidades
    const regionalSelect = document.getElementById('equipamento-regional');
    const comarcaSelect = document.getElementById('equipamento-comarca');
    const unidadeSelect = document.getElementById('equipamento-unidade');

    // Adiciona um listener para o evento de mudança na seleção de regionais
    if (regionalSelect) {
        regionalSelect.addEventListener('change', function() {
            // Chama a função para carregar as comarcas quando o regional for alterado
            loadComarcas();
        });
    }

    // Adiciona um listener para o evento de mudança na seleção de comarcas
    if (comarcaSelect) {
        comarcaSelect.addEventListener('change', function() {
            // Chama a função para carregar as unidades quando a comarca for alterada
            loadUnidades();
        });
    }

    // Função para carregar comarcas com base no ID do regional selecionado
    function loadComarcas() {
        const regionalId = regionalSelect.value;

        if (regionalId) {
            // Faz uma requisição para obter as comarcas associadas ao regional selecionado
            fetch(`/comarcasPorRegional?regionalId=${regionalId}`)
                .then(response => response.json()) // Converte a resposta para JSON
                .then(data => {
                    if (comarcaSelect) {
                        // Limpa as opções atuais e adiciona uma opção padrão
                        comarcaSelect.innerHTML = '<option value="">Selecione...</option>';

                        // Itera sobre os dados das comarcas e adiciona cada uma como uma nova opção
                        data.forEach(comarca => {
                            const option = document.createElement('option');
                            option.value = comarca.id;
                            option.text = comarca.nome;
                            comarcaSelect.appendChild(option);
                        });
                    }
                })
                .catch(error => console.error('Erro ao carregar comarcas:', error)); // Exibe um erro no console em caso de falha
        } else {
            // Se nenhum regional estiver selecionado, limpa as opções de comarcas
            if (comarcaSelect) {
                comarcaSelect.innerHTML = '<option value="">Selecione...</option>';
            }
        }
    }

    // Função para carregar unidades com base no ID da comarca selecionada
    function loadUnidades() {
        const comarcaId = comarcaSelect.value;

        if (comarcaId) {
            // Faz uma requisição para obter as unidades associadas à comarca selecionada
            fetch(`/unidadesPorComarca?comarcaId=${comarcaId}`)
                .then(response => response.json()) // Converte a resposta para JSON
                .then(data => {
                    if (unidadeSelect) {
                        // Limpa as opções atuais e adiciona uma opção padrão
                        unidadeSelect.innerHTML = '<option value="">Selecione...</option>';

                        // Itera sobre os dados das unidades e adiciona cada uma como uma nova opção
                        data.forEach(unidade => {
                            const option = document.createElement('option');
                            option.value = unidade.id;
                            option.text = unidade.nome;
                            unidadeSelect.appendChild(option);
                        });
                    }
                })
                .catch(error => console.error('Erro ao carregar unidades:', error)); // Exibe um erro no console em caso de falha
        } else {
            // Se nenhuma comarca estiver selecionada, limpa as opções de unidades
            if (unidadeSelect) {
                unidadeSelect.innerHTML = '<option value="">Selecione...</option>';
            }
        }
    }
});
