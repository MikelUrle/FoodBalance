<?php

use Illuminate\Http\Request;
use Illuminate\Support\Facades\Route;
use App\Http\Controllers\ComidaController;


/*
|--------------------------------------------------------------------------
| API Routes
|--------------------------------------------------------------------------
|
| Here is where you can register API routes for your application. These
| routes are loaded by the RouteServiceProvider and all of them will
| be assigned to the "api" middleware group. Make something great!
|
*/

Route::middleware('auth:sanctum')->get('/user', function (Request $request) {
    return $request->user();
});


Route::get('/Comida', [ComidaController::class, 'sacarDatos']);

Route::post('/MeterComida', [ComidaController::class, 'meterComida']);

Route::put('/CambiarComida', [ComidaController::class, 'editarComida']);

Route::delete('/BorrarComida', [ComidaController::class, 'borrarComida']);

Route::get('/CategoriasComida', [ComidaController::class, 'categoriaComida']);
