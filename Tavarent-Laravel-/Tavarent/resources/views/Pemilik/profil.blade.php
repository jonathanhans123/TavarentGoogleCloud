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
<div class="container" style="height:auto;padding-bottom:100px;padding-top:150px;display:flex;flex-direction:column;align-items:center;justify-content:center;">

<svg xmlns="http://www.w3.org/2000/svg" width="100" height="100" fill="currentColor" class="bi bi-person-fill" viewBox="0 0 16 16">
  <path d="M3 14s-1 0-1-1 1-4 6-4 6 3 6 4-1 1-1 1H3Zm5-6a3 3 0 1 0 0-6 3 3 0 0 0 0 6Z"/>
</svg>
<form action="" method="post" style="height:auto;width:50%;display:flex;flex-direction:column;">@csrf
    Username    <br>
    <input type="text" class="form-control" name="username" value="{{ old('username',Session::get('pemilik')->username) }}">
    @error("username")
    <label class="label text-danger">{{$message}}</label>
    @enderror
    <br>
    Email    <br>
    <input type="text" class="form-control"  name="email" value="{{ old('email',Session::get('pemilik')->email) }}">
    @error("email")
    <label class="label text-danger">{{$message}}</label>
    @enderror
    <br>
    Nomor Telepon    <br>
    <input type="text" class="form-control"  name="no_telp" value="{{ old('no_telp',Session::get('pemilik')->no_telp) }}">
    @error("no_telp")
    <label class="label text-danger">{{$message}}</label>
    @enderror
    <br>
    Password    <br>
    <input type="password" class="form-control"  name="password" value="{{ old('password',Session::get('pemilik')->password) }}">
    @error("password")
    <label class="label text-danger">{{$message}}</label>
    @enderror
    <br>
    @if(Session::has("success"))
    <label class="label text-success">{{Session::get("success")}}</label>
    @endif
    <input type="submit" value="Update Profile" class="btn btn-success">
    
</form>

</div>
@endsection



