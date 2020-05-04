<h2>Login</h2>

<form action="/login" method="post" class="main-form needs-validation" novalidate>
    <div class="alert alert-warning">
        ${authenticationErrorMessage}
    </div>

    <div class="form-group">
        <label for="email">Email</label>
        <input type="text" id="email" name="email" class="form-control" required>
    </div>

    <div class="form-group">
        <label for="pwd">Password</label>
        <input type="password" id="pwd" name="password" class="form-control" required>
    </div>

    <button type="submit" class="btn btn-success">Login</button>
    <a href="/register" class="btn btn-light" role="button">Register</a>

</form>

<script>
    var form = document.querySelector('.needs-validation');
    form.addEventListener('submit', function (event) {
        if (form.checkValidity() === false) {
            event.preventDefault();
            event.stopPropagation();
            form.classList.add('was-validated');
        }
    })
</script>