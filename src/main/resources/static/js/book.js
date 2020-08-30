var table;
$(document).ready(function() {
    initializePage({role: "ROLE_SUPER_ADMIN", onComplete: getData});
});

var getData = function getData() {
    getSubjectGroups();
    getSchoolYears();
    getBooks();
}

var getSubjectGroups = function getSubjectGroups() {
    sendAuthorisedRequest({
        url: "/api/get/subjectGroups",
        onSuccess: function(data){
            data = JSON.parse(data);
            for(var i = 0; i < data.length; i++) {
                $("#subject-group-select").append("<option value='" + data[i].id + "'>" + data[i].name + "</option>")
            }

            $(".loading-overlay-div").hide();
        },
        onError: function(error){
            console.log(error);
        }
    });
}

var getSchoolYears = function getSchoolYears() {
    sendAuthorisedRequest({
        url: "/api/get/schoolYears",
        onSuccess: function(data){
            data = JSON.parse(data);
            var length = data.length;

            for(var i = 0; i < length; i++) {
                $("#school-year-select").append("<option value='" + data[i].id + "'>" + data[i].name + "</option>")
            }
        },
        onError: function(error){
            console.log(error);
        }
    });
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
            var length = data.length;

            $("#subject-select").html("");
            $("#subject-select").append("<option disabled selected>Select Subject</option>");
            for(var i = 0; i < length; i++) {
                $("#subject-select").append("<option value='" + data[i].id + "'>" + data[i].name + "</option>")
            }
        },
        onError: function(error){
            console.log(error);
        }
    });
}

var getBooks = function getBooks() {
    sendAuthorisedRequest({
        url: "/api/get/books",
        onSuccess: function(data){
            renderTable(data);
        },
        onError: function(error){
            console.log(error);
        }
    });
}

$("#submit").on("click", function() {
    saveBook();
});

function renderTable(data) {
    table = $('#book-table').DataTable({
            "processing": true,
            "data": JSON.parse(data),
            "order": [[ 0, 'asc' ]],
            "columns": [
                { "data": "name" },
                { "data": "subject.name" },
                { "data": "schoolYear.name" },
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

var saveBook = function saveBook() {
    var schoolYearId = $("#school-year-select").val();
    var subjectId = $("#subject-select").val();
    var bookName = $("#book-name-input").val().trim();

    if(bookName != null && bookName != "" && subjectId > 0 && schoolYearId > 0) {
        var data = { subjectId : subjectId, name : bookName, schoolYearId : schoolYearId };

        sendAuthorisedPostRequest({
            url: "/api/save/book",
            data: data,
            onSuccess: function(response){
                getBooks();
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
    else if(schoolYearId <= 0) {
        notify("Select Class first", "Warning", "orange");
    }
    else if(subjectId <= 0) {
        notify("Select subject first", "Warning", "orange");
    }
    else {
        notify("Chapter name cannot be blank", "Warning", "orange");
    }
}

$(document).on("click", "#book-table tbody tr .delete", function() {
    var chapter = table.row($(this).parent().parent()).data();

    var r = confirm("You want to delete '" + chapter.name + "' permanently. This action is irreversible. Continue?");
    if (r == true) {
        var chapterId = chapter.id;

        sendAuthorisedRequest({
            url: "/api/delete/book/" + chapterId,
            onSuccess: function(response){
                table.destroy();
                getBooks();
                notify("Book deleted successfully", "Deleted", "blue");
            },
            onError: function(error){
                console.log(error);
                var msg = (JSON.parse(error.response)).message;
                notify(msg, "Error", "red");
            }
        });
    }
});