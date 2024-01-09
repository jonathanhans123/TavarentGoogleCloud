<?php

namespace App\Http\Controllers;

use App\Models\Penginap;
use Illuminate\Http\Request;
use App\Models\Penginapan;
use App\Models\Chat;
use App\Models\Pembayaran;
use App\Models\Pemilik;
use App\Models\Pengumuman;
use Illuminate\Support\Facades\DB;
use Illuminate\Support\Facades\Session;
use Illuminate\Support\Facades\Storage;
use Illuminate\Validation\Rule;

class PenyewaController extends Controller
{
    public function PenyewaHome(Request $request)
    {
        $param = [];
        $penginapan = Penginapan::all();

        $param["penginapan"] = $penginapan;
        $param["photos"] = Storage::disk('public')->files('imagesPenginapan');
        return view('penyewa.cari',$param);
    }
    public function PenyewaSearch(Request $request)
    {
        $param = [];
        $penginapan = Penginapan::all();

        if ($request->lat!=""&&$request->lng!=""){
            $penginapan = Penginapan::selectRaw("*,ST_Distance_Sphere(
                POINT( $request->lng, $request->lat),
                POINT(SUBSTRING(koordinat,POSITION(',' IN koordinat)+1,LENGTH(koordinat)-POSITION(',' IN koordinat)+1),SUBSTR(koordinat,1,POSITION(',' IN koordinat)-1)))  AS 'distance'")
            ->whereRaw(DB::raw("ST_Distance_Sphere(
                POINT( $request->lng, $request->lat),
                POINT(SUBSTRING(koordinat,POSITION(',' IN koordinat)+1,LENGTH(koordinat)-POSITION(',' IN koordinat)+1),SUBSTR(koordinat,1,POSITION(',' IN koordinat)-1)))<=10000"))
             ->get();
        }

        $param["penginapan"] = $penginapan;
        $param["lat"] = $request->lat;
        $param["lng"] = $request->lng;
        $param["alamat"] = $request->alamat;
        $param["java"] = "<script>start();</script>";
        return view('penyewa.search',$param);
    }
    public function PenginapanDetail(Request $request)
    {
        $penginapan = Penginapan::find($request->id);

        $param["penginapan"] = $penginapan;
        $param["photos"] = Storage::disk('public')->files('imagesPenginapan');
        $param["java"] = "<script>start();</script>";
        $param["fav"] = (int)count(Penginap::find(Session::get("penyewa")->id)
        ->Penginapan()
        ->where("penginapan.id","=",$request->id)
        ->get());
        return view("penyewa.penginapan",$param);
    }
    public function ToggleFavorit(Request $request)
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
        return redirect()->back();
    }
    public function PenyewaFavorit()
    {
        $param = [];
        $param["penginapan"] = Penginap::find(Session::get("penyewa")->id)->Penginapan()->get();

        return view("penyewa.favorit",$param);
    }

    public function PenyewaChatPemilik(Request $request)
    {
        $param = [];
        $param["pemilik"] = Pemilik::find($request->id);
        $param["chat"] = Chat::where("chat.id_penginap","=",Session::get("penyewa")->id)
        ->where("chat.id_pemilik","=",$request->id)->get();
        $param["semuachat"] = Chat::where("chat.id_penginap","=",Session::get("penyewa")->id)->get();
        return view('penyewa.chat',$param);
    }
    public function sendchat(Request $request)
    {
        $chat = Chat::create(array(
            "pesan" => $request->chat,
            "id_penginap" => Session::get("penyewa")->id,
            "id_pemilik" => $request->id,
            "sender" => "penginap",
            "status" => "",
        ));

        return redirect()->back();
    }
    public function PenyewaProfil(Request $request)
    {
        $param = [];

        return view('penyewa.profil',$param);
    }
    public function updatePenyewa(Request $request)
    {
        Validator([
            "email" => [
                "required",
                "email",
                Rule::unique('App\Models\Penginap','email')->ignore(Session::get("penyewa")->id,'id'),
                Rule::unique('App\Models\Pemilik,email')
            ],
            "username" => [
                "required",
                Rule::unique('App\Models\Penginap','username')->ignore(Session::get("penyewa")->id,'id'),
                Rule::unique('App\Models\Pemilik,username')
            ],
            "no_telp" => [
                'numeric','min_digits:10','max_digits:12',
                Rule::unique('App\Models\Penginap','no_telp')->ignore(Session::get("penyewa")->id,'id'),
                Rule::unique('App\Models\Pemilik,no_telp')
            ],
            "password"  => ["required"]
        ]);
        $penginap = Penginap::find(Session::get("penyewa")->id)
        ->update([
            "password"=>$request->password,
            "username"=>$request->username,
            "email"=>$request->email,
            "no_telp"=>$request->no_telp,
        ]);
        return redirect()->back()->with("success","Berhasil ganti profil");
    }
    public function doPenginapanDetail(Request $request)
    {
        $request->validate([
            "date" => ["required"],
            "bulan" => ["required"],
        ]);
        $param = [];
        $param["harga"] = $request->hargaakhir*$request->bulan;
        $param["tanggal_mulai"] = $request->date;
        $param["bulan"] = $request->bulan;
        $param["id_penginap"] = Session::get("penyewa")->id;
        $param["id_penginapan"] = $request->id_penginapan;

        
        return redirect()->route("pembayaran",$param);
    }
    public function Pembayaran(Request $request)
    {
        $param = [];
        $param["order_id"] = "TESTTAVARENT0";
        if (Pembayaran::max("id")==null){
            $param["order_id"] = $param["order_id"]. "1";
        }else{
            $param["order_id"] = $param["order_id"].(Pembayaran::max("id")+1);
        }
        $param["gross_amount"] = $request->harga;
        $param["first_name"] = substr(Session::get("penyewa")->nama_lengkap,0,strpos(Session::get("penyewa")->nama_lengkap,' '));
        $param["last_name"] = substr(Session::get("penyewa")->nama_lengkap,strpos(Session::get("penyewa")->nama_lengkap,' '),strlen(Session::get("penyewa")->nama_lengkap)-1);;
        $param["email"] = Session::get("penyewa")->email;
        $param["phone"] = Session::get("penyewa")->no_telp;
        $param["tanggal_mulai"] = $request->tanggal_mulai;
        $param["bulan"] = $request->bulan;
        $param["id_penginapan"] = $request->id_penginapan;
        $param["tanggal_selesai"] = date("Y-m-d",strtotime("+".$request->bulan." months",strtotime($request->tanggal_selesai)));

        $param["java"] = "<script>start();</script>";

        return view("penyewa.payment",$param);
    }
    public function insertPembayaran(Request $request)
    {
        $tanggalselesai = date("Y-m-d",strtotime("+".$request->bulan." months",strtotime($request->tanggal_mulai)));

        $pembayaran = Pembayaran::create(array(
            "total" => $request->total,
            "tanggal_mulai" => $request->tanggal_mulai,
            "tanggal_selesai" => $tanggalselesai,
            "id_penginap" => Session::get("penyewa")->id,
            "id_penginapan" => $request->id_penginapan
        ));
        return redirect("/penyewa");
    }
    public function PenyewaKosSaya(Request $request)
    {
        $param = [];
        $pembayaran = Pembayaran::where("id_penginap","=",Session::get("penyewa")->id)->get();
        $param["pembayaran"] = $pembayaran;
        return view("penyewa.kossaya",$param);
    }
    public function PenyewaNotifikasi(Request $request)
    {
        $param = [];
        $param["pengumuman"] = Pengumuman::where("tipe","=",0)->get();

        return view("penyewa.notifikasi",$param);
    }
}

