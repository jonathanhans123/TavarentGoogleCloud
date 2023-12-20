<?php

namespace Database\Seeders;

use Illuminate\Database\Console\Seeds\WithoutModelEvents;
use Illuminate\Database\Seeder;
use Illuminate\Support\Facades\DB;


class PenginapSeeder extends Seeder
{
    /**
     * Run the database seeds.
     *
     * @return void
     */
    public function run()
    {
        DB::statement("SET FOREIGN_KEY_CHECKS=0");
        DB::table("penginap")->truncate();
        DB::statement("SET FOREIGN_KEY_CHECKS=1");

        DB::table("penginap")->insert(
            [
                "username" => "test",
                "password" => "test",
                "nama_lengkap" => "Testing Testing",
                "email" => "test@gmail.com",
                "no_telp" => "0812345678",
            ],
        );
    }
}
