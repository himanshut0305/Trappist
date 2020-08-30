$(document).ready(function() {
    initializePage({role: ["ROLE_SUPER_ADMIN", "ROLE_SUBJECT_EXPERT", "ROLE_CONTRIBUTOR"], onComplete: function() { }});
    afterPageLoad(getQuestion);
    getTopicsByChapter();
});

var afterPageLoad = function onPageLoad(firstAction) {
    console.log("afterPageLoad");

    var topicId = localStorage.getItem("topicIdForQuestions");
    var topicName = localStorage.getItem("topicNameForQuestions");

    $("#topic-name").html(topicName);

    if(topicId === null) {
        window.location.href = '/lessons.html';
    }
    else {
        firstAction();
    }
}

var questionString = "";
var currentlyFocusedElement = $("#question-preview");
$("#rich-text-box").keyup(function(event) {
	var str = $(this).val();
    storeStr = str;

	var results = [];
	$("<div></div>").html(str).find("formula").each(function(l) {
	    var h = katex.renderToString($(this).text(), { throwOnError: false });
	    str = str.replace($(this).text(), h);
	});

	currentlyFocusedElement.html(str);

	if(currentlyFocusedElement.attr("id") === "question-preview") {
	    questionString = storeStr;
	}
	else {
        $("#option-preview-" + currentlyFocusedOptionId).attr("data-explanation", storeStr);
        if(storeStr != null && storeStr != "") {
            $("#explanation-indicator-" + currentlyFocusedOptionId).show();
        }
        else {
            $("#explanation-indicator-" + currentlyFocusedOptionId).hide();
        }
	}
});

var currentlyFocusedOption = null;
var currentlyFocusedOptionId = 0;

$("#option-input").keyup(function(event) {
	var str = $(this).val();
	currentlyFocusedOption.html(str);
});

var totalOptions = 0;
$("#add-option-btn").click(function() {
    var qt = $("#question-type-select").val();
    if(qt === "MCQ_True_False") {
        var numOfOptions = 0;
        $(".option-preview").each(function() {
            numOfOptions++;
        });

        if(numOfOptions > 1) {
            notify("True-False Questions cannot have more than 2 options", "Warning", "orange");
            $(this).val("MCQ_Multiple_Answer");
            return;
        }
    }

    totalOptions++;

    $(".option-preview-div").each(function() {
        $(this).removeClass("active");
    });

    $("#options-preview-div").append("<div id='option-preview-div-"+ totalOptions +"' data-option-index='"+ totalOptions +"' class='option-preview-div active'><span id='explanation-indicator-"+ totalOptions +"' class='explanation-indicator'>E</span><p id='option-preview-"+ totalOptions +"' class='option-preview' data-is-correct='false' data-explanation=''></p></div>");
    $("#option-input").prop('disabled', false);
    $("#option-input").val("");
    $("#option-input").focus();

    $("#hide-option-explanation-btn").hide();
    $("#add-option-explanation-btn").show();
    $(".explanation-preview-div").hide();

    $("#option-remove-btn").prop('disabled', false);
    $("#add-option-explanation-btn").prop('disabled', false);
    $("#mark-option-correct-btn").prop('disabled', false);

    currentlyFocusedOption = $("#option-preview-" + totalOptions);
    currentlyFocusedOptionId = totalOptions;
    currentlyFocusedElement = $("#question-preview");

    $("#rich-text-box").val(questionString);

    $("#device-screen").append("<div id='explanation-preview-div-"+ currentlyFocusedOptionId +"' class='explanation-preview-div'><p id='explanation-preview-"+ currentlyFocusedOptionId +"'></p></div>");
});

