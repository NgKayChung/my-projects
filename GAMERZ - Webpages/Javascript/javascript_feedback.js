function calculateLength(feedb)
{
    if(feedb.value.length == 200 && document.getElementById("length").innerHTML == 200)
        alert("Your feedback reached length of 200 characters");
    document.getElementById("length").innerHTML = feedb.value.length;
}

function setZero()
{
    document.getElementById("length").innerHTML = 0;
}

function checkFeedbackForm()
{
    var userName = document.feedbackForm.username;
    var feedBack = document.feedbackForm.feedback;
    
    if(validateUserName(userName))
    {
        if(feedBack.value != "")
        {
            document.feedbackForm.action = "comp_feedback.html";
            return true;
        }
        else
        {
            alert("Dear user, you haven't type your feedback");
            feedBack.focus();
        }
    }
    return false;
}

function validateUserName(user)
{
    var unFormat = /^[A-Za-z0-9]+$/;
    
    try
    {
        if(user.value == "")
            throw("Username should not be empty");
        if(!(user.value.match(unFormat)))
            throw("Username should consists of alphanumeric characters only");
        return true;
    }
    catch(wrong)
    {
        alert(wrong);
        user.focus();
        return false;
    }
}