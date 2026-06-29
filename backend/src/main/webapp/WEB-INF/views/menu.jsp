<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>GymWork - Menú Principal</title>
    <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/GymWork_Logo.png">
    <link href="https://fonts.googleapis.com/css2?family=Bebas+Neue&family=Montserrat:wght@400;700&display=swap" rel="stylesheet">
    <style>
        body {
            margin: 0;
            padding: 0;
            font-family: 'Montserrat', sans-serif;
            background-color: #0f0f0f;
            color: white;
        }
        .navbar {
            background-color: #1a1a1a;
            padding: 15px 40px;
            display: flex;
            justify-content: space-between;
            align-items: center;
            border-bottom: 3px solid #fcd535;
            box-shadow: 0 4px 10px rgba(0,0,0,0.5);
            position: relative;
        }
        .navbar h1 {
            font-family: 'Bebas Neue', sans-serif;
            margin: 0;
            font-size: 32px;
            color: #fcd535;
            letter-spacing: 2px;
        }
        .profile-container {
            position: relative;
            display: inline-block;
        }
        .profile-btn {
            background: #000;
            border: 2px solid #555;
            border-radius: 50%;
            width: 45px;
            height: 45px;
            display: flex;
            align-items: center;
            justify-content: center;
            cursor: pointer;
            transition: all 0.3s ease;
            padding: 0;
            overflow: hidden;
        }
        .profile-btn img {
            width: 100%;
            height: 100%;
            object-fit: cover;
            transform: scale(1.15);
        }
        .profile-btn:hover {
            border-color: #fcd535;
        }
        .dropdown-menu {
            display: none;
            position: absolute;
            right: 0;
            top: 55px;
            background-color: #1a1a1a;
            min-width: 160px;
            box-shadow: 0px 8px 16px rgba(0,0,0,0.6);
            border: 1px solid #333;
            border-radius: 4px;
            z-index: 1000;
            overflow: hidden;
        }
        .dropdown-item {
            color: white;
            padding: 12px 16px;
            text-decoration: none;
            display: block;
            font-size: 13px;
            font-weight: bold;
            transition: background 0.2s, color 0.2s;
            cursor: pointer;
            border: none;
            background: none;
            width: 100%;
            text-align: left;
            font-family: 'Montserrat', sans-serif;
        }
        .dropdown-item:hover {
            background-color: #fcd535;
            color: black;
        }
        .show {
            display: block;
        }
        .dashboard-layout {
            display: flex;
            height: calc(100vh - 80px);
        }
        .dashboard-main-content {
            position: relative;
            flex: 1;
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: center;
            background-color: #121212;
            overflow-y: auto;
            color: #ffffff;
            font-family: 'Montserrat', sans-serif;
        }
        .background-circle {
            position: absolute;
            width: 500px;
            height: 500px;
            border-radius: 50%;
            border: 2px solid rgba(255, 204, 0, 0.15);
            box-shadow: 0 0 100px rgba(255, 204, 0, 0.05) inset;
            pointer-events: none;
            z-index: 0;
        }
        .welcome-message-container {
            position: relative;
            z-index: 2;
            text-align: center;
        }
        #dynamicContent {
            position: relative;
            z-index: 1;
        }
        .greeting {
            font-size: 3rem;
            font-weight: 300;
            margin-bottom: 0.5rem;
            color: #f0f0f0;
            text-align: center;
        }
        .status-message {
            font-size: 2.5rem;
            font-weight: 400;
            margin-bottom: 2rem;
            color: #ffffff;
            text-align: center;
        }
        .instruction {
            font-size: 1.2rem;
            font-weight: 300;
            color: #aaaaaa;
            line-height: 1.5;
            text-align: center;
        }
        .sidebar {
            width: 260px;
            background-color: #1a1a1a;
            border-right: 2px solid #fcd535;
            padding: 30px 0;
            display: flex;
            flex-direction: column;
            box-shadow: 4px 0 10px rgba(0,0,0,0.4);
            overflow-y: auto;
        }
        .sidebar-header {
            font-family: 'Bebas Neue', sans-serif;
            font-size: 24px;
            color: #fcd535;
            text-align: center;
            padding: 10px 15px 25px;
            letter-spacing: 2px;
            border-bottom: 1px solid #333;
            margin-bottom: 15px;
        }
        .sidebar-item {
            display: flex;
            align-items: center;
            gap: 14px;
            padding: 18px 25px;
            color: #cccccc;
            font-size: 15px;
            font-weight: 500;
            cursor: pointer;
            transition: background 0.2s, color 0.2s, border-color 0.2s;
            border-left: 4px solid transparent;
            letter-spacing: 0.5px;
        }
        .sidebar-item:hover {
            background-color: rgba(252, 213, 53, 0.1);
            color: #ffffff;
            border-left-color: #fcd535;
        }
        .sidebar-item.active {
            background-color: rgba(252, 213, 53, 0.15);
            color: #fcd535;
            border-left-color: #fcd535;
        }
        .sidebar-icon {
            width: 28px;
            height: 28px;
            object-fit: contain;
            flex-shrink: 0;
        }
        .profile-card {
            display: none;
            background-color: #1e1e1e;
            border: 1px solid #333;
            border-radius: 12px;
            padding: 40px 50px;
            width: 500px;
            box-shadow: 0 8px 30px rgba(0,0,0,0.6);
            position: relative;
            z-index: 2;
        }
        .profile-card.visible {
            display: block;
        }
        .profile-card h2 {
            font-family: 'Bebas Neue', sans-serif;
            font-size: 28px;
            color: #fcd535;
            margin: 0 0 30px 0;
            text-align: center;
            letter-spacing: 2px;
        }
        .profile-grid {
            display: grid;
            grid-template-columns: 1fr 1fr;
            gap: 20px;
        }
        .profile-field {
            display: flex;
            flex-direction: column;
        }
        .profile-field.full-width {
            grid-column: 1 / -1;
        }
        .profile-field label {
            font-size: 11px;
            color: #888;
            text-transform: uppercase;
            letter-spacing: 1px;
            margin-bottom: 4px;
        }
        .profile-field span {
            font-size: 16px;
            color: #fff;
            font-weight: 500;
        }
        .profile-close-btn {
            margin-top: 30px;
            grid-column: 1 / -1;
            padding: 12px;
            background: transparent;
            border: 1px solid #555;
            color: #ccc;
            font-family: 'Montserrat', sans-serif;
            font-size: 14px;
            font-weight: 700;
            border-radius: 6px;
            cursor: pointer;
            transition: all 0.2s;
            text-transform: uppercase;
            letter-spacing: 1px;
        }
        .profile-close-btn:hover {
            border-color: #fcd535;
            color: #fcd535;
        }
        .profile-edit-btn {
            margin-top: 10px;
            grid-column: 1 / -1;
            padding: 12px;
            background: #fcd535;
            border: none;
            color: #1a1a1a;
            font-family: 'Montserrat', sans-serif;
            font-size: 14px;
            font-weight: 700;
            border-radius: 6px;
            cursor: pointer;
            transition: all 0.2s;
            text-transform: uppercase;
            letter-spacing: 1px;
        }
        .profile-edit-btn:hover {
            background: #e6c12e;
        }
        .edit-card {
            display: none;
            background-color: #1e1e1e;
            border: 1px solid #333;
            border-radius: 12px;
            padding: 40px 50px;
            width: 500px;
            box-shadow: 0 8px 30px rgba(0,0,0,0.6);
            position: relative;
            z-index: 2;
        }
        .edit-card.visible {
            display: block;
        }
        .edit-card h2 {
            font-family: 'Bebas Neue', sans-serif;
            font-size: 28px;
            color: #fcd535;
            margin: 0 0 30px 0;
            text-align: center;
            letter-spacing: 2px;
        }
        .edit-grid {
            display: grid;
            grid-template-columns: 1fr 1fr;
            gap: 20px;
        }
        .edit-field {
            display: flex;
            flex-direction: column;
        }
        .edit-field.full-width {
            grid-column: 1 / -1;
        }
        .edit-field label {
            font-size: 11px;
            color: #888;
            text-transform: uppercase;
            letter-spacing: 1px;
            margin-bottom: 4px;
        }
        .edit-field input {
            background: #2a2a2a;
            border: 1px solid #444;
            border-radius: 6px;
            padding: 10px 12px;
            color: #fff;
            font-family: 'Montserrat', sans-serif;
            font-size: 14px;
            outline: none;
            transition: border-color 0.2s;
        }
        .edit-field input:focus {
            border-color: #fcd535;
        }
        .edit-field input[readonly] {
            background: #1a1a1a;
            color: #666;
            cursor: not-allowed;
        }
        .edit-buttons {
            display: flex;
            gap: 12px;
            grid-column: 1 / -1;
            margin-top: 20px;
        }
        .edit-buttons button {
            flex: 1;
            padding: 12px;
            font-family: 'Montserrat', sans-serif;
            font-size: 14px;
            font-weight: 700;
            border-radius: 6px;
            cursor: pointer;
            text-transform: uppercase;
            letter-spacing: 1px;
            transition: all 0.2s;
        }
        .btn-save {
            background: #fcd535;
            border: none;
            color: #1a1a1a;
        }
        .btn-save:hover {
            background: #e6c12e;
        }
        .btn-cancel {
            background: transparent;
            border: 1px solid #555;
            color: #ccc;
        }
        .btn-cancel:hover {
            border-color: #fcd535;
            color: #fcd535;
        }
        .success-toast {
            display: none;
            position: fixed;
            top: 30px;
            left: 50%;
            transform: translateX(-50%);
            background: #1a1a1a;
            color: #fcd535;
            border: 2px solid #fcd535;
            padding: 16px 32px;
            border-radius: 8px;
            font-family: 'Montserrat', sans-serif;
            font-size: 16px;
            font-weight: 700;
            letter-spacing: 1px;
            box-shadow: 0 4px 20px rgba(0,0,0,0.5);
            z-index: 9999;
            animation: fadeInOut 2.5s ease forwards;
        }
        .success-toast.show {
            display: block;
        }
        @keyframes fadeInOut {
            0% { opacity: 0; transform: translateX(-50%) translateY(-20px); }
            15% { opacity: 1; transform: translateX(-50%) translateY(0); }
            85% { opacity: 1; transform: translateX(-50%) translateY(0); }
            100% { opacity: 0; transform: translateX(-50%) translateY(-20px); }
        }
        .edit-field input.error {
            border-color: #ff4444 !important;
        }
        .edit-field input.valid {
            border-color: #4caf50 !important;
        }
        .edit-field .validation-msg {
            font-size: 11px;
            margin-top: 4px;
            min-height: 15px;
        }
        .edit-field .validation-msg.error {
            color: #ff4444;
        }
        .edit-field .validation-msg.ok {
            color: #4caf50;
        }
    </style>
