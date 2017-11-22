function CheckRegisterForm()
{
    var fNameObj = document.getElementById("firstname");
    var lNameObj = document.getElementById("lastname");
    var userObj = document.getElementById("username");
    var emailObj = document.getElementById("email");
    var passObj = document.getElementById("password");
    var confirmPassObj = document.getElementById("confirm_password");
    var phoneNoObj = document.getElementById("phonenumber");
    var checkedTCObj = document.getElementById("acceptTC");
    
    if(validateFirstName(fNameObj))
    {
        if(validateLastName(lNameObj))
        {
            if(validateUserName(userObj))
            {
                if(validateEmailAddress(emailObj))
                {
                    if(validatePassword(passObj, 8))
                    {
                        if(passwordIsSame(passObj, confirmPassObj))
                        {
                            if(validatePhoneNumber(phoneNoObj))
                            {
                                if(isChecked(checkedTCObj))
                                {
                                    document.registerForm.action = "comp_register.html";
                                    return true;
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    return false;
}

function validateFirstName(firstName)
{
    var fnFormat = /^[A-Za-z]+$/;
    
    try
    {
        if(firstName.value == "")
            throw("First name should not be empty");
        if(!(firstName.value.match(fnFormat)))
            throw("First name should consists of alphabetic characters only");
        return true;
    }
    catch(wrong)
    {
        alert(wrong);
        firstName.focus();
        return false;
    }
}

function validateLastName(lastName)
{
    var lnFormat = /^[A-Za-z\s]+$/;
    
    try
    {
        if(lastName.value == "")
            throw("Last name should not be empty");
        if(!(lastName.value.match(lnFormat)))
            throw("Last name should consists of alphabetic characters only");
        return true;
    }
    catch(wrong)
    {
        alert(wrong);
        lastName.focus();
        return false;
    }
}

function validateUserName(userName)
{
    var unFormat = /^[A-Za-z0-9]+$/;
    
    try
    {
        if(userName.value == "")
            throw("Username should not be empty");
        if(!(userName.value.match(unFormat)))
            throw("Username should consists of alphanumeric characters only");
        return true;
    }
    catch(wrong)
    {
        alert(wrong);
        userName.focus();
        return false;
    }
}

function validateEmailAddress(emailAddress)
{
    var emailFormat = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/;
    
    try
    {
        if(emailAddress.value == "")
            throw("Email address should not be empty");
        if(!(emailAddress.value.match(emailFormat)))
            throw("You typed a wrong format email address");
        return true;
    }
    catch(wrong)
    {
        alert(wrong);
        emailAddress.focus();
        return false;
    }
}

function validatePassword(password, minLength)
{
    try
    {
        if(password.value == "")
            throw("Please do not leave password be empty");
        if(password.value.length < minLength)
            throw("Password should have length at least " + minLength);
        return true;
    }
    catch(wrong)
    {
        alert(wrong);
        password.focus();
        return false;
    }
}

function passwordIsSame(password, confirmPassword)
{
    try
    {
        if(confirmPassword.value == "")
            throw("Confirmation password should not be empty");
        if(!(password.value === confirmPassword.value))
            throw("Confimation password entered is not the same as password entered above");
        return true;
    }
    catch(wrong)
    {
        alert(wrong);
        confirmPassword.focus();
        return false;
    }
}

function validatePhoneNumber(phoneNumber)
{
    var pnFormat = /^([0][1][0, 2-9]{1}([\s])?[\-]([\s])?\d{3}([\s])?\d{4})+$/;
    var pnFormat011 = /^([0][1][1]([\s])?[\-]([\s])?\d{4}([\s])?\d{4})+$/;
    var pnInternationalFormat = /^(([\(])?([2-9]){3}([\)])?([\s])?\d{3}([\s])?([\-])?([\s])?\d{4})+$/;
    
    if(phoneNumber.value == "")
    {
        alert("Phone number should not be empty");
        phoneNumber.focus();
        return false;
    }
    else if(phoneNumber.value.match(pnFormat))
    {
        return true;
    }
    else if(phoneNumber.value.match(pnFormat011))
    {
        return true;
    }
    else if(phoneNumber.value.match(pnInternationalFormat))
    {
        if((phoneNumber.value.search("[(]") == -1) && (phoneNumber.value.search("[)]") == -1))
        {
            alert("Please open or close the parentheses of the phone number entered");
            phoneNumber.focus();
            return false;
        }
        return true;
    }
    else
    {
        alert("Please enter a correct format phone number");
        phoneNumber.focus();
        return false;
    }
}

function isChecked(acceptTerms_Conditions)
{
    if(acceptTerms_Conditions.checked)
    {
        return true;
    }
    alert("Please tick on the accept terms and conditions box");
    acceptTerms_Conditions.focus();
    return false;
}