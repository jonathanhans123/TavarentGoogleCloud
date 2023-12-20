@extends("layout.layout")
@section('title','Penginap')
@section("extracss")
    <link rel="stylesheet" href="{{asset('/css/penginap.css')}}">
    <link rel="stylesheet" href="{{asset('/css/jquery-ui.css')}}">
    <link rel="stylesheet" type="text/css"
    href="https://js.api.here.com/v3/3.1/mapsjs-ui.css" />
@endsection
@section("extrajs")
    <script src="{{asset('/java/penginap.js')}}"></script>
    <script src="{{asset('/java/jquery-ui.js')}}"></script>
    <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.12.9/dist/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
    <script src="https://js.api.here.com/v3/3.1/mapsjs-core.js"
     type="text/javascript" charset="utf-8"></script>
    <script src="https://js.api.here.com/v3/3.1/mapsjs-service.js"
    type="text/javascript" charset="utf-8"></script>
    <script src="https://js.api.here.com/v3/3.1/mapsjs-ui.js"
    type="text/javascript" charset="utf-8"></script>
    <script src="https://js.api.here.com/v3/3.1/mapsjs-mapevents.js"
    type="text/javascript" charset="utf-8"></script>
@endsection
@section('navbar')
    @include("navbar.navbarpenginap")
