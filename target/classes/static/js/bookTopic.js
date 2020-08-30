$(document).ready(function() {
    initializePage({role: ["ROLE_SUPER_ADMIN", "ROLE_SUBJECT_EXPERT", "ROLE_CONTRIBUTOR"], onComplete: getData});
});

var getData = function getData() {
    getSchoolYears();
    getBookTopics();
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

            $(".loading-overlay-div").hide();
        },
        onError: function(error){
            console.log(error);
        }
    });
}

$("#school-year-select").change(function() {
    getBooks();
});

$("#book-select").change(function() {
    var bookName = $("#book-select").find(":selected").text();
    getBookChaptersByBook();

    var filteredTopics = [];

    for(var i = 0; i < allBookTopics.length; i++) {
        if(allBookTopics[i].bookChapter.book.name == bookName){
            filteredTopics.push(allBookTopics[i])
        }
    }

    table.destroy();
    renderTable(filteredTopics);
});

$("#book-chapter-select").change(function() {
    var bookName = $("#book-select").find(":selected").text();
    var bookChapter = $("#book-chapter-select").find(":selected").text();

    var filteredTopics = [];
    for(var i = 0; i < allBookTopics.length; i++) {
        if(allBookTopics[i].bookChapter.book.name == bookName && allBookTopics[i].bookChapter.name == bookChapter){
            filteredTopics.push(allBookTopics[i])
        }
    }

    table.destroy();
    renderTable(filteredTopics);
});

var getBooks = function getBooks(onComplete) {
    var schoolYearId = $("#school-year-select").val();

    if(schoolYearId > 0) {
        sendAuthorisedRequest({
            url: "/api/get/booksByYear/" + schoolYearId,
            onSuccess: function(data){
                data = JSON.parse(data);

                $("#book-select").html("");
                $("#book-select").append("<option disabled selected>Select Book</option>");
                for(var i = 0; i < data.length; i++) {
                    $("#book-select").append("<option value='" + data[i].id + "'>" + data[i].name + "</option>")
                }

                if(onComplete != null)
                    onComplete();
            },
            onError: function(error){
                console.log(error);
            }
        });
    }
}

var allBookTopics = {};
var getBookTopics = function getBookTopics() {
    sendAuthorisedRequest({
        url: "/api/get/bookTopics",
        onSuccess: function(data){
            allBookTopics = JSON.parse(data);
            renderTable(allBookTopics);
        },
        onError: function(error){
            console.log(error);
        }
    });
}

var getBookChaptersByBook = function getBookChaptersByBook(onComplete) {
    var bookId = $("#book-select").val();

    sendAuthorisedRequest({
        url: "/api/get/bookChaptersByBook/" + bookId,
        onSuccess: function(data){
            data = JSON.parse(data);

            $("#book-chapter-select").html("");
            $("#book-chapter-select").append("<option disabled selected>Select Chapter</option>");
            for(var i = 0; i < data.length; i++) {
                $("#book-chapter-select").append("<option value='" + data[i].id + "'>" + data[i].name + "</option>")
            }

            if(onComplete != null)
                onComplete();
        },
        onError: function(error){
            console.log(error);
        }
    });
}

$("#submit").on("click", function() {
    saveBookTopic();
});

var saveBookTopic = function saveBookChapter() {
    var bookChapterID = $("#book-chapter-select").val();
    var bookTopicName = $("#book-topic-name-input").val().trim();
    var bookTopicIndex = $("#book-topic-index-input").val();
    var bookTopicDescription = $("#book-topic-description").val();

    getTopicIndexesForChapter();
    if(filteredTopicIndexes.includes(parseInt(bookTopicIndex))) {
        notify("Topic index already exists for the chapter", "Error", "red");
        stopWaiting("#submit");
        return;
    }

    if(bookTopicName != null && bookTopicName != "" && bookChapterID > 0 && bookTopicIndex != null) {
        var data = { bookChapterId : bookChapterID, name : bookTopicName, topicIndex : bookTopicIndex, description : bookTopicDescription };

        sendAuthorisedPostRequest({
            url: "/api/save/bookTopic",
            data: data,
            onSuccess: function(response){
                getAllBookTopics();
                $("#book-chapter-name-input").val("");
                notify("Chapter created successfully", "Created", "blue");
            },
            onError: function(error){
                var msg = (JSON.parse(error.response)).message;
                notify(msg, "Error", "red");
            }
        });
    }
    else if(bookChapterID <= 0) {
        notify("Select Chapter first", "Warning", "orange");
    }
    else if(bookTopicIndex == null) {
        notify("Topic index cannot be blank", "Warning", "orange");
    }
    else {
        notify("Topic name cannot be blank", "Warning", "orange");
    }
}

