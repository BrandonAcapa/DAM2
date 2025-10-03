// function obtenerDatos() {
//   return new Promise((resolve, reject) => {
//     setTimeout(() => {
//       const exito = true;
//       if (exito) {
//         resolve("Datos recibidos correctamente");
//       } else {
//         reject("Error al recibir los datos");
//       }
//     }, 2000);
//   });
// }

// obtenerDatos()
//   .then(resultado => console.log(resultado))
//   .catch(error => console.error(error));

const ruta = 'C:/\Program Files';
const fs = require('fs');
fs.readdirSync(ruta).forEach(fichero => {console.log(fichero);});