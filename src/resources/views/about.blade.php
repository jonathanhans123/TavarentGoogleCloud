@extends('layout.layout')
@section('title','About Us')
@section('content')
<div class="container">
    <h1 class="text-center">Welcome To Taravent!</h1>
    <br>
    <img src="{{url('img/tavarentlogo-removebg-big.png')}}" class="rounded mx-auto d-block" alt="...">
    <br>
    <div class="bg-primary mx-auto" style="color: white;width:400px">
        <h3>Our Mission</h3>
        <p>
            A. Sistem ini akan dapat menghemat waktu untuk para pencari penginapan dengan memberi semua informasi penginapan yang terdapat disekitar lokasi yang ditentukan oleh pencari.
website mempermudah para penyewa untuk dapat mengelola penginapan dengan baik. <br>
B. Penyewa akan bisa menyebarkan informasi tentang penginapan lebih mudah dan akan bisa meningkatkan keuntungan dan kemudahan dalam pengurusan penginapan.
Penyewa akan mendapatkan harga terbaik yang bisa didapatkan pada penginapan tersebut. <br>
C. Aplikasi akan menyediakan banyak promo untuk penginap seperti promo dari penyewa dan promo dari minigame yang akan disediakan pada aplikasi.

        </p>
    </div>
</div>
@endsection
