<?php

namespace App\Http\Middleware;

use App\Models\Log;
use Closure;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Session;

class cekUserPemilik
{
    /**
     * Handle an incoming request.
     *
     * @param  \Illuminate\Http\Request  $request
     * @param  \Closure(\Illuminate\Http\Request): (\Illuminate\Http\Response|\Illuminate\Http\RedirectResponse)  $next
     * @return \Illuminate\Http\Response|\Illuminate\Http\RedirectResponse
     */
    public function handle(Request $request, Closure $next)
    {
        if(Session::has('cekuser')){
            if(Session::get('cekuser')=="penginap"){
                return redirect('/penyewa');
            }
            if(Session::get('cekuser')=="admin"){
                return redirect('/admin');
            }
        }else if(isset($_COOKIE['cekRawUser'])){
            if(hash('sha256', 'penginap')==$_COOKIE['cekRawUser']){
                return redirect('/penyewa');
            }
            if(hash('sha256', 'admin')==$_COOKIE['cekRawUser']){
                return redirect('/admin');
            }
        }
        else{
            return redirect('login');
        }
        return $next($request);
    }
}
