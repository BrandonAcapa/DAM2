const datos = require('./datos.json');
const { dialog } = require('@electron/remote');

let editando = false;

const eventosSpanEditar = (i) => {
    document.getElementById(`spanEditar${i}`).addEventListener('click', () => {
        console.log(i);
        if (editando == false) {
            //cambiar los tr${i} a editables
            document.getElementById(`tr${i}`).innerHTML = `
            <td><input type="text" id="alimento" value="${datos[i].Alimento}" size="10"></td>
            <td><input type="text" id="calorias" value="${datos[i].Calorias}" size="5"></td>
            <td><input type="text" id="grasas" value="${datos[i].Grasas}" size="5"></td>
            <td><input type="text" name="proteina" id="proteina" value="${datos[i].Proteina}" size="5"></td>
            <td><input type="text" name="carbohidratos" id="carbohidratos" value="${datos[i].Carbohidratos}" size="
            5"></td>
            <td>En Edición</td>
            `;
            editando = true;
        } else {
            dialog.showErrorBox("ERROR", "Sólo se puede editar de uno en uno");
        }
    })
}

//CONSTRUIR DOM DE LA TABLA
let ctabla = document.getElementById("ctabla");
let filas = "";
// recorro los datos
for (let i = 0; i < datos.length; i++) {
    filas += `
    <tr id="tr${i}">
    <td>${datos[i].Alimento}</td>
    <td>${datos[i].Calorias}</td>
    <td>${datos[i].Grasas}</td>
    <td>${datos[i].Proteina}</td>
    <td>${datos[i].Carbohidratos}</td>
    <td><span class="editar" id="spanEditar${i}">Editar</span></td>
    </tr> `;
}
ctabla.innerHTML = filas;

for (let i = 0; i < datos.length; i++){
    eventosSpanEditar(i);
}