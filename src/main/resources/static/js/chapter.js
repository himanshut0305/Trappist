var table;
$(document).ready(function() {
    initializePage({role: "ROLE_SUPER_ADMIN", onComplete: getData});
});

var getData = function getData() {
    getSubjectGroups();
    getChapters();
}

$("#subject-group-select").change(function() {
    getSubjects();
});

var getSubjects = function getSubjects() {
    var subjectGroupId = $("#subject-group-select").val();

    var requestData = {subjectGroupId: subjectGroupId};

    sendAuthorisedPostRequest({
        url: "/api/get/subjectsByGroup",
        data: requestData,
        onSuccess: function(data){
            data = JSON.parse(data);

            $("#subject-select").html("");
            $("#subject-select").append("<option value='0' selected disabled>Select Subject</option>")
            for(var i = 0; i < data.length; i++) {
                $("#subject-select").append("<option value='" + data[i].id + "'>" + data[i].name + "</option>")
            }
        },
        onError: function(error){
            console.log(error);
        }
    });
}

var getChapters = function getChapters() {
    sendAuthorisedRequest({
        url: "/api/get/chapters",
        onSuccess: function(data){
            console.log(data);
            renderTable(data);
        },
        onError: function(error){
            console.log(error);
        }
    });
}

var getSubjectGroups = function getSubjectGroups() {
    console.log("Getting Subject Groups");

    sendAuthorisedRequest({
        url: "/api/get/subjectGroups",
        onSuccess: function(data){
            data = JSON.parse(data);
            var length = data.length;

            for(var i = 0; i < length; i++) {
                $("#subject-group-select").append("<option value='" + data[i].id + "'>" + data[i].name + "</option>")
            }

            $(".loading-overlay-div").hide();
        },
        onError: function(error){
            console.log(error);
        }
    });
}

$("#submit").on("click", function() {
    saveChapter();
});

function renderTable(data) {
    table = $('#chapter-table').DataTable({
            "processing": true,
            "data": JSON.parse(data),
            "order": [[ 0, 'asc' ]],
            "columns": [
                { "data": "name" },
                { "data": "subject.name" },
                {
                    "orderable":      false,
                    "data":           null,
                    "defaultContent": "<i class='material-icons' style='cursor: pointer;'>edit</i>"
                },
                {
                    "orderable":      false,
                    "data":           null,
                    "defaultContent": "<i class='material-icons delete' style='cursor: pointer;'>delete</i>"
                }
            ]
        });
}

var saveChapter = function saveChapter() {
    var subjectId = $("#subject-select").val();
    var chapterName = $("#chapter-name-input").val().trim();

    if(chapterName != null && chapterName != "" && subjectId > 0) {
        var data = { subjectId : subjectId, name : chapterName };

        sendAuthorisedPostRequest({
            url: "/api/save/chapter",
            data: data,
            onSuccess: function(response){
                getChapters();
                table.destroy();
                $("#chapter-name-input").val("");
                notify("Chapter created successfully", "Created", "blue");
            },
            onError: function(error){
                var msg = (JSON.parse(error.response)).message;
                notify(msg, "Error", "red");
            }
        });
    }
    else if(subjectId <= 0) {
        notify("Select subject first", "Warning", "orange");
    }
    else {
        notify("Chapter name cannot be blank", "Warning", "orange");
    }
}

$(document).on("click", "#chapter-table tbody tr .delete", function() {
    var chapter = table.row($(this).parent().parent()).data();

    var r = confirm("You want to delete '" + chapter.name + "' permanently. This action is irreversible. Continue?");
    if (r == true) {
        var chapterId = chapter.id;

        sendAuthorisedRequest({
            url: "/api/delete/chapter/" + chapterId,
            onSuccess: function(response){
                table.destroy();
                getChapters();
                notify("Chapter deleted successfully", "Deleted", "blue");
            },
            onError: function(error){
                console.log(error);
                var msg = (JSON.parse(error.response)).message;
                notify(msg, "Error", "red");
            }
        });
    }
});