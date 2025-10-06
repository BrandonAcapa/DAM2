const div = document.getElementById('app');
const div2 = document.getElementById('buttonDiv');
const button = document.getElementById('button');

div.innerHTML = "<h1>Hello Array!</h1>";

let nombre = ["Nombre 1", "Nombre 2", "Nombre 3", "Nombre 4"];

let x = "";

button.addEventListener('click', () => {
    for (i in nombre){
        x += nombre[i] + "<br>";
    }
    div2.innerHTML = x;
});