document.addEventListener('DOMContentLoaded', function() {
    const regionalSelect = document.getElementById('equipamento-regional');
    const comarcaSelect = document.getElementById('equipamento-comarca');
    const unidadeSelect = document.getElementById('equipamento-unidade');

    if (regionalSelect) {
        regionalSelect.addEventListener('change', function() {
            loadComarcas();
        });
    }

    if (comarcaSelect) {
        comarcaSelect.addEventListener('change', function() {
            loadUnidades();
        });
    }

    function loadComarcas() {
        const regionalId = regionalSelect.value;

        if (regionalId) {
            fetch(`/comarcasPorRegional?regionalId=${regionalId}`)
                .then(response => response.json())
                .then(data => {
                    if (comarcaSelect) {
                        comarcaSelect.innerHTML = '<option value="">Selecione...</option>';

                        data.forEach(comarca => {
                            const option = document.createElement('option');
                            option.value = comarca.id;
                            option.text = comarca.nome;
                            comarcaSelect.appendChild(option);
                        });
                    }
                })
                .catch(error => console.error('Erro ao carregar comarcas:', error));
        } else {
            if (comarcaSelect) {
                comarcaSelect.innerHTML = '<option value="">Selecione...</option>';
            }
        }
    }

    function loadUnidades() {
        const comarcaId = comarcaSelect.value;

        if (comarcaId) {
            fetch(`/unidadesPorComarca?comarcaId=${comarcaId}`)
                .then(response => response.json())
                .then(data => {
                    if (unidadeSelect) {
                        unidadeSelect.innerHTML = '<option value="">Selecione...</option>';

                        data.forEach(unidade => {
                            const option = document.createElement('option');
                            option.value = unidade.id;
                            option.text = unidade.nome;
                            unidadeSelect.appendChild(option);
                        });
                    }
                })
                .catch(error => console.error('Erro ao carregar unidades:', error));
        } else {
            if (unidadeSelect) {
                unidadeSelect.innerHTML = '<option value="">Selecione...</option>';
            }
        }
    }
});
