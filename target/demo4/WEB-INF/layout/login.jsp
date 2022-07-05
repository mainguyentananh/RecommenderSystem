<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<html lang="en">
<head>
<!-- Required meta tags -->
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<link
	href="https://fonts.googleapis.com/css?family=Roboto:300,400&display=swap"
	rel="stylesheet">
<link rel="icon" href="<c:url value="/static/img/logo-ctu.png"/>" />
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3"
	crossorigin="anonymous">
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"
	integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p"
	crossorigin="anonymous"></script>

<!-- Style -->
<link rel="stylesheet" href="<c:url value="/static/css/sfLogin.css"/>">

<title>Login</title>
</head>
<body>

	<div class="container">
		<div class="col">

			<div id="carouselExampleSlidesOnly" class="carousel slide"
				data-bs-ride="carousel">
				<div class="carousel-inner">
					<div class="carousel-item active">
						<img src="<c:url value="/static/img/bg1.jpg"/>" class="d-block w-100"
							alt="Hình bộ môn Khoa Học Máy Tính CTU">
					</div>

					<div class="carousel-item">
						<img src="<c:url value="/static/img/bg2.jpg"/>" class="d-block w-100 "
							alt="Hình giảng viên bộ môn Khoa Học Máy Tính CTU">
					</div>
				</div>
			</div>
		</div>
	</div>

	<div class="half">
		<div class="contents ">
			<div class="container">
				<div class="row align-items-center justify-content-center">
					<div class="col-md-6">
						<div class="form-block">
							<div class="text-center mb-5">
								<h3>Welcome!</h3>
									${message}
							</div>
							
							<form action="<c:url value="/j_spring_security_check"/>" method="POST">
								<div class="form-group">
									<label for="username">Username</label> <input type="text"
										class="form-control" placeholder="Your Student Code"
										name="username">
								</div>
								<div class="form-group  mb-3">
									<label for="password">Password</label> <input type="password"
										class="form-control" placeholder="Your Password"
										name="password">
								</div>

								<div class="d-sm-flex align-items-center">
									<div class="form-check text-muted">
										<input class="form-check-input" type="checkbox"
											name="remember-me" id="flexCheckDefault"> <label
											class="form-check-label" for="flexCheckDefault">
											Remember me </label>
									</div>
									
								</div>
							
								<div class="mt-3">
								<input type="submit" value="Log In"
									class="btn btn-lg btn-primary">
								</div>
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>