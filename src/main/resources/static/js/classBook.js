$("#respective-school-year-select").change(function(){
    getSubjectAndBookBySchoolYear();
});

function getSubjectAndBookBySchoolYear(){
    var schoolId = $("#school-select").val();
    var schoolYearId =$("#respective-school-year-select").val();

    var jsonData = {
        schoolId : schoolId,
        schoolYearId: schoolYearId
    }

    sendAuthorisedPostRequest({
        url:"/api/get/subjectsAndBookBySchoolYear",
        data : jsonData,
        onSuccess: function(response){
            response = JSON.parse(response);

            $("#subject-book-list").html("");
            $("#subject-select").html("");

            for(var i = 0; i < response.length; i++) {
                if(response[i].book == null) {
                    $("#subject-book-list").append("<p>"+ response[i].subject.name +"</p>")
                    $("#subject-select").append("<option value='"+ response[i].subject.id +"'>"+ response[i].subject.name +"</option>")
                }
                else {
                    $("#subject-book-list").append("<p>"+ response[i].subject.name +" "+ response[i].book.name +"</p>")
                }
            }
        },
        onError: function(error){
            console.log(error);
        }
    });
}

$("#subject-select").change(function() {
    getBooksByYearAndSubject();
});

function getBooksByYearAndSubject(){
    var subjectId = $("#subject-select").val();
    var schoolYearId =$("#respective-school-year-select option:selected").data("year-id");

    var jsonData = {
        subjectId: subjectId,
        schoolYearId: schoolYearId
    }

    sendAuthorisedPostRequest({
        url:"/api/get/booksByYearAndSubject",
        data : jsonData,
        onSuccess: function(response){
            response = JSON.parse(response);
            for(var i = 0; i < response.length; i++) {
                $("#book-select").append("<option value='"+ response[i].id +"'>"+ response[i].name +"</option>")
            }
        },
        onError: function(error){
            console.log(error);
        }
    });
}


$(document).on("click", ".save-book", function() {
    var subjectId = $(this).data("subject-id");
    var schoolYearId =$("#school-year-select").val();

    var jsonData = {
       subjectId : subjectId,
       schoolYearId : schoolYearId
    }

    sendAuthorisedPostRequest({
        url:"/api/get/booksByYearAndSubject",
        data : jsonData,
        onSuccess: function(response){
            response = JSON.parse(response);
            console.log(response);
        },
        onError: function(error){
            console.log(error);
        }

    });
});

$("#assign-book").click(function() {
    var subjectId = $("#subject-select").val();
    var schoolYearId = $("#respective-school-year-select").val();
    var bookId = $("#book-select").val();

    var jsonData = {
       subjectId : subjectId,
       schoolYearId : schoolYearId,
       bookId: bookId
    }

    console.log(jsonData);

    sendAuthorisedPostRequest({
        url:"/api/save/bookForSchoolYearAndSubject",
        data : jsonData,
        onSuccess: function(response){
            response = JSON.parse(response);
            console.log(response);
        },
        onError: function(error){
            console.log(error);
        }
    });
});