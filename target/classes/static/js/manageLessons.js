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
            var length = data.length;

            for(var i = 0; i < length; i++) {
                $("#subject-group-select").append("<option value='" + data[i].id + "'>" + data[i].name + "</option>")
            }

            var sg = localStorage.getItem("selectedSubjectGroup");
            if(sg != "" && sg != null) {
                $("#subject-group-select").val(sg);
                getSubjectsByGroup();
            }
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
    localStorage.setItem("selectedSubjectGroup", $(this).val());
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

            var i = 0;
            for(i = 0; i < subjectList.length; i++) {
                $("#subject-select").append("<option data-name='" + subjectList[i].name + "' value='" + subjectList[i].id + "'>" + subjectList[i].name + "</option>")
            }

            if(i > 0) {
                $('#subject-select').prop('disabled', false);

                var s = localStorage.getItem("selectedSubject");
                if(s != "" && s != null) {
                    $("#subject-select").val(s);
                    getChaptersBySubject();
                    changeBuildBtnState();

                    var y = $("#school-year-select").val();
                    if(y > 0) {
                        getBooksByYearAndSubject();
                    }
                }
            }
        },
        onError: function(error){
            var errorMsg = (JSON.parse(error.response)).message;
            notify(errorMsg, "Error", "red");
        }
    });
}

$("#subject-select").change(function() {
    getChaptersBySubject();
    getBooksByYearAndSubject();
    changeBuildBtnState();

    localStorage.setItem("selectedSubject", $(this).val());
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

            var i = 0;
            for(i = 0; i < chapterList.length; i++) {
                $("#chapter-select").append("<option data-name='" + chapterList[i].name + "' value='" + chapterList[i].id + "'>" + chapterList[i].name + "</option>")
            }

            if(i > 0) {
                var s = localStorage.getItem("selectedChapter");
                if(s != "" && s != null) {
                    $("#chapter-select").val(s);
                    getTopicsByChapter();
                    changeInitializeBtnState();
                }
            }

            $('#chapter-select').prop('disabled', false);
            $("#chapter-select").append("<option value='-1'>Add Chapter</option>");
        },
        onError: function(error){
            var errorMsg = (JSON.parse(error.response)).message;
            notify(errorMsg, "Error", "red");
        }
    });
}

$("#chapter-select").change(function() {
    var x = $("#chapter-select").val();

    if(x == -1) {
        var subjectId = $("#subject-select").val();

        if(subjectId != 0 && subjectId != -1) {
            $(".overlay").hide();
            $("#chapter-overlay").show();
        }
        else {
            notify("Select subject to add new chapter", "Attention", "orange");
        }
    }
    else {
        $("#topic-select").html("");
        $('#topic-select').prop('disabled', false);
        $("#topic-select").append("<option disabled selected value='0'>Select Topic</option>")
        $("#topic-select").append("<option value='-1'>Add Topic</option>");

        getTopicsByChapter();

        localStorage.setItem("selectedChapter", $(this).val());
    }

    changeInitializeBtnState();
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

            var i = 0;
            for(i = 0; i < topicList.length; i++) {
                $("#topic-select").append("<option data-name='" + topicList[i].name + "' value='" + topicList[i].id + "'>" + topicList[i].name + "</option>")
            }

            if(i > 0) {
                var s = localStorage.getItem("selectedTopic");
                if(s != "" && s != null) {
                    $("#topic-select").val(s);
                    changeInitializeBtnState();
                }
            }

            $('#topic-select').prop('disabled', false);
            $("#topic-select").append("<option value='-1'>Add Topic</option>");
        },
        onError: function(error){
            var errorMsg = (JSON.parse(error.response)).message;
            notify(errorMsg, "Error", "red");
        }
    });
}

$("#topic-select").change(function() {
    var x = $(this).val();
    if(x == -1) {
        $(".overlay").hide();
        $("#topic-overlay").show();
    }
    else {
        localStorage.setItem("selectedTopic", $(this).val());
    }

    changeInitializeBtnState();
});

var saveTopic = function saveTopic() {
    var chapterId = $("#chapter-select").val();
    var topicName = $("#topic-name-input").val().trim();

    if(topicName != null && topicName != "") {
        var data = { chapterId : chapterId, name : topicName };

        sendAuthorisedPostRequest({
            url: "/api/save/topic",
            data: data,
            onSuccess: function(response){
                getTopicsByChapter();

                $(".overlay").hide();
                $("#topic-name-input").val("");
                notify("Topic created successfully", "Created", "blue");
            },
            onError: function(error){
                var errorMsg = (JSON.parse(error.response)).message;
                notify(errorMsg, "Error", "red");
            }
        });
    }
    else {
        notify("Topic name cannot be blank", "Warning", "orange");
    }
}

