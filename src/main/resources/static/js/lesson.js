$(".qed-sub-menu-item > div").hover(function() {
    $("#current-sub-module-name").html($(this).data("name"));
},
function() {
    $("#current-sub-module-name").html("");
});

$(".qed-sub-menu-item > div").click(function() {
    if($(this).data("ally") == "manage-chapters") {
        if(!isBuilt) {
            notify("Build the workspace first", "Notification", "blue");
            return;
        }
    }
    else if($(this).data("ally") == "manage-books") {
        if(!isInitialised) {
            notify("Initialise the workspace first", "Notification", "blue");
            return;
        }
    }

    $(".qed-sub-menu-item > div").removeClass("active-sub-menu-item");
    $(this).addClass("active-sub-menu-item");

    $(".main-content").hide();
    $("#" + $(this).data("ally") + "-div").show();
});

var isBuilt = false;

$("#build-workspace-btn").click(function() {
    var subjectGroupName = $("#subject-group-select").find(":selected").data("name");
    var subjectName = $("#subject-select").find(":selected").data("name");
    var schoolYearName = $("#school-year-select").find(":selected").data("name");

    $("#workspace-label").html("<b>Class " + schoolYearName + "th, " + subjectName + "</b>");
    $("#workspace-label").show();
    isBuilt = true;

    $("#metadata-mi").trigger("click");
});

var changeBuildBtnState = function changeBuildBtnState() {
    var subjectId = $("#subject-select").val();
    var schoolYearId = $("#school-year-select").val();

    if(subjectId > 0 && schoolYearId > 0 && subjectId != null && schoolYearId != null) {
        $("#build-workspace-btn").prop("disabled", false);
    }
    else {
        $("#build-workspace-btn").prop("disabled", true);
    }
};