// Feedback de carga (TP: indicador cuando la latencia supera 300 ms).
//  1) Botón de envío: se deshabilita al instante (evita doble envío) y muestra spinner + "Enviando…".
//  2) Navegación (enlaces y formularios): si la respuesta tarda más de 300 ms, muestra la barra de progreso.
(() => {
  const UMBRAL_MS = 300;
  const barra = document.querySelector('[data-progress]');
  let timer;

  const programarBarra = () => {
    clearTimeout(timer);
    timer = setTimeout(() => { if (barra) barra.hidden = false; }, UMBRAL_MS);
  };
  const restaurar = () => {
    clearTimeout(timer);
    if (barra) barra.hidden = true;
    document.querySelectorAll('button[aria-busy="true"]').forEach((b) => {
      b.textContent = b.dataset.textoOriginal || b.textContent;
      b.removeAttribute('aria-busy');
      b.disabled = false;
    });
  };

  document.addEventListener('click', (e) => {
    const a = e.target.closest('a[href]');
    if (!a || e.defaultPrevented || e.button !== 0 || e.metaKey || e.ctrlKey || e.shiftKey || e.altKey) return;
    if ((a.target && a.target !== '_self') || a.hasAttribute('download')) return;
    if (a.origin !== location.origin) return;
    if (a.pathname === location.pathname && a.hash) return;   // ancla interna (ej. "Ir al contenido")
    programarBarra();
  });

  document.addEventListener('submit', (e) => {
    const form = e.target;
    if (!form.checkValidity()) return;
    const boton = form.querySelector('button[type="submit"]');
    if (boton) {
      boton.dataset.textoOriginal = boton.textContent;
      boton.textContent = 'Enviando…';
      boton.setAttribute('aria-busy', 'true');
      boton.disabled = true;
    }
    programarBarra();
  });

  // Si el usuario vuelve con "Atrás" (página en caché), dejamos todo como estaba.
  window.addEventListener('pageshow', restaurar);
})();
