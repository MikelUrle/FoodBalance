<script>
import MenuFoodBalance from "../components/MenuFoodBalance.vue";
import HeaderFoodBalance from "../components/HeaderFoodBalance.vue";

export default {
    data() {
        return {
          eleccion: '',
          cantidadG: '',
          textareaContent: '',
          textareaContentTotal: '',
          datos: [],
          comida: [],
          datosCategorias: [],
          datoCalculo: [],
          calculo: [],
          restaCalculo: [],
          total: [],
        };
    },
    components: {
      MenuFoodBalance,
      HeaderFoodBalance
    },
    methods:{
      async sacarCategorias(){

        try {

          const response = await fetch('http://localhost/MikelUrle/foodbalanceback/public/api/CategoriasComida', {
              method: 'GET',
              mode: 'cors'
          });

          const data = await response.json();
          console.log(data);

          this.datosCategorias = [];

          for (let i = 0; i < data.length; i++) {
              this.datosCategorias.push(data[i]);
          }


        } catch (error) {
          console.error('Error al obtener los datos:', error);
        }
      },

      async fetchData(){

        try {
          // el fetch que hace la llamada al back para recoger los datos, usando el metodo 'GET' y el modo 'cors'
          const response = await fetch('http://localhost/MikelUrle/foodbalanceback/public/api/Comida', {
              method: 'GET',
              mode: 'cors'
          });

          const data = await response.json();
          console.log(data);

          this.datos = [];
          this.comida = [];

          for (let i = 0; i < data.length; i++) {
              this.datos.push({
                  "id": data[i].id,
                  "Nombre": data[i].Nombre,
                  "Calorias": data[i].Calorias,
                  "Proteina": data[i].Proteina,
                  "Grasa": data[i].Grasa,
                  "CH": data[i].CH,
                  "Fibra": data[i].Fibra,
                  "Extra": data[i].Extra,
                  "Categoria": data[i].Categoria
              });
          }

          this.comida = this.datos;

        } catch (error) {
          console.error('Error al obtener los datos:', error);
        }
      },

      filtroCategorias(){
        this.comida = [];
        for (let index = 0; index < this.datos.length; index++) {
          if (this.datos[index].Categoria == this.selectedOption) {
            this.comida.push(this.datos[index]);
          }
        }
        console.log(this.comida);
      },

      cambioEleccion(texto){
        this.eleccion = texto;
      },

      calculoDeEleccion(dato, cantidad){

        var multiplicador;
        multiplicador = cantidad/100;


        for (let index = 0; index < this.datos.length; index++) {
          if (this.datos[index].Nombre==dato) {
            this.datoCalculo = this.datos[index];
          }
        }

        this.datoCalculo.CH = Math.ceil(this.datoCalculo.CH * multiplicador);
        this.datoCalculo.Calorias = Math.ceil(this.datoCalculo.Calorias * multiplicador);
        this.datoCalculo.Fibra = Math.ceil(this.datoCalculo.Fibra * multiplicador);
        this.datoCalculo.Grasa = Math.ceil(this.datoCalculo.Grasa * multiplicador);
        this.datoCalculo.Proteina = Math.ceil(this.datoCalculo.Proteina * multiplicador);

        this.calculo.push(this.datoCalculo);


        let content = '';
        this.calculo.forEach((item, index) => {
          Object.entries(item).forEach(([key, value]) => {
            if (key != "id" && key != "Categoria") {
              content += `${key}: ${value}\n`;
            }

          });
          if (index < this.calculo.length - 1) {
            content += '----------------------------------------------------\n';
          }
        });
        this.textareaContent = content.trim();


        this.total = [
          {
            CH: 0,
            Calorias: 0,
            Fibra: 0,
            Grasa: 0,
            Proteina: 0
          }
        ];

        for (let index = 0; index < this.calculo.length; index++) {
          
          this.total[0].CH += this.calculo[index].CH;
          this.total[0].Calorias += this.calculo[index].Calorias;
          this.total[0].Fibra += this.calculo[index].Fibra;
          this.total[0].Grasa += this.calculo[index].Grasa;
          this.total[0].Proteina += this.calculo[index].Proteina;
          
        }

        let contentTotal = '';
        this.total.forEach((item) => {
          Object.entries(item).forEach(([key, value]) => {
            if (key != "id" && key != "Categoria" && key != "Nombre") {
              contentTotal += `${key}: ${value}\n`;
            }

          });
          this.textareaContentTotal = contentTotal.trim();
        });
      },
      validarNumeros(event) {
        const input = event.target.value;

        const numeros = input.replace(/[^0-9]/g, '');
        event.target.value = numeros;
      },

      botonBorrarTodo(){
        window.location.reload();
      },

      botonBorrarUltimo(){

        this.restaCalculo = [];

        if (this.calculo.length >= 1) {
          for (let index = 0; index < (this.calculo.length-1); index++) {
            this.restaCalculo.push(this.calculo[index]);
          }
        } else {
          this.calculo = [];
          this.textareaContent = "";
        }


        this.calculo = this.restaCalculo;


        let contentNew = '';
        this.calculo.forEach((item, index) => {
          Object.entries(item).forEach(([key, value]) => {
            if (key != "id" && key != "Categoria") {
              contentNew += `${key}: ${value}\n`;
            }

          });
          if (index < this.calculo.length - 1) {
            contentNew += '----------------------------------------------------\n';
          }
        });
        this.textareaContent = contentNew.trim();

        this.total = [
          {
            CH: 0,
            Calorias: 0,
            Fibra: 0,
            Grasa: 0,
            Proteina: 0
          }
        ];

        for (let index = 0; index < this.calculo.length; index++) {
          
          this.total[0].CH += this.calculo[index].CH;
          this.total[0].Calorias += this.calculo[index].Calorias;
          this.total[0].Fibra += this.calculo[index].Fibra;
          this.total[0].Grasa += this.calculo[index].Grasa;
          this.total[0].Proteina += this.calculo[index].Proteina;
          
        }

        this.textareaContentTotal = "";
        if (this.total[0].Calorias == 0) {
          this.textareaContentTotal = "";
        } else {
        let contentTotal = '';
        this.total.forEach((item) => {
          Object.entries(item).forEach(([key, value]) => {
            if (key != "id" && key != "Categoria" && key != "Nombre") {
              contentTotal += `${key}: ${value}\n`;
            }

          });
          this.textareaContentTotal = contentTotal.trim();
        });
        }
      },

    },
    mounted: function() {
      this.fetchData();
      this.sacarCategorias();
    }};
