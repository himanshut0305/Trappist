var table;
$(document).ready(function() {
    initializePage({role: "ROLE_SUPER_ADMIN", onComplete: getData});
    renderTable("[]");
});

function getData() {
    getSchoolBoards();
    getSchoolChains();
    getSchoolYears();
}

function getSchoolBoards(){
    sendAuthorisedRequest({
        url:"/api/get/boards",
        onSuccess : function(response){
            response = JSON.parse(response);
            for(var i=0;i<response.length;i++){
                $("#school-board-select").append("<option value = '"+response[i].id+"'>"+response[i].aka+"</option>");
            }
        },
        onError:function(error){
            console.log(error);
        }
    })
}

function getSchoolChains(){
    sendAuthorisedRequest({
        url:"/api/get/schoolChains",
        onSuccess : function(response){
            response = JSON.parse(response);
            for(var i=0;i<response.length;i++){
                $("#school-chain-select").append("<option value = '"+response[i].id+"'>"+response[i].name+"</option>");
            }
        },
        onError:function(error){
            console.log(error);
        }
    });
}

function getSchools(){
    sendAuthorisedRequest({
        url:"/api/get/schools",
        onSuccess : function(response){
            response = JSON.parse(response);
            for(var i=0;i<response.length;i++){
                $("#school-select").append("<option value = '"+response[i].id+"'>"+response[i].name+"</option>");
            }
        },
        onError:function(error){
            console.log(error);
        }
    })
}

$("#school-chain-select").change(function(){
    getSchoolsByBoardAndGroup();
})

function getSchoolsByBoardAndGroup(){
    var boardId = $("#school-board-select").val();
    var groupId = $("#school-chain-select").val();

    var jsonData = {
        boardId :boardId,
        groupId:groupId
    }

    sendAuthorisedPostRequest({
        url:"/api/get/schoolsByBoardAndGroup",
        data : jsonData,
        onSuccess: function(response){
            response = JSON.parse(response);
            $("#school-select").html("");
            $("#school-select").append("<option value='0'>Select School</option>")
            for(var i=0;i<response.length;i++){
                $("#school-select").append("<option value = '"+response[i].id+"'>"+response[i].name+"</option>");
            }
        },
        onError: function(error){
            console.log(error);
        }
    });
}

function getSubjects(){
    sendAuthorisedRequest({
        url:"/api/get/subjects",
        onSuccess : function(response){
            response = JSON.parse(response);
            for(var i=0;i<response.length;i++){
                $("#subject-select").append("<option value = '"+response[i].id+"'>"+response[i].name+"</option>");
            }
        },
        onError:function(error){
            console.log(error);
        }
    })
}

function getBookByYearAndSubject(){
    var subjectId = $("#subject-select").val();
    var yearId = $("#school-year-select").val();

    var jsonData = {
       subjectId : subjectId,
       schoolYearId : yearId
    }

    sendAuthorisedPostRequest({
        url:"/api/get/booksByYearAndSubject",
        data : jsonData,
        onSuccess: function(response){
            response = JSON.parse(response);
            $("#book-select").html("");
            $("#book-select").append("<option value='0'>Select Book</option>");
            for(var i=0; i<response.length; i++){
                $("#book-select").append("<option value = '"+response[i].id+"'>"+response[i].name+"</option>");
            }
        },
        onError: function(error){
            console.log(error);
        }
    })
}

$("#subject-select").change(function(){
    getBookByYearAndSubject();
})

function getSchoolYears(){
    sendAuthorisedRequest({
        url:"/api/get/schoolYears",
        onSuccess: function(response) {
            response = JSON.parse(response);
            for(var i=0;i<response.length;i++){
                $("#school-year-select").append("<option value = '"+response[i].id+"'>Class "+response[i].name+"</option>");
            }

            $(".loading-overlay-div").hide();
        },
        onError: function(error) {
            console.log(error);
        }
    })
}

$("#school-select").change(function() {
    getSchoolClassesBySchool();
});

function getSchoolClassesBySchool(){
    var schoolId = $("#school-select").val();

    var jsonData = {
        schoolId : schoolId
    }

    sendAuthorisedPostRequest({
        url: "/api/get/schoolClassesBySchool",
        data: jsonData,
        onSuccess: function(response){
            table.destroy();
            renderTable(response);
            response = JSON.parse(response);

            var result = response.reduce(function (r, a) {
                    r[a.schoolYear.year.name] = r[a.schoolYear.year.name] || [];
                    r[a.schoolYear.year.name].push({"schoolYearId" : a.schoolYear.id, "yearId" : a.schoolYear.year.id});
                    return r;
                }, Object.create(null));

            for (x in result) {
                $("#respective-school-year-select").append("<option value = '"+ result[x][0].schoolYearId +"' data-year-id='"+ result[x][0].yearId +"' >Class "+ x +"</option>");
            }
        },
        onError: function(error){
            console.log(error);
        }
    })
}

function onlyUnique(value, index, self) {
    return self.indexOf(value) === index;
}


function saveClasses() {
    var schoolId = $("#school-select").val();
    var yearId = $("#school-year-select").val();
    var fromSection = $("#from-section").val();
    var toSection = $("#to-section").val();

    var jsonData = {
        schoolId : schoolId,
        yearId : yearId,
        fromSection : fromSection,
        toSection : toSection
    }

    sendAuthorisedPostRequest({
        url: "/api/save/schoolClasses",
        data: jsonData,
        onSuccess: function(response) {
            notify("Classes saved successfully", "Created", "blue");
        },
        onError: function(error) {
            console.log(error);
        }
    })
}

$("#submit").click(function() {
    saveClasses();
});

function renderTable(data) {
    table = $('#school-classes-table').DataTable({
                "processing": true,
                "data": JSON.parse(data),
                "order": [[ 0, 'asc' ]],
                "columns": [
//                    {
//                        "className":      'details-control',
//                        "orderable":      false,
//                        "data":           null,
//                        "defaultContent": ''
//                    },
                    { "data": "name" },
                    {
                        "data": null,
                        "render": function (data, type, row, meta) {
                            if(data.books == null || data.books == [] || data.books.length == 0)
                                return "NO_BOOK"
                            else
                                return "HAS_BOOKS"
                        }
                    },
                    {
                        "orderable":      false,
                        "data":           null,
                        "defaultContent": "<i class='material-icons add' style='cursor: pointer;'>library_books</i>"
                    },
                    {
                        "orderable":      false,
                        "data":           null,
                        "defaultContent": "<i class='material-icons edit' style='cursor: pointer;'>edit</i>"
                    },
                    {
                        "orderable":      false,
                        "data":           null,
                        "defaultContent": "<i class='material-icons delete' style='cursor: pointer;'>delete</i>"
                    }
                ]
            });
}

function format (data) {
    console.log(data);
    var subjects = data.schoolYear.subjects;
    console.log(subjects);

    var tds = "";
    for(var i = 0; i < subjects.length; i++) {
        tds = tds + "<tr><td>"+ subjects[i].name +"</td><td><button class='add-book-btn'>Add Book</button></td></tr>"
    }

    return '<div class="col-lg-6"><table width="100%">'+ tds +'</table></div>';
}

$(document).on('click', '#school-classes-table tbody td.details-control', function () {
    var tr = $(this).closest('tr');
    var row = table.row(tr);

    if (row.child.isShown()) {
        row.child.hide();
        tr.removeClass('shown');
    }
    else {
        row.child(format(row.data())).show();
        tr.addClass('shown');
    }
});

$(document).on('click', '.add-book-btn', function() {

});