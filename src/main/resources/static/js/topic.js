var table;
$(document).ready(function() {
    initializePage({role: "ROLE_SUPER_ADMIN", onComplete: getData});
});

var getData = function getData() {
    getSubjectGroups();
    getTopics();
}

var getTopics = function getTopics() {
    sendAuthorisedRequest({
        url: "/api/get/topics",
        onSuccess: function(data){
            renderTable(data);
        },
        onError: function(error){
            console.log(error);
        }
    });
}

$("#submit").on("click", function() {
    saveTopic();
});

function renderTable(data) {
    table = $('#topic-table').DataTable({
            "processing": true,
            "data": JSON.parse(data),
            "order": [[ 0, 'asc' ]],
            "columns": [
                { "data": "name" },
                { "data": "chapter.name" },
                { "data": "chapter.subject.name" },
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

$(document).on("click", "#topic-table tbody tr .delete", function() {
    var topic = table.row($(this).parent().parent()).data();

    var r = confirm("You want to delete '" + topic.name + "' permanently? This action is irreversible. Continue?");
    if (r == true) {
        var topicId = topic.id;

        sendAuthorisedRequest({
            url: "/api/delete/topic/" + topicId,
            onSuccess: function(response){
                table.destroy();
                getTopics();
                notify("Topic deleted successfully", "Deleted", "blue");
            },
            onError: function(error){
                var msg = (JSON.parse(error.response)).message;
                notify(msg, "Error", "red");
            }
        });
    }
});

var saveTopic = function saveTopic() {
    var chapterId = $("#chapter-select").val();
    var topicName = $("#topic-name-input").val().trim();

    if(topicName != null && topicName != "" && chapterId > 0) {
        var data = { chapterId : chapterId, name : topicName };

        sendAuthorisedPostRequest({
            url: "/api/save/topic",
            data: data,
            onSuccess: function(response){
                getTopics();
                table.destroy();
                $("#topic-name-input").val("");
                notify("Topic created successfully", "Created", "blue");
            },
            onError: function(error){
                var msg = (JSON.parse(error.response)).message;
                notify(msg, "Error", "red");
            }
        });
    }
    else if(chapterId <= 0) {
        notify("Select chapter first", "Warning", "orange");
    }
    else {
        notify("Topic name cannot be blank", "Warning", "orange");
    }
}