// ====================================
// ADMIN PANEL FUNCTIONALITY
// ====================================

let mockCategorias = [
    { id: 1, nome: "Eletrônicos", descricao: "Produtos eletrônicos e tecnologia", produtos: 3 },
    { id: 2, nome: "Roupas", descricao: "Vestuário e acessórios", produtos: 1 },
    { id: 3, nome: "Casa & Jardim", descricao: "Produtos para casa e jardim", produtos: 1 },
    { id: 4, nome: "Esportes", descricao: "Artigos esportivos e fitness", produtos: 1 }
];

let mockUsuarios = [
    { id: 1, nome: "João Silva", email: "joao@email.com", telefone: "(11) 99999-1234", status: "ativo", dataCadastro: "2024-01-15" },
    { id: 2, nome: "Maria Santos", email: "maria@email.com", telefone: "(11) 99999-5678", status: "ativo", dataCadastro: "2024-01-20" },
    { id: 3, nome: "Pedro Costa", email: "pedro@email.com", telefone: "(11) 99999-9876", status: "bloqueado", dataCadastro: "2024-02-10" },
    { id: 4, nome: "Ana Oliveira", email: "ana@email.com", telefone: "(11) 99999-4321", status: "inativo", dataCadastro: "2024-02-15" }
];

let mockPedidos = [
    { 
        id: 1001, 
        cliente: "João Silva", 
        email: "joao@email.com",
        produtos: ["Smartphone Pro Max", "Camiseta Premium"], 
        total: 3089.89, 
        status: "entregue", 
        dataPedido: "2024-07-28",
        dataEntrega: "2024-08-01",
        endereco: "Rua das Flores, 123 - São Paulo, SP"
    },
    { 
        id: 1002, 
        cliente: "Maria Santos", 
        email: "maria@email.com",
        produtos: ["Notebook Gamer"], 
        total: 4599.99, 
        status: "enviado", 
        dataPedido: "2024-07-30",
        dataEntrega: "2024-08-03",
        endereco: "Av. Paulista, 456 - São Paulo, SP"
    },
    { 
        id: 1003, 
        cliente: "Pedro Costa", 
        email: "pedro@email.com",
        produtos: ["Tênis Esportivo", "Cadeira Ergonômica"], 
        total: 1199.98, 
        status: "processando", 
        dataPedido: "2024-08-01",
        dataEntrega: "2024-08-05",
        endereco: "Rua Augusta, 789 - São Paulo, SP"
    },
    { 
        id: 1004, 
        cliente: "Ana Oliveira", 
        email: "ana@email.com",
        produtos: ["Smartphone Pro Max"], 
        total: 2999.99, 
        status: "pendente", 
        dataPedido: "2024-08-02",
        dataEntrega: "2024-08-06",
        endereco: "Rua da Consolação, 321 - São Paulo, SP"
    },
    { 
        id: 1005, 
        cliente: "Carlos Lima", 
        email: "carlos@email.com",
        produtos: ["Camiseta Premium", "Tênis Esportivo"], 
        total: 389.89, 
        status: "cancelado", 
        dataPedido: "2024-07-25",
        dataEntrega: null,
        endereco: "Rua Oscar Freire, 654 - São Paulo, SP"
    }
];

// Global Variables
let editingItem = null;
let editingType = null;

// Initialize Admin Panel
document.addEventListener('DOMContentLoaded', function() {
    setupThemeToggle();
    setupModals();
    setupFilters();
    setupLogout();
    loadDashboard();
    loadCategorias();
    // loadUsuarios(); // Comentado - usuários vêm do backend via Thymeleaf
    // loadPedidos(); // Comentado - pedidos vêm do backend via Thymeleaf

    // Initialize password and phone features
    initPasswordFeatures();
});

