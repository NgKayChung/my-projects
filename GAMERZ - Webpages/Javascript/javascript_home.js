function setPS4Games()
{
    var pic1 = document.getElementById("consoles_games1");
    var pic2 = document.getElementById("consoles_games2");
    var pic3 = document.getElementById("consoles_games3");
    var name1 = document.getElementById("game_name1");
    var name2 = document.getElementById("game_name2");
    var name3 = document.getElementById("game_name3");
    var gamePic = document.getElementById("games_pic");
    
    pic1.src = "Images/Consoles_Games_Button/nioh.png";
    pic1.alt = "Nioh Icon";
    pic1.value = "PS4";
    name1.innerHTML = "Nioh";
    
    pic2.src = "Images/Consoles_Games_Button/bio.png";
    pic2.alt = "Biohazard 7 Icon";
    pic2.value = "PS4";
    name2.innerHTML = "Biohazard 7";
    
    pic3.src = "Images/Consoles_Games_Button/for_honor.png";
    pic3.alt = "For Honor Icon";
    pic3.value = "PS4";
    name3.innerHTML = "For Honor";
    
    gamePic.src = "Images/Games_Pictures/nioh_pic.jpg";
    gamePic.title = "Nioh";
    
    document.getElementById("link").href = "shoppg_playstation4_games.html";
}

function setPSVRGames()
{
    var pic1 = document.getElementById("consoles_games1");
    var pic2 = document.getElementById("consoles_games2");
    var pic3 = document.getElementById("consoles_games3");
    var name1 = document.getElementById("game_name1");
    var name2 = document.getElementById("game_name2");
    var name3 = document.getElementById("game_name3");
    var gamePic = document.getElementById("games_pic");
    
    pic1.src = "Images/Consoles_Games_Button/robinson.png";
    pic1.alt = "Robinson the Journey Icon";
    pic1.value = "PSVR";
    name1.innerHTML = "Robinson the Journey";
    
    pic2.src = "Images/Consoles_Games_Button/until_dawn_rob.png";
    pic2.alt = "Until Dawn Rush of Blood Icon";
    pic2.value = "PSVR";
    name2.innerHTML = "Until Dawn Rush of Blood";
    
    pic3.src = "Images/Consoles_Games_Button/eagle_flight.png";
    pic3.alt = "Eagle Flight Icon";
    pic3.value = "PSVR";
    name3.innerHTML = "Eagle Flight";
    
    gamePic.src = "Images/Games_Pictures/robinson_pic.jpg";
    gamePic.title = "Robinson the Journey";
    
    document.getElementById("link").href = "shoppg_playstationvr_games.html";
}

function setPSVitaGames()
{
    var pic1 = document.getElementById("consoles_games1");
    var pic2 = document.getElementById("consoles_games2");
    var pic3 = document.getElementById("consoles_games3");
    var name1 = document.getElementById("game_name1");
    var name2 = document.getElementById("game_name2");
    var name3 = document.getElementById("game_name3");
    var gamePic = document.getElementById("games_pic");
    
    pic1.src = "Images/Consoles_Games_Button/gravity_rush.png";
    pic1.alt = "Gravity Rush Icon";
    pic1.value = "PSVita";
    name1.innerHTML = "Gravity Rush";
    
    pic2.src = "Images/Consoles_Games_Button/yomawari.png";
    pic2.alt = "Yomawari Icon";
    pic2.value = "PSVita";
    name2.innerHTML = "Yomawari | Night Alone";
    
    pic3.src = "Images/Consoles_Games_Button/dragon_quest_builders.png";
    pic3.alt = "Dragon Quest Builders Icon";
    pic3.value = "PSVita";
    name3.innerHTML = "Dragon Quest Builders";
    
    gamePic.src = "Images/Games_Pictures/gravity_rush_pic.jpg";
    gamePic.title = "Gravity Rush";
    
    document.getElementById("link").href = "shoppg_playstationvita_games.html";
}

function GetPicture1()
{
    var gameName = document.getElementById("game_name1").innerHTML;
    var changePic = document.getElementById("games_pic");
    
    if(gameName == "Nioh")
    {
        changePic.src = "Images/Games_Pictures/nioh_pic.jpg";
        changePic.title = "Nioh";
    }
    else if(gameName == "Robinson the Journey")
    {
        changePic.src = "Images/Games_Pictures/robinson_pic.jpg";
        changePic.title = "Robinson the Journey";
    }
    else if(gameName == "Gravity Rush")
    {
        changePic.src = "Images/Games_Pictures/gravity_rush_pic.jpg";
        changePic.title = "Gravity Rush";
    }
}

function GetPicture2()
{
    var gameName = document.getElementById("game_name2").innerHTML;
    var changePic = document.getElementById("games_pic");
    
    if(gameName == "Biohazard 7")
    {
        changePic.src = "Images/Games_Pictures/bio7_pic.jpg";
        changePic.title = "Biohazard 7";
    }
    else if(gameName == "Until Dawn Rush of Blood")
    {
        changePic.src = "Images/Games_Pictures/until_dawn_rob_pic.png";
        changePic.title = "Until Dawn Rush of Blood";
    }
    else if(gameName == "Yomawari | Night Alone")
    {
        changePic.src = "Images/Games_Pictures/yomawari_pic.jpg";
        changePic.title = "Yomawari | Night Alone";
    }
}

function GetPicture3()
{
    var gameName = document.getElementById("game_name3").innerHTML;
    var changePic = document.getElementById("games_pic");
    
    if(gameName == "For Honor")
    {
        changePic.src = "Images/Games_Pictures/for_honor_pic.jpg";
        changePic.title = "For Honor";
    }
    else if(gameName == "Eagle Flight")
    {
        changePic.src = "Images/Games_Pictures/eagle_flight_pic.png";
        changePic.title = "Eagle Flight";
    }
    else if(gameName == "Dragon Quest Builders")
    {
        changePic.src = "Images/Games_Pictures/dragon_quest_builders_pic.jpg";
        changePic.title = "Dragon Quest Builders";
    }
}