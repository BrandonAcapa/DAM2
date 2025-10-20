let personas = [
    {
        nombre: "Nacho",
        telefono: "966112233",
        edad: 40
    },
    {
        nombre: "Ana",
        telefono: "911223344",
        edad: 35
    },
    {
        nombre: "Mario",
        telefono: "611998877",
        edad: 15
    },
    {
        nombre: "Laura",
        telefono: "633663366",
        edad: 17
    }
];

const div = document.getElementById('tabla');
const button = document.getElementById('button');

let tabla = `<table style="border: 1px solid black"><tr><th>Nombre</th><th>Teléfono</th><th>Edad</th></tr>`;

for (i in personas){
    tabla += `<tr><td>${personas[i].nombre}</td><td>${personas[i].telefono}</td><td>${personas[i].edad}</td></tr>`;
}

button.addEventListener('click', () => {
    div.innerHTML = tabla;
});