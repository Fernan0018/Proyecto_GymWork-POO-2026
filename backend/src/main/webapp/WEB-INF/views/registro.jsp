<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>


<!DOCTYPE html>
<html>

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
            .module-container{
                width:90%;
                max-width:1100px;
                z-index:2;
                overflow-y: auto;
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
                overflow-y: auto;
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
                padding:12px;
                color:#fff;
                font-family:'Montserrat',sans-serif;
                font-size:14px;
                outline:none;
                transition:border-color 0.2s;
            }
            .module-actions input[type="text"]:focus{
                border-color:#fcd535;
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
            .modal-overlay{
                display:none;
                position:fixed;
                top:0;
                left:0;
                width:100%;
                height:100%;
                background:rgba(0,0,0,0.7);
                z-index:10000;
                justify-content:center;
                align-items:center;
            }
            .modal-overlay.show{
                display:flex;
            }
            .modal-card{
                background:#1e1e1e;
                border:1px solid #333;
                border-radius:12px;
                padding:35px 40px;
                width:500px;
                max-height:80vh;
                overflow-y:auto;
                box-shadow:0 8px 30px rgba(0,0,0,0.8);
            }
            .modal-card h2{
                font-family:'Bebas Neue',sans-serif;
                font-size:28px;
                color:#fcd535;
                margin:0 0 25px 0;
                text-align:center;
                letter-spacing:2px;
            }
            .modal-field{
                display:flex;
                flex-direction:column;
                margin-bottom:14px;
            }
            .modal-field label{
                font-size:11px;
                color:#888;
                text-transform:uppercase;
                letter-spacing:1px;
                margin-bottom:5px;
            }
            .modal-field input,.modal-field select{
                background:#2a2a2a;
                border:1px solid #444;
                border-radius:6px;
                padding:10px 12px;
                color:#fff;
                font-family:'Montserrat',sans-serif;
                font-size:14px;
                outline:none;
                transition:border-color 0.2s;
                box-sizing:border-box;
            }
            .modal-field input:focus,.modal-field select:focus{
                border-color:#fcd535;
            }
            .modal-field input[readonly]{
                background:#1a1a1a;
                color:#aaa;
                cursor:not-allowed;
            }
            .modal-field select[disabled]{
                background:#1a1a1a;
                color:#aaa;
                cursor:not-allowed;
            }
            .modal-buttons{
                display:flex;
                gap:12px;
                margin-top:25px;
            }
            .modal-buttons button{
                flex:1;
                padding:12px;
                font-family:'Montserrat',sans-serif;
                font-size:14px;
                font-weight:700;
                border-radius:6px;
                cursor:pointer;
                text-transform:uppercase;
                letter-spacing:1px;
                transition:all 0.2s;
            }
            .modal-grid{
                display:grid;
                grid-template-columns:1fr 1fr;
                gap:12px;
            }
            .modal-grid .full-width{
                grid-column:1 / -1;
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
            padding: 40px;
            justify-content: center;
            background-color: #121212;
            overflow-y: auto;
            color: #ffffff;
            font-family: 'Montserrat', sans-serif;
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
        .btn-primary {
            background: #fcd535;
            border: none;
            color: #1a1a1a;
        }
        .btn-primary:hover {
            background: #e6c12e;
        }
        @keyframes fadeInOut {
            0% { opacity: 0; transform: translateX(-50%) translateY(-20px); }
            15% { opacity: 1; transform: translateX(-50%) translateY(0); }
            85% { opacity: 1; transform: translateX(-50%) translateY(0); }
            100% { opacity: 0; transform: translateX(-50%) translateY(-20px); }
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
            <div class="sidebar-item"
                onclick="window.location.href='${pageContext.request.contextPath}/maquinas'">
                <img class="sidebar-icon" src="${pageContext.request.contextPath}/Maquinas.png" alt="Estado de Máquinas"> Estado de Máquinas
            </div>
            <div class="sidebar-item"
                onclick="window.location.href='${pageContext.request.contextPath}/soporte'">
                <img class="sidebar-icon" src="${pageContext.request.contextPath}/Soporte.png" alt="Soporte"> Soporte
            </div>
            <div class="sidebar-item" data-modulo="admin">
                <img class="sidebar-icon" src="${pageContext.request.contextPath}/Administracion.png" alt="Módulo de Administración"> Módulo de Administración
            </div>
        </nav>
         <div class="dashboard-main-content">
            <div class="module-container">

    <h1 class="module-title">
        REGISTRO DE CLIENTES
    </h1>

    <div class="module-card">

        <div class="module-actions">
            <input type="text" id="buscarCliente" placeholder="Buscar por nombre o código" oninput="filtrarClientes()">
            <a href="${pageContext.request.contextPath}/registro/reporte/pdf" class="action-btn" style="text-decoration:none;background:#28a745;color:white;display:inline-flex;align-items:center;gap:6px;">DESCARGAR PDF</a>
        </div>
        <h3>Total clientes: <span id="totalClientes">${clientes.size()}</span></h3>

        <div class="table-wrapper">
        <table class="table-registro">
            <thead>
                <tr>
                    <th>Código</th>
                    <th>Nombre</th>
                    <th>Apellidos</th>
                    <th>Plan</th>
                    <th>Fecha Registro</th>
                    <th>Acciones</th>
                </tr>
            </thead>
            <tbody id="tablaRegistroBody">
                <c:forEach var="c" items="${clientes}">
                    <tr data-codigo="${c.codigo}"
                        data-nombre="${c.nombre}"
                        data-apellido-paterno="${c.apellidoPaterno}"
                        data-apellido-materno="${c.apellidoMaterno}"
                        data-tipo-plan="${c.tipoPlan}"
                        data-fecha-registro="${c.fechaRegistro}"
                        data-sexo="${c.sexo}"
                        data-correo="${c.correo}"
                        data-telefono="${c.telefono}"
                        data-departamento="${c.departamento}"
                        data-ciudad="${c.ciudad}"
                        data-calle="${c.calle}"
                        data-fecha-nacimiento="${c.fechaNacimiento}"
                        data-edad="${c.edad}">
                        <td>${c.codigo}</td>
                        <td>${c.nombre}</td>
                        <td>${c.apellidoPaterno} ${c.apellidoMaterno}</td>
                        <td>
                                            <c:choose>
                                                <c:when test="${c.tipoPlan == '001'}">Paquete Fit</c:when>
                                                <c:when test="${c.tipoPlan == '010'}">Paquete B&aacute;sico</c:when>
                                                <c:when test="${c.tipoPlan == '100'}">Plan Premium</c:when>
                                                <c:otherwise>${c.tipoPlan}</c:otherwise>
                                            </c:choose>
                                        </td>
                        <td>${c.fechaRegistro}</td>
                        <td style="white-space:nowrap;">
                            <button class="action-btn-sm" onclick="abrirModalVisualizar(this)">&#128065; VER</button>
                            <button class="action-btn-sm" onclick="abrirModalEditar(this)">&#9998; EDIT</button>
                            <button class="btn-danger-sm" onclick="abrirModalEliminar(this)">&#128465; ELIM</button>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
        </div>

    </div>

    <div class="modal-overlay" id="modalSuscripcion">
        <div class="modal-card">
            <h2 id="modalSuscripcionTitulo">SUSCRIPCIONES</h2>
            <form id="formSuscripcion" action="${pageContext.request.contextPath}/registro/actualizar" method="post">
                <div class="modal-grid">
                    <div class="modal-field">
                        <label>Código</label>
                        <input type="text" name="codigo" id="sCódigo" readonly>
                    </div>
                    <div class="modal-field">
                        <label>Nombre</label>
                        <input type="text" name="nombre" id="sNombre">
                    </div>
                    <div class="modal-field">
                        <label>Apellido Paterno</label>
                        <input type="text" name="apellidoPaterno" id="sApellidoPaterno">
                    </div>
                    <div class="modal-field">
                        <label>Apellido Materno</label>
                        <input type="text" name="apellidoMaterno" id="sApellidoMaterno">
                    </div>
                    <div class="modal-field">
                        <label>Edad</label>
                        <input type="number" name="edad" id="sEdad" readonly>
                    </div>
                    <div class="modal-field">
                        <label>Fecha Nacimiento</label>
                        <input type="date" name="fechaNacimiento" id="sFechaNacimiento" onchange="calcularEdadModal()">
                    </div>
                    <div class="modal-field">
                        <label>Sexo</label>
                        <select name="sexo" id="sSexo">
                            <option value="M">Masculino</option>
                            <option value="F">Femenino</option>
                        </select>
                    </div>
                    <div class="modal-field">
                        <label>Correo</label>
                        <input type="email" name="correo" id="sCorreo">
                    </div>
                    <div class="modal-field full-width">
                        <label>Teléfono</label>
                        <input type="text" name="telefono" id="sTelefono">
                    </div>
                    <div class="modal-field full-width">
                        <label>Tipo de Plan</label>
                            <select name="tipoPlan" id="sTipoPlan">
                                    <option value="001">Paquete Fit</option>
                                    <option value="010">Paquete B&aacute;sico</option>
                                    <option value="100">Plan Premium</option>
                                </select>
                    </div>
                    <div class="modal-field">
                        <label>Departamento</label>
                        <input type="text" name="departamento" id="sDepartamento">
                    </div>
                    <div class="modal-field">
                        <label>Ciudad</label>
                        <input type="text" name="ciudad" id="sCiudad">
                    </div>
                    <div class="modal-field full-width">
                        <label>Calle</label>
                        <input type="text" name="calle" id="sCalle">
                    </div>
                </div>
                <div class="modal-buttons">
                    <button type="button" class="btn-cancel" onclick="cerrarModalSuscripcion()">CANCELAR</button>
                    <button type="submit" class="btn-save" id="btnGuardarSuscripcion" style="display:none;">GUARDAR</button>
                </div>
            </form>
        </div>
    </div>

    <div class="modal-overlay" id="modalEliminar">
        <div class="modal-card" style="width:400px;">
            <h2>ELIMINAR CLIENTE</h2>
            <p style="text-align:center;color:#ccc;margin-bottom:20px;">
                ¿Está seguro de eliminar al cliente <strong id="elimNombreCliente" style="color:#fcd535;"></strong>?
            </p>
            <form id="formEliminar" action="${pageContext.request.contextPath}/registro/eliminar" method="post">
                <input type="hidden" name="codigo" id="elimCodigo">
                <div class="modal-buttons">
                    <button type="button" class="btn-cancel" onclick="cerrarModalEliminar()">CANCELAR</button>
                    <button type="submit" class="btn-primary" style="background:#dc3545;color:#fff;border:none;padding:12px;font-weight:700;border-radius:6px;cursor:pointer;text-transform:uppercase;letter-spacing:1px;">ELIMINAR</button>
                </div>
            </form>
        </div>
    </div>

            <c:if test="${not empty error}">
            <div class="error-toast show" id="errorToast">${error}</div>
            </c:if>

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
            </c:if>

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
            var wc = document.getElementById('welcomeContainer');
            if (wc) wc.style.display = 'none';
            document.getElementById('profileCard').classList.add('visible');
        }

        function cerrarPerfil() {
            document.getElementById('profileCard').classList.remove('visible');
            var wc = document.getElementById('welcomeContainer');
            if (wc) wc.style.display = '';
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
            });
        });

        var errorToast = document.getElementById('errorToast');
        if (errorToast) {
            setTimeout(function() {
                errorToast.style.display = 'none';
            }, 3000);
        }

        function filtrarClientes() {
            var input = document.getElementById('buscarCliente').value.toLowerCase();
            var tbody = document.getElementById('tablaRegistroBody');
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

            document.getElementById('totalClientes').textContent = visibles;
        }

        function poblarModalSuscripcion(tr, soloLectura) {
            var d = tr.dataset;
            document.getElementById('sCódigo').value = d.codigo || '';
            document.getElementById('sNombre').value = d.nombre || '';
            document.getElementById('sApellidoPaterno').value = d.apellidoPaterno || '';
            document.getElementById('sApellidoMaterno').value = d.apellidoMaterno || '';
            if (d.fechaNacimiento) {
                var parts = d.fechaNacimiento.split('-');
                if (parts.length === 3) {
                    var nac = new Date(parts[0], parts[1] - 1, parts[2]);
                    var hoy = new Date();
                    var calcEdad = hoy.getFullYear() - nac.getFullYear();
                    var m = hoy.getMonth() - nac.getMonth();
                    if (m < 0 || (m === 0 && hoy.getDate() < nac.getDate())) calcEdad--;
                    document.getElementById('sEdad').value = calcEdad;
                }
            } else {
                document.getElementById('sEdad').value = '';
            }
            document.getElementById('sFechaNacimiento').value = d.fechaNacimiento || '';
            document.getElementById('sSexo').value = d.sexo || 'M';
            document.getElementById('sCorreo').value = d.correo || '';
            document.getElementById('sTelefono').value = d.telefono || '';
            document.getElementById('sTipoPlan').value = d.tipoPlan || '001';
            document.getElementById('sDepartamento').value = d.departamento || '';
            document.getElementById('sCiudad').value = d.ciudad || '';
            document.getElementById('sCalle').value = d.calle || '';

            var inputs = document.querySelectorAll('#formSuscripcion input, #formSuscripcion select');
            for (var i = 0; i < inputs.length; i++) {
                if (inputs[i].id === 'sCódigo') continue;
                if (soloLectura) {
                    inputs[i].setAttribute('readonly', '');
                    inputs[i].setAttribute('disabled', '');
                } else {
                    inputs[i].removeAttribute('readonly');
                    inputs[i].removeAttribute('disabled');
                }
            }

            document.getElementById('btnGuardarSuscripcion').style.display = soloLectura ? 'none' : 'block';
            document.getElementById('modalSuscripcionTitulo').textContent = soloLectura ? 'VISUALIZAR CLIENTE' : 'EDITAR CLIENTE';
            document.getElementById('modalSuscripcion').classList.add('show');
        }

        function abrirModalVisualizar(btn) {
            var tr = btn.closest('tr');
            poblarModalSuscripcion(tr, true);
        }

        function abrirModalEditar(btn) {
            var tr = btn.closest('tr');
            poblarModalSuscripcion(tr, false);
        }

        function calcularEdadModal() {
            var fechaInput = document.getElementById('sFechaNacimiento');
            var edadInput = document.getElementById('sEdad');
            if (fechaInput.value) {
                var parts = fechaInput.value.split('-');
                var nac = new Date(parts[0], parts[1] - 1, parts[2]);
                var hoy = new Date();
                var calcEdad = hoy.getFullYear() - nac.getFullYear();
                var m = hoy.getMonth() - nac.getMonth();
                if (m < 0 || (m === 0 && hoy.getDate() < nac.getDate())) calcEdad--;
                edadInput.value = calcEdad;
            } else {
                edadInput.value = '';
            }
        }

        function cerrarModalSuscripcion() {
            document.getElementById('modalSuscripcion').classList.remove('show');
        }

        function abrirModalEliminar(btn) {
            var tr = btn.closest('tr');
            var d = tr.dataset;
            document.getElementById('elimCodigo').value = d.codigo;
            document.getElementById('elimNombreCliente').textContent = d.nombre + ' ' + (d.apellidoPaterno || '');
            document.getElementById('modalEliminar').classList.add('show');
        }

        function cerrarModalEliminar() {
            document.getElementById('modalEliminar').classList.remove('show');
        }
    </script>


</body>

</html>