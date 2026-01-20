document.addEventListener('DOMContentLoaded', () => {
  const fs = (typeof require === 'function') ? require('fs') : null;
  const nodeRequire = (typeof require === 'function') ? require : null;
  const path = nodeRequire ? nodeRequire('path') : null;

  if (!fs || !path) console.warn('Atención: `fs` o `path` no disponibles en este entorno; manejo de imágenes limitado.');

  let Alimento;

  // normaliza un nombre para usar en filenames (minusculas, guiones bajos, sin caracteres raros)
  function sanitizeName(name) {
    if (name === undefined || name === null) return '';
    return name.toString().trim().toLowerCase().replace(/\s+/g, '_').replace(/[^a-z0-9_\-\.]/g, '');
  }

  // VERIFICAR QUE EXISTA LA CARPETA img/
  function ensureImgDir() {
    try {
      const dir = path.join(__dirname, 'img');
      if (!fs.existsSync(dir)) fs.mkdirSync(dir);
      return dir;
    } catch (err) {
      console.error('No se pudo crear/verificar img/:', err);
      return null;
    }
  }

  // GUARDAR IMAGENES SUBIDAS
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

      const file = fileOrPath;
      const origName = file.name || '';
      const ext = path.extname(origName) || '';
      const filename = targetName + ext;
      const dest = path.join(dir, filename);

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
      const normalizedOld = String(oldRelPath).replace(/^\.\//, '');
      const oldPath = path.join(__dirname, normalizedOld);
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

  // IMAGEN POR DEFECTO
  const placeholderDataUrl = 'data:image/svg+xml;utf8,' + encodeURIComponent('<svg xmlns="http://www.w3.org/2000/svg" width="300" height="200"><rect width="100%" height="100%" fill="#eef6ef"/><text x="50%" y="50%" dominant-baseline="middle" text-anchor="middle" fill="#9aa" font-family="sans-serif" font-size="18">Sin imagen</text></svg>');

  // BUSCA LA IMAGEN EN img/ Y SI NO ESTÁ, DEVUELVE LA IMAGEN POR DEFECTO
  function resolveImageUrl(record) {
    try {
      if (!record) return placeholderDataUrl;
      const imgField = record.imagen || '';
      const projectRoot = __dirname;
      if (imgField) {
        const normalized = String(imgField).replace(/^\.\//, '');
        const candidate = path.isAbsolute(imgField) ? imgField : path.join(projectRoot, normalized);
        if (fs.existsSync(candidate)) {
          if (normalized.startsWith('img/')) return normalized;
          return `img/${path.basename(normalized)}`;
        }
      }

      return placeholderDataUrl;
    } catch (err) {
      return placeholderDataUrl;
    }
  }

  // OBTIENER PRÓXIMO ID DISPONIBLE
  async function getNextId() {
    try {
      const last = await Alimento.findOne({}).sort({ id: -1 }).limit(1);
      return last ? last.id + 1 : 1;
    } catch (err) {
      console.error('Error obteniendo next id:', err);
      return Date.now();
    }
  }

  // INICIA BASE DE DATOS Y CARGA ALIMENTOS
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

  // CARGAR LAS CARDS DEALIMENTOS EN LA INTERFAZ
  async function cargarAlimentos() {
    try {
      const alimentos = await Alimento.find({}).sort({ id: 1 });
      const container = document.getElementById('cardsContainer');
      container.innerHTML = '';

      alimentos.forEach(a => {
        const card = document.createElement('article');
        card.className = 'card';
        const imageUrl = resolveImageUrl(a);
        const imgSrc = (typeof imageUrl === 'string' && imageUrl.startsWith('data:')) ? imageUrl : (imageUrl ? `${imageUrl}?v=${Date.now()}` : imageUrl);
        card.innerHTML = `
          <div class="card-image"><img src="${imgSrc}" alt="${a.nombre}"></div>
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

      // RELLENAR SELECT DE EDICIÓN
      const editSelect = document.getElementById('editSelect');
      if (editSelect) {
        // ORDENAR ALIMENTOS POR NOMBRE
        const byName = alimentos.slice().sort((x, y) => x.nombre.localeCompare(y.nombre, 'es', { sensitivity: 'base' }));
        editSelect.innerHTML = '<option value="" disabled selected>Selecciona...</option>';
        byName.forEach(a => {
          const opt = document.createElement('option');
          opt.value = a.id;
          opt.textContent = a.nombre;
          editSelect.appendChild(opt);
        });
        // SI HAY UN ALIMENTO SIENDO EDITADO, SELECCIONARLO
        const currentEditId = document.getElementById('editFoodId')?.value;
        if (currentEditId) editSelect.value = currentEditId;
      }

      // AÑADIR EVENTOS A BOTONES DE EDITAR
      container.querySelectorAll('.edit-btn').forEach(btn => {
        btn.addEventListener('click', async (e) => {
          const id = parseInt(e.currentTarget.dataset.id, 10);
          const doc = await Alimento.findOne({ id });
          if (doc) {
            // CAMBIAR A MODO EDITAR
            const modeSwitch = document.getElementById('modeSwitch');
            if (modeSwitch) {
              modeSwitch.checked = true;
              modeSwitch.dispatchEvent(new Event('change'));
            }
            loadIntoEditForm(doc);
            const foodForm = document.getElementById('foodForm');
            if (foodForm) foodForm.scrollIntoView({ behavior: 'smooth' });
          }
        });
      });

      // AÑADIR EVENTOS A BOTONES DE ELIMINAR
      container.querySelectorAll('.delete-btn').forEach(btn => {
        btn.addEventListener('click', async (e) => {
          const id = parseInt(e.currentTarget.dataset.id, 10);
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

  // RELLENA EL FORMULARIO DE EDICIÓN
  function loadIntoEditForm(doc) {
    const hid = document.getElementById('editFoodId');
    if (hid) hid.value = doc.id;
    const select = document.getElementById('editSelect');
    if (select) select.value = doc.id;
    const nameEl = document.getElementById('name'); if (nameEl) nameEl.value = doc.nombre;
    const catEl = document.getElementById('category'); if (catEl) catEl.value = doc.categoria;
    const fatEl = document.getElementById('fat'); if (fatEl) fatEl.value = doc.grasas;
    const carbEl = document.getElementById('carb'); if (carbEl) carbEl.value = doc.carbohidratos;
    const protEl = document.getElementById('protein'); if (protEl) protEl.value = doc.proteinas;
    const calEl = document.getElementById('calories'); if (calEl) calEl.value = doc.calorias;
  }

  // CALCULA CALORÍAS EN FUNCIÓN DE LOS VALORES NUTRICIONALES
  function calculateCaloriesFor(ids) {
    const fat = parseFloat(document.getElementById(ids.fat).value) || 0;
    const carb = parseFloat(document.getElementById(ids.carb).value) || 0;
    const protein = parseFloat(document.getElementById(ids.protein).value) || 0;
    const kcal = (fat * 9) + (carb * 4) + (protein * 4);
    document.getElementById(ids.calories).value = Math.round(kcal);
  }

  // CALCULAR CALORÍAS AL CAMBIAR VALORES NUTRICIONALES
  if (document.getElementById('fat')) {
    document.getElementById('fat').addEventListener('input', () => calculateCaloriesFor({ fat: 'fat', carb: 'carb', protein: 'protein', calories: 'calories' }));
    document.getElementById('carb').addEventListener('input', () => calculateCaloriesFor({ fat: 'fat', carb: 'carb', protein: 'protein', calories: 'calories' }));
    document.getElementById('protein').addEventListener('input', () => calculateCaloriesFor({ fat: 'fat', carb: 'carb', protein: 'protein', calories: 'calories' }));
  }
  

  // FORMUKARIO CREAR/EDITAR
  const foodFormEl = document.getElementById('foodForm');
  if (foodFormEl) {
    foodFormEl.addEventListener('submit', async (e) => {
      e.preventDefault();
      if (!Alimento) {
        console.log('Modelo Alimento no disponible');
        return;
      }

      // LOGS PARA VER ERRORES
      // try {
      //   console.log('Submitting form. modeSwitch checked:', !!document.getElementById('modeSwitch') && document.getElementById('modeSwitch').checked);
      //   console.log('editFoodId:', document.getElementById('editFoodId')?.value, 'editSelect:', document.getElementById('editSelect')?.value);
      //   console.log('fields ->', {
      //     nombre: document.getElementById('name')?.value,
      //     categoria: document.getElementById('category')?.value,
      //     grasas: document.getElementById('fat')?.value,
      //     carbohidratos: document.getElementById('carb')?.value,
      //     proteinas: document.getElementById('protein')?.value,
      //     calorias: document.getElementById('calories')?.value,
      //   });
      // } catch (logErr) { console.error('Error logging form state:', logErr); }

      const modeSwitch = document.getElementById('modeSwitch');
      const isEdit = modeSwitch && modeSwitch.checked;

      const nombre = document.getElementById('name').value.trim();
      const categoria = document.getElementById('category').value;
      const grasas = parseFloat(document.getElementById('fat').value) || 0;
      const carbohidratos = parseFloat(document.getElementById('carb').value) || 0;
      const proteinas = parseFloat(document.getElementById('protein').value) || 0;
      const calorias = parseInt(document.getElementById('calories').value, 10) || 0;

      if (!nombre) return console.log('Rellena al menos el nombre');

      try {
        if (isEdit) {
          const editId = parseInt(document.getElementById('editFoodId').value, 10) || parseInt(document.getElementById('editSelect')?.value, 10);
          if (!editId) return console.log('Selecciona un alimento para editar');

          const current = await Alimento.findOne({ id: editId });
          let imagenRel = current && current.imagen ? current.imagen : '';

          // DETECTAR CAMBIOS
          const fileInput = document.getElementById('image');
          const imageSelected = fileInput && fileInput.files && fileInput.files.length > 0;
          const nameChanged = String(nombre) !== String(current?.nombre);
          const categoryChanged = String(categoria) !== String(current?.categoria);
          const fatChanged = Number(grasas) !== Number(current?.grasas);
          const carbChanged = Number(carbohidratos) !== Number(current?.carbohidratos);
          const proteinChanged = Number(proteinas) !== Number(current?.proteinas);
          const caloriesChanged = Number(calorias) !== Number(current?.calorias);

          if (!imageSelected && !nameChanged && !categoryChanged && !fatChanged && !carbChanged && !proteinChanged && !caloriesChanged) {
            alert('No hay cambios. Realiza al menos una modificación antes de guardar.');
            return;
          }

          // COMPRUEBA SI HAY UNA NUEVA IMAGEN O CAMBIOS EN EL NOMBRE
          if (imageSelected) {
            const f = fileInput.files[0];
            const safe = sanitizeName(nombre);
            const copied = await copyImageFile(f, safe);
            if (copied) imagenRel = copied;
            if (current && current.imagen && current.imagen !== imagenRel) {
              try { fs.unlinkSync(path.join(process.cwd(), current.imagen)); } catch (e) { /* ignore */ }
            }
          } else {
            if (current && current.imagen && current.nombre !== nombre) {
              const safe = sanitizeName(nombre);
              const renamed = renameImageFile(current.imagen, safe);
              if (renamed) imagenRel = renamed;
            }
          }

          const update = { nombre, categoria, grasas, carbohidratos, proteinas, calorias };
          if (imagenRel !== undefined) update.imagen = imagenRel;
          console.log('Will update id', editId, 'with', update);
          try {
            const res = await Alimento.updateOne({ id: editId }, { $set: update });
            console.log('updateOne result:', res);
          } catch (updErr) {
            console.error('Error during updateOne:', updErr);
            throw updErr;
          }
          foodFormEl.reset();
          document.getElementById('editFoodId').value = '';
          cargarAlimentos();

        } else {
          const idField = await getNextId();
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
        }
      } catch (err) {
        console.error('Error procesando formulario:', err);
      }
    });
  }

  // CAMBIO DE MODO CREAR/EDITAR DEL FORMULARIO
  const modeSwitch = document.getElementById('modeSwitch');
  if (modeSwitch) {
    modeSwitch.addEventListener('change', () => {
      const label = document.getElementById('formModeLabel');
      const editControls = document.querySelector('.edit-controls');
      if (modeSwitch.checked) {
        if (label) label.textContent = 'EDITAR';
        if (editControls) editControls.style.display = 'block';
      } else {
        if (label) label.textContent = 'CREAR';
        if (editControls) editControls.style.display = 'none';
        const hid = document.getElementById('editFoodId'); if (hid) hid.value = '';
        const sel = document.getElementById('editSelect'); if (sel) sel.value = '';
      }
    });
  }

  // CARGA DATOS AL SELECCIONAR EN EL SELECT DE EDICIÓN
  const editSelectEl = document.getElementById('editSelect');
  if (editSelectEl) {
    editSelectEl.addEventListener('change', async (e) => {
      const id = parseInt(e.currentTarget.value, 10);
      if (!id) return;
      const doc = await Alimento.findOne({ id });
      if (doc) loadIntoEditForm(doc);
    });
  }

  // INICIAR DB Y CARGAR ALIMENTOS
  initDBAndLoad();
});