<?php
use App\Http\Controllers\GaleriController;
use App\Http\Controllers\AdminController;
use App\Http\Controllers\LoginRegisterController;
use App\Http\Controllers\PemilikController;
use App\Http\Controllers\PenyewaController;
use Illuminate\Support\Facades\Artisan;
use Illuminate\Support\Facades\Route;

/*
|--------------------------------------------------------------------------
| Web Routes
|--------------------------------------------------------------------------
|
| Here is where you can register web routes for your application. These
| routes are loaded by the RouteServiceProvider within a group which
| contains the "web" middleware group. Now create something great!
|
*/
// Route::post('submit', [LoginRegisterController::class, 'check'])->name('check');
Route::get('/ts', function () {
    return view('ts');
});
Route::get('/terms', function () {
    return view('terms');
});
Route::get('/about', function () {
    return view('about');
});
Route::get('/help', function () {
    return view('help');
});
Route::get('closeAdmin', [AdminController::class, 'destroyadmin']);
Route::post('logout', [LoginRegisterController::class, 'logout']);
Route::middleware(['cekLogin'])->group(function () {
    Route::get('/', function () {
        return redirect('login');
    });
    Route::get('login', [LoginRegisterController::class, 'login']);
    Route::get('register', [LoginRegisterController::class, 'register']);
    Route::post('login', [LoginRegisterController::class, 'doLogin']);
    Route::post('register', [LoginRegisterController::class, 'doRegister']);
});
Route::middleware(['cekUserPenyewa'])->group(function () {

    Route::get('penyewa', [PenyewaController::class, 'PenyewaHome']);
    Route::get('penyewa/search/{lat?}/{lng?}/{alamat?}', [PenyewaController::class, 'PenyewaSearch']);
    Route::get('penyewa/penginapan/{id}', [PenyewaController::class, 'PenginapanDetail']);
    Route::post('penyewa/penginapan/{id}', [PenyewaController::class, 'doPenginapanDetail']);
    Route::get('penyewa/favorit', [PenyewaController::class, 'PenyewaFavorit']);
    Route::get('penyewa/kossaya', [PenyewaController::class, 'PenyewaKosSaya']);
    Route::get('penyewa/chat/{id?}', [PenyewaController::class, 'PenyewaChatPemilik']);
    Route::post('penyewa/chat/{id}', [PenyewaController::class, 'sendchat']);
    Route::get('penyewa/profil', [PenyewaController::class, 'PenyewaProfil']);
    Route::post('penyewa/profil', [PenyewaController::class, 'updatePenyewa']);
    Route::post('penyewa/togglefavorit',[PenyewaController::class, 'ToggleFavorit'])->name("toggle");
    Route::get('penyewa/pembayaran',[PenyewaController::class, 'Pembayaran'])->name("pembayaran");
    Route::get("penyewa/insertpembayaran",[PenyewaController::class, 'insertPembayaran']);
    Route::get("penyewa/notifikasi",[PenyewaController::class, 'PenyewaNotifikasi']);
});


Route::middleware(['cekUserPenyewa'])->group(function () {
Route::get('penyewa', [PenyewaController::class, 'PenyewaHome']);
Route::get('penyewa/search', [PenyewaController::class, 'PenyewaSearch']);
Route::get('penyewa/favorit', [PenyewaController::class, 'PenyewaFavorit']);
Route::get('penyewa/kossaya', [PenyewaController::class, 'PenyewaKosSaya']);
Route::get('penyewa/chat', [PenyewaController::class, 'PenyewaChat']);
Route::get('penyewa/profil', [PenyewaController::class, 'PenyewaProfil']);
});
Route::middleware(['cekUserPemilik'])->group(function () {
    Route::get('pemilik', [PemilikController::class, 'PemilikHome']);
    Route::get('pemilik/chat/{id?}', [PemilikController::class, 'PemilikChatPenyewa']);
    Route::post('pemilik/chat/{id?}', [PemilikController::class, 'sendchat']);
    Route::get('pemilik/kelola', [PemilikController::class, 'PemilikKelola']);
    Route::get('pemilik/promo', [PemilikController::class, 'PemilikPromo']);
    Route::post('pemilik/promo', [PemilikController::class, 'doPemilikPromo']);
    Route::post('pemilik/kelola', [PemilikController::class, 'doPemilikKelola']);
    Route::get('pemilik/statistik', [PemilikController::class, 'PemilikStatistik']);
    Route::get('pemilik/profil', [PemilikController::class, 'PemilikProfil']);
    Route::get('pemilik/notifikasi', [PemilikController::class, 'PemilikNotifikasi']);
    Route::post('pemilik/profil', [PemilikController::class, 'updatePemilik']);
    Route::post('pemilik/promo/{id}', [PemilikController::class, 'deletePromo']);
    Route::get('pemilik/penginapan/{id}', [PemilikController::class, 'PenginapanDetail']);
    Route::get('logout', [PemilikController::class, 'logoutpemilik']);
});

Route::middleware(['cekUserAdmin'])->group(function () {
    Route::get('admin', [AdminController::class, 'AdminListPenginap']);
    Route::get('admin/listpenginap', [AdminController::class, 'AdminListPenginap']);
    Route::get('admin/listpenginap/hapus/{id}', [AdminController::class, 'AdminHapusListPenginap']);
    Route::get('admin/listpenginap/ubah/{id}',[AdminController::class,'AdminUbahListPenginap']);
    Route::post('admin/listpenginap/ubah/{id}',[AdminController::class,'AdmindoUbahListPenginap']);
    Route::get('admin/listpemilik', [AdminController::class, 'AdminListPemilik']);
    Route::get('admin/listpemilik/hapus/{id}', [AdminController::class, 'AdminHapusListPemilik']);
    Route::get('admin/listpemilik/ubah/{id}',[AdminController::class,'AdminUbahListPemilik']);
    Route::post('admin/listpemilik/ubah/{id}',[AdminController::class,'AdmindoUbahListPemilik']);
    Route::get('admin/listpenginapan', [AdminController::class, 'AdminListPenginapan']);
    Route::get('admin/listpenginapan/hapus/{id}', [AdminController::class, 'AdminHapusListPenginapan']);
    Route::get('admin/listpenginapan/ubah/{id}',[AdminController::class,'AdminUbahListPenginapan']);
    Route::post('admin/listpenginapan/ubah/{id}',[AdminController::class,'AdmindoUbahListPenginapan']);
    Route::get('admin/mail', [AdminController::class, 'AdminMail']);
    Route::post('admin/mail', [AdminController::class, 'doAdminMail']);
    Route::get('admin/listnotifikasi', [AdminController::class, 'AdminListNotifikasi']);
    Route::post('admin/listnotifikasi', [AdminController::class, 'AdminTambahNotifikasi']);
    Route::get('admin/listnotifikasi/hapus/{id}', [AdminController::class, 'AdminHapusNotifikasi']);
    Route::get('admin/listnotifikasi/ubah/{id}',[AdminController::class,'AdminUbahNotifikasi']);
    Route::post('admin/listnotifikasi/ubah/{id}',[AdminController::class,'AdmindoUbahNotifikasi']);
    Route::get('admin/logout',[AdminController::class,'logoutadmin']);
    Route::get('testing', [AdminController::class,'testing']);
});

Route::prefix("galeri")->group(function(){
    Route::get('upload', [GaleriController::class, "upload"]);
    Route::post('doUpload', [GaleriController::class, "doUpload"]);
    Route::get('download/{namafile}', [GaleriController::class, 'download']);
});

Route::get('testing', [AdminController::class,'testing']);




Route::get('/link', function () {
    Artisan::call('storage:link');
});