$(document).on("click", "#book-topic-table tbody tr .delete", function() {
    var topic = table.row($(this).parent().parent()).data();

    var r = confirm("You want to delete '" + topic.name + "' permanently. This action is irreversible. Continue?");
    if (r == true) {
        var topicId = topic.id;

        sendAuthorisedRequest({
            url: "/api/delete/bookTopic/" + topicId,
            onSuccess: function(response){
                getAllBookTopics();
                notify("Topic deleted successfully", "Deleted", "blue");
            },
            onError: function(error){
                console.log(error);
                var msg = (JSON.parse(error.response)).message;
                notify(msg, "Error", "red");
            }
        });
    }
});

function renderTable(data) {
    table = $('#book-topic-table').DataTable({
                "processing": true,
                "data": data,
                "order": [[3, 'asc'], [2, 'asc'], [0, 'asc']],
                "columns": [
                    { "data": "topicIndex" },
                    { "data": "name" },
                    { "data": "bookChapter.name" },
                    { "data": "bookChapter.book.name" },
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

var currentlyEditedTopicId = null;
var currentlyEditedTopicIndex = null;
$(document).on("click", "#book-topic-table tbody tr .edit", function() {
    var topic = table.row($(this).parent().parent()).data();

    console.log(topic);

    currentlyEditedTopicId = topic.id;

    $("#school-year-select").val(topic.bookChapter.book.subjectYear.year.id);
    $("#book-topic-name-input").val(topic.name);
    $("#book-topic-index-input").val(topic.topicIndex);
    $("#book-topic-description").val(topic.description);

    currentlyEditedTopicIndex = topic.topicIndex;

    getBooks(function() {
        $("#book-select").val(topic.bookChapter.book.id);

        getBookChaptersByBook(function(){
            $("#book-chapter-select").val(topic.bookChapter.id);
        });
    });

    $("#update").show();
    $("#cancel-edit-btn").show();
    $("#submit").hide();
});

$("#cancel-edit-btn").click(function() {
    $("#book-topic-name-input").val("");
    $("#book-topic-index-input").val("");
    $("#book-topic-description").val("");

    $("#update").hide();
    $("#cancel-edit-btn").hide();
    $("#submit").show();

    currentlyEditedChapterId = null;
});

$("#update").click(function() {
    var id = currentlyEditedTopicId;
    var name = $("#book-topic-name-input").val();
    var index = $("#book-topic-index-input").val();
    var description = $("#book-topic-description").val();

    getTopicIndexesForChapter();
    if(filteredTopicIndexes.includes(parseInt(index)) && currentlyEditedTopicIndex != index) {
        notify("Topic index already exists for the chapter", "Error", "red");
        stopWaiting("#update");
        return;
    }

    var requestData = {bookTopicId : id, bookTopicName : name, bookTopicIndex : index, description: description};

    sendAuthorisedPostRequest({
        url: "/api/update/bookTopic",
        data: requestData,
        onSuccess: function(response){
            getAllBookTopics();

            $("#cancel-edit-btn").trigger("click");
            notify("Topic Updated successfully", "Updated", "blue");
        },
        onError: function(error){
            var msg = (JSON.parse(error.response)).message;
            notify(msg, "Error", "red");
        }
    });
});

var getAllBookTopics = function getAllBookTopics() {
    sendAuthorisedRequest({
        url: "/api/get/bookTopics",
        onSuccess: function(data){
            allBookTopics = JSON.parse(data);

            var bookName = $("#book-select").find(":selected").text();
            var bookChapter = $("#book-chapter-select").find(":selected").text();

            var filteredTopics = [];
            for(var i = 0; i < allBookTopics.length; i++) {
                if(allBookTopics[i].bookChapter.book.name == bookName && allBookTopics[i].bookChapter.name == bookChapter){
                    filteredTopics.push(allBookTopics[i])
                }
            }

            table.destroy();
            renderTable(filteredTopics);
        },
        onError: function(error){
            console.log(error);
        }
    });
}

var filteredTopicIndexes = [];
var getTopicIndexesForChapter = function getTopicIndexesForChapter() {
    var bookName = $("#book-select").find(":selected").text();
    var bookChapter = $("#book-chapter-select").find(":selected").text();

    filteredTopicIndexes = [];
    for(var i = 0; i < allBookTopics.length; i++) {
        if(allBookTopics[i].bookChapter.book.name == bookName && allBookTopics[i].bookChapter.name == bookChapter){
            filteredTopicIndexes.push(allBookTopics[i].topicIndex);
        }
    }
}