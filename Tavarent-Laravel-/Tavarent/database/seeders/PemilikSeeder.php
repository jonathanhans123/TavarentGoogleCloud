<?php

namespace Database\Seeders;

use Illuminate\Database\Console\Seeds\WithoutModelEvents;
use Illuminate\Database\Seeder;
use Illuminate\Support\Facades\DB;

class PemilikSeeder extends Seeder
{
    /**
     * Run the database seeds.
     *
     * @return void
     */
    public function run()
    {
        //
        DB::statement("SET FOREIGN_KEY_CHECKS=0");
        DB::table("pemilik")->truncate();
        DB::statement("SET FOREIGN_KEY_CHECKS=1");

        DB::table("pemilik")->insert(
            [
                [
                    "username" => "ryan",
                    "password" => "ryan",
                    "nama_lengkap" => "Ryan Reynaldi",
                    "email" => "ryan@gmail.com",
                    "no_telp" => "0813333333",
                ],
                [
                    "username" => "ivan",
                    "password" => "ivan",
                    "nama_lengkap" => "Agustinus Ivan",
                    "email" => "ivan@gmail.com",
                    "no_telp" => "0814444444",
                ],
                [
                    "username" => "jojo",
                    "password" => "jojo",
                    "nama_lengkap" => "Jonathan Hans",
                    "email" => "jojo@gmail.com",
                    "no_telp" => "0811111111",
                ],
                [
                    "username" => "hezron",
                    "password" => "hezron",
                    "nama_lengkap" => "Hezron Dharmawan",
                    "email" => "hezron@gmail.com",
                    "no_telp" => "0812222222",
                ],
            ]
        );
    }
}
