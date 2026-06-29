<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div id="mensajeSuscripcion" style="text-align: center; margin-bottom: 10px;"></div>

<form:form action="${pageContext.request.contextPath}/suscripciones/guardar" method="POST" modelAttribute="cliente" id="suscripcionForm" style="background: #1a1a1a; padding: 30px 40px; border-radius: 12px; width: 520px; margin: 20px auto; border: 1px solid #333;">

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
            <span class="val-msg" style="color: #ff4444; font-size: 11px; display: none;"></span>
        </div>
        <div>
            <label style="color: #fcd535; font-size: 12px; display: block;">Apellido Paterno:</label>
            <form:input path="apellidoPaterno" style="width: 100%; padding: 8px; background: #2a2a2a; border: 1px solid #444; color: #fff; border-radius: 4px; box-sizing: border-box;"/>
            <span class="val-msg" style="color: #ff4444; font-size: 11px; display: none;"></span>
        </div>
        <div>
            <label style="color: #fcd535; font-size: 12px; display: block;">Apellido Materno:</label>
            <form:input path="apellidoMaterno" style="width: 100%; padding: 8px; background: #2a2a2a; border: 1px solid #444; color: #fff; border-radius: 4px; box-sizing: border-box;"/>
            <span class="val-msg" style="color: #ff4444; font-size: 11px; display: none;"></span>
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
                <form:option value="M">Masculino</form:option>
                <form:option value="F">Femenino</form:option>
            </form:select>
        </div>
        <div>
            <label style="color: #fcd535; font-size: 12px; display: block;">Correo:</label>
            <form:input path="correo" type="email" style="width: 100%; padding: 8px; background: #2a2a2a; border: 1px solid #444; color: #fff; border-radius: 4px; box-sizing: border-box;"/>
        </div>
        <div style="grid-column: span 2;">
            <label style="color: #fcd535; font-size: 12px; display: block;">Telefono:</label>
            <form:input path="telefono" style="width: 100%; padding: 8px; background: #2a2a2a; border: 1px solid #444; color: #fff; border-radius: 4px; box-sizing: border-box;"/>
            <span class="val-msg" style="color: #ff4444; font-size: 11px; display: none;"></span>
        </div>
    </div>

    <h3 style="color: #fcd535; border-bottom: 1px solid #333; padding-bottom: 6px; margin-top: 20px;">Paquete</h3>
    <div style="margin-bottom: 10px;">
        <label style="color: #fcd535; font-size: 12px; display: block;">Tipo de Plan:</label>
            <form:select path="tipoPlan" style="width: 100%; padding: 8px; background: #2a2a2a; border: 1px solid #444; color: #fff; border-radius: 4px; box-sizing: border-box;">
                <form:option value="001">Paquete Fit (001)</form:option>
                <form:option value="010">Paquete Basico (010)</form:option>
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
        <button type="submit" id="btnAceptar" style="background: #00cc66; color: white; border: none; padding: 12px 40px; font-size: 16px; font-weight: bold; border-radius: 6px; cursor: pointer; text-transform: uppercase; letter-spacing: 1px;">Aceptar</button>
    </div>
</form:form>

<style>
.val-msg.error { display: block; }
input.error { border-color: #ff4444 !important; }
input.valid { border-color: #00cc66 !important; }
</style>

<script>
(function() {
    var form = document.getElementById('suscripcionForm');
    if (!form) return;

    var inputs = {
        nombre: form.querySelector('input[name="nombre"]'),
        apellidoPaterno: form.querySelector('input[name="apellidoPaterno"]'),
        apellidoMaterno: form.querySelector('input[name="apellidoMaterno"]'),
        telefono: form.querySelector('input[name="telefono"]')
    };

    var onlyLetters = /^[a-zA-ZáéíóúÁÉÍÓÚñÑ\s]*$/;
    var onlyDigits = /^[0-9]*$/;

    function getMsgEl(input) {
        var parent = input.parentNode;
        return parent.querySelector('.val-msg');
    }

    function showError(input, msg) {
        input.classList.remove('valid');
        input.classList.add('error');
        var msgEl = getMsgEl(input);
        if (msgEl) {
            msgEl.textContent = msg;
            msgEl.className = 'val-msg error';
        }
    }

    function showValid(input) {
        input.classList.remove('error');
        input.classList.add('valid');
        var msgEl = getMsgEl(input);
        if (msgEl) {
            msgEl.textContent = '';
            msgEl.className = 'val-msg';
        }
    }

    function validateNombre(value) {
        value = value.trim();
        if (!value) return 'Requerido';
        if (!/^[a-zA-ZáéíóúÁÉÍÓÚñÑ\s]+$/.test(value)) return 'Solo letras';
        return '';
    }

    function validateApellido(value) {
        value = value.trim();
        if (!value) return 'Requerido';
        if (!/^[a-zA-ZáéíóúÁÉÍÓÚñÑ\s]+$/.test(value)) return 'Solo letras';
        return '';
    }

    function validateTelefono(value) {
        value = value.trim();
        if (!value) return 'Requerido';
        if (!/^[0-9]+$/.test(value)) return 'Solo numeros';
        return '';
    }

    function checkField(name) {
        var input = inputs[name];
        if (!input) return true;
        var err;
        if (name === 'telefono') {
            err = validateTelefono(input.value);
        } else {
            err = validateApellido(input.value);
        }
        if (err) {
            showError(input, err);
            return false;
        } else {
            showValid(input);
            return true;
        }
    }

    inputs.nombre.addEventListener('input', function() {
        this.value = this.value.replace(/[^a-zA-ZáéíóúÁÉÍÓÚñÑ\s]/g, '');
        checkField('nombre');
    });

    inputs.apellidoPaterno.addEventListener('input', function() {
        this.value = this.value.replace(/[^a-zA-ZáéíóúÁÉÍÓÚñÑ\s]/g, '');
        checkField('apellidoPaterno');
    });

    inputs.apellidoMaterno.addEventListener('input', function() {
        this.value = this.value.replace(/[^a-zA-ZáéíóúÁÉÍÓÚñÑ\s]/g, '');
        checkField('apellidoMaterno');
    });

    inputs.telefono.addEventListener('input', function() {
        this.value = this.value.replace(/[^0-9]/g, '');
        checkField('telefono');
    });

    form.addEventListener('submit', function(e) {
        e.preventDefault();

        var ok = checkField('nombre') & checkField('apellidoPaterno') & checkField('apellidoMaterno') & checkField('telefono');
        if (!ok) return;

        var formData = new FormData(form);
        var params = new URLSearchParams(formData);
        var mensajeDiv = document.getElementById('mensajeSuscripcion');
        var btn = document.getElementById('btnAceptar');
        btn.disabled = true;
        btn.textContent = 'Registrando...';

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
                mensajeDiv.innerHTML = '<p style="color: #00cc66; font-weight: bold;">Suscripcion guardada con exito</p>';
                form.reset();
                [inputs.nombre, inputs.apellidoPaterno, inputs.apellidoMaterno, inputs.telefono].forEach(function(inp) {
                    inp.classList.remove('valid', 'error');
                });
            }
        })
        .catch(function(error) {
            mensajeDiv.innerHTML = '<p style="color: #ff3333; font-weight: bold;">Error: ' + error.message + '</p>';
        })
        .finally(function() {
            btn.disabled = false;
            btn.textContent = 'Aceptar';
        });
    });
})();
</script>

