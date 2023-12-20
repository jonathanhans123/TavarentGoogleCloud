@extends('layout.layout')
@section('title','Help Center')
@section('navbar')
<div class="navigation-wrap bg-light start-header start-style">
    <div class="container">
        <div class="row">
            <div class="col-12">
                <nav class="navbar navbar-expand-md navbar-light">

                    <a href="/penyewa"><img src="{{ asset('img/tavarentlogo-removebg-big.png')}}" width="50px" alt=""></a>
                </nav>
            </div>
@endsection
@section('content')
<div class="Container">
    <h1>Help Center</h1>
    <div>
        <h3>Getting Started</h3>
        <ul>
           <a href="">cara membuat akun</a> <br>
           <a href="">cara membuka chat</a> <br>
           <a href="">cara mencari tempat penginapan</a> <br>
           <a href="">cara menyewa tempat</a> <br>
        </ul>
    </div>
    <h3>Account problems</h3>
    <ul>
       <a href="">akun saya dihack?</a> <br>
       <a href="">kenapa akun saya diban?</a> <br>

    </ul>
</div>
@endsection
