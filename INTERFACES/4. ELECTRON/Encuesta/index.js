// let btnComprobar = document.getElementById("comprobar");
// let nPreguntas = 4;
// //EVENTOS
// btnComprobar.addEventListener('click', () => {
//     let aciertos = 0;
//     let fallos = 0;
//     for (let i = 0; i < nPreguntas; i++) {
//         let a = document.getElementById("rA" + i);
//         let b = document.getElementById("rB" + i);
//         let c = document.getElementById("rC" + i);
//         if (a.checked) {
//             aciertos++;
//         }
//         if (b.checked) {
//             fallos++;
//         }
//         if (c.checked) {
//             fallos++;
//         }
//     }
//     let muestra = document.getElementById("muestra");
//     muestra.innerHTML = 'aciertos: ' + aciertos + ' ' + 'fallos :' + fallos;
//     console.log('aciertos: ' + aciertos);
//     console.log('fallos :' + fallos);
// });

const { dialog } = require('@electron/remote')
//Comprueba que la respuesta correcta sea la A
document.getElementById('comprobar').addEventListener('click', () => {
    let aciertos = 0;
    let fallos = 0;
    let nc = 0;
    //Hacemos un array con todos los objetos inputs que nos devuelve el getElements
    let inputs = Array.from(document.getElementsByTagName('input'));
    //Sabemos que sólo hay input type radio, pero si no lo supiéramos y hubiese otro tipo de inputs:
    inputs = inputs.filter(inp => inp.type == 'radio');
    for (let i = 0; i < inputs.length; i++) {
        const input = inputs[i];
        if (input.value == 'a' && input.checked)//Aciertos son todos los radios de la respuesta a marcadas
            aciertos++;
        else if (input.value != 'a' && input.checked)//fallos son todos los radios que no tienen marcada la a
            fallos++;
    }
    dialog.showMessageBox({
        type: 'info',
        title: 'Resultado',
        message: `Número de aciertos: ${aciertos}
        Número de fallos: ${fallos}`
    })
})
