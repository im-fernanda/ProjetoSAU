// Dados de exemplo
const regionais = [
    { id: '1', name: 'Região 1' },
    { id: '2', name: 'Região 2' }
];

const comarcas = {
    '1': [
        { id: '1', name: 'Comarca A' },
        { id: '2', name: 'Comarca B' }
    ],
    '2': [
        { id: '3', name: 'Comarca C' },
        { id: '4', name: 'Comarca D' }
    ]
};

const unidades = {
    '1': [
        { id: '1', name: 'Unidade 1A' },
        { id: '2', name: 'Unidade 1B' }
    ],
    '2': [
        { id: '3', name: 'Unidade 2A' },
        { id: '4', name: 'Unidade 2B' }
    ],
    '3': [
        { id: '5', name: 'Unidade 3A' },
        { id: '6', name: 'Unidade 3B' }
    ],
    '4': [
        { id: '7', name: 'Unidade 4A' },
        { id: '8', name: 'Unidade 4B' }
    ]
};

function updateOptions(selectElement, options) {
    selectElement.innerHTML = '<option value="">Selecione...</option>';
    options.forEach(option => {
        const optionElement = document.createElement('option');
        optionElement.value = option.id;
        optionElement.textContent = option.name;
        selectElement.appendChild(optionElement);
    });
}

function handleRegionalChange() {
    const regionalId = document.getElementById('regional').value;
    const comarcaSelect = document.getElementById('comarca');
    const unidadeSelect = document.getElementById('unidade');

    if (regionalId) {
        updateOptions(comarcaSelect, comarcas[regionalId] || []);
        unidadeSelect.innerHTML = '<option value="">Selecione...</option>'; // Clear unidade options
    } else {
        comarcaSelect.innerHTML = '<option value="">Selecione...</option>';
        unidadeSelect.innerHTML = '<option value="">Selecione...</option>';
    }
}

function handleComarcaChange() {
    const comarcaId = document.getElementById('comarca').value;
    const unidadeSelect = document.getElementById('unidade');

    if (comarcaId) {
        updateOptions(unidadeSelect, unidades[comarcaId] || []);
    } else {
        unidadeSelect.innerHTML = '<option value="">Selecione...</option>';
    }
}

document.getElementById('regional').addEventListener('change', handleRegionalChange);
document.getElementById('comarca').addEventListener('change', handleComarcaChange);

// Inicializa as opções de região
document.addEventListener('DOMContentLoaded', () => {
    const regionalSelect = document.getElementById('regional');
    updateOptions(regionalSelect, regionais);
});
