// ====================================
// AUTH PAGES FUNCTIONALITY
// ====================================

document.addEventListener('DOMContentLoaded', function() {
    // Password Toggle Functionality
    initPasswordToggle();
    
    // Password Requirements Validation (for signup page)
    initPasswordValidation();
    
    // Form Validation
    initFormValidation();
    
    // Phone Mask
    initPhoneMask();
});

// Password Toggle Functionality
function initPasswordToggle() {
    const passwordToggles = document.querySelectorAll('.password-toggle');
    
    passwordToggles.forEach(toggle => {
        toggle.addEventListener('click', function() {
            const passwordField = this.parentElement.querySelector('input[type="password"], input[type="text"]');
            const icon = this.querySelector('i');
            
            if (passwordField.type === 'password') {
                passwordField.type = 'text';
                icon.classList.remove('fa-eye');
                icon.classList.add('fa-eye-slash');
            } else {
                passwordField.type = 'password';
                icon.classList.remove('fa-eye-slash');
                icon.classList.add('fa-eye');
            }
        });
    });
}

// Password Requirements Validation
function initPasswordValidation() {
    const passwordField = document.getElementById('password');
    const confirmPasswordField = document.getElementById('confirmPassword');
    const requirements = document.getElementById('password-requirements');
    
    if (!passwordField || !requirements) return;
    
    const reqLength = document.getElementById('req-length');
    const reqUppercase = document.getElementById('req-uppercase');
    const reqNumber = document.getElementById('req-number');
    const reqSpecial = document.getElementById('req-special');
    
    passwordField.addEventListener('input', function() {
        const password = this.value;
        
        // Check length
        validateRequirement(reqLength, password.length >= 8);
        
        // Check uppercase
        validateRequirement(reqUppercase, /[A-Z]/.test(password));
        
        // Check number
        validateRequirement(reqNumber, /\d/.test(password));
        
        // Check special character
        validateRequirement(reqSpecial, /[!@#$%^&*(),.?":{}|<>]/.test(password));
        
        // Validate confirm password if it has value
        if (confirmPasswordField && confirmPasswordField.value) {
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

// Form Validation
function initFormValidation() {
    const signinForm = document.getElementById('signin-form');
    const signupForm = document.getElementById('signup-form');
    
    if (signinForm) {
        signinForm.addEventListener('submit', handleSigninSubmit);
    }
    
    if (signupForm) {
        signupForm.addEventListener('submit', handleSignupSubmit);
    }
}

function handleSigninSubmit(e) {
    e.preventDefault();
    
    const email = document.getElementById('email').value;
    const password = document.getElementById('password').value;
    const remember = document.getElementById('remember').checked;
    
    // Validações básicas
    if (!email || !password) {
        showMessage('Por favor, preencha todos os campos obrigatórios.', 'error');
        if (!email) highlightField('email', true);
        if (!password) highlightField('password', true);
        return;
    }
    
    if (!isValidEmail(email)) {
        showMessage('Por favor, insira um e-mail válido.', 'error');
        highlightField('email', true);
        return;
    }
    
    // Show loading state
    const submitButton = e.target.querySelector('button[type="submit"]');
    const originalText = submitButton.innerHTML;
    submitButton.innerHTML = '<i class="fas fa-spinner fa-spin"></i> Entrando...';
    submitButton.disabled = true;
    
    // Simulate API call
    setTimeout(() => {
        // Here you would make the actual API call
        console.log('Login attempt:', { email, password, remember });
        
        // Show success message
        showMessage('Login realizado com sucesso! Redirecionando...', 'success');
        
        // Redirecionar para index.html após login bem-sucedido
        setTimeout(() => {
            window.location.href = 'index.html';
        }, 1500);
        
    }, 2000);
}

function handleSignupSubmit(e) {
    e.preventDefault();
    
    const formData = new FormData(e.target);
    const userData = Object.fromEntries(formData);
    
    // Validações básicas
    if (!userData.firstName || !userData.lastName || !userData.email || !userData.phone || !userData.password || !userData.confirmPassword) {
        showMessage('Por favor, preencha todos os campos obrigatórios.', 'error');
        highlightEmptyFields(userData);
        return;
    }
    
    if (!isValidEmail(userData.email)) {
        showMessage('Por favor, insira um e-mail válido.', 'error');
        highlightField('email', true);
        return;
    }
    
    if (!isValidPhone(userData.phone)) {
        showMessage('Por favor, insira um telefone válido.', 'error');
        highlightField('phone', true);
        return;
    }
    
    // Validate password requirements
    if (!validateAllPasswordRequirements(userData.password)) {
        showMessage('Por favor, atenda todos os requisitos de senha.', 'error');
        return;
    }
    
    // Validate password confirmation
    if (userData.password !== userData.confirmPassword) {
        showMessage('As senhas não coincidem.', 'error');
        return;
    }
    
    // Check terms acceptance
    if (!userData.terms) {
        showMessage('Você deve aceitar os termos de uso.', 'error');
        return;
    }
    
    // Show loading state
    const submitButton = e.target.querySelector('button[type="submit"]');
    const originalText = submitButton.innerHTML;
    submitButton.innerHTML = '<i class="fas fa-spinner fa-spin"></i> Criando conta...';
    submitButton.disabled = true;
    
    // Simulate API call
    setTimeout(() => {
        // Here you would make the actual API call
        console.log('Signup attempt:', userData);
        
        // Show success message
        showMessage('Conta criada com sucesso! Redirecionando para login...', 'success');
        
        // Redirecionar para signin.html após cadastro bem-sucedido
        setTimeout(() => {
            window.location.href = 'signin.html';
        }, 2000);
        
    }, 2500);
}

function validateAllPasswordRequirements(password) {
    return password.length >= 8 &&
           /[A-Z]/.test(password) &&
           /\d/.test(password) &&
           /[!@#$%^&*(),.?":{}|<>]/.test(password);
}

// Função para validar e-mail
function isValidEmail(email) {
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return emailRegex.test(email);
}

// Função para validar telefone brasileiro
function isValidPhone(phone) {
    // Remove todos os caracteres não numéricos
    const cleanPhone = phone.replace(/\D/g, '');
    // Verifica se tem 10 ou 11 dígitos (com ou sem o 9 adicional)
    return cleanPhone.length === 10 || cleanPhone.length === 11;
}

// Funções para destacar campos com erro
function highlightField(fieldId, hasError) {
    const field = document.getElementById(fieldId);
    if (field) {
        if (hasError) {
            field.classList.add('error');
            // Remove o erro após 3 segundos
            setTimeout(() => field.classList.remove('error'), 3000);
        } else {
            field.classList.remove('error');
        }
    }
}

function highlightEmptyFields(userData) {
    const requiredFields = ['firstName', 'lastName', 'email', 'phone', 'password', 'confirmPassword'];
    requiredFields.forEach(field => {
        if (!userData[field]) {
            highlightField(field, true);
        }
    });
}

// Phone Mask
function initPhoneMask() {
    const phoneField = document.getElementById('phone');
    if (!phoneField) return;

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

// Message Display Function
function showMessage(message, type = 'info') {
    // Remove existing messages
    const existingMessage = document.querySelector('.auth-message');
    if (existingMessage) {
        existingMessage.remove();
    }
    
    // Create message element
    const messageEl = document.createElement('div');
    messageEl.className = `auth-message auth-message-${type}`;
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
        z-index: 1000;
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

// Add animation keyframes
const style = document.createElement('style');
style.textContent = `
    @keyframes slideInRight {
        from {
            transform: translateX(100%);
            opacity: 0;
        }
        to {
            transform: translateX(0);
            opacity: 1;
        }
    }
    
    @keyframes slideOutRight {
        from {
            transform: translateX(0);
            opacity: 1;
        }
        to {
            transform: translateX(100%);
            opacity: 0;
        }
    }
    
    .form-group input.error {
        border-color: #ef4444;
        box-shadow: 0 0 0 3px rgba(239, 68, 68, 0.1);
    }
`;
document.head.appendChild(style);
