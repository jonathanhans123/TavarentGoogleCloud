@extends('layout.layout')
@section('title','List Penginap')
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
                    <i class="bi bi-envelope-fill fa-2x"></i>
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
    <h1>List Penginapan</h1>
@if (Session::has("pesanSukses"))
<div class="alert alert-success">{{ Session::get("pesanSukses") }}</div>
@endif

@if (Session::has("pesanGagal"))
<div class="alert alert-danger">{{ Session::get("pesanGagal") }}</div>
@endif
@if (!$penginapan->isEmpty())
    <table class="table table table-striped table-hover table-bordered border-dark">
        <thead class="table-dark">
            <tr>
                <th>ID</th>
                <th>Nama</th>
                <th>Alamat</th>
                <th>Koordinat</th>
                <th>Deskripsi</th>
                <th>Fasilitas</th>
                <th>Jenis Kelamin Yang Boleh</th>
                <th>Tipe</th>
                <th>Harga</th>
                <th>Id Pemilik</th>
                <th>Action</th>
            </tr>
        </thead>

        @foreach ($penginapan as $penginapans)
            <tr>
                <td>{{ $penginapans->id }}</td>
                <td>{{ $penginapans->nama }}</td>
                <td>{{ $penginapans->alamat }}</td>
                <td>{{ $penginapans->koordinat }}</td>
                <td>{{ $penginapans->deskripsi }}</td>
                <td>{{ $penginapans->fasilitas }}</td>
                <td>{{ $penginapans->jk_boleh }}</td>
                <td>{{ $penginapans->tipe }}</td>
                <td>{{ $penginapans->harga }}</td>
                <td>{{ $penginapans->Pemilik->nama_lengkap }}</td>
                <td>
                    
                    @if($penginapans->trashed())
                    <a href="{{ url("admin/listpenginapan/hapus/$penginapans->id") }}" class="btn btn-success">Unban</a>
                    @else
                    <a href="{{ url("admin/listpenginapan/hapus/$penginapans->id") }}" class="btn btn-danger">Ban</a>
                    @endif
                </td>
            </tr>
        @endforeach
    </table>
@else
<h1>tidak ada daftar penginap</h1>
@endif
</div>
@endsection

