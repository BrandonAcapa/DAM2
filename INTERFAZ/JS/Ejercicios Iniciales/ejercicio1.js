console.log("Diagonal con N=20\n");

let n = 0;
let y = "  ";

// while (n < 20){
//     let x = "*";
//     console.log(y + x);
//     n = n + 1;
//     y = y + "  ";
// }

while (n < 20){
    console.log(y.repeat(n) + "*");
    n++;
}