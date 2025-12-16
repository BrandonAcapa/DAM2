document.addEventListener('DOMContentLoaded', () => {
    const wrapper = document.getElementById('wrapper');

    // Si estamos en un entorno con Node (Electron), intentar insertar en MongoDB
    try {
        const nodeRequire = typeof require === 'function' ? require : null;
        if (nodeRequire) {
            const path = nodeRequire('path');
            const fs = nodeRequire('fs');
            const { Libro, insertarLibros } = nodeRequire('./model.js');

            // Leer books.json desde el sistema de archivos
            try {
                const fichero = fs.readFileSync(path.join(__dirname, 'books.json'));
                const librosArray = JSON.parse(fichero);

                // Insertar datos SOLO si la colección está vacía
                Libro.countDocuments().then(count => {
                    if (count === 0){
                        insertarLibros(librosArray);
                    } else {
                        console.log('Los libros ya están en la base de datos.');
                    }
                }).catch(err => console.error('Error contando documentos:', err));
            }
            catch (err) {
                console.error('Error leyendo books.json desde Node:', err);
            }
        }
    }
    catch (e) {
        // No hay require disponible, no hacemos inserción en MongoDB
    }

    // Renderizado en la página (usa fetch para poder funcionar también en servidor HTTP)
    fetch('books.json')
        .then(response => {
            if (!response.ok) throw new Error('Error cargando books.json');
            return response.json();
        })
        .then(libros => renderLibros(libros))
        .catch(error => {
            console.error(error);
            if (wrapper) wrapper.innerHTML = '<p>Error cargando libros.</p>';
        });

    function renderLibros(libros) {
        if (!wrapper) return;
        wrapper.innerHTML = '';
        libros.forEach(libro => {
            const card = document.createElement('div');
            card.className = 'book-card';

            const img = document.createElement('img');
            img.className = 'book-img';
            img.alt = libro.title;
            img.src = `imagenes/${libro.img}`;

            const info = document.createElement('div');
            info.className = 'book-info';

            const title = document.createElement('h3');
            title.textContent = libro.title;

            const author = document.createElement('p');
            author.textContent = libro.author;

            info.appendChild(title);
            info.appendChild(author);

            card.appendChild(img);
            card.appendChild(info);

            wrapper.appendChild(card);
        });
    }
});
