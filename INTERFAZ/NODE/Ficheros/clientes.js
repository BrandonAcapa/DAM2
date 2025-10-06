const fs = require('fs');

let fichero = fs.readFileSync('./clientes.json');

let clientes = new Array();

clientes = JSON.parse(fichero);

console.log(clientes);

// Escribir en el fichero json

fs.writeFileSync('./clientes.json', JSON.stringify(clientes));