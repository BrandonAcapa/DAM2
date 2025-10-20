const personas = require("./personas.js");

let datos = [
{nombre: "Nacho", telefono: "966112233", edad: 40},
{nombre: "Ana", telefono: "911223344", edad: 35},
{nombre: "Mario", telefono: "611998877", edad: 15},
{nombre: "Laura", telefono: "633663366", edad: 17}
];


console.log(personas.nuevaPersona(datos, {nombre: "Juan", telefono:"965661564", edad: 60}));
console.log(personas.nuevaPersona(datos, {nombre: "Rodolfo", telefono:"910011001", edad: 20}));
console.log(personas.borrarPersona(datos, "910011001"));

console.log(personas);