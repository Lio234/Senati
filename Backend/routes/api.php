<?php

use Illuminate\Http\Request;
use Illuminate\Support\Facades\Route;

Route::post('/auth/login', function (Request $r) {
    return response()->json([
        'token' => 'FAKE_TOKEN_FOR_DEV',
        'user'  => ['id'=>1,'name'=>'Leonardo','email'=>$r->input('email')]
    ]);
});