$(document).on("click", ".option-preview-div", function() {
    $(".option-preview-div").each(function() {
        $(this).removeClass("active");
    });

    $(this).addClass("active");

    currentlyFocusedOptionId = $(this).data("option-index");
    var focusedOptionValue = $("#option-preview-" + currentlyFocusedOptionId).text();
    $("#option-input").val(focusedOptionValue);
    $("#option-input").focus();
    currentlyFocusedOption = $("#option-preview-" + currentlyFocusedOptionId);

    $("#hide-option-explanation-btn").hide();
    $("#add-option-explanation-btn").show();
    $(".explanation-preview-div").hide();

    if($("#option-preview-div-"+currentlyFocusedOptionId).hasClass("correct")) {
        $("#mark-option-correct-btn").hide();
        $("#unmark-option-correct-btn").show();
    }
    else {
        $("#mark-option-correct-btn").show();
        $("#unmark-option-correct-btn").hide();
    }

    currentlyFocusedElement = $("#question-preview");
    $("#rich-text-box").val(questionString);
});

$("#question-preview").click(function() {
    $(".explanation-preview-div").hide();

    currentlyFocusedElement = $("#question-preview");
    $("#rich-text-box").val(questionString);
});

$("#rich-text-box").keydown(function(e) {
    onShortcutClick(e, "rich-text-box");
});

$("#add-option-explanation-btn").click(function() {
    $(this).hide();

    $("#hide-option-explanation-btn").show();
    $(".explanation-preview-div").hide();
    $("#explanation-preview-div-"+ currentlyFocusedOptionId).show();

    currentlyFocusedElement = $("#explanation-preview-div-"+ currentlyFocusedOptionId);

    var t = $("#option-preview-" + currentlyFocusedOptionId).attr("data-explanation");
    $("#rich-text-box").val(t);
    $("#rich-text-box").focus();
});

$("#hide-option-explanation-btn").click(function() {
    $(this).hide();
    $("#add-option-explanation-btn").show();
    $("#explanation-preview-div-"+ currentlyFocusedOptionId).hide();

    currentlyFocusedElement = $("#question-preview");
    $("#rich-text-box").val(questionString);
});

$("#mark-option-correct-btn").click(function() {
    $(this).hide();
    $("#unmark-option-correct-btn").show();

    $("#option-preview-div-" + currentlyFocusedOptionId).addClass("correct");
    $("#explanation-preview-div-"+ currentlyFocusedOptionId).addClass("correct");
    $("#option-preview-" + currentlyFocusedOptionId).attr("data-is-correct", "true");

    $("#add-option-explanation-btn").focus();
});

$("#unmark-option-correct-btn").click(function() {
    $(this).hide();
    $("#mark-option-correct-btn").show();

    $("#option-preview-div-" + currentlyFocusedOptionId).removeClass("correct");
    $("#explanation-preview-div-"+ currentlyFocusedOptionId).removeClass("correct");
    $("#option-preview-" + currentlyFocusedOptionId).attr("data-is-correct", "false");
});

$("#option-remove-btn").click(function() {
    $("#option-preview-div-" + currentlyFocusedOptionId).remove();
    $(".option-preview-div:first-child").addClass("active");
    $("#explanation-preview-div-" + currentlyFocusedOptionId).remove();

    currentlyFocusedOptionId = $(".option-preview-div:first-child").data("option-index");

    if(currentlyFocusedOptionId != null) {
        var focusedOptionValue = $("#option-preview-" + currentlyFocusedOptionId).text();
        $("#option-input").val(focusedOptionValue);
        $("#option-input").focus();
        currentlyFocusedOption = $("#option-preview-" + currentlyFocusedOptionId);

        if($("#option-preview-div-"+currentlyFocusedOptionId).hasClass("correct")) {
            $("#mark-option-correct-btn").hide();
            $("#unmark-option-correct-btn").show();
        }
        else {
            $("#mark-option-correct-btn").show();
            $("#unmark-option-correct-btn").hide();
        }
    }
    else {
        $("#option-input").val("");
        $("#option-input").prop('disabled', true);

        $("#hide-option-explanation-btn").hide();
        $("#add-option-explanation-btn").show();

        $("#mark-option-correct-btn").show();
        $("#unmark-option-correct-btn").hide();

        $("#option-remove-btn").prop('disabled', true);
        $("#add-option-explanation-btn").prop('disabled', true);
        $("#mark-option-correct-btn").prop('disabled', true);
    }

    $("#rich-text-box").val(questionString);
});