var getSchoolYears = function getSchoolYears() {
    sendAuthorisedRequest({
        url: "/api/get/schoolYears",
        onSuccess: function(data){
            data = JSON.parse(data);
            var length = data.length;

            var i = 0;
            for(i = 0; i < length; i++) {
                $("#school-year-select").append("<option data-name='" + data[i].name + "' value='" + data[i].id + "'>Class " + data[i].name + "</option>")
            }

            if(i > 0) {
                var s = localStorage.getItem("selectedSchoolYear");
                if(s != "" && s != null) {
                    $("#school-year-select").val(s);

                    var sub = $("#subject-select").val();
                    if(sub > 0) {
                        getBooksByYearAndSubject();
                        changeBuildBtnState();
                    }
                }
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
    localStorage.setItem("selectedSchoolYear", $(this).val());
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

                var i = 0;
                for(var i = 0; i < bookList.length; i++) {
                    $("#book-select").append("<option data-name='" + bookList[i].name + "' value='" + bookList[i].id + "'>" + bookList[i].name + "</option>")
                }

                if(i > 0) {
                    var s = localStorage.getItem("selectedBook");
                    if(s != "" && s != null) {
                        $("#book-select").val(s);
                        getBookChaptersByBook();
                        changeAddAttributeBtnState();
                    }
                }

                $('#book-select').prop('disabled', false);
                $("#book-select").append("<option value='-1'>Add Book</option>");
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
    var bookId = $("#book-select").val();

    if(bookId == -1) {
        $(".overlay").hide();
        $("#book-overlay").show();
    }
    else {
        getBookChaptersByBook();
        changeAddAttributeBtnState();

        localStorage.setItem("selectedBook", $(this).val());
    }
});

var saveBook = function saveBook() {
    var subjectId = $("#subject-select").val();
    var schoolYearId = $("#school-year-select").val();
    var bookName = $("#book-name-input").val().trim();

    if(bookName == null || bookName == "") {
        notify("Book name cannot be blank", "Warning", "orange");
    }
    else {
        var data = { subjectId : subjectId, schoolYearId : schoolYearId, name : bookName };

        sendAuthorisedPostRequest({
            url: "/api/save/book",
            data: data,
            onSuccess: function(response){
                getBooksByYearAndSubject();

                $(".overlay").hide();
                notify("Book created successfully");
            },
            onError: function(error){
                var errorMsg = (JSON.parse(error.response)).message;
                notify(errorMsg, "Error", "red");
            }
        });
    }
}

var existingChapterIndexes = [];
var getBookChaptersByBook = function getBookChaptersByBook() {
    existingChapterIndexes = [];

    var bookId = $("#book-select").val();
    var data = { bookId : bookId };

    if(bookId > 0 && bookId != null) {
        sendAuthorisedPostRequest({
            url: "/api/get/bookChaptersByBook",
            data: data,
            onSuccess: function(response){
                bookChapterList = JSON.parse(response);

                $("#book-chapter-select").html("");
                $("#book-chapter-select").append("<option disabled selected value='0'>Select Book Chapter</option>")

                var i = 0;
                for(i = 0; i < bookChapterList.length; i++) {
                    $("#book-chapter-select").append("<option data-name='" + bookChapterList[i].name + "' value='" + bookChapterList[i].id + "'>" + bookChapterList[i].name + "</option>")
                    existingChapterIndexes.push(bookChapterList[i].chapterIndex);
                }

                if(i > 0) {
                    var s = localStorage.getItem("selectedBookChapter");
                    if(s != "" && s != null) {
                        $("#book-chapter-select").val(s);
                        getBookTopicsByBookChapter();
                        changeAddAttributeBtnState();
                    }
                }

                $('#book-chapter-select').prop('disabled', false);
                $("#book-chapter-select").append("<option value='-1'>Add Book Chapter</option>");
            },
            onError: function(error){
                var errorMsg = (JSON.parse(error.response)).message;
                notify(errorMsg, "Error", "red");
            }
        });
    }
}

$("#book-chapter-select").change(function() {
    var bookChapterId = $("#book-chapter-select").val();

    if(bookChapterId == -1) {
        var chapterId = $("#chapter-select").val();

        if(chapterId < 1) {
            notify("Select Parent Chapter First");
            $("#chapter-select").focus();
            $("#book-chapter-select").val($("#book-chapter-select option:first").val());
        }
        else {
            $(".overlay").hide();
            $("#book-chapter-overlay").show();
        }
    }
    else {
        getBookTopicsByBookChapter();
        changeAddAttributeBtnState();

        localStorage.setItem("selectedBookChapter", $(this).val());
    }
});

var saveBookChapter = function saveBookChapter() {
    var bookId = $("#book-select").val();
    var name = $("#book-chapter-name-input").val().trim();
    var chapterId = $("#chapter-select").val();
    var chapterIndex = $("#book-chapter-index-input").val();

    var data = { bookId : bookId, name : name, chapterIndex : chapterIndex, chapterId : chapterId };

    if(name == "" || name == null) {
        notify("Chapter Name cannot be blank", "Warning", "orange");
        return;
    }

    if(chapterIndex == "" || chapterIndex == null) {
        notify("Chapter Index cannot be blank", "Warning", "orange");
        return;
    }

    for(var z = 0; z < existingChapterIndexes.length; z++) {
        console.log("Index " + z + " : " + existingChapterIndexes[z]);
        if(existingChapterIndexes[z] == chapterIndex) {
            notify("Index is already assigned to another chapter", "Warning", "orange");
            return;
        }
    }

    sendAuthorisedPostRequest({
        url: "/api/save/bookChapter",
        data: data,
        onSuccess: function(response){
            getBookChaptersByBook();
            existingChapterIndexes = [];
            $(".overlay").hide();
            notify("Book Chapter created successfully");
        },
        onError: function(error){
            var errorMsg = (JSON.parse(error.response)).message;
            notify(errorMsg, "Error", "red");
        }
    });
}

var existingTopicIndexes = [];
var getBookTopicsByBookChapter = function getBookTopicsByBookChapter() {
    existingTopicIndexes = [];

    var bookChapterId = $("#book-chapter-select").val();
    var data = { bookChapterId : bookChapterId };

    sendAuthorisedPostRequest({
        url: "/api/get/bookTopicsByBookChapter",
        data: data,
        onSuccess: function(response){
            bookTopicList = JSON.parse(response);

            $("#book-topic-select").html("");
            $("#book-topic-select").append("<option disabled selected value='0'>Select Book Chapter</option>")

            var i = 0;
            for(i = 0; i < bookTopicList.length; i++) {
                $("#book-topic-select").append("<option data-name='" + bookTopicList[i].name + "' value='" + bookTopicList[i].id + "'>" + bookTopicList[i].name + "</option>")
                existingTopicIndexes.push(bookTopicList[i].topicIndex);
            }

            if(i > 0) {
                var s = localStorage.getItem("selectedBookTopic");
                if(s != "" && s != null) {
                    $("#book-topic-select").val(s);
                    changeAddAttributeBtnState();
                }
            }

            $('#book-topic-select').prop('disabled', false);
            $("#book-topic-select").append("<option value='-1'>Add Book Topic</option>");
        },
        onError: function(error){
            var errorMsg = (JSON.parse(error.response)).message;
            notify(errorMsg, "Error", "red");
        }
    });
}

$("#book-topic-select").change(function() {
    var bookTopicId = $("#book-topic-select").val();

    if(bookTopicId == -1) {
        var topicId = $("#topic-select").val();

        if(topicId < 1) {
            notify("Select parent topic first");
            $("#topic-select").focus();
            $("#book-topic-select").val($("#book-chapter-select option:first").val());
        }
        else {
            $(".overlay").hide();
            $("#book-topic-overlay").show();
        }
    }
    else {
        localStorage.setItem("selectedBookTopic", $(this).val());
        changeAddAttributeBtnState();
    }
});

var saveBookTopic = function saveBookTopic() {
    var bookChapterId = $("#book-chapter-select").val();
    var name = $("#book-topic-name-input").val().trim();
    var topicId = $("#topic-select").val();
    var topicIndex = $("#book-topic-index-input").val();

    var data = { topicId : topicId, name : name, topicIndex : topicIndex, bookChapterId : bookChapterId };

    if(name == "" || name == null) {
        notify("Topic Name cannot be blank", "Warning", "orange");
        return;
    }

    if(topicIndex == "" || topicIndex == null) {
        notify("Topic Index cannot be blank", "Warning", "orange");
        return;
    }

    for(var z = 0; z < existingTopicIndexes.length; z++) {
        console.log("Index " + z + " : " + existingTopicIndexes[z]);
        if(existingTopicIndexes[z] == topicIndex) {
            $("#topic-error-notification").html("Index is already assigned to another topic");
            return;
        }
    }

    sendAuthorisedPostRequest({
        url: "/api/save/bookTopic",
        data: data,
        onSuccess: function(response){
            getBookTopicsByBookChapter();
            existingTopicIndexes = [];
            $(".overlay").hide();
            notify("Book topic created successfully");
        },
        onError: function(error){
            var errorMsg = (JSON.parse(error.response)).message;
            notify(errorMsg, "Error", "red");
        }
    });
}

$("#create-chapter-btn").click(function() {
    saveChapter();
});

$("#create-topic-btn").click(function() {
    saveTopic();
});

$("#create-book-btn").click(function() {
    saveBook();
});

$("#create-book-chapter-btn").click(function() {
    saveBookChapter();
});

$("#create-book-topic-btn").click(function() {
    saveBookTopic();
});

$(".overlay").click(function(e) {
    $(this).hide();
});

$(".create-form").click(function(e) {
    e.stopPropagation();
});

$("#create-slide-btn").click(function() {
    var topicId = $("#topic-select").val();
    var topicName = $("#topic-select").find(":selected").text();

    localStorage.setItem("currentTopicId", topicId);
    localStorage.setItem("currentTopicName", topicName);
    localStorage.setItem("currentSlideIndex", 1);

    window.location.href = '/ManageSlides.html';
});

$(".close-form").click(function() {
    $(this).parent().hide();
});