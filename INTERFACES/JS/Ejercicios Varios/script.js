// EJERCICIO 1: Cadena más larga

function cadenaMasLarga(c1, c2) {
    if (typeof c1 !== "string" || typeof c2 !== "string"){
        console.log("Error: ambos parámetros deben ser cadenas de texto.");
    }
    else{
        if (c1.length > c2.length){
            console.log(c1 + " es más larga que " + c2);
        }
        else if (c1.length < c2.length){
            console.log(c2 + " es más larga que " + c1);
        }
    }
}

// cadenaMasLarga("hola", "adiós");
// cadenaMasLarga("hola", 4);
// cadenaMasLarga("pato", "elefante");
// cadenaMasLarga("mapache", "hoja");
// cadenaMasLarga("ratón", "ey");


// EJERCICIO 2: Imprimir numero n veces

function imprimirNumeroNVeces(x, n){
    if (typeof x !== "number"  || typeof n !== "number"){
        console.log("Error: ambos parámetros deben ser números.");
    }
    else{
        if (n < 0){
            console.log("Error: el segundo parámetro debe ser un número positivo.");
        }
        else{
            console.log(x.toString().repeat(n));
        }
    }
}

// imprimirNumeroNVeces(3, 5);
// imprimirNumeroNVeces(7, -3);
// imprimirNumeroNVeces(-4, 6);

// EJERCCICIO 3: Imprimir las veces que un caracter aparece en una cadena

function contarCaracterCadena(cadena, c){
    if (typeof cadena !== "string" || typeof c !== "string" || c.length !== 1){
        console.log("Error: el primer parámetro debe ser una cadena y el segundo debe ser un caracter")
    }
    else{
        
    }
}
