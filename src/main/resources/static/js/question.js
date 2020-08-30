$("#question-type-select").change(function() {
    var x = $(this).val();

    $("#explanation-input").removeAttr("disabled");

    switch(x) {
        case "MCQSingleAnswer" :
            $("#options-div").load("../templates/MCQSingleAnswer.html");
            break;

        case "MCQMultipleAnswer" :
            $("#options-div").load("../templates/MCQMultipleAnswer.html");
            break;

        case "TrueFalse" :
            $("#options-div").load("../templates/TrueFalse.html");
            break;

        case "OneWord" :
            $("#options-div").load("../templates/OneWord.html");
            break;

        case "MatchTheColumns" :
            break;

        case "Reorder" :
            break;

        default :
            $("#explanation-input").prop("disabled", "true");
    }
});

$(document).on("click", ".add-more-option-mcqsa", function() {
    $("<div>").load("../templates/MCQSingleAnswer.html", function() {
          $("#options-div").append($(this).html());
    });
});

$(document).on("click", ".add-more-option-mcqma", function() {
    $("<div>").load("../templates/MCQMultipleAnswer.html", function() {
          $("#options-div").append($(this).html());
    });
});