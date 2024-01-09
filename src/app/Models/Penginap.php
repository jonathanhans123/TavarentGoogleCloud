<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;
use Illuminate\Database\Eloquent\SoftDeletes;

class Penginap extends Model
{
    use HasFactory;
    use SoftDeletes;
    protected $table = "penginap";
    protected $primaryKey = "id";
    public $incrementing = true;
    protected $fillable = ["username","password","nama_lengkap","email","no_telp","saldo"];
    public $timestamps = false;

    public function Pembayaran()
    {
        return $this->hasMany(Pembayaran::class,'id_penginap','id');
    }
    public function Kupon()
    {
        return $this->hasMany(Kupon::class,'id_penginap','id');
    }
    public function Rating()
    {
        return $this->hasMany(Rating::class,'id_penginap','id');
    }
    public function Penginapan()
    {
        return $this->belongsToMany(Penginapan::class,'favorit','id_penginap','id_penginapan')->withPivot("id");
    }
}
