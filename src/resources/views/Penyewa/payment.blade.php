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
    <script type="text/javascript"
      src="https://app.sandbox.midtrans.com/snap/snap.js"
      data-client-key="SB-Mid-client-CAcpC230ptHYx8Z1"></script>
@endsection
@section('navbar')
    @include("navbar.navbarpenginap")
@endsection
@section('content')

    @php
    // Set your Merchant Server Key
    \Midtrans\Config::$serverKey = 'SB-Mid-server-NMVGX2Rc1aiDP37T7ABu93tq';
    // Set to Development/Sandbox Environment (default). Set to true for Production Environment (accept real transaction).
    \Midtrans\Config::$isProduction = false;
    // Set sanitization on (default)
    \Midtrans\Config::$isSanitized = true;
    // Set 3DS transaction for credit card to true
    \Midtrans\Config::$is3ds = true;
    
    $params = array(
        'transaction_details' => array(
            'order_id' => $order_id,
            'gross_amount' => $gross_amount,
        ),
        'customer_details' => array(
            'first_name' => $first_name,
            'last_name' => $last_name,
            'email' => $email,
            'phone' => $phone,
        ),
    );
    
    $snapToken = \Midtrans\Snap::getSnapToken($params);
    echo "<script type='text/javascript'>
      function start(){
      // For example trigger on button clicked, or any time you need
      var payButton = document.getElementById('pay-button');
      payButton.addEventListener('click', function () {
        // Trigger snap popup. @TODO: Replace TRANSACTION_TOKEN_HERE with your transaction token
        window.snap.pay('$snapToken', {
          onSuccess: function(result){
            var total = $('#total').val();
            var tanggal_mulai = $('#tanggal_mulai').val();
            var bulan = $('#bulan').val();
            var id_penginapan = $('#id_penginapan').val();
            window.location.href = '/penyewa/insertpembayaran?total='+total+'&tanggal_mulai='+tanggal_mulai+'&bulan='+bulan+'&id_penginapan='+id_penginapan;
          },
          
        })
      });
    }
    </script>";
    @endphp
    <div class="container-fluid" style="background-image:url( {{asset('/img/housebg.jpg')}} );background-position-y:center;background-size:cover;height:100vh;padding-top:100px;">
    <div style="background-color:white; width:500px ; height:95%;padding:30px;border-radius:30px;margin-left:5%;box-shadow:0px 0px 10px 1px gray;opacity:0.95; ">
    <h4>Total</h4>
    <p class="hint" style="margin-top:0px;margin-bottom:10%;">Rp. {{number_format($gross_amount)}}</p>
    <h4>Tanggal Mulai</h4>
    <p class="hint" style="margin-top:0px;margin-bottom:10%;">{{$tanggal_mulai}}</p>
    <h4>Tanggal_Selesai</h4>
    <p class="hint" style="margin-top:0px;margin-bottom:10%;">{{$tanggal_selesai}}</p>
    
    <button class="btn btn-secondary" style="width:100%;" id="pay-button">Pay!</button>
    
    <input type="hidden" id="total" value="{{$gross_amount}}">
    <input type="hidden" id="tanggal_mulai" value="{{$tanggal_mulai}}">
    <input type="hidden" id="bulan" value="{{$bulan}}">
    <input type="hidden" id="id_penginapan" value="{{$id_penginapan}}">
    </div>
    </div>
    
    @php
      echo $java;
    @endphp
@endsection