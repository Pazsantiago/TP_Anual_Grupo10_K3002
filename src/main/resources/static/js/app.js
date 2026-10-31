// Indicador de carga: al enviar un formulario, deshabilita el botón y avisa que está trabajando.
document.addEventListener('submit', (e) => {
  const boton = e.target.querySelector('button[type="submit"]');
  if (!boton || !e.target.checkValidity()) return;
  boton.dataset.textoOriginal = boton.textContent;
  boton.textContent = 'Enviando…';
  boton.setAttribute('aria-busy', 'true');
  boton.disabled = true;
});

// Si el usuario vuelve con "Atrás", restauramos el botón.
window.addEventListener('pageshow', () => {
  document.querySelectorAll('button[aria-busy="true"]').forEach((b) => {
    b.textContent = b.dataset.textoOriginal || b.textContent;
    b.removeAttribute('aria-busy');
    b.disabled = false;
  });
});
