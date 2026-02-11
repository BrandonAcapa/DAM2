import { describe, it, expect } from 'vitest';
import { sanitizeName, calculateCalories, resolveImageUrl, placeholderDataUrl } from '../lib/helpers.js';

describe('helpers.sanitizeName (básicos)', () => {
  it('convierte a minúsculas y reemplaza espacios por guiones bajos', () => {
    expect(sanitizeName('  Manzana Roja ')).toBe('manzana_roja');
  });

  it('devuelve cadena vacía para null/undefined', () => {
    expect(sanitizeName(null)).toBe('');
    expect(sanitizeName(undefined)).toBe('');
  });
});

describe('helpers.calculateCalories (básicos)', () => {
  it('calcula kcal para números simples', () => {
    expect(calculateCalories(1, 0, 0)).toBe(9);
    expect(calculateCalories(0, 1, 0)).toBe(4);
    expect(calculateCalories(0, 0, 1)).toBe(4);
  });
});

describe('helpers.resolveImageUrl (básicos)', () => {
  it('devuelve placeholder si no hay registro', () => {
    expect(resolveImageUrl(null)).toBe(placeholderDataUrl);
  });

  it('devuelve la ruta img/ cuando existsFn indica existencia', () => {
    const rec = { imagen: 'img/apple.png' };
    const existsFn = (p) => p === 'img/apple.png';
    expect(resolveImageUrl(rec, existsFn)).toBe('img/apple.png');
  });
});
