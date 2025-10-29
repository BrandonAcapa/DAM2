const fs = require('fs')
let fichero = fs.readFileSync('./clientes.json');

let clientes = new Array()
clientes = JSON.parse(fichero);

const btnFirst = document.getElementById('btnFirst');
const btnLast = document.getElementById('btnLast');
const btnBack = document.getElementById('btnBack');
const btnForward = document.getElementById('btnForward');

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