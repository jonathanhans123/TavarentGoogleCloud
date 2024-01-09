<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>@yield('title')</title>
    <link rel="stylesheet" href="{{url('css/bootstrap.min.css')}}">
    <script src="/java/jquery.min.js"></script>
    
    @yield("extracss")
    @yield("extrajs")
</head>
<body class="hero-anime">
    @yield("navbar")
    @yield("content")
    
</body>
</html>
