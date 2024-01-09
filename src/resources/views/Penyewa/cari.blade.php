@extends('layout.layout')
@section('title','Penginap')
@section("extracss")
    <link rel="stylesheet" href="/css/penginap.css">
@endsection
@section("extrajs")
    <script src="/java/penginap.js"></script>
@endsection
@section('navbar')
    @include("navbar.navbarpenginap")
@endsection
@section('content')
<div class="container" style="margin-top:150px; height:1000px;">
    
    <div style="margin-left:2%">
        <h4>Search Penginapan Terdekat</h4>
        <a href="/penyewa/search" style="text-decoration:none;">
            <input type="text" name="" id="" class="form-control" style="width:60%;" placeholder="Search penginapan terdekat">
        </a>
    </div>
    
    <div class="container" style="justify-content:space-between;">
    @forelse($penginapan as $p)
        <div class="product-card">
            <div class="badge">{{$p->tipe}}</div>
            <div class="product-tumb">
                <img src="/storage/imagesPenginapan/{{$p->id}}_1.jpg" alt="" style="height:100%;width:100%;object-fit:cover;">
            </div>
            <div class="product-details">
                <span class="product-catagory">{{$p->jk_boleh}}</span>
                <h4><a href="/penyewa/penginapan/{{$p->id}}">{{$p->nama}}</a></h4>
                <p style="height:100px;">
                    @php
                        if (strlen($p->deskripsi)>100){
                            echo substr($p->deskripsi,0,100) . " ... ";
                        }else{
                            echo $p->deskripsi;
                        }
                    @endphp
                </p>
                <div class="product-bottom-details">
                    <div class="product-price" style="height:40px;">
                        @php
                            $promo = $p->Promo()->get();
                            if (count($promo)==0){
                                echo 'Rp. '.number_format($p->harga);
                            }else{
                                echo '<p style="text-decoration:line-through;margin-bottom:0px">Rp. '.number_format($p->harga).'</p>';
                            
                                
                                $hargaakhir = 0;
                                foreach($promo as $pro){
                                if ($pro->jenis=="diskon"){
                                    $hargaakhir = $p->harga*(100-$pro->jumlah)/100;
                                }else{
                                    $hargaakhir = $p->harga-$pro->jumlah;
                                }
                            echo 'Rp. '.number_format($hargaakhir);

                            }
                        
                        }
                        @endphp
                    </div>
                    <div class="product-links">
                    </div>
                </div>
            </div>
        </div>
        @empty
            <h2>Tidak ada penginapan</h2>
        @endforelse
    </div>
</div>

@endsection