// Logout Setup
function setupLogout() {
    const logoutBtn = document.getElementById('logout-btn');
    if (logoutBtn) {
        logoutBtn.addEventListener('click', function(e) {
            e.preventDefault();
            if (confirm('Deseja realmente sair do painel administrativo?')) {
                // Limpar dados de sessão se necessário
                localStorage.removeItem('adminLoggedIn');
                // Redirecionar para a página inicial
                window.location.href = 'index.html';
            }
        });
    }
}

// Theme Toggle Setup
function setupThemeToggle() {
    const themeToggle = document.getElementById('theme-toggle');
    if (themeToggle) {
        themeToggle.addEventListener('click', toggleTheme);
        loadSavedTheme();
    }
}

function toggleTheme() {
    const currentTheme = document.documentElement.getAttribute('data-theme');
    const newTheme = currentTheme === 'light' ? 'dark' : 'light';
    
    document.documentElement.setAttribute('data-theme', newTheme);
    localStorage.setItem('theme', newTheme);
    
    // Update button icon
    const iconElement = document.querySelector('#theme-toggle i');
    if (newTheme === 'light') {
        iconElement.className = 'fas fa-moon';
    } else {
        iconElement.className = 'fas fa-sun';
    }
}

function loadSavedTheme() {
    const savedTheme = localStorage.getItem('theme') || 'dark';
    document.documentElement.setAttribute('data-theme', savedTheme);
    
    const iconElement = document.querySelector('#theme-toggle i');
    if (savedTheme === 'light') {
        iconElement.className = 'fas fa-moon';
    } else {
        iconElement.className = 'fas fa-sun';
    }
}

// Dashboard Functions
function loadDashboard() {
    // Só atualiza stats se estiver na seção dashboard
    if (document.getElementById('total-usuarios')) {
        updateDashboardStats();
    }
}

function updateDashboardStats() {
    const totalProdutosEl = document.getElementById('total-produtos');
    const totalUsuariosEl = document.getElementById('total-usuarios');
    const totalPedidosEl = document.getElementById('total-pedidos');
    const totalVendasEl = document.getElementById('total-vendas');

    if (totalUsuariosEl) totalUsuariosEl.textContent = mockUsuarios.length;
    if (totalPedidosEl) totalPedidosEl.textContent = mockPedidos.length;

    // Calcular total de vendas
    const totalVendas = mockPedidos
        .filter(p => p.status === 'entregue')
        .reduce((sum, pedido) => sum + pedido.total, 0);

    if (totalVendasEl) totalVendasEl.textContent = `R$ ${totalVendas.toFixed(2).replace('.', ',')}`;
}

function getCategoriaName(categoria) {
    const categoriaMap = {
        'eletronicos': 'Eletrônicos',
        'roupas': 'Roupas',
        'casa': 'Casa & Jardim',
        'esportes': 'Esportes'
    };
    return categoriaMap[categoria] || categoria;
}

// Categories Management
function loadCategorias() {
    const tbody = document.getElementById('categorias-tbody');
    if (!tbody) return; // Se não existe a tabela, não carrega

    tbody.innerHTML = '';

    mockCategorias.forEach(categoria => {
        const row = createCategoriaRow(categoria);
        tbody.appendChild(row);
    });
}

function createCategoriaRow(categoria) {
    const row = document.createElement('tr');
    
    row.innerHTML = `
        <td>#${categoria.id}</td>
        <td>${categoria.nome}</td>
        <td>${categoria.descricao}</td>
        <td>${categoria.produtos} produtos</td>
        <td>
            <div class="action-buttons">
                <button class="btn-icon btn-edit" onclick="editCategoria(${categoria.id})" title="Editar">
                    <i class="fas fa-edit"></i>
                </button>
                <button class="btn-icon btn-delete" onclick="deleteCategoria(${categoria.id})" title="Excluir">
                    <i class="fas fa-trash"></i>
                </button>
            </div>
        </td>
    `;
    
    return row;
}

// Users Management
function loadUsuarios() {
    const tbody = document.getElementById('usuarios-tbody');
    if (!tbody) return; // Se não existe a tabela, não carrega

    tbody.innerHTML = '';

    mockUsuarios.forEach(usuario => {
        const row = createUsuarioRow(usuario);
        tbody.appendChild(row);
    });
}

