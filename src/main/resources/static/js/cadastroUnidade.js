// Função para carregar comarcas via AJAX
function loadComarcas() {
    const regionalId = document.getElementById('comarca-regional').value;

    if (regionalId) {
        fetch(`/comarcasPorRegional?regionalId=${regionalId}`)
            .then(response => response.json())
            .then(data => {
                const comarcaSelect = document.getElementById('comarca');
                comarcaSelect.innerHTML = '<option value="">Selecione...</option>';

                data.forEach(comarca => {
                    const option = document.createElement('option');
                    option.value = comarca.id;
                    option.text = comarca.nome;
                    comarcaSelect.appendChild(option);
                });
            })
            .catch(error => console.error('Erro ao carregar comarcas:', error));
    } else {
        document.getElementById('comarca').innerHTML = '<option value="">Selecione...</option>';
    }
}
