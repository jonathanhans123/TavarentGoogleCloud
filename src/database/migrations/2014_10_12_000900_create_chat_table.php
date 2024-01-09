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
        Schema::create('chat', function (Blueprint $table) {
            $table->id();
            $table->longText("pesan");
            $table->string("sender");
            $table->string("status");
            $table->unsignedBigInteger("id_penginap");
            $table->unsignedBigInteger("id_pemilik");
            $table->timestamps();
            $table->foreign('id_penginap')->references('id')->on('penginap')->onDelete('cascade');
            $table->foreign('id_pemilik')->references('id')->on('pemilik')->onDelete('cascade');
        });
    }

    /**
     * Reverse the migrations.
     *
     * @return void
     */
    public function down()
    {
        Schema::dropIfExists('chat');
    }
};