function createUsuarioRow(usuario) {
    const row = document.createElement('tr');
    const statusClass = `status-${usuario.status}`;
    
    row.innerHTML = `
        <td>#${usuario.id}</td>
        <td>${usuario.nome}</td>
        <td>${usuario.email}</td>
        <td>${usuario.telefone}</td>
        <td><span class="status-badge ${statusClass}">${usuario.status}</span></td>
        <td>${formatDate(usuario.dataCadastro)}</td>
        <td>
            <div class="action-buttons">
                <button class="btn-icon btn-edit" onclick="editUsuario(${usuario.id})" title="Editar">
                    <i class="fas fa-edit"></i>
                </button>
                <button class="btn-icon btn-delete" onclick="deleteUsuario(${usuario.id})" title="Excluir">
                    <i class="fas fa-trash"></i>
                </button>
            </div>
        </td>
    `;
    
    return row;
}

// Orders Management
// Orders Management - Comentado pois pedidos são gerenciados pelo backend
/*
function loadPedidos() {
    const tbody = document.getElementById('pedidos-tbody');
    if (!tbody) return;

    tbody.innerHTML = '';

    mockPedidos.forEach(pedido => {
        const row = createPedidoRow(pedido);
        tbody.appendChild(row);
    });
}
*/

/*
function createPedidoRow(pedido) {
    ...
}

function viewPedido(id) {
    const pedido = mockPedidos.find(p => p.id === id);
    if (!pedido) return;
    
    const content = `
        <div class="pedido-details">
            <div class="detail-section">
                <h4><i class="fas fa-info-circle"></i> Informações do Pedido</h4>
                <div class="detail-grid">
                    <div class="detail-item">
                        <label>Número do Pedido:</label>
                        <span>#${pedido.id}</span>
                    </div>
                    <div class="detail-item">
                        <label>Data do Pedido:</label>
                        <span>${formatDate(pedido.dataPedido)}</span>
                    </div>
                    <div class="detail-item">
                        <label>Status:</label>
                        <span class="status-badge status-${pedido.status.replace(' ', '-')}">${pedido.status}</span>
                    </div>
                    <div class="detail-item">
                        <label>Total:</label>
                        <span class="total-value">R$ ${pedido.total.toFixed(2).replace('.', ',')}</span>
                    </div>
                </div>
            </div>
            
            <div class="detail-section">
                <h4><i class="fas fa-user"></i> Dados do Cliente</h4>
                <div class="detail-grid">
                    <div class="detail-item">
                        <label>Nome:</label>
                        <span>${pedido.cliente}</span>
                    </div>
                    <div class="detail-item">
                        <label>E-mail:</label>
                        <span>${pedido.email}</span>
                    </div>
                    <div class="detail-item full-width">
                        <label>Endereço de Entrega:</label>
                        <span>${pedido.endereco}</span>
                    </div>
                </div>
            </div>
            
            <div class="detail-section">
                <h4><i class="fas fa-box"></i> Produtos</h4>
                <div class="produtos-list">
                    ${pedido.produtos.map(produto => `
                        <div class="produto-item">
                            <i class="fas fa-package"></i>
                            <span>${produto}</span>
                        </div>
                    `).join('')}
                </div>
            </div>
            
            ${pedido.dataEntrega ? `
                <div class="detail-section">
                    <h4><i class="fas fa-truck"></i> Entrega</h4>
                    <div class="detail-item">
                        <label>Data de Entrega:</label>
                        <span>${formatDate(pedido.dataEntrega)}</span>
                    </div>
                </div>
            ` : ''}
        </div>
    `;
    
    const footer = `
        <button type="button" class="btn btn-outline" onclick="closeModal()">Fechar</button>
        <button type="button" class="btn btn-primary" onclick="editPedidoStatus(${pedido.id})">Alterar Status</button>
    `;
    
    const modal = createModal(`Detalhes do Pedido #${pedido.id}`, content, footer);
    showModal(modal);
}

function editPedidoStatus(id) {
    const pedido = mockPedidos.find(p => p.id === id);
    if (!pedido) return;
    
    editingItem = pedido;
    editingType = 'pedido';
    
    const content = `
        <div class="status-change-form">
            <div class="form-group">
                <label for="pedido-status">Status do Pedido</label>
                <select id="pedido-status" required>
                    <option value="pendente" ${pedido.status === 'pendente' ? 'selected' : ''}>Pendente</option>
                    <option value="processando" ${pedido.status === 'processando' ? 'selected' : ''}>Processando</option>
                    <option value="enviado" ${pedido.status === 'enviado' ? 'selected' : ''}>Enviado</option>
                    <option value="entregue" ${pedido.status === 'entregue' ? 'selected' : ''}>Entregue</option>
                    <option value="cancelado" ${pedido.status === 'cancelado' ? 'selected' : ''}>Cancelado</option>
                </select>
            </div>
            
            <div class="current-info">
                <h4>Informações do Pedido</h4>
                <p><strong>Cliente:</strong> ${pedido.cliente}</p>
                <p><strong>Total:</strong> R$ ${pedido.total.toFixed(2).replace('.', ',')}</p>
                <p><strong>Data:</strong> ${formatDate(pedido.dataPedido)}</p>
            </div>
        </div>
    `;
    
    const footer = `
        <button type="button" class="btn btn-outline" onclick="closeModal()">Cancelar</button>
        <button type="button" class="btn btn-primary" onclick="savePedidoStatus()">Atualizar Status</button>
    `;
    
    const modal = createModal(`Alterar Status - Pedido #${pedido.id}`, content, footer);
    showModal(modal);
}

function savePedidoStatus() {
    ...
}
*/

