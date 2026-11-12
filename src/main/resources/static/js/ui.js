// Comportamientos de interfaz compartidos (sin dependencias). Cada uno se activa con atributos data-*:
//
//  1) data-confirmar="¿Seguro?"            en un botón/enlace: pide confirmación antes de actuar.
//  2) data-mostrar-si="nombre:VAL1|VAL2"   en un bloque: se muestra solo si el campo `nombre` (select, radio o checkbox)
//                                          vale alguno de esos valores. Si está oculto, sus campos se deshabilitan
//                                          (no se validan ni se envían).
//  3) data-agregar="idPlantilla" data-destino="idContenedor"   en un botón: clona una <template> (filas repetibles).
//     data-quitar                                              en un botón dentro de una fila [data-fila]: la elimina.
//     La plantilla usa __N__ como índice; los nombres quedan consecutivos (bienes[0].x, bienes[1].x…) para el binding.
(() => {
  // 1) Confirmación
  document.addEventListener('click', (e) => {
    const el = e.target.closest('[data-confirmar]');
    if (el && !window.confirm(el.dataset.confirmar)) { e.preventDefault(); e.stopPropagation(); }
  }, true);

  // 2) Campos condicionales
  const valorActual = (nombre) => {
    let actual = '';
    document.querySelectorAll(`[name="${CSS.escape(nombre)}"]`).forEach((c) => {
      if (c.type === 'radio' || c.type === 'checkbox') { if (c.checked) actual = c.value; } else { actual = c.value; }
    });
    return actual;
  };
  const aplicar = (bloque) => {
    const [nombre, valores = ''] = bloque.dataset.mostrarSi.split(':');
    const visible = valores.split('|').includes(valorActual(nombre));
    bloque.hidden = !visible;
    bloque.querySelectorAll('input, select, textarea').forEach((campo) => { campo.disabled = !visible; });
  };
  const aplicarTodos = (raiz = document) => raiz.querySelectorAll('[data-mostrar-si]').forEach(aplicar);
  document.addEventListener('change', () => aplicarTodos());
  document.addEventListener('DOMContentLoaded', () => aplicarTodos());

  // 3) Filas repetibles
  const reindexar = (contenedor) => {
    const filas = contenedor.querySelectorAll('[data-fila]');
    filas.forEach((fila, i) => {
      const viejo = fila.dataset.indice;
      if (viejo !== String(i)) {
        fila.querySelectorAll('*').forEach((el) => {
          ['name', 'id', 'for', 'data-mostrar-si', 'aria-describedby'].forEach((a) => {
            const v = el.getAttribute(a);
            if (v) el.setAttribute(a, v.split(`[${viejo}]`).join(`[${i}]`).split(`-${viejo}-`).join(`-${i}-`));
          });
        });
        fila.dataset.indice = i;
      }
      const numero = fila.querySelector('[data-numero]');
      if (numero) numero.textContent = i + 1;
      const quitar = fila.querySelector('[data-quitar]');
      if (quitar) quitar.hidden = filas.length === 1;
    });
  };
  document.addEventListener('click', (e) => {
    const agregar = e.target.closest('[data-agregar]');
    if (agregar) {
      const plantilla = document.getElementById(agregar.dataset.agregar);
      const destino = document.getElementById(agregar.dataset.destino);
      if (!plantilla || !destino) return;
      const n = destino.querySelectorAll('[data-fila]').length;
      const caja = document.createElement('div');
      caja.innerHTML = plantilla.innerHTML.split('__N__').join(n);
      const fila = caja.firstElementChild;
      fila.dataset.indice = n;
      destino.appendChild(fila);
      reindexar(destino); aplicarTodos(fila);
      const primero = fila.querySelector('input, select, textarea'); if (primero) primero.focus();
      return;
    }
    const quitar = e.target.closest('[data-quitar]');
    if (quitar) {
      const fila = quitar.closest('[data-fila]'); const destino = fila && fila.parentElement;
      if (destino && destino.querySelectorAll('[data-fila]').length > 1) { fila.remove(); reindexar(destino); }
    }
  });
  document.addEventListener('DOMContentLoaded', () => document.querySelectorAll('[data-fila]').forEach((f) => reindexar(f.parentElement)));
})();
