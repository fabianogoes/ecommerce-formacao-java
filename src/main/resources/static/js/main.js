// Simulação de dados dos produtos (será substituído pela API)
const mockProducts = [
    {
        id: 1,
        nome: "Smartphone Pro Max",
        descricao: "Smartphone com câmera de alta resolução e processador potente",
        preco: 2999.99,
        categoria: "eletronicos",
        icon: "fas fa-mobile-alt"
    },
    {
        id: 2,
        nome: "Camiseta Premium",
        descricao: "Camiseta 100% algodão com design exclusivo",
        preco: 89.90,
        categoria: "roupas",
        icon: "fas fa-tshirt"
    },
    {
        id: 3,
        nome: "Notebook Gamer",
        descricao: "Notebook para jogos com placa de vídeo dedicada",
        preco: 4599.99,
        categoria: "eletronicos",
        icon: "fas fa-laptop"
    },
    {
        id: 4,
        nome: "Cadeira Ergonômica",
        descricao: "Cadeira de escritório com apoio lombar e ajuste de altura",
        preco: 899.99,
        categoria: "casa",
        icon: "fas fa-chair"
    },
    {
        id: 5,
        nome: "Tênis Esportivo",
        descricao: "Tênis para corrida com tecnologia de amortecimento",
        preco: 299.99,
        categoria: "esportes",
        icon: "fas fa-running"
    },
    {
        id: 6,
        nome: "Fones Bluetooth",
        descricao: "Fones de ouvido sem fio com cancelamento de ruído",
        preco: 599.99,
        categoria: "eletronicos",
        icon: "fas fa-headphones"
    }
];

// Variáveis globais
let allProducts = [];
let currentCategory = 'todos';

// DOM Elements
const productsContainer = document.getElementById('products-container');
const categoryButtons = document.querySelectorAll('.category-btn');
const themeToggle = document.getElementById('theme-toggle');
const profileDropdown = document.getElementById('profile-dropdown');
const dropdownMenu = document.getElementById('dropdown-menu');

// Inicialização
document.addEventListener('DOMContentLoaded', function() {
    // Removido loadProducts() - produtos agora vêm do backend via Thymeleaf
    setupSmoothScroll();
    setupThemeToggle();
    setupDropdown();
    loadSavedTheme();
});

// Funções para interação com produtos (mantidas para os botões de ação)
function viewProduct(productId) {
    console.log(`Ver detalhes do produto ${productId}`);
    alert(`Visualizando produto ${productId}. Esta funcionalidade será conectada à API.`);
}

// Smooth scroll para navegação (apenas para âncoras internas)
function setupSmoothScroll() {
    document.querySelectorAll('a[href^="#"]').forEach(anchor => {
        // Ignorar links de dropdown que não são âncoras de seção
        if (anchor.closest('.dropdown-menu')) return;
        
        anchor.addEventListener('click', function (e) {
            const href = this.getAttribute('href');
            
            // Só aplicar smooth scroll para âncoras que existem na página
            const target = document.querySelector(href);
            if (target) {
                e.preventDefault();
                target.scrollIntoView({
                    behavior: 'smooth',
                    block: 'start'
                });
            }
            // Se não encontrar o target, deixa o comportamento padrão
        });
    });
}

// Theme Toggle Functions
function setupThemeToggle() {
    themeToggle.addEventListener('click', toggleTheme);
}

function toggleTheme() {
    const currentTheme = document.documentElement.getAttribute('data-theme');
    const newTheme = currentTheme === 'light' ? 'dark' : 'light';
    
    document.documentElement.setAttribute('data-theme', newTheme);
    localStorage.setItem('theme', newTheme);
    
    // Update button icon
    const iconElement = themeToggle.querySelector('i');
    if (newTheme === 'light') {
        iconElement.className = 'fas fa-moon';
        themeToggle.title = 'Alternar para tema escuro';
    } else {
        iconElement.className = 'fas fa-sun';
        themeToggle.title = 'Alternar para tema claro';
    }
}

function loadSavedTheme() {
    const savedTheme = localStorage.getItem('theme') || 'dark';
    document.documentElement.setAttribute('data-theme', savedTheme);
    
    // Update button icon based on saved theme
    const iconElement = themeToggle.querySelector('i');
    if (savedTheme === 'light') {
        iconElement.className = 'fas fa-moon';
        themeToggle.title = 'Alternar para tema escuro';
    } else {
        iconElement.className = 'fas fa-sun';
        themeToggle.title = 'Alternar para tema claro';
    }
}

// Dropdown Functions
function setupDropdown() {
    if (!profileDropdown || !dropdownMenu) return;

    profileDropdown.addEventListener('click', function(e) {
        e.preventDefault();
        e.stopPropagation();
        toggleDropdown();
    });

    // Close dropdown when clicking outside
    document.addEventListener('click', function(e) {
        if (!profileDropdown.contains(e.target) && !dropdownMenu.contains(e.target)) {
            closeDropdown();
        }
    });


}

function toggleDropdown() {
    const dropdown = profileDropdown.parentElement;
    dropdown.classList.toggle('active');
}

function closeDropdown() {
    const dropdown = profileDropdown.parentElement;
    dropdown.classList.remove('active');
}
