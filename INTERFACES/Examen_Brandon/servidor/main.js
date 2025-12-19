const express = require('express');
const bodyParser = require('body-parser');
const fs = require('fs');
const os = require('node:os');

let fichero = fs.readFileSync('juegos.json', 'utf-8');
let juegos = JSON.parse(fichero);

const app = express();
app.use(bodyParser.json());

app.get('/juegos', (req, res) => {
    res.json(juegos);
});

app.get('/juegos/:id', (req, res) => {
    let juego = juegos.find(j => j.id === parseInt(req.params.id));

    if (!juego) {
        return res.status(404).json({ error: 'Juego no encontrado' });
    }

    res.json(juego);
});

app.post('/nuevojuego', (req, res) => {
    let nuevoJuego = req.body;
    juegos.push(nuevoJuego);
    fs.writeFileSync('juegos.json', JSON.stringify(juegos, null, 2));
    res.json({ ok: true });
});

app.get('/red', (req, res) => {
    const interfaces = os.networkInterfaces();
    res.json(interfaces);
});

app.listen(8080, () => {
    console.log('Servidor iniciado en http://localhost:8080');
});
