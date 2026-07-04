<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>GymWork - Administración</title>
    <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/GymWork_Logo.png">
    <link href="https://fonts.googleapis.com/css2?family=Bebas+Neue&family=Montserrat:wght@400;700&display=swap" rel="stylesheet">
    <style>
        * {
            box-sizing: border-box;
        }
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
        .dropdown-menu.show {
            display: block;
        }
        .dashboard-layout {
            display: flex;
            height: calc(100vh - 80px);
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
            flex-shrink: 0;
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
            text-decoration: none;
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
        .dashboard-main-content {
            position: relative;
            flex: 1;
            display: flex;
            justify-content: center;
            background-color: #121212;
            overflow-y: auto;
            color: #ffffff;
            font-family: 'Montserrat', sans-serif;
            padding: 30px;
        }
        .admin-module {
            width: 100%;
            max-width: 1200px;
        }
        .admin-header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 25px;
        }
        .module-title {
            color: #fcd535;
            font-family: 'Bebas Neue', sans-serif;
            font-size: 40px;
            margin: 0;
            letter-spacing: 2px;
        }
        .digital-clock {
            font-family: 'Bebas Neue', sans-serif;
            font-size: 32px;
            color: #fcd535;
            letter-spacing: 3px;
            background: #1a1a1a;
            padding: 10px 24px;
            border-radius: 8px;
            border: 1px solid #333;
        }
        .cards-row {
            display: flex;
            gap: 20px;
            margin-bottom: 30px;
        }
        .stat-card {
            flex: 1;
            background: #1e1e1e;
            border: 1px solid #333;
            border-radius: 12px;
            padding: 25px;
            display: flex;
            align-items: center;
            gap: 18px;
            box-shadow: 0 8px 30px rgba(0,0,0,0.6);
            transition: border-color 0.2s;
        }
        .stat-card:hover {
            border-color: #fcd535;
        }
        .stat-icon {
            font-size: 40px;
            line-height: 1;
        }
        .stat-body {
            display: flex;
            flex-direction: column;
        }
        .stat-label {
            font-size: 12px;
            color: #888;
            text-transform: uppercase;
            letter-spacing: 1px;
            margin-bottom: 6px;
        }
        .stat-value {
            font-family: 'Bebas Neue', sans-serif;
            font-size: 36px;
            color: #fcd535;
            line-height: 1;
            display: flex;
            align-items: center;
            gap: 8px;
        }
        .stat-ganancia {
            font-size: 32px;
        }
        .plan-badge {
            display: inline-flex;
            flex-direction: column;
            align-items: center;
            padding: 6px 14px;
            border-radius: 4px;
            font-family: 'Montserrat', sans-serif;
            font-size: 14px;
            font-weight: 700;
            line-height: 1.2;
        }
        .plan-label {
            font-size: 10px;
            font-weight: 400;
            margin-top: 2px;
        }
        .plan-basico {
            background: rgba(252,213,53,0.15);
            color: #fcd535;
            border: 1px solid rgba(252,213,53,0.4);
        }
        .plan-fit {
            background: rgba(0,123,255,0.15);
            color: #6db3ff;
            border: 1px solid rgba(0,123,255,0.4);
        }
        .plan-premium {
            background: rgba(220,53,69,0.15);
            color: #ff6b7a;
            border: 1px solid rgba(220,53,69,0.4);
        }
        .admin-tables-grid {
            display: flex;
            flex-direction: column;
            gap: 25px;
        }
        .admin-table-card {
            background: #1e1e1e;
            border: 1px solid #333;
            border-radius: 12px;
            padding: 25px 30px;
            box-shadow: 0 8px 30px rgba(0,0,0,0.6);
        }
        .card-title {
            font-family: 'Bebas Neue', sans-serif;
            font-size: 26px;
            color: #fcd535;
            margin: 0 0 18px 0;
            letter-spacing: 2px;
            border-bottom: 1px solid #333;
            padding-bottom: 12px;
        }
        .table-actions {
            margin-bottom: 15px;
        }
        .table-wrapper {
            max-height: 350px;
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
            font-size: 13px;
            letter-spacing: 0.5px;
        }
        .table-registro td {
            padding: 12px;
            border-bottom: 1px solid #333;
            color: #fff;
            font-size: 14px;
        }
        .table-registro tr:hover {
            background: #2a2a2a;
        }
        .actions-cell {
            text-align: right;
            white-space: nowrap;
        }
        .action-btn {
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
            letter-spacing: 0.5px;
        }
        .action-btn:hover {
            background: #e6c12e;
        }
        .btn-mod {
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
            letter-spacing: 0.5px;
            margin: 2px;
        }
        .btn-mod:hover {
            background: #fcd535;
            color: #111;
        }
        .btn-del {
            background: transparent;
            border: 1px solid #ff6b7a;
            color: #ff6b7a;
            font-family: 'Montserrat', sans-serif;
            font-weight: 700;
            font-size: 11px;
            padding: 6px 12px;
            border-radius: 4px;
            cursor: pointer;
            transition: all 0.2s;
            letter-spacing: 0.5px;
            margin: 2px;
        }
        .btn-del:hover {
            background: #ff6b7a;
            color: #111;
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
        .badge-activo {
            background: rgba(40,167,69,0.12);
            color: #5ddb6e;
            border-color: rgba(40,167,69,0.4);
        }
        .badge-vencido {
            background: rgba(220,53,69,0.12);
            color: #ff6b7a;
            border-color: rgba(220,53,69,0.4);
        }
        .radio-cell {
            text-align: center;
        }
        .gold-radio {
            appearance: none;
            -webkit-appearance: none;
            width: 18px;
            height: 18px;
            border: 2px solid #555;
            border-radius: 50%;
            background: transparent;
            cursor: pointer;
            transition: all 0.2s;
            vertical-align: middle;
        }
        .gold-radio:checked {
            border-color: #fcd535;
            background: #fcd535;
            box-shadow: inset 0 0 0 3px #1e1e1e;
        }
        .gold-radio:hover {
            border-color: #fcd535;
        }
        .pagos-footer {
            text-align: right;
            margin-top: 18px;
            padding-top: 15px;
            border-top: 1px solid #333;
        }
        .btn-renovar {
            background: #fcd535;
            border: none;
            color: #111;
            font-family: 'Montserrat', sans-serif;
            font-weight: 700;
            font-size: 14px;
            padding: 12px 32px;
            border-radius: 6px;
            cursor: pointer;
            transition: background 0.2s;
            letter-spacing: 1px;
            text-transform: uppercase;
        }
        .btn-renovar:hover {
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
            <a class="sidebar-item" href="${pageContext.request.contextPath}/menu">
                <img class="sidebar-icon" src="${pageContext.request.contextPath}/Suscripciones.png" alt="Menú Principal"> Menú Principal
            </a>
            <a class="sidebar-item" href="${pageContext.request.contextPath}/menu?modulo=suscripciones">
                <img class="sidebar-icon" src="${pageContext.request.contextPath}/Suscripciones.png" alt="Suscripciones"> Suscripciones
            </a>
            <a class="sidebar-item" href="${pageContext.request.contextPath}/registro">
                <img class="sidebar-icon" src="${pageContext.request.contextPath}/Registro.png" alt="Registro"> Registro
            </a>
            <a class="sidebar-item" href="${pageContext.request.contextPath}/sedes">
                <img class="sidebar-icon" src="${pageContext.request.contextPath}/Sedes.png" alt="Sedes"> Sedes
            </a>
            <a class="sidebar-item" href="${pageContext.request.contextPath}/maquinas">
                <img class="sidebar-icon" src="${pageContext.request.contextPath}/Maquinas.png" alt="Estado de Máquinas"> Estado de Máquinas
            </a>
            <a class="sidebar-item" href="${pageContext.request.contextPath}/soporte">
                <img class="sidebar-icon" src="${pageContext.request.contextPath}/Soporte.png" alt="Soporte"> Soporte
            </a>
            <a class="sidebar-item active" href="${pageContext.request.contextPath}/administracion">
                <img class="sidebar-icon" src="${pageContext.request.contextPath}/Administracion.png" alt="Módulo de Administración"> Módulo de Administración
            </a>
            <a class="sidebar-item" href="${pageContext.request.contextPath}/validacion">
                <img class="sidebar-icon" src="${pageContext.request.contextPath}/Suscripciones.png" alt="Control de Asistencia"> Control de Asistencia
            </a>
        </nav>

        <div class="dashboard-main-content">
            <div class="admin-module">
                <div class="admin-header">
                    <h1 class="module-title">MÓDULO DE ADMINISTRACIÓN</h1>
                    <div class="digital-clock" id="relojDigital"></div>
                </div>

                <div class="cards-row">
                    <div class="stat-card">
                        <div class="stat-icon">👥</div>
                        <div class="stat-body">
                            <span class="stat-label">Total Clientes</span>
                            <span class="stat-value">${totalClientes}</span>
                        </div>
                    </div>
                    <div class="stat-card">
                        <div class="stat-icon">📋</div>
                        <div class="stat-body">
                            <span class="stat-label">Membresías Activas</span>
                                <span class="stat-value">
                                    <span class="plan-badge plan-basico">${basicosActivos}<span class="plan-label">Básico</span></span>
                                    <span class="plan-badge plan-fit">${fitsActivos}<span class="plan-label">Fit</span></span>
                                    <span class="plan-badge plan-premium">${premiumsActivos}<span class="plan-label">Premium</span></span>
                                </span>
                        </div>
                    </div>
                    <div class="stat-card">
                        <div class="stat-icon">💰</div>
                        <div class="stat-body">
                            <span class="stat-label">Ganancia Total</span>
                            <span class="stat-value stat-ganancia">S/ ${gananciaTotal}</span>
                        </div>
                    </div>
                </div>

                <div class="admin-tables-grid">
                    <div class="admin-table-card">
                        <h2 class="card-title">ADMINISTRADORES</h2>
                        <div class="table-actions">
                            <button class="action-btn" onclick="location.href='${pageContext.request.contextPath}/administracion/registrar-admin'">REGISTRAR</button>
                        </div>
                        <div class="table-wrapper">
                            <table class="table-registro">
                                <thead>
                                    <tr>
                                        <th>Usuario</th>
                                        <th>Contraseña</th>
                                        <th>Acciones</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="u" items="${usuarios}">
                                        <tr>
                                                <td>${u.username}</td>
                                                <td>${u.password}</td>
                                            <td class="actions-cell">
                                                <button class="btn-mod" onclick="location.href='${pageContext.request.contextPath}/administracion/modificar-admin?id=${u.idUsuario}'">MODIFICAR</button>
                                                <button class="btn-del" onclick="location.href='${pageContext.request.contextPath}/administracion/eliminar-admin?id=${u.idUsuario}'">ELIMINAR</button>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div>

                    <div class="admin-table-card">
                        <h2 class="card-title">MEMBRESÍAS</h2>
                        <div class="table-wrapper">
                            <table class="table-registro">
                                <thead>
                                    <tr>
                                        <th>Código</th>
                                        <th>Nombre</th>
                                        <th>Plan</th>
                                        <th>Fecha Registro</th>
                                        <th>Último Pago</th>
                                        <th>Estado</th>
                                        <th>Acciones</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="cl" items="${clientes}">
                                        <tr>
                                            <td>${cl.codigo}</td>
                                            <td>${cl.nombre} ${cl.apellidoPaterno} ${cl.apellidoMaterno}</td>
                                            <td>
                                                <c:choose>
                                                    <c:when test="${cl.tipoPlan == '100'}">Premium</c:when>
                                                    <c:when test="${cl.tipoPlan == '010'}">Básico</c:when>
                                                    <c:when test="${cl.tipoPlan == '001'}">Fit</c:when>
                                                    <c:otherwise>${cl.tipoPlan}</c:otherwise>
                                                </c:choose>
                                            </td>
                                            <td>${cl.fechaRegistro}</td>
                                            <td>${ultimoPagoMap[cl.codigo]}</td>
                                            <td>
                                                <span class="badge ${estadosMap[cl.codigo] == 'Activo' ? 'badge-activo' : 'badge-vencido'}">${estadosMap[cl.codigo]}</span>
                                            </td>
                                            <td>
                                                <form action="/administracion/renovar" method="POST" style="margin:0">
                                                    <input type="hidden" name="codigoCliente" value="${cl.codigo}">
                                                    <button type="submit" class="btn-mod">RENOVAR</button>
                                                </form>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div>

                    <div class="admin-table-card">
                        <h2 class="card-title">HISTORIAL DE PAGOS</h2>
                        <div class="table-wrapper">
                            <table class="table-registro">
                                <thead>
                                    <tr>
                                        <th>Código Cliente</th>
                                        <th>Plan</th>
                                        <th>Fecha de Pago</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="p" items="${pagos}">
                                        <tr>
                                            <td>${p.codigoCliente}</td>
                                            <td>
                                                <c:choose>
                                                    <c:when test="${p.tipoPlan == '100'}">Premium</c:when>
                                                    <c:when test="${p.tipoPlan == '010'}">Básico</c:when>
                                                    <c:when test="${p.tipoPlan == '001'}">Fit</c:when>
                                                    <c:otherwise>${p.tipoPlan}</c:otherwise>
                                                </c:choose>
                                            </td>
                                            <td>${p.fechaPago}</td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

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
                    <span>${edad} años</span>
                </div>
                <div class="profile-field full-width">
                    <label>Correo Electrónico</label>
                    <span>${perfil.correo}</span>
                </div>
                <div class="profile-field full-width">
                    <label>Teléfono</label>
                    <span>${perfil.telefono}</span>
                </div>
                <button class="profile-close-btn" onclick="cerrarPerfil()">CERRAR</button>
                <button class="profile-edit-btn" onclick="editarPerfil()">EDITAR PERFIL</button>
            </div>
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

        function actualizarReloj() {
            var ahora = new Date();
            var h = String(ahora.getHours()).padStart(2, '0');
            var m = String(ahora.getMinutes()).padStart(2, '0');
            var s = String(ahora.getSeconds()).padStart(2, '0');
            document.getElementById('relojDigital').textContent = h + ':' + m + ':' + s;
        }
        actualizarReloj();
        setInterval(actualizarReloj, 1000);
    </script>
</body>
</html>