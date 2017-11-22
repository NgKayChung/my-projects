function checkLoginForm()
{
    var emailObj = document.getElementById("email");
    var userObj = document.getElementById("username");
    var passObj = document.getElementById("password");
    
    if(validateEmail_User(emailObj, userObj))
    {
        if(validatePass(passObj))
        {
            document.loginForm.action = "comp_login.html";
            return true;
        }
    }
    return false;
}

function validateEmail_User(email, user)
{
    if(validateEmail(email.value))
    {
        return true;
    }
    
    if(validateUser(user.value))
    {
        return true;
    }
    
    if(email.value == "" && user.value == "")
    {
        alert("Please do not leave email address and username be empty");
        email.style.border = "1px solid red";
        user.style.border = "1px solid red";
    }
    else if(email.value != "")
    {
        alert("Wrong email format typed");
        email.focus();
    }
    else if(email.value == "")
    {
        alert("Username should consists of alphanumeric characters only");
        user.focus();
    }
    return false;
}

function validateEmail(emailAddress)
{
    var emailFormat = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/;
    
    if(emailAddress.match(emailFormat))
    {
        return true;
    }
    return false;
}

function validateUser(username)
{
    var userFormat = /^[A-Za-z0-9]+$/;
    
    if(username.match(userFormat))
    {
        return true;
    }
    return false;
}

function validatePass(password)
{    
    try
    {
        if(password.value == "")
            throw("Password cannot be empty");
        if(password.value.length < 8)
            throw("Password length should be at least 8");
        return true;
    }
    catch(error)
    {
        alert(error);
        password.focus();
        return false;
    }
}