import { describe, it, expect } from "vitest";
import { add, mayorQue } from "./add.js";

describe ("función add", () => {
    it("debería 3 si sumamos 1 y 2", () => {
        expect(add(1, 2)).toBe(3);
    });

    it.only("debe ser función", () => {
        expect(typeof add).toBe("function");
    })
});

describe("función mayorQue", () => {
    it("debería devolver true si el primer número es mayor que el segundo", () => {
        expect(mayorQue(3, 2)).toBe(true);
    });

    it.only("debe ser función", () => {
        expect(typeof mayorQue).toBe("function");
    })
});