const limpiar = document.getElementById("limpiar");
const btn = document.getElementById("convertir");
let celsius = document.getElementById("celsius");
let kelvin = document.getElementById("kelvin");

btn.addEventListener("click", () => {
    if (celsius.value === "" && kelvin.value === "") {
        alert("Por favor, ingrese un valor en Celsius o Kelvin.");
    }
    else if (celsius.value !== "" && kelvin.value === "") {
        kelvin.value = parseFloat(celsius.value) + 273.15;
    }
    else if (kelvin.value !== "" && celsius.value === "") {
        celsius.value = parseFloat(kelvin.value) - 273.15;
    }
    else{
        alert("Por favor, ingrese un valor en solo uno de los campos.");
    }

})

limpiar.addEventListener("click", () => {
    celsius.value = "";
    kelvin.value = "";
})