function formatDate(dateString) {
    const date = new Date(dateString);
    return date.toLocaleDateString('pt-BR');
}

// Modal Management
function setupModals() {
    // Add event listeners for add buttons
    const addProdutoBtn = document.getElementById('add-produto-btn');
    const addCategoriaBtn = document.getElementById('add-categoria-btn');
    const addUsuarioBtn = document.getElementById('add-usuario-btn');

    if (addProdutoBtn) {
        addProdutoBtn.addEventListener('click', () => openProdutoModal());
    }
    if (addCategoriaBtn) {
        addCategoriaBtn.addEventListener('click', () => openCategoriaModal());
    }
    // addUsuarioBtn não precisa de modal - formulário está na página
}

function createModal(title, content, footer) {
    const modal = document.createElement('div');
    modal.className = 'modal-overlay';
    modal.innerHTML = `
        <div class="modal">
            <div class="modal-header">
                <h3 class="modal-title">${title}</h3>
                <button class="modal-close" onclick="closeModal()">
                    <i class="fas fa-times"></i>
                </button>
            </div>
            <div class="modal-body">
                ${content}
            </div>
            <div class="modal-footer">
                ${footer}
            </div>
        </div>
    `;
    
    return modal;
}

function showModal(modal) {
    const container = document.getElementById('modal-container');
    container.innerHTML = '';
    container.appendChild(modal);
    
    setTimeout(() => {
        modal.classList.add('active');
    }, 10);
    
    // Close on backdrop click
    modal.addEventListener('click', function(e) {
        if (e.target === modal) {
            closeModal();
        }
    });
}

function closeModal() {
    const modal = document.querySelector('.modal-overlay');
    if (modal) {
        modal.classList.remove('active');
        setTimeout(() => {
            modal.remove();
            editingItem = null;
            editingType = null;
        }, 300);
    }
}

