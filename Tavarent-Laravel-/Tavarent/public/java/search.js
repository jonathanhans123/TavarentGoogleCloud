$(document).ready(function(){ 
    alert($("#lat").val()+$("#lng").val());
    if ($("#lat").val()==""&&$("#lng").val()==""){
        
        if (navigator.geolocation) {
            navigator.geolocation.getCurrentPosition(changeHeader);
        } else { 
            alert("iowejfiojw");
        }
    }else{
        if ($("$alamat")!=""){
            $("#searchtextinput").val($("#alamat").val());
        }
        getLocation({lat:$("#lat").val(), lng:$("#lng").val()});
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
        navigator.geolocation.getCurrentPosition({lat:$("#lat").val(), lng:$("#lng").val()});
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
                $('#alamat').val(autocomplete[ui.item.idx].label);
                $('#lat').val(location[ui.item.idx].koordinat.lat);
                $('#lng').val(location[ui.item.idx].koordinat.lng);
                map.setCenter(location[ui.item.idx].koordinat);
                map.removeObject(marker);
                marker = new H.map.Marker(location[ui.item.idx].koordinat);
                map.addObject(marker);
            }
        }).focus(function () {
            $(this).autocomplete("search");
        });

    }
    
    
});
     
               