var getQuestion = function getQuestion() {
    var questionId = localStorage.getItem("currentQuestionId");

    if(questionId == null) {
        $("#delete-question-btn").hide();
        return;
    }

    sendAuthorisedRequest({
        url: "/api/get/questionById/" + questionId,
        onSuccess: function(response){
            response = JSON.parse(response);
            console.log(response);

            if(response.question != null) {
            $("#rich-text-box").val(response.question);
                $("#rich-text-box").focus();
                currentlyFocusedElement = $("#question-preview");
                $("#question-preview").html(response.question);
                questionString = response.question;

                var options = response.options;
                var numOfOptions = options.length;

                $("#question-type-select").val(response.questionType);
                $("#question-level-select").val(response.level);
                $("#question-category-select").val(response.category);

                if(response.diagram != null) {
                    $("#diagram-div").show();
                    $("#preview").attr("src", response.diagram.url);

                    toDataURL(response.diagram.url, function(base64Diagram) {
                        DIAGRAM = base64Diagram;
                    });

                    $("#add-diagram-btn").hide();
                    $("#remove-diagram-btn").show();
                }

                var i = 0;
                for(i = 0; i < numOfOptions; i++) {
                    totalOptions++;

                    $(".option-preview-div").each(function() {
                        $(this).removeClass("active");
                    });

                    $("#options-preview-div").append("<div id='option-preview-div-"+ totalOptions +"' data-option-index='"+ totalOptions +"' class='option-preview-div active'><span id='explanation-indicator-"+ totalOptions +"' class='explanation-indicator'>E</span><p id='option-preview-"+ totalOptions +"' class='option-preview' data-is-correct='"+ options[i].correct +"' data-explanation='"+ options[i].explanation +"'>"+ options[i].optionText +"</p></div>");
                    $("#option-input").prop('disabled', false);
                    $("#option-input").val(options[i].optionText);

                    if(options[i].correct) {
                        $("#option-preview-div-" + totalOptions).addClass("correct");
                    }

                    if(options[i].explanation != null) {
                        $("#explanation-indicator-" + totalOptions).show();
                    }

                    currentlyFocusedOption = $("#option-preview-" + totalOptions);
                    currentlyFocusedOptionId = totalOptions;

                    $("#device-screen").append("<div id='explanation-preview-div-"+ currentlyFocusedOptionId +"' class='explanation-preview-div'><p id='explanation-preview-"+ currentlyFocusedOptionId +"'>"+ options[i].explanation +"</p></div>");
                }

                if(i > 0) {
                    $(".option-action-btn").prop('disabled', false);
                }
            }
        },
        onError: function(error){
            console.log(error);
        }
    });
}

