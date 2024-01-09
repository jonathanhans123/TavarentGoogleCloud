<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class Chat extends Model
{
    use HasFactory;
    protected $table = "chat";
    protected $primaryKey = "id";
    public $incrementing = true;
    protected $fillable = ["pesan","id_penginap","id_pemilik","sender","status"];
    public $timestamps = true;

    public function Penginap()
    {
        return $this->belongsTo(Penginap::class,"id_penginap","id");
    }
    public function Pemilik()
    {
        return $this->belongsTo(Pemilik::class,"id_pemilik","id");
    }
}