</head>
<body>
    <nav class="navbar">
        <h1>GYMWORK</h1>
        <div class="profile-container">
            <button class="profile-btn" id="btnPerfil">
                <img src="${pageContext.request.contextPath}/User_Profile.jpg" alt="Perfil">
            </button>
            <div class="dropdown-menu" id="menuDesplegable">
                <button class="dropdown-item" onclick="verPerfil()">VER PERFIL</button>
                <button class="dropdown-item" onclick="cerrarSesion()">CERRAR SESIÓN</button>
            </div>
        </div>
    </nav>

    <div class="dashboard-layout">
        <nav class="sidebar">
            <div class="sidebar-header">MÓDULOS</div>
            <div class="sidebar-item" data-modulo="suscripciones">
                <img class="sidebar-icon" src="${pageContext.request.contextPath}/Suscripciones.png" alt="Suscripciones"> Suscripciones
            </div>
            <div class="sidebar-item"
                onclick="window.location.href='${pageContext.request.contextPath}/registro'">
                <img class="sidebar-icon" src="${pageContext.request.contextPath}/Registro.png" alt="Registro"> Registro
            </div>
            <div class="sidebar-item"
                onclick="window.location.href='${pageContext.request.contextPath}/sedes'">
                <img class="sidebar-icon" src="${pageContext.request.contextPath}/Sedes.png" alt="Sedes"> Sedes
            </div>
            <div class="sidebar-item" data-modulo="maquinas">
                <img class="sidebar-icon" src="${pageContext.request.contextPath}/Maquinas.png" alt="Estado de Máquinas"> Estado de Máquinas
            </div>
            <div class="sidebar-item"
                onclick="window.location.href='${pageContext.request.contextPath}/soporte'">
                <img class="sidebar-icon" src="${pageContext.request.contextPath}/Soporte.png" alt="Soporte"> Soporte
            </div>
            <div class="sidebar-item"
                onclick="window.location.href='${pageContext.request.contextPath}/administracion'">
                <img class="sidebar-icon" src="${pageContext.request.contextPath}/Administracion.png" alt="Módulo de Administración"> Módulo de Administración
            </div>
        </nav>

        <div class="dashboard-main-content">
            <div class="background-circle"></div>

            <div class="welcome-message-container" id="welcomeContainer">
                <h1 class="greeting" id="greeting"></h1>
                <h2 class="status-message">El gimnasio está funcionando bien.</h2>
                <p class="instruction">Seleccione una opción en el menú lateral<br>para gestionar los módulos.</p>
            </div>

            <c:if test="${perfil != null}">
            <div class="profile-card" id="profileCard">
                <h2>PERFIL DEL ADMINISTRADOR</h2>
                <div class="profile-grid">
                    <div class="profile-field full-width">
                        <label>DNI</label>
                        <span id="pDni">${perfil.dni}</span>
                    </div>
                    <div class="profile-field">
                        <label>Nombre</label>
                        <span id="pNombre">${perfil.nombre}</span>
                    </div>
                    <div class="profile-field">
                        <label>Apellidos</label>
                        <span id="pApellidos">${perfil.apellidos}</span>
                    </div>
                    <div class="profile-field">
                        <label>Fecha de Nacimiento</label>
                        <span id="pFechaNac">${perfil.fechaNacimiento}</span>
                    </div>
                    <div class="profile-field">
                        <label>Edad</label>
                        <span id="pEdad">${edad} años</span>
                    </div>
                    <div class="profile-field full-width">
                        <label>Correo Electrónico</label>
                        <span id="pCorreo">${perfil.correo}</span>
                    </div>
                    <div class="profile-field full-width">
                        <label>Teléfono</label>
                        <span id="pTelefono">${perfil.telefono}</span>
                    </div>
                    <button class="profile-close-btn" onclick="cerrarPerfil()">CERRAR</button>
                    <button class="profile-edit-btn" onclick="editarPerfil()">EDITAR PERFIL</button>
                </div>
            </div>
            </c:if>

            <c:if test="${perfil != null}">
            <div class="edit-card" id="editCard">
                <h2>EDITAR PERFIL</h2>
                <form action="${pageContext.request.contextPath}/perfil/actualizar" method="post" novalidate>
                    <div class="edit-grid">
                        <div class="edit-field full-width">
                            <label>DNI</label>
                            <input type="text" name="dni" value="${perfil.dni}" readonly>
                        </div>
                        <div class="edit-field">
                            <label>Nombre</label>
                            <input type="text" name="nombre" value="${perfil.nombre}">
                        </div>
                        <div class="edit-field">
                            <label>Apellidos</label>
                            <input type="text" name="apellidos" value="${perfil.apellidos}">
                        </div>
                        <div class="edit-field">
                            <label>Fecha de Nacimiento</label>
                            <input type="date" name="fechaNacimiento" value="${perfil.fechaNacimiento}">
                        </div>
                        <div class="edit-field full-width">
                            <label>Correo Electrónico</label>
                            <input type="email" name="correo" value="${perfil.correo}">
                        </div>
                        <div class="edit-field full-width">
                            <label>Teléfono</label>
                            <input type="text" name="telefono" value="${perfil.telefono}">
                        </div>
                        <div class="edit-buttons">
                            <button type="button" class="btn-cancel" onclick="cancelarEdicion()">CANCELAR</button>
                            <button type="submit" class="btn-save">GUARDAR</button>
                        </div>
                    </div>
                </form>
            </div>
            </c:if>

            <c:if test="${not empty exito}">
            <div class="success-toast show" id="successToast">${exito}</div>
            </c:if>
        </div>
    </div>

    <script>
        const hora = new Date().getHours();
        let saludo;
        if (hora >= 6 && hora < 12) saludo = 'Buenos d\u00EDas';
        else if (hora >= 12 && hora < 18) saludo = 'Buenas tardes';
        else saludo = 'Buenas noches';
        const nombreUsuario = '${username}';
        document.getElementById('greeting').innerText = 'Hola, ' + nombreUsuario + '. ' + saludo;

        const btnPerfil = document.getElementById('btnPerfil');
        const menuDesplegable = document.getElementById('menuDesplegable');

        btnPerfil.addEventListener('click', function(evento) {
            evento.stopPropagation();
            menuDesplegable.classList.toggle('show');
        });

        document.addEventListener('click', function() {
            if (menuDesplegable.classList.contains('show')) {
                menuDesplegable.classList.remove('show');
            }
        });

        function cerrarSesion() {
            window.location.href = '${pageContext.request.contextPath}/logout';
        }

        function verPerfil() {
            document.getElementById('welcomeContainer').style.display = 'none';
            document.getElementById('profileCard').classList.add('visible');
        }

        function cerrarPerfil() {
            document.getElementById('profileCard').classList.remove('visible');
            document.getElementById('welcomeContainer').style.display = '';
        }

        function editarPerfil() {
            document.getElementById('profileCard').classList.remove('visible');
            document.getElementById('editCard').classList.add('visible');
        }

        function cancelarEdicion() {
            document.getElementById('editCard').classList.remove('visible');
            document.getElementById('profileCard').classList.add('visible');
        }

        const toast = document.getElementById('successToast');
        if (toast) {
            setTimeout(function() {
                toast.style.display = 'none';
            }, 3000);
        }

        document.querySelectorAll('.sidebar-item').forEach(function(item) {
            item.addEventListener('click', function() {
                document.querySelectorAll('.sidebar-item').forEach(function(i) { i.classList.remove('active'); });
                this.classList.add('active');
                var modulo = this.getAttribute('data-modulo');

                document.getElementById('welcomeContainer').style.display = '';

                var pc = document.getElementById('profileCard');
                if (pc) pc.classList.remove('visible');
                var ec = document.getElementById('editCard');
                if (ec) ec.classList.remove('visible');

                var container = document.getElementById('dynamicContent');
                if (container) { container.innerHTML = ''; container.style.display = 'none'; }

                if (modulo === 'suscripciones') {
                    fetch('${pageContext.request.contextPath}/suscripciones')
                        .then(function(r) { return r.text(); })
                        .then(function(html) {
                            document.getElementById('welcomeContainer').style.display = 'none';
                            var dc = document.getElementById('dynamicContent');
                            if (!dc) {
                                dc = document.createElement('div');
                                dc.id = 'dynamicContent';
                                document.querySelector('.dashboard-main-content').appendChild(dc);
                            }
                            dc.style.display = '';
                            dc.innerHTML = html;
                        });
                } else {
                    var greeting = document.getElementById('greeting');
                    var statusMsg = document.querySelector('.status-message');
                    var instruction = document.querySelector('.instruction');
                    greeting.innerText = 'Modulo: ' + this.textContent.trim();
                    statusMsg.innerText = 'Cargando contenido...';
                    instruction.innerHTML = 'Use las opciones disponibles<br>para gestionar este modulo.';
                }
            });
        });

        document.addEventListener('submit', function(e) {
            if (e.target && e.target.id === 'suscripcionForm') {
                e.preventDefault();
                var formData = new FormData(e.target);
                var params = new URLSearchParams(formData);
                var mensajeDiv = document.getElementById('mensajeSuscripcion');
                fetch('${pageContext.request.contextPath}/suscripciones/guardar', {
                    method: 'POST',
                    headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
                    body: params
                })
                .then(function(response) {
                    if (!response.ok) {
                        return response.json().then(function(data) {
                            throw new Error(data.error || 'Error al registrar');
                        });
                    }
                    return response.json();
                })
                .then(function(data) {
                    if (data.success) {
                        mensajeDiv.innerHTML = '<p style="color: #00cc66; font-weight: bold;">Suscripci\u00f3n guardada con \u00e9xito</p>';
                        e.target.reset();
                    }
                })
                .catch(function(error) {
                    mensajeDiv.innerHTML = '<p style="color: #ff3333; font-weight: bold;">Error: ' + error.message + '</p>';
                });
            }
        });
    </script>
    <script>
        (function() {
            var c = document.getElementById('editCard');
            if (!c) return;
            var f = c.querySelector('form');
            if (!f) return;
            var rules = {
                nombre: function(v) {
                    v = v.trim();
                    if (v.length < 2) return 'Mínimo 2 caracteres';
                    if (!/^[a-zA-ZáéíóúÁÉÍÓÚñÑ\s]+$/.test(v)) return 'Solo letras';
                    return '';
                },
                apellidos: function(v) {
                    v = v.trim();
                    if (v.length < 2) return 'Mínimo 2 caracteres';
                    if (!/^[a-zA-ZáéíóúÁÉÍÓÚñÑ\s]+$/.test(v)) return 'Solo letras';
                    return '';
                },
                fechaNacimiento: function(v) {
                    if (!v) return 'Requerido';
                    var d = new Date(v + 'T00:00:00');
                    if (isNaN(d.getTime())) return 'Fecha inválida';
                    if (d >= new Date()) return 'Debe ser fecha pasada';
                    return '';
                },
                correo: function(v) {
                    v = v.trim();
                    if (!v) return 'Requerido';
                    if (!/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(v)) return 'Correo inválido';
                    return '';
                },
                telefono: function(v) {
                    v = v.trim();
                    if (!v) return 'Requerido';
                    var digits = v.replace(/\D/g, '');
                    if (digits.length < 9) return 'Mínimo 9 dígitos';
                    return '';
                }
            };
            Object.keys(rules).forEach(function(name) {
                var inp = c.querySelector('input[name="' + name + '"]');
                if (!inp) return;
                var field = inp.closest('.edit-field');
                if (!field) return;
                var msg = document.createElement('span');
                msg.className = 'validation-msg';
                field.appendChild(msg);
                function check() {
                    var err = rules[name](inp.value);
                    if (err) {
                        inp.classList.remove('valid');
                        inp.classList.add('error');
                        msg.textContent = err;
                        msg.className = 'validation-msg error';
                    } else {
                        inp.classList.remove('error');
                        inp.classList.add('valid');
                        msg.textContent = '✓';
                        msg.className = 'validation-msg ok';
                    }
                }
                inp.addEventListener('input', check);
                inp.addEventListener('blur', check);
                check();
            });
            f.addEventListener('submit', function(e) {
                var bad = false;
                Object.keys(rules).forEach(function(name) {
                    var inp = c.querySelector('input[name="' + name + '"]');
                    if (!inp) return;
                    if (rules[name](inp.value)) bad = true;
                });
                if (bad) e.preventDefault();
            });
        })();
    </script>
</body>
</html>
