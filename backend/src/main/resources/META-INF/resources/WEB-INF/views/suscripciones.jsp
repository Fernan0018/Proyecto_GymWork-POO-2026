<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div id="mensajeSuscripcion" style="text-align: center; margin-bottom: 10px;"></div>

<form:form action="#" method="POST" modelAttribute="cliente" id="suscripcionForm" style="background: #1a1a1a; padding: 30px 40px; border-radius: 12px; width: 520px; margin: 20px auto; border: 1px solid #333;">

    <h2 style="color: #fcd535; font-family: 'Bebas Neue', sans-serif; text-align: center; letter-spacing: 2px; margin: 0 0 25px 0;">SUSCRIPCIONES</h2>

    <h3 style="color: #fcd535; border-bottom: 1px solid #333; padding-bottom: 6px;">Datos del Cliente</h3>
    <div style="display: grid; grid-template-columns: 1fr 1fr; gap: 12px;">
        <div>
            <label style="color: #fcd535; font-size: 12px; display: block;">Codigo:</label>
            <form:input path="codigo" placeholder="Ej: 20240123E" style="width: 100%; padding: 8px; background: #2a2a2a; border: 1px solid #444; color: #fff; border-radius: 4px; box-sizing: border-box;"/>
        </div>
        <div>
            <label style="color: #fcd535; font-size: 12px; display: block;">Nombre:</label>
            <form:input path="nombre" style="width: 100%; padding: 8px; background: #2a2a2a; border: 1px solid #444; color: #fff; border-radius: 4px; box-sizing: border-box;"/>
        </div>
        <div>
            <label style="color: #fcd535; font-size: 12px; display: block;">Apellido Paterno:</label>
            <form:input path="apellidoPaterno" style="width: 100%; padding: 8px; background: #2a2a2a; border: 1px solid #444; color: #fff; border-radius: 4px; box-sizing: border-box;"/>
        </div>
        <div>
            <label style="color: #fcd535; font-size: 12px; display: block;">Apellido Materno:</label>
            <form:input path="apellidoMaterno" style="width: 100%; padding: 8px; background: #2a2a2a; border: 1px solid #444; color: #fff; border-radius: 4px; box-sizing: border-box;"/>
        </div>
        <div>
            <label style="color: #fcd535; font-size: 12px; display: block;">Edad:</label>
            <form:input path="edad" type="number" style="width: 100%; padding: 8px; background: #2a2a2a; border: 1px solid #444; color: #fff; border-radius: 4px; box-sizing: border-box;"/>
        </div>
        <div>
            <label style="color: #fcd535; font-size: 12px; display: block;">Fecha Nacimiento:</label>
            <form:input path="fechaNacimiento" type="date" style="width: 100%; padding: 8px; background: #2a2a2a; border: 1px solid #444; color: #fff; border-radius: 4px; box-sizing: border-box;"/>
        </div>
        <div>
            <label style="color: #fcd535; font-size: 12px; display: block;">Sexo:</label>
            <form:select path="sexo" style="width: 100%; padding: 8px; background: #2a2a2a; border: 1px solid #444; color: #fff; border-radius: 4px; box-sizing: border-box;">
                <form:option value="Masculino">Masculino</form:option>
                <form:option value="Femenino">Femenino</form:option>
                <form:option value="No especificado">No especificado</form:option>
            </form:select>
        </div>
        <div>
            <label style="color: #fcd535; font-size: 12px; display: block;">Correo:</label>
            <form:input path="correo" type="email" style="width: 100%; padding: 8px; background: #2a2a2a; border: 1px solid #444; color: #fff; border-radius: 4px; box-sizing: border-box;"/>
        </div>
        <div style="grid-column: span 2;">
            <label style="color: #fcd535; font-size: 12px; display: block;">Telefono:</label>
            <form:input path="telefono" style="width: 100%; padding: 8px; background: #2a2a2a; border: 1px solid #444; color: #fff; border-radius: 4px; box-sizing: border-box;"/>
        </div>
    </div>

    <h3 style="color: #fcd535; border-bottom: 1px solid #333; padding-bottom: 6px; margin-top: 20px;">Paquete</h3>
    <div style="margin-bottom: 10px;">
        <label style="color: #fcd535; font-size: 12px; display: block;">Tipo de Plan:</label>
        <form:select path="tipoPlan" style="width: 100%; padding: 8px; background: #2a2a2a; border: 1px solid #444; color: #fff; border-radius: 4px; box-sizing: border-box;">
            <form:option value="010">Paquete Basico (010)</form:option>
            <form:option value="001">Paquete Fit (001)</form:option>
            <form:option value="100">Plan Premium (100)</form:option>
        </form:select>
    </div>

    <h3 style="color: #fcd535; border-bottom: 1px solid #333; padding-bottom: 6px; margin-top: 20px;">Domicilio</h3>
    <div style="margin-bottom: 10px;">
        <div>
            <label style="color: #fcd535; font-size: 12px; display: block;">Departamento:</label>
            <form:input path="departamento" style="width: 100%; padding: 8px; background: #2a2a2a; border: 1px solid #444; color: #fff; border-radius: 4px; box-sizing: border-box;"/>
        </div>
        <div>
            <label style="color: #fcd535; font-size: 12px; display: block;">Ciudad:</label>
            <form:input path="ciudad" style="width: 100%; padding: 8px; background: #2a2a2a; border: 1px solid #444; color: #fff; border-radius: 4px; box-sizing: border-box;"/>
        </div>
        <div>
            <label style="color: #fcd535; font-size: 12px; display: block;">Calle:</label>
            <form:input path="calle" style="width: 100%; padding: 8px; background: #2a2a2a; border: 1px solid #444; color: #fff; border-radius: 4px; box-sizing: border-box;"/>
        </div>
    </div>

    <div style="text-align: center; margin-top: 25px;">
        <button type="submit" style="background: #00cc66; color: white; border: none; padding: 12px 40px; font-size: 16px; font-weight: bold; border-radius: 6px; cursor: pointer; text-transform: uppercase; letter-spacing: 1px;">Aceptar</button>
    </div>
</form:form>

<script>
document.getElementById('suscripcionForm').addEventListener('submit', function(e) {
    e.preventDefault();

    var formData = new FormData(this);
    var params = new URLSearchParams(formData);
    var mensajeDiv = document.getElementById('mensajeSuscripcion');

    fetch('${pageContext.request.contextPath}/suscripciones/guardar', {
        method: 'POST',
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
            document.getElementById('suscripcionForm').reset();
        }
    })
    .catch(function(error) {
        mensajeDiv.innerHTML = '<p style="color: #ff3333; font-weight: bold;">Error: ' + error.message + '</p>';
    });
});
</script>
