var table;
$(document).ready(function() {
    initializePage({role: ["ROLE_SUPER_ADMIN", "ROLE_SUBJECT_EXPERT"], onComplete: getData});
});

var getData = function getData() {
    getSchoolYears();
    getBookChapters();
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
    var filteredChapters = [];

    for(var i = 0; i < allBookChapters.length; i++) {
        if(allBookChapters[i].book.name == bookName){
            filteredChapters.push(allBookChapters[i])
        }
    }

    table.destroy();
    renderTable(filteredChapters);
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

var allBookChapters = {};
var getBookChapters = function getBookChapters() {
    sendAuthorisedRequest({
        url: "/api/get/bookChapters",
        onSuccess: function(data){
            allBookChapters = JSON.parse(data);
            renderTable(allBookChapters);
        },
        onError: function(error){
            console.log(error);
        }
    });
}

$("#submit").on("click", function() {
    saveBookChapter();
});

var saveBookChapter = function saveBookChapter() {
    var bookId = $("#book-select").val();
    var bookChapterName = $("#book-chapter-name-input").val().trim();
    var bookChapterIndex = $("#book-chapter-index-input").val();

    getChapterIndexesForBook();
    if(filteredChapterIndexes.includes(parseInt(bookChapterIndex))) {
        notify("Chapter index already exists for the book", "Error", "red");
        stopWaiting("#submit");
        return;
    }

    if(bookChapterName != null && bookChapterName != "" && bookId > 0 && bookChapterIndex != null) {
        var data = { bookId : bookId, name : bookChapterName, chapterIndex : bookChapterIndex };

        sendAuthorisedPostRequest({
            url: "/api/save/bookChapter",
            data: data,
            onSuccess: function(response){
                getBookChapters();
                table.destroy();
                stopWaiting("#submit");
                $("#book-chapter-name-input").val("");
                notify("Chapter created successfully", "Created", "blue");
            },
            onError: function(error){
                var msg = (JSON.parse(error.response)).message;
                notify(msg, "Error", "red");
            }
        });
    }
    else if(bookId <= 0) {
        notify("Select Book first", "Warning", "orange");
        stopWaiting("#submit");
    }
    else if(bookChapterIndex == null || bookChapterIndex == "") {
        notify("Chapter index cannot be blank", "Warning", "orange");
        stopWaiting("#submit");
    }
    else {
        notify("Chapter name cannot be blank", "Warning", "orange");
        stopWaiting("#submit");
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

function renderTable(data) {
    table = $('#book-chapter-table').DataTable({
            "processing": true,
            "data": data,
            "order": [[ 2, 'asc' ], [ 0, 'asc' ]],
            "columns": [
                { "data": "chapterIndex" },
                { "data": "name" },
                { "data": "book.name" },
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

$(document).on("click", "#book-chapter-table tbody tr .delete", function() {
    var chapter = table.row($(this).parent().parent()).data();

    var r = confirm("You want to delete '" + chapter.name + "' permanently. This action is irreversible. Continue?");
    if (r == true) {
        var chapterId = chapter.id;

        sendAuthorisedRequest({
            url: "/api/delete/bookChapter/" + chapterId,
            onSuccess: function(response){
                getAllBookChapters();
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

var currentlyEditedChapterId = null;
var currentlyEditedChapterIndex = null;
$(document).on("click", "#book-chapter-table tbody tr .edit", function() {
    var chapter = table.row($(this).parent().parent()).data();
    currentlyEditedChapterId = chapter.id;

    $("#school-year-select").val(chapter.book.subjectYear.year.id);
    $("#book-chapter-name-input").val(chapter.name);
    $("#book-chapter-index-input").val(chapter.chapterIndex);

    currentlyEditedChapterIndex = chapter.chapterIndex;

    getBooks(function() {
        $("#book-select").val(chapter.book.id);
    });

    $("#update").show();
    $("#cancel-edit-btn").show();
    $("#submit").hide();
});

$("#cancel-edit-btn").click(function() {
    $("#book-chapter-name-input").val("");
    $("#book-chapter-index-input").val("");

    $("#update").hide();
    $("#cancel-edit-btn").hide();
    $("#submit").show();

    currentlyEditedChapterId = null;
});

$("#update").click(function() {
    var id = currentlyEditedChapterId;
    var name = $("#book-chapter-name-input").val();
    var index = $("#book-chapter-index-input").val();

    getChapterIndexesForBook();
    if(filteredChapterIndexes.includes(parseInt(index)) && currentlyEditedChapterIndex != index) {
        notify("Chapter index already exists for the book", "Error", "red");
        stopWaiting("#update");
        return;
    }

    if(index == null || index == "") {
        notify("Chapter index cannot be blank", "Warning", "orange");
        stopWaiting("#update");
        return;
    }

    if(name == null || name == "") {
        notify("Chapter name cannot be blank", "Warning", "orange");
        stopWaiting("#update");
        return;
    }

    var requestData = {bookChapterId : id, bookChapterName : name, bookChapterIndex : index};

    sendAuthorisedPostRequest({
        url: "/api/update/bookChapter",
        data: requestData,
        onSuccess: function(response){
            getAllBookChapters();
            stopWaiting("#update");
            $("#cancel-edit-btn").trigger("click");
            notify("Chapter Updated successfully", "Updated", "blue");
        },
        onError: function(error){
            var msg = (JSON.parse(error.response)).message;
            notify(msg, "Error", "red");
            stopWaiting("#update");
        }
    });
});

var getAllBookChapters = function getAllBookChapters() {
    sendAuthorisedRequest({
        url: "/api/get/bookChapters",
        onSuccess: function(data){
            allBookChapters = JSON.parse(data);

            var bookName = $("#book-select").find(":selected").text();
            var filteredChapters = [];

            for(var i = 0; i < allBookChapters.length; i++) {
                if(allBookChapters[i].book.name == bookName){
                    filteredChapters.push(allBookChapters[i]);
                }
            }

            table.destroy();
            renderTable(filteredChapters);
        },
        onError: function(error){
            console.log(error);
        }
    });
}

var filteredChapterIndexes = {};
var getChapterIndexesForBook = function getChapterIndexesForBook() {
    var bookName = $("#book-select").find(":selected").text();

    filteredChapterIndexes = [];
    for(var i = 0; i < allBookChapters.length; i++) {
        if(allBookChapters[i].book.name == bookName){
            filteredChapterIndexes.push(allBookChapters[i].chapterIndex);
        }
    }
}