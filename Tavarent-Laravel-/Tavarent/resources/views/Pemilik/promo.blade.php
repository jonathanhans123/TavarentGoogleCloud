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
    <div class="container" style="overflow:hidden;height:auto;padding-bottom:100px;padding-top:100px;">
        <div class="left" style="float:left;width:30%;height:100%">
        <h1>Tambah Promo</h1>
        <form action="" method="post">@csrf
            <p class="hint">Judul Promo</p>
            <input type="text" name="nama" class="form-control">
            <p class="hint">Jenis Promo</p>
            <select name="jenis" id="jenis" class="form-select">
                <option value="diskon">Diskon</option>
                <option value="nominal">Nominal</option>
                
            </select>
            <p class="hint">Jumlah Promo</p>
            <input type="text" name="jumlah" class="form-control" id="jumlah">
            <p class="hint">Tanggal Mulai</p>
            <input type="date" name="tanggal_mulai" class="form-control">
            <p class="hint">Tanggal Selesai</p>
            <input type="date" name="tanggal_selesai" class="form-control">
            <p class="hint">Pilih Penginapan</p>
            <select name="id_penginapan" class="form-select" id="harga">
                @foreach($penginapan as $p)
                    <option value="{{$p->id}}" value2="{{$p->harga}}">{{$p->nama}}</option>
                @endforeach
            </select>
            <br>
            <p style="font-size:15pt;font-weight:bold;text-decoration:line-through;" id="hargaawal">Rp -</p>
            <p style="font-size:15pt;font-weight:bold;" id="hargaakhir">Rp -</p>
            <br>
            <input type="submit" value="Tambah Promo" class="btn btn-success">
        </div>
        </form>
        <div class="right" style="float:left;width:65%;margin-left:5%;height:100%">
            <table class="table">
                <thead>
                    <tr>
                        <td>No</td>
                        <td>Nama Penginapan</td>
                        <td>Harga Awal</td>
                        <td>Harga Akhir</td>
                        <td>Mulai</td>
                        <td>Akhir</td>
                        <td>Action</td>
                    </tr>
                </thead>
                <tbody>
                    @forelse($promo as $idx => $p)
                        <tr>
                            @php
                            
                            $penginapan = App\Models\Penginapan::find($p->id_penginapan);
                            @endphp
                            <td>{{$idx+1}}</td>
                            
                            <td>{{$penginapan->nama}}</td>
                            <td>{{number_format($penginapan->harga)}}</td>
                            <td>
                            @php
                            
                            if ($p->jenis=="diskon"){
                                $hargaakhir = $penginapan->harga*(100-$p->jumlah)/100;
                            }else{
                                $hargaakhir = $penginapan->harga-$p->jumlah;
                            }
                            echo 'Rp. '.number_format($hargaakhir);
                            @endphp
                            </td>
                            <td>{{substr($p->tanggal_mulai,0,10)}}</td>
                            <td>{{substr($p->tanggal_selesai,0,10)}}</td>
                            <td><form action="/pemilik/promo/{{$p->id}}" method="post">@csrf
                                <input type="submit" value="Delete" class="btn btn-danger">
                            </form></td>
                        </tr>
                    @empty
                        <h1>Masih Belum ada Promo</h1>
                    @endforelse
                </tbody>
            </table>
        </div>
    </div>
    <script>
        $(document).ready(function () {
            $("#jumlah").on("input",function(){
                var jumlah = $(this).val();
                var tipe = $("#jenis").val();
                var harga = $("#harga option:selected").attr("value2");
                console.log(jumlah+tipe+harga);
                if ( jumlah!=""&&jumlah>0){
                    var hargaakhir= 0;
                    const formatter = new Intl.NumberFormat('id-ID', {
                    style: 'currency',
                    currency: 'IDR',});
                    if (tipe=="diskon"){
                        hargaakhir = harga*(100-jumlah)/100;
                    }else{
                        hargaakhir = harga-jumlah;
                    }
                    console.log(hargaakhir);
                    $("#hargaakhir").html(formatter.format(hargaakhir));
                }else{
                    $("#hargaakhir").html("Rp -")
                }
            });
            $("#jenis").change(calculateprice());
            $("#harga").change(calculateprice());
            function calculateprice(){
                var jumlah = $("#harga").val();
                var tipe = $("#jenis").val();
                var harga = $("#harga option:selected").attr("value2");
                const formatter = new Intl.NumberFormat('id-ID', {
                    style: 'currency',
                    currency: 'IDR',});

                $("#hargaawal").html(formatter.format(harga));
                console.log(jumlah+tipe+harga);
                if ( jumlah!=""&&harga>0&&harga.isNumeric){
                    var hargaakhir= 0;
                    
                    if (tipe=="diskon"){
                        hargaakhir = harga*(100-jumlah)/100;
                    }else{
                        hargaakhir = harga-jumlah;
                    }
                    $("#hargaakhir").html(formatter.format(hargaakhir));
                }else{
                    $("#hargaakhir").html("Rp -")
                }
            }
        });
        
    </script>
    <!-- @php 
        echo $java;
    @endphp -->
@endsection