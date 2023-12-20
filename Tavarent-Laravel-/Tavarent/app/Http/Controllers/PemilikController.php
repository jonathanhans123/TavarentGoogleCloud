<?php

namespace App\Http\Controllers;

use App\Models\Chat;
use App\Models\Pemilik;
use App\Models\Penginap;
use Illuminate\Support\Facades\Session;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Storage;
use App\Models\Penginapan;
use App\Models\Pengumuman;
use App\Models\Promo;
use Illuminate\Database\Eloquent\Collection;
use Illuminate\Validation\Rule;

class PemilikController extends Controller
{
    function logoutpemilik(){
        setcookie('cekRawUser','',time()-3600);
        Session::forget('cekuser');
        return redirect('/login');
    }
    function PemilikHome(){
        $param["penginapan"] = Penginapan::where("id_pemilik",'=',Session::get("pemilik")->id)->get();
        return view('pemilik/home',$param);
    }

    function PemilikKelola(){
        return view('pemilik/kelola');
    }
    private function getUserPenginapan(Request $request){
        $users = Penginapan::all();
        $return_data = [];
        foreach( $users as $row)
        {
            $return_data['users'][] =$row->getOriginal();
        }
        return $return_data;
    }
    function doPemilikKelola(Request $request){

        $request->validate([
            "nproperti" => ["required"],
            "alamat" =>["required"],
            "deskripsi" =>["required"],
            "selector" =>["required"],
            "rbJenis" =>["required"],
            "harga" =>["required","numeric","min:1"],
            "koordinat" =>["required"],
            "photo"=>["required"],
            "photo.*"=>["mimes:jpg"]
        ],[
            "nproperti" => "Nama harus di isi",
            "alamat" => "Alamat harus di isi",
            "deskripsi" => "Harus ada deskripsi tambahan",
            "selector" => "Harus memilih jenis kelamin ",
            "rbJenis" => "Harus memilih tipe",
            "harga" => "Harus ada harga",
            "koordinat" => "Harus memilih alamat dari autosuggest",
        ]);
        $totalimage = 0;
        foreach($request->file('photo') as $photo){
            $totalimage++;
        }
        $list = "";
        $id_pemilik=Session::get('pemilik')->id;
        if($request->ac != null){
            $list=$list."Air Conditioner,";
        }if($request->termasuklistrik != null){
            $list=$list."Termasuk Listrik,";
        }if($request->kdalam != null){
            $list=$list."K. Mandi Dalam,";
        }if($request->kursi != null){
            $list=$list."Kursi,";
        }if($request->meja != null){
            $list=$list."Meja,";
        }if($request->wifi != null){
            $list=$list."Wifi,";
        }if($request->kasurdouble != null){
            $list=$list."Kasur Double,";
        }if($request->tv != null){
            $list=$list."Tv,";
        }if($request->kasursingle != null){
            $list=$list."Kasur Single,";
        }if($request->jendela != null){
            $list=$list."Jendela,";
        }if($request->airpanas != null){
            $list=$list."Air Panas,";
        }if($request->dapur != null){
            $list=$list."Dapur,";
        }
        Penginapan::create(array(
            "nama" =>$request->nproperti,
            "alamat" =>$request->alamat,
            "deskripsi" =>$request->deskripsi,
            "fasilitas" =>$list,
            "jk_boleh" =>$request->selector,
            "tipe" =>$request->rbJenis,
            "harga" =>$request->harga,
            "koordinat" =>$request->koordinat,
            "jumlah_foto" =>$totalimage,
            "id_pemilik" =>$id_pemilik,
        ));

        $default=0;
        $dataPemilik = $this->getUserPenginapan($request);
        foreach( $dataPemilik['users'] as $row){
            $default=$row['id'];
        }
        $value =1 ;
        foreach($request->file('photo') as $photo){
            $path = $photo->storeAs("imagesPenginapan",$default.'_'.$value.'.jpg',"public");
            $value++;
        }
        return redirect()->back()->with("success","Berhasil Tambah Penginapan");
    }
    public function PemilikChatPenyewa(Request $request)
    {
        $param = [];
        $param["penginap"] = Penginap::find($request->id);
        $param["chat"] = Chat::where("chat.id_penginap","=",$request->id)
        ->where("chat.id_pemilik","=",Session::get("pemilik")->id)->get();
        $param["semuachat"] = Chat::where("chat.id_pemilik","=",Session::get("pemilik")->id)->get();
        return view('pemilik.chat',$param);
    }
    public function sendchat(Request $request)
    {
        $chat = Chat::create(array(
            "pesan" => $request->chat,
            "id_penginap" => $request->id,
            "id_pemilik" => Session::get("pemilik")->id,
            "sender" => "penyewa",
            "status" => "",
        ));

        return redirect()->back();
    }
    public function PemilikProfil(Request $request)
    {
        $param = [];

        return view('pemilik.profil',$param);
    }
    public function updatePemilik(Request $request)
    {
        Validator([
            "email" => [
                "required",
                "email",
                Rule::unique('App\Models\Pemilik','email')->ignore(Session::get("pemilik")->id,'id'),
                Rule::unique('App\Models\Penginap,email')
            ],
            "username" => [
                "required",
                Rule::unique('App\Models\Pemilik','username')->ignore(Session::get("pemilik")->id,'id'),
                Rule::unique('App\Models\Penginap,username')
            ],
            "no_telp" => [
                'numeric','min_digits:10','max_digits:12',
                Rule::unique('App\Models\Pemilik','no_telp')->ignore(Session::get("pemilik")->id,'id'),
                Rule::unique('App\Models\Penginap,no_telp')
            ],
            "password"  => ["required"]
        ]);
        $pemilik = Pemilik::find(Session::get("pemilik")->id)
        ->update([
            "password"=>$request->password,
            "username"=>$request->username,
            "email"=>$request->email,
            "no_telp"=>$request->no_telp,
        ]);
        return redirect()->back()->with("success","Berhasil ganti profil");
    }
    public function PenginapanDetail(Request $request)
    {
        $penginapan = Penginapan::find($request->id);

        $param["penginapan"] = $penginapan;
        $param["photos"] = Storage::disk('public')->files('imagesPenginapan');
        $param["java"] = "<script>start();</script>";

        return view("pemilik.penginapan",$param);
    }
    public function PemilikPromo(Request $request)
    {
        $param = [];
        $penginapan = Pemilik::find(Session::get("pemilik")->id)
        ->Penginapan->all();
        $promo = new Collection();
        foreach($penginapan as $p){
            $promo = $promo->merge(Promo::where("id_penginapan","=",$p->id)->get());
        }
        $param["promo"] = $promo;
        $param["penginapan"] = Pemilik::find(Session::get("pemilik")->id)
        ->Penginapan->all();
        $param["java"] = "<script>start();</script>";
        return view("pemilik.promo",$param);
    }
    public function doPemilikPromo(Request $request)
    {
        $request->validate([
            "id_penginapan" => ["unique:promo,id_penginapan"],
        ]);
        $promo = Promo::create(array(
            "nama" => $request->nama,
            "jenis" => $request->jenis,
            "jumlah" => $request->jumlah,
            "tanggal_mulai" => $request->tanggal_mulai,
            "tanggal_selesai" => $request->tanggal_selesai,
            "id_penginapan" => $request->id_penginapan
        ));
        return redirect()->back();
    }
    public function deletePromo(Request $request)
    {
        $promo = Promo::find($request->id)->delete();
        return redirect()->back();
    }
    public function PemilikNotifikasi(Request $request)
    {
        $param = [];
        $param["pengumuman"] = Pengumuman::where("tipe","=",1)->get();

        return view("penyewa.notifikasi",$param);
    }
}
