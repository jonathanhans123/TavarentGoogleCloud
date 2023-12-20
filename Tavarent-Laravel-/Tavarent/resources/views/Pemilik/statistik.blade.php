@extends('layout.layout')
@section('title','Pemilik')
@section("extracss")
    <link rel="stylesheet" href="/css/penginap.css">
@endsection
@section('navbar')
    @include("navbar.navbarpemilik")
@endsection
@section('content')
<div class="container" style="margin-top:150px; height:1000px;">
    <form class="mt-5 d-flex" role="search">
        <input class="form-control me-2" type="search" placeholder="Cari Kos Disini " aria-label="Search">
        <img src="{{asset('img/search.png')}}" alt="" width="50" height="auto" type="submit">
    </form>
</div>
@endsection

