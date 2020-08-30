$(document).ready(function() {
    initializePage({role: ["ROLE_SUPER_ADMIN", "ROLE_SUBJECT_EXPERT", "ROLE_CONTRIBUTOR"], onComplete: function() { }});
    onPageLoad(getSlide);
});
//
//
//
//
//
//



var data = {
    preface1Internal : "",
    explanation1Internal : "",
    preface2Internal : "",
    explanation2Internal : "",
    revealInteractionQuestionInternal : "",
    revealInteractionAnswerInternal : "",
    confirmInteractionQuestionInternal : "",
    diagramCaptionInternal : "",

    preface1Listener : function(val) {},
    explanation1Listener : function(val) {},
    preface2Listener : function(val) {},
    explanation2Listener : function(val) {},
    revealInteractionQuestionListener : function(val) {},
    revealInteractionAnswerListener : function(val) {},
    confirmInteractionQuestionListener : function(val) {},
    diagramCaptionListener : function(val) {},

    set preface1(val) {
        this.preface1Internal = val;
        this.preface1Listener(val);
    },

    set explanation1(val) {
        this.explanation1Internal = val;
        this.explanation1Listener(val);
    },

    set preface2(val) {
        this.preface2Internal = val;
        this.preface2Listener(val);
    },

    set explanation2(val) {
        this.explanation2Internal = val;
        this.explanation2Listener(val);
    },

    set revealInteractionQuestion(val) {
        this.revealInteractionQuestionInternal = val;
        this.revealInteractionQuestionListener(val);
    },

    set revealInteractionAnswer(val) {
        this.revealInteractionAnswerInternal = val;
        this.revealInteractionAnswerListener(val);
    },

    set revealInteractionQuestion(val) {
        this.revealInteractionQuestionInternal = val;
        this.revealInteractionQuestionListener(val);
    },

    set confirmInteractionQuestion(val) {
        this.confirmInteractionQuestionInternal = val;
        this.confirmInteractionQuestionListener(val);
    },

    set diagramCaption(val) {
        this.diagramCaptionInternal = val;
        this.diagramCaptionListener(val);
    },

    get preface1() {
        return this.preface1Internal;
    },

    get explanation1() {
        return this.explanation1Internal;
    },

    get preface2() {
        return this.preface2Internal;
    },

    get explanation2() {
        return this.explanation2Internal;
    },

    get revealInteractionQuestion() {
        return this.revealInteractionQuestionInternal;
    },

    get revealInteractionAnswer() {
        return this.revealInteractionAnswerInternal;
    },

    get revealInteractionQuestion() {
        return this.revealInteractionQuestionInternal;
    },

    get confirmInteractionQuestion() {
        return this.confirmInteractionQuestionInternal;
    },

    get diagramCaption() {
        return this.diagramCaptionInternal;
    },

    registerPreface1Listener: function(listener) {
        this.preface1Listener = listener;
    },

    registerExplanation1Listener: function(listener) {
        this.explanation1Listener = listener;
    },

    registerPreface2Listener: function(listener) {
        this.preface2Listener = listener;
    },

    registerExplanation2Listener: function(listener) {
        this.explanation2Listener = listener;
    },

    registerRIQuestionListener: function(listener) {
        this.revealInteractionQuestionListener = listener;
    },

    registerRIAnswerListener: function(listener) {
        this.revealInteractionAnswerListener = listener;
    },

    registerCIQuestionListener: function(listener) {
        this.confirmInteractionQuestionListener = listener;
    },

    registerDiagramCaptionListener: function(listener) {
        this.diagramCaptionListener = listener;
    }
}

data.registerPreface1Listener(function(val) {
    console.log("Preface 1 " + val);
});

data.registerExplanation1Listener(function(val) {
    console.log("Explanation 1 " + val);
});

data.registerPreface2Listener(function(val) {
    console.log("Preface 2 " + val);
});

data.registerExplanation2Listener(function(val) {
    console.log("Explanation 2 " + val);
});

data.registerRIQuestionListener(function(val) {
    console.log("RIQ " + val);
});

data.registerRIAnswerListener(function(val) {
    console.log("RIA " + val);
});

data.registerCIQuestionListener(function(val) {
    console.log("CIQ " + val);
});

data.registerDiagramCaptionListener(function(val) {
    console.log("DC " + val);
});