// Category Modal
function openCategoriaModal(categoria = null) {
    editingItem = categoria;
    editingType = 'categoria';

    const isEdit = categoria !== null;
    const title = isEdit ? 'Editar Categoria' : 'Adicionar Categoria';

    const content = `
        <form id="categoria-form" class="form-grid two-columns">
            <div class="form-group">
                <label for="categoria-nome">Nome da Categoria</label>
                <input type="text" id="categoria-nome" value="${categoria ? categoria.nome : ''}" required>
            </div>
            <div class="form-group">
                <label for="categoria-descricao">Descrição</label>
                <input type="text" id="categoria-descricao" value="${categoria ? categoria.descricao : ''}" required>
            </div>
        </form>
    `;

    const footer = `
        <button type="button" class="btn btn-outline" onclick="closeModal()">Cancelar</button>
        <button type="button" class="btn btn-primary" onclick="saveCategoria()">${isEdit ? 'Atualizar' : 'Adicionar'}</button>
    `;

    const modal = createModal(title, content, footer);
    showModal(modal);
}

function saveCategoria() {
    const nome = document.getElementById('categoria-nome').value;
    const descricao = document.getElementById('categoria-descricao').value;

    if (!nome || !descricao) {
        showMessage('Por favor, preencha todos os campos obrigatórios.', 'error');
        return;
    }

    if (editingItem) {
        // Edit existing category
        const index = mockCategorias.findIndex(c => c.id === editingItem.id);
        if (index !== -1) {
            mockCategorias[index] = {
                ...mockCategorias[index],
                nome,
                descricao
            };
        }
        showMessage('Categoria atualizada com sucesso!', 'success');
    } else {
        // Add new category
        const newId = Math.max(...mockCategorias.map(c => c.id)) + 1;
        mockCategorias.push({
            id: newId,
            nome,
            descricao,
            produtos: 0
        });
        showMessage('Categoria adicionada com sucesso!', 'success');
    }

    loadCategorias();
    closeModal();
}

// User Modal - Comentado pois usuários são gerenciados pelo backend
// O formulário está na própria página admin.html, não usa modal
/*
function openUsuarioModal(usuario = null) {
    ...
}

function saveUsuario() {
    ...
}
*/

// Funções de edição e exclusão são gerenciadas pelo backend via Thymeleaf
// editUsuario e deleteUsuario não são mais necessários aqui

// Filters Setup
function setupFilters() {
    // Product filters
    const searchProdutos = document.getElementById('search-produtos');
    const filterCategoria = document.getElementById('filter-categoria');
    
    if (searchProdutos) {
        searchProdutos.addEventListener('input', filterProdutos);
    }
    if (filterCategoria) {
        filterCategoria.addEventListener('change', filterProdutos);
    }
    
    // User filters
    const searchUsuarios = document.getElementById('search-usuarios');
    // filterStatus gerenciado pelo Thymeleaf via onchange no HTML

    if (searchUsuarios) {
        searchUsuarios.addEventListener('input', filterUsuarios);
    }
    
    // Order filters
    const searchPedidos = document.getElementById('search-pedidos');
    // filterStatusPedido gerenciado pelo Thymeleaf via onchange no HTML

    if (searchPedidos) {
        searchPedidos.addEventListener('input', filterPedidos);
    }
}

function filterProdutos() {
    const searchEl = document.getElementById('search-produtos');
    const categoriaEl = document.getElementById('filter-categoria');
    const tbody = document.getElementById('produtos-tbody');

    if (!searchEl || !categoriaEl || !tbody) return;

    const search = searchEl.value.toLowerCase();
    const categoria = categoriaEl.value;

    // Produtos virão do backend via Thymeleaf, não usaremos mockProdutos
    console.log('Filtro de produtos:', search, categoria);
}

