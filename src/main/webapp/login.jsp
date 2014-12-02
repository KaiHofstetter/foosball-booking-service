<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Foosball Booking Service</title>
<link type="text/css" rel="stylesheet"
	href="./webjars/bootstrap/3.2.0/css/bootstrap.min.css" />
<script type="text/javascript" src="./webjars/jquery/2.1.1/jquery.min.js"></script>
<script type="text/javascript"
	src="./webjars/bootstrap/3.2.0/js/bootstrap.min.js"></script>
</head>

<body>

	<div class="container">

		<h1>Foosball Booking Service</h1>

		<c:if test="${not empty param.authentication_error}">
			<h1>Woops!</h1>

			<p class="error">Your login attempt was not successful.</p>
		</c:if>
		<c:if test="${not empty param.authorization_error}">
			<h1>Woops!</h1>

			<p class="error">You are not permitted to access that resource.</p>
		</c:if>

		<div class="form-horizontal">
			<p>We've got a grand total of 2 users: Peter and Bob. Go ahead
				and log in. The password for both is 'secret'.</p>
			<form action="<c:url value="/login.do"/>" method="post" role="form">
				<fieldset>
					<legend>
						<h2>Login</h2>
					</legend>
					<div class="form-group">
						<label for="username">Username:</label> <input id="username"
							class="form-control" type='text' name='username'
							value="Peter" />
					</div>
					<div class="form-group">
						<label for="password">Password:</label> <input id="password"
							class="form-control" type='text' name='password' value="secret" />
					</div>
					<button class="btn btn-primary" type="submit">Login</button>
					<input type="hidden"
						   name="${_csrf.parameterName}"
						   value="${_csrf.token}"/>
				</fieldset>
			</form>
		</div>
	</div>
</body>
</html>
