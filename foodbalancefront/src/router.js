import { createRouter, createWebHistory } from "vue-router"


import HomeFoodBalance from "./views/HomeFoodBalance.vue"
import ComidaFoodBalance from "./views/ComidaFoodBalance.vue"
import PesoFoodBalance from "./views/PesoFoodBalance.vue"
import EstadisticasFoodBalance from "./views/EstadisticasFoodBalance.vue"

 
const routes = [
    {
        name: 'homeFoodBalance',
        path: '/',
        component: HomeFoodBalance
    },
    {
        name: 'comidaFoodBalance',
        path: '/comidaFoodBalance',
        component: ComidaFoodBalance
    },
    {
        name: 'pesoFoodBalance',
        path: '/pesoFoodBalance',
        component: PesoFoodBalance
    },
    {
        name: 'estadisticasFoodBalance',
        path: '/estadisticasFoodBalance',
        component: EstadisticasFoodBalance
    }
]


const router = createRouter({
    history: createWebHistory(),
    routes: routes
})


export default router