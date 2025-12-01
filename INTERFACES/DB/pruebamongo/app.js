const mongoose = require('mongoose');
//establecemos el motor de promesa por defecto en javascript
//esto es necesario pq mongo admite distintos tipos de promesas
mongoose.Promise = global.Promise;

mongoose.connect('mongodb://localhost:27017/contactos')

let contactoSchema = new mongoose.Schema({
    nombre: {
        type: String,
        required: true,
        minlength: 1,
        trim: true
    },
    telefono: {
        type: String,
        required: true,
        unique: true,
        trim: true,
        match: /^\d{9}$/

    },
    edad: {
        type: Number,
        min: 18,
        max: 120
    }
});

let Contacto = mongoose.model('contactos', contactoSchema);

let contacto1 = new Contacto({
    nombre: "Boris",
    telefono: "946112230",
    edad: 49
});

let contacto2 = new Contacto({
    nombre: "Brandon",
    telefono: "640926020",
    edad: 20
});

//usaremos una promesa save para guardarlo en la base de datos
let p1 = contacto1.save().then(resultado => {
    console.log("Contacto añadido:", resultado);
}).catch(error => {
    console.log("ERROR añadiendo contacto:", error);
});

// let p2 = contacto2.save().then(resultado => {
//     console.log("Contacto añadido:", resultado);
// }).catch(error => {
//     console.log("ERROR añadiendo contacto:", error);

// });

//busqueda con find
// let p3 = Contacto.find().then(resultado => {
//     console.log(resultado);
// }).catch(error => {
//     console.log("ERROR:", error);
// });

let p4 = Contacto.find({nombre: 'Boris', edad: 49}).then(resultado => {
    console.log(resultado);
}).catch(error => {
    console.log("ERROR:", error);
});

// let p5 = Contacto.deleteOne({ nombre: 'Boris' }).then(resultado => {
//     console.log(resultado);
// }).catch(error => {
//     console.log("ERROR:", error);
// });

//Actualizar documentos
// let p6 = Contacto.findOneAndUpdate(
//     { nombre: 'Boris' },
//     { nombre: 'Boris Anaya', edad: 50 },
//     { new: true })
//     .then((resultado) => { console.log("Contacto Actualizado", resultado) })

let p6 = Contacto.findByIdAndUpdate('5ede78b4c5e89d072c3d1a71',
    { nombre: 'Boris Anaya Moreno', edad: 51 }, { new: true })
    .then(resultado => {
        console.log("Modificado contacto:", resultado);
    }).catch(error => {
        console.log("ERROR:", error);
    });

//debemos esperar que acaben todas las promesas 
//para cerrar la conexión a la base de datos
Promise.all([p1, p4, p6]).then(values => {
    mongoose.connection.close();
});