function filterUsuarios() {
    const searchEl = document.getElementById('search-usuarios');
    const tbody = document.getElementById('usuarios-tbody');

    if (!searchEl || !tbody) return;

    const search = searchEl.value.toLowerCase().trim();
    const rows = tbody.querySelectorAll('tr');

    // Se não há busca, mostra todas as linhas
    if (search === '') {
        rows.forEach(row => {
            row.style.display = '';
        });
        return;
    }

    // Filtra apenas se há texto de busca
    rows.forEach(row => {
        const nome = row.cells[1]?.textContent.toLowerCase() || '';
        const email = row.cells[2]?.textContent.toLowerCase() || '';

        if (nome.includes(search) || email.includes(search)) {
            row.style.display = '';
        } else {
            row.style.display = 'none';
        }
    });
}

function filterPedidos() {
    const searchEl = document.getElementById('search-pedidos');
    const tbody = document.getElementById('pedidos-tbody');

    if (!searchEl || !tbody) return;

    const search = searchEl.value.toLowerCase().trim();
    const rows = tbody.querySelectorAll('tr');

    // Se não há busca, mostra todas as linhas
    if (search === '') {
        rows.forEach(row => {
            row.style.display = '';
        });
        return;
    }

    // Filtra apenas se há texto de busca
    rows.forEach(row => {
        const cliente = row.cells[1]?.textContent.toLowerCase() || '';
        const id = row.cells[0]?.textContent.toLowerCase() || '';

        if (cliente.includes(search) || id.includes(search)) {
            row.style.display = '';
        } else {
            row.style.display = 'none';
        }
    });
}

// Message Display Function (reuse from auth.js)
function showMessage(message, type = 'info') {
    // Remove existing messages
    const existingMessage = document.querySelector('.admin-message');
    if (existingMessage) {
        existingMessage.remove();
    }
    
    // Create message element
    const messageEl = document.createElement('div');
    messageEl.className = `admin-message admin-message-${type}`;
    messageEl.innerHTML = `
        <i class="fas ${getMessageIcon(type)}"></i>
        <span>${message}</span>
        <button class="message-close" onclick="this.parentElement.remove()">
            <i class="fas fa-times"></i>
        </button>
    `;
    
    // Add styles
    messageEl.style.cssText = `
        position: fixed;
        top: 20px;
        right: 20px;
        z-index: 2000;
        background: ${getMessageColor(type)};
        color: white;
        padding: 1rem 1.5rem;
        border-radius: 8px;
        box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
        display: flex;
        align-items: center;
        gap: 0.75rem;
        font-size: 0.9rem;
        font-weight: 500;
        max-width: 400px;
        animation: slideInRight 0.3s ease-out;
    `;
    
    // Add close button styles
    const closeBtn = messageEl.querySelector('.message-close');
    closeBtn.style.cssText = `
        background: none;
        border: none;
        color: white;
        cursor: pointer;
        padding: 0.25rem;
        margin-left: auto;
        opacity: 0.8;
        transition: opacity 0.3s ease;
    `;
    
    document.body.appendChild(messageEl);
    
    // Auto remove after 5 seconds
    setTimeout(() => {
        if (messageEl.parentElement) {
            messageEl.style.animation = 'slideOutRight 0.3s ease-in forwards';
            setTimeout(() => messageEl.remove(), 300);
        }
    }, 5000);
}

function getMessageIcon(type) {
    const icons = {
        success: 'fa-check-circle',
        error: 'fa-exclamation-circle',
        warning: 'fa-exclamation-triangle',
        info: 'fa-info-circle'
    };
    return icons[type] || icons.info;
}

function getMessageColor(type) {
    const colors = {
        success: '#10b981',
        error: '#ef4444',
        warning: '#f59e0b',
        info: '#3b82f6'
    };
    return colors[type] || colors.info;
}

// ====================================
// PASSWORD VALIDATION AND PHONE MASK
// ====================================

// Initialize all password and phone features
function initPasswordFeatures() {
    // Use setTimeout to ensure DOM is fully loaded
    setTimeout(() => {
        initPasswordToggle();
        initPasswordValidation();
        initPhoneMask();
    }, 100);
}

