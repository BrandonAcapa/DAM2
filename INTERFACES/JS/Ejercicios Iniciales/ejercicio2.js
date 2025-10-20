console.log("Diagonal inversa con N=20\n");

let limit = 20;
let n = 20;
let y = "  ";

// for (let n = 0; n < 20; n++){

//     let y = "";
//     let x = "*";

//     for (let n = 0; n < limit; n++){
//         y = y + "  ";
//     }
//     console.log(y + x);
//     limit = limit - 1;
// }

while (n > 0){
    console.log(y.repeat(n-1) + "*");
    n--;
}