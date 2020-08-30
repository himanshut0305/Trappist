$("#subject-group-select").change(function() {
    getSubjects();
});

$("#subject-select").change(function() {
    getChapters();
});

var getSubjectGroups = function getSubjectGroups() {
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
    var subjectId = $("#subject-select").val();
    var requestData = {subjectId: subjectId};

    sendAuthorisedPostRequest({
        url: "/api/get/chaptersBySubject",
        data: requestData,
        onSuccess: function(data){
            data = JSON.parse(data);

            $("#chapter-select").html("");
            $("#chapter-select").append("<option value='0' selected disabled>Select Chapter</option>")
            for(var i = 0; i < data.length; i++) {
                $("#chapter-select").append("<option value='" + data[i].id + "'>" + data[i].name + "</option>")
            }
        },
        onError: function(error){
            console.log(error);
        }
    });
}