<?php

namespace App\Http\Controllers\Api;

use App\Http\Controllers\Controller;
use Illuminate\Http\Request;
use App\Models\Penginap;
use App\Models\Penginapan;
use App\Models\Pemilik;
use App\Models\Pengumuman;
use App\Models\Promo;
use App\Models\Kupon;
use App\Models\Rating;
use App\Models\Chat;
use App\Models\Pembayaran;

class DatabaseController extends Controller
{
    public function listuser(Request $request)
    {
        $penginap = Penginap::all();
        $pemilik = Pemilik::all();
        $combine = $penginap->merge($pemilik);
        return response()->json($combine, 200);
    }
    public function listpenginap(Request $request)
    {
        $penginap = Penginap::all();
        return response()->json($penginap, 200);
    }
    public function listpemilik(Request $request)
    {
        $pemilik = Pemilik::all();
        return response()->json($pemilik, 200);
    }
    public function findpemilik(Request $request)
    {
        $pemilik = Pemilik::find($request->id_pemilik);
        return response()->json($pemilik, 201);
    }
    public function findpemilikdaripenginapan(Request $request)
    {
        $pemilik = Penginapan::find($request->id_penginapan)->Pemilik()->get();
        return response()->json($pemilik, 201);
    }
    public function findpenginap(Request $request)
    {
        $penginap = Penginap::find($request->id_penginap);
        return response()->json($penginap, 201);
    }
    public function listpenginapan(Request $request)
    {
        return response()->json(Penginapan::all(), 200);
    }
    public function listpenginapanrating(Request $request)
    {
        $penginapan = Penginapan::all();
        return response()->json($penginapan, 200);
    }
    public function listchatpenginap(Request $request)
    {
        $chat = Chat::
        selectRaw("chat.id as id,chat.pesan as pesan, chat.sender as sender,chat.status as status,
        penginap.id as id_penginap,penginap.username as penginapusername,
        pemilik.id as id_pemilik,pemilik.username as pemilikusername,
        chat.created_at as created_at")
        ->join("penginap","penginap.id","=","chat.id_penginap")
        ->join("pemilik","pemilik.id","=","chat.id_pemilik")
        ->where("chat.id_penginap","=",$request->id_penginap)
        ->get();
        return response()->json($chat, 201);
    }
    public function listpesanchat(Request $request)
    {
        $chat = Chat::
        selectRaw("chat.id as id,chat.pesan as pesan, chat.sender as sender,chat.status as status,
        penginap.id as id_penginap,penginap.username as penginapusername,
        pemilik.id as id_pemilik,pemilik.username as pemilikusername,
        chat.created_at as created_at")
        ->join("penginap","penginap.id","=","chat.id_penginap")
        ->join("pemilik","pemilik.id","=","chat.id_pemilik")
        ->where("chat.id_penginap","=",$request->id_penginap)
        ->where("chat.id_pemilik","=",$request->id_pemilik)
        ->get();
        return response()->json($chat, 201);
    }
    public function insertchat(Request $request)
    {
        $chat = Chat::create(array(
            "pesan" => $request->pesan,
            "id_penginap" => $request->id_penginap,
            "id_pemilik" => $request->id_pemilik,
            "sender" => $request->sender,
            "status" => $request->status,
        ));
        return response()->json($chat, 201);
    }
    public function listchatpemilik(Request $request)
    {
        $chat = Chat::
        selectRaw("chat.id as id,chat.pesan as pesan, chat.sender as sender,chat.status as status,
        penginap.id as id_penginap,penginap.username as penginapusername,
        pemilik.id as id_pemilik,pemilik.username as pemilikusername,
        chat.created_at as created_at")
        ->join("penginap","penginap.id","=","chat.id_penginap")
        ->join("pemilik","pemilik.id","=","chat.id_pemilik")
        ->where("chat.id_pemilik","=",$request->id_pemilik)
        ->get();
        return response()->json($chat, 201);
    }
    public function listpenginapanfavorit(Request $request)
    {
        $penginapan = Penginap::find($request->id_penginap)->Penginapan()->get();
        return response()->json($penginapan, 201);
    }
    public function checkpenginapanfavorit(Request $request)
    {
        $penginapan = Penginap::find($request->id_penginap)->Penginapan()->where("penginapan.id","=",$request->id_penginapan)->get();
        return response()->json($penginapan, 201);
    }
    public function togglepenginapanfavorit(Request $request)
    {
        $exist = "";
        $penginapan = Penginap::find($request->id_penginap)->Penginapan()->where("penginapan.id","=",$request->id_penginapan)->first();
        if ($penginapan==null){
            $p = Penginapan::find($request->id_penginapan);
            $favorit = Penginap::find($request->id_penginap)->Penginapan()->attach($p);
            $exist = "bukanfavorit";
        }else{
            $p = Penginapan::find($request->id_penginapan);
            $favorit = Penginap::find($request->id_penginap)->Penginapan()->detach($p);
        }
        return response()->json($exist, 201);
    }

    public function listkupon(Request $request)
    {
        return response()->json(Kupon::all(), 200);
    }
    public function listpromo(Request $request)
    {
        return response()->json(Promo::all(), 200);
    }
    public function listrating(Request $request)
    {
        return response()->json(Rating::all(), 200);
    }
    public function listpengumuman(Request $request)
    {
        return response()->json(Pengumuman::all(), 200);
    }

    function insertpemilik(Request $request){
        $pemilik = Pemilik::create(array(
            "email" => $request->email,
            "no_telp" => $request->no_telp,
            "username" => $request->username,
            "nama_lengkap" => $request->nama_lengkap,
            "password" => $request->password,
        ));
        return response()->json($pemilik, 201);
    }
    function insertpenginap(Request $request){
        $penginap = Penginap::create(array(
            "email" => $request->email,
            "no_telp" => $request->no_telp,
            "username" => $request->username,
            "nama_lengkap" => $request->nama_lengkap,
            "password" => $request->password,
        ));
        return response()->json($penginap, 201);
    }
    function insertpengumuman(Request $request){
        $pengumuman = Pengumuman::create(array(
            "judul" => $request->judul,
            "isi" => $request->isi,
            "tipe" => $request->tipe,
        ));
        return response()->json($pengumuman, 201);
    }
    function insertpenginapan(Request $request){
        $penginapan = Penginapan::create(array(
            "nama" =>$request->nama,
            "alamat" =>$request->alamat,
            "deskripsi" =>$request->deskripsi,
            "fasilitas" =>$request->fasilitas,
            "jk_boleh" =>$request->jk_boleh,
            "tipe" =>$request->tipe,
            "harga" =>$request->harga,
            "koordinat" =>$request->koordinat,
            "id_pemilik" =>$request->id_pemilik,
        ));


        return response()->json($penginapan, 201);
    }
    function updatepemilik(Request $request){
        $pemilik=Pemilik::find($request->id);
        $pemilik->update([
            "password"=>$request->password,
            "nama_lengkap"=>$request->nama_lengkap,
            "no_telp"=>$request->no_telp,
        ]);
        return response()->json($pemilik, 201);
    }
    function updatepenginap(Request $request){
        $penginap=Penginap::find($request->id);
        $penginap->update([
            "password"=>$request->password,
            "nama_lengkap"=>$request->nama_lengkap,
            "no_telp"=>$request->no_telp,
        ]);
        return response()->json($penginap, 201);
    }
    function tambahsaldopenginap(Request $request){
        $penginap=Penginap::find($request->id);
        $penginap->update([
            "saldo"=>$request->saldo,
        ]);
        return response()->json($penginap, 201);
    }

    public function listpembayaran(Request $request)
    {
        $pembayaran = Pembayaran::find($request->id_penginap)->get();
        return response()->json($pembayaran,201);
    }

    public function listpembayaransemua(Request $request)
    {
        return response()->json(Pembayaran::all(),200);
    }
    public function listpembayaranpenginapan(Request $request)
    {
        $pembayaran = Pembayaran::where("id_penginap","=",$request->id_penginap)
        ->join("penginapan","penginapan.id","=","pembayaran.id_penginapan")
        ->get();
        return response()->json($pembayaran,201);
    }
    public function countpembayaran(Request $request)
    {
        $pembayaran = Pembayaran::selectRaw("count(pembayaran.id) as count")
        ->get();
        return response()->json($pembayaran,201);
    }

    public function insertpembayaran(Request $request)
    {
        $pembayaran = Pembayaran::create(array(
            "total" => $request->total,
            "tanggal_mulai" => $request->tanggal_mulai,
            "tanggal_selesai" => $request->tanggal_selesai,
            "id_penginap" => $request->id_penginap,
            "id_penginapan" => $request->id_penginapan,
            "id_kupon" => $request->id_kupon,
            "id_promo" => $request->id_promo,
        ));
        $pemilik = Penginapan::find($request->id_penginapan)->Pemilik()->first();
        $pemilik->saldo += $request->total;
        $pemilik->save();
        return response()->json($pembayaran,201);
    }
    
}
