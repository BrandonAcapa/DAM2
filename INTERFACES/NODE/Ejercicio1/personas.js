const nuevaPersona = (datos, persona) => {
	const existe = datos.some(p => p.telefono === persona.telefono);
	if (!existe) {
		datos.push(persona);
		return `Persona añadida: ${persona.nombre}`;
	} else {
		return `El teléfono ${persona.telefono} ya existe.`;
	}
};

const borrarPersona = (datos, telefonoABuscar) => {
	const existe = datos.some(p => p.telefono === telefonoABuscar);
	if (existe) {
		const nuevoArray = datos.filter(persona => persona.telefono != telefonoABuscar);
		// Actualiza el array original
		datos.length = 0;
		datos.push(...nuevoArray);
		return `Persona con teléfono ${telefonoABuscar} eliminada.`;
	} else {
		return `No existe persona con teléfono ${telefonoABuscar}.`;
	}
};

module.exports = { nuevaPersona, borrarPersona };
