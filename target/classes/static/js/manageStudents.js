var table;
$(document).ready(function() {
    initializePage({role: "ROLE_SUPER_ADMIN", onComplete: getData});
    renderTable("[]");
});

var getData = function() {
    getSchoolBoards();
    getSchoolChains();
    $(".loading-overlay-div").hide();
}

function getSchoolBoards(){
    sendAuthorisedRequest({
        url:"/api/get/boards",
        onSuccess : function(response){
            response = JSON.parse(response);
            for(var i=0;i<response.length;i++){
                $("#school-board-select").append("<option value = '"+response[i].id+"'>"+response[i].aka+"</option>");
            }
        },
        onError:function(error){
            console.log(error);
        }
    })
}

function getSchoolChains(){
    sendAuthorisedRequest({
        url:"/api/get/schoolChains",
        onSuccess : function(response){
            response = JSON.parse(response);
            for(var i=0;i<response.length;i++){
                $("#school-chain-select").append("<option value = '"+response[i].id+"'>"+response[i].name+"</option>");
            }
        },
        onError:function(error){
            console.log(error);
        }
    });
}

$("#school-chain-select").change(function(){
    getSchoolsByBoardAndGroup();
});

function getSchoolsByBoardAndGroup(){
    var boardId = $("#school-board-select").val();
    var groupId = $("#school-chain-select").val();

    var jsonData = {
        boardId :boardId,
        groupId:groupId
    }

    sendAuthorisedPostRequest({
        url:"/api/get/schoolsByBoardAndGroup",
        data : jsonData,
        onSuccess: function(response){
            response = JSON.parse(response);
            $("#school-select").html("");
            $("#school-select").append("<option value='0'>Select School</option>")
            for(var i=0;i<response.length;i++){
                $("#school-select").append("<option value = '"+response[i].id+"'>"+response[i].name+"</option>");
            }
        },
        onError: function(error){
            console.log(error);
        }
    });
}

$("#school-select").change(function() {
    getSchoolClassesBySchool();
});

function getSchoolClassesBySchool(){
    var schoolId = $("#school-select").val();

    var jsonData = {
        schoolId : schoolId
    }

    sendAuthorisedPostRequest({
        url: "/api/get/schoolClassesBySchool",
        data: jsonData,
        onSuccess: function(response){
            response = JSON.parse(response);

            for (x in response) {
                console.log(response[x]);
                $("#school-class-select").append("<option value='"+ response[x].id +"'>"+ response[x].name +"</option>");
            }
        },
        onError: function(error){
            console.log(error);
        }
    })
}

function renderTable(data) {
    table = $('#student-table').DataTable({
                "processing": true,
                "data": JSON.parse(data),
                "order": [[ 0, 'asc' ]],
                "columns": [
                    { "data": "name" },
                    { "data": "username" },
                    { "data": "email" },
                    { "data": "roles[0].name" },
                    {
                        "orderable":      false,
                        "data":           null,
                        "defaultContent": "<i class='material-icons' style='cursor: pointer;'>edit</i>"
                    },
                    {
                        "orderable":      false,
                        "data":           null,
                        "defaultContent": "<i class='material-icons' style='cursor: pointer;'>delete</i>"
                    }
                ]
            });
}

$("#submit").on("click", function() {
    var name = $("#name-input").val();
    var rollNo =  $("#roll-no-input").val();
    var admissionNo =  $("#admission-no-input").val();
    var email =  $("#email-input").val();
    var schoolClassId = $("#school-class-select").val();

    if(!/^.+@.+\..+$/.test(email) ){
        notify("Not a valid email", "Attention", "orange");
        return;
    }

    var jsonData = {
        name: name,
        rollNo: rollNo,
        admissionNo: admissionNo,
        email: email,
        schoolClassId: schoolClassId
    };

    console.log(jsonData);
    sendAuthorisedPostRequest({
        url: "/api/save/student",
        data: jsonData,
        onSuccess: function(response){
            notify(response.message);
        },
        onError: function(error){
            console.log(error);
        }
    });
});

$("#school-class-select").change(function() {

});