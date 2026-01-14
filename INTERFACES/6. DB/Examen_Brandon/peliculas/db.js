const mongoose = require('mongoose');

const peliculaSchema = new mongoose.Schema({
  id: {
    type: Number,
    required: true,
    unique: true
  },
  nombre: {
    type: String,
    required: true
  },
  director: {
    type: String,
    required: true
  },
  clasificacion: {
    type: String,
    required: true
  }
});

const Pelicula = mongoose.model('peliculas', peliculaSchema);

async function conectarDB() {
  try {
    await mongoose.connect('mongodb://localhost:27017/peliculas');
    console.log('Conectado a MongoDB');
  } catch (error) {
    console.log("ERROR conectando a MongoDB: ", error);
    throw error;
  }
}

async function inicializarDB(peliculas) {
  try {
    const resultado = await Pelicula.insertMany(peliculas);
    console.log("Peliculas añadidas: ", resultado);
  }
  catch (error) {
    console.log("ERROR añadiendo peliculas: ", error);
  }
}

module.exports = { Pelicula, inicializarDB, conectarDB };