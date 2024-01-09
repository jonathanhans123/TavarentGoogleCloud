<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use Illuminate\Support\Facades\Storage;

class GaleriController extends Controller
{
    public function upload(Request $request){
        return redirect('pemilik/kelola');
    }

    public function doUpload(Request $request){

        $value =1 ;
        $files = $_FILES;
        $destinationPath = 'HomeImages';
        $totalimage= count($_FILES['photo']['name']);
        $files = Storage::disk('public-folder')->allFiles();
        foreach($files as $cek){
            $value+=1;
        }

        for ($i = 0; $i < $totalimage; $i++) {
            move_uploaded_file($_FILES['photo']['tmp_name'][$i],public_path($destinationPath).'/'. $value.".jpg");
            $value++;
        }
        return redirect('galeri/upload');
    }
}
