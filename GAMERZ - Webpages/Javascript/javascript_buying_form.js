var itemprice;

function CheckBuyingForm()
{
    var FirstName = document.buying_form.firstName;
    var LastName = document.buying_form.lastName;
    var Country = document.buying_form.country;
    var Address = document.buying_form.address;
    var Address2 = document.buying_form.address2;
    var PostCode = document.buying_form.postCode;
    var ContactNo = document.buying_form.contactNo;
    var CardType = document.buying_form.cardType;
    var CardNo = document.buying_form.cardNo;
    var AcceptTC = document.buying_form.acceptTC;
    
    if(ValidateFirstName(FirstName))
    {
        if(ValidateLastName(LastName))
        {
            if(ValidateCountry(Country))
            {
                if(ValidateAddress(Address))
                {
                    if(ValidateAddress2(Address2))
                    {
                        if(ValidatePostCode(PostCode))
                        {
                            if(ValidateContactNo(ContactNo))
                            {
                                if(ValidateCardType(CardType))
                                {
                                    if(ValidateCardNo(CardType, CardNo))
                                    {
                                        if(isChecked(AcceptTC))
                                        {
                                            if(confirmed())
                                            {
                                                return true;
                                            }
                                        }
                                    }
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

function ValidateFirstName(firstName)
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

function ValidateLastName(lastName)
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

function ValidateCountry(country)
{
    itemprice = parseFloat(document.getElementById("itemPrice").innerHTML);
    
    if(country.value == "")
    {
        alert("Please select your country");
        country.focus();
        return false;
    }
    
    if(country.value != "MY")
    {
        itemprice += 20.00;
    }
    return true;
}

function ValidateAddress(address)
{
   var addressFormat = /^[A-Za-z0-9\s\-\.\,\/]+$/;
   
   try
   {
       if(address.value == "")
           throw("Please enter your address");
       if(!(address.value.match(addressFormat)))
           throw("Wrong format of address entered");
       return true;
   }
   catch(errorMsg)
   {
       alert(errorMsg);
       address.focus();
       return false;
   }
}

function ValidateAddress2(address2)
{
   var address2Format = /^[A-Za-z0-9\s\-\/\,\.]+$/;
    
   try
   {
       if(address2.value == "")
           throw("Please enter your address2");
       if(!(address2.value.match(address2Format)))
           throw("Wrong format of address2 entered");
       return true;
   }
   catch(errorMsg)
   {
       alert(errorMsg);
       address2.focus();
       return false;
   }
}

function ValidatePostCode(postCode)
{
    var pCodeFormat = /^\d{5}$/;
    
    try
    {
        if(postCode.value == "")
            throw("Please enter postcode");
        if(!(postCode.value.match(pCodeFormat)))
            throw("Invalid postcode");
        return true;
    }
    catch(errorMsg)
    {
        alert(errorMsg);
        postCode.focus();
        return false;
    }
}

function ValidateContactNo(contactNo)
{
    var cnFormat = /^([0][1][0, 2-9]{1}([\s])?[\-]([\s])?\d{3}([\s])?\d{4})+$/;
    var cnFormat011 = /^([0][1][1]([\s])?[\-]([\s])?\d{4}([\s])?\d{4})+$/;
    var cnInternationalFormat = /^(([\(])?([2-9]){3}([\)])?([\s])?\d{3}([\s])?([\-])?([\s])?\d{4})+$/;
    
    if(contactNo.value == "")
    {
        alert("Contact number should not be empty");
        contactNo.focus();
        return false;
    }
    else if(contactNo.value.match(cnFormat))
    {
        return true;
    }
    else if(contactNo.value.match(cnFormat011))
    {
        return true;
    }
    else if(contactNo.value.match(cnInternationalFormat))
    {
        if((contactNo.value.search("[(]") == -1) && (contactNo.value.search("[)]") == -1))
        {
            alert("Please open or close the parentheses of the contact number entered");
            contactNo.focus();
            return false;
        }
        return true;
    }
    else
    {
        alert("Please enter a correct format contact number");
        contactNo.focus();
        return false;
    }
}

function ValidateCardType(cardType)
{
    if(cardType.value == "")
    {
        alert("Please select your card type");
        cardType.focus();
        return false;
    }
    return true;
}

function ValidateCardNo(cardType, cardNo)
{
    var AEFormat = /^(?:3[47][0-9]{13})+$/;
    var DSFormat = /^(?:6(?:011[0-9]{2}|22(?:1(?:2[6-9]|[3-9][0-9])|[2-8][0-9][0-9]|9(?:[0-1][0-9]|2[0-5]))|4[4-9][0-9]{3}|5[0-9]{4})[0-9]{10})+$/;
    var MCFormat = /^(?:5[1-5]([0-9])?([0-9])?([0-9])?[0-9]{14})+$/;
    var VSFormat = /^(?:4[0-9]{12}([0-9])?([0-9])?([0-9])?)+$/;
    
    if(cardNo.value == "")
    {
        alert("Please input your card number");
        cardNo.focus();
        return false;
    }
    
    if(cardType.value == "AE")
    {
        if(cardNo.value.match(AEFormat))
        {
            return true;
        }
        else
        {
            alert("Invalid American Express card number entered");
            cardNo.focus();
            return false;
        }
    }
    else if(cardType.value == "DS")
    {
        if(cardNo.value.match(DSFormat))
        {
            return true;
        }
        else
        {
            alert("Invalid Discover card number entered");
            cardNo.focus();
            return false;
        }
    }
    else if(cardType.value == "MC")
    {
        if(cardNo.value.match(MCFormat))
        {
            return true;
        }
        else
        {
            alert("Invalid Mastercard card number entered");
            cardNo.focus();
            return false;
        }
    }
    else if(cardType.value == "VS")
    {
        if(cardNo.value.match(VSFormat))
        {
            return true;
        }
        else
        {
            alert("Invalid Visa card number entered");
            cardNo.focus();
            return false;
        }
    }
}

function isChecked(acceptTC)
{
    if(acceptTC.checked)
    {
        return true;
    }
    alert("Please tick on the accept terms and conditions box");
    acceptTC.focus();
    return false;
}