//var data = {
//    "preface1" : "",
//    "explanation1" : "",
//    "preface2" : "",
//    "explanation2" : "",
//    "revealInteractionQuestion" : "",
//    "revealInteractionAnswer" : "",
//    "confirmInteractionQuestion" : "",
//    "ciAffirmativeAnswer" : "Yes",
//    "ciNegativeAnswer" : "No",
//    "diagramCaption" : ""
//}

var secondPartType = "";

var currentPreface = $("#preface-1");
var currentExplanation = $("#explanation-1");
var currentlyFocusedElement = "POINT1";

var DIAGRAM = null;
var FILE = null;

$("#submit-slide-btn").click(function() {
    var topicId = localStorage.getItem("currentTopicId");
    var currentSlideIndex = localStorage.getItem("currentSlideIndex");

    if(secondPartType !== "POINT") {
        data.preface2 = "";
        data.explanation2 = "";
    }

    if(secondPartType !== "RI") {
        data.revealInteractionQuestion = "";
        data.revealInteractionAnswer = "";
    }

    if(secondPartType !== "CI") {
        data.confirmInteractionQuestion = "";
        data.ciAffirmativeAnswer = "";
        data.ciNegativeAnswer = "";
    }

    if(secondPartType !== "DIG") {
        data.diagramCaption = "";
    }

    data.topicId = topicId;
    data.slideIndex = currentSlideIndex;

    console.log(data);

    sendAuthorisedPostRequest({
        url: "/api/save/revisionSlide",
        data: data,
        onSuccess: function(response){
            console.log(response);
            var currentSlideIndex = localStorage.getItem("currentSlideIndex");
            currentSlideIndex++;
            localStorage.setItem("currentSlideIndex", currentSlideIndex);

            if(DIAGRAM != null) {
               var form = new FormData();
               form.append("file", FILE);

               $.ajax({
                   type: 'POST',
                   contentType: "multipart/form-data",
                   url: '/api/save/diagram',
                   "processData": false,
                   "contentType": false,
                   "mimeType": "multipart/form-data",
                   "data": form,
                   success: function(response) {
                       console.log(response);
                   },
                   error: function(xhr, exception) {
                       onError(xhr, exception);
                   }
               });
            }
            else {
                window.location.href = '/ManageSlides.html';
            }
        },
        onError: function(error){
            console.log(error);
        }
    });
});

var getSlide = function getSlide() {
    var topicId = localStorage.getItem("currentTopicId");
    var currentSlideIndex = localStorage.getItem("currentSlideIndex");

    var data = { "topicId" : topicId, "slideIndex" : currentSlideIndex };

    sendAuthorisedPostRequest({
        url: "/api/get/revisionSlide",
        data: data,
        onSuccess: function(response){
            response = JSON.parse(response);

            $("#preface-1").html(response.preface1);
            $("#explanation-1").html(response.explanation1);
            $("#preface-2").html(response.preface2);
            $("#explanation-2").html(response.explanation2);
            $("#reveal-interaction-question").html(response.riQuestion);
            $("#reveal-interaction-answer").html(response.riAnswer);
            $("#confirm-interaction-question").html(response.ciQuestion);
            $("#diagram-caption").html(response.caption);

            data.preface1 = response.preface1;
            data.explanation1 = response.explanation1;
            data.preface2 = response.preface2;
            data.explanation2 = response.explanation2;
            data.revealInteractionQuestion = response.riQuestion;
            data.revealInteractionAnswer = response.riAnswer;
            data.confirmInteractionQuestion = response.ciQuestion;
            data.ciAffirmativeAnswer = response.ciAffirmativeAnswer;
            data.ciNegativeAnswer = response.ciNegativeAnswer;
            data.diagramCaption = response.caption;

            currentlyFocusedElement = "POINT1";

            $("#preface-input").val(data.preface1);
            $("#explanation-input").val(data.explanation1);

            currentPreface = $("#preface-1");
            currentExplanation = $("#explanation-1");

            if(response.preface2 != null || response.explanation2 != null) {
                $("#preface-2-div").css("display", "block");
                $("#explanation-2-div").css("display", "block");

                $("#add-point-btn").css("display", "none");
                $("#remove-point-btn").css("display", "inline-block");

                $("#diagram-upload-btn").prop('disabled', true);
                $("#add-reveal-interaction-btn").prop('disabled', true);
                $("#add-confirm-interaction-btn").prop('disabled', true);
            }
            else if(response.riQuestion != null || response.riAnswer != null) {
                $("#reveal-interaction-question-div").css("display", "block");
                $("#reveal-interaction-answer-div").css("display", "block");

                $("#add-reveal-interaction-btn").css("display", "none");
                $("#remove-reveal-interaction-btn").css("display", "inline-block");

                $("#diagram-upload-btn").prop('disabled', true);
                $("#add-point-btn").prop('disabled', true);
                $("#add-confirm-interaction-btn").prop('disabled', true);
            }
            else if(response.ciQuestion != null) {
                $("#confirm-interaction-question-div").css("display", "block");
                $("#confirm-interaction-answer-div").css("display", "block");

                $("#add-confirm-interaction-btn").css("display", "none");
                $("#remove-confirm-interaction-btn").css("display", "inline-block");

                $("#diagram-upload-btn").prop('disabled', true);
                $("#add-point-btn").prop('disabled', true);
                $("#add-reveal-interaction-btn").prop('disabled', true);
            }
            else if(response.diagramCaption != null) {
                $("#preview-div").css("display", "block");
                $("#diagram-caption-div").css("display", "block");

                $("#diagram-upload-btn").css("display", "none");
                $("#diagram-remove-btn").css("display", "inline-block");

                $("#add-point-btn").prop('disabled', true);
                $("#add-reveal-interaction-btn").prop('disabled', true);
                $("#add-confirm-interaction-btn").prop('disabled', true);
            }

            console.log(data);
        },
        onError: function(error){
            console.log(error);
        }
    });
}

