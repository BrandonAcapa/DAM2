const {Libro, insertarLibros} = require('./model.js');
const fs = require('fs');

let fichero = fs.readFileSync('libros.json');
let librosArray = JSON.parse(fichero);

// Insertar datos SOLO si la colección está vacía
Libro.countDocuments().then(count => {
    if (count === 0){
        insertarLibros(librosArray);
    }else{
        console.log("Los libros ya están en la base de datos.");
    }
});