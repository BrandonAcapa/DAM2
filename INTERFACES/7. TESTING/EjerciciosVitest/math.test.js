import {vi, expect, it, describe} from 'vitest';
import { duplicarYSumar, generarNumero } from './operaciones.js';

describe('duplicarYSumar', () => {
    it('debería duplicar el número por 2 y sumar 5', () => {
        expect(duplicarYSumar(3)).toBe(11); // (3 * 2) + 5 = 11
    });

    it.only('debería funcionar con números negativos', () => {
        expect(duplicarYSumar(-4)).toBe(-3); // (-4 * 2) + 5 = -3
    });
});

describe('generarNumero', () => {
    it('debería generar un número entre 0 y 10', () => {
        const numero = generarNumero();
        expect(numero).toBeGreaterThanOrEqual(0);
        expect(numero).toBeLessThan(11);
    });
});