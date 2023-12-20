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
        Schema::create('rating', function (Blueprint $table) {
            $table->id();
            $table->integer('rating');
            $table->string('komen');
            $table->unsignedBigInteger('id_penginap');
            $table->unsignedBigInteger('id_penginapan');
            $table->foreign('id_penginap')->references('id')->on('penginap')->onDelete('cascade');
            $table->foreign('id_penginapan')->references('id')->on('penginapan')->onDelete('cascade');
        });
    }

    /**
     * Reverse the migrations.
     *
     * @return void
     */
    public function down()
    {
        Schema::dropIfExists('rating');
    }
};
