<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>GymWork - Control de Asistencia</title>
    <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/GymWork_Logo.png">
    <link href="https://fonts.googleapis.com/css2?family=Bebas+Neue&family=Montserrat:wght@400;700&display=swap" rel="stylesheet">
    <style>
        * { margin:0; padding:0; box-sizing:border-box; }
        body {
            font-family:'Montserrat',sans-serif;
            background-color:#0f0f0f;
            color:white;
        }
        .navbar {
            background-color:#1a1a1a;
            padding:15px 40px;
            display:flex;
            justify-content:space-between;
            align-items:center;
            border-bottom:3px solid #fcd535;
            box-shadow:0 4px 10px rgba(0,0,0,0.5);
        }
        .navbar h1 {
            font-family:'Bebas Neue',sans-serif;
            margin:0;
            font-size:32px;
            color:#fcd535;
            letter-spacing:2px;
        }
        .profile-container { position:relative; display:inline-block; }
        .profile-btn {
            background:#000; border:2px solid #555; border-radius:50%;
            width:45px; height:45px; display:flex;
            align-items:center; justify-content:center;
            cursor:pointer; transition:all 0.3s ease; padding:0; overflow:hidden;
        }
        .profile-btn img { width:100%; height:100%; object-fit:cover; transform:scale(1.15); }
        .profile-btn:hover { border-color:#fcd535; }
        .dropdown-menu {
            display:none; position:absolute; right:0; top:55px;
            background-color:#1a1a1a; min-width:160px;
            box-shadow:0px 8px 16px rgba(0,0,0,0.6);
            border:1px solid #333; border-radius:4px; z-index:1000; overflow:hidden;
        }
        .dropdown-item {
            color:white; padding:12px 16px; text-decoration:none; display:block;
            font-size:13px; font-weight:bold; transition:background 0.2s, color 0.2s;
            cursor:pointer; border:none; background:none; width:100%;
            text-align:left; font-family:'Montserrat',sans-serif;
        }
        .dropdown-item:hover { background-color:#fcd535; color:black; }
        .show { display:block; }
        .dashboard-layout { display:flex; height:calc(100vh - 80px); }
        .sidebar {
            width:260px; background-color:#1a1a1a;
            border-right:2px solid #fcd535; padding:30px 0;
            display:flex; flex-direction:column;
            box-shadow:4px 0 10px rgba(0,0,0,0.4); overflow-y:auto;
        }
        .sidebar-header {
            font-family:'Bebas Neue',sans-serif; font-size:24px;
            color:#fcd535; text-align:center; padding:10px 15px 25px;
            letter-spacing:2px; border-bottom:1px solid #333; margin-bottom:15px;
        }
        .sidebar-item {
            display:flex; align-items:center; gap:14px;
            padding:18px 25px; color:#cccccc; font-size:15px; font-weight:500;
            cursor:pointer; transition:background 0.2s, color 0.2s, border-color 0.2s;
            border-left:4px solid transparent; letter-spacing:0.5px;
        }
        .sidebar-item:hover {
            background-color:rgba(252,213,53,0.1); color:#ffffff;
            border-left-color:#fcd535;
        }
        .sidebar-item.active {
            background-color:rgba(252,213,53,0.15); color:#fcd535;
            border-left-color:#fcd535;
        }
        .sidebar-icon { width:28px; height:28px; object-fit:contain; flex-shrink:0; }
        .main-content {
            flex:1; display:flex; flex-direction:column;
            background-color:#121212; padding:30px 40px; overflow-y:auto;
        }
        .page-title {
            font-family:'Bebas Neue',sans-serif; font-size:36px;
            color:#fcd535; letter-spacing:2px; margin-bottom:8px;
        }
        .page-subtitle { color:#888; font-size:13px; margin-bottom:25px; }
        .table-wrapper {
            background:#1e1e1e; border:1px solid #333; border-radius:12px;
            padding:20px; overflow-x:auto;
        }
        table {
            width:100%; border-collapse:collapse; font-size:13px;
        }
        thead th {
            background:#2a2a2a; color:#fcd535; padding:12px 14px;
            text-align:left; font-weight:700; text-transform:uppercase;
            letter-spacing:0.5px; border-bottom:2px solid #fcd535;
            white-space:nowrap;
        }
        tbody td {
            padding:12px 14px; border-bottom:1px solid #2a2a2a;
            color:#e0e0e0; white-space:nowrap;
        }
        tbody tr:hover { background-color:rgba(252,213,53,0.05); }
        .badge-plan {
            display:inline-block; padding:4px 10px; border-radius:4px;
            font-size:11px; font-weight:700; text-transform:uppercase;
        }
        .badge-plan.Premium { background:#2ecc71; color:#000; }
        .badge-plan.Basico { background:#3498db; color:#fff; }
        .badge-plan.Fit { background:#f39c12; color:#000; }
        .badge-plan.default { background:#555; color:#fff; }
        .btn-asistencia {
            padding:8px 16px; background:#fcd535; color:#000;
            border:none; border-radius:6px; font-weight:700; font-size:12px;
            cursor:pointer; text-transform:uppercase; letter-spacing:0.5px;
            transition:background 0.2s;
        }
        .btn-asistencia:hover { background:#e6c12c; }
        .modal-overlay {
            display:none; position:fixed; top:0; left:0;
            width:100%; height:100%; background:rgba(0,0,0,0.7);
            z-index:10000; justify-content:center; align-items:center;
        }
        .modal-overlay.show { display:flex; }
        .modal-card {
            background:#1e1e1e; border:1px solid #333; border-radius:12px;
            padding:35px 40px; width:460px; box-shadow:0 8px 30px rgba(0,0,0,0.8);
        }
        .modal-card h2 {
            font-family:'Bebas Neue',sans-serif; font-size:28px;
            color:#fcd535; margin:0 0 6px 0; text-align:center; letter-spacing:2px;
        }
        .modal-card .cliente-info {
            text-align:center; color:#aaa; font-size:13px; margin-bottom:25px;
        }
        .modal-field {
            display:flex; flex-direction:column; margin-bottom:20px;
        }
        .modal-field label {
            font-size:11px; color:#888; text-transform:uppercase;
            letter-spacing:1px; margin-bottom:5px;
        }
        .modal-field input {
            background:#2a2a2a; border:1px solid #444; border-radius:6px;
            padding:10px 12px; color:#fff; font-size:14px;
            font-family:'Montserrat',sans-serif; outline:none;
            transition:border-color 0.2s;
        }
        .modal-field input:focus { border-color:#fcd535; }
        .modal-field .hint {
            font-size:11px; color:#666; margin-top:3px;
        }
        .modal-buttons {
            display:flex; gap:12px; margin-top:25px;
        }
        .modal-buttons button {
            flex:1; padding:12px; font-family:'Montserrat',sans-serif;
            font-size:14px; font-weight:700; border-radius:6px;
            cursor:pointer; text-transform:uppercase; letter-spacing:1px;
            transition:all 0.2s;
        }
        .btn-cancel {
            background:transparent; border:1px solid #555; color:#ccc;
        }
        .btn-cancel:hover { border-color:#fcd535; color:#fcd535; }
        .btn-save {
            background:#fcd535; border:none; color:#1a1a1a;
        }
        .btn-save:hover { background:#e6c12e; }
        .toast {
            display:none; position:fixed; top:30px; left:50%;
            transform:translateX(-50%); padding:16px 32px;
            border-radius:8px; font-family:'Montserrat',sans-serif;
            font-size:16px; font-weight:700; letter-spacing:1px;
            box-shadow:0 4px 20px rgba(0,0,0,0.5); z-index:9999;
        }
        .toast.show { display:block; animation:fadeInOut 2.5s ease forwards; }
        .toast.success { background:#1a1a1a; color:#fcd535; border:2px solid #fcd535; }
        .toast.error { background:#1a1a1a; color:#ff4444; border:2px solid #ff4444; }
        @keyframes fadeInOut {
            0% { opacity:0; transform:translateX(-50%) translateY(-20px); }
            15% { opacity:1; transform:translateX(-50%) translateY(0); }
            85% { opacity:1; transform:translateX(-50%) translateY(0); }
            100% { opacity:0; transform:translateX(-50%) translateY(-20px); }
        }
        .sin-registros {
            text-align:center; padding:40px; color:#666; font-size:15px;
        }
        @media(max-width:768px){
            .sidebar{width:200px;}
            .main-content{padding:20px;}
            .modal-card{width:90%;padding:25px 20px;}
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
                <img class="sidebar-icon" src="${pageContext.request.contextPath}/Suscripciones.png" alt="Men&uacute;"> Men&uacute; Principal
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
            <div class="sidebar-item"
                onclick="window.location.href='${pageContext.request.contextPath}/maquinas'">
                <img class="sidebar-icon" src="${pageContext.request.contextPath}/Maquinas.png" alt="Estado de M&aacute;quinas"> Estado de M&aacute;quinas
            </div>
            <div class="sidebar-item"
                onclick="window.location.href='${pageContext.request.contextPath}/soporte'">
                <img class="sidebar-icon" src="${pageContext.request.contextPath}/Soporte.png" alt="Soporte"> Soporte
            </div>
            <div class="sidebar-item"
                onclick="window.location.href='${pageContext.request.contextPath}/administracion'">
                <img class="sidebar-icon" src="${pageContext.request.contextPath}/Administracion.png" alt="M&oacute;dulo de Administraci&oacute;n"> M&oacute;dulo de Administraci&oacute;n
            </div>
            <div class="sidebar-item active"
                onclick="window.location.href='${pageContext.request.contextPath}/validacion'">
                <img class="sidebar-icon" src="${pageContext.request.contextPath}/Suscripciones.png" alt="Control de Asistencia"> Control de Asistencia
            </div>
        </nav>

        <div class="main-content">
            <div class="page-title">CONTROL DE ASISTENCIA</div>
            <div class="page-subtitle">Clientes con matr&iacute;cula activa &mdash; Sede ID: ${idSede}</div>

            <div class="table-wrapper">
                <table>
                    <thead>
                        <tr>
                            <th>C&Oacute;DIGO</th>
                            <th>NOMBRE</th>
                            <th>APELLIDO PATERNO</th>
                            <th>APELLIDO MATERNO</th>
                            <th>PLAN</th>
                            <th>TEL&Eacute;FONO</th>
                            <th>ASISTENCIA</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:choose>
                            <c:when test="${empty clientesActivos}">
                                <tr><td colspan="7" class="sin-registros">No hay clientes con matr&iacute;cula activa</td></tr>
                            </c:when>
                            <c:otherwise>
                                <c:forEach var="cliente" items="${clientesActivos}">
                                    <tr>
                                        <td>${cliente.codigo}</td>
                                        <td>${cliente.nombre}</td>
                                        <td>${cliente.apellidoPaterno}</td>
                                        <td>${cliente.apellidoMaterno}</td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${planesMap[cliente.codigo] == '001'}"><span class="badge-plan Fit">PAQUETE FIT</span></c:when>
                                                <c:when test="${planesMap[cliente.codigo] == '010'}"><span class="badge-plan Basico">PAQUETE B&Aacute;SICO</span></c:when>
                                                <c:when test="${planesMap[cliente.codigo] == '100'}"><span class="badge-plan Premium">PLAN PREMIUM</span></c:when>
                                                <c:otherwise><span class="badge-plan default">${planesMap[cliente.codigo]}</span></c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td>${cliente.telefono}</td>
                                        <td>
                                            <button class="btn-asistencia" onclick="abrirAsistencia('${cliente.codigo}','${cliente.nombre} ${cliente.apellidoPaterno}')">ASISTENCIA</button>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </c:otherwise>
                        </c:choose>
                    </tbody>
                </table>
            </div>
        </div>
    </div>

    <div class="modal-overlay" id="modalAsistencia">
        <div class="modal-card">
            <h2>REGISTRAR ASISTENCIA</h2>
            <div class="cliente-info" id="infoCliente"></div>
            <form id="formAsistencia" action="${pageContext.request.contextPath}/validacion/asistencia" method="post">
                <input type="hidden" name="codigoCliente" id="inputCodigo">
                <div class="modal-field">
                    <label for="inputHoraIngreso">HORA DE INGRESO</label>
                    <input type="text" id="inputHoraIngreso" name="horaIngreso" placeholder="08:00" required>
                    <span class="hint">Formato: HH:mm</span>
                </div>
                <div class="modal-field">
                    <label for="inputHoraSalida">HORA DE SALIDA</label>
                    <input type="text" id="inputHoraSalida" name="horaSalida" placeholder="20:00" required>
                    <span class="hint">Formato: HH:mm</span>
                </div>
                <div class="modal-buttons">
                    <button type="button" class="btn-cancel" onclick="cerrarAsistencia()">CANCELAR</button>
                    <button type="submit" class="btn-save">REGISTRAR</button>
                </div>
            </form>
        </div>
    </div>

    <c:if test="${not empty exito}">
        <div class="toast success show" id="toastExito">${exito}</div>
    </c:if>
    <c:if test="${not empty error}">
        <div class="toast error show" id="toastError">${error}</div>
    </c:if>

    <script>
        var btnPerfil = document.getElementById('btnPerfil');
        var menuDesp = document.getElementById('menuDesplegable');
        btnPerfil.addEventListener('click', function(e) {
            e.stopPropagation();
            menuDesp.classList.toggle('show');
        });
        document.addEventListener('click', function() {
            if (menuDesp.classList.contains('show')) menuDesp.classList.remove('show');
        });

        function cerrarSesion() {
            window.location.href = '${pageContext.request.contextPath}/logout';
        }

        function verPerfil() {
            window.location.href = '${pageContext.request.contextPath}/menu';
        }

        function abrirAsistencia(codigo, nombre) {
            document.getElementById('inputCodigo').value = codigo;
            document.getElementById('infoCliente').textContent = 'Cliente: ' + nombre + ' (C\u00F3digo: ' + codigo + ')';
            document.getElementById('inputHoraIngreso').value = '';
            document.getElementById('inputHoraSalida').value = '';
            document.getElementById('modalAsistencia').classList.add('show');
        }

        function cerrarAsistencia() {
            document.getElementById('modalAsistencia').classList.remove('show');
        }

        document.getElementById('formAsistencia').addEventListener('submit', function(e) {
            var hi = document.getElementById('inputHoraIngreso').value.trim();
            var hs = document.getElementById('inputHoraSalida').value.trim();
            var patron = /^([01]\d|2[0-3]):[0-5]\d$/;
            if (!patron.test(hi)) {
                alert('Formato inv\u00E1lido para HORA DE INGRESO. Use HH:mm');
                e.preventDefault(); return;
            }
            if (!patron.test(hs)) {
                alert('Formato inv\u00E1lido para HORA DE SALIDA. Use HH:mm');
                e.preventDefault(); return;
            }
        });

        var tExito = document.getElementById('toastExito');
        var tError = document.getElementById('toastError');
        if (tExito) { setTimeout(function(){ tExito.classList.remove('show'); }, 3000); }
        if (tError) { setTimeout(function(){ tError.classList.remove('show'); }, 3000); }

        document.getElementById('modalAsistencia').addEventListener('click', function(e) {
            if (e.target === this) cerrarAsistencia();
        });
    </script>
</body>
</html>
