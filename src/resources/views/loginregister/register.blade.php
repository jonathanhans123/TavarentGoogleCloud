@extends('layout.loginregister')
@section('title','Register')

@section('content')
<form action="" method="POST" class="signin-form">
    @csrf
    <input type="hidden" id="hidden" name="hidden" value="register">
    <div class="form-group mb-3">
        <label class="label" for="name">Email</label>
        <input type="text" class="form-control" placeholder="Masukkan Email" name="email" value="{{ old('email') }}">
        @error("email")
            <label class="label text-danger" for="email">{{$message}}</label>
        @enderror
    </div>
    <div class="form-group mb-3">
        <label class="label" for="name">Username</label>
        <input type="text" class="form-control" placeholder="Masukkan Username" name="username" value="{{ old('username') }}">
        @error("username")
            <label class="label text-danger" for="Username">{{$message}}</label>
        @enderror
    </div>
    <div class="form-group mb-3">
        <label class="label" for="name">Nama</label>
        <input type="text" class="form-control" placeholder="Masukkan Nama" name="nama" value="{{ old('nama') }}">
        @error("nama")
            <label class="label text-danger" for="nama">{{$message}}</label>
        @enderror
    </div>
    <div class="form-group mb-3">
        <label class="label" for="name">Nomor Telepon</label>
        <input type="text" class="form-control" placeholder="Nomor Telepon" name="notelp" value="{{ old('notelp') }}">
        @error("notelp")
            <label class="label text-danger" for="notelp">{{$message}}</label>
        @enderror
    </div>

    <div class="form-group mb-4">
        <label class="label" for="password">Password</label>
        <input id="password" type="password" class="form-control" placeholder="Masukkan Password" name="password"  value="{{ old('password') }}">
        @error("password")
            <label class="label text-danger" for="password">{{$message}}</label>
        @enderror
    </div>
    <div class="form-group mb-4">
        <label class="label" for="name">Pemilik</label> <input type="radio" name="rbJenis" value="pemilik"><i class="validation"></i>
        <label class="label" for="name">Penginap</label>  <input type="radio" name="rbJenis" value="penginap"><i class="validation"></i><br>
        @error("rbJenis")
            <label class="label text-danger" for="rbJenis">{{$message}}</label>
        @enderror
    </div>
    <div class="form-group mb-3">
        <button type="submit" class="button form-control rounded px-3">Register</button>
    </div>
</form>
<p class="text-center">OR<br><a href="{{url('/login')}}">Go To Login Page</a></p>

@endsection
