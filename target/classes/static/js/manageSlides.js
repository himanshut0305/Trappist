$(document).ready(function() {
    initializePage({role: ["ROLE_SUPER_ADMIN", "ROLE_SUBJECT_EXPERT", "ROLE_CONTRIBUTOR"], onComplete: function() { }});
    onPageLoad(getSlide);
});

var data = {
    preface1 : "",
    explanation1 : "",
    preface2 : "",
    explanation2 : "",
    riQuestion : "",
    riAnswer : "",
    ciQuestion : "",
    diagramCaption : "",
    diagram : ""
}

var isEmptySlide = function isEmptySlide() {
    if(data.preface1 != null && data.preface1 != "") {
        return false;
    }
    else if(data.preface2 != null && data.preface2 != "") {
        return false;
    }
    else if(data.explanation1 != null && data.explanation1 != "") {
        return false;
    }
    else if(data.explanation2 != null && data.explanation2 != "") {
        return false;
    }
    else if(data.riQuestion != null && data.riQuestion != "") {
        return false;
    }
    else if(data.riAnswer != null && data.riAnswer != "") {
        return false;
    }
    else if(data.ciQuestion != null && data.ciQuestion != "") {
        return false;
    }
    else if(data.diagramCaption != null && data.diagramCaption != "") {
        return false;
    }
    else if(data.diagram != null && data.diagram != "") {
        return false;
    }

    return true;
}

var secondPartType = "";

var currentPreface = $("#preface-1");
var currentExplanation = $("#explanation-1");
var currentlyFocusedElement = "POINT1";

$("#submit-slide-btn").click(function() {
    if(isEmptySlide()) {
        notify("Slide cannot be blank", "Attention", "orange");
        stopWaiting("#submit-slide-btn");
        return;
    }

    var topicId = localStorage.getItem("currentTopicId");
    var currentSlideIndex = localStorage.getItem("currentSlideIndex");

    if(secondPartType !== "POINT") {
        data.preface2 = "";
        data.explanation2 = "";
    }

    if(secondPartType !== "RI") {
        data.riQuestion = "";
        data.riAnswer = "";
    }

    if(secondPartType !== "CI") {
        data.ciQuestion = "";
        data.ciAffirmativeAnswer = "";
        data.ciNegativeAnswer = "";
    }

    if(secondPartType !== "DIG") {
        data.diagramCaption = "";
        data.diagram = "";
    }

    var comment = $("#new-comment-input").val().trim();
    console.log("Comment : " + comment);
    if(comment == "" || comment == null) {
        comment = "No Comment";
    }

    data.comment = comment;
    data.topicId = topicId;
    data.slideIndex = currentSlideIndex;

    sendAuthorisedPostRequest({
        url: "/api/save/revisionSlide",
        data: data,
        onSuccess: function(response){
            console.log(response);
            stopWaiting("#submit-slide-btn");
            notify("Slide saved successfully", "Created", "blue");
            $("#add-question-btn").hide();
        },
        onError: function(error){
            console.log(error);
        }
    });
});

var getSlide = function getSlide() {
    var topicId = localStorage.getItem("currentTopicId");
    var currentSlideIndex = localStorage.getItem("currentSlideIndex");
    var requestData = { "topicId" : topicId, "slideIndex" : currentSlideIndex };

    sendAuthorisedPostRequest({
        url: "/api/get/revisionSlide",
        data: requestData,
        onSuccess: function(response){
            data = JSON.parse(response);
            localStorage.setItem("isLastSlide", data.lastSlide);

            console.log(data);

            if(data.numberOfSlides != null && data.numberOfSlides != 0) {
                for(var k = 1; k <= data.numberOfSlides; k++) {
                    $("#skip-to-slide").append("<option val='"+ k +"'>"+ k +"</option>")
                }
            }
            else {
                $("#skip-to-slide").hide();
            }

            if(data.slideType == "Revision") {
                focusFirstPoint();

                $("#preface-input").trigger("keyup");
                $("#explanation-input").trigger("keyup");

                var triggerExplanation = true;
                if(data.preface2 != null || data.explanation2 != null) {
                    $("#add-point-btn").trigger("click");
                    focusSecondPoint();
                }
                else if(data.riQuestion != null) {
                    $("#add-reveal-interaction-btn").trigger("click");
                    focusRevealInteraction();
                }
                else if(data.ciQuestion != null) {
                    $("#add-confirm-interaction-btn").trigger("click");
                    focusConfirmInteraction();
                    triggerExplanation = false;
                }
                else if(data.diagramCaption != null) {
                    $("#diagram-upload-btn").trigger("click");
                    $("#preview").attr("src", data.diagramURL);
                    focusDiagram();
                    triggerExplanation = false;
                }

                $("#preface-input").trigger("keyup");
                if(triggerExplanation)
                    $("#explanation-input").trigger("keyup");

                $("#previous-comment-input").html(data.comment);
                $("#creator-name").html(data.createdBy);
                $("#reviewer-name").html(data.reviewedBy);

                if(data.slideId == 0) {
                    $("#delete-slide-btn").attr("disabled", true);
                }
            }
            else if(data.slideType == "Question") {
                window.location.href = '/ManageQuestions.html';
            }

            if(!isEmptySlide()) {
                $("#add-question-btn").hide();
            }

            $(".loading-overlay-div").hide();
        },
        onError: function(error){
            console.log(error);
        }
    });
}

