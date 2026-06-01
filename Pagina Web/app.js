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

// Capturamos el formulario
const form = document.getElementById('formLogin');

form.addEventListener('submit', async function(evento) {
    // 1. Evitamos que la página se recargue
    evento.preventDefault(); 

    // 2. Extraemos los valores de las cajas de texto
    const usuarioTexto = document.getElementById('username').value.trim();
    const claveTexto = document.getElementById('password').value.trim();

    // 3. Validación básica (Frontend)
    if (usuarioTexto === "" || claveTexto === "") {
        alert("Por favor, completa todos los campos.");
        return; // Detiene la ejecución si falta algo
    }

    // 4. Preparamos los datos para enviarlos al servidor
    const datosLogin = {
        username: usuarioTexto,
        password: claveTexto
    };
    // Esto nos mostrará en el navegador qué estamos a punto de enviar
    console.log("Datos a enviar desde JS:", datosLogin);
    try {
        // 5. Enviamos los datos al Backend (Esta URL es un ejemplo de tu futuro servidor local)
        const respuesta = await fetch('http://localhost:8080/api/login', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(datosLogin)
        });

        // 6. Evaluamos la respuesta de la base de datos
        if (respuesta.ok) {
            alert("¡Acceso concedido! Redirigiendo al sistema...");
            // Aquí harás el redireccionamiento a la pantalla principal del sistema
            // window.location.href = 'dashboard.html';
        } else {
            alert("Usuario o contraseña incorrectos. Intenta de nuevo.");
        }

    } catch (error) {
        console.error("Error al conectar con el servidor:", error);
        alert("Error de conexión. Verifica que el servidor backend esté encendido.");
    }
});
