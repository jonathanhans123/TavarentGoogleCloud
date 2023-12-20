<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

return new class extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::create('penginapan', function (Blueprint $table) {
            $table->id();
            $table->string('nama');
            $table->string('alamat');
            $table->string('koordinat');
            $table->string('deskripsi');
            $table->string('fasilitas');
            $table->string('jk_boleh');
            $table->string('tipe');
            $table->integer('harga');
            $table->integer('jumlah_foto')->nullable();
            $table->unsignedBigInteger('id_pemilik');
            $table->foreign('id_pemilik')->references('id')->on('pemilik')->onDelete('cascade');
            $table->softDeletes();
        });
    }

    /**
     * Reverse the migrations.
     *
     * @return void
     */
    public function down()
    {
        Schema::dropIfExists('penginapan');
    }
};
