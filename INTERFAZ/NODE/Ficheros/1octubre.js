const fs = require('fs');
// const http=require ('http');

// let server = http.createServer((req, res) => {
//     res.writeHead(200, {'Content-Type': 'text/plain'});
//     let miObj={
//         nombre: 'Juan',
//         trabajo: 'Programador',
//         edad: 30
//     }
//     res.end(miobj);
// })

// server.listen(3000, '127.0.0.1');

// console.log('Escuchando el puerto 3000');

var leeme = fs.readFileSync('readme.txt', 'utf8');
console.log(leeme);
fs.writeFileSync('escribeme.txt', leeme);