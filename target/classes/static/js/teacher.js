$(document).ready(function() {
    initializePage({role: "ROLE_SUPER_ADMIN", onComplete: getData});
    renderTable([]);
});

var getData = function() {
    getSchoolBoards();
    getSchoolChains();
    getTeachers();
    $(".loading-overlay-div").hide();
}

function getTeachers(){
    sendAuthorisedRequest({
        url:"/api/get/teachers",
        onSuccess : function(response){
            response = JSON.parse(response);
//            renderTable(response);
            console.log(response);
        },
        onError:function(error){
            console.log(error);
        }
    })
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

$("#school-select").change(function() {
    getSchoolClassesBySchool();
    getTeachersForSchool();
});

function getTeachersForSchool() {
    var schoolId = $("#school-select").val();

    sendAuthorisedRequest({
        url: "/api/get/teachersForSchool/" + schoolId,
        onSuccess: function(response) {
            var allTeachersForSelectedSchool = JSON.parse(response);
            console.log(allTeachersForSelectedSchool);

            table.destroy();
            renderTable(allTeachersForSelectedSchool);
        },
        onError: function(error) {

        }
    });
}

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

            console.log(response);

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

$("#submit").on("click", function() {
    var name = $("#name-input").val();
    var username = $("#username-input").val();
    var phoneNo =  $("#phone-no-input").val();
    var email =  $("#email-input").val();
    var schoolId = $("#school-select").val();

    if(!/^.+@.+\..+$/.test(email) ){
        notify("Not a valid email", "Attention", "orange");
        return;
    }

    var jsonData = {
        name: name,
        username: username,
        phoneNo: phoneNo,
        email: email,
        schoolId: schoolId
    };

    console.log(jsonData);
    sendAuthorisedPostRequest({
        url: "/api/save/teacher",
        data: jsonData,
        onSuccess: function(response){
            response = JSON.parse(response);
            console.log(response);
            getTeachersForSchool();
            notify(response.message, "Created", "blue");
        },
        onError: function(error){
            console.log(error);
        }
    });
});

function renderTable(data) {
    table = $('#teacher-table').DataTable({
                "processing": true,
                "data": data,
                "order": [[0, 'asc']],
                "columnDefs": [{
                    "targets": [0],
                    "data": "schoolYearSubjectBook[, ]"
                }],
                "columns": [
                    { "data": "user.name" },
                    { "data": "user.username" },
                    { "data": "schoolClassSubjects", defaultContent: 'DEFAULT',
                        render: function(data, type, row) {
                            var subjects = "";
                            for(var i = 0; i < data.length; i++) {
                               console.log(data[i].subject.name);
                               console.log(data[i].schoolClass.name);

                               subjects = subjects + data[i].subject.name + " - " + data[i].schoolClass.name + ", ";
                            }
                            console.log(data);
                            return subjects;
                        }
                    },
                    {
                        "orderable":      false,
                        "data":           null,
                        "defaultContent": "<i class='material-icons assign' style='cursor: pointer; font-size: large;'>link</i>"
                    },
                    {
                        "orderable":      false,
                        "data":           null,
                        "defaultContent": "<i class='material-icons edit' style='cursor: pointer; font-size: large;'>edit</i>"
                    },
                    {
                        "orderable":      false,
                        "data":           null,
                        "defaultContent": "<i class='material-icons delete' style='cursor: pointer; font-size: large;'>delete</i>"
                    }
                ]
            });
}

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

$("#school-class-select").change(function() {
    getSubjectsBySchoolClass();
});

function getSubjectsBySchoolClass(){
    console.log("School Class Select Changed");
    var schoolClassId = $("#school-class-select").val();

    var jsonData = {
        schoolClassId : schoolClassId
    }

    console.log(jsonData);

    sendAuthorisedPostRequest({
        url: "/api/get/subjectsBySchoolClass",
        data: jsonData,
        onSuccess: function(response){
            response = JSON.parse(response);
            console.log(response);

            $("#subject-select").html("");
            for (x in response) {
                console.log(response[x]);
                $("#subject-select").append("<option value='"+ response[x].subject.id +"'>"+ response[x].subject.name +"</option>");
            }
        },
        onError: function(error){
            console.log(error);
        }
    });
}

var currentlySelectedTeacher = {};
$(document).on("click", "#teacher-table tbody tr .assign", function() {
    var teacher = table.row($(this).parent().parent()).data();
    currentlySelectedTeacher = teacher;
    console.log(teacher);

    $("#class-subject-assign-form").show();
});

$("#assign-class-subject-btn").click(function() {
    var teacherId = currentlySelectedTeacher.id;
    var schoolClassId = $("#school-class-select").val();
    var subjectId = $("#subject-select").val();

    var jsonData = {
        "teacherId": teacherId,
        "schoolClassId": schoolClassId,
        "subjectId": subjectId
    }

    sendAuthorisedPostRequest({
            url: "/api/assign/subjectToTeacher",
            data: jsonData,
            onSuccess: function(response){
                getTeachersForSchool();
                response = JSON.parse(response);
                console.log(response);

                notify("Subject-Class assigned successfully", "Assigned", "blue");
            },
            onError: function(error){
                console.log(error);
            }
    });
});