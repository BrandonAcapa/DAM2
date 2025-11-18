const fs = require('fs')
let fichero = fs.readFileSync('./clientes.json');

let clientes = new Array()
clientes = JSON.parse(fichero);

const btnFirst = document.getElementById('btnFirst');
const btnLast = document.getElementById('btnLast');
const btnBack = document.getElementById('btnBack');
const btnForward = document.getElementById('btnForward');
const btnDelete = document.getElementById('btnDelete');
const btnUpdate = document.getElementById('btnUpdate');
const btnInsert = document.getElementById('btnInsert');

let posicion = 0
let insertando = false
document.getElementById('dni').value = clientes[posicion].dni
document.getElementById("name").value = clientes[posicion].nombre
document.getElementById("telephone").value = clientes[posicion].telefono

// Función para validar y actualizar el estado del botón Insert
function actualizarEstadoBoton() {
    if (insertando) {
        const dni = document.getElementById('dni').value.trim();
        const nombre = document.getElementById('name').value.trim();
        const telefono = document.getElementById('telephone').value.trim();
        
        // Si los campos están completos, habilitar botón
        if (dni && nombre && telefono) {
            btnInsert.disabled = false;
            btnInsert.style.opacity = '1';
            btnInsert.style.cursor = 'pointer';
        } else {
            // Si faltan campos, deshabilitar botón
            btnInsert.disabled = true;
            btnInsert.style.opacity = '0.5';
            btnInsert.style.cursor = 'not-allowed';
        }
    }
}

// Listeners para los inputs cuando estamos en modo inserción
document.getElementById('dni').addEventListener('input', actualizarEstadoBoton);
document.getElementById('name').addEventListener('input', actualizarEstadoBoton);
document.getElementById('telephone').addEventListener('input', actualizarEstadoBoton);

btnFirst.addEventListener('click', () => {
    posicion = 0
    document.getElementById('dni').value = clientes[posicion].dni
    document.getElementById("name").value = clientes[posicion].nombre
    document.getElementById("telephone").value = clientes[posicion].telefono
});

btnLast.addEventListener('click', () => {
    posicion = clientes.length -1
    document.getElementById('dni').value = clientes[posicion].dni
    document.getElementById("name").value = clientes[posicion].nombre
    document.getElementById("telephone").value = clientes[posicion].telefono    
});

btnBack.addEventListener('click', () => {
    if (posicion > 0){
        posicion -= 1
        document.getElementById('dni').value = clientes[posicion].dni
        document.getElementById("name").value = clientes[posicion].nombre
        document.getElementById("telephone").value = clientes[posicion].telefono
    }
});

btnForward.addEventListener('click', () => {
    if (posicion < clientes.length -1){
        posicion += 1
        document.getElementById('dni').value = clientes[posicion].dni
        document.getElementById("name").value = clientes[posicion].nombre
        document.getElementById("telephone").value = clientes[posicion].telefono
    }
});

// Eliminar cliente
btnDelete.addEventListener('click', () => {
    if (!Array.isArray(clientes) || clientes.length === 0) {
        window.alert("No hay clientes para eliminar");
        return;
    }

    const confirmar = window.confirm("¿Estás seguro de eliminar este cliente?");
    if (!confirmar) return;

    const anteriorPos = posicion;
    clientes.splice(posicion, 1);

    if (clientes.length === 0) {
        posicion = 0;
        document.getElementById('dni').value = '';
        document.getElementById('name').value = '';
        document.getElementById('telephone').value = '';
    } else {
        if (anteriorPos > 0) {
            posicion = anteriorPos - 1;
        } else {
            posicion = 0;
        }
        document.getElementById('dni').value = clientes[posicion].dni;
        document.getElementById('name').value = clientes[posicion].nombre;
        document.getElementById('telephone').value = clientes[posicion].telefono;
    }

    try {
        fs.writeFileSync('./clientes.json', JSON.stringify(clientes, null, 2));
    } catch (err) {
        console.error('Error al guardar clientes.json:', err);
    }
});

// Actualizar cliente
btnUpdate.addEventListener('click', () => {
    clientes[posicion].dni = document.getElementById('dni').value;
    clientes[posicion].nombre = document.getElementById("name").value;
    clientes[posicion].telefono = document.getElementById("telephone").value;
    try {
        fs.writeFileSync('./clientes.json', JSON.stringify(clientes, null, 2));
    } catch (err) {
        console.error('Error al guardar clientes.json:', err);
    }
})

// Insertar cliente
btnInsert.addEventListener('click', () => {
    if (!insertando) {
        insertando = true;
        
        btnInsert.style.setProperty('background-color', 'red', 'important');
        btnInsert.style.setProperty('background-image', 'none', 'important');
        btnInsert.style.setProperty('border-color', 'red', 'important');
        
        document.getElementById('dni').value = '';
        document.getElementById('name').value = '';
        document.getElementById('telephone').value = '';
        
        actualizarEstadoBoton();
        document.getElementById('dni').focus();
    } else {
        const nuevoCliente = {
            dni: document.getElementById('dni').value,
            nombre: document.getElementById('name').value,
            telefono: document.getElementById('telephone').value
        };
        
        clientes.push(nuevoCliente);
        
        try {
            fs.writeFileSync('./clientes.json', JSON.stringify(clientes, null, 2));
            
            insertando = false;
            btnInsert.disabled = false;
            btnInsert.style.opacity = '1';
            btnInsert.style.cursor = 'pointer';
            btnInsert.style.removeProperty('background-color');
            btnInsert.style.removeProperty('background-image');
            btnInsert.style.removeProperty('border-color');
            
            posicion = clientes.length - 1;
            document.getElementById('dni').value = clientes[posicion].dni;
            document.getElementById('name').value = clientes[posicion].nombre;
            document.getElementById('telephone').value = clientes[posicion].telefono;
        } catch (err) {
            console.error('Error al guardar clientes.json:', err);
        }
    }
});