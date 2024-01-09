<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;
use Illuminate\Database\Eloquent\SoftDeletes;


class Pemilik extends Model
{
    use HasFactory;
    use SoftDeletes;

    protected $table = "pemilik";
    protected $primaryKey = "id";
    public $incrementing = true;
    protected $fillable = [
        "username",
        "password",
        "nama_lengkap",
        "email",
        "no_telp",
        "saldo"
    ];
    public $timestamps = false;

    public function Penginapan()
    {
        return $this->hasMany(Penginapan::class,'id_pemilik','id');
    }
    public function Chat()
    {
        return $this->hasMany(Chat::class,'id_pemilik','id');
    }
}
