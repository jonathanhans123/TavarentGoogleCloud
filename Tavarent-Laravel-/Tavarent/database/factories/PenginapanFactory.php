<?php

namespace Database\Factories;

use Illuminate\Database\Eloquent\Factories\Factory;

/**
 * @extends \Illuminate\Database\Eloquent\Factories\Factory<\App\Models\Penginapan>
 */
class PenginapanFactory extends Factory
{
    /**
     * Define the model's default state.
     *
     * @return array<string, mixed>
     */
    public function definition()
    {
        $tipe =  $this->faker->randomElement(["Apartment","Kos"]);
        $alamat = $this->faker->randomElement(
            [
                "Jalan Klampis Aji II, Surabaya 60117, Indonesia",
                "45, Jalan Klampis Aji I, Surabaya 60117, Indonesia"
            ]);
        $koordinat = "";
        if ($alamat=="Jalan Klampis Aji II, Surabaya 60117, Indonesia"){
            $koordinat = "-7.30432,112.78298";
        }else{
            $koordinat = "-7.28791,112.77717";
        }
        $fasilitas = "";
        $listfasilitas = ["Air Conditioner","Termasuk Listrik","K. Mandi Dalam","Kursi","Meja","Wifi","Kasur Double",
        "Kasur Single","Tv","Jendela","Air Panas","Dapur"];
        for($i=0;$i<4;$i++){
            $fasilitas= $fasilitas.$this->faker->randomElement($listfasilitas).',';
        }
        return [
            "nama" => $tipe.$this->faker->word(),
            "alamat" => $alamat,
            "deskripsi" => $this->faker->text(100),
            "fasilitas" => $fasilitas,
            "jk_boleh" => $this->faker->randomElement(["campur","pria","wanita"]),
            "tipe"=> $tipe,
            "harga" => $this->faker->numberBetween(500000,2500000),
            "koordinat"=>$koordinat,
            "jumlah_foto"=>0,
            "id_pemilik"=>$this->faker->numberBetween(1,4)
        ];
        
    }
}
