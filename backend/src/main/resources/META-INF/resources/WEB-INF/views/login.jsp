<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>GymWork - Acceso</title>
    <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/GymWork_Logo.png">
    <link href="https://fonts.googleapis.com/css2?family=Bebas+Neue&family=Montserrat:wght@400;700&display=swap" rel="stylesheet">
    <style>
        body {
            margin: 0;
            padding: 0;
            font-family: 'Montserrat', sans-serif;
            background-size: cover;
            background-position: center;
            height: 100vh;
            display: flex;
            justify-content: center;
            align-items: center;
        }
        .login-box {
            background: rgba(15, 15, 15, 0.85);
            padding: 50px 40px;
            width: 350px;
            border-radius: 5px;
            text-align: center;
            color: white;
            box-shadow: 0 15px 25px rgba(0,0,0,0.8);
        }
        .logo-gym {
            width: 140px;
            display: block;
            margin: -20px auto 30px auto;
        }
        .login-box h2 {
            font-family: 'Bebas Neue', sans-serif;
            margin-bottom: 40px;
            font-weight: 400;
            font-size: 28px;
            letter-spacing: 1px;
        }
        .input-group {
            position: relative;
            margin-bottom: 30px;
            text-align: left;
        }
        .input-group label {
            display: block;
            font-size: 11px;
            color: #888;
            margin-bottom: 5px;
            text-transform: uppercase;
        }
        .input-group input {
            width: 100%;
            padding: 8px 0;
            background: transparent;
            border: none;
            border-bottom: 1px solid #555;
            color: white;
            font-size: 16px;
            outline: none;
        }
        .show-password {
            display: flex;
            align-items: center;
            gap: 6px;
            margin: -15px 0 20px 0;
            font-size: 12px;
            color: #888;
            cursor: pointer;
        }
        .show-password input[type="checkbox"] {
            accent-color: #fcd535;
            cursor: pointer;
        }
        .show-password label {
            cursor: pointer;
            user-select: none;
        }
        .btn-login {
            width: 100%;
            background: #fcd535;
            color: black;
            border: none;
            padding: 14px;
            font-weight: bold;
            font-size: 14px;
            cursor: pointer;
            border-radius: 2px;
        }
        .btn-login:hover {
            background: #e6c12c;
        }
        .mensaje-error {
            background-color: #8e8f3a;
            color: #000000;
            padding: 12px;
            margin-bottom: 20px;
            border-radius: 5px;
            font-size: 14px;
            text-align: left;
            border: 1px solid #000000;
        }
    </style>
</head>
<body style="background-image: url('${pageContext.request.contextPath}/Fondo_Login.jpg'); background-size: cover; background-position: center;">
    <div class="login-box">
        <h2>Bienvenido a Gymwork</h2>
        <img src="${pageContext.request.contextPath}/GymWork_Logo.png" alt="Logo GymWork" class="logo-gym">
        <c:if test="${not empty error}">
            <div class="mensaje-error">${error}</div>
        </c:if>
        <form action="${pageContext.request.contextPath}/login" method="post">
            <div class="input-group">
                <label for="username">Login</label>
                <input type="text" id="username" name="username" value="${username}" required>
            </div>
            <div class="input-group">
                <label for="password">Password</label>
                <input type="password" id="password" name="password" required>
            </div>
            <div class="show-password">
                <input type="checkbox" id="togglePassword">
                <label for="togglePassword">Ver contraseña</label>
            </div>
            <button type="submit" class="btn-login">LOGIN</button>
        </form>
    </div>
    <script>
        document.getElementById('togglePassword').addEventListener('change', function() {
            const pwd = document.getElementById('password');
            pwd.type = this.checked ? 'text' : 'password';
        });
        <c:if test="${not empty error}">
        document.getElementById('password').focus();
        </c:if>
    </script>
</body>
</html>