// Password Toggle Functionality
function initPasswordToggle() {
    const passwordToggles = document.querySelectorAll('.password-toggle');

    passwordToggles.forEach(toggle => {
        toggle.addEventListener('click', function() {
            const passwordField = this.parentElement.querySelector('input[type="password"], input[type="text"]');
            const icon = this.querySelector('i');

            if (passwordField && passwordField.type === 'password') {
                passwordField.type = 'text';
                icon.classList.remove('fa-eye');
                icon.classList.add('fa-eye-slash');
            } else if (passwordField) {
                passwordField.type = 'password';
                icon.classList.remove('fa-eye-slash');
                icon.classList.add('fa-eye');
            }
        });
    });
}

// Password Requirements Validation
function initPasswordValidation() {
    const passwordField = document.getElementById('usuario-password');
    const confirmPasswordField = document.getElementById('usuario-confirm-password');
    const requirements = document.getElementById('usuario-password-requirements');

    if (!passwordField || !requirements) {
        console.log('Elementos de validação de senha não encontrados');
        return;
    }

    const reqLength = document.getElementById('usuario-req-length');
    const reqUppercase = document.getElementById('usuario-req-uppercase');
    const reqNumber = document.getElementById('usuario-req-number');
    const reqSpecial = document.getElementById('usuario-req-special');

    if (!reqLength || !reqUppercase || !reqNumber || !reqSpecial) {
        console.log('Elementos de requisitos não encontrados');
        return;
    }

    console.log('Iniciando validação de senha para admin');

    passwordField.addEventListener('input', function() {
        const password = this.value;
        console.log('Validando senha:', password);

        // Check length
        validateRequirement(reqLength, password.length >= 8);

        // Check uppercase
        validateRequirement(reqUppercase, /[A-Z]/.test(password));

        // Check number
        validateRequirement(reqNumber, /\d/.test(password));

        // Check special character
        validateRequirement(reqSpecial, /[!@#$%^&*(),.?":{}|<>]/.test(password));

        // Validate confirm password if it has value
        const confirmField = document.getElementById('usuario-confirm-password');
        if (confirmField && confirmField.value) {
            validatePasswordMatch();
        }
    });

    if (confirmPasswordField) {
        confirmPasswordField.addEventListener('input', validatePasswordMatch);
    }

    function validateRequirement(element, isValid) {
        const icon = element.querySelector('i');
        if (isValid) {
            element.classList.add('valid');
            icon.classList.remove('fa-times');
            icon.classList.add('fa-check');
        } else {
            element.classList.remove('valid');
            icon.classList.remove('fa-check');
            icon.classList.add('fa-times');
        }
    }

    function validatePasswordMatch() {
        const password = passwordField.value;
        const confirmPassword = confirmPasswordField.value;

        if (confirmPassword && password !== confirmPassword) {
            confirmPasswordField.setCustomValidity('As senhas não coincidem');
            confirmPasswordField.classList.add('error');
        } else {
            confirmPasswordField.setCustomValidity('');
            confirmPasswordField.classList.remove('error');
        }
    }
}

// Phone Mask
function initPhoneMask() {
    const phoneField = document.getElementById('usuario-phone');
    if (!phoneField) {
        console.log('Campo de telefone não encontrado');
        return;
    }

    console.log('Iniciando máscara de telefone');

    phoneField.addEventListener('input', function(e) {
        let value = e.target.value.replace(/\D/g, '');

        // Limita a 11 dígitos
        if (value.length > 11) {
            value = value.substring(0, 11);
        }

        if (value.length >= 11) {
            value = value.replace(/(\d{2})(\d{5})(\d{4})/, '($1) $2-$3');
        } else if (value.length >= 7) {
            value = value.replace(/(\d{2})(\d{4})(\d{0,4})/, '($1) $2-$3');
        } else if (value.length >= 3) {
            value = value.replace(/(\d{2})(\d{0,5})/, '($1) $2');
        } else if (value.length >= 1) {
            value = value.replace(/(\d{0,2})/, '($1');
        }

        e.target.value = value;
    });
}