$("#submit-slide-btn").click(function() {
    var reqObject = {};
    var options = [];

    $(".option-preview").each(function() {
        var optionIndex = $(this).parent().data("option-index");
        var option = {}

        option.text = $(this).text().trim();
        option.isCorrect = this.getAttribute("data-is-correct");
        option.explanation = this.getAttribute("data-explanation").trim();

        options.push(option);
    });

    totalCorrectAnswers = 0
    for(var i = 0; i < options.length; i++) {
        if(options[i].text == null || options[i].text == "") {
            notify("Option "+ (i + 1) +" is blank. Options can not be blank.", "Warning", "orange");
            stopWaiting("#submit-slide-btn");
            return;
        }

        if(options[i].explanation == null || options[i].explanation == "") {
            notify("Option "+ (i + 1) +" doesn't have an explanation", "Warning", "orange");
            stopWaiting("#submit-slide-btn");
            return;
        }

        if(options[i].isCorrect == "true") {
            totalCorrectAnswers++;
        }
    }

    reqObject.topicId = localStorage.getItem("topicIdForQuestions");

    if(questionString.trim() == null || questionString.trim() == "") {
        notify("Question cannot be blank", "Warning", "orange");
        stopWaiting("#submit-slide-btn");
        return;
    }

    reqObject.question = questionString;
    reqObject.questionType = $("#question-type-select").val();

    if(reqObject.questionType != "One_Word_Answer") {
        if(options.length < 2) {
            notify("There should at least be 2 options for this Type of Question", "Warning", "orange");
            stopWaiting("#submit-slide-btn");
            return;
        }

        if(reqObject.questionType === "MCQ_Multiple_Answer") {
            if(totalCorrectAnswers === 0) {
                notify("There should at least be 1 correct option", "Warning", "orange");
                stopWaiting("#submit-slide-btn");
                return;
            }
        }
        else if(reqObject.questionType === "MCQ_Single_Answer") {
            if(totalCorrectAnswers == 0) {
                notify("There should at least be 1 correct option", "Warning", "orange");
                stopWaiting("#submit-slide-btn");
                return;
            }
            else if(totalCorrectAnswers > 1) {
                notify("There can only be 1 correct option", "Warning", "orange");
                stopWaiting("#submit-slide-btn");
                return;
            }
        }
        else if(reqObject.questionType === "MCQ_True_False") {
            if(totalCorrectAnswers != 1) {
                notify("There can only be 1 correct option", "Warning", "orange");
                stopWaiting("#submit-slide-btn");
                return;
            }
        }
    }

    if(options.length == 0) {
        notify("There should at least be one option", "Warning", "orange");
        stopWaiting("#submit-slide-btn");
        return;
    }

    reqObject.option = options;
    reqObject.diagram = DIAGRAM;
    reqObject.comment = $("#new-comment-input").val();

    reqObject.instruction = $("#instruction-preview").html();
    reqObject.isInteractive = enableAllSelect;
    reqObject.level = $("#question-level-select").val();
    reqObject.category = $("#question-category-select").val();

    reqObject.currentQuestionId = localStorage.getItem("currentQuestionId");

    console.log(reqObject);

    sendAuthorisedPostRequest({
        url: "/api/save/question",
        data: reqObject,
        onSuccess: function(response){
            console.log(response);

            localStorage.setItem("currentQuestionId", response);
            $("#delete-question-btn").show();

            notify("Question saved successfully");
            stopWaiting("#submit-slide-btn");
        },
        onError: function(error){
            console.log(error);
            stopWaiting("#submit-slide-btn");
        }
    });
});

$('#add-diagram-btn').click(function(){
    $('#diagram-input').trigger('click');
});

var DIAGRAM = null;
function previewFile(){
    var preview = document.getElementById("preview");
    var file    = document.getElementById("diagram-input").files[0];
    var reader  = new FileReader();

    reader.onloadend = function () {
       preview.src = reader.result;
       DIAGRAM = reader.result;
    }

    if (file) {
        $("#diagram-div").show();
        reader.readAsDataURL(file);

        $("#add-diagram-btn").hide();
        $("#remove-diagram-btn").show();
    }
    else {
        preview.src = "";
    }
}

$("#question-type-select").change(function() {
    var qt = $("#question-type-select").val();

    if(qt === "MCQ_Multiple_Answer") {
        $("#instruction-preview").html("Select all the correct options");
    }
    else if(qt === "MCQ_Single_Answer") {
        $("#instruction-preview").html("Select the correct option");
    }
    else if(qt === "MCQ_True_False") {
        $("#instruction-preview").html("Select the correct option");

        var numOfOptions = 0;
        $(".option-preview").each(function() {
            numOfOptions++;
        });

        if(numOfOptions > 2) {
            notify("True-False Questions cannot have more than 2 options", "Warning", "orange");
            $(this).val("MCQ_Multiple_Answer");
        }
    }
    else if(qt === "One_Word_Answer") {
        $("#instruction-preview").html("Answer in one word");
    }
    else {
        $("#instruction-preview").html("Select the correct option");
    }
});

var enableAllSelect = false;
$("#enable-all-select").click(function(){
    $(this).hide();
    $("#disable-all-select").show();
    enableAllSelect = true;
});

$("#disable-all-select").click(function(){
    $(this).hide();
    $("#enable-all-select").show();
    enableAllSelect = false;
});

