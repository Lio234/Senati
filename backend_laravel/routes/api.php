<?php

use Illuminate\Http\Request;
use Illuminate\Support\Facades\Route;
use Illuminate\Support\Facades\Hash;
use Illuminate\Validation\Rule;
use App\Models\Usuario;

Route::post('/auth/register', function (Request $request) {
    $data = $request->validate([
        'nombre'         => 'required|string|max:100',
        'email'          => 'required|email|max:100|unique:usuario,correo',
        'password'       => 'required|string|min:4|max:255',
        'rol'            => ['required', Rule::in(['ADMINISTRADOR','TRABAJADOR'])],
        'perfil_tecnico' => 'nullable|string|max:50',
    ]);

    $u = Usuario::create([
        'nombre'         => $data['nombre'],
        'correo'         => $data['email'],
        'password'       => Hash::make($data['password']),
        'rol'            => $data['rol'],
        'perfil_tecnico' => $data['perfil_tecnico'] ?? null, // queda NULL si no lo mandas
    ]);

    return response()->json([
        'token' => 'FAKE_TOKEN_FOR_DEV',
        'user'  => [
            'id'    => $u->usuario_id,
            'name'  => $u->nombre,
            'email' => $u->correo,
            'rol'   => $u->rol,
        ],
    ], 201);
});

Route::post('/auth/login', function (Request $request) {
    $request->validate([
        'email'    => 'required|email',
        'password' => 'required',
    ]);

    $u = Usuario::where('correo', $request->input('email'))->first();

    if (!$u || !Hash::check($request->input('password'), $u->password)) {
        return response()->json(['message' => 'Credenciales inválidas'], 401);
    }

    return [
        'token' => 'FAKE_TOKEN_FOR_DEV',
        'user'  => [
            'id'    => $u->usuario_id,
            'name'  => $u->nombre,
            'email' => $u->correo,
            'rol'   => $u->rol,
        ],
    ];
});
use Illuminate\Support\Facades\DB;

Route::get('/_debug', function () {
    try {
        DB::connection()->getPdo(); // ping
        $c = DB::selectOne('select count(*) as c from usuario');
        return ['db' => 'ok', 'usuario_count' => $c->c];
    } catch (\Throwable $e) {
        return response()->json(['db' => 'error', 'msg' => $e->getMessage()], 500);
    }
});
