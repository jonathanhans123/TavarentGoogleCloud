<?php

use Illuminate\Http\Request;
use Illuminate\Support\Facades\Route;
use App\Http\Controllers\Api\DatabaseController;

/*
|--------------------------------------------------------------------------
| API Routes
|--------------------------------------------------------------------------
|
| Here is where you can register API routes for your application. These
| routes are loaded by the RouteServiceProvider within a group which
| is assigned the "api" middleware group. Enjoy building your API!
|
*/

Route::middleware('auth:sanctum')->get('/user', function (Request $request) {
    return $request->user();
});

Route::get("/semuauser",[DatabaseController::class, "listuser"]);
Route::get("/penginap/list",[DatabaseController::class, "listpenginap"]);
Route::post("/penginap/find",[DatabaseController::class, "findpenginap"]);
Route::post("/penginap/insert",[DatabaseController::class, "insertpenginap"]);
Route::post("/penginap/update",[DatabaseController::class, "updatepenginap"]);
Route::post("/penginap/tambahsaldo",[DatabaseController::class, "tambahsaldopenginap"]);

Route::get("/pemilik/list",[DatabaseController::class, "listpemilik"]);
Route::post("/pemilik/find",[DatabaseController::class, "findpemilik"]);
Route::post("/pemilik/find/penginapan",[DatabaseController::class, "findpemilikdaripenginapan"]);
Route::post("/pemilik/insert",[DatabaseController::class, "insertpemilik"]);
Route::post("/pemilik/update",[DatabaseController::class, "updatepemilik"]);

Route::get("/penginapan/list",[DatabaseController::class, "listpenginapan"]);
Route::get("/penginapan/list/rating",[DatabaseController::class, "listpenginapanrating"]);
Route::post("/penginapan/list/favorit",[DatabaseController::class, "listpenginapanfavorit"]);
Route::post("/penginapan/check/favorit",[DatabaseController::class, "checkpenginapanfavorit"]);
Route::post("/penginapan/toggle/favorit",[DatabaseController::class, "togglepenginapanfavorit"]);
Route::post("/penginapan/insert",[DatabaseController::class, "insertpenginapan"]);

Route::get("/pengumuman/list",[DatabaseController::class, "listpengumuman"]);
Route::post("/pengumuman/insert",[DatabaseController::class, "insertpengumuman"]);

Route::get("/kupon/list",[DatabaseController::class, "listkupon"]);

Route::post("/chat/list/penginap",[DatabaseController::class, "listchatpenginap"]);
Route::post("/chat/list/pemilik",[DatabaseController::class, "listchatpemilik"]);
Route::post("/chat/pesan/list",[DatabaseController::class, "listpesanchat"]);
Route::post("/chat/insert",[DatabaseController::class, "insertchat"]);

Route::get("/promo/list",[DatabaseController::class, "listpromo"]);
Route::get("/rating/list",[DatabaseController::class, "listrating"]);

Route::post("/pembayaran/list",[DatabaseController::class, "listpembayaran"]);
Route::get("/pembayaran/list/semua",[DatabaseController::class, "listpembayaransemua"]);
Route::post("/pembayaran/list/count",[DatabaseController::class, "countpembayaran"]);
Route::post("/pembayaran/insert",[DatabaseController::class, "insertpembayaran"]);
Route::post("/pembayaran/list/penginapan",[DatabaseController::class, "listpembayaranpenginapan"]);