@endsection
@section('content')
<div class="container" style="padding-bottom:1000px;">
    <div class="jumbotron" style="height:400px;margin-top:100px;border:2px solid black;box-shadow:1px 1px 5px 0px; border-radius:10px;overflow:hidden;">
        <div class="carousel" style="float:left;height:398px;width:50%;">
            <div id="carouselExampleControls" class="carousel slide h-100" data-ride="carousel">
                <div class="carousel-inner h-100">
                    @php
                        $value = 1;
                        foreach($photos as $photo){
                            if (substr($photo,17,1)==$penginapan->id){
                                if ($value==1){
                                    echo '
                                    <div class="carousel-item active h-100">
                                    <img class="d-block" src="/storage/'.$photo.'" style="object-fit:cover;height:100%;">
                                    </div>
                                    ';
                                }else{
                                    echo '
                                    <div class="carousel-item h-100">
                                    <img class="d-block w-100" src="/storage/'.$photo.'" style="object-fit:cover;height:100%;">
                                    </div>
                                    ';
                                }
                                $value++;
                            }
                        }
                    @endphp
                </div>
                <a class="carousel-control-prev" href="#carouselExampleControls" role="button" data-slide="prev">
                    <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                </a>
                <a class="carousel-control-next" href="#carouselExampleControls" role="button" data-slide="next">
                    <span class="carousel-control-next-icon" aria-hidden="true"></span>
                </a>
            </div>
        </div>
        <div id="mapContainer" style="float:left;width:50%;height:100%;">

        </div>
    </div>
    <div class="body">
        <div class="left" style="float:left;width:60%;">
            <div style="overflow: hidden;">
                <div style="float:left;">
                    <p class="hint">{{strtoupper($penginapan->jk_boleh)}}</p>
                    <h1 style="margin-top:0px;font-size:25pt;font-weight:bold;">{{$penginapan->nama}}</h1>
                    <!-- <p class="hint">Tipe</p> -->
                    <div class="kotak" style="border:1px solid gray; text-align:center;padding-left:20px;width:fit-content;padding-right:20px;padding-top:5px;padding-bottom:5px;border-radius:10px;">
                        {{$penginapan->tipe}}
                    </div>
                    <div class="alamat" style="margin-top:20px;margin-bottom:20px;">
                        <svg xmlns="http://www.w3.org/2000/svg" style="float:left" width="30" height="30" fill="currentColor" class="bi bi-geo-alt" viewBox="0 0 16 16">
                            <path d="M12.166 8.94c-.524 1.062-1.234 2.12-1.96 3.07A31.493 31.493 0 0 1 8 14.58a31.481 31.481 0 0 1-2.206-2.57c-.726-.95-1.436-2.008-1.96-3.07C3.304 7.867 3 6.862 3 6a5 5 0 0 1 10 0c0 .862-.305 1.867-.834 2.94zM8 16s6-5.686 6-10A6 6 0 0 0 2 6c0 4.314 6 10 6 10z"/>
                            <path d="M8 8a2 2 0 1 1 0-4 2 2 0 0 1 0 4zm0 1a3 3 0 1 0 0-6 3 3 0 0 0 0 6z"/>
                        </svg>
                        <p style="float:left">{{$penginapan->alamat}}</p>
                    </div>
                </div>
                <form action="{{ route('toggle') }}" method="post"  style="float:right;margin-top:50px;margin-right:20px;">
                @csrf
                <button style="background-color:white;border:none;">
                <div class="favorite">
                    <input type="hidden" class="fav" value="{{$fav}}">
                    <input type="hidden" name="id_penginapan" value="{{$penginapan->id}}">
                    <input type="hidden" name="id_penginap" value="{{Session::get('penyewa')->id}}">
                    
                </div>
                </button>
                </form>
            </div>
            
            
            <hr>
            <div style="width: 100%;overflow:hidden;">   
                <p style="font-size:15pt;font-weight:bold;margin-bottom:0px">{{$penginapan->tipe}} ini dikelola oleh {{$penginapan->Pemilik->nama_lengkap}}</p><br>
                <svg xmlns="http://www.w3.org/2000/svg" style="margin-top:20px;float:left;" width="70" height="70" fill="currentColor" class="bi bi-person-circle" viewBox="0 0 16 16">
                    <path d="M11 6a3 3 0 1 1-6 0 3 3 0 0 1 6 0z"/>
                    <path fill-rule="evenodd" d="M0 8a8 8 0 1 1 16 0A8 8 0 0 1 0 8zm8-7a7 7 0 0 0-5.468 11.37C3.242 11.226 4.805 10 8 10s4.757 1.225 5.468 2.37A7 7 0 0 0 8 1z"/>
                </svg>
                <p style="float:left;line-height:110px;margin-left:20px;font-size:15pt;">{{$penginapan->Pemilik->nama_lengkap}}</p>
                <a href="/penyewa/chat/{{$penginapan->Pemilik->id}}" style="float:right;padding-top:35px"><input type="button" value="Hubungi" class="btn btn-success"></a>
            </div>
            <hr>
            @php
                $fasilitas = explode(",",$penginapan->fasilitas);
                array_pop($fasilitas);
            @endphp
            <div>
                <p style="font-size:15pt;font-weight:bold;margin-bottom:0px;">Fasilitas yang Tersedia</p><br>
                <p class="hint" style="margin-top:0px;margin-bottom:10px;opacity:0.7;">Penginapan ini menyiapkan fasilitas-fasilitas tersebut:</p>
                <ul>
                @foreach($fasilitas as $f)
                    <li style="margin-bottom:10px;margin-left:0px;">{{$f}}</li>  
                @endforeach
                </ul>
            </div>
            <hr>
            <div>
            <p style="font-size:15pt;font-weight:bold;margin-bottom:0px;">Deskripsi Pemilik</p><br>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;{{$penginapan->deskripsi}}
            </div>
        </div>
        <div class="right" style="float:left;width:40%;margin-top:40px;padding-left:20px;">
            <div class="kotakbeli" style="box-shadow:2px 2px 5px 0px;padding:10px;border-radius:20px;overflow:hidden">
            <form action="" method="post">@csrf
                @php
                    $promo = $penginapan->Promo()->get();
                    if (count($promo)==0){
                        echo '<p style="margin-top:20px;font-size:15pt;font-weight:bold;text-align:center">Rp. '.number_format($penginapan->harga) .'</p>';
                        echo '<input type="hidden" name="hargaakhir" value="'.$penginapan->harga.'">';
                    }else{
                        echo '<p style="text-decoration:line-through;margin-bottom:0px;text-align:center;">Rp. '.number_format($penginapan->harga).'</p>';
                    
                        
                        $hargaakhir = 0;
                        foreach($promo as $pro){
                        if ($pro->jenis=="diskon"){
                            $hargaakhir = $penginapan->harga*(100-$pro->jumlah)/100;
                        }else{
                            $hargaakhir = $penginapan->harga-$pro->jumlah;
                        }
                        echo '<p style="margin-top:5px;font-size:15pt;font-weight:bold;text-align:center">Rp. '.number_format($hargaakhir).'</p>';
                        echo '<input type="hidden" name="hargaakhir" value="'.$hargaakhir.'">';
                        }
                    }
                
                
                @endphp
                
                <div style="width: 100%;">
                    <p class="hint" style="width: 45%;float:left;">Tanggal Mulai</p>
                    <p class="hint" style="width: 45%;float:left;margin-left:10%;">Berapa Bulan?</p>
                </div>
                <input type="hidden" name="id_penginapan" value="{{$penginapan->id}}">
                <div style="width: 100%;margin-top:20px;">
                    
                    <input type="date" name="date" class="form-date" id="" style="width: 45%;float:left;border-radius:10px;margin-right:5%;padding:5px;">
                    <input type="number" name="bulan" id="" style="width: 45%;float:left;border-radius:10px;margin-left:5%;padding:5px;">
                </div>
                @error("date")
                    <p class="text-danger">{{$message}}</p>
                @enderror
                @error("bulan")
                    <p class="text-danger">{{$message}}</p>
                @enderror
                <input type="submit" value="Sewa Penginapan" id="pay-button" class="btn btn-success" style="width:100%;margin-top:30px;">
                </form>
            </div>
        </div>
        
        
        

    </div>
