var subjectList, schoolYearList, bookList, chapterList, topicList;

$(document).ready(function() {
    initializePage({role: ["ROLE_SUPER_ADMIN", "ROLE_SUBJECT_EXPERT", "ROLE_CONTRIBUTOR"], onComplete: getData});
});

var getData = function getData() {
    getSubjectGroups();
    getSchoolYears();
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
            var errorMsg = (JSON.parse(error.response)).message;
            notify(errorMsg, "Error", "red");
        }
    });
}

$("#subject-group-select").on("change", function() {
    getSubjectsByGroup();
    changeBuildBtnState();
});

var getSubjectsByGroup = function getSubjectsByGroup() {
    var subjectGroupId = $("#subject-group-select").val();

    var data = {subjectGroupId : subjectGroupId};

    sendAuthorisedPostRequest({
        url: "/api/get/subjectsByGroup",
        data: data,
        onSuccess: function(response){
            subjectList = JSON.parse(response);

            $("#subject-select").html("");
            $("#subject-select").append("<option disabled selected value='0'>Select Subject</option>");

            for(var i = 0; i < subjectList.length; i++) {
                $("#subject-select").append("<option data-name='" + subjectList[i].name + "' value='" + subjectList[i].id + "'>" + subjectList[i].name + "</option>")
            }

            $('#subject-select').prop('disabled', false);
        },
        onError: function(error){
            var errorMsg = (JSON.parse(error.response)).message;
            notify(errorMsg, "Error", "red");
        }
    });
}

$("#subject-select").change(function() {
    getBooksByYearAndSubject();
    getChaptersBySubject();
    changeBuildBtnState();
});

var getSchoolYears = function getSchoolYears() {
    sendAuthorisedRequest({
        url: "/api/get/schoolYears",
        onSuccess: function(data){
            data = JSON.parse(data);
            var length = data.length;

            for(var i = 0; i < length; i++) {
                $("#school-year-select").append("<option data-name='" + data[i].name + "' value='" + data[i].id + "'>Class " + data[i].name + "</option>")
            }
        },
        onError: function(error){
            var errorMsg = (JSON.parse(error.response)).message;
            notify(errorMsg, "Error", "red");
        }
    });
}

$("#school-year-select").change(function() {
    getBooksByYearAndSubject();
    changeBuildBtnState();
});

var getBooksByYearAndSubject = function getBooksByYearAndSubject() {
    var subjectId = $("#subject-select").val();
    var schoolYearId = $("#school-year-select").val();

    if(subjectId > 0 && schoolYearId > 0) {
        var data = { subjectId : subjectId, schoolYearId : schoolYearId };

        sendAuthorisedPostRequest({
            url: "/api/get/booksByYearAndSubject",
            data: data,
            onSuccess: function(response){
                bookList = JSON.parse(response);

                $("#book-select").html("");
                $("#book-select").append("<option disabled selected value='0'>Select Book</option>");
                for(var i = 0; i < bookList.length; i++) {
                    $("#book-select").append("<option data-name='" + bookList[i].name + "' value='" + bookList[i].id + "'>" + bookList[i].name + "</option>")
                }

                $('#book-select').prop('disabled', false);

                $("#reference-book-select").html("");
                $("#reference-book-select").append("<option disabled selected value='0'>Select Reference Book</option>");
                for(var i = 0; i < bookList.length; i++) {
                    $("#reference-book-select").append("<option data-name='" + bookList[i].name + "' value='" + bookList[i].id + "'>" + bookList[i].name + "</option>")
                }

                $('#reference-book-select').prop('disabled', false);
            },
            onError: function(error){
                var errorMsg = (JSON.parse(error.response)).message;
                notify(errorMsg, "Error", "red");
            }
        });
    }
    else {
        $('#book-select').prop('disabled', true);
    }
}

$("#book-select").change(function() {
    getBookChaptersByBook();
});

