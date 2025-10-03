let datos = [
{nombre: "Nacho", telefono: "966112233", edad: 40},
{nombre: "Ana", telefono: "911223344", edad: 35},
{nombre: "Mario", telefono: "611998877", edad: 15},
{nombre: "Laura", telefono: "633663366", edad: 17}
];

const nuevaPersona = persona => {
	return new Promise((resolve, reject) => {
		const existe = datos.some(p => p.telefono === persona.telefono);
		if (!existe) {
			datos.push(persona);
			resolve(`Persona añadida: ${persona.nombre}`);
		} else {
			reject(`El teléfono ${persona.telefono} ya existe.`);
		}
	});
};

const borrarPersona = telefonoABuscar => {
	return new Promise((resolve, reject) => {
		const existe = datos.some(p => p.telefono === telefonoABuscar);
		if (existe) {
			datos = datos.filter(persona => persona.telefono != telefonoABuscar);
			resolve(`Persona con teléfono ${telefonoABuscar} eliminada.`);
		} else {
			reject(`No existe persona con teléfono ${telefonoABuscar}.`);
		}
	});
};

nuevaPersona({nombre: "Juan", telefono:"965661564", edad: 60})
	.then(msg => console.log(msg))
	.catch(err => console.error(err));

nuevaPersona({nombre: "Rodolfo", telefono:"910011001", edad: 20})
	.then(msg => console.log(msg))
	.catch(err => console.error(err));

borrarPersona("910011001")
	.then(msg => console.log(msg))
	.catch(err => console.error(err));

console.log(datos);
nuevaPersona({nombre: "Juan", telefono:"965661564", edad: 60});
nuevaPersona({nombre: "Rodolfo", telefono:"910011001", edad: 20});
borrarPersona("910011001");
console.log(datos);