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

let posicion = 0
document.getElementById('dni').value = clientes[posicion].dni
document.getElementById("name").value = clientes[posicion].nombre
document.getElementById("telephone").value = clientes[posicion].telefono

// fs.writeFileSync('./clientes.json', JSON.stringify(clientes));

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

btnDelete.addEventListener('click', () => {
    // Comprobar que el array existe y no está vacío
    if (!Array.isArray(clientes) || clientes.length === 0) {
        window.alert("No hay clientes para eliminar");
        return;
    }

    // Usaremos confirm para confirmar la eliminación
    const confirmar = window.confirm("¿Estás seguro de eliminar este cliente?");
    if (!confirmar) return;

    // Borrar el cliente  la posición actual
    clientes.splice(posicion, 1);

    // Ajustar posición y actualizar los campos del formulario
    document.getElementById('dni').value = clientes[posicion - 1].dni;
    document.getElementById("name").value = clientes[posicion - 1].nombre;
    document.getElementById("telephone").value = clientes[posicion - 1].telefono;

    // Guardar cambios en el JSON
    try {
        fs.writeFileSync('./clientes.json', JSON.stringify(clientes, null, 2));
    } catch (err) {
        console.error('Error al guardar clientes.json:', err);
    }
});

btnUpdate.addEventListener('click', () => {
    // Comprobar que hay clientes cargados
    if (!Array.isArray(clientes) || clientes.length === 0) {
        window.alert('No hay clientes para actualizar');
        return;
    }

    // Leer valores desde el formulario
    const nuevoDni = document.getElementById('dni').value.trim();
    const nuevoNombre = document.getElementById('name').value.trim();
    const nuevoTelefono = document.getElementById('telephone').value.trim();

    // Validaciones básicas
    if (!nuevoDni) {
        window.alert('El campo DNI no puede estar vacío');
        return;
    }
    if (!nuevoNombre) {
        window.alert('El campo Nombre no puede estar vacío');
        return;
    }

    // Comprobar duplicado de DNI en otro registro
    const duplicado = clientes.some((c, i) => i !== posicion && String(c.dni) === nuevoDni);
    if (duplicado) {
        window.alert('Ya existe otro cliente con ese DNI');
        return;
    }

    // Actualizar el objeto en la posición actual
    clientes[posicion].dni = nuevoDni;
    clientes[posicion].nombre = nuevoNombre;
    clientes[posicion].telefono = nuevoTelefono;

    // Persistir los cambios
    try {
        fs.writeFileSync('./clientes.json', JSON.stringify(clientes, null, 2));
        window.alert('Cliente actualizado correctamente');
    } catch (err) {
        console.error('Error al guardar clientes.json:', err);
        window.alert('Error al guardar los cambios. Revisa la consola.');
    }

});