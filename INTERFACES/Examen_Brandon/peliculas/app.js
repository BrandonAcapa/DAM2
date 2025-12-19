document.addEventListener('DOMContentLoaded', () => {
    const path = require('path');
    const fs = require('fs');
    let Pelicula;

    console.log("Aplicación de gestión de películas iniciada");

    // INICIALIZAR BASE DE DATOS, importando el modulo db.js
    try {
        const nodeRequire = typeof require === 'function' ? require : null;
        if (nodeRequire) {
            const { Pelicula: PeliculaModel, inicializarDB, conectarDB } = nodeRequire('./db.js');
            Pelicula = PeliculaModel;

            try {
                let fichero = fs.readFileSync('peliculas.json', 'utf-8');
                let peliculas = JSON.parse(fichero);

                conectarDB().then(() => {
                    return Pelicula.countDocuments();
                }).then(count => {
                    if (count === 0) {
                        return inicializarDB(peliculas);
                    }
                    else {
                        console.log("La base de datos ya tiene peliculas");
                    }
                }).then(() => {
                    // Cargar películas después de inicializar
                    cargarPeliculas();
                }).catch(error => {
                    console.log('Error con la base de datos: ', error);
                });
            } catch (error) {
                console.log('Error leyendo peliculas.json ', error);
            }
        }
    }
    catch (e) {
        console.log("Error inicializando: ", e);
    }

    // CARGAR PELICULAS
    async function cargarPeliculas() {
        try {
            const peliculas = await Pelicula.find({});

            const peliculasPorGenero = {};
            peliculas.forEach(pelicula => {
                if (!peliculasPorGenero[pelicula.clasificacion]) {
                    peliculasPorGenero[pelicula.clasificacion] = [];
                }
                peliculasPorGenero[pelicula.clasificacion].push(pelicula);
            });

            const container = document.getElementById('peliculas');
            container.innerHTML = '';

            for (const [genero, peliculas] of Object.entries(peliculasPorGenero)) {
                const generoDiv = document.createElement('div');
                generoDiv.className = 'genero';
                generoDiv.innerHTML = `<h3>${genero}</h3>`;

                peliculas.forEach(pelicula => {
                    const peliculaDiv = document.createElement('div');
                    peliculaDiv.className = 'pelicula';
                    peliculaDiv.innerHTML = `<b>(ID: ${pelicula.id})</b>
                    ${pelicula.nombre} 
                    - "${pelicula.director}"`;
                    generoDiv.appendChild(peliculaDiv);
                });

                container.appendChild(generoDiv);
            }

            console.log('Películas cargadas:', peliculas.length);
        } catch (error) {
            console.error('Error cargando películas:', error);
        }
    }


    // INSERTAR PELÍCULAS
    async function insertarPelicula() {
        const id = document.getElementById('insertarId').value;
        const nombre = document.getElementById('insertarNombre').value;
        const director = document.getElementById('insertarDirector').value;
        const clasificacion = document.getElementById('insertarClasificacion').value;

        if (!id || !nombre || !director || !clasificacion) {
            console.log('Por favor, rellena todos los campos');
            return;
        }

        try {
            const nuevaPelicula = new Pelicula({
                id: parseInt(id),
                nombre,
                director,
                clasificacion
            });

            await nuevaPelicula.save();
            console.log('Película insertada:', nuevaPelicula);

            document.getElementById('insertarId').value = '';
            document.getElementById('insertarNombre').value = '';
            document.getElementById('insertarDirector').value = '';
            document.getElementById('insertarClasificacion').value = '';

            cargarPeliculas();
        } catch (error) {
            console.error('Error insertando película:', error);
        }
    }

    // MODIFICAR PELÍCULA
    async function modificarPelicula() {
        const id = document.getElementById('modificarId').value;
        const nombre = document.getElementById('modificarNombre').value;
        const director = document.getElementById('modificarDirector').value;
        const clasificacion = document.getElementById('modificarClasificacion').value;

        if (!id) {
            console.log('Introduce el ID de la película a modificar');
            return;
        }

        if (!nombre && !director && !clasificacion) {
            console.log('Introduce al menos un campo para modificar');
            return;
        }

        try {
            const updateData = {};
            if (nombre) updateData.nombre = nombre;
            if (director) updateData.director = director;
            if (clasificacion) updateData.clasificacion = clasificacion;

            const resultado = await Pelicula.updateOne(
                { id: parseInt(id) },
                { $set: updateData }
            );

            if (resultado.modifiedCount > 0) {
                console.log('Película modificada, ID:', id);

                document.getElementById('modificarId').value = '';
                document.getElementById('modificarNombre').value = '';
                document.getElementById('modificarDirector').value = '';
                document.getElementById('modificarClasificacion').value = '';

                cargarPeliculas();
            } else {
                console.log('No se encontró ninguna película con ese ID o no hubo cambios');
            }
        } catch (error) {
            console.error('Error modificando película:', error);
        }
    }

    // BORRAR PELÍCULA
    async function borrarPelicula() {
        const id = document.getElementById('borrarPelicula').value;

        if (!id) {
            console.log('Introduce el ID de la película a borrar');
            return;
        }

        try {
            const resultado = await Pelicula.deleteOne({ id: parseInt(id) });

            if (resultado.deletedCount > 0) {
                console.log('Película borrada, ID:', id);
                console.log('Película borrada correctamente');
                document.getElementById('borrarPelicula').value = '';
                cargarPeliculas();
            } else {
                console.log('No se encontró ninguna película con ese ID');
            }
        } catch (error) {
            console.error('Error borrando película:', error);
            console.log('Error borrando película: ' + error.message);
        }
    }

    document.getElementById('insertar').addEventListener('click', insertarPelicula);
    document.getElementById('modificar').addEventListener('click', modificarPelicula);
    document.getElementById('borrar').addEventListener('click', borrarPelicula);
});