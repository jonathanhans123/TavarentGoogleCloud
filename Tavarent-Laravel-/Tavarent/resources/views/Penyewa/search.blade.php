@extends('layout.layout')
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
    <div class="container" style="margin-top:100px;">
        <input type="hidden" name="" val="{{$alamat}}" id="alamat">
        <input type="hidden" name="" val="{{$lat}}" id="lat">
        <input type="hidden" name="" val="{{$lng}}" id="lng">
        <div class="left" style="width:50%;float:left;">
            <div style="width:100%;margin-bottom:30px;height:auto;overflow:hidden;">
                <input type="text" name="" id="searchtextinput" class="form-control" style="width:80%; float:left;" placeholder="Search penginapan terdekat">
                <input type="button" value="Search" id="search" class="btn btn-warning" style="width:18%;margin-left:2%;float:left;">
            </div>
            @forelse($penginapan as $p)
            <a href="/penyewa/penginapan/{{$p->id}}" style="color:black;">
            <div class="kotak" style="border:1px solid gray; border-radius:10px;margin-top:10px;margin-bottom:10px;height:auto;overflow:hidden;padding:20px;position:relative;">
                <img src="/storage/imagesPenginapan/{{$p->id}}_1.jpg" alt="gambar penginapan" style="float:left;object-fit:cover;background-color:gray;" width="160px" height="160px">
                <div class="right" style="width:calc(95% - 160px);float:left;margin-left:5%;">
                    <h4 style="font-size:15pt;margin-bottom:0px;">{{$p->nama}}</h4>
                    <p style="font-size:10pt;">{{$p->alamat}}</p>
                    <p style="font-size:10pt;">
                        @php
                            if(ceil($p->distance)>=1000){
                                echo round(ceil($p->distance)/1000,2). " kilometer";
                            }else{
                                echo ceil($p->distance)." meter";
                            }
                        @endphp
                        
                    </p>
                    <div style="position:absolute;right:20px;top:20px;font-weight:bold;">{{$p->tipe}}</div>
                    <div style="position:absolute;right:20px;bottom:20px;border:2px solid lightblue;border-radius:5px;padding:2px;font-size:10pt;">{{$p->jk_boleh}}</div>
                    <div class="product-price">
                    @php
                        $promo = $p->Promo()->get();
                        if (count($promo)==0){
                            echo 'Rp. '.number_format($p->harga);
                        }else{
                            echo '<p style="text-decoration:line-through;margin-bottom:0px;color:gray;font-size:11pt;">Rp. '.number_format($p->harga).'</p>';
                        
                            
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
                </div>
            </div>
            </a>
            @empty
            <h4>Tidak ada penginapan di dekat anda</h4>
            @endforelse
        </div>
        <div class="right" style="width:50%;float:left;padding-left:5%;">
            <div style="position:fixed;top:20%;left:55%;width: 40%; height: 70%;border-radius:10px;border:1px solid gray; overflow:hidden" id="mapContainer"></div>
        </div>
    </div>
    
    <script>

        function start(){ 
            if ($("#lat").attr("val")==""||$("#lng").attr("val")==""){
                if (navigator.geolocation) {
                    navigator.geolocation.getCurrentPosition(changeHeader);
                } else { 
                    alert("iowejfiojw");
                }
            }else{
                var position = {
                    lat:$("#lat").attr("val"), 
                    lng:$("#lng").attr("val")
                }
                getLocation(position);
                if ($("#alamat")!=""){
                    $("#searchtextinput").val($("#alamat").attr("val"));
                    getAddress(position)
                }
                
                
            }
            
            var platform;
            var map;
            var marker;
            function changeHeader(position){
                window.location.href = "/penyewa/search/"+position.coords.latitude+"/"+position.coords.longitude;
            }
            function getLocation(position){
                platform = new H.service.Platform({
                    'apikey': 'rQWmEReEoxYDzqrD4qDxp08gW5ZGMBv_0zXDW547jRg'
                });
                var defaultLayers = platform.createDefaultLayers();
                map = new H.Map(
                    document.getElementById('mapContainer'),
                    defaultLayers.vector.normal.map,
                    {
                        zoom: 14,
                        center: { lat: position.lat, lng: position.lng }
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

                
                marker = new H.map.Marker({ lat: position.lat, lng: position.lng });
                // Add the marker to the map and center the map at the location of the marker:
                map.addObject(marker);
                map.setCenter({ lat: position.lat, lng: position.lng });
            }
            $('#search').click(function(){
                window.location.href = "/penyewa/search/"+$("#lat").attr("val")+"/"+$("#lng").attr("val")+"/"+$("#searchtextinput").val();
            });


            function getAddress(position){
                var autocomplete = [];
                var location = [];

                var platform = new H.service.Platform({
                    'apikey': 'rQWmEReEoxYDzqrD4qDxp08gW5ZGMBv_0zXDW547jRg'
                });
                var service = platform.getSearchService();

                service.autosuggest({
                // Search query
                    q: String($('#searchtextinput').val()),
                    at: position.lat+','+position.lng,
                }, (result) => {

                    var idx = 1;
                    result.items.forEach((item)=>{
                        autocomplete.push({label:item.title,idx:idx});
                        location.push({koordinat:item.position,idx:idx})
                        idx++;
                    });
                });
                console.log(autocomplete.toString());
                $("#searchtextinput").autocomplete({
                    source: autocomplete,
                    minLenght: 0,
                    autoFocus: true,
                    select: function(event,ui){
                        window.location.href = "/penyewa/search/"+location[ui.item.idx].koordinat.lat+"/"+location[ui.item.idx].koordinat.lng+"/"+autocomplete[ui.item.idx].label;
                    }
                }).focus(function () {
                    $(this).autocomplete("search");
                });

            }
            
            
        };

             
                        
    </script>
    @php
        echo $java;
    @endphp
@endsection