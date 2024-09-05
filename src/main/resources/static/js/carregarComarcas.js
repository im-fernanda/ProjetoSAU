// Função para carregar as comarcas com base no regional selecionado

function carregarComarcas() {
    const regionalId = document.getElementById('comarca-regional').value;
    const comarcaSelect = document.getElementById('comarca');

    // Limpa as opções anteriores
    comarcaSelect.innerHTML = '<option value="">Selecione...</option>';

    if (regionalId) {
        // Cria a URL para a requisição
        const url = `/comarcasPorRegional/${regionalId}`;

        // Faz a requisição para obter as comarcas
        fetch(url)
            .then(response => response.json())
            .then(comarcas => {
                comarcas.forEach(comarca => {
                    const option = document.createElement('option');
                    option.value = comarca.id;
                    option.textContent = comarca.nome;
                    comarcaSelect.appendChild(option);
                });
            })
            .catch(error => console.error('Erro ao carregar comarcas:', error));
    }
}

// Adiciona o listener de evento quando o DOM estiver completamente carregado
document.addEventListener('DOMContentLoaded', () => {
    document.getElementById('comarca-regional').addEventListener('change', carregarComarcas);
});
