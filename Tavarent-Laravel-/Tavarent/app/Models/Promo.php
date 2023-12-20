<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class Promo extends Model
{
    use HasFactory;
    protected $table = "promo";
    protected $primaryKey = "id";
    public $incrementing = true;
    protected $fillable = ["nama","jenis","jumlah","tanggal_mulai","tanggal_selesai","id_penginapan"];
    public $timestamps = false;

    public function Pembayaran()
    {
        return $this->hasMany(Pembayaran::class,'id_pembayaran','id');
    }
    public function Penginapan()    
    {
        return $this->belongsTo(Promo::class,'id_penginapan','id');
    }
}
