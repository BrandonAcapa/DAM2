// CONEXIÓN: mongodb://localhost:27017/alimentos

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

// DATOS DE EJEMPLO
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
		imagen: 'img/lechuga.jpg',
		grasas: 0.2,
		carbohidratos: 2.9,
		proteinas: 1.4,
		calorias: 19
	},
	{
		id: 3,
		nombre: 'Salmón',
		categoria: 'Pescado',
		imagen: 'img/salmon.jpg',
		grasas: 13,
		carbohidratos: 0,
		proteinas: 20,
		calorias: 197
	},
	{
		id: 4,
		nombre: 'Pollo',
		categoria: 'Carne',
		imagen: 'img/pollo.jpg',
		grasas: 6.0,
		carbohidratos: 0,
		proteinas: 27,
		calorias: 165
	},
	{
		id: 5,
		nombre: 'Yogur',
		categoria: 'Lácteo',
		imagen: 'img/yogur.jpg',
		grasas: 3.3,
		carbohidratos: 4.7,
		proteinas: 3.5,
		calorias: 61
	},
	{
		id: 6,
		nombre: 'Plátano',
		categoria: 'Fruta',
		imagen: 'img/platano.jpg',
		grasas: 0.3,
		carbohidratos: 23,
		proteinas: 1.1,
		calorias: 96
	},
	{
		id: 7,
		nombre: 'Zanahoria',
		categoria: 'Verdura',
		imagen: 'img/zanahoria.jpg',
		grasas: 0.2,
		carbohidratos: 10,
		proteinas: 0.9,
		calorias: 41
	},
	{
		id: 8,
		nombre: 'Ternera',
		categoria: 'Carne',
		imagen: 'img/ternera.jpg',
		grasas: 15,
		carbohidratos: 0,
		proteinas: 26,
		calorias: 250
	},
	{
		id: 9,
		nombre: 'Queso',
		categoria: 'Lácteo',
		imagen: 'img/queso.jpg',
		grasas: 33,
		carbohidratos: 1.3,
		proteinas: 25,
		calorias: 402
	}
];

module.exports = { Alimento, inicializarDB, conectarDB, ejemplosAlimentos };