var getBookChaptersByBook = function getBookChaptersByBook() {
    var bookId = $("#book-select").val();

    if(bookId > 0 && bookId != null) {
        sendAuthorisedRequest({
            url: "/api/get/bookChaptersByBook/" + bookId,
            onSuccess: function(response){
                bookChapterList = JSON.parse(response);

                $("#book-chapter-select").html("");
                $("#book-chapter-select").append("<option disabled selected value='0'>Select Book Chapter</option>")
                for(var i = 0; i < bookChapterList.length; i++) {
                    $("#book-chapter-select").append("<option data-name='" + bookChapterList[i].name + "' value='" + bookChapterList[i].id + "'>" + bookChapterList[i].name + "</option>")
                }

                $('#book-chapter-select').prop('disabled', false);
            },
            onError: function(error){
                var errorMsg = (JSON.parse(error.response)).message;
                notify(errorMsg, "Error", "red");
            }
        });
    }
    else {
        notify("Something went wrong", "Error", "red");
    }
}

$("#book-chapter-select").change(function() {
    getBookTopicsByBookChapter();
});

var getBookTopicsByBookChapter = function getBookTopicsByBookChapter() {
    var bookChapterId = $("#book-chapter-select").val();
    var data = { bookChapterId : bookChapterId };

    sendAuthorisedPostRequest({
        url: "/api/get/bookTopicsByBookChapter",
        data: data,
        onSuccess: function(response){
            bookTopicList = JSON.parse(response);

            $("#book-topic-select").html("");
            $("#book-topic-select").append("<option disabled selected value='0'>Select Book Topic</option>")

            for(var i = 0; i < bookTopicList.length; i++) {
                $("#book-topic-select").append("<option data-name='" + bookTopicList[i].name + "' value='" + bookTopicList[i].id + "'>" + bookTopicList[i].name + "</option>")
            }

            $('#book-topic-select').prop('disabled', false);
        },
        onError: function(error){
            var errorMsg = (JSON.parse(error.response)).message;
            notify(errorMsg, "Error", "red");
        }
    });
}

$("#create-slide-btn").click(function() {
    var topicId = $("#book-topic-select").val();
    var topicName = $("#book-topic-select").find(":selected").text();

    localStorage.setItem("currentTopicId", topicId);
    localStorage.setItem("currentTopicName", topicName);
    localStorage.setItem("currentSlideIndex", 1);

    window.location.href = '/ManageSlides.html';
});

var getChaptersBySubject = function getChaptersBySubject() {
    var subjectId = $("#subject-select").val();
    var data = { subjectId : subjectId };

    sendAuthorisedPostRequest({
        url: "/api/get/chaptersBySubject",
        data: data,
        onSuccess: function(response){
            chapterList = JSON.parse(response);

            $("#chapter-select").html("");
            $("#chapter-select").append("<option disabled selected value='0'>Select Chapter</option>")

            for(var i = 0; i < chapterList.length; i++) {
                $("#chapter-select").append("<option data-name='" + chapterList[i].name + "' value='" + chapterList[i].id + "'>" + chapterList[i].name + "</option>")
            }

            $('#chapter-select').prop('disabled', false);
        },
        onError: function(error){
            var errorMsg = (JSON.parse(error.response)).message;
            notify(errorMsg, "Error", "red");
        }
    });
}

$("#chapter-select").change(function() {
    getTopicsByChapter();
    var chapterId = $(this).val();
    localStorage.setItem("selectedChapterIdForQuestions", chapterId);
});

var getTopicsByChapter = function getTopicsByChapter() {
    var chapterId = $("#chapter-select").val();
    var data = { chapterId : chapterId };

    sendAuthorisedPostRequest({
        url: "/api/get/topicsByChapter",
        data: data,
        onSuccess: function(response){
            topicList = JSON.parse(response);

            $("#topic-select").html("");
            $("#topic-select").append("<option disabled selected value='0'>Select Topic</option>")
            for(var i = 0; i < topicList.length; i++) {
                $("#topic-select").append("<option data-name='" + topicList[i].name + "' value='" + topicList[i].id + "'>" + topicList[i].name + "</option>")
            }

            $('#topic-select').prop('disabled', false);
        },
        onError: function(error){
            var errorMsg = (JSON.parse(error.response)).message;
            notify(errorMsg, "Error", "red");
        }
    });
}

$("#add-questions-btn").click(function() {
    var topicId = $("#topic-select").val();
    var topicName = $("#topic-select option:selected").text();
    localStorage.setItem("topicIdForQuestions", topicId);
    localStorage.setItem("topicNameForQuestions", topicName);

    localStorage.removeItem("currentQuestionId");

    window.location.href = '/addQuestions.html';
});

$("#review-questions-btn").click(function() {
    var topicId = $("#topic-select").val();
});