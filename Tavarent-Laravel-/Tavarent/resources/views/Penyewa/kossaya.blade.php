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
    <div class="container" style="padding-top:100px;">
        @forelse($pembayaran as $p)
        <a href="/penyewa/penginapan/{{$p->id}}" style="color:black;text-decoration:none;">
        <div class="kotak" style="border: 1px solid gray;border-radius:20px;padding-left:10%;padding-right:10%;margin-bottom:20px;padding-top:20px;padding-bottom:20px;overflow:hidden;">
        <div class="left" style="float:left;">
            <div style="font-size:20pt;font-weight:bold;color:#fbb72c;">ORDER{{$p->id}}</div>
            <div style="font-weight:bold;">Total: Rp. {{number_format($p->total,2)}}</div>
            <table>
                <tr style="font-weight:bold;font-size:10pt;">
                    <td style="margin-right:20px;">Tanggal Mulai</td>
                    <td>Tanggal Selesai</td>
                </tr>
                <tr>
                    <td style="padding-right:20px;">{{date("F j, Y",strtotime($p->tanggal_mulai))}}</td>
                    <td>{{date("F j, Y",strtotime($p->tanggal_mulai))}}</td>
                </tr>
            </table>
            <div class="hint">Nama Penginapan: {{$p->Penginapan->nama}}</div>
            
        </div>
        <div class="right" style="float:right;width:10%;height:10%;margin-top:30px;">
            <img src="{{ asset('img/paid.png') }}" alt="" style="object-fit: contain;width:100px;">
    </div>
        </div>
        
        </a>
        @empty
            <h1>Kamu belum pernah sewa penginapan</h1>
        @endforelse
    </div>
@endsection