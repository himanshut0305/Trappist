var table;
$(document).ready(function() {
    initializePage({role: "ROLE_SUPER_ADMIN", onComplete: getSchools});
});

var getBoards = function getBoards() {
    sendAuthorisedRequest({
        url: "/api/get/boards",
        onSuccess: function(data){
            data = JSON.parse(data);
            for(var i = 0; i < data.length; i++) {
                $("#school-board-select").append("<option value='"+ data[i].id +"'>"+ data[i].aka +"</option>")
            }
        },
        onError: function(error){
            console.log(error);
        }
    });
}

var getSchoolGroups = function getSchoolGroups() {
    sendAuthorisedRequest({
        url: "/api/get/schoolChains",
        onSuccess: function(data){
            data = JSON.parse(data);
            for(var i = 0; i < data.length; i++) {
                $("#school-chain-select").append("<option value='"+ data[i].id +"'>"+ data[i].aka +"</option>");
            }

            $("#school-chain-select").append("<option value'-1'>No Group</option>");
        },
        onError: function(error){
            console.log(error);
        }
    });
}

var getSchools = function getSchools() {
    $(".loading-overlay-div").hide();
    getBoards();
    getSchoolGroups();

    sendAuthorisedRequest({
        url: "/api/get/schools",
        onSuccess: function(data){
            console.log(JSON.parse(data));
            renderTable(data);
        },
        onError: function(error){
            console.log(error);
        }
    });
}

$("#school-logo-upload-btn").click(function() {
    $("#school-logo-input").trigger("click");
});

var SCHOOL_LOGO = null;
function previewFile() {
    var preview = document.getElementById("school-logo-preview");
    var file    = document.getElementById("school-logo-input").files[0];
    var reader  = new FileReader();

    reader.onloadend = function () {
       preview.src = reader.result;
       SCHOOL_LOGO = reader.result;
    }

    if (file) {
        reader.readAsDataURL(file);
    }
    else {
        preview.src = "";
    }
}


$("#submit").on("click", function() {
    var boardId = $("#school-board-select").val();
    var schoolChainId = $("#school-chain-select").val();

    if(boardId == 0 || boardId == null) {
        notify("Select school board", "Attention", "orange");
        $("#school-board-select").focus();
        return;
    }

    var name = $("#school-name").val();
    if(name == null || name == "") {
        notify("Name cannot be blank", "Attention", "orange");
        $("#school-name").focus();
        return;
    }

    var alias = $("#school-alias").val();
    if(alias == null || alias == "") {
        notify("Alias cannot be blank", "Attention", "orange");
        $("#school-alias").focus();
        return;
    }

    var code = $("#school-code").val();
    if(code == null || code == "") {
        notify("Code cannot be blank", "Attention", "orange");
        $("#school-code").focus();
        return;
    }

    var theme = $("#school-theme-select").val();
    if(SCHOOL_LOGO == null) {
        notify("Every school needs a logo", "Attention", "orange");
        return;
    }

    var schoolData = {
        boardId : boardId,
        schoolChainId : schoolChainId,

        name : name,
        alias : alias,
        code : code,

        theme : theme,
        logo : SCHOOL_LOGO
    }

    console.log(schoolData);
    sendAuthorisedPostRequest({
        url: "/api/save/school",
        data: schoolData,
        onSuccess: function(data){
            notify("School created Successfully", "Success", "blue");
            table.destroy();
            getSchools();
            console.log(data);
        },
        onError: function(error){
            console.log(error);
        }
    });
});

function renderTable(data) {
    table = $('#school-board-table').DataTable( {
            "processing": true,
            "data": JSON.parse(data),
            "order": [[ 0, 'asc' ]],
            "columns": [
                { "data": "name" },
                { "data": "aka" },
                { "data": "schoolCode" },
                { "data": "schoolTheme" },
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