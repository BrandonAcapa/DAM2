document.addEventListener('DOMContentLoaded', () => {
  const fs = (typeof require === 'function') ? require('fs') : null;
  const nodeRequire = (typeof require === 'function') ? require : null;

  let Alimento;

  async function initDBAndLoad() {
    if (!nodeRequire) {
      console.log('No hay require disponible en este entorno.');
      return;
    }

    const { Alimento: AlimentoModel, inicializarDB, conectarDB, ejemplosAlimentos } = nodeRequire('./db.js');
    Alimento = AlimentoModel;

    try {
      await conectarDB();
      const count = await Alimento.countDocuments();
      if (count === 0) {
        await inicializarDB(ejemplosAlimentos);
      }
      await cargarAlimentos();
    } catch (err) {
      console.error('Error inicializando DB o cargando alimentos:', err);
    }
  }

  async function cargarAlimentos() {
    try {
      const alimentos = await Alimento.find({}).sort({ id: 1 });
      const container = document.getElementById('cardsContainer');
      container.innerHTML = '';

      alimentos.forEach(a => {
        const card = document.createElement('article');
        card.className = 'card';
        const imageUrl = a.imagen || 'placeholder.png';
        card.innerHTML = `
          <div class="card-image"><img src="${imageUrl}" alt="${a.nombre}"></div>
          <div class="card-body">
            <h3>${a.nombre} </h3>
            <p class="meta">${a.categoria}</p>
            <p class="macros">Grasas: ${a.grasas}g · Carbs: ${a.carbohidratos}g · Proteínas: ${a.proteinas}g</p>
            <p class="cal">${a.calorias} kcal</p>
            <div class="card-actions">
              <button type="button" class="edit-btn" data-id="${a.id}">Editar</button>
              <button type="button" class="delete-btn" data-id="${a.id}">Eliminar</button>
            </div>
          </div>
        `;
        container.appendChild(card);
        // <small>#{${a.id}}</small>
      });

      // attach events
      container.querySelectorAll('.edit-btn').forEach(btn => {
        btn.addEventListener('click', async (e) => {
          const id = parseInt(e.currentTarget.dataset.id, 10);
          const doc = await Alimento.findOne({ id });
          if (doc) loadIntoForm(doc);
          window.scrollTo({ top: document.body.scrollHeight, behavior: 'smooth' });
        });
      });

      container.querySelectorAll('.delete-btn').forEach(btn => {
        btn.addEventListener('click', async (e) => {
          const id = parseInt(e.currentTarget.dataset.id, 10);
          if (!confirm('Borrar alimento con ID ' + id + '?')) return;
          try {
            const res = await Alimento.deleteOne({ id });
            if (res.deletedCount > 0) cargarAlimentos();
          } catch (err) { console.error(err); }
        });
      });

    } catch (err) {
      console.error('Error cargando alimentos:', err);
    }
  }

  function loadIntoForm(doc) {
    document.getElementById('foodId').value = doc.id;
    document.getElementById('idField').value = doc.id;
    document.getElementById('name').value = doc.nombre;
    document.getElementById('category').value = doc.categoria;
    document.getElementById('fat').value = doc.grasas;
    document.getElementById('carb').value = doc.carbohidratos;
    document.getElementById('protein').value = doc.proteinas;
    document.getElementById('calories').value = doc.calorias;
  }

  function calculateCalories() {
    const fat = parseFloat(document.getElementById('fat').value) || 0;
    const carb = parseFloat(document.getElementById('carb').value) || 0;
    const protein = parseFloat(document.getElementById('protein').value) || 0;
    const kcal = (fat * 9) + (carb * 4) + (protein * 4);
    document.getElementById('calories').value = Math.round(kcal);
  }

  document.getElementById('fat').addEventListener('input', calculateCalories);
  document.getElementById('carb').addEventListener('input', calculateCalories);
  document.getElementById('protein').addEventListener('input', calculateCalories);

  document.getElementById('foodForm').addEventListener('submit', async (e) => {
    e.preventDefault();
    if (!Alimento) return console.log('Modelo Alimento no disponible');

    const existingId = document.getElementById('foodId').value;
    const idField = parseInt(document.getElementById('idField').value, 10);
    const nombre = document.getElementById('name').value.trim();
    const categoria = document.getElementById('category').value;
    const grasas = parseFloat(document.getElementById('fat').value) || 0;
    const carbohidratos = parseFloat(document.getElementById('carb').value) || 0;
    const proteinas = parseFloat(document.getElementById('protein').value) || 0;
    const calorias = parseInt(document.getElementById('calories').value, 10) || 0;

    if (!idField || !nombre) return console.log('Rellena al menos ID y nombre');

    try {
      if (existingId) {
        await Alimento.updateOne({ id: parseInt(existingId, 10) }, {
          $set: {
            id: idField,
            nombre,
            categoria,
            grasas,
            carbohidratos,
            proteinas,
            calorias
          }
        });
      } else {
        const nuevo = new Alimento({ id: idField, nombre, categoria, grasas, carbohidratos, proteinas, calorias });
        await nuevo.save();
      }

      document.getElementById('foodForm').reset();
      document.getElementById('foodId').value = '';
      cargarAlimentos();
    } catch (err) {
      console.error('Error guardando alimento:', err);
    }
  });

  // Init
  initDBAndLoad();
});
