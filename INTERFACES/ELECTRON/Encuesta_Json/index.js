const fs = require('fs');
let lista = document.getElementById("lista");
let btnComprobar = document.getElementById("comprobar");

const preguntas = require('./test.json');

fichero = fs.readFileSync('./test.json');

let contenidoLista = "";

for (let i = 0; i < preguntas.length; i++) {
    contenidoLista += `<li class="list-group-item"><img class="img-circle media-object pull-left" src="./images/${i + 1}.png" width="32" height="32">
    <div class="media-body"><strong>${preguntas[i].pregunta}</strong>
    <div><input type="radio" id="rA${i + 1}" class="a" value="${preguntas[i].rA}"> ${preguntas[i].rA}</div>
    <div><input type="radio" id="rB${i + 1}" class="b" value="${preguntas[i].rB}"> ${preguntas[i].rB}</div>
    <div><input type="radio" id="rC${i + 1}" class="c" value="${preguntas[i].rC}"> ${preguntas[i].rC}</div>`;
}

lista.innerHTML = contenidoLista;

btnComprobar.addEventListener('click', () => {
    let aciertos = 0;
    let fallos = 0;

    for (let i = 0; i < preguntas.length; i++) {
        let a = document.getElementById(`rA${i + 1}`);
        let b = document.getElementById(`rB${i + 1}`);
        let c = document.getElementById(`rC${i + 1}`);

        if (a.checked && a.class === preguntas[i].correcta){
            aciertos++;
        }
        else if (b.checked && b.class === preguntas[i].correcta){
            aciertos++;
        }
        else if (c.checked && c.class === preguntas[i].correcta){
            aciertos++;
        }
        else{
            fallos++;
        }
    }

    // alert(`Aciertos: ${aciertos} \n Fallos: ${fallos}`);
    let muestra = document.getElementById("muestra");
    muestra.innerHTML = 'aciertos: ' + aciertos + ' fallos: ' + fallos;
});