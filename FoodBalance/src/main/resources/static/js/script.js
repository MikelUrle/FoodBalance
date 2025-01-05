function abrirMenu() {
  var menuContenedor = document.querySelector('.menu-contenedor');
  var menuTexto = menuContenedor.querySelectorAll('.menu-text');
    
  menuContenedor.style.width = '250px'; 
    
  menuTexto.forEach(function (text) {
    text.style.display = 'inline';
  });
}
    
function cerrarMenu() {
  var menuContenedor = document.querySelector('.menu-contenedor');
  var menuTexto = menuContenedor.querySelectorAll('.menu-text');
    
  menuContenedor.style.width = '115px'; 
    
  menuTexto.forEach(function (text) {
    text.style.display = 'none';
  });
}
