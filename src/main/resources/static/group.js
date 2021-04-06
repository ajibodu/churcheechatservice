var stompClient = null;

function connect() {
    var socket = new SockJS("/chatSocket");
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);

        stompClient.subscribe('/oneToGroupChat/'+ $("#receiver").val() +'/sub', function (poolData) {
            let dt = JSON.parse(poolData.body);
            if(dt.senderID != $("#sender").val()){
                poolMessage(dt);
            }
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

function sendGroupChat() {
    $("#greetings").append("<tr><td>"+ $("#sender").val() + " " + $("#name").val() + "</td></tr>");
    stompClient.send("/chatApp/oneToGroup", {}, JSON.stringify(
        {
            'senderID': $("#sender").val(),
            'roomID': $("#receiver").val(),
            'messageContent': $("#name").val(),
            'messageContentType': 'TEXT',
        }));
}

function poolMessage(poolData) {
    $.ajax({
        url:'/poolMessage',
        type:"POST",
        data:JSON.stringify({
            "messageType": "ONETOGROUP",
            "roomID": poolData.roomID,
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
            "messageType": "ONETOGROUP",
            "roomID": poolData.roomID,
            "senderID": poolData.senderID,
            "messageID": message.messageID,
            "receiverID": $("#sender").val(),
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
    $( "#send" ).click(function() { sendGroupChat(); });
});