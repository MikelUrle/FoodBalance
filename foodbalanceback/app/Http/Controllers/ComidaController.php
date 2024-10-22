<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use App\Models\Comida;

class ComidaController extends Controller
{
    //
    public function sacarDatos(){
        $datosTotal=Comida::all();

        return response()->json($datosTotal);
    }

    public function meterComida(Request $request){
        $datos = $request->json()->all();
        $hoy = date('Y-m-d H:i:s');
        $id = Comida::insertGetId([
            'Nombre' => $datos["Nombre"],
            'Calorias' => $datos["Calorias"],
            'Proteina' => $datos["Proteina"],
            'Grasa' => $datos["Grasa"],
            'CH' => $datos["CH"],
            'Fibra' => $datos["Fibra"],
            'Extra' => $datos["Extra"],
            'Categoria' => $datos["Categoria"]
        ]);
    }

    public function editarComida(Request $request){
        $datos = $request->json()->all();
        
        $emaitza = Comida::where('id', $datos["id"])->update(['Nombre' => $datos['Nombre'], 'Calorias' => $datos['Calorias'],'Proteina' => $datos['Proteina'],'Grasa' => $datos['Grasa'],'CH' => $datos['CH'],'Fibra' => $datos['Fibra'],'Extra' => $datos['Extra'],'Categoria' => $datos['Categoria']]);
       
        if ($emaitza) {
           return response()->json(['message' => 'Datuak aldatu dira.'], 200);
       } else {
           return response()->json(['message' => 'Ezin izan dira datuak aldatu.'], 400);
       }
   }

    public function borrarComida(Request $request){
        $datos = $request->json()->all();
       // Buscar la comida por ID
       $comida = Comida::find($datos["id"]);

       // Verificar si la comida existe
       if (!$comida) {
           return response()->json(['message' => 'Comida no encontrada.'], 404);
       }

       // Eliminar la comida
       $comida->delete();

       // Devolver una respuesta exitosa
       return response()->json(['message' => 'Comida eliminada exitosamente.'], 200);
    }

    public function categoriaComida()
    {
        $categorias = Comida::distinct()
                            ->pluck('categoria');

        return $categorias;
    }

}
