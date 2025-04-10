// Tema
document.querySelectorAll('.theme-btn').forEach(btn => {
    btn.addEventListener('click', function() {
        document.querySelectorAll('.theme-btn').forEach(b => b.classList.remove('active'));
        this.classList.add('active');
        
        const theme = this.dataset.theme;
        document.body.className = theme === 'oscuro' ? 'theme-dark' : '';
        localStorage.setItem('theme', theme);
    });
});

// Tamaño de fuente
let fontSize = 16;
function adjustFontSize(action) {
    const html = document.documentElement;
    
    if(action === 'increase') fontSize += 1;
    if(action === 'decrease') fontSize -= 1;
    if(action === 'reset') fontSize = 16;
    
    fontSize = Math.min(Math.max(12, fontSize), 24);
    html.style.fontSize = `${fontSize}px`;
    localStorage.setItem('fontSize', fontSize);
}

// Idioma
document.querySelectorAll('.language-btn').forEach(btn => {
    btn.addEventListener('click', function() {
        document.querySelectorAll('.language-btn').forEach(b => b.classList.remove('active'));
        this.classList.add('active');
        
        const lang = this.dataset.lang;
        fetch(`/cambiar-idioma?lang=${lang}`, { method: 'POST' })
            .then(() => location.reload());
    });
});

// Imagen del menú
document.getElementById('menuImageUpload').addEventListener('change', function(e) {
    const reader = new FileReader();
    reader.onload = function(e) {
        const preview = document.getElementById('imagePreview');
        preview.innerHTML = `<img src="${e.target.result}" class="img-fluid rounded" style="max-height: 100px">`;
        
        // Aplicar al menú
        document.querySelector('.sidebar').style.backgroundImage = `url(${e.target.result})`;
        localStorage.setItem('menuBackground', e.target.result);
    }
    reader.readAsDataURL(e.target.files[0]);
});

// Cargar config guardada al iniciar
window.addEventListener('load', () => {
    // Tema
    const savedTheme = localStorage.getItem('theme') || 'claro';
    document.querySelector(`[data-theme="${savedTheme}"]`).click();
    
    // Tamaño fuente
    const savedSize = localStorage.getItem('fontSize') || 16;
    document.documentElement.style.fontSize = `${savedSize}px`;
    
    // Imagen
    const savedImage = localStorage.getItem('menuBackground');
    if(savedImage) {
        document.querySelector('.sidebar').style.backgroundImage = `url(${savedImage})`;
    }
});
