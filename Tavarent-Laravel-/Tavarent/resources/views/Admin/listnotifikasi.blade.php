@extends('layout.layout')
@section('title','List Pengumuman')
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
    <br>
    @if (Session::has("pesanSukses"))
        <div class="alert alert-success">{{ Session::get("pesanSukses") }}</div>
    @endif

    @if (Session::has("pesanGagal"))
        <div class="alert alert-danger">{{ Session::get("pesanGagal") }}</div>
    @endif
    <h1>Tambah Pengumuman</h1>
    <form action="{{ url("admin/listnotifikasi") }}" method="POST">
        @csrf
        <div class="mb-3">
            <label class="form-label">Judul</label>
            <input type="text" name="title" id="" class="form-control" placeholder="Judul Pengumuman" value="{{old('title')}}">
            @error("title")
            <label class="label text-danger" for="title">{{$message}}</label>
            @enderror
        </div>

        <div class="mb-3">
            <label class="form-label">Isi</label>
            <textarea name="isi" rows="4" cols="50" class="form-control">{{ old('isi') }}</textarea>
            @error("isi")
            <label class="label text-danger" for="isi">{{$message}}</label>
            @enderror
        </div>
        <div class="form-group mb-4">
            <label class="form-label">Jenis</label><br>
            <label class="label" for="name">Penginap</label>  <input type="radio" name="rbJenis" value="penginap"><i class="validation"></i>
            <label class="label" for="name">Pemilik</label> <input type="radio" name="rbJenis" value="pemilik"><i class="validation"></i>
            @error("rbJenis")
            <label class="label text-danger" for="rbJenis">{{$message}}</label>
            @enderror
        </div>

        <div class="mb-3">
            <input type="submit" value="tambah" class="btn btn-success">
        </div>
    </form>
<br>
<h1>List Pengumuman</h1>
    @if (!$notifikasi->isEmpty())
        <table class="table table-striped table-hover table-bordered border-dark">
            <thead class="table-dark">
                <tr>
                    <th>ID</th>
                    <th>Judul</th>
                    <th>Isi</th>
                    <th>Kepada</th>
                    <th>Action</th>
                </tr>
            </thead>
            <tbody>
                @foreach ($notifikasi as $notif)
                    <tr>
                        <td>{{ $notif->id }}</td>
                        <td>{{ $notif->judul }}</td>
                        <td>{{ $notif->isi }}</td>
                        <td> <?php echo ($notif->tipe == 0)? "Penginap" : "Pemilik" ?>
                        </td>
                        <td>
                            <a href="{{ url("admin/listnotifikasi/hapus/$notif->id") }}" class="btn btn-danger">Hapus</a>

                        </td>
                    </tr>
                @endforeach
            </tbody>
        </table>
    @else
    <h1>tidak ada daftar notifikasi</h1>
    @endif
</div>
@endsection

