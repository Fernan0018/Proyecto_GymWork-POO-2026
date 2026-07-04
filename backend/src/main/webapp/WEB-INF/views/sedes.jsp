<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>GymWork - Sedes</title>
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
        .module-container{
            width:90%;
            max-width:1100px;
            z-index:2;
        }
        .module-title{
            color:#fcd535;
            font-family:'Bebas Neue';
            font-size:40px;
            margin-bottom:25px;
            letter-spacing:2px;
        }
        .module-card{
            background:#1e1e1e;
            border:1px solid #333;
            border-radius:12px;
            padding:30px;
            box-shadow:0 8px 30px rgba(0,0,0,.6);
        }
        .module-actions{
            display:flex;
            gap:15px;
            margin-bottom:25px;
        }
        .module-actions input[type="text"]{
            width:300px;
        }
        .action-btn{
            background:#fcd535;
            color:#111;
            border:none;
            padding:12px 22px;
            border-radius:8px;
            font-weight:bold;
            cursor:pointer;
        }
        .action-btn:hover{
            background:#e6c12e;
        }
        .action-btn-sm{
            background:#fcd535;
            color:#111;
            border:none;
            padding:6px 12px;
            border-radius:6px;
            font-weight:bold;
            cursor:pointer;
            font-size:12px;
            margin:2px;
        }
        .action-btn-sm:hover{
            background:#e6c12e;
        }
        .btn-danger-sm{
            background:#dc3545;
            color:#fff;
            border:none;
            padding:6px 12px;
            border-radius:6px;
            font-weight:bold;
            cursor:pointer;
            font-size:12px;
            margin:2px;
        }
        .btn-danger-sm:hover{
            background:#c82333;
        }
        .table-wrapper{
            max-height:400px;
            overflow-y:auto;
        }
        .table-wrapper::-webkit-scrollbar{
            width:8px;
        }
        .table-wrapper::-webkit-scrollbar-track{
            background:#1e1e1e;
        }
        .table-wrapper::-webkit-scrollbar-thumb{
            background:#555;
            border-radius:4px;
        }
        .table-wrapper::-webkit-scrollbar-thumb:hover{
            background:#fcd535;
        }
        .table-registro{
            width:100%;
            border-collapse:collapse;
        }
        .table-registro th{
            background:#fcd535;
            color:#111;
            padding:12px;
            position:sticky;
            top:0;
            z-index:1;
        }
        .table-registro td{
            padding:12px;
            border-bottom:1px solid #333;
        }
        .table-registro tr:hover{
            background:#2a2a2a;
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
            align-items: flex-start;
            justify-content: center;
            background-color: #121212;
            overflow-y: auto;
            color: #ffffff;
            font-family: 'Montserrat', sans-serif;
        }

        .module-container{
            margin-top:10px;
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
        .error-toast {
            display: none;
            position: fixed;
            top: 30px;
            left: 50%;
            transform: translateX(-50%);
            background: #1a1a1a;
            color: #ff4444;
            border: 2px solid #ff4444;
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
        .error-toast.show {
            display: block;
        }
        .modal-overlay {
            display: none;
            position: fixed;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            background: rgba(0,0,0,0.7);
            z-index: 10000;
            justify-content: center;
            align-items: center;
        }
        .modal-overlay.show {
            display: flex;
        }
        .modal-card {
            background: #1e1e1e;
            border: 1px solid #333;
            border-radius: 12px;
            padding: 35px 40px;
            width: 500px;
            box-shadow: 0 8px 30px rgba(0,0,0,0.8);
        }
        .modal-card h2 {
            font-family: 'Bebas Neue', sans-serif;
            font-size: 28px;
            color: #fcd535;
            margin: 0 0 25px 0;
            text-align: center;
            letter-spacing: 2px;
        }
        .modal-field {
            display: flex;
            flex-direction: column;
            margin-bottom: 18px;
        }
        .modal-field label {
            font-size: 11px;
            color: #888;
            text-transform: uppercase;
            letter-spacing: 1px;
            margin-bottom: 5px;
        }
        .modal-field input, .modal-field select {
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
        .modal-field input:focus, .modal-field select:focus {
            border-color: #fcd535;
        }
        .modal-field input.error, .modal-field select.error {
            border-color: #ff4444;
        }
        .modal-field input.valid, .modal-field select.valid {
            border-color: #4caf50;
        }
        .modal-field .validation-msg {
            font-size: 11px;
            margin-top: 4px;
            min-height: 15px;
        }
        .modal-field .validation-msg.error {
            color: #ff4444;
        }
        .modal-field .validation-msg.ok {
            color: #4caf50;
        }
        .modal-field .ubigeo-info {
            font-size: 13px;
            color: #aaa;
            margin-top: 4px;
        }
        .modal-buttons {
            display: flex;
            gap: 12px;
            margin-top: 25px;
        }
        .modal-buttons button {
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
        .btn-primary {
            background: #fcd535;
            border: none;
            color: #1a1a1a;
        }
        .btn-primary:hover {
            background: #e6c12e;
        }
        .btn-primary:disabled {
            opacity: 0.5;
            cursor: not-allowed;
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
    <script>
        var ubigeos = [];
        <c:forEach var="u" items="${ubigeos}">
        ubigeos.push({codigo:'${u.codigo}', nombre:'${u.nombre}'});
        </c:forEach>
    </script>
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
            <div class="sidebar-item"
                onclick="window.location.href='${pageContext.request.contextPath}/menu'">
                <img class="sidebar-icon" src="${pageContext.request.contextPath}/Suscripciones.png" alt="Menú Principal"> Menú Principal
            </div>
            <div class="sidebar-item"
                onclick="window.location.href='${pageContext.request.contextPath}/menu?modulo=suscripciones'">
                <img class="sidebar-icon" src="${pageContext.request.contextPath}/Suscripciones.png" alt="Suscripciones"> Suscripciones
            </div>
            <div class="sidebar-item"
                onclick="window.location.href='${pageContext.request.contextPath}/registro'">
                <img class="sidebar-icon" src="${pageContext.request.contextPath}/Registro.png" alt="Registro"> Registro
            </div>
            <div class="sidebar-item active"
                onclick="window.location.href='${pageContext.request.contextPath}/sedes'">
                <img class="sidebar-icon" src="${pageContext.request.contextPath}/Sedes.png" alt="Sedes"> Sedes
            </div>
            <div class="sidebar-item"
                onclick="window.location.href='${pageContext.request.contextPath}/maquinas'">
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
            <div class="sidebar-item"
                onclick="window.location.href='${pageContext.request.contextPath}/validacion'">
                <img class="sidebar-icon" src="${pageContext.request.contextPath}/Suscripciones.png" alt="Control de Asistencia"> Control de Asistencia
            </div>
        </nav>

        <div class="dashboard-main-content">
            <div class="module-container">
                <h1 class="module-title">GESTIÓN DE SEDES</h1>

                <div class="module-card">
                    <div class="module-actions">
                        <input type="text" id="buscarSede" placeholder="Buscar por nombre o departamento" oninput="filtrarSedes()">
                        <button class="action-btn" onclick="abrirModal()">+ REGISTRAR SEDE</button>
                        <button class="action-btn" onclick="window.location.href='${pageContext.request.contextPath}/sedes/mapa'">🗺 VER MAPA</button>
                    </div>

                    <h3>Total sedes: <span id="totalSedes">${sedes.size()}</span></h3>

                    <div class="table-wrapper">
                        <table class="table-registro">
                            <thead>
                                <tr>
                                    <th>Id</th>
                                    <th>Nombre</th>
                                    <th>Departamento</th>
                                    <th>Distrito</th>
                                    <th>Estado</th>
                                    <th>Aforo</th>
                                    <th>Acciones</th>
                                </tr>
                            </thead>
                            <tbody id="tablaSedesBody">
                                <c:forEach var="s" items="${sedes}">
                                    <tr>
                                        <td>${s.idSede}</td>
                                        <td>${s.nombreSede}</td>
                                        <td>${s.departamento}</td>
                                        <td>${s.distrito}</td>
                                        <td>${s.estado}</td>
                                        <td>${s.aforo}</td>
                                        <td style="white-space:nowrap;">
                                            <button class="action-btn-sm" onclick="abrirModalModificar(${s.idSede}, '${s.nombreSede}', '${s.departamento}', '${s.distrito}', '${s.estado}', ${s.aforo})">&#9998; MOD</button>
                                            <button class="btn-danger-sm" onclick="abrirModalEliminar(${s.idSede}, '${s.nombreSede}')">&#128465; ELIM</button>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>

            <div class="modal-overlay" id="modalRegistro">
                <div class="modal-card">
                    <h2>REGISTRAR SEDE</h2>
                    <form id="formSede" action="${pageContext.request.contextPath}/sedes/guardar" method="post">
                        <div class="modal-field">
                            <label>Nombre de la Sede</label>
                            <input type="text" name="nombre" id="inputNombre" maxlength="100" required oninput="validarNombreSede()">
                            <div class="validation-msg" id="nombreMsg"></div>
                        </div>
                        <div class="modal-field">
                            <label>UBIGEO</label>
                            <input type="text" name="ubigeo" id="inputUbigeo" maxlength="6" placeholder="Código de 6 dígitos" required oninput="validarUbigeo()">
                            <div class="ubigeo-info" id="ubigeoInfo"></div>
                            <div class="validation-msg" id="ubigeoMsg"></div>
                        </div>
                        <div class="modal-field">
                            <label>Estado</label>
                            <select name="estado" id="inputEstado" required onchange="validarEstadoSede()">
                                <option value="">Seleccionar...</option>
                                <option value="Activa">Activa</option>
                                <option value="Inactivo">Inactivo</option>
                                <option value="Mantenimiento">Mantenimiento</option>
                            </select>
                            <div class="validation-msg" id="estadoMsg"></div>
                        </div>
                        <div class="modal-field">
                            <label>Aforo Máximo</label>
                            <input type="number" name="aforo" id="inputAforo" min="1" max="99999" required oninput="validarAforoSede()">
                            <div class="validation-msg" id="aforoMsg"></div>
                        </div>
                        <div class="modal-buttons">
                            <button type="button" class="btn-cancel" onclick="cerrarModal()">CANCELAR</button>
                            <button type="submit" class="btn-primary" id="btnGuardar" disabled>GUARDAR</button>
                        </div>
                    </form>
                </div>
            </div>

            <div class="modal-overlay" id="modalModificar">
                <div class="modal-card">
                    <h2>MODIFICAR SEDE</h2>
                    <form id="formModificar" action="${pageContext.request.contextPath}/sedes/actualizar" method="post">
                        <input type="hidden" name="id" id="modId">

                        <div class="modal-field">
                            <label>Nombre de la Sede</label>
                            <input type="text" id="modNombre" readonly style="background:#1a1a1a;color:#666;cursor:not-allowed;">
                        </div>
                        <div class="modal-field">
                            <label>Ubicación</label>
                            <input type="text" id="modUbicacion" readonly style="background:#1a1a1a;color:#666;cursor:not-allowed;">
                        </div>
                        <div class="modal-field">
                            <label>Estado</label>
                            <select name="estado" id="modEstado" required onchange="validarEstadoSedeMod()">
                                <option value="">Seleccionar...</option>
                                <option value="Activa">Activa</option>
                                <option value="Inactivo">Inactivo</option>
                                <option value="Mantenimiento">Mantenimiento</option>
                            </select>
                            <div class="validation-msg" id="modEstadoMsg"></div>
                        </div>
                        <div class="modal-field">
                            <label>Aforo Máximo</label>
                            <input type="number" name="aforo" id="modAforo" min="1" max="99999" required oninput="validarAforoSedeMod()">
                            <div class="validation-msg" id="modAforoMsg"></div>
                        </div>
                        <div class="modal-buttons">
                            <button type="button" class="btn-cancel" onclick="cerrarModalModificar()">CANCELAR</button>
                            <button type="submit" class="btn-primary">GUARDAR</button>
                        </div>
                    </form>
                </div>
            </div>

            <div class="modal-overlay" id="modalEliminar">
                <div class="modal-card" style="width:400px;">
                    <h2>ELIMINAR SEDE</h2>
                    <p style="text-align:center;color:#ccc;margin-bottom:20px;">
                        ¿Está seguro de eliminar la sede <strong id="elimNombreSede" style="color:#fcd535;"></strong>?
                    </p>
                    <form id="formEliminar" action="${pageContext.request.contextPath}/sedes/eliminar" method="post">
                        <input type="hidden" name="id" id="elimId">
                        <div class="modal-field">
                            <label>Contraseña de Eliminación (6 dígitos)</label>
                            <input type="password" name="deletePassword" id="inputDeletePassword" maxlength="6" inputmode="numeric" pattern="[0-9]{6}" required autocomplete="off">
                        </div>
                        <div class="modal-buttons">
                            <button type="button" class="btn-cancel" onclick="cerrarModalEliminar()">CANCELAR</button>
                            <button type="submit" class="btn-primary" style="background:#dc3545;">ELIMINAR</button>
                        </div>
                    </form>
                </div>
            </div>

            <c:if test="${not empty error}">
            <div class="error-toast show" id="errorToast">${error}</div>
            </c:if>

            <div class="modal-overlay" id="modalPerfil">
                <div class="modal-card" style="width:500px;">
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
            </div>

            <div class="modal-overlay" id="modalEditPerfil">
                <div class="modal-card" style="width:500px;">
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
            </div>

            <c:if test="${not empty exito}">
            <div class="success-toast show" id="successToast">${exito}</div>
            </c:if>
        </div>
    </div>

    <script>
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
            document.getElementById('modalPerfil').classList.add('show');
        }

        function cerrarPerfil() {
            document.getElementById('modalPerfil').classList.remove('show');
        }

        function editarPerfil() {
            document.getElementById('modalPerfil').classList.remove('show');
            document.getElementById('modalEditPerfil').classList.add('show');
        }

        function cancelarEdicion() {
            document.getElementById('modalEditPerfil').classList.remove('show');
            document.getElementById('modalPerfil').classList.add('show');
        }

        var successToast = document.getElementById('successToast');
        if (successToast) {
            setTimeout(function() {
                successToast.style.display = 'none';
            }, 3000);
        }

        var errorToast = document.getElementById('errorToast');
        if (errorToast) {
            setTimeout(function() {
                errorToast.style.display = 'none';
            }, 3000);
        }

        function filtrarSedes() {
            var input = document.getElementById('buscarSede').value.toLowerCase();
            var tbody = document.getElementById('tablaSedesBody');
            var filas = tbody.getElementsByTagName('tr');
            var visibles = 0;

            for (var i = 0; i < filas.length; i++) {
                var texto = filas[i].textContent.toLowerCase();
                if (texto.indexOf(input) > -1) {
                    filas[i].style.display = '';
                    visibles++;
                } else {
                    filas[i].style.display = 'none';
                }
            }

            document.getElementById('totalSedes').textContent = visibles;
        }

        function abrirModal() {
            document.getElementById('modalRegistro').classList.add('show');
            document.getElementById('formSede').reset();
            document.getElementById('ubigeoInfo').textContent = '';
            document.getElementById('ubigeoMsg').textContent = '';
            document.getElementById('ubigeoMsg').className = 'validation-msg';
            document.getElementById('inputUbigeo').className = '';
            document.getElementById('inputNombre').className = '';
            document.getElementById('inputEstado').className = '';
            document.getElementById('inputAforo').className = '';
            document.getElementById('nombreMsg').textContent = '';
            document.getElementById('nombreMsg').className = 'validation-msg';
            document.getElementById('estadoMsg').textContent = '';
            document.getElementById('estadoMsg').className = 'validation-msg';
            document.getElementById('aforoMsg').textContent = '';
            document.getElementById('aforoMsg').className = 'validation-msg';
            document.getElementById('btnGuardar').disabled = true;
        }

        function cerrarModal() {
            document.getElementById('modalRegistro').classList.remove('show');
        }

        function validarUbigeo() {
            var input = document.getElementById('inputUbigeo');
            var msg = document.getElementById('ubigeoMsg');
            var info = document.getElementById('ubigeoInfo');
            var codigo = input.value.trim();

            if (codigo.length === 0) {
                input.className = '';
                msg.textContent = '';
                msg.className = 'validation-msg';
                info.textContent = '';
                document.getElementById('btnGuardar').disabled = true;
                return;
            }

            if (!/^\d{0,6}$/.test(codigo)) {
                input.className = 'error';
                msg.textContent = 'Solo se permiten d\u00EDgitos';
                msg.className = 'validation-msg error';
                info.textContent = '';
                document.getElementById('btnGuardar').disabled = true;
                return;
            }

            if (codigo.length < 4) {
                input.className = '';
                msg.textContent = 'Ingrese al menos 4 d\u00EDgitos del c\u00F3digo UBIGEO';
                msg.className = 'validation-msg';
                info.textContent = '';
                document.getElementById('btnGuardar').disabled = true;
                return;
            }

            var depPref = codigo.substring(0, 2);
            var disPref = codigo.substring(0, 4);

            var dep = null;
            var dis = null;
            var full = false;

            for (var i = 0; i < ubigeos.length; i++) {
                var c = ubigeos[i].codigo;
                if (c === codigo) full = true;
                if (c.indexOf(depPref) === 0 && !dep) dep = ubigeos[i].nombre;
                if (c.indexOf(disPref) === 0 && !dis) dis = ubigeos[i].nombre;
                if (full && dep && dis) break;
            }

            if (dep && dis && full) {
                input.className = 'valid';
                msg.textContent = 'UBIGEO v\u00E1lido';
                msg.className = 'validation-msg ok';
                info.textContent = dep + ' - ' + dis;
            } else {
                input.className = 'error';
                msg.textContent = 'El c\u00F3digo UBIGEO no existe';
                msg.className = 'validation-msg error';
                info.textContent = '';
            }
            validarFormularioSede();
        }

        function validarNombreSede() {
            var input = document.getElementById('inputNombre');
            var msg = document.getElementById('nombreMsg');
            var val = input.value.trim();

            if (val.length === 0) {
                input.className = 'error';
                msg.textContent = 'El nombre de la sede es obligatorio';
                msg.className = 'validation-msg error';
                validarFormularioSede();
                return;
            }

            if (val.length < 3) {
                input.className = 'error';
                msg.textContent = 'El nombre debe tener al menos 3 caracteres';
                msg.className = 'validation-msg error';
                validarFormularioSede();
                return;
            }

            input.className = 'valid';
            msg.textContent = 'Nombre v\u00E1lido';
            msg.className = 'validation-msg ok';
            validarFormularioSede();
        }

        function validarEstadoSede() {
            var select = document.getElementById('inputEstado');
            var msg = document.getElementById('estadoMsg');

            if (select.value === '') {
                select.className = 'error';
                msg.textContent = 'Seleccione un estado';
                msg.className = 'validation-msg error';
            } else {
                select.className = 'valid';
                msg.textContent = 'Estado v\u00E1lido';
                msg.className = 'validation-msg ok';
            }
            validarFormularioSede();
        }

        function validarAforoSede() {
            var input = document.getElementById('inputAforo');
            var msg = document.getElementById('aforoMsg');
            var val = parseInt(input.value, 10);

            if (isNaN(val) || val < 1) {
                input.className = 'error';
                msg.textContent = 'El aforo debe ser un n\u00FAmero positivo';
                msg.className = 'validation-msg error';
                validarFormularioSede();
                return;
            }

            if (val > 99999) {
                input.className = 'error';
                msg.textContent = 'El aforo no puede superar los 99,999';
                msg.className = 'validation-msg error';
                validarFormularioSede();
                return;
            }

            input.className = 'valid';
            msg.textContent = 'Aforo v\u00E1lido';
            msg.className = 'validation-msg ok';
            validarFormularioSede();
        }

        function validarFormularioSede() {
            var nombreVal = document.getElementById('inputNombre').value.trim().length >= 3;
            var aforoVal = (function() {
                var v = parseInt(document.getElementById('inputAforo').value, 10);
                return !isNaN(v) && v >= 1 && v <= 99999;
            })();
            var estadoVal = document.getElementById('inputEstado').value !== '';
            var ubigeoVal = document.getElementById('inputUbigeo').classList.contains('valid');

            document.getElementById('btnGuardar').disabled = !(nombreVal && aforoVal && estadoVal && ubigeoVal);
        }

        function validarEstadoSedeMod() {
            var select = document.getElementById('modEstado');
            var msg = document.getElementById('modEstadoMsg');

            if (select.value === '') {
                select.className = 'error';
                msg.textContent = 'Seleccione un estado';
                msg.className = 'validation-msg error';
            } else {
                select.className = 'valid';
                msg.textContent = 'Estado v\u00E1lido';
                msg.className = 'validation-msg ok';
            }
            validarFormularioMod();
        }

        function validarAforoSedeMod() {
            var input = document.getElementById('modAforo');
            var msg = document.getElementById('modAforoMsg');
            var val = parseInt(input.value, 10);

            if (isNaN(val) || val < 1) {
                input.className = 'error';
                msg.textContent = 'El aforo debe ser un n\u00FAmero positivo';
                msg.className = 'validation-msg error';
                validarFormularioMod();
                return;
            }

            if (val > 99999) {
                input.className = 'error';
                msg.textContent = 'El aforo no puede superar los 99,999';
                msg.className = 'validation-msg error';
                validarFormularioMod();
                return;
            }

            input.className = 'valid';
            msg.textContent = 'Aforo v\u00E1lido';
            msg.className = 'validation-msg ok';
            validarFormularioMod();
        }

        function validarFormularioMod() {
            var aforoVal = (function() {
                var v = parseInt(document.getElementById('modAforo').value, 10);
                return !isNaN(v) && v >= 1 && v <= 99999;
            })();
            var estadoVal = document.getElementById('modEstado').value !== '';
            document.getElementById('formModificar').querySelector('button[type="submit"]').disabled = !(aforoVal && estadoVal);
        }

        function abrirModalModificar(id, nombre, departamento, distrito, estado, aforo) {
            document.getElementById('modId').value = id;
            document.getElementById('modNombre').value = nombre;
            document.getElementById('modUbicacion').value = departamento + ' - ' + distrito;
            document.getElementById('modEstado').value = estado;
            document.getElementById('modAforo').value = aforo;
            document.getElementById('modEstado').className = 'valid';
            document.getElementById('modAforo').className = 'valid';
            document.getElementById('modEstadoMsg').textContent = '';
            document.getElementById('modEstadoMsg').className = 'validation-msg';
            document.getElementById('modAforoMsg').textContent = '';
            document.getElementById('modAforoMsg').className = 'validation-msg';
            document.getElementById('formModificar').querySelector('button[type="submit"]').disabled = false;
            document.getElementById('modalModificar').classList.add('show');
        }

        function cerrarModalModificar() {
            document.getElementById('modalModificar').classList.remove('show');
            document.getElementById('modEstado').className = '';
            document.getElementById('modAforo').className = '';
            document.getElementById('modEstadoMsg').textContent = '';
            document.getElementById('modEstadoMsg').className = 'validation-msg';
            document.getElementById('modAforoMsg').textContent = '';
            document.getElementById('modAforoMsg').className = 'validation-msg';
        }

        function abrirModalEliminar(id, nombre) {
            document.getElementById('elimId').value = id;
            document.getElementById('elimNombreSede').textContent = nombre;
            document.getElementById('inputDeletePassword').value = '';
            document.getElementById('modalEliminar').classList.add('show');
        }

        function cerrarModalEliminar() {
            document.getElementById('modalEliminar').classList.remove('show');
        }

        document.querySelectorAll('.sidebar-item').forEach(function(item) {
            item.addEventListener('click', function() {
                document.querySelectorAll('.sidebar-item').forEach(function(i) { i.classList.remove('active'); });
                this.classList.add('active');
            });
        });
    </script>
    <script>
        (function() {
            var c = document.getElementById('modalEditPerfil');
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
