import { describe, it, expect } from 'vitest';
import { calculateCalories, validateAlimento } from '../lib/alimentos.js';
import { ejemplosAlimentos } from '../db.js';

describe('Cálculo de calorías', () => {
  it('Manzana: 0.3g grasas, 14g carbohidratos, 0.3g proteínas = 60 kcal', () => {
    const manzana = ejemplosAlimentos[0];
    expect(calculateCalories(manzana.grasas, manzana.carbohidratos, manzana.proteinas)).toBe(60);
  });

  it('Salmón: 13g grasas, 0g carbohidratos, 20g proteínas = 197 kcal', () => {
    const salmon = ejemplosAlimentos[2];
    expect(calculateCalories(salmon.grasas, salmon.carbohidratos, salmon.proteinas)).toBe(197);
  });

  it('Pollo: 6g grasas, 0g carbohidratos, 27g proteínas = 162 kcal', () => {
    const pollo = ejemplosAlimentos[3];
    expect(calculateCalories(pollo.grasas, pollo.carbohidratos, pollo.proteinas)).toBe(162);
  });

  it('Yogur: 3.3g grasas, 4.7g carbohidratos, 3.5g proteínas = 63 kcal', () => {
    const yogur = ejemplosAlimentos[4];
    expect(calculateCalories(yogur.grasas, yogur.carbohidratos, yogur.proteinas)).toBe(63);
  });

  it('Queso: 33g grasas, 1.3g carbohidratos, 25g proteínas = 402 kcal', () => {
    const queso = ejemplosAlimentos[8];
    expect(calculateCalories(queso.grasas, queso.carbohidratos, queso.proteinas)).toBe(402);
  });
});

describe('Validación de tipo de alimento', () => {
  it('Alimento válido: Manzana con todos los campos correctos', () => {
    const manzana = ejemplosAlimentos[0];
    expect(validateAlimento(manzana)).toBe(true);
  });

  it('Alimento inválido: falta el campo nombre', () => {
    const invalido = { id: 1, categoria: 'Fruta', grasas: 0, carbohidratos: 0, proteinas: 0, calorias: 0 };
    expect(validateAlimento(invalido)).toBe(false);
  });

  it('Alimento inválido: categoría no válida', () => {
    const invalido = { ...ejemplosAlimentos[0], categoria: 'Dulces' };
    expect(validateAlimento(invalido)).toBe(false);
  });
});
