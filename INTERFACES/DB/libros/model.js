const mongoose = require('mongoose');

mongoose.Promise = global.Promise;
mongoose.connect('mongodb://localhost:27017/libros')

let librosSchema = new mongoose.Schema({
    title: {
        type: String,
        required: true,
        minlength: 1,
        trim: true
    },
    author: {
        type: String,
        required: true,
        minlength: 1,
        trim: true
    },
    img: {
        type: String,
        required: true,
        minlength: 1,
        unique: true,
        trim: true
    }
});

const Libro = mongoose.model('libros', librosSchema);

async function insertarLibros(libros){
    try{
        const resultado = await Libro.insertMany(libros);
        console.log("Libros añadidos:", resultado);
    }
    catch(error){
        console.log("ERROR añadiendo libros:", error);
    }   
}

module.exports = Libro;
insertarLibros(librosArray);