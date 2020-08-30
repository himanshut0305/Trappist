$(document).ready(function() {
    initializePage({role: ["ROLE_SUPER_ADMIN", "ROLE_SUBJECT_EXPERT", "ROLE_CONTRIBUTOR"], onComplete: getData});
});

var getData = function getData() {
    getSchoolYears();
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
});

$("#book-chapter-select").change(function() {
    getBookTopicsByBookChapter();
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

var getBookTopicsByBookChapter = function getBookTopicsByBookChapter() {
    var bookChapterId = $("#book-chapter-select").val();
    var data = { bookChapterId : bookChapterId };

    sendAuthorisedPostRequest({
        url: "/api/get/bookTopicsByBookChapter",
        data: data,
        onSuccess: function(response){
            bookTopicList = JSON.parse(response);
            console.log(bookTopicList);

            for(var i = 0; i < bookTopicList.length; i++) {
                if(bookTopicList[i].qpt != null) {
                    $("#book-topic-qpt-list").append("<tr><td>" + bookTopicList[i].topicIndex + ". " + bookTopicList[i].name + "</td><td></td></tr>");
                }
                else {
                    $("#book-topic-qpt-list").append("<tr><td>" + bookTopicList[i].topicIndex + ". " + bookTopicList[i].name + "</td><td><button class='add-qpt-btn btn' data-topic-id='"+ bookTopicList[i].id +"'>Add QPT After</button></td></tr>");
                }

                if(bookTopicList[i].doesPrecedeQPT) {
                    $("#book-topic-qpt-list").append("<tr><td id='qpt-div-"+ bookTopicList[i].id +"'>"+ bookTopicList[i].qpt.name +"</td><td><button class='remove-qpt-btn btn' data-qpt-id='"+ bookTopicList[i].qpt.id +"'>Remove</button></td></tr>");
                }
            }
        },
        onError: function(error){
            var errorMsg = (JSON.parse(error.response)).message;
            notify(errorMsg, "Error", "red");
        }
    });
}

$(document).on("click", ".add-qpt-btn", function() {
    var t = $(this).data("topic-id");

    sendAuthorisedRequest({
        url: "/api/save/qptAfterBookTopic/" + t,
        onSuccess: function(response) {
            var r = JSON.parse(response);
            console.log(r);
            notify("Successfully Created", "Success", "blue");
        },
        onError: function(error) {
            var errorMsg = (JSON.parse(error.response)).message;
            notify(errorMsg, "Error", "red");
        }
    })
});


$(document).on("click", ".remove-qpt-btn", function() {
    var t = $(this).data("qpt-id");

    sendAuthorisedRequest({
        url: "/api/delete/qpt/" + t,
        onSuccess: function(response) {
            var r = JSON.parse(response);
            console.log(r);
            notify("Successfully Deleted", "Success", "blue");
        },
        onError: function(error) {
            var errorMsg = (JSON.parse(error.response)).message;
            notify(errorMsg, "Error", "red");
        }
    })
});