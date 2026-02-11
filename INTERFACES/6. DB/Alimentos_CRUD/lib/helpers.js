export const placeholderDataUrl = 'data:image/svg+xml;utf8,' + encodeURIComponent('<svg xmlns="http://www.w3.org/2000/svg" width="300" height="200"><rect width="100%" height="100%" fill="#eef6ef"/><text x="50%" y="50%" dominant-baseline="middle" text-anchor="middle" fill="#9aa" font-family="sans-serif" font-size="18">Sin imagen</text></svg>');

export function sanitizeName(name) {
  if (name === undefined || name === null) return '';
  return String(name).trim().toLowerCase().replace(/\s+/g, '_').replace(/[^a-z0-9_\-\.]/g, '');
}

export function calculateCalories(fat, carb, protein) {
  const f = Number(fat) || 0;
  const c = Number(carb) || 0;
  const p = Number(protein) || 0;
  const kcal = (f * 9) + (c * 4) + (p * 4);
  return Math.round(kcal);
}

/**
 * resolveImageUrl(record, existsFn)
 * - record: object that may contain `imagen` field
 * - existsFn: (candidatePath) => boolean, used to determine whether a file exists
 * Returns either a relative `img/xxx` path or the placeholder data url.
 */
export function resolveImageUrl(record, existsFn = () => false) {
  try {
    if (!record) return placeholderDataUrl;
    const imgField = record.imagen || '';
    if (imgField) {
      const normalized = String(imgField).replace(/^\.\//, '');
      if (existsFn(normalized)) {
        if (normalized.startsWith('img/')) return normalized;
        const parts = normalized.split(/[\\/]/);
        const base = parts[parts.length - 1];
        return `img/${base}`;
      }
    }
    return placeholderDataUrl;
  } catch (err) {
    return placeholderDataUrl;
  }
}
