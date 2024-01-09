<?php

namespace Database\Factories;

use Illuminate\Database\Eloquent\Factories\Factory;

/**
 * @extends \Illuminate\Database\Eloquent\Factories\Factory<\App\Models\Model>
 */
class PengumumanMigrasiFactory extends Factory
{
    /**
     * Define the model's default state.
     *
     * @return array<string, mixed>
     */
    public function definition()
    {
        return [
            "judul" => "Pengumuman".$this->faker->word(),
            "isi" => $this->faker->text(100),
            "tipe" => $this->faker->numberBetween(0,1),
        ];
    }
}
