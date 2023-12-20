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
        Schema::create('pembayaran', function (Blueprint $table) {
            $table->id();
            $table->integer('total');
            $table->timestamp('tanggal_mulai')->useCurrent();
            $table->timestamp('tanggal_selesai')->useCurrent();
            $table->unsignedBigInteger('id_penginap');
            $table->unsignedBigInteger('id_penginapan');
            $table->unsignedBigInteger('id_kupon')->nullable();
            $table->unsignedBigInteger('id_promo')->nullable();
            $table->foreign('id_penginap')->references('id')->on('penginap')->onDelete('cascade');
            $table->foreign('id_penginapan')->references('id')->on('penginapan')->onDelete('cascade');
            $table->foreign('id_kupon')->nullable()->references('id')->on('kupon')->onDelete('cascade');
            $table->foreign('id_promo')->nullable()->references('id')->on('promo')->onDelete('cascade');
        });
    }

    /**
     * Reverse the migrations.
     *
     * @return void
     */
    public function down()
    {
        Schema::dropIfExists('pembayaran');
    }
};
