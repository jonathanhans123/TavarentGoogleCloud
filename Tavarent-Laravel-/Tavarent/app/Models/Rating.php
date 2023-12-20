<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class Rating extends Model
{
    use HasFactory;
    protected $table = "rating";
    protected $primaryKey = "id";
    public $incrementing = true;
    protected $fillable = ["rating","komen","id_penginap","id_penginapan"];
    public $timestamps = true;

    public function Penginap()
    {
        return $this->belongsTo(Penginap::class,'id_penginap','id');
    }
    public function Penginapan()
    {
        return $this->belongsTo(Penginapan::class,'id_penginapan','id');
    }
}
