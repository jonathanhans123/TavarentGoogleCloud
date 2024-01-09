@extends('layout.loginregister')
@section('title','Login')
@section('content')
<form action="" method="POST" class="signin-form" >
    @csrf
    <input type="hidden" id="hidden" name="hidden" value="login">
    <div class="form-group mb-3">
        <label class="label" for="name">Email</label>
        <input type="text" class="form-control" placeholder="Masukkan Email" value="{{ old('email') }}" name="email">
        @error("email")
            <label class="label text-danger" for="email">{{$message}}</label>
        @enderror
    </div>
    <div class="form-group mb-3">
        <label class="label" for="password">Password</label>
        <input id="password" type="password" value="{{ old('password') }}" class="form-control" placeholder="Masukkan Password" name="password">
        @error("password")
            <label class="label text-danger" for="password">{{$message}}</label>
        @enderror
    </div>
    <div class="form-group mb-3">
    <div class="form-check">
        <input type="checkbox" class="form-check-input" name="rbremember" value="true">
        <label class="form-check-label" for="remember" >Remember me</label>
    </div>
    <div class="form-group mb-3">
        <button type="submit" class="button form-control rounded px-3" >Login</button>
    </div>
</form>
<p class="text-center">OR<br><a href="{{url('/register')}}">Go To Register Page</a></p>

@endsection
