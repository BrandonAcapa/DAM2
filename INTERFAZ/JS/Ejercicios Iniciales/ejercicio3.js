console.log("Triángulo inferior con N=20\n");

// let n = 0;
// let limit = 20;

// while (n < 20){
//     let i = 0;
//     let y = "";
//     let x = "*";
    
//     while (i < limit){
//         y = y + "* ";
//         i++;
//         // console.log(i)
//     }

//     console.log(y + x);
//     limit = limit - 1;
//     n++;
// }

// PROBANDO .repeat();

// let z = "  ";

// console.log(z + "*");

// console.log(z.repeat(19) + "*");

let n = 20;
let y = "* ";

while (n > 0){
    console.log(y.repeat(n) + "*");
    n--;
}