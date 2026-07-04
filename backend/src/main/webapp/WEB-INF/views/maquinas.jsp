<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>GymWork - Estado de M&aacute;quinas</title>
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
            align-items: flex-start;
            justify-content: center;
            background-color: #121212;
            overflow-y: auto;
            color: #ffffff;
            font-family: 'Montserrat', sans-serif;
        }
        .module-container {
            width: 95%;
            max-width: 1200px;
            z-index: 2;
            margin-top: 10px;
        }
        .module-title {
            color: #fcd535;
            font-family: 'Bebas Neue';
            font-size: 40px;
            margin-bottom: 25px;
            letter-spacing: 2px;
        }
        .module-card {
            background: #1e1e1e;
            border: 1px solid #333;
            border-radius: 12px;
            padding: 30px;
            box-shadow: 0 8px 30px rgba(0,0,0,.6);
            margin-bottom: 20px;
        }
        .stats-row {
            display: grid;
            grid-template-columns: repeat(4, 1fr);
            gap: 20px;
            margin-bottom: 25px;
        }
        .stat-card {
            background: #1e1e1e;
            border: 1px solid #333;
            border-radius: 12px;
            padding: 22px 20px;
            text-align: center;
            box-shadow: 0 4px 15px rgba(0,0,0,.4);
            transition: transform 0.2s;
        }
        .stat-card:hover {
            transform: translateY(-3px);
        }
        .stat-card .stat-icon {
            font-size: 32px;
            margin-bottom: 8px;
        }
        .stat-card .stat-value {
            font-family: 'Bebas Neue', sans-serif;
            font-size: 40px;
            line-height: 1;
            margin-bottom: 4px;
        }
        .stat-card .stat-label {
            font-size: 12px;
            color: #aaa;
            text-transform: uppercase;
            letter-spacing: 1px;
            font-weight: 700;
        }
        .stat-total .stat-value { color: #fcd535; }
        .stat-operativas .stat-value { color: #5ddb6e; }
        .stat-reparacion .stat-value { color: #ff9800; }
        .stat-inactivas .stat-value { color: #dc3545; }
        .stat-total { border-top: 4px solid #fcd535; }
        .stat-operativas { border-top: 4px solid #5ddb6e; }
        .stat-reparacion { border-top: 4px solid #ff9800; }
        .stat-inactivas { border-top: 4px solid #dc3545; }
        .module-actions {
            display: flex;
            gap: 15px;
            margin-bottom: 25px;
            align-items: center;
            flex-wrap: wrap;
        }
        .module-actions input[type="text"] {
            width: 300px;
            background: #2a2a2a;
            border: 1px solid #444;
            border-radius: 6px;
            padding: 10px 12px;
            color: #fff;
            font-family: 'Montserrat', sans-serif;
            outline: none;
        }
        .module-actions input[type="text"]:focus {
            border-color: #fcd535;
        }
        .btn-primary {
            background: #fcd535;
            border: none;
            color: #111;
            font-family: 'Montserrat', sans-serif;
            font-weight: 700;
            font-size: 13px;
            padding: 10px 20px;
            border-radius: 6px;
            cursor: pointer;
            transition: background 0.2s;
            white-space: nowrap;
            letter-spacing: 0.5px;
            text-decoration: none;
            display: inline-flex;
            align-items: center;
            gap: 6px;
        }
        .btn-primary:hover {
            background: #e6c12e;
        }
        .btn-success {
            background: #28a745;
            border: none;
            color: #fff;
            font-family: 'Montserrat', sans-serif;
            font-weight: 700;
            font-size: 13px;
            padding: 10px 20px;
            border-radius: 6px;
            cursor: pointer;
            transition: background 0.2s;
            white-space: nowrap;
            letter-spacing: 0.5px;
            text-decoration: none;
            display: inline-flex;
            align-items: center;
            gap: 6px;
        }
        .btn-success:hover {
            background: #218838;
        }
        .btn-danger-sm {
            background: #dc3545;
            color: #fff;
            border: none;
            padding: 6px 12px;
            border-radius: 6px;
            font-weight: bold;
            cursor: pointer;
            font-size: 12px;
            margin: 2px;
        }
        .btn-danger-sm:hover {
            background: #c82333;
        }
        .action-btn-sm {
            background: #fcd535;
            color: #111;
            border: none;
            padding: 6px 12px;
            border-radius: 6px;
            font-weight: bold;
            cursor: pointer;
            font-size: 12px;
            margin: 2px;
        }
        .action-btn-sm:hover {
            background: #e6c12e;
        }
        .table-wrapper {
            max-height: 450px;
            overflow-y: auto;
        }
        .table-wrapper::-webkit-scrollbar {
            width: 8px;
        }
        .table-wrapper::-webkit-scrollbar-track {
            background: #1e1e1e;
        }
        .table-wrapper::-webkit-scrollbar-thumb {
            background: #555;
            border-radius: 4px;
        }
        .table-wrapper::-webkit-scrollbar-thumb:hover {
            background: #fcd535;
        }
        .table-registro {
            width: 100%;
            border-collapse: collapse;
        }
        .table-registro th {
            background: #fcd535;
            color: #111;
            padding: 12px;
            position: sticky;
            top: 0;
            z-index: 1;
        }
        .table-registro td {
            padding: 12px;
            border-bottom: 1px solid #333;
        }
        .table-registro tr:hover {
            background: #2a2a2a;
        }
        .badge {
            display: inline-block;
            padding: 4px 12px;
            border-radius: 4px;
            font-size: 12px;
            font-weight: 700;
            letter-spacing: 0.5px;
            border: 1px solid;
        }
        .badge-operativa {
            background: rgba(40,167,69,0.12);
            color: #5ddb6e;
            border-color: rgba(40,167,69,0.4);
        }
        .badge-reparacion {
            background: rgba(255,152,0,0.12);
            color: #ff9800;
            border-color: rgba(255,152,0,0.4);
        }
        .badge-inactiva {
            background: rgba(220,53,69,0.12);
            color: #dc3545;
            border-color: rgba(220,53,69,0.4);
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
            max-height: 80vh;
            overflow-y: auto;
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
            margin-bottom: 14px;
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
            box-sizing: border-box;
        }
        .modal-field input:focus, .modal-field select:focus {
            border-color: #fcd535;
        }
        .modal-field input[readonly] {
            background: #1a1a1a;
            color: #aaa;
            cursor: not-allowed;
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
            margin-top: 10px;
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
        @keyframes fadeInOut {
            0% { opacity: 0; transform: translateX(-50%) translateY(-20px); }
            15% { opacity: 1; transform: translateX(-50%) translateY(0); }
            85% { opacity: 1; transform: translateX(-50%) translateY(0); }
            100% { opacity: 0; transform: translateX(-50%) translateY(-20px); }
        }
        .table-registro th:last-child,
        .table-registro td:last-child {
            text-align: center;
        }
        .modal-field select option {
            background: #2a2a2a;
            color: #fff;
        }
        .modal-section {
            margin-bottom: 20px;
            padding-bottom: 20px;
            border-bottom: 1px solid #333;
        }
        .modal-section:last-of-type {
            border-bottom: none;
            margin-bottom: 0;
            padding-bottom: 0;
        }
        .modal-section-title {
            font-family: 'Bebas Neue', sans-serif;
            font-size: 18px;
            color: #fcd535;
            letter-spacing: 1px;
            margin-bottom: 15px;
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
                <button class="dropdown-item" onclick="cerrarSesion()">CERRAR SESI&Oacute;N</button>
            </div>
        </div>
    </nav>

    <div class="dashboard-layout">
        <nav class="sidebar">
            <div class="sidebar-header">M&Oacute;DULOS</div>
            <div class="sidebar-item"
                onclick="window.location.href='${pageContext.request.contextPath}/menu'">
                <img class="sidebar-icon" src="${pageContext.request.contextPath}/Suscripciones.png" alt="Men&uacute; Principal"> Men&uacute; Principal
            </div>
            <div class="sidebar-item"
                onclick="window.location.href='${pageContext.request.contextPath}/menu?modulo=suscripciones'">
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
            <div class="sidebar-item active" onclick="window.location.href='${pageContext.request.contextPath}/maquinas'">
                <img class="sidebar-icon" src="${pageContext.request.contextPath}/Maquinas.png" alt="Estado de M&aacute;quinas"> Estado de M&aacute;quinas
            </div>
            <div class="sidebar-item" onclick="window.location.href='${pageContext.request.contextPath}/soporte'">
                <img class="sidebar-icon" src="${pageContext.request.contextPath}/Soporte.png" alt="Soporte"> Soporte
            </div>
            <div class="sidebar-item"
                onclick="window.location.href='${pageContext.request.contextPath}/administracion'">
                <img class="sidebar-icon" src="${pageContext.request.contextPath}/Administracion.png" alt="M&oacute;dulo de Administraci&oacute;n"> M&oacute;dulo de Administraci&oacute;n
            </div>
            <div class="sidebar-item"
                onclick="window.location.href='${pageContext.request.contextPath}/validacion'">
                <img class="sidebar-icon" src="${pageContext.request.contextPath}/Suscripciones.png" alt="Control de Asistencia"> Control de Asistencia
            </div>
        </nav>

        <div class="dashboard-main-content">
            <div class="module-container">
                <h1 class="module-title">ESTADO DE M&Aacute;QUINAS</h1>

                <div class="stats-row">
                    <div class="stat-card stat-total">
                        <div class="stat-icon">&#9881;</div>
                        <div class="stat-value">${totalMaquinas}</div>
                        <div class="stat-label">Total M&aacute;quinas</div>
                    </div>
                    <div class="stat-card stat-operativas">
                        <div class="stat-icon">&#10003;</div>
                        <div class="stat-value">${totalOperativas}</div>
                        <div class="stat-label">Operativas</div>
                    </div>
                    <div class="stat-card stat-reparacion">
                        <div class="stat-icon">&#9888;</div>
                        <div class="stat-value">${totalMantenimiento}</div>
                        <div class="stat-label">En Reparaci&oacute;n</div>
                    </div>
                    <div class="stat-card stat-inactivas">
                        <div class="stat-icon">&#10007;</div>
                        <div class="stat-value">${totalInactivas}</div>
                        <div class="stat-label">Inactivas</div>
                    </div>
                </div>

                <div class="module-card">
                    <div class="module-actions">
                        <input type="text" id="buscarMaquina" placeholder="Buscar por nombre/modelo" oninput="filtrarMaquinas()">
                        <a href="${pageContext.request.contextPath}/maquinas/reporte/pdf" class="btn-success" style="text-decoration:none;display:inline-flex;align-items:center;gap:6px;">DESCARGAR PDF</a>
                        <button class="btn-primary" onclick="abrirModalAgregar()" style="margin-left:auto;">+ AGREGAR</button>
                    </div>

                    <h3>Total: <span id="totalMaquinas">${totalMaquinas}</span></h3>

                    <div class="table-wrapper">
                        <table class="table-registro">
                            <thead>
                                <tr>
                                    <th>ID</th>
                                    <th>Nombre / Modelo</th>
                                    <th>Sede</th>
                                    <th>&Uacute;ltimo Mantenimiento</th>
                                    <th>Estado</th>
                                    <th>Acciones</th>
                                </tr>
                            </thead>
                            <tbody id="tablaMaquinasBody">
                                <c:forEach var="m" items="${maquinas}">
                                    <tr data-id="${m.idMaquina}"
                                        data-nombre="${m.nombreModelo}"
                                        data-id-sede="${m.idSede}"
                                        data-ultimo-mantenimiento="${m.ultimoMantenimiento}"
                                        data-estado="${m.estadoMaquina}">
                                        <td>${m.idMaquina}</td>
                                        <td>${m.nombreModelo}</td>
                                        <td>${sedeMap[m.idSede]}</td>
                                        <td>${m.ultimoMantenimiento != null ? m.ultimoMantenimiento : '-'}</td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${m.estadoMaquina == 'Operativa'}">
                                                    <span class="badge badge-operativa">Operativa</span>
                                                </c:when>
                                                <c:when test="${m.estadoMaquina == 'En Reparación' || m.estadoMaquina == 'Revisión' || m.estadoMaquina == 'Mantenimiento'}">
                                                    <span class="badge badge-reparacion">En Reparaci&oacute;n</span>
                                                </c:when>
                                                <c:when test="${m.estadoMaquina == 'Inactiva'}">
                                                    <span class="badge badge-inactiva">Inactiva</span>
                                                </c:when>
                                                <c:otherwise>
                                                    ${m.estadoMaquina}
                                                </c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td>
                                            <button class="action-btn-sm" onclick="abrirModalEditar(this)">&#9998; EDIT</button>
                                            <button class="btn-danger-sm" onclick="abrirModalEliminar(this)">&#128465; ELIM</button>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>

            <div class="modal-overlay" id="modalMaquina">
                <div class="modal-card">
                    <h2 id="modalMaquinaTitulo">REGISTRAR EQUIPO</h2>
                    <form id="formMaquina" action="${pageContext.request.contextPath}/maquinas/guardar" method="post">
                        <input type="hidden" name="idMaquina" id="mId">

                        <div class="modal-section">
                            <div class="modal-section-title">INFORMACI&Oacute;N T&Eacute;CNICA</div>
                            <div class="modal-field">
                                <label>ID del Equipo</label>
                                <input type="text" id="mIdDisplay" readonly placeholder="Generado autom&aacute;ticamente">
                            </div>
                            <div class="modal-field">
                                <label>Nombre / Modelo</label>
                                <input type="text" name="nombreModelo" id="mNombre" required>
                            </div>
                            <div class="modal-field">
                                <label>Sede</label>
                                <select name="idSede" id="mSede">
                                    <option value="">Seleccionar sede...</option>
                                    <c:forEach var="s" items="${sedes}">
                                        <option value="${s.idSede}">${s.nombreSede}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>

                        <div class="modal-section">
                            <div class="modal-section-title">ACCIONES</div>
                            <div class="modal-field">
                                <label>Fecha de Mantenimiento</label>
                                <input type="date" name="ultimoMantenimiento" id="mMantenimiento">
                            </div>
                            <div class="modal-field">
                                <label>Estado del Equipo</label>
                                <select name="estadoMaquina" id="mEstado" required>
                                    <option value="">Seleccionar...</option>
                                    <option value="Operativa">Operativa</option>
                                    <option value="En Reparaci&oacute;n">En Reparaci&oacute;n</option>
                                    <option value="Inactiva">Inactiva</option>
                                </select>
                            </div>
                        </div>

                        <div class="modal-buttons">
                            <button type="button" class="btn-cancel" id="btnCerrarMaquina" onclick="cerrarModalMaquina()">CERRAR</button>
                            <button type="submit" class="btn-save" id="btnGuardarMaquina">CREAR</button>
                        </div>
                    </form>
                </div>
            </div>

            <div class="modal-overlay" id="modalEliminar">
                <div class="modal-card" style="width:400px;">
                    <h2>ELIMINAR M&Aacute;QUINA</h2>
                    <p style="text-align:center;color:#ccc;margin-bottom:10px;">
                        &iquest;Est&aacute; seguro de eliminar la m&aacute;quina <strong id="elimNombreMaquina" style="color:#fcd535;"></strong>?
                    </p>
                    <form id="formEliminar" action="${pageContext.request.contextPath}/maquinas/eliminar" method="post">
                        <input type="hidden" name="idMaquina" id="elimId">
                        <div class="modal-field">
                            <label>Contrase&ntilde;a de eliminaci&oacute;n</label>
                            <input type="password" name="deletePassword" id="elimPassword" required placeholder="Ingrese su contrase&ntilde;a">
                        </div>
                        <div class="modal-buttons">
                            <button type="button" class="btn-cancel" onclick="cerrarModalEliminar()">CANCELAR</button>
                            <button type="submit" class="btn-save" style="background:#dc3545;color:#fff;border:none;padding:12px;font-weight:700;border-radius:6px;cursor:pointer;text-transform:uppercase;letter-spacing:1px;">ELIMINAR</button>
                        </div>
                    </form>
                </div>
            </div>

            <div class="modal-overlay" id="modalEditPerfil">
                <div class="modal-card">
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

            <c:if test="${not empty error}">
            <div class="error-toast show" id="errorToast">${error}</div>
            </c:if>

            <c:if test="${not empty exito}">
            <div class="success-toast show" id="successToast">${exito}</div>
            </c:if>

            <div class="modal-overlay" id="modalPerfil">
                <div class="modal-card">
                    <h2>PERFIL DEL ADMINISTRADOR</h2>
                    <div class="profile-grid">
                        <div class="profile-field full-width">
                            <label>DNI</label>
                            <span>${perfil.dni}</span>
                        </div>
                        <div class="profile-field">
                            <label>Nombre</label>
                            <span>${perfil.nombre}</span>
                        </div>
                        <div class="profile-field">
                            <label>Apellidos</label>
                            <span>${perfil.apellidos}</span>
                        </div>
                        <div class="profile-field">
                            <label>Fecha de Nacimiento</label>
                            <span>${perfil.fechaNacimiento}</span>
                        </div>
                        <div class="profile-field">
                            <label>Edad</label>
                            <span>${edad} a&ntilde;os</span>
                        </div>
                        <div class="profile-field full-width">
                            <label>Correo Electr&oacute;nico</label>
                            <span>${perfil.correo}</span>
                        </div>
                        <div class="profile-field full-width">
                            <label>Tel&eacute;fono</label>
                            <span>${perfil.telefono}</span>
                        </div>
                        <button class="profile-close-btn" onclick="cerrarPerfil()">CERRAR</button>
                        <button class="profile-edit-btn" onclick="editarPerfil()">EDITAR PERFIL</button>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <script>
        var btnPerfil = document.getElementById('btnPerfil');
        var menuDesplegable = document.getElementById('menuDesplegable');

        btnPerfil.addEventListener('click', function(e) {
            e.stopPropagation();
            menuDesplegable.classList.toggle('show');
        });

        document.addEventListener('click', function() {
            menuDesplegable.classList.remove('show');
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

        document.querySelectorAll('.sidebar-item').forEach(function(item) {
            item.addEventListener('click', function() {
                document.querySelectorAll('.sidebar-item').forEach(function(i) { i.classList.remove('active'); });
                this.classList.add('active');
            });
        });

        var successToast = document.getElementById('successToast');
        if (successToast) {
            setTimeout(function() { successToast.style.display = 'none'; }, 3000);
        }
        var errorToast = document.getElementById('errorToast');
        if (errorToast) {
            setTimeout(function() { errorToast.style.display = 'none'; }, 3000);
        }

        function filtrarMaquinas() {
            var input = document.getElementById('buscarMaquina').value.toLowerCase();
            var tbody = document.getElementById('tablaMaquinasBody');
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

            document.getElementById('totalMaquinas').textContent = visibles;
        }

        function abrirModalAgregar() {
            document.getElementById('modalMaquinaTitulo').textContent = 'REGISTRAR EQUIPO';
            document.getElementById('formMaquina').action = '${pageContext.request.contextPath}/maquinas/guardar';
            document.getElementById('mId').value = '';
            document.getElementById('mIdDisplay').value = '';
            document.getElementById('mNombre').value = '';
            document.getElementById('mSede').value = '';
            document.getElementById('mEstado').value = '';
            document.getElementById('mMantenimiento').value = '';
            document.getElementById('btnGuardarMaquina').textContent = 'CREAR';
            document.getElementById('btnCerrarMaquina').textContent = 'CERRAR';
            document.getElementById('mIdDisplay').placeholder = 'Generado autom\u00E1ticamente';
            document.getElementById('modalMaquina').classList.add('show');
        }

        function abrirModalEditar(btn) { var tr = btn.closest('tr'); var d = tr.dataset; 
            document.getElementById('modalMaquinaTitulo').textContent = 'ACTUALIZAR EQUIPO';
            document.getElementById('formMaquina').action = '${pageContext.request.contextPath}/maquinas/actualizar'; 
            document.getElementById('mId').value = d.id; document.getElementById('mIdDisplay').value = d.id; 
            document.getElementById('mIdDisplay').placeholder = ''; 
            document.getElementById('mNombre').value = d.nombre; 
            document.getElementById('mSede').value = d.idSede || ''; 
            document.getElementById('mEstado').value = d.estado === 'Revisi\u00F3n' || d.estado === 'Mantenimiento' ? 'En Reparaci\u00F3n' : d.estado; 
            document.getElementById('mMantenimiento').value = d.ultimoMantenimiento || ''; 
            document.getElementById('btnGuardarMaquina').textContent = 'ACTUALIZAR'; 
            document.getElementById('btnCerrarMaquina').textContent = 'CANCELAR'; 
            document.getElementById('modalMaquina').classList.add('show'); }

        function cerrarModalMaquina() {
            document.getElementById('modalMaquina').classList.remove('show');
        }

        function abrirModalEliminar(btn) {
            var tr = btn.closest('tr');
            var d = tr.dataset;
            document.getElementById('elimId').value = d.id;
            document.getElementById('elimNombreMaquina').textContent = d.nombre;
            document.getElementById('elimPassword').value = '';
            document.getElementById('modalEliminar').classList.add('show');
        }

        function cerrarModalEliminar() {
            document.getElementById('modalEliminar').classList.remove('show');
        }
    </script>
</body>
</html>
