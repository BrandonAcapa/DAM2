const fs = require('fs');

let fichero = fs.readFileSync('./books.json');

let books = new Array();

books = JSON.parse(fichero);

console.log(books);