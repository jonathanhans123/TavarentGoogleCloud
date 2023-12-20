<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class Kupon extends Model
{
    use HasFactory;
    protected $table = "kupon";
    protected $primaryKey = "id";
    public $incrementing = true;
    protected $fillable = ["nama","jenis","jumlah","tanggal_mulai","tanggal_selesai"];
    public $timestamps = false;

    public function Pembayaran()
    {
        return $this->hasMany(Pembayaran::class,'id_kupon','id');
    }
    public function Penginap()
    {
        return $this->belongsToMany(Penginap::class,'kupon_penginap','id_kupon','id_penginap');
    }
}
