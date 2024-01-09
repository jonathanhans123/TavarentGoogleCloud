
	<div class="navigation-wrap bg-light start-header start-style">
		<div class="container">
			<div class="row">
				<div class="col-12">
					<nav class="navbar navbar-expand-md navbar-light">

						<a href="/pemilik"><img src="{{ asset('img/tavarentlogo-removebg-big.png') }}" width="70px" alt=""></a>

						<div class="collapse navbar-collapse" id="navbarSupportedContent">
							<ul class="navbar-nav ml-auto py-4 py-md-0">
                                <li class="nav-item pl-4 pl-md-0 ml-0 ml-md-4">
									<a class="nav-link" href="/pemilik">Home</a>
								</li>
								<li class="nav-item pl-4 pl-md-0 ml-0 ml-md-4">
									<a class="nav-link" href="/pemilik/chat">Chat</a>
								</li>
								<li class="nav-item pl-4 pl-md-0 ml-0 ml-md-4">
									<a class="nav-link" href="/pemilik/kelola">Kelola</a>
								</li>
								<li class="nav-item pl-4 pl-md-0 ml-0 ml-md-4">
									<a class="nav-link" href="/pemilik/promo">Promo</a>
								</li>
								<li class="nav-item pl-4 pl-md-0 ml-0 ml-md-4">
									<a class="nav-link" href="/pemilik/profil">Profil</a>
								</li>
								<li class="nav-item pl-4 pl-md-0 ml-0 ml-md-4">
									<a class="nav-link" href="/pemilik/notifikasi">Notifikasi</a>
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
