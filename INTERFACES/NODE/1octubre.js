const fs = require('fs');
const http=require ('http');

let server = http.createServer((req, res) => {
    consolet.log(runInNewContext.url);
    res.writeHead(200, {'Content-Type': 'text/plain'});
    let miObj={
        nombre: 'Juan',
        trabajo: 'Programador',
        edad: 30
    }
    let myStream=fs.createReadStream('bsb.txt', 'utf8');
    res.end(miobj);
})