/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

var lines = new Array();
lines[0] = "<div onmouseover='this.title.show' title='Generate Report modules code with DHTMLX Grid'><b>Report Generation Utility V3 - DhtmlxGrid</b><br><font color='green'><i>Deployed</i></font></div>";
lines[1] = "<div title='Use this component to upload multiple files with Ajax'><b>Filebox</b><br><font color='red'><i>Coming soon</i></font></div>";
lines[2] = "<div title='Manage Data requests and view results of each query.'><b>Data Request Executor</b><br><font color='green'><i>Deployed</i></font></div>";
lines[3] = "<div title='Quickly generate Web service Producer and Consumer codes'><b>Web Service Generation Utility</b><br><font color='green'><i>Deployed</i></font></div>";
lines[4] = "<div title='Covert Oracle statments to Mysql equivalent syntax'><b>Oracle to Mysql</b><br><font color='green'><i>Deployed</i></font></div>";
lines[5] = "<div title='View configuration files like connection alias, property files etc. of all servers'><b>Configuration Audit Report</b><br><font color='green'><i>Deployed</i></font></div>";
lines[6] = "<div title='View list of web service available on different servers'><b>Serivce List</b><br><font color='green'><i>Deployed</i></font></div>";
var count = 0;
var notes;

var Switch = "ON";
var msg_frequency = 4900; // after how much time next message should come
var msg_duration = 10000; // till how long each message should be displayed

function loadNotifications()
{
    if(Switch=="OFF")
    {
        return;
    }
    dhtmlx.message.position="bottom";
    dhtmlx.message({
        id:"clearall",
        type:"clearall",
        text: "<div align='center'><b>Clear All Notifications</b></div>",
        expire: -1        
    });    
    
    var divs = document.getElementsByTagName("div");
    for(var i=0;i<divs.length;i++)
        {
            if(divs[i].getAttribute("class")=="dhtmlx-info dhtmlx-clearall")
                {
                    divs[i].onclick = function(){clearNotifications()};
                }
        }
    
    notes = window.setInterval(showMessage,msg_frequency);
    showMessage();
}

function showMessage()
{
    if(Switch=="OFF")
    {
        return;
    }
    dhtmlx.message.expire=msg_duration;
    count = count%(lines.length);
    dhtmlx.message({
        id:"msg"+count,
        text:lines[count]        
    });    
    count++;
}

function clearNotifications()
{    
    Switch = "OFF";
    window.clearInterval(notes);
    for(var i=0;i<lines.length;i++)
    {
        dhtmlx.message.hide("msg"+i);
    }    
    dhtmlx.message.hide("clearall");
}