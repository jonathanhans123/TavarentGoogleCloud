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
<div class="container" style="height:100vh;padding-top:100px;padding-bottom:20px;">
    <div class="left" style="height:100%;width:30%;float:left;border:1px solid black;">
        @php
            for($i=(count($semuachat)-1) ; $i >= 0 ; $i--){
                $exist = false;
                for($j=count($semuachat)-1 ; $j > $i ; $j--){
                    if ($semuachat[$i]->id_penginap == $semuachat[$j]->id_penginap &&
                    $semuachat[$i]->id_pemilik == $semuachat[$j]->id_pemilik){
                    
                        $exist = true;
                        break;
                    }
                }
                
                if (!$exist){
                    echo '<a href="/pemilik/chat/'.$semuachat[$i]->id_penginap.'" style="color:black;text-decoration:none;">
                        <div style="display:flex;flex-direction:row;">
                            <div  style="border:3px solid black;border-radius:50%;width:auto;height:60px;margin-top:10px;margin-right:10px;margin-left:10px;">
                                <svg xmlns="http://www.w3.org/2000/svg" width="50" height="50" fill="currentColor" class="bi bi-person" viewBox="0 0 16 16">
                                    <path d="M8 8a3 3 0 1 0 0-6 3 3 0 0 0 0 6Zm2-3a2 2 0 1 1-4 0 2 2 0 0 1 4 0Zm4 8c0 1-1 1-1 1H3s-1 0-1-1 1-4 6-4 6 3 6 4Zm-1-.004c-.001-.246-.154-.986-.832-1.664C11.516 10.68 10.289 10 8 10c-2.29 0-3.516.68-4.168 1.332-.678.678-.83 1.418-.832 1.664h10Z"/>
                                </svg>
                            </div>
                            <div>
                                <p style="font-weight:bold;margin-bottom:0px;margin-top:10px;">'.$semuachat[$i]->Penginap->nama_lengkap.'</p>
                                <p class="hint" style="margin-top:0px;font-size:10pt;line-height:15px;">';
                    if(strlen($semuachat[$i]->pesan)>70){
                        echo substr($semuachat[$i]->pesan,0,70).'...';
                    }else{
                        echo $semuachat[$i]->pesan;
                    }
                    echo '</p>
                            </div>
                        </div><hr></a>';
                }
            }
        @endphp
        
    </div>
    <div class="right" style="height:100%;width:70%;float:left;border:1px solid black;display:flex;flex-direction:column;">
        @if($penginap==null)
            <h1>Pilih Chat</h1>
        @else
        <div class="top" style="box-shadow: 0 6px 10px -4px black;background-color:lightgreen;padding:10px;display:flex;flex-direction:row;">
            <div style="border:3px solid black;border-radius:50%;margin-left:20px;">
            <svg xmlns="http://www.w3.org/2000/svg" width="50" height="50" fill="currentColor" class="bi bi-person" viewBox="0 0 16 16">
                <path d="M8 8a3 3 0 1 0 0-6 3 3 0 0 0 0 6Zm2-3a2 2 0 1 1-4 0 2 2 0 0 1 4 0Zm4 8c0 1-1 1-1 1H3s-1 0-1-1 1-4 6-4 6 3 6 4Zm-1-.004c-.001-.246-.154-.986-.832-1.664C11.516 10.68 10.289 10 8 10c-2.29 0-3.516.68-4.168 1.332-.678.678-.83 1.418-.832 1.664h10Z"/>
            </svg>
            </div>
            <div style="flex-grow:1;font-size:18pt;margin-left:10px;line-height:50px;font-weight:500;">
                {{$penginap->nama_lengkap}}
            </div>
        </div>
        <div class="mid" style="overflow-y:scroll;flex-grow:1;padding-left:20px;padding-right:20px;padding-top:20px;display:flex;flex-direction:column;">
            @forelse($chat as $c)
                @if($c->sender=="pemilik")
                    <div style="padding:10px 20px 10px 20px;margin-bottom:10px;background-color:lightgray;border-radius:10px;height:auto;width:fit-content;margin-left:auto">
                        {{$c->pesan}}
                    </div>
                @else
                    
                    <div style="padding:10px 20px 10px 20px;margin-bottom:10px;border:1px solid black;border-radius:10px;height:auto;width:fit-content;">
                        {{$c->pesan}}
                    </div>
                @endif
            @empty
                <h4>Kamu masih belum pernah chat dengan penginap ini</h4>
            @endforelse

        </div>
        <div class="bottom" style="background-color:green;overflow:hidden;padding:10px;">
            <form action="/penyewa/chat/{{$penginap->id}}" method="post">
                @csrf
                <input type="text" name="chat" id="" class="form-control" style="width: 90%;float:left;">
                <button style="background-color:transparent;border:none;width:10%;float:left;margin-top:3px;">
                <svg xmlns="http://www.w3.org/2000/svg" width="30" height="30" fill="currentColor" class="bi bi-send-fill" viewBox="0 0 16 16">
                    <path d="M15.964.686a.5.5 0 0 0-.65-.65L.767 5.855H.766l-.452.18a.5.5 0 0 0-.082.887l.41.26.001.002 4.995 3.178 3.178 4.995.002.002.26.41a.5.5 0 0 0 .886-.083l6-15Zm-1.833 1.89L6.637 10.07l-.215-.338a.5.5 0 0 0-.154-.154l-.338-.215 7.494-7.494 1.178-.471-.47 1.178Z"/>
                </svg>
                </button>
            </form>
            
        </div>
        @endif
    </div>
</div>

@endsection

