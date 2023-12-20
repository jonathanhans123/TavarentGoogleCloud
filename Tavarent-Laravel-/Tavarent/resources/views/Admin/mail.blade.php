@extends('layout.layout')
@section('title','Laporan')
@section("extracss")
    <link rel="stylesheet" href="/css/admin.css">
@endsection
@section('navbar')

    <nav class="main-menu">
        <ul>
            <li class="has-subnav">
                <a href="/admin/listpenginap">
                    <i class="fa fa-list fa-2x"></i>
                    <span class="nav-text">
                        List Penginap
                    </span>
                </a>

            </li>
            <li class="has-subnav">
                <a href="/admin/listpemilik">
                    <i class="fa fa-key fa-2x"></i>
                    <span class="nav-text">
                        List Pemilik
                    </span>
                </a>

            </li>
            <li class="has-subnav">
                <a href="/admin/listpenginapan">
                    <i class="fa fa-inbox fa-2x"></i>
                    <span class="nav-text">
                        List Penginapan
                    </span>
                </a>

            </li>

            <li>
                <a href="/admin/listnotifikasi">
                    <i class="fa fa-bell fa-2x"></i>
                    <span class="nav-text">
                        List Notifikasi
                    </span>
                </a>
            </li>

            <li>
                <a href="/admin/mail">
                    <i class="fa fa-envelope fa-2x"></i>
                    <span class="nav-text">
                        Send Mail
                    </span>
                </a>
            </li>
        </ul>

        <ul class="logout">
            <li>
                <a href="/admin/logout">
                        <i class="fa fa-power-off fa-2x"></i>
                    <span class="nav-text">
                        Logout
                    </span>
                </a>
            </li>
        </ul>
    </nav>

@endsection
@section('content')
<div class="area">
    <form action="" method="post">@csrf
    <h1>Send Mail</h1>
    <h4>Subject</h4>
    <input type="text" name="subject" id="" class="form-control">
    @error("subject")
        <p class="label-danger">{{$message}}</p>
    @enderror
    <h4>User</h4>
    <select name="email" class="form-select">
        @if(is_array($user)||is_object($user))
        @foreach($user as $u)
        <option value="{{$u->email}}">{{$u->username}} - {{$u->nama_lengkap}}</option>
        @endforeach
        @else
        <option value="">{{$user}}</option>
        @endif
    </select>
    @error("id")
        <p class="label-danger">{{$message}}</p>
    @enderror
    <h4>Deskripsi</h4>
    <textarea name="deskripsi" id="" cols="30" rows="10" class="form-control"></textarea>
    <input type="submit" value="Send Mail" class="btn btn-success">
    @error("deskripsi")
        <p class="label-danger">{{$message}}</p>
    @enderror
    </form>
</div>

@endsection

