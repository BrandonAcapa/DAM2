const express = require('express');
const bodyParser = require('body-parser');
const fs = require('fs');
const moment = require('moment/moment');
const { format } = require('path');
const os = require('node:os');

let app = express();
app.use(bodyParser.json());

//definimos una ruta: http://localhost:8080/bienvenida
//y una respuesta a esa ruta: mensaje "Hola, bienvenido/a"
app.get('/bienvenida', (req, res) => {
    res.send('Hola, bienvenido/a');
});

let fichero = fs.readFileSync('clientes.json', 'utf-8');
let clientes = JSON.parse(fichero);

app.get('/clientes', (req, res) => {
    res.json(clientes);
})

app.get('/clientes/:dni', (req, res) => {
    let cliente = clientes.filter(c => c.dni === req.params.dni);
    if (cliente.length > 0) {
        res.json(cliente[0]);
    } else {
        res.status(404).json({ error: 'No existe el cliente con ese DNI' });
    }
})

// Servicio para insertar clientes
app.post('/inserta', (req, res) => {
    try {
        //obtener el cliente dado con la petición post
        let nuevoCliente = req.body;
        //leer clientes del archivo
        let fichero = fs.readFileSync('./clientes.json');
        let clientes = JSON.parse(fichero);
        //añadir el nuevo cliente:
        clientes.push(nuevoCliente);
        //guardar el fichero completo:
        fs.writeFileSync('./clientes.json', JSON.stringify(clientes, null, 2));
        res.json({ ok: true });
    }
    catch (err) {
        res.status(500).json({ ok: false, error: err.message });
    }
});

app.get('/fecha', (req, res) => {
    res.send(moment().format("MMM Do YY"));
});

app.get('/usuario', (req, res) => {
    res.send(os.userInfo().username);
})

app.listen(8080, () => console.log('Servidor iniciado en http://localhost:8080'));