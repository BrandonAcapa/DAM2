// Categorías válidas de alimentos
export const CATEGORIAS_VALIDAS = ['Fruta', 'Verdura', 'Pescado', 'Carne', 'Lácteo'];

// Calcula calorías totales usando la fórmula:
// Calorías = (grasas × 9) + (carbohidratos × 4) + (proteínas × 4)

export function calculateCalories(fat, carb, protein) {
  const f = Number(fat) || 0;
  const c = Number(carb) || 0;
  const p = Number(protein) || 0;
  const kcal = (f * 9) + (c * 4) + (p * 4);
  return Math.round(kcal);
}

// Valida que un alimento tenga todos los campos requeridos con valores correctos

export function validateAlimento(alimento) {
  if (!alimento || typeof alimento !== 'object') return false;
  
  // Verificar que existan todos los campos requeridos
  const requiredFields = ['id', 'nombre', 'categoria', 'grasas', 'carbohidratos', 'proteinas', 'calorias'];
  const hasAllFields = requiredFields.every(field => alimento.hasOwnProperty(field));
  
  if (!hasAllFields) return false;
  
  // Validar tipos y valores
  const isValidId = typeof alimento.id === 'number' && alimento.id > 0;
  const isValidName = typeof alimento.nombre === 'string' && alimento.nombre.trim().length > 0;
  const isValidCategory = CATEGORIAS_VALIDAS.includes(alimento.categoria);
  const isValidNutrition = ['grasas', 'carbohidratos', 'proteinas', 'calorias'].every(
    field => typeof alimento[field] === 'number' && alimento[field] >= 0
  );
  
  return isValidId && isValidName && isValidCategory && isValidNutrition;
}

