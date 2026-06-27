<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>GymWork - Soporte</title>
    <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/GymWork_Logo.png">
    <link href="https://fonts.googleapis.com/css2?family=Bebas+Neue&family=Montserrat:wght@400;700&display=swap" rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/chart.js@4.4.7/dist/chart.umd.min.js"></script>
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
            width:95%;
            max-width:1200px;
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
            background:#2a2a2a;
            border:1px solid #444;
            border-radius:6px;
            padding:10px 12px;
            color:#fff;
            font-family:'Montserrat',sans-serif;
            outline:none;
        }
        .module-actions input[type="text"]:focus{
            border-color:#fcd535;
        }
        .badge {
            display:inline-block;
            padding:4px 12px;
            border-radius:4px;
            font-size:12px;
            font-weight:700;
            letter-spacing:0.5px;
            border:1px solid;
        }
        .badge-prioridad-alta {
            background:rgba(220,53,69,0.12);
            color:#ff6b7a;
            border-color:rgba(220,53,69,0.4);
        }
        .badge-prioridad-media {
            background:rgba(252,213,53,0.12);
            color:#fcd535;
            border-color:rgba(252,213,53,0.4);
        }
        .badge-prioridad-baja {
            background:rgba(40,167,69,0.12);
            color:#5ddb6e;
            border-color:rgba(40,167,69,0.4);
        }
        .badge-estado-abierto {
            background:rgba(0,123,255,0.12);
            color:#6db3ff;
            border-color:rgba(0,123,255,0.4);
        }
        .badge-estado-enproceso {
            background:rgba(253,126,20,0.12);
            color:#ffb366;
            border-color:rgba(253,126,20,0.4);
        }
        .badge-estado-resuelto {
            background:rgba(40,167,69,0.12);
            color:#5ddb6e;
            border-color:rgba(40,167,69,0.4);
        }
        .tooltip-trigger {
            cursor:help;
            border-bottom:1px dashed #fcd535;
        }
        #floating-tooltip {
            display:none;
            position:fixed;
            background:#1a1a1a;
            border:1px solid #fcd535;
            color:#fff;
            padding:6px 12px;
            border-radius:6px;
            font-size:12px;
            white-space:nowrap;
            z-index:99999;
            box-shadow:0 4px 12px rgba(0,0,0,0.5);
            pointer-events:none;
        }
        .table-wrapper{
            max-height:450px;
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
        .asunto-scroll {
            display: inline-block;
            max-width: 260px;
            overflow-x: auto;
            white-space: nowrap;
            scrollbar-width: thin;
            scrollbar-color: #555 #1e1e1e;
        }
        .asunto-scroll::-webkit-scrollbar {
            height: 6px;
        }
        .asunto-scroll::-webkit-scrollbar-track {
            background: #1e1e1e;
        }
        .asunto-scroll::-webkit-scrollbar-thumb {
            background: #555;
            border-radius: 3px;
        }
        .asunto-scroll::-webkit-scrollbar-thumb:hover {
            background: #fcd535;
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
        @keyframes fadeInOut {
            0% { opacity: 0; transform: translateX(-50%) translateY(-20px); }
            15% { opacity: 1; transform: translateX(-50%) translateY(0); }
            85% { opacity: 1; transform: translateX(-50%) translateY(0); }
            100% { opacity: 0; transform: translateX(-50%) translateY(-20px); }
        }

        .btn-nuevo-ticket {
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
        }
        .btn-nuevo-ticket:hover {
            background: #e6c12e;
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
        .modal-overlay.visible {
            display: flex;
        }
        .ticket-form-card {
            background: #1e1e1e;
            border: 1px solid #333;
            border-radius: 12px;
            padding: 40px 50px;
            width: 520px;
            box-shadow: 0 8px 30px rgba(0,0,0,0.6);
            position: relative;
            z-index: 2;
            max-height: 90vh;
            overflow-y: auto;
        }
        .ticket-form-card h2 {
            font-family: 'Bebas Neue', sans-serif;
            font-size: 28px;
            color: #fcd535;
            margin: 0 0 30px 0;
            text-align: center;
            letter-spacing: 2px;
        }
        .ticket-form-grid {
            display: grid;
            gap: 20px;
        }
        .ticket-field {
            display: flex;
            flex-direction: column;
        }
        .ticket-field label {
            font-size: 11px;
            color: #888;
            text-transform: uppercase;
            letter-spacing: 1px;
            margin-bottom: 4px;
        }
        .ticket-field input,
        .ticket-field select {
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
        .ticket-field input:focus,
        .ticket-field select:focus {
            border-color: #fcd535;
        }
        .ticket-field select option {
            background: #2a2a2a;
            color: #fff;
        }
        .ticket-form-buttons {
            display: flex;
            gap: 12px;
            margin-top: 10px;
        }
        .ticket-form-buttons button {
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
        .btn-ticket-cancelar {
            background: transparent;
            border: 1px solid #555;
            color: #ccc;
        }
        .btn-ticket-cancelar:hover {
            border-color: #fcd535;
            color: #fcd535;
        }
        .btn-ticket-guardar {
            background: #fcd535;
            border: none;
            color: #1a1a1a;
        }
        .btn-ticket-guardar:hover {
            background: #e6c12e;
        }

        .btn-modificar-ticket {
            background: transparent;
            border: 1px solid #fcd535;
            color: #fcd535;
            font-family: 'Montserrat', sans-serif;
            font-weight: 700;
            font-size: 11px;
            padding: 6px 12px;
            border-radius: 4px;
            cursor: pointer;
            transition: all 0.2s;
            white-space: nowrap;
            letter-spacing: 0.5px;
        }
        .btn-modificar-ticket:hover {
            background: #fcd535;
            color: #111;
        }
        .btn-modificar-disabled {
            opacity: 0.35;
            border-color: #555;
            color: #666;
            cursor: not-allowed;
        }
        .btn-modificar-disabled:hover {
            background: transparent;
            color: #666;
            border-color: #555;
        }
        .chart-card {
            margin-top: 30px;
        }
        .chart-layout {
            display: flex;
            gap: 30px;
            align-items: stretch;
        }
        .chart-left {
            flex: 1;
            min-width: 0;
        }
        .chart-right {
            width: 260px;
            flex-shrink: 0;
            display: flex;
            align-items: center;
        }
        .promedio-box {
            background: #2a2a2a;
            border: 1px solid #444;
            border-radius: 12px;
            padding: 25px 20px;
            text-align: center;
            width: 100%;
        }
        .promedio-label {
            font-size: 11px;
            color: #888;
            letter-spacing: 1px;
            text-transform: uppercase;
            font-weight: 700;
            margin-bottom: 15px;
            line-height: 1.4;
        }
        .promedio-valor {
            font-family: 'Bebas Neue', sans-serif;
            font-size: 48px;
            color: #fcd535;
            line-height: 1;
        }
        .promedio-unidad {
            font-family: 'Montserrat', sans-serif;
            font-size: 16px;
            color: #aaa;
            font-weight: 400;
        }
        .promedio-fecha {
            font-size: 12px;
            color: #666;
            margin-top: 10px;
        }
        .table-registro th:last-child,
        .table-registro td:last-child {
            text-align: center;
        }

        .modificar-field {
            display: flex;
            flex-direction: column;
        }
        .modificar-field label {
            font-size: 11px;
            color: #888;
            text-transform: uppercase;
            letter-spacing: 1px;
            margin-bottom: 4px;
        }
        .modificar-field .modificar-valor {
            background: #1a1a1a;
            border: 1px solid #333;
            border-radius: 6px;
            padding: 10px 12px;
            color: #aaa;
            font-family: 'Montserrat', sans-serif;
            font-size: 14px;
        }
        .modificar-field select {
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
        .modificar-field select:focus {
            border-color: #fcd535;
        }
        .modificar-field select option {
            background: #2a2a2a;
            color: #fff;
        }
        .modificar-form-buttons {
            display: flex;
            gap: 12px;
            margin-top: 10px;
        }
        .modificar-form-buttons button {
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
            <div class="sidebar-item active"
                onclick="window.location.href='${pageContext.request.contextPath}/soporte'">
                <img class="sidebar-icon" src="${pageContext.request.contextPath}/Soporte.png" alt="Soporte"> Soporte
            </div>
            <div class="sidebar-item" data-modulo="admin">
                <img class="sidebar-icon" src="${pageContext.request.contextPath}/Administracion.png" alt="Módulo de Administración"> Módulo de Administración
            </div>
        </nav>

        <div class="dashboard-main-content">
            <div class="module-container">
                <h1 class="module-title">MESA DE AYUDA - SOPORTE</h1>

                <div class="module-card">
                    <div class="module-actions">
                        <input type="text" id="buscarTicket" placeholder="Buscar por asunto, remitente, categoría o sede" oninput="filtrarTickets()">
                        <button class="btn-nuevo-ticket" onclick="abrirModalNuevoTicket()">+ NUEVO TICKET</button>
                    </div>

                    <h3>Total tickets: <span id="totalTickets">${tickets.size()}</span></h3>

                    <div class="table-wrapper">
                        <table class="table-registro">
                            <thead>
                                <tr>
                                    <th>ID Ticket</th>
                                    <th>Asunto</th>
                                    <th>Remitente</th>
                                    <th>Categoría</th>
                                    <th>Sede</th>
                                    <th>Prioridad</th>
                                    <th>Estado</th>
                                    <th>Acciones</th>
                                </tr>
                            </thead>
                            <tbody id="tablaTicketsBody">
                                <c:forEach var="t" items="${tickets}">
                                    <tr>
                                        <td>${t.idTicket}</td>
                                        <td><span class="asunto-scroll">${t.asuntoTicket}</span></td>
                                        <td>${t.remitente}</td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${t.categoria == 'Máquinas' and t.idMaquinas != null}">
                                                    <span class="tooltip-trigger" data-tooltip="${maquinaMap[t.idMaquinas]}">Máquinas</span>
                                                </c:when>
                                                <c:otherwise>
                                                    ${t.categoria}
                                                </c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td>${sedeMap[t.idSede]}</td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${t.prioridad == 'Alta'}">
                                                    <span class="badge badge-prioridad-alta">Alta</span>
                                                </c:when>
                                                <c:when test="${t.prioridad == 'Media'}">
                                                    <span class="badge badge-prioridad-media">Media</span>
                                                </c:when>
                                                <c:when test="${t.prioridad == 'Baja'}">
                                                    <span class="badge badge-prioridad-baja">Baja</span>
                                                </c:when>
                                                <c:otherwise>
                                                    ${t.prioridad}
                                                </c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${t.estadoTicket == 'Abierto'}">
                                                    <span class="badge badge-estado-abierto">Abierto</span>
                                                </c:when>
                                                <c:when test="${t.estadoTicket == 'En proceso'}">
                                                    <span class="badge badge-estado-enproceso">En proceso</span>
                                                </c:when>
                                                <c:when test="${t.estadoTicket == 'Resuelto'}">
                                                    <span class="badge badge-estado-resuelto">Resuelto</span>
                                                </c:when>
                                                <c:otherwise>
                                                    ${t.estadoTicket}
                                                </c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td>
                                            <button class="btn-modificar-ticket ${t.estadoTicket == 'Resuelto' ? 'btn-modificar-disabled' : ''}"
                                                onclick="${t.estadoTicket == 'Resuelto' ? '' : 'abrirModalModificar(this)'}"
                                                ${t.estadoTicket == 'Resuelto' ? 'disabled' : ''}
                                                data-id="${t.idTicket}"
                                                data-asunto="${t.asuntoTicket}"
                                                data-remitente="${t.remitente}"
                                                data-categoria="${t.categoria}"
                                                data-sede="${sedeMap[t.idSede]}"
                                                data-prioridad="${t.prioridad}"
                                                data-estado="${t.estadoTicket}">MODIFICAR</button>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>

                <div class="module-card chart-card">
                    <h3 style="color:#fcd535;font-family:'Bebas Neue';font-size:24px;letter-spacing:1px;margin:0 0 20px 0;">ESTADÍSTICAS</h3>
                    <div class="chart-layout">
                        <div class="chart-left">
                            <canvas id="chartCategorias" height="150"></canvas>
                        </div>
                        <div class="chart-right">
                            <c:choose>
                                <c:when test="${ultimoPromedio != null}">
                                    <div class="promedio-box">
                                        <div class="promedio-label">TIEMPO PROMEDIO DE RESOLUCIÓN</div>
                                        <div class="promedio-valor"><fmt:formatNumber value="${ultimoPromedio}" pattern="#0.00" /> <span class="promedio-unidad">min</span></div>
                                        <div class="promedio-fecha">${ultimaFecha}</div>
                                    </div>
                                </c:when>
                                <c:otherwise>
                                    <div class="promedio-box">
                                        <div class="promedio-label">TIEMPO PROMEDIO DE RESOLUCIÓN</div>
                                        <div class="promedio-valor">-- <span class="promedio-unidad">min</span></div>
                                        <div class="promedio-fecha">Sin datos</div>
                                    </div>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </div>
                </div>
            </div>

            <div class="modal-overlay" id="modalModificarTicket">
                <div class="ticket-form-card">
                    <h2>MODIFICAR TICKET</h2>
                    <form id="formModificarTicket" action="${pageContext.request.contextPath}/soporte/modificar" method="post">
                        <div class="ticket-form-grid">
                            <div class="modificar-field">
                                <label>ID Ticket</label>
                                <div class="modificar-valor" id="modIdTicket"></div>
                                <input type="hidden" name="idTicket" id="modIdTicketInput">
                            </div>
                            <div class="modificar-field">
                                <label>Asunto</label>
                                <div class="modificar-valor" id="modAsunto"></div>
                            </div>
                            <div class="modificar-field">
                                <label>Remitente</label>
                                <div class="modificar-valor" id="modRemitente"></div>
                            </div>
                            <div class="modificar-field">
                                <label>Categoría</label>
                                <div class="modificar-valor" id="modCategoria"></div>
                            </div>
                            <div class="modificar-field">
                                <label>Sede</label>
                                <div class="modificar-valor" id="modSede"></div>
                            </div>
                            <div class="modificar-field">
                                <label>Prioridad</label>
                                <div class="modificar-valor" id="modPrioridad"></div>
                            </div>
                            <div class="modificar-field">
                                <label>Estado</label>
                                <select name="estadoTicket" id="modEstado" required>
                                    <option value="Abierto">Abierto</option>
                                    <option value="En proceso">En proceso</option>
                                    <option value="Resuelto">Resuelto</option>
                                </select>
                            </div>
                        </div>
                        <div class="modificar-form-buttons">
                            <button type="button" class="btn-ticket-cancelar" onclick="cerrarModalModificar()">CANCELAR</button>
                            <button type="submit" class="btn-ticket-guardar">GUARDAR</button>
                        </div>
                    </form>
                </div>
            </div>

            <div class="modal-overlay" id="modalNuevoTicket">
                <div class="ticket-form-card">
                    <h2>NUEVO TICKET</h2>
                    <form id="formNuevoTicket" action="${pageContext.request.contextPath}/soporte/crear" method="post">
                        <div class="ticket-form-grid">
                            <div class="ticket-field">
                                <label>Asunto del Ticket</label>
                                <input type="text" name="asunto" required>
                            </div>
                            <div class="ticket-field">
                                <label>Remitente</label>
                                <input type="text" name="remitente" required>
                            </div>
                            <div class="ticket-field">
                                <label>Categoría</label>
                                <select name="categoria" id="categoriaSelect" onchange="mostrarCampoCategoria()" required>
                                    <option value="">Seleccionar...</option>
                                    <option value="Suscripciones">Suscripciones</option>
                                    <option value="Registro">Registro</option>
                                    <option value="Software">Software</option>
                                    <option value="Sedes">Sedes</option>
                                    <option value="Máquinas">Máquinas</option>
                                </select>
                            </div>
                            <div class="ticket-field" id="campoSede" style="display:none;">
                                <label>Sede</label>
                                <select name="idSede">
                                    <option value="">Seleccionar sede...</option>
                                    <c:forEach var="s" items="${sedes}">
                                        <option value="${s.idSede}">${s.nombreSede}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="ticket-field" id="campoMaquina" style="display:none;">
                                <label>Máquina</label>
                                <select name="idMaquina">
                                    <option value="">Seleccionar máquina...</option>
                                    <c:forEach var="m" items="${maquinas}">
                                        <option value="${m.idMaquina}">${m.nombreModelo}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="ticket-field">
                                <label>Prioridad</label>
                                <select name="prioridad" required>
                                    <option value="">Seleccionar...</option>
                                    <option value="Baja">Baja</option>
                                    <option value="Media">Media</option>
                                    <option value="Alta">Alta</option>
                                </select>
                            </div>
                        </div>
                        <div class="ticket-form-buttons">
                            <button type="button" class="btn-ticket-cancelar" onclick="cerrarModalNuevoTicket()">CANCELAR</button>
                            <button type="submit" class="btn-ticket-guardar">GUARDAR</button>
                        </div>
                    </form>
                </div>
            </div>

            <c:if test="${perfil != null}">
            <div class="modal-overlay" id="modalPerfil" style="display:none;position:fixed;top:0;left:0;width:100%;height:100%;background:rgba(0,0,0,0.7);z-index:10000;justify-content:center;align-items:center;">
                <div class="profile-card visible" style="display:block;">
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
            </c:if>

            <c:if test="${perfil != null}">
            <div class="modal-overlay" id="modalEditPerfil" style="display:none;position:fixed;top:0;left:0;width:100%;height:100%;background:rgba(0,0,0,0.7);z-index:10000;justify-content:center;align-items:center;">
                <div class="edit-card visible" style="display:block;">
                    <h2>EDITAR PERFIL</h2>
                    <form action="${pageContext.request.contextPath}/perfil/actualizar" method="post">
                        <div class="edit-grid">
                            <div class="edit-field full-width">
                                <label>DNI</label>
                                <input type="text" name="dni" value="${perfil.dni}" readonly>
                            </div>
                            <div class="edit-field">
                                <label>Nombre</label>
                                <input type="text" name="nombre" value="${perfil.nombre}" required>
                            </div>
                            <div class="edit-field">
                                <label>Apellidos</label>
                                <input type="text" name="apellidos" value="${perfil.apellidos}" required>
                            </div>
                            <div class="edit-field">
                                <label>Fecha de Nacimiento</label>
                                <input type="date" name="fechaNacimiento" value="${perfil.fechaNacimiento}" required>
                            </div>
                            <div class="edit-field full-width">
                                <label>Correo Electrónico</label>
                                <input type="email" name="correo" value="${perfil.correo}" required>
                            </div>
                            <div class="edit-field full-width">
                                <label>Teléfono</label>
                                <input type="text" name="telefono" value="${perfil.telefono}" required>
                            </div>
                            <div class="edit-buttons">
                                <button type="button" class="btn-cancel" onclick="cancelarEdicion()">CANCELAR</button>
                                <button type="submit" class="btn-save">GUARDAR</button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
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
            document.getElementById('modalPerfil').style.display = 'flex';
        }

        function cerrarPerfil() {
            document.getElementById('modalPerfil').style.display = 'none';
        }

        function editarPerfil() {
            document.getElementById('modalPerfil').style.display = 'none';
            document.getElementById('modalEditPerfil').style.display = 'flex';
        }

        function cancelarEdicion() {
            document.getElementById('modalEditPerfil').style.display = 'none';
            document.getElementById('modalPerfil').style.display = 'flex';
        }

        function filtrarTickets() {
            var input = document.getElementById('buscarTicket').value.toLowerCase();
            var tbody = document.getElementById('tablaTicketsBody');
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

            document.getElementById('totalTickets').textContent = visibles;
        }

        document.querySelectorAll('.sidebar-item').forEach(function(item) {
            item.addEventListener('click', function() {
                document.querySelectorAll('.sidebar-item').forEach(function(i) { i.classList.remove('active'); });
                this.classList.add('active');
            });
        });

        var tooltipEl = document.createElement('div');
        tooltipEl.id = 'floating-tooltip';
        document.body.appendChild(tooltipEl);

        document.querySelectorAll('.tooltip-trigger').forEach(function(el) {
            el.addEventListener('mouseenter', function(e) {
                tooltipEl.textContent = this.getAttribute('data-tooltip');
                tooltipEl.style.display = 'block';
                posicionarTooltip(e);
            });
            el.addEventListener('mousemove', function(e) {
                posicionarTooltip(e);
            });
            el.addEventListener('mouseleave', function() {
                tooltipEl.style.display = 'none';
            });
        });

        function posicionarTooltip(e) {
            var x = e.clientX + 12;
            var y = e.clientY - 30;
            if (x + tooltipEl.offsetWidth > window.innerWidth) {
                x = e.clientX - tooltipEl.offsetWidth - 12;
            }
            if (y < 10) {
                y = e.clientY + 12;
            }
            tooltipEl.style.left = x + 'px';
            tooltipEl.style.top = y + 'px';
        }

        function abrirModalNuevoTicket() {
            document.getElementById('modalNuevoTicket').classList.add('visible');
            document.getElementById('formNuevoTicket').reset();
            document.getElementById('campoSede').style.display = 'none';
            document.getElementById('campoMaquina').style.display = 'none';
        }

        function cerrarModalNuevoTicket() {
            document.getElementById('modalNuevoTicket').classList.remove('visible');
        }

        function mostrarCampoCategoria() {
            var cat = document.getElementById('categoriaSelect').value;
            document.getElementById('campoSede').style.display = cat === 'Sedes' ? 'block' : 'none';
            document.getElementById('campoMaquina').style.display = cat === 'Máquinas' ? 'block' : 'none';
        }

        function abrirModalModificar(btn) {
            document.getElementById('modIdTicket').textContent = btn.getAttribute('data-id');
            document.getElementById('modIdTicketInput').value = btn.getAttribute('data-id');
            document.getElementById('modAsunto').textContent = btn.getAttribute('data-asunto');
            document.getElementById('modRemitente').textContent = btn.getAttribute('data-remitente');
            document.getElementById('modCategoria').textContent = btn.getAttribute('data-categoria');
            document.getElementById('modSede').textContent = btn.getAttribute('data-sede');
            document.getElementById('modPrioridad').textContent = btn.getAttribute('data-prioridad');
            document.getElementById('modEstado').value = btn.getAttribute('data-estado');
            document.getElementById('modalModificarTicket').classList.add('visible');
        }

        function cerrarModalModificar() {
            document.getElementById('modalModificarTicket').classList.remove('visible');
        }

        var ctx = document.getElementById('chartCategorias').getContext('2d');
        new Chart(ctx, {
            type: 'bar',
            data: {
                labels: [
                    <c:forEach var="entry" items="${categoriasCount}" varStatus="s">
                        '${entry.key}'${s.last ? '' : ','}
                    </c:forEach>
                ],
                datasets: [{
                    label: 'Tickets',
                    data: [
                        <c:forEach var="entry" items="${categoriasCount}" varStatus="s">
                            ${entry.value}${s.last ? '' : ','}
                        </c:forEach>
                    ],
                    backgroundColor: ['#fcd535','#ff6b7a','#5ddb6e','#6db3ff','#ffb366'],
                    borderColor: ['#fcd535','#ff6b7a','#5ddb6e','#6db3ff','#ffb366'],
                    borderWidth: 1,
                    borderRadius: 4
                }]
            },
            options: {
                responsive: true,
                maintainAspectRatio: true,
                plugins: {
                    legend: { display: false }
                },
                scales: {
                    x: {
                        ticks: { color: '#ccc', font: { family: 'Montserrat', size: 12 } },
                        grid: { color: '#333' }
                    },
                    y: {
                        beginAtZero: true,
                        ticks: { color: '#ccc', font: { family: 'Montserrat', size: 12 }, stepSize: 1 },
                        grid: { color: '#333' }
                    }
                }
            }
        });
    </script>
</body>
</html>
