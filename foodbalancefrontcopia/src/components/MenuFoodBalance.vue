<template>
  <div id="app">
    <div id="menu">
        <div style="margin-top: 0%;" class="menu-contenedor">
            <div class="menu" @mouseover="abrirMenu" @mouseout="cerrarMenu">
            <ul class="menu-items">
                <a href="/">
                    <li>
                        <img src="../Imagenes/Home.png" alt="Menu Icon" class="menu-icon">
                        <span class="menu-text" style="margin-left: 7%;"><b>Home</b></span>
                    </li>
                </a>
                <a href="/comidaFoodBalance">
                    <li>
                        <img src="../Imagenes/IconoComida.png" alt="Image 1" class="menu-icon">
                        <span class="menu-text" style="margin-left: 5%;"><b>Comida</b></span>
                    </li>
                </a>
                <a href="/pesoFoodBalance">
                    <li>
                        <img src="../Imagenes/IconoPeso.png" alt="Image 2" class="menu-icon">
                        <span class="menu-text" style="margin-left: 5%;"><b>Peso</b></span>
                    </li>
                </a>
                <a href="/estadisticasFoodBalance">
                    <li>
                        <img src="../Imagenes/IconoEstadisticas.png" alt="Image 3" class="menu-icon">
                        <span class="menu-text" style="margin-left: 5%;"><b>Estadisticas</b></span>
                    </li>
                </a>
            </ul>
            </div>
        </div>
    </div>
  </div>
</template>
  
<script>
  
export default {
    data() {
        return {

        };
    },
    methods:{
      abrirMenu() {
        var menuContenedor = document.querySelector('.menu-contenedor');
        var menuTexto = menuContenedor.querySelectorAll('.menu-text');
          
        menuContenedor.style.width = '250px'; 
          
        menuTexto.forEach(function (text) {
          text.style.display = 'inline';
        });
      },
          
      cerrarMenu() {
        var menuContenedor = document.querySelector('.menu-contenedor');
        var menuTexto = menuContenedor.querySelectorAll('.menu-text');
          
        menuContenedor.style.width = '100px'; 
          
        menuTexto.forEach(function (text) {
          text.style.display = 'none';
        });
      }
    }};
</script>
  
<style scoped>
  
</style>