<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>@yield('title')</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-iYQeCzEYFbKjA/T2uDLTpkwGzCiq6soy8tYaI1GyVh/UjpbCx/TYkiZhlZB6+fzT" crossorigin="anonymous">
    <link rel="stylesheet" href="{{url('css/main.css')}}">
</head>
<body>
    <section class="ftco-section">
		<div class="container" style="height:90vh;">
			<div class="row justify-content-center">
				<div class="col-lg-10 col-md-12 mt-5 mb-5">
					<div class="wrap d-md-flex rounded-4" style="background-color:#D9D9D9">
						<div class="img rounded-left" style="width:50%; background-repeat:no-repeat; background-image:url('img/tavarentlogo-removebg-big.png');background-position-y:50%;background-position-x:50%;object-fit:contain;"></div>
						<div class="login-wrap pt-4 pb-4 p-md-5" style="width: 50%">
                            <div class="d-flex">
                                <div class="w-100">
                                    <h3 class="mb-4">@yield('title')</h3>
                                </div>
                            </div>
							@yield('content')
		                </div>
		            </div>
				</div>
			</div>
		</div>
	</section>
    @include('footer')
</body>
</html>
