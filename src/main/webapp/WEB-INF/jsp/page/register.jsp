<h2>Register</h2>
<form action="/register" method="post" class="main-form needs-validation" novalidate>
    <div class="alert alert-warning">
        ${emailAlreadyTakenMessage}
        ${invalidPasswordPatternMessage}
        ${invalidEmailPatternMessage}

    </div>
    <div class="form-group">
        <label for="fname">First Name</label>
        <input type="text" id="fname" name="fname" class="form-control" required>
    </div>

    <div class="form-group">
        <label for="lname">Last Name</label>
        <input type="text" id="lname" name="lname" class="form-control" required>
    </div>

    <div class="form-group">
        <label for="email">Email</label>
        <input type="text" id="email" name="email" class="form-control" required>
    </div>

    <div class="form-group">
        <label for="pwd">Password</label>
        <input type="password" id="pwd" name="password" class="form-control" required>
        <small class="form-text text-muted">
            Password should be at least 8 digits, contain lower-case and upper-case characters, at least one digit and
            special character
        </small>
    </div>

    <button type="submit" class="btn btn-outline-success">Register</button>

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