$("#preface-1-div").click(function() {
    currentlyFocusedElement = "POINT1";
    $("#explanation-input").prop('disabled', false);

    $("#preface-input").val(data.preface1);
    $("#explanation-input").val(data.explanation1);

    currentPreface = $("#preface-1");
    currentExplanation = $("#explanation-1");
});

$("#explanation-1-div").click(function() {
    currentlyFocusedElement = "POINT1";
    $("#explanation-input").prop('disabled', false);

    $("#preface-input").val(data.preface1);
    $("#explanation-input").val(data.explanation1);

    currentPreface = $("#preface-1");
    currentExplanation = $("#explanation-1");
});

$("#preface-2-div").click(function() {
    currentlyFocusedElement = "POINT2";
    $("#explanation-input").prop('disabled', false);

    $("#preface-input").val(data.preface2);
    $("#explanation-input").val(data.explanation2);

    currentPreface = $("#preface-2");
    currentExplanation = $("#explanation-2");
});

$("#explanation-2-div").click(function() {
    currentlyFocusedElement = "POINT2";
    $("#explanation-input").prop('disabled', false);

    $("#preface-input").val(data.preface2);
    $("#explanation-input").val(data.explanation2);

    currentPreface = $("#preface-2");
    currentExplanation = $("#explanation-2");
});

$("#reveal-interaction-answer-div").click(function() {
    currentlyFocusedElement = "RI";
    $("#explanation-input").prop('disabled', false);

    $("#preface-input").val(data.revealInteractionQuestion);
    $("#explanation-input").val(data.revealInteractionQuestion);

    currentPreface = $("#reveal-interaction-question");
    currentExplanation = $("#reveal-interaction-answer");
});

$("#reveal-interaction-question-div").click(function() {
    currentlyFocusedElement = "RI";
    $("#explanation-input").prop('disabled', false);

    $("#preface-input").val(data.revealInteractionQuestion);
    $("#explanation-input").val(data.revealInteractionQuestion);

    currentPreface = $("#reveal-interaction-question");
    currentExplanation = $("#reveal-interaction-answer");
});

$("#confirm-interaction-answer-div").click(function() {
    currentlyFocusedElement = "CI";
    $("#explanation-input").prop('disabled', true);

    $("#preface-input").val(data.confirmInteractionQuestion);
    $("#explanation-input").val("");

    currentPreface = $("#confirm-interaction-question");
    currentExplanation = null;
});

$("#confirm-interaction-question-div").click(function() {
    currentlyFocusedElement = "CI";
    $("#explanation-input").prop('disabled', true);

    $("#preface-input").val(data.confirmInteractionQuestion);
    $("#explanation-input").val("");

    currentPreface = $("#confirm-interaction-question");
    currentExplanation = null;
});

