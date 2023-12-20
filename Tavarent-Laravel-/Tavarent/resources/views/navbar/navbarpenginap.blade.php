
	<div class="navigation-wrap bg-light start-header start-style">
		<div class="container">
			<div class="row">
				<div class="col-12">
					<nav class="navbar navbar-expand-md navbar-light">
					
						<a href="/penyewa"><img src="{{ asset('img/tavarentlogo-removebg-big.png')}}" width="50px" alt=""></a>	
						
						<div class="collapse navbar-collapse" id="navbarSupportedContent">
							<ul class="navbar-nav ml-auto py-4 py-md-0">
                                <li class="nav-item pl-4 pl-md-0 ml-0 ml-md-4">
									<a class="nav-link" href="/penyewa">Cari</a>
								</li>
								<li class="nav-item pl-4 pl-md-0 ml-0 ml-md-4">
									<a class="nav-link" href="/penyewa/chat">Chat</a>
								</li>
								<li class="nav-item pl-4 pl-md-0 ml-0 ml-md-4">
									<a class="nav-link" href="/penyewa/favorit">Favorit</a>
								</li>
								<li class="nav-item pl-4 pl-md-0 ml-0 ml-md-4">
									<a class="nav-link" href="/penyewa/kossaya">Kos Saya</a>
								</li>
								<li class="nav-item pl-4 pl-md-0 ml-0 ml-md-4">
									<a class="nav-link" href="/penyewa/profil">Profil</a>
								</li>
								<li class="nav-item pl-4 pl-md-0 ml-0 ml-md-4">
									<a class="nav-link" href="/penyewa/notifikasi">Notifikasi</a>
								</li>
							</ul>
						</div>

						<form action="{{url('logout')}}" method="post" style="float:right;">
						@csrf
							<input type="submit" value="Logout" class="btn btn-danger">
						</form>
					</nav>
				</div>
			</div>
		</div>
	</div>
