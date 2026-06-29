<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>GymWork - ${modo} Administrador</title>
    <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/GymWork_Logo.png">
    <link href="https://fonts.googleapis.com/css2?family=Bebas+Neue&family=Montserrat:wght@400;700&display=swap" rel="stylesheet">
    <style>
        * { box-sizing: border-box; }
        body {
            margin: 0; padding: 0;
            font-family: 'Montserrat', sans-serif;
            background-color: #0f0f0f;
            color: white;
            display: flex; justify-content: center; align-items: center;
            min-height: 100vh;
        }
        .form-card {
            background: #1e1e1e;
            border: 1px solid #333;
            border-radius: 12px;
            padding: 40px;
            width: 600px;
            box-shadow: 0 8px 30px rgba(0,0,0,0.6);
        }
        .form-title {
            font-family: 'Bebas Neue', sans-serif;
            font-size: 32px;
            color: #fcd535;
            margin: 0 0 25px 0;
            letter-spacing: 2px;
            text-align: center;
            border-bottom: 1px solid #333;
            padding-bottom: 15px;
        }
        .form-group {
            margin-bottom: 18px;
        }
        .form-group label {
            color: #fcd535;
            font-size: 12px;
            text-transform: uppercase;
            letter-spacing: 1px;
            display: block;
            margin-bottom: 6px;
        }
        .form-group input,
        .form-group select {
            width: 100%;
            padding: 10px 12px;
            background: #2a2a2a;
            border: 1px solid #444;
            color: #fff;
            border-radius: 6px;
            font-family: 'Montserrat', sans-serif;
            font-size: 14px;
            transition: border-color 0.2s;
        }
        .form-group select {
            cursor: pointer;
            appearance: none;
            -webkit-appearance: none;
        }
        .form-group select option {
            background: #2a2a2a;
            color: #fff;
        }
        .form-group input:focus,
        .form-group select:focus {
            outline: none;
            border-color: #fcd535;
        }
        .form-row {
            display: grid;
            grid-template-columns: 1fr 1fr;
            gap: 16px;
        }
        .section-title {
            font-family: 'Bebas Neue', sans-serif;
            font-size: 20px;
            color: #fcd535;
            margin: 20px 0 15px 0;
            letter-spacing: 1px;
            border-bottom: 1px solid #333;
            padding-bottom: 8px;
        }
        .form-actions {
            display: flex;
            gap: 12px;
            margin-top: 25px;
        }
        .btn-primary {
            flex: 1;
            background: #fcd535;
            border: none;
            color: #111;
            font-family: 'Montserrat', sans-serif;
            font-weight: 700;
            font-size: 14px;
            padding: 12px;
            border-radius: 6px;
            cursor: pointer;
            transition: background 0.2s;
            text-transform: uppercase;
            letter-spacing: 1px;
        }
        .btn-primary:hover { background: #e6c12e; }
        .btn-cancel {
            flex: 1;
            background: transparent;
            border: 1px solid #555;
            color: #ccc;
            font-family: 'Montserrat', sans-serif;
            font-weight: 700;
            font-size: 14px;
            padding: 12px;
            border-radius: 6px;
            cursor: pointer;
            transition: all 0.2s;
            text-transform: uppercase;
            letter-spacing: 1px;
            text-align: center;
            text-decoration: none;
        }
        .btn-cancel:hover { border-color: #fcd535; color: #fcd535; }
    </style>
</head>
<body>
    <div class="form-card">
        <h1 class="form-title">${modo} ADMINISTRADOR</h1>
        <form:form action="${pageContext.request.contextPath}/administracion/${accion}" method="POST" modelAttribute="usuario">
            <c:if test="${modo == 'Modificar'}">
                <form:hidden path="idUsuario"/>
            </c:if>

            <div class="section-title">DATOS DE USUARIO</div>
            <div class="form-group">
                <label for="username">Usuario</label>
                <form:input path="username" id="username" required="required"/>
            </div>
            <div class="form-group">
                <label for="password">Contraseña</label>
                <form:password path="password" id="password" required="required" showPassword="true"/>
            </div>

            <c:if test="${modo == 'Registrar'}">
                <div class="section-title">DATOS DEL ADMINISTRADOR</div>
                <div class="form-group">
                    <label for="dni">DNI</label>
                    <input type="text" name="dni" id="dni" required="required" maxlength="8"/>
                </div>
                <div class="form-row">
                    <div class="form-group">
                        <label for="nombre">Nombres</label>
                        <input type="text" name="nombre" id="nombre" required="required"/>
                    </div>
                    <div class="form-group">
                        <label for="apellidos">Apellidos</label>
                        <input type="text" name="apellidos" id="apellidos" required="required"/>
                    </div>
                </div>
                <div class="form-row">
                    <div class="form-group">
                        <label for="fechaNacimiento">Fecha de Nacimiento</label>
                        <input type="date" name="fechaNacimiento" id="fechaNacimiento" required="required"/>
                    </div>
                    <div class="form-group">
                        <label for="telefono">Teléfono</label>
                        <input type="text" name="telefono" id="telefono" required="required"/>
                    </div>
                </div>
                <div class="form-group">
                    <label for="correo">Correo Electrónico</label>
                    <input type="email" name="correo" id="correo" required="required"/>
                </div>
                <div class="form-group">
                    <label for="idSede">Sede</label>
                    <select name="idSede" id="idSede" required="required">
                        <option value="">Seleccione una sede...</option>
                        <c:forEach var="sede" items="${sedes}">
                            <option value="${sede.idSede}">${sede.nombreSede}</option>
                        </c:forEach>
                    </select>
                </div>
            </c:if>

            <div class="form-actions">
                <button type="submit" class="btn-primary">${modo}</button>
                <a href="${pageContext.request.contextPath}/administracion" class="btn-cancel">Cancelar</a>
            </div>
        </form:form>
    </div>
</body>
</html>