</script>

<template>
  <MenuFoodBalance />
  <HeaderFoodBalance />
  <div id="medioHome">
    <select class="form-select" aria-label="Default select example" id="selectorHomeCategoria" v-model="selectedOption" @change="filtroCategorias">
      <option v-for="(categoria, index) in datosCategorias" :key="index" :value="categoria">
        {{ categoria }}
      </option>
    </select>
    

    <input type="text" class="form-control" placeholder="Nombre del Alimento" aria-label="Recipient's username" aria-describedby="basic-addon2" id="eleccionHome" v-model="eleccion" readonly>

    <input type="text" class="form-control" placeholder="Gramos" aria-label="Recipient's username" aria-describedby="basic-addon2" id="gramosHome" v-model="cantidadG" @input="validarNumeros">
  
    <button type="button" class="btn btn" id="botonAñadirHome" @click="calculoDeEleccion(this.eleccion, cantidadG)">Añadir</button>

    <table class="table table-hover" id="tablaHome">
      <thead>
        <tr>
          <th scope="col"></th>
          <th scope="col">Selecciona una Comida</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="(dato, index) in comida" :key="index" @click="cambioEleccion(dato.Nombre)">
          <th scope="row">{{ index + 1 }}</th>
          <td>{{ dato.Nombre }}</td>
        </tr>
      </tbody>
    </table>

    <div class="form-floating" id="textAreaHome">
      <textarea class="form-control" placeholder="Leave a comment here" id="floatingTextarea2" v-model="textareaContent" style="height: 300px" readonly></textarea>
    </div>

    <button type="button" class="btn btn" id="botonQuitarHome" @click="botonBorrarUltimo">
      <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" fill="currentColor" class="bi bi-file-minus" viewBox="0 0 16 16">
        <path d="M5.5 8a.5.5 0 0 1 .5-.5h4a.5.5 0 0 1 0 1H6a.5.5 0 0 1-.5-.5"/>
        <path d="M4 0a2 2 0 0 0-2 2v12a2 2 0 0 0 2 2h8a2 2 0 0 0 2-2V2a2 2 0 0 0-2-2zm0 1h8a1 1 0 0 1 1 1v12a1 1 0 0 1-1 1H4a1 1 0 0 1-1-1V2a1 1 0 0 1 1-1"/>
      </svg>  
    </button>

    <button type="button" class="btn btn" id="botonQuitarTodoHome" @click="botonBorrarTodo">
      <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" fill="currentColor" class="bi bi-trash3-fill" viewBox="0 0 16 16">
        <path d="M11 1.5v1h3.5a.5.5 0 0 1 0 1h-.538l-.853 10.66A2 2 0 0 1 11.115 16h-6.23a2 2 0 0 1-1.994-1.84L2.038 3.5H1.5a.5.5 0 0 1 0-1H5v-1A1.5 1.5 0 0 1 6.5 0h3A1.5 1.5 0 0 1 11 1.5m-5 0v1h4v-1a.5.5 0 0 0-.5-.5h-3a.5.5 0 0 0-.5.5M4.5 5.029l.5 8.5a.5.5 0 1 0 .998-.06l-.5-8.5a.5.5 0 1 0-.998.06m6.53-.528a.5.5 0 0 0-.528.47l-.5 8.5a.5.5 0 0 0 .998.058l.5-8.5a.5.5 0 0 0-.47-.528M8 4.5a.5.5 0 0 0-.5.5v8.5a.5.5 0 0 0 1 0V5a.5.5 0 0 0-.5-.5"/>
      </svg>
    </button>

    <div class="form-floating" id="textTotalHome">
      <textarea class="form-control" placeholder="Leave a comment here" id="floatingTextarea2" v-model="textareaContentTotal" style="height: 140px" readonly></textarea>
    </div>

  </div>

  

</template>