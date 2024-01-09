@extends('layout.layout')
@section('title','List Pemilik')
@section("extracss")
    <link rel="stylesheet" href="/css/admin.css">
@endsection
@section('navbar')
<div class="area"></div>
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
    <br>
<h1>List Pemilik</h1>
@if (Session::has("pesanSukses"))
<div class="alert alert-success">{{ Session::get("pesanSukses") }}</div>
@endif

@if (Session::has("pesanGagal"))
<div class="alert alert-danger">{{ Session::get("pesanGagal") }}</div>
@endif
@if (!$pemilik->isEmpty())
    <table class="table table table-striped table-hover table-bordered border-dark">
        <thead class="table-dark">
            <tr>
                <th>ID</th>
                <th>Username</th>
                <th>Password</th>
                <th>Nama Lengkap</th>
                <th>Email</th>
                <th>No Telephone</th>
                <th>Saldo</th>
                <th>Action</th>
            </tr>
        </thead>

        <tbody>
            @foreach ($pemilik as $pemiliks)
            <tr>
                <td>{{ $pemiliks->id }}</td>
                <td>{{ $pemiliks->username }}</td>
                <td>{{ $pemiliks->password }}</td>
                <td>{{ $pemiliks->nama_lengkap }}</td>
                <td>{{ $pemiliks->email }}</td>
                <td>{{ $pemiliks->no_telp }}</td>
                <td>{{ $pemiliks->saldo }}</td>
                <td>
                    <a href="{{ url("admin/listpemilik/ubah/$pemiliks->id") }}" class="btn btn-primary">Ubah</a>
                    @if($pemiliks->trashed())
                    <a href="{{ url("admin/listpemilik/hapus/$pemiliks->id") }}" class="btn btn-success">Recover</a>
                    @else
                    <a href="{{ url("admin/listpemilik/hapus/$pemiliks->id") }}" class="btn btn-danger">Hapus</a>
                    @endif
                </td>
            </tr>
        @endforeach
        </tbody>

    </table>
@else
<h1>tidak ada daftar pemilik</h1>
@endif
</div>
@endsection