$("#preview-div").click(function() {
    currentlyFocusedElement = "DIG";
    $("#explanation-input").prop('disabled', true);

    $("#preface-input").val(data.diagramCaption);
    $("#explanation-input").val("");

    currentPreface = $("#diagram-caption");
    currentExplanation = null;
});

$("#diagram-caption-div").click(function() {
    currentlyFocusedElement = "DIG";
    $("#explanation-input").prop('disabled', true);

    $("#preface-input").val(data.diagramCaption);
    $("#explanation-input").val("");

    currentPreface = $("#diagram-caption");
    currentExplanation = null;
});

$("#add-point-btn").click(function() {
    currentlyFocusedElement = "POINT2";

    $("#preface-2-div").css("display", "block");
    $("#preface-2").html(data.preface2);
    $("#preface-input").val(data.preface2);

    $("#explanation-2-div").css("display", "block");
    $("#explanation-2").html(data.explanation2);
    $("#explanation-input").val(data.explanation2);

    $("#preface-input").focus();

    $(this).css("display", "none");
    $("#remove-point-btn").css("display", "inline-block");

    currentPreface = $("#preface-2");
    currentExplanation = $("#explanation-2");

    $("#diagram-upload-btn").prop('disabled', true);
    $("#add-reveal-interaction-btn").prop('disabled', true);
    $("#add-confirm-interaction-btn").prop('disabled', true);

    secondPartType = "POINT";
});

$("#remove-point-btn").click(function() {
    currentlyFocusedElement = "POINT1";

    $("#preface-2-div").css("display", "none");
    $("#explanation-2-div").css("display", "none");

    $(this).css("display", "none");
    $("#add-point-btn").css("display", "inline-block");

    currentPreface = $("#preface-1");
    currentExplanation = $("#explanation-1");

    $("#preface-input").val(data.preface1);
    $("#explanation-input").val(data.explanation1);
    $("#preface-input").focus();

    $("#diagram-upload-btn").prop('disabled', false);
    $("#add-reveal-interaction-btn").prop('disabled', false);
    $("#add-confirm-interaction-btn").prop('disabled', false);

    secondPartType = "";
});

$("#add-reveal-interaction-btn").click(function() {
    currentlyFocusedElement = "RI";

    $("#reveal-interaction-question-div").css("display", "block");
    $("#reveal-interaction-question").html(data.revealInteractionQuestion);
    $("#preface-input").val(data.revealInteractionQuestion);

    $("#reveal-interaction-answer-div").css("display", "block");
    $("#reveal-interaction-answer").html(data.revealInteractionAnswer);
    $("#explanation-input").val(data.revealInteractionAnswer);

    $(this).css("display", "none");
    $("#remove-reveal-interaction-btn").css("display", "inline-block");

    currentPreface = $("#reveal-interaction-question");
    currentExplanation = $("#reveal-interaction-answer");

    $("#preface-input").focus();

    $("#add-point-btn").prop('disabled', true);
    $("#diagram-upload-btn").prop('disabled', true);
    $("#add-confirm-interaction-btn").prop('disabled', true);

    secondPartType = "RI";
});

$("#remove-reveal-interaction-btn").click(function() {
    currentlyFocusedElement = "POINT1";

    $("#reveal-interaction-question-div").css("display", "none");
    $("#reveal-interaction-answer-div").css("display", "none");

    $(this).css("display", "none");
    $("#add-reveal-interaction-btn").css("display", "inline-block");

    currentPreface = $("#preface-1");
    currentExplanation = $("#explanation-1");

    $("#preface-input").val(data.preface1);
    $("#explanation-input").val(data.explanation1);
    $("#preface-input").focus();

    $("#add-point-btn").prop('disabled', false);
    $("#diagram-upload-btn").prop('disabled', false);
    $("#add-confirm-interaction-btn").prop('disabled', false);

    secondPartType = "";
});

