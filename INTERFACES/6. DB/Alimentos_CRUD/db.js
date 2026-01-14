const mongoose = require('mongoose');

const alimentoSchema = new mongoose.Schema({
	id: {
		type: Number,
		required: true,
		unique: true
	},
	nombre: {
		type: String,
		required: true
	},
	categoria: {
		type: String,
		required: true
	},
	imagen: {
		type: String,
		required: false,
		default: ''
	},
	grasas: {
		type: Number,
		required: true,
		default: 0
	},
	carbohidratos: {
		type: Number,
		required: true,
		default: 0
	},
	proteinas: {
		type: Number,
		required: true,
		default: 0
	},
	calorias: {
		type: Number,
		required: true,
		default: 0
	}
});

const Alimento = mongoose.model('alimentos', alimentoSchema);

async function conectarDB() {
	try {
		await mongoose.connect('mongodb://localhost:27017/alimentos');
		console.log('Conectado a MongoDB (alimentos)');
	} catch (error) {
		console.log('ERROR conectando a MongoDB: ', error);
		throw error;
	}
}

async function inicializarDB(alimentos, options = { dropExisting: false }) {
	try {
		if (options.dropExisting) {
			await Alimento.deleteMany({});
		}
		const resultado = await Alimento.insertMany(alimentos, { ordered: false });
		console.log('Alimentos añadidos: ', resultado);
	} catch (error) {
		if (error && error.code === 11000) {
			console.log('Duplicados detectados al insertar (algún documento ya existe).');
		} else {
			console.log('ERROR añadiendo alimentos: ', error);
		}
	}
}

// Datos de ejemplo (tomados de index.html)
const ejemplosAlimentos = [
	{
		id: 1,
		nombre: 'Manzana',
		categoria: 'Fruta',
		imagen: 'img/manzana.jpg',
		grasas: 0.3,
		carbohidratos: 14,
		proteinas: 0.3,
		calorias: 60
	},
	{
		id: 2,
		nombre: 'Lechuga',
		categoria: 'Verdura',
		grasas: 0.2,
		carbohidratos: 2.9,
		proteinas: 1.4,
		calorias: 19
	},
	{
		id: 3,
		nombre: 'Salmón',
		categoria: 'Pescado',
		grasas: 13,
		carbohidratos: 0,
		proteinas: 20,
		calorias: 197
	}
];

module.exports = { Alimento, inicializarDB, conectarDB, ejemplosAlimentos };
