// 1. Capturamos los elementos (inputs y sus respectivos labels)
const inputs = document.querySelectorAll('input');

// 2. Recorremos cada caja de texto para agregarle interactividad visual
inputs.forEach(input => {
    
    // Evento 'focus': Se dispara cuando das clic adentro para escribir
    input.addEventListener('focus', function() {
        // Pinta la línea de abajo de amarillo
        this.style.borderBottom = '2px solid #fcd535'; 
        
        // Busca el "label" que está justo arriba (la etiqueta del texto) y lo pinta de amarillo
        const label = this.previousElementSibling;
        label.style.color = '#fcd535';
    });

    // Evento 'blur': Se dispara cuando haces clic afuera de la caja
    input.addEventListener('blur', function() {
        // Regresa la línea inferior a su color gris original
        this.style.borderBottom = '1px solid #555';
        
        // Regresa el texto del label a gris
        const label = this.previousElementSibling;
        label.style.color = '#888';
    });
});

// 3. Toggle para ver/ocultar contraseña
document.getElementById('togglePassword').addEventListener('change', function() {
    const passwordInput = document.getElementById('password');
    passwordInput.type = this.checked ? 'text' : 'password';
});

// Capturamos el formulario
// Capturamos el formulario y el nuevo div de error
const form = document.getElementById('formLogin');
const mensajeError = document.getElementById('mensajeError'); 

form.addEventListener('submit', async function(evento) {
    // 1. Evitamos que la página se recargue
    evento.preventDefault(); 

    // Ocultamos el mensaje de error cada vez que se intenta iniciar sesión
    mensajeError.style.display = 'none';

    // 2. Extraemos los valores de las cajas de texto
    const usuarioTexto = document.getElementById('username').value.trim();
    const claveTexto = document.getElementById('password').value.trim();

    // 3. Validación básica (Frontend)
    if (usuarioTexto === "" || claveTexto === "") {
        mensajeError.textContent = "Por favor, completa todos los campos.";
        mensajeError.style.display = 'block'; // Mostramos el error
        return; 
    }

    // 4. Preparamos los datos
    const datosLogin = {
        username: usuarioTexto,
        password: claveTexto
    };

    try {
        // 5. Enviamos al Backend
        const respuesta = await fetch('http://localhost:8080/api/login', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(datosLogin)
        });

        // 6. Evaluamos la respuesta
        if (respuesta.ok) {
            // ¡ÉXITO! Sin mensajes, redirección directa y silenciosa
            window.location.href = 'MenuPrincipal.html';
        } else {
            // ERROR: Mostramos el cuadro de error visual
            mensajeError.textContent = "Acceso inválido. Por favor, inténtelo otra vez.";
            mensajeError.style.display = 'block'; 
            // NUEVO: Limpiamos el campo de contraseña y le damos el foco
            const inputPassword = document.getElementById('password');
            inputPassword.value = ''; // Borra lo que estaba escrito
            inputPassword.focus();    // Coloca el cursor parpadeando ahí
        }

    } catch (error) {
        console.error("Error al conectar con el servidor:", error);
        // Error de conexión también se muestra en el diseño
        mensajeError.textContent = "Error de conexión. Verifica el servidor.";
        mensajeError.style.display = 'block';
    }
});
