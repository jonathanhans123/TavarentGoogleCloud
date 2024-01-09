@extends('layout.layout')
@section('title','Pemilik')
@section("extracss")
    <link rel="stylesheet" href="{{asset('/css/jquery-ui.css')}}">
    <link rel="stylesheet" href="{{asset('/css/penginap.css')}}">
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
    @include("navbar.navbarpemilik")
@endsection
@section('content')

<div class="container" style="margin-top:150px; height:1000px;">
    <form action="" method="POST" class="signin-form" enctype='multipart/form-data'>
        @csrf
        <input type="hidden" id="hidden" name="hidden" value="login">
        <div class="form-group mb-3">
            <label class="label" for="name">Upload Foto:</label>
            <input type="file" name="photo[]" id="photo" class="form-control" accept="image/jpeg" multiple >
            @error("photo")
                <label class="label text-danger" for="nproperti">{{$message}}</label>
            @enderror
        </div>
        <div class="form-group mb-3">
            <label class="label" for="name">Nama Properti</label>
            <input type="text" class="form-control" placeholder="Nama Properti" value="{{ old('nproperti') }}" name="nproperti">
            @error("nproperti")
                <label class="label text-danger" for="nproperti">{{$message}}</label>
            @enderror
        </div>
        <div class="form-group mb-3">
            <label class="label" for="name">Deskripsi Tambahan</label>
            <input type="text" value="{{ old('deskripsi') }}" class="form-control" placeholder="Deskripsi Tambahan" name="deskripsi">
            @error("deskripsi")
                <label class="label text-danger" for="deskripsi">{{$message}}</label>
            @enderror
        </div>
        <div class="form-group mb-3">
            <label class="label" for="name">Harga Perbulan</label>
            <input type="text" value="{{ old('harga') }}" class="form-control" placeholder="Harga Perbulan" name="harga">
            @error("harga")
                <label class="label text-danger" for="harga">{{$message}}</label>
            @enderror
        </div>
        <div class="form-group mb-3">
            <label class="label" for="name">Jenis Kelamin yang diperbolehkan</label>
            <select name="selector">
                <option value="campur">Campur</option>
                <option value="pria">Pria</option>
                <option value="wanita">Wanita</option>
            </select>
            @error("jkelamin")
                <label class="label text-danger" for="jkelamin">{{$message}}</label>
            @enderror
        </div>
        <div class="form-group mb-3">
            <label class="label" for="name">Alamat</label>
            <small class="form-text text-muted">Pilih salah satu alamat dari autosuggest!</small>
            <input type="hidden" value="{{ old('koordinat') }}" name="koordinat" id="koordinat">
            <input type="text" value="{{ old('alamat') }}" class="form-control" id="alamat" placeholder="Masukkan Alamat" name="alamat">
            @error("alamat")
                <label class="label text-danger" for="alamat">{{$message}}</label>
            @enderror
            @error("koordinat")
                <label class="label text-danger" for="alamat">{{$message}}</label>
            @enderror
            <div style="height:300px;border-radius:10px;border:1px solid gray; overflow:hidden" id="mapContainer"></div>

        </div>
        <div class="form-group mb-3">
            <label class="label" for="name">Fasilitas Minimal 3</label>
            <div>
                <input type="checkbox" name="ac" value="yes">
                <label >Air Conditioner</label>
                <input type="checkbox" name="termasuklistrik" value="yes">
                <label >Termasuk Listrik</label>
                <input type="checkbox" name="kdalam" value="yes">
                <label >K. Mandi Dalam</label>
                <br>
                <input type="checkbox" name="kursi" value="yes">
                <label >Kursi</label>
                <input type="checkbox" name="meja" value="yes">
                <label >Meja</label>
                <input type="checkbox" name="wifi" value="yes">
                <label >Wifi</label>
                <br>
                <input type="checkbox" name="kasurdouble"  value="yes">
                <label >Kasur Double</label>
                <input type="checkbox" name="tv" value="yes">
                <label >Tv</label>
                <input type="checkbox" name="kasursingle" value="yes">
                <label >Kasur Single</label>
                <br>
                <input type="checkbox" name="jendela"  value="yes">
                <label >Jendela</label>
                <input type="checkbox" name="airpanas" value="yes">
                <label >Air Panas</label>
                <input type="checkbox" name="dapur" value="yes">
                <label >Dapur</label>

        </div> <br>
        <label class="label" for="name">Jenis Penginapan</label>
        <div class="form-group mb-4">
            <label class="label" for="name">Apartemen</label> <input type="radio" name="rbJenis" value="Apartemen"><i class="validation"></i>
            <label class="label" for="name">Kos</label>  <input type="radio" name="rbJenis" value="Kos"><i class="validation"></i><br>
            @error("rbJenis")
                <label class="label text-danger" for="rbJenis">{{$message}}</label>
            @enderror
        </div>
            @if(Session::has("success"))
            <label class="label text-success">{{Session::get("success")}}</label>
            @endif
            <button type="submit" class="button form-control rounded px-3" >Daftar</button>
        </div>
    </form>
</div>
<script>
    $(document).ready(function(){ 
        if (navigator.geolocation) {
            navigator.geolocation.getCurrentPosition(getLocation);
        } else { 
            alert("Nyalakan Lokasi");
        }    
        
        var platform;
        var map;
        var marker;

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
                    center: { lat: position.coords.latitude, lng: position.coords.longitude }
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

            
            marker = new H.map.Marker({ lat: position.coords.latitude, lng: position.coords.longitude });
            // Add the marker to the map and center the map at the location of the marker:
            map.addObject(marker);
            map.setCenter({ lat: position.coords.latitude, lng: position.coords.longitude });
        }

        var typingTimer;                //timer identifier
        var doneTypingInterval = 2000;  //time in ms, 5 seconds for example

        $("#alamat").on("keydown",function(){
            clearTimeout(typingTimer)
        });
        $("#alamat").on('keyup', function () {
            clearTimeout(typingTimer);
            typingTimer = setTimeout(doneTyping, doneTypingInterval);
        });

        function doneTyping () {
            console.log("eiwjfoe");
            if (navigator.geolocation) {
                navigator.geolocation.getCurrentPosition(getAddress);
            }
        }

        function getAddress(position){
            var autocomplete = [];
            var location = [];

            var platform = new H.service.Platform({
                'apikey': 'rQWmEReEoxYDzqrD4qDxp08gW5ZGMBv_0zXDW547jRg'
            });
            var service = platform.getSearchService();

            service.autosuggest({
            // Search query
                q: String($('#alamat').val()),
                at: position.coords.latitude+','+position.coords.longitude,
            }, (result) => {

                var idx = 1;
                result.items.forEach((item)=>{
                    autocomplete.push({label:item.title,idx:idx});
                    location.push({koordinat:item.position,idx:idx})
                    idx++;
                });
            });
            console.log(autocomplete.toString());
            $("#alamat").autocomplete({
                source: autocomplete,
                minLenght: 0,
                autoFocus: true,
                select: function(event,ui){
                    console.log($("#koordinat").val());
                    $("#koordinat").val(location[ui.item.idx].koordinat.lat+","+location[ui.item.idx].koordinat.lng);
                    console.log($("#koordinat").val());
                
                }
            }).focus(function () {
                $(this).autocomplete("search");
            });
            $("#alamat").attr("autocomplete", "on");

        }
        
        
    });
</script>
@endsection

