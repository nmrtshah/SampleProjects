function padlength(what)
{
    var output = (what.toString().length==1)? "0"+what : what;
    return output;
}

function setTime()
{
    serverdate.setSeconds(serverdate.getSeconds()+1);    
}

function displaytime()
{
    var datestring = padlength(serverdate.getDate()) + "/" + (serverdate.getMonth()+1) + "/" + serverdate.getFullYear();
    var timestring = padlength(serverdate.getHours()) + ":" + padlength(serverdate.getMinutes()); // + ":" + padlength(serverdate.getSeconds());
    document.getElementById("servertime").innerHTML = datestring + " " + timestring;    
}

function startTimer()
{
    setTime();
    displaytime();
    setInterval("setTime()", 1000);    
    setInterval("displaytime()", 30000);    
}