$("#add-confirm-interaction-btn").click(function() {
    currentlyFocusedElement = "CI";

    $("#confirm-interaction-question-div").css("display", "block");
    $("#confirm-interaction-answer-div").css("display", "block");

    $("#confirm-interaction-question").html(data.confirmInteractionQuestion);

    $(this).css("display", "none");
    $("#remove-confirm-interaction-btn").css("display", "inline-block");

    currentPreface = $("#confirm-interaction-question");
    currentExplanation = null;

    $("#preface-input").val(data.confirmInteractionQuestion);
    $("#explanation-input").val("");
    $("#preface-input").focus();

    $("#add-point-btn").prop('disabled', true);
    $("#diagram-upload-btn").prop('disabled', true);
    $("#add-reveal-interaction-btn").prop('disabled', true);

    $("#explanation-input").prop('disabled', true);

    secondPartType = "CI";
});

$("#remove-confirm-interaction-btn").click(function() {
    currentlyFocusedElement = "POINT1";

    $("#confirm-interaction-question-div").css("display", "none");
    $("#confirm-interaction-answer-div").css("display", "none");

    $(this).css("display", "none");
    $("#add-confirm-interaction-btn").css("display", "inline-block");

    currentPreface = $("#preface-1");
    currentExplanation = $("#explanation-1");

    $("#preface-input").val(data.preface1);
    $("#explanation-input").val(data.explanation1);
    $("#preface-input").focus();

    $("#add-point-btn").prop('disabled', false);
    $("#diagram-upload-btn").prop('disabled', false);
    $("#add-reveal-interaction-btn").prop('disabled', false);

    $("#explanation-input").prop('disabled', false);

    secondPartType = "";
});

$('#diagram-upload-btn').click(function(){
    $('#diagram-input').trigger('click');
});

$('#diagram-remove-btn').click(function(){
    currentlyFocusedElement = "POINT1";

    $("#diagram-input").val("");
    $("#preview").prop("src", "");

    $("#diagram-upload-btn").show();
    $("#diagram-remove-btn").hide();

    $("#add-point-btn").prop("disabled", false);
    $("#add-reveal-interaction-btn").prop('disabled', false);
    $("#add-confirm-interaction-btn").prop('disabled', false);

    $("#explanation-input").prop('disabled', false);

    secondPartType = "";
});

function previewFile(){
    var preview = document.getElementById("preview");
    var file    = document.getElementById("diagram-input").files[0];
    var reader  = new FileReader();

    reader.onloadend = function () {
       preview.src = reader.result;

        DIAGRAM = reader.result;
    }

    if (file) {
        FILE = file;
        console.log(file);

        reader.readAsDataURL(file);
        currentlyFocusedElement = "DIG";

        $("#diagram-upload-btn").hide();
        $("#diagram-remove-btn").show();

        $("#add-point-btn").prop("disabled", true);
        $("#add-reveal-interaction-btn").prop('disabled', true);
        $("#add-confirm-interaction-btn").prop('disabled', true);

        $("#explanation-input").prop('disabled', true);

        $("#diagram-caption-div").css("display", "block");
        $("#diagram-caption").html(data.diagramCaption);

        currentPreface = $("#diagram-caption");

        $("#preface-input").val(data.diagramCaption);
        $("#explanation-input").val("");
        $("#preface-input").focus();

        secondPartType = "DIG";
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
        data.revealInteractionQuestion = str;
    }
    else if(currentlyFocusedElement === "CI") {
        data.confirmInteractionQuestion = str;
    }
    else if(currentlyFocusedElement === "DIG") {
        data.diagramCaption = str;
    }
});

$("#explanation-input").keyup(function(event) {
	var str = $(this).val();
    var storeStr = str;

	var results = [];
	$("<div></div>").html(str).find("formula").each(function(l) {
	    var h = katex.renderToString($(this).text(), { throwOnError: false });
	    str = str.replace($(this).text(), h);
	});

	currentExplanation.html(str);

    if(currentlyFocusedElement === "POINT1") {
        data.explanation1 = storeStr;
    }
    else if(currentlyFocusedElement === "POINT2") {
        data.explanation2 = storeStr;
    }
    else if(currentlyFocusedElement === "RI") {
        data.revealInteractionAnswer = storeStr;
    }
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
    var txt;
    var r = confirm("All info entered on this page will be lost. Continue?");
    if (r == true) {
        window.location.href = '/ManageQuestions.html';
    }
});

function notify(msg) {
    console.log("Notifying");

    $("#notification").html(msg);
    $("#notification").show();

    setTimeout(function() {
        $("#notification").hide();
    }, 3000);
}