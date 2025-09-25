<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Model;

class Usuario extends Model {
    protected $table = 'usuario';
    protected $primaryKey = 'usuario_id';
    public $timestamps = false;
    protected $fillable = ['nombre','correo','password','rol','perfil_tecnico'];
    protected $hidden = ['password'];
}