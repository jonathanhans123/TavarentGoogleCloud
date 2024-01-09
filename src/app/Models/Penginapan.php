<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\SoftDeletes;
use Illuminate\Database\Eloquent\Model;

class Penginapan extends Model
{
    use HasFactory;
    use SoftDeletes;
    protected $table = "penginapan";
    protected $primaryKey = "id";
    public $incrementing = true;
    protected $fillable = [
        "nama",
        "alamat",
        "deskripsi",
        "fasilitas",
        "jk_boleh",
        "tipe",
        "harga",
        "koordinat",
        "jumlah_foto",
        "id_pemilik"];
    public $timestamps = false;

    public function Promo()
    {
        return $this->hasMany(Promo::class,'id_penginapan','id');
    }

    public function Pemilik()
    {
        return $this->belongsTo(Pemilik::class,'id_pemilik','id');
    }

    public function Rating()
    {
        return $this->hasMany(Rating::class,'id_rating','id');
    }

    public function Penginap()
    {
        return $this->belongsToMany(Penginap::class,'favorit','id_penginapan','id_penginap');
    }

}
