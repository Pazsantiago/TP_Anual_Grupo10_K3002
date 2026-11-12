// Mapa interactivo (Leaflet). Los puntos salen de las filas [data-marcador] de una tabla de la página
// (esa tabla es también la alternativa textual para lectores de pantalla):
//   <tr data-marcador data-clave="AB123CD" data-lat="-34.60" data-lng="-58.38" data-titulo="Camión AB123CD">…</tr>
// Si el contenedor [data-mapa] trae data-url, cada 15 s se piden las ubicaciones actuales de los camiones (JSON) y se mueven los marcadores.
(() => {
  const contenedor = document.querySelector('[data-mapa]');
  if (!contenedor || typeof L === 'undefined') return;

  const mapa = L.map(contenedor, { scrollWheelZoom: false }).setView([-34.6037, -58.3816], 11);   // CABA por defecto
  L.tileLayer('https://tile.openstreetmap.org/{z}/{x}/{y}.png', {
    maxZoom: 19,
    attribution: '&copy; colaboradores de <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a>'
  }).addTo(mapa);

  const marcadores = new Map();
  const contenido = (titulo, detalle) => {               // DOM en vez de HTML: los datos no se interpretan como marcado
    const caja = document.createElement('div');
    const t = document.createElement('strong'); t.textContent = titulo; caja.appendChild(t);
    if (detalle) { const p = document.createElement('p'); p.style.margin = '4px 0 0'; p.textContent = detalle; caja.appendChild(p); }
    return caja;
  };
  const poner = (clave, lat, lng, titulo, detalle) => {
    if (Number.isNaN(lat) || Number.isNaN(lng)) return;
    const existente = marcadores.get(clave);
    if (existente) { existente.setLatLng([lat, lng]).setPopupContent(contenido(titulo, detalle)); return; }
    marcadores.set(clave, L.marker([lat, lng], { title: titulo, alt: titulo }).addTo(mapa).bindPopup(contenido(titulo, detalle)));
  };
  const encuadrar = () => {
    if (marcadores.size) mapa.fitBounds(L.featureGroup([...marcadores.values()]).getBounds().pad(0.2), { maxZoom: 15 });
  };

  document.querySelectorAll('[data-marcador]').forEach((fila, i) => {
    poner(fila.dataset.clave || String(i), parseFloat(fila.dataset.lat), parseFloat(fila.dataset.lng),
          fila.dataset.titulo || 'Punto', fila.dataset.detalle || '');
  });
  encuadrar();

  const url = contenedor.dataset.url;
  if (!url) return;
  const nota = document.querySelector('[data-ultima-actualizacion]');
  setInterval(async () => {
    try {
      const resp = await fetch(url, { headers: { Accept: 'application/json' } });
      if (!resp.ok) return;
      (await resp.json()).forEach((c) => {
        const u = c.ultimaUbicacion;
        if (u && u.latitud != null && u.longitud != null) {
          poner(c.patente, u.latitud, u.longitud, 'Camión ' + c.patente, u.timestamp ? 'Última actualización: ' + u.timestamp : '');
        }
      });
      if (nota) nota.textContent = new Date().toLocaleTimeString('es-AR');
    } catch (_) { /* sin red: se reintenta en el próximo ciclo */ }
  }, 15000);
})();
