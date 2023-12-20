@extends('layout.layout')
@section('title','Ubah Pemilik')
@section("extracss")
    <link rel="stylesheet" href="/css/admin.css">
@endsection
@section('navbar')
<a href="/admin/listpemilik" style="text-decoration: none">
    <i class="fa fa-arrow-circle-o-left" style="color: black;"></i>
</a>
@endsection
@section('content')
<div class="container">

    <h1>Ubah Pemilik</h1>
    <form action="{{url("admin/listpemilik/ubah/$pemilik->id")}}" method="POST">
        @csrf

        <input type="hidden" name="id" value="{{$pemilik->id}}">
        <div class="form-group mb-3">
            <label class="label" for="name">Email</label>
            <input type="text" class="form-control" placeholder="Masukkan Email" name="email" value="{{ old('email',$pemilik->email) }}">
            @error("email")
                <label class="label text-danger" for="email">{{$message}}</label>
            @enderror
        </div>
        <div class="form-group mb-3">
            <label class="label" for="name">Username</label>
            <input type="text" class="form-control" placeholder="Masukkan Username" name="username" value="{{ old('username',$pemilik->username) }}">
            @error("username")
                <label class="label text-danger" for="Username">{{$message}}</label>
            @enderror
        </div>
        <div class="form-group mb-3">
            <label class="label" for="name">Nama</label>
            <input type="text" class="form-control" placeholder="Masukkan Nama" name="nama" value="{{ old('nama',$pemilik->nama_lengkap) }}">
            @error("nama")
                <label class="label text-danger" for="nama">{{$message}}</label>
            @enderror
        </div>
        <div class="form-group mb-3">
            <label class="label" for="name">Nomor Telepon</label>
            <input type="text" class="form-control" placeholder="Nomor Telepon" name="notelp" value="{{ old('notelp',$pemilik->no_telp) }}">
            @error("notelp")
                <label class="label text-danger" for="notelp">{{$message}}</label>
            @enderror
        </div>

        <div class="form-group mb-4">
            <label class="label" for="password">Password</label>
            <input id="password" type="password" class="form-control" placeholder="Masukkan Password" name="password"  value="{{ old('password',$pemilik->password) }}">
            @error("password")
                <label class="label text-danger" for="password">{{$message}}</label>
            @enderror
        </div>

        <div class="form-group mb-4">
            <label class="label" for="password">Saldo</label>
            <input id="" type="number" class="form-control" placeholder="Masukkan Saldo" name="saldo"  value="{{ old('saldo',$pemilik->saldo) }}">
            @error("saldo")
                <label class="label text-danger" for="saldo">{{$message}}</label>
            @enderror
        </div>

        <div class="mb-3">
            <input type="submit" value="ubah" class="btn btn-success">
        </div>
    </form>
</div>
@endsection
