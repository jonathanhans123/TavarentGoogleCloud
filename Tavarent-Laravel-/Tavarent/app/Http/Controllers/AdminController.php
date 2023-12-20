<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use App\Models\Penginap;
use App\Models\Penginapan;
use App\Models\Pemilik;
use App\Models\Pengumuman;
use App\Models\Promo;
use App\Models\Kupon;
use App\Models\Rating;
use App\Models\Pembayaran;
use App\Models\Chat;
use App\Http\Controllers\Controller;
use App\Mail\BeritaMail;
use Illuminate\Support\Facades\DB;
use Illuminate\Support\Facades\Mail;
use Illuminate\Support\Facades\Session;

class AdminController extends Controller
{
    public function testing()
    {
        
    }

    function destroyadmin(){
        setcookie('cekRawUser','',time()-3600);
        Session::forget('cekuser');
        return redirect('login');
    }
    function logoutadmin(){
        return redirect('/closeAdmin');
    }
    function AdminListPenginap(){
        $param = [];
        $penginap = Penginap::withTrashed()->get();

        $param["penginap"] = $penginap;

        return view('Admin.listpenginap',$param);
    }

    public function AdminHapusListPenginap(Request $request){
        $penginap = Penginap::withTrashed()->find($request->id);
        if ($penginap->trashed()){
            $penginap->restore();
            return redirect("admin/listpenginap")->with("pesanSukses", "user telah di unban!");
        } else{
            $penginap->delete();
            return redirect("admin/listpenginap")->with("pesanSukses", "user telah di ban!");
        }
    }

    public function AdminUbahListPenginap(Request $request){
        $param =[];
        $penginap = Penginap::where('id',$request->id)->first();

        $param["penginap"] = $penginap;
        // dd($param);
        return view('Admin.ubahlistpenginap', $param);
    }

    public function AdmindoUbahListPenginap(Request $request){

        $res = Penginap::where('id',$request->id)->update(
            [
                "username"=>$request->username,
                "password"=>$request->password,
                "nama_lengkap"=>$request->nama,
                "email"=>$request->email,
                "no_telp"=>$request->notelp,
                "saldo"=>$request->saldo,
            ]
        );
        if($res){
            return redirect("admin/listpenginap")->with("pesanSukses","Data berhasil diubah");
        }else{
            return redirect("admin/listpenginap")->with("pesanGagal","Data gagal berhasil diubah");
        }
    }

    function AdminListPemilik(){
        $param = [];
        $pemilik = Pemilik::withTrashed()->get();

        $param["pemilik"] = $pemilik;
        return view('Admin.listpemilik',$param);
    }

    public function AdminHapusListPemilik(Request $request){
        $pemilik = Pemilik::withTrashed()->find($request->id);
        if ($pemilik->trashed()){
            $pemilik->restore();
            return redirect("admin/listpemilik")->with("pesanSukses", "user telah di unban!");
        } else{
            $pemilik->delete();
            return redirect("admin/listpemilik")->with("pesanSukses", "user telah di ban!");
        }
    }

    public function AdminUbahListPemilik(Request $request){

        $param =[];
        $pemilik = Pemilik::where('id',$request->id)->first();

        $param["pemilik"] = $pemilik;
        // dd($param);
        return view('Admin.ubahlistpemilik', $param);
    }

    public function AdmindoUbahListPemilik(Request $request){

        $res = Pemilik::where('id',$request->id)->update(
            [
                "username"=>$request->username,
                "password"=>$request->password,
                "nama_lengkap"=>$request->nama,
                "email"=>$request->email,
                "no_telp"=>$request->notelp,
                "saldo"=>$request->saldo,
            ]
        );
        if($res){
            return redirect("admin/listpemilik")->with("pesanSukses","Data berhasil diubah");
        }else{
            return redirect("admin/listpemilik")->with("pesanGagal","Data gagal berhasil diubah");
        }
    }

    function AdminListPenginapan(){
        $param = [];
        $penginapan = Penginapan::withTrashed()->get();

        $param["penginapan"] = $penginapan;
        return view('Admin.listpenginapan',$param);
    }

    public function AdminHapusListPenginapan(Request $request){
        $penginapan = Penginapan::withTrashed()->find($request->id);
        if ($penginapan->trashed()){
            $penginapan->restore();
            return redirect("admin/listpenginapan")->with("pesanSukses", "penginapan telah di unban!");
        } else{
            $penginapan->delete();
            return redirect("admin/listpenginapan")->with("pesanSukses", "penginapan telah di ban!");
        }
    }

    public function AdminUbahListPenginapan(Request $request){

        $param =[];
        $penginapan = Penginapan::where('id',$request->id)->first();

        $param["penginapan"] = $penginapan;
        // dd($param);
        return view('Admin.ubahlistpenginapan', $param);
    }

    public function AdmindoUbahListPenginapan(Request $request){

        $res = Penginapan::where('id',$request->id)->update(
            [
                "username"=>$request->username,
                "password"=>$request->password,
                "nama_lengkap"=>$request->nama,
                "email"=>$request->email,
                "no_telp"=>$request->notelp,
                "saldo"=>$request->saldo,
            ]
        );
        if($res){
            return redirect("admin/listpenginapan")->with("pesanSukses","Data berhasil diubah");
        }else{
            return redirect("admin/listpenginapan")->with("pesanGagal","Data gagal berhasil diubah");
        }
    }

    function AdminMail(){
        $param =[];
        $penginap = Penginap::all();
        $pemilik = Pemilik::all();
        $user = $penginap->merge($pemilik);


        $param["user"] = $user;
        return view('Admin.mail',$param);
    }
    public function doAdminMail(Request $request)
    {
        $request->validate([
            "subject" => "required",
            "email" => ["required"],
            "deskripsi" => "required",
        ]);

        $penginap = Penginap::where("email","=",$request->email)->first();
        $pemilik = Pemilik::where("email","=",$request->email)->first();
        if (empty($penginap)){
            Mail::to($request->email)
        ->send(new BeritaMail("$pemilik->username - $pemilik->nama_lengkap",$request->deskripsi,$request->email));
        }else{
            Mail::to($request->email)
        ->send(new BeritaMail("$penginap->username - $penginap->nama_lengkap",$request->deskripsi,$request->email));
        }
        return redirect()->back();
        
    }
    function AdminListNotifikasi(){
        $param =[];
        $notifikasi = Pengumuman::all();

        $param["notifikasi"] = $notifikasi;
        return view('Admin.listnotifikasi', $param);
    }
    function AdminTambahNotifikasi(Request $request){
        $request->validate([
            "title" => ["required"],
            "isi" => ["required"],
            "rbJenis" => ['required','in:pemilik,penginap'],
        ]);
        if($request->rbJenis == "penginap"){
            $tipe = 0;
        }
        else if($request->rbJenis == "pemilik"){
            $tipe = 1;
        }
        $res = Pengumuman::create(array(
            "judul" => $request->title,
            "isi" => $request->isi,
            "tipe" => $tipe
        ));
        return redirect()->back();
    }

    public function AdminHapusNotifikasi(Request $request){
        $res = Pengumuman::where('id',$request->id)->delete();

        if($res){
            return redirect("admin/listnotifikasi")->with("pesanSukses","Data berhasil dihapus");
        }else{
            return redirect("admin/listnotifikasi")->with("pesanGagal","Data gagal berhasil dihapus");

        }
    }


}
