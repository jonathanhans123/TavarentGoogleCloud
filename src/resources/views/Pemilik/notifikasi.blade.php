@extends('layout.layout')
@section('title','Pemilik')
@section("extracss")
    <link rel="stylesheet" href="/css/penginap.css">
@endsection
@section("extrajs")
    <script src="/java/penginap.js"></script>
@endsection
@section('navbar')
    @include("navbar.navbarpemilik")
@endsection
@section('content')
    <div class="container" style="padding-top:120px;">
    <h1>Notifikasi</h1>
    <hr>
        @forelse($pengumuman as $p)
            <h4>{{$p->judul}}</h4>
            <p>{{$p->isi}}</p>
        @empty
            <h1>Tidak ada pengumuman</h1>
        @endforelse
        <hr>
    </div>
@endsection