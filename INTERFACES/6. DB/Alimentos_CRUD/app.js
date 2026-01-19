document.addEventListener('DOMContentLoaded', () => {
  const fs = (typeof require === 'function') ? require('fs') : null;
  const nodeRequire = (typeof require === 'function') ? require : null;

  let Alimento;

  // helpers for image handling and id generation
  const path = nodeRequire ? nodeRequire('path') : null;
  function sanitizeName(name) {
    return name.toString().trim().toLowerCase().replace(/\s+/g, '_').replace(/[^a-z0-9_\-\.]/g, '');
  }

  function ensureImgDir() {
    try {
      const dir = path.join(process.cwd(), 'img');
      if (!fs.existsSync(dir)) fs.mkdirSync(dir);
      return dir;
    } catch (err) {
      console.error('No se pudo crear/verificar img/:', err);
      return null;
    }
  }

  async function copyImageFile(fileOrPath, targetName) {
    try {
      const dir = ensureImgDir();
      if (!dir) return null;

      if (typeof fileOrPath === 'string') {
        const ext = path.extname(fileOrPath) || '';
        const filename = targetName + ext;
        const dest = path.join(dir, filename);
        fs.copyFileSync(fileOrPath, dest);
        return `img/${filename}`;
      }

      // assume File object from input
      const file = fileOrPath;
      const origName = file.name || '';
      const ext = path.extname(origName) || '';
      const filename = targetName + ext;
      const dest = path.join(dir, filename);

      // read file contents via arrayBuffer (browser File API)
      const arrayBuffer = await file.arrayBuffer();
      const buffer = Buffer.from(arrayBuffer);
      fs.writeFileSync(dest, buffer);
      return `img/${filename}`;
    } catch (err) {
      console.error('Error copiando imagen:', err);
      return null;
    }
  }

  function renameImageFile(oldRelPath, newName) {
    try {
      if (!oldRelPath) return null;
      const dir = ensureImgDir();
      if (!dir) return null;
      const oldPath = path.join(process.cwd(), oldRelPath);
      if (!fs.existsSync(oldPath)) return null;
      const ext = path.extname(oldPath) || '';
      const newFilename = newName + ext;
      const newPath = path.join(dir, newFilename);
      fs.renameSync(oldPath, newPath);
      return `img/${newFilename}`;
    } catch (err) {
      console.error('Error renombrando imagen:', err);
      return null;
    }
  }

  const placeholderDataUrl = 'data:image/svg+xml;utf8,' + encodeURIComponent('<svg xmlns="http://www.w3.org/2000/svg" width="300" height="200"><rect width="100%" height="100%" fill="#eef6ef"/><text x="50%" y="50%" dominant-baseline="middle" text-anchor="middle" fill="#9aa" font-family="sans-serif" font-size="18">Sin imagen</text></svg>');

  function resolveImageUrl(record) {
    try {
      if (!record) return placeholderDataUrl;
      const imgField = record.imagen || '';
      const projectRoot = process.cwd();
      if (imgField) {
        const full = path.join(projectRoot, imgField);
        if (fs.existsSync(full)) return imgField;
      }

      // try to find by sanitized name in img/ directory
      const dir = path.join(projectRoot, 'img');
      if (fs.existsSync(dir)) {
        const files = fs.readdirSync(dir);
        const base = sanitizeName(record.nombre || '');
        const match = files.find(f => sanitizeName(path.basename(f, path.extname(f))) === base);
        if (match) return `img/${match}`;
      }

      return placeholderDataUrl;
    } catch (err) {
      return placeholderDataUrl;
    }
  }

  async function getNextId() {
    try {
      const last = await Alimento.findOne({}).sort({ id: -1 }).limit(1);
      return last ? last.id + 1 : 1;
    } catch (err) {
      console.error('Error obteniendo next id:', err);
      return Date.now();
    }
  }

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
        const imageUrl = resolveImageUrl(a);
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
      });

      // populate edit select (sorted alphabetically by nombre)
      const editSelect = document.getElementById('editSelect');
      if (editSelect) {
        // copy and sort by name
        const byName = alimentos.slice().sort((x, y) => x.nombre.localeCompare(y.nombre, 'es', { sensitivity: 'base' }));
        // clear and add placeholder
        editSelect.innerHTML = '<option value="" disabled selected>Selecciona...</option>';
        byName.forEach(a => {
          const opt = document.createElement('option');
          opt.value = a.id;
          opt.textContent = a.nombre;
          editSelect.appendChild(opt);
        });
        // if an item is currently selected in edit form, keep selection
        const currentEditId = document.getElementById('editFoodId')?.value;
        if (currentEditId) editSelect.value = currentEditId;
      }

      // attach events for edit and delete buttons
      container.querySelectorAll('.edit-btn').forEach(btn => {
        btn.addEventListener('click', async (e) => {
          const id = parseInt(e.currentTarget.dataset.id, 10);
          const doc = await Alimento.findOne({ id });
          if (doc) loadIntoEditForm(doc);
          const editSection = document.getElementById('editForm');
          if (editSection) editSection.scrollIntoView({ behavior: 'smooth' });
        });
      });

      container.querySelectorAll('.delete-btn').forEach(btn => {
        btn.addEventListener('click', async (e) => {
          const id = parseInt(e.currentTarget.dataset.id, 10);
          // fetch doc to show name in confirm
          let nombre = id;
          try {
            const doc = await Alimento.findOne({ id });
            if (doc && doc.nombre) nombre = doc.nombre;
          } catch (_) { /* ignore */ }
          if (!confirm('Borrar alimento "' + nombre + '"?')) return;
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

  function loadIntoEditForm(doc) {
    const hid = document.getElementById('editFoodId');
    if (hid) hid.value = doc.id;
    const select = document.getElementById('editSelect');
    if (select) select.value = doc.id;
    document.getElementById('name_edit').value = doc.nombre;
    document.getElementById('category_edit').value = doc.categoria;
    document.getElementById('fat_edit').value = doc.grasas;
    document.getElementById('carb_edit').value = doc.carbohidratos;
    document.getElementById('protein_edit').value = doc.proteinas;
    document.getElementById('calories_edit').value = doc.calorias;
  }

  function calculateCaloriesFor(ids) {
    const fat = parseFloat(document.getElementById(ids.fat).value) || 0;
    const carb = parseFloat(document.getElementById(ids.carb).value) || 0;
    const protein = parseFloat(document.getElementById(ids.protein).value) || 0;
    const kcal = (fat * 9) + (carb * 4) + (protein * 4);
    document.getElementById(ids.calories).value = Math.round(kcal);
  }

  // bind calculators for create and edit forms (if elements exist)
  if (document.getElementById('fat')) {
    document.getElementById('fat').addEventListener('input', () => calculateCaloriesFor({ fat: 'fat', carb: 'carb', protein: 'protein', calories: 'calories' }));
    document.getElementById('carb').addEventListener('input', () => calculateCaloriesFor({ fat: 'fat', carb: 'carb', protein: 'protein', calories: 'calories' }));
    document.getElementById('protein').addEventListener('input', () => calculateCaloriesFor({ fat: 'fat', carb: 'carb', protein: 'protein', calories: 'calories' }));
  }
  if (document.getElementById('fat_edit')) {
    document.getElementById('fat_edit').addEventListener('input', () => calculateCaloriesFor({ fat: 'fat_edit', carb: 'carb_edit', protein: 'protein_edit', calories: 'calories_edit' }));
    document.getElementById('carb_edit').addEventListener('input', () => calculateCaloriesFor({ fat: 'fat_edit', carb: 'carb_edit', protein: 'protein_edit', calories: 'calories_edit' }));
    document.getElementById('protein_edit').addEventListener('input', () => calculateCaloriesFor({ fat: 'fat_edit', carb: 'carb_edit', protein: 'protein_edit', calories: 'calories_edit' }));
  }

  // Create (foodForm) - solo crear
  const foodFormEl = document.getElementById('foodForm');
  if (foodFormEl) {
    foodFormEl.addEventListener('submit', async (e) => {
      e.preventDefault();
      if (!Alimento) return console.log('Modelo Alimento no disponible');

      const nombre = document.getElementById('name').value.trim();
      const categoria = document.getElementById('category').value;
      const grasas = parseFloat(document.getElementById('fat').value) || 0;
      const carbohidratos = parseFloat(document.getElementById('carb').value) || 0;
      const proteinas = parseFloat(document.getElementById('protein').value) || 0;
      const calorias = parseInt(document.getElementById('calories').value, 10) || 0;

      if (!nombre) return console.log('Rellena al menos el nombre');

      try {
        const idField = await getNextId();

        // handle image input
        let imagenRel = '';
        const fileInput = document.getElementById('image');
        if (fileInput && fileInput.files && fileInput.files.length > 0) {
          const f = fileInput.files[0];
          const safe = sanitizeName(nombre);
          const copied = await copyImageFile(f, safe);
          if (copied) imagenRel = copied;
        }

        const nuevo = new Alimento({ id: idField, nombre, categoria, imagen: imagenRel, grasas, carbohidratos, proteinas, calorias });
        await nuevo.save();
        foodFormEl.reset();
        cargarAlimentos();
      } catch (err) {
        console.error('Error creando alimento:', err);
      }
    });
  }

  // Edit form submit
  const editFormEl = document.getElementById('editForm');
  if (editFormEl) {
    editFormEl.addEventListener('submit', async (e) => {
      e.preventDefault();
      if (!Alimento) return console.log('Modelo Alimento no disponible');
      const editId = parseInt(document.getElementById('editFoodId').value, 10);
      if (!editId) return console.log('Selecciona un alimento para editar');

      const nombre = document.getElementById('name_edit').value.trim();
      const categoria = document.getElementById('category_edit').value;
      const grasas = parseFloat(document.getElementById('fat_edit').value) || 0;
      const carbohidratos = parseFloat(document.getElementById('carb_edit').value) || 0;
      const proteinas = parseFloat(document.getElementById('protein_edit').value) || 0;
      const calorias = parseInt(document.getElementById('calories_edit').value, 10) || 0;

      try {
        // find current doc to manage images
        const current = await Alimento.findOne({ id: editId });
        let imagenRel = current && current.imagen ? current.imagen : '';

        // check if new file uploaded
        const fileInput = document.getElementById('image_edit');
        if (fileInput && fileInput.files && fileInput.files.length > 0) {
          const f = fileInput.files[0];
          const safe = sanitizeName(nombre);
          const copied = await copyImageFile(f, safe);
          if (copied) imagenRel = copied;
          // remove old file if different
          if (current && current.imagen && current.imagen !== imagenRel) {
            try { fs.unlinkSync(path.join(process.cwd(), current.imagen)); } catch (e) { /* ignore */ }
          }
        } else {
          // no new file: if name changed, rename existing image file
          if (current && current.imagen && current.nombre !== nombre) {
            const safe = sanitizeName(nombre);
            const renamed = renameImageFile(current.imagen, safe);
            if (renamed) imagenRel = renamed;
          }
        }

        const update = { nombre, categoria, grasas, carbohidratos, proteinas, calorias };
        if (imagenRel !== undefined) update.imagen = imagenRel;

        await Alimento.updateOne({ id: editId }, { $set: update });
        editFormEl.reset();
        document.getElementById('editFoodId').value = '';
        cargarAlimentos();
      } catch (err) {
        console.error('Error actualizando alimento:', err);
      }
    });

    // when select changes, populate fields
    const editSelectEl = document.getElementById('editSelect');
    if (editSelectEl) {
      editSelectEl.addEventListener('change', async (e) => {
        const id = parseInt(e.currentTarget.value, 10);
        if (!id) return;
        const doc = await Alimento.findOne({ id });
        if (doc) loadIntoEditForm(doc);
      });
    }
  }

  // Init
  initDBAndLoad();
});
