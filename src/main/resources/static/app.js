var stompClient = null;
const headers = {UserAuth: `eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjYwNTYxZjExYWEwZTAzNzM5ZTQwMzczOSIsImVtYWlsIjoidGVzdEBnbWFpbC5jb20iLCJ0eXBlIjoiY2h1cmNoIiwic291cmNlIjoic3lzdGVtIiwiaWF0IjoxNjE3MTQwMDg5LCJleHAiOjE2MjkyMzYwODl9.0krvxDNbVKECDFV8pBrkUr_ap5t_24m80PPvOtzDAZU`};

function connect() {
    var socket = new SockJS("/chatSocket");
    console.log(socket);
    stompClient = Stomp.over(socket);
    //stompClient = Stomp.client("ws://localhost:8080/chatSocket/026/kpl0usk2/websocket");
    console.log(stompClient);

    stompClient.connect(headers, (frame) =>{
        setConnected(true);
        console.log('Connected: ' + frame);

        stompClient.subscribe('/oneOnOneChat/'+ $("#sender").val() +'/sub', function (poolData) {
            console.log("data "+ poolData.body);
            poolMessage(JSON.parse(poolData.body));
        }, function (error){console.log('Error: ' + error)});
    });
}

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    }
    else {
        $("#conversation").hide();
    }
    $("#greetings").html("");
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function sendPersonalChat() {
    $("#greetings").append("<tr><td>"+ $("#sender").val() + " " + $("#name").val() + "</td></tr>");
    stompClient.send("/chatApp/oneOnOne", headers, JSON.stringify(
        {
            'senderID': $("#sender").val() ,
            'receiverID': $("#receiver").val() ,
            'messageContent': $("#name").val(),
            'messageContentType': 'TEXT',
        }));
    $("#name").text("");
}

function poolMessage(poolData) {
    $.ajax({
        url:'/poolMessage',
        type:"POST",
        data:JSON.stringify({
            "messageType": "ONEONONE",
            "receiverID": poolData.receiverID,
            "senderID": poolData.senderID
        }),
        contentType:"application/json",
        dataType:"json"})
        .done(function(data, op) {
            console.log(data);
            data.forEach((ms)=>{
                $("#greetings").append("<tr><td style='text-align: right'>"+ ms.senderID + " " + ms.messageContent + "</td></tr>");
                messageStatus(poolData, ms);
            })

        })
        .fail(function() { console.log("error"); })
        //.always(function() { console.log("complete"); });
}
function messageStatus(poolData, message) {
    //console.log(message);
    $.ajax({
        url:'/messageStatus',
        type:"POST",
        data:JSON.stringify({
            "messageType": "ONEONONE",
            "receiverID": poolData.receiverID,
            "senderID": poolData.senderID,
            "messageID": message.messageID,
            "messageStatus": "DELIVERED"
        }),
        contentType:"application/json",
        dataType:"json"})
        .done(function(data, op) {
            //console.log(data);
        })
        .fail(function() { console.log("error"); })
        //.always(function() { console.log("complete"); });

}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $( "#connect" ).click(function() { connect(); });
    $( "#disconnect" ).click(function() { disconnect(); });
    $( "#send" ).click(function() { sendPersonalChat(); });
});