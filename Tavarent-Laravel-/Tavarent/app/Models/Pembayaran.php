<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class Pembayaran extends Model
{
    use HasFactory;
    protected $table = "pembayaran";
    protected $primaryKey = "id";
    public $incrementing = true;
    protected $fillable = [
        "total",
        "tanggal_mulai",
        "tanggal_selesai",
        "id_penginap",
        "id_penginapan"
    ];
    public $timestamps = false;

    public function Kupon()
    {
        return $this->belongsTo(Kupon::class,'id_kupon','id');
    }
    public function Penginap()
    {
        return $this->belongsTo(Penginap::class,'id_penginap','id');
    }
    public function Penginapan()
    {
        return $this->belongsTo(Penginapan::class,'id_penginapan','id');
    }
    public function Promo()
    {
        return $this->belongsTo(Promo::class,'id_promo','id');
    }
}
