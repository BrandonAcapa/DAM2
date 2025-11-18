const { rejects } = require('assert')
const { error } = require('console')
const fs = require('fs')


// EJERCICIO 1

async function modificarFichero() {
    try {
        let fichero = await fs.promises.readFile('robots.txt', 'utf8');
        fichero += "Hola, soy un robot!\n";
        await fs.promises.writeFile('robots.txt', fichero);
        console.log(fichero);
    } catch (err) {
        error.log("El fichero seleccionado no existe");
    }
}

modificarFichero();


// EJERCICIO 2

function comprobarArrayDentro(x, y){

    x.every(elem => y.includes(elem))

    if(x.every(elem => y.includes(elem))){
        console.log("El primer array se encuentra dentro del segundo\n");
    }
    else if(y.every(elem => x.includes(elem))){
        console.log("El segundo array se encuentra dentro del primero\n");
    }
    else{
        console.log("Ningún array se encuentra dentro del otro\n");
    }
}

let a1 = [0, 1, 2];

let a2 = [0, 1, 2, 3, 4, 5];

comprobarArrayDentro(a1, a2)


// EJERCICIO 3

let fichero2 = fs.readFileSync('./autoescuela.json'); 
let clientes = new Array() 
clientes = JSON.parse(fichero2);

function anyadirPersona(persona){
    clientes.push(persona);
}

let paco = {nombre: 'Paco Escobar', edad: 36, 'carnet de conducir': false}
let pedro = {nombre: 'Pedro Aguilar', edad: 29, 'carnet de conducir': true}

anyadirPersona(paco)
anyadirPersona(pedro)

function personasConCarnet(){
    let conCarnet = []

    for(let i in clientes){
        if(clientes[i]['carnet de conducir'] == true){
            conCarnet.push(clientes[i].nombre)
        }
    }

    return conCarnet
}

console.log("Personas de la Autoescuela:")
console.log(clientes);
console.log("\nPersonas con Carnet:")
console.log(personasConCarnet());


fs.writeFileSync('./autoescuela.json', JSON.stringify(clientes));

// EJERCICIO 4

let colores= 
[{ 
    "nombreColor":"rojo", 
    "valorHexadec":"#f00" 
}, 
{ 
    "nombreColor":"verde", 
    "valorHexadec":"#0f0" 
}, 
{ 
    "nombreColor":"azul", 
    "valorHexadec":"#00f" 
}, 
{ 
    "nombreColor":"cyan", 
    "valorHexadec":"#0ff" 
}, 
{ 
    "nombreColor":"magenta", 
    "valorHexadec":"#f0f" 
}, 
{ 
    "nombreColor":"amarillo", 
    "valorHexadec":"#ff0" 
}, 
{
    "nombreColor":"negro", 
    "valorHexadec":"#000" 
}]

function colorHexadecimial(c){
    if (c.typeOf = "string"){
        for(let i in colores){
            if(c == colores[i].nombreColor){
                return colores[i].valorHexadec
            }
        }
    }
    else{
        return "El color debe ser un string"
    }
}

color = "amarillo"

console.log(`\nEl hexadecimal del color ${color} es ` + colorHexadecimial(color) + "\n");

// EJERCICIO 5

class Ropa{
    tipo
    material

    Ropa(){

    }

    Ropa(tipo, material){
        this.tipo = tipo
        this.material = material
    }

    lavar() {
        
    }

    secar() {

    }
}

class Pantalones extends Ropa{
    Pantalones(){

    }

    Pantalones(tipo, material){
        this.tipo = tipo
        this.material = material
    }

    hacercolada(){
        this.lavar()
        this.secar()
        return "Colada de pantalones hecha\n"
    }
}

class Zapatos extends Ropa{
    zapatos(){

    }

    zapatos(tipo, material){
        this.tipo = tipo
        this.material = material
    }

    hacercolada(){
        this.lavar()
        this.secar()
        return "Colada de zapatos hecha\n"

    }
}

let pantalonAzul = new Pantalones("Hombre", "Tela");


console.log(pantalonAzul.hacercolada());
