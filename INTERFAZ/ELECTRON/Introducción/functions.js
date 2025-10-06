let mDiv = document.getElementById("datos");

mDiv.innerHTML = "<p>Contenido del Div</p>";

let button = document.createElement("button");
button.textContent = "Botón";

document.body.appendChild(button);

button.addEventListener("click", function() {
    alert("¡Hola! Has hecho clic en el botón.");
});