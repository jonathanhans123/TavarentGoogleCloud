<?php

namespace Database\Seeders;

use App\Models\Pengumuman;
use Database\Factories\PengumumanMigrasiFactory;
use Illuminate\Database\Console\Seeds\WithoutModelEvents;
use Illuminate\Database\Seeder;
use Illuminate\Support\Facades\DB;

class PengumumanSeeder extends Seeder
{
    /**
     * Run the database seeds.
     *
     * @return void
     */
    public function run()
    {
        DB::statement("SET FOREIGN_KEY_CHECKS=0");
        DB::table("pengumuman")->truncate();
        DB::statement("SET FOREIGN_KEY_CHECKS=1");
        Pengumuman::factory()->count(10)->make();
    }
}