$("#check-similar-questions-btn").click(function(){
    if(questionString.trim() == null || questionString.trim() == "") {
        notify("Question cannot be blank", "Warning", "orange");
        return;
    }
});

$("#get-all-questions-btn").on("click", function() {
    var topicId = localStorage.getItem("topicIdForQuestions");
    sendAuthorisedRequest({
        url: "/api/get/questions/" + topicId,
        onSuccess: function(response){
            var questionList = JSON.parse(response);
            console.log(questionList);

            $("#questions-list").html("");
            for(var i = 0; i < questionList.length; i++) {
                $("#questions-list").append("<div class='ques-div' data-id='"+ questionList[i].id +"'>" + (i+1) + ". " + questionList[i].question + "</div>");
            }

            $("#show-questions-overlay-div").show();
            $("#regular-body").hide();
        },
        onError: function(error){
            console.log(error);
            stopWaiting("#submit-slide-btn");
        }
    });
});

$("#close-questions-list-btn").on("click", function() {
    $("#show-questions-overlay-div").hide();
    $("#regular-body").show();
});

$(document).on("click", ".ques-div", function() {
    var questionId = $(this).data("id");
    localStorage.setItem("currentQuestionId", questionId);
    window.location.href = '/addQuestions.html';
});

$("#insert-new-question-btn").on("click", function() {
    localStorage.removeItem("currentQuestionId");
    window.location.href = '/addQuestions.html';
});

$("#delete-question-btn").on("click", function() {
    var r = confirm("Delete this Question?");
    if (r == true) {
        var questionId = localStorage.getItem("currentQuestionId");
        sendAuthorisedRequest({
            url: "/api/delete/question/" + questionId,
            onSuccess: function(response){
                console.log(response);
                localStorage.removeItem("currentQuestionId");
                window.location.href = '/addQuestions.html';
            },
            onError: function(error){
                console.log(error);
                stopWaiting("#delete-slide-btn");
            }
        });
    }
});

$(".shortcut-btn").click(function() {
    var insertText = $(this).data("insert-text");
    var offset = $(this).data("offset");

	insertAtCaret("rich-text-box", insertText, offset);
});

function toDataURL(url, callback) {
    var xhr = new XMLHttpRequest();

    xhr.onload = function() {
        var reader = new FileReader();
        reader.onloadend = function() {
            callback(reader.result);
        }

        reader.readAsDataURL(xhr.response);
    };

    xhr.open('GET', url);
    xhr.responseType = 'blob';
    xhr.send();
}

$("#remove-diagram-btn").click(function() {
    $(this).hide();
    $("#add-diagram-btn").show();
    DIAGRAM = null;

    $("#diagram-div").hide();
    var preview = document.getElementById("preview");
    preview.src = "";
});

var getTopicsByChapter = function getTopicsByChapter() {
    var chapterId = localStorage.getItem("selectedChapterIdForQuestions");
    var data = { chapterId : chapterId };

    sendAuthorisedPostRequest({
        url: "/api/get/topicsByChapter",
        data: data,
        onSuccess: function(response){
            topicList = JSON.parse(response);
            console.log(topicList);

            $("#topic-select").html("");
            $("#topic-select").append("<option disabled selected value='0'>Select Topic</option>")
            for(var i = 0; i < topicList.length; i++) {
                $("#topic-select").append("<option data-name='" + topicList[i].name + "' value='" + topicList[i].id + "'>" + topicList[i].name + "</option>")
            }


            var topId = localStorage.getItem("topicIdForQuestions");
            $("#topic-select").val(topId);
        },
        onError: function(error){
            var errorMsg = (JSON.parse(error.response)).message;
            notify(errorMsg, "Error", "red");
        }
    });
}

$("#topic-select").change(function() {
    var topicId = $("#topic-select").val();
    var topicName = $("#topic-select option:selected").text();
    localStorage.setItem("topicIdForQuestions", topicId);
    localStorage.setItem("topicNameForQuestions", topicName);

    localStorage.removeItem("currentQuestionId");

    window.location.href = '/addQuestions.html';
});
