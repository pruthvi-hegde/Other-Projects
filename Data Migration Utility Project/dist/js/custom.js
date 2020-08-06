
var countries = ['United States', 'Canada', 'Argentina', 'Armenia'];
    var assetList = $('#assetNameMenu')
    $.each(countries, function(i)
    {
        var li = $('<li/>')
            .addClass('ui-menu-item')
            .attr('role', 'menuitem')
            .appendTo(assetList);
        var aaa = $('<a/>')
            .addClass('ui-all')
            .text(countries[i])
            .appendTo(li);
        var input = $('<input/>')
            .addClass('ui-all')
            .attr('role', 'checkbox')
            .appendTo(aaa);

    })
    var resultJSON = '{"FirstName":"John","LastName":"Doe","Email":"johndoe@johndoe.com","Phone":"123 dead drive"}';
    var result = $.parseJSON(resultJSON);
    $.each(result, function(k, v) {
        //display the key and value pair

    $("#outputdisplay").append("<div>"+k+": <input value='"+v+"' /> <input type='checkbox' /> </div>")
    });


function submitform(){
    console.log("Submit form");

    var xhr = new XMLHttpRequest();
    xhr.open('POST', "https://ipinfo.io/json", true);

    var json = {
        "host" :"ora-dev15-custom-d1.vmware.com",
        "port" : "1521",
        "type":"Oracle",
        "tableName":"SOURCET3",
        "serviceName":"CUSDEV15",
        "userName":"XXVMPORTAL",
        "password":"tfgdsaz4gh"
    }

    xhr.send(json);

    xhr.addEventListener("readystatechange", processRequest, false);

    function processRequest(e) {
         if (xhr.readyState == 4 && xhr.status == 200) {
            var response = JSON.parse(xhr.responseText);
            alert(response.ip);
        }
    }


  /*  alert("inside json");
       $.ajax({
           type: "POST",
           url: "http://localhost:8090/DMV/connectionSetup",
           dataType: "json",
           contentType: 'application/json',
           success: function (msg) {
               alert(sucess);
           },
           fail: function(status){
            alert(fail);
           },
           data: JSON.stringify({
        host:"ora-dev15-custom-d1.vmware.com",
        port:"1521",
        type:"Oracle",
        tableName:"SOURCET3",
        serviceName:"CUSDEV15",
        userName:"XXVMPORTAL",
        password:"tfgdsaz4gh"
    })
       });*/
}

