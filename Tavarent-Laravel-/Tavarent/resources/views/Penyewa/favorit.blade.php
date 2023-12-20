@extends('layout.layout')
@section('title','Penginap')
@section("extracss")
    <link rel="stylesheet" href="/css/penginap.css">
@endsection
@section("extrajs")
    <script src="/java/penginap.js"></script>
@endsection
@section('navbar')
    @include("navbar.navbarpenginap")
@endsection
@section('content')

<div class="container" style="justify-content:space-between;margin-top:80px; ">
        <h1>Penginapan Favorit</h1>
        <p class="hint">Ini adalah semua penginapan yang kamu sukai</p>
        @forelse($penginapan as $p)
        <div class="product-card">
            <div class="badge">{{$p->tipe}}</div>
            <div class="product-tumb">
                <img src="/storage/imagesPenginapan/{{$p->id}}_1.jpg" alt="" style="height:100%;width:100%;object-fit:cover;">
            </div>
            <div class="product-details">
                <span class="product-catagory">{{$p->jk_boleh}}</span>
                <h4><a href="/penyewa/penginapan/{{$p->id}}">{{$p->nama}}</a></h4>
                <p>
                    @php
                        if (strlen($p->deskripsi)>100){
                            echo substr($p->deskripsi,0,80) . " ... ";
                        }else{
                            echo $p->deskripsi;
                        }
                    @endphp
                </p>
                <div class="product-bottom-details">
                    <div class="product-price">Rp. {{$p->harga}}</div>
                    <div class="product-links">
                    </div>
                </div>
            </div>
        </div>
        @empty
            <h2>Cari penginapan kesukaaanmu dulu</h2>
        @endforelse
    </div>

@endsection

