import fetch from 'node-fetch';
const recurso = "http://127.0.0.1:8080";

fetch(recurso + '/juegos')
    .then(res => res.text())
    .then(body => console.log(body));

fetch(recurso + '/juegos/35')
    .then(res => res.text())
    .then(body => console.log(body));

let nuevo = {
    "id": 1,
    "game": "GTA VI",
    "author": "Rockstar",
    "ages": "1+"
}

fetch(recurso + '/nuevojuego', {
    method: "post",
    body: JSON.stringify(nuevo),
    headers: {'Content-Type': 'application/json'}
})
    .then(res => res.text())
    .then(body => console.log(body));

fetch(recurso + '/red')
    .then(res => res.text())
    .then(body => console.log(body));