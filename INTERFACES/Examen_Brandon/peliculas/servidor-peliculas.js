const express = require('express');
const { Pelicula, inicializarBD } = require('../servidor/db');

const app = express();
app.use(express.json());

// Inicializar base de datos
inicializarBD();

// Ruta GET /peliculas - obtener todas las películas
app.get('/peliculas', async (req, res) => {
  try {
    const peliculas = await Pelicula.find();
    res.json(peliculas);
  } catch (error) {
    res.status(500).json({ error: error.message });
  }
});

// Ruta GET /peliculas/:id - obtener película por id
app.get('/peliculas/:id', async (req, res) => {
  try {
    const pelicula = await Pelicula.findOne({ id: parseInt(req.params.id) });
    if (!pelicula) {
      return res.status(404).json({ error: 'Película no encontrada' });
    }
    res.json(pelicula);
  } catch (error) {
    res.status(500).json({ error: error.message });
  }
});

// Ruta POST /peliculas - crear nueva película
app.post('/peliculas', async (req, res) => {
  try {
    const nuevaPelicula = new Pelicula({
      id: req.body.id,
      nombre: req.body.nombre,
      director: req.body.director,
      clasificacion: req.body.clasificacion
    });
    
    const resultado = await nuevaPelicula.save();
    res.status(201).json(resultado);
  } catch (error) {
    res.status(400).json({ error: error.message });
  }
});

// Ruta PUT /peliculas/:id - actualizar película
app.put('/peliculas/:id', async (req, res) => {
  try {
    const pelicula = await Pelicula.findOneAndUpdate(
      { id: parseInt(req.params.id) },
      {
        nombre: req.body.nombre,
        director: req.body.director,
        clasificacion: req.body.clasificacion
      },
      { new: true }
    );
    
    if (!pelicula) {
      return res.status(404).json({ error: 'Película no encontrada' });
    }
    
    res.json(pelicula);
  } catch (error) {
    res.status(400).json({ error: error.message });
  }
});

// Ruta DELETE /peliculas/:id - eliminar película
app.delete('/peliculas/:id', async (req, res) => {
  try {
    const pelicula = await Pelicula.findOneAndDelete({ id: parseInt(req.params.id) });
    
    if (!pelicula) {
      return res.status(404).json({ error: 'Película no encontrada' });
    }
    
    res.json({ mensaje: 'Película eliminada', pelicula });
  } catch (error) {
    res.status(500).json({ error: error.message });
  }
});

const PORT = 3001;
app.listen(PORT, () => {
  console.log(`Servidor de películas escuchando en puerto ${PORT}`);
});