$("#add-point-btn").click(function() {
    focusSecondPoint();
});

$("#add-reveal-interaction-btn").click(function() {
    focusRevealInteraction();
});

$("#add-confirm-interaction-btn").click(function() {
    focusConfirmInteraction();
});

$('#diagram-upload-btn').click(function(){
    $('#diagram-input').trigger('click');
});

function previewFile(){
    var preview = document.getElementById("preview");
    var file    = document.getElementById("diagram-input").files[0];
    var reader  = new FileReader();

    reader.onloadend = function () {
       preview.src = reader.result;
       data.diagram = reader.result;
    }

    if (file) {
        reader.readAsDataURL(file);
        focusDiagram();
    }
    else {
        preview.src = "";
    }
}

$("#preface-input").keyup(function(event) {
    var str = $(this).val();
    currentPreface.html(str);

    if(currentlyFocusedElement === "POINT1") {
        data.preface1 = str;
    }
    else if(currentlyFocusedElement === "POINT2") {
        data.preface2 = str;
    }
    else if(currentlyFocusedElement === "RI") {
        data.riQuestion = str;
    }
    else if(currentlyFocusedElement === "CI") {
        data.ciQuestion = str;
    }
    else if(currentlyFocusedElement === "DIG") {
        data.diagramCaption = str;
    }
});

$("#explanation-input").keyup(function(event) {
	var str = $(this).val();
    
    if(currentlyFocusedElement === "POINT1") {
        data.explanation1 = str;
    }
    else if(currentlyFocusedElement === "POINT2") {
        data.explanation2 = str;
    }
    else if(currentlyFocusedElement === "RI") {
        data.riAnswer = str;
    }
    
	var results = [];
	$("<div></div>").html(str).find("formula").each(function(l) {
	    var h = katex.renderToString($(this).text(), { throwOnError: false });
	    str = str.replace($(this).text(), h);
	});

	currentExplanation.html(str);
});

$(".shortcut-btn").click(function() {
    var insertText = $(this).data("insert-text");
    var offset = $(this).data("offset");

	insertAtCaret("explanation-input", insertText, offset);
});

$("#explanation-input").keydown(function(event) {
    onShortcutClick(event, "explanation-input");
});

$("#add-question-btn").click(function() {
    if(isEmptySlide()) {
        window.location.href = '/ManageQuestions.html';
    }
    else {
        var r = confirm("All info entered on this page will be lost. Continue?");
        if (r == true) {
            window.location.href = '/ManageQuestions.html';
        }
    }
});

$(".add-btn").click(function() {
    $(this).hide();
    $(".add-btn").attr("disabled", true);
    $("#" + $(this).data("brother") + "-btn").show();
    $("#" + $(this).data("ally") + "-div").show();

    secondPartType = $(this).data("type");
    $("#preface-input").focus();
});

$(".remove-btn").click(function() {
    focusFirstPoint();

    $(this).hide();
    $(".add-btn").attr("disabled", false);
    $("#" + $(this).data("brother") + "-btn").show();
    $("#" + $(this).data("ally") + "-div").hide();

    secondPartType = "";
});

function focusFirstPoint() {
    currentlyFocusedElement = "POINT1";
    $("#explanation-input").prop('disabled', false);

    $("#preface-input").val(data.preface1);
    $("#explanation-input").val(data.explanation1);

    currentPreface = $("#preface-1");
    currentExplanation = $("#explanation-1");
    $("#preface-input").focus();
}

function focusSecondPoint() {
    currentlyFocusedElement = "POINT2";
    $("#explanation-input").prop('disabled', false);

    $("#preface-input").val(data.preface2);
    $("#explanation-input").val(data.explanation2);

    currentPreface = $("#preface-2");
    currentExplanation = $("#explanation-2");

    $("#preface-input").focus();
}

function focusRevealInteraction() {
    currentlyFocusedElement = "RI";
    $("#explanation-input").prop('disabled', false);

    $("#preface-input").val(data.riQuestion);
    $("#explanation-input").val(data.riAnswer);

    currentPreface = $("#reveal-interaction-question");
    currentExplanation = $("#reveal-interaction-answer");

    $("#preface-input").focus();
}

function focusConfirmInteraction() {
    currentlyFocusedElement = "CI";
    $("#explanation-input").prop('disabled', true);

    $("#preface-input").val(data.ciQuestion);
    $("#explanation-input").val("");

    currentPreface = $("#confirm-interaction-question");
    currentExplanation = null;

    $("#preface-input").focus();
}

function focusDiagram() {
    currentlyFocusedElement = "DIG";
    $("#explanation-input").prop('disabled', true);

    $("#preface-input").val(data.diagramCaption);
    $("#explanation-input").val("");

    currentPreface = $("#diagram-caption");
    currentExplanation = null;

    $("#preface-input").focus();
}