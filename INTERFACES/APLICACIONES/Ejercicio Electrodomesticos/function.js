document.addEventListener('DOMContentLoaded', () => {
    const fs = require('fs');
    const desplegable = document.getElementById('desplegable');
    const informacion = document.getElementById('inf');
    const totales = document.getElementById('totales');

    function mostrarInformacion(e){
        informacion.innerHTML = `Descripción: ${e.nombre} <br>
        Precio coste: ${e.precioCoste} <br>
        Precio venta: ${e.precioVenta} <br>
        Stock actual: ${e.stockActual} <br>
        Stock mínimo: ${e.stockMin}`;
    }

    try {
        const fichero = fs.readFileSync('./electrodomesticos.json');

        let electrodomesticos = new Array();
        electrodomesticos = JSON.parse(fichero);


        desplegable.innerHTML = electrodomesticos.map(e => `<option value="${e.nombre}">${e.nombre}</option>`).join('');
        mostrarInformacion(electrodomesticos[0]);

        desplegable.addEventListener('change', function(){
            const seleccionado = this.value;

            let encontrado = electrodomesticos.find(e => e.nombre === seleccionado);

            if (encontrado){
                mostrarInformacion(encontrado);
            }
            else {
                informacion.innerHTML = '<p>Por favor selecciona un producto</p>';

            }
        });

        totales.innerHTML = `<ul>
            <li>Total Productos: ${electrodomesticos.length}</li>
            <li>Total Stock Actual: ${electrodomesticos.reduce((acc, e) => acc + e.stockActual, 0)}</li>
            <li>Productos con Stock por debajo del mínimo:
                <ol>${electrodomesticos.filter(e => e.stockActual < e.stockMin).map(e => `<li>${e.nombre}</li>`).join('')}</ol>
            </li>
        </ul>`
    } catch (error) {
        console.error("Error al cargar los datos: ", error);
        informacion.innerHTML = '<p>Error al cargar los datos del producto.</p>';
    }
});