</div>
<input type="hidden" id="koordinat" val="{{$penginapan->koordinat}}">
    <script>
        function start(){
            var koordinat = $("#koordinat").attr("val");
            var latitude = koordinat.substring(0, koordinat.indexOf(','));
            var longitude = koordinat.substring(koordinat.indexOf(',')+1, koordinat.length-1);
            var platform = new H.service.Platform({
                    'apikey': 'rQWmEReEoxYDzqrD4qDxp08gW5ZGMBv_0zXDW547jRg'
            });
            var defaultLayers = platform.createDefaultLayers();
            var map = new H.Map(
                document.getElementById('mapContainer'),
                defaultLayers.vector.normal.map,
                {
                    zoom: 14,
                    center: { lat: latitude, lng: longitude }
                }
            );
            var mapevents = new H.mapevents.MapEvents(map)
            var ui = H.ui.UI.createDefault(map, defaultLayers);
            ui.getControl('mapsettings').setDisabled(true)
            map.addEventListener('tap', function(evt) {
                // Log 'tap' and 'mouse' events:
                console.log(evt.type, evt.currentPointer.type); 
            });
            var behavior = new H.mapevents.Behavior(mapevents);
            var marker = new H.map.Marker({ lat: latitude, lng: longitude});
            map.addObject(marker);
        };
        if ($(".fav").val()=="0"){
            $(".favorite").append('<svg xmlns="http://www.w3.org/2000/svg" width="40" height="40" fill="currentColor" class="bi bi-heart" viewBox="0 0 16 16"><path d="m8 2.748-.717-.737C5.6.281 2.514.878 1.4 3.053c-.523 1.023-.641 2.5.314 4.385.92 1.815 2.834 3.989 6.286 6.357 3.452-2.368 5.365-4.542 6.286-6.357.955-1.886.838-3.362.314-4.385C13.486.878 10.4.28 8.717 2.01L8 2.748zM8 15C-7.333 4.868 3.279-3.04 7.824 1.143c.06.055.119.112.176.171a3.12 3.12 0 0 1 .176-.17C12.72-3.042 23.333 4.867 8 15z"/></svg>');    
        }else{
            $(".favorite").append('<svg xmlns="http://www.w3.org/2000/svg" width="40" height="40" fill="#f00" class="bi bi-heart-fill" viewBox="0 0 16 16"><path fill-rule="evenodd" d="M8 1.314C12.438-3.248 23.534 4.735 8 15-7.534 4.736 3.562-3.248 8 1.314z"/></svg>');
        }
        
    </script>
    
    @php
        echo $java;
    @endphp
@endsection