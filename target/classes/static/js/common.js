var onPageLoad = function onPageLoad(firstAction) {
    var topicId = localStorage.getItem("currentTopicId");
    var topicName = localStorage.getItem("currentTopicName");
    var currentSlideIndex = localStorage.getItem("currentSlideIndex");

    $("#topic-name").html(topicName);
    $("#slide-index").html(currentSlideIndex);

    if(topicId === null) {
        window.location.href = '/lessons.html';
    }
    else if (currentSlideIndex === null) {
        window.location.href = '/lessons.html';
    }
    else {
        firstAction();
    }
}

function setCaretPosition(elemId, caretPos) {
    var elem = document.getElementById(elemId);

    if(elem != null) {
        if(elem.createTextRange) {
            var range = elem.createTextRange();
            range.move('character', caretPos);
            range.select();
        }
        else {
  			var len = $("#" + elemId).val().length;

            if(elem.selectionStart) {
                elem.focus();
                elem.setSelectionRange(len - caretPos, len - caretPos);
            }
            else
                elem.focus();
        }
    }
}

function insertAtCaret(areaId, text, finalPosOffset) {
    var txtarea = document.getElementById(areaId);
    if (!txtarea) {
        return;
    }

    var scrollPos = txtarea.scrollTop;
    var strPos = 0;
    var br = ((txtarea.selectionStart || txtarea.selectionStart == '0') ? "ff" : (document.selection ? "ie" : false));
    if (br == "ie") {
        txtarea.focus();
        var range = document.selection.createRange();
        range.moveStart('character', -txtarea.value.length);
        strPos = range.text.length;
    }
    else if (br == "ff") {
        strPos = txtarea.selectionStart;
    }

    var front = (txtarea.value).substring(0, strPos);
    var back = (txtarea.value).substring(strPos, txtarea.value.length);
    txtarea.value = front + text + back;
    strPos = strPos + text.length;

    if (br == "ie") {
        txtarea.focus();
        var ieRange = document.selection.createRange();
        ieRange.moveStart('character', -txtarea.value.length);
        ieRange.moveStart('character', strPos);
        ieRange.moveEnd('character', 0);
        ieRange.select();
    }
    else if (br == "ff") {
        txtarea.selectionStart = strPos;
        txtarea.selectionEnd = strPos;
        txtarea.focus();
    }

    txtarea.scrollTop = scrollPos;
    txtarea.setSelectionRange(txtarea.selectionStart - finalPosOffset, txtarea.selectionStart - finalPosOffset);
    txtarea.focus();
}

var onShortcutClick = function onShortcutClick(e, inputId) {
    if(e.key == "f" && e.ctrlKey) {
        e.preventDefault();
        insertAtCaret(inputId, "<formula></formula>", 10);
    }

    if(e.key == "b" && e.ctrlKey) {
        e.preventDefault();
        insertAtCaret(inputId, "<b></b>", 4);
    }

    if(e.key == "n" && e.ctrlKey && e.altKey) {
        e.preventDefault();
        insertAtCaret(inputId, "</br>", 0);
    }

    if(e.key == "p" && e.ctrlKey) {
        e.stopPropagation();
        e.preventDefault();
        insertAtCaret(inputId, "\\pi", 0);
    }

    if(e.key == "e" && e.ctrlKey) {
        e.stopPropagation();
        e.preventDefault();
        insertAtCaret(inputId, "\\degree", 0);
    }

    if(e.key == "P" && e.ctrlKey && e.shiftKey) {
        e.preventDefault();
        insertAtCaret(inputId, "\\Pi", 0);
    }

    if(e.key == "h" && e.ctrlKey) {
        e.preventDefault();
        insertAtCaret(inputId, "<font color='#EE0000'></font>", 7);
    }

    if(e.key == "d" && e.ctrlKey) {
        e.preventDefault();
        insertAtCaret(inputId, "\\cfrac{d}{dx} (x)", 0);
    }

    if(e.key == "i" && e.ctrlKey) {
        e.preventDefault();
        insertAtCaret(inputId, "\\int_{n}^{2n} (x)", 0);
    }

    if(e.key == "t" && e.ctrlKey) {
        e.preventDefault();
        insertAtCaret(inputId, "\\theta", 0);
    }

    if(e.key == "Insert" && e.ctrlKey) {
        e.preventDefault();
        var quotes = [
            "We build our computer (systems) the way we build our cities: over time, without a plan, on top of ruins. ",
            "Software being 'Done' is like lawn being 'Mowed'. ",
            "If I do a job in 30 minutes it’s because I spent 10 years learning how to do that in 30 minutes. ",
            "Always code as if the guy who ends up maintaining your code will be a violent psychopath who knows where you live. ",
            "Truth can only be found in one place: the code. ",
            "Walking on water and developing software from a specification are easy if both are frozen. ",
            "Perl – The only language that looks the same before and after RSA encryption. ",
            "Frameworks pass; language remains. ",
            "Debugging is like an onion. There are multiple layers to it, and the more you peel them back, the more likely you're going to start crying at inappropriate times. ",
            "In programming you don't understand things. You just get used to them. ",
            "The only thing more frightening than a programmer with a screwdriver or a hardware engineer with a program is a user with a pair of wire cutters and the root password. ",
            "No one in the brief history of computing has ever written a piece of perfect software. It's unlikely that you'll be the first. ",
            "Without requirements or design, programming is the art of adding bugs to an empty text file. ",
            "So much complexity in software comes from trying to make one thing do two things. ",
            "A program is never less than 90% complete, and never more than 95% complete. ",
            "One of my most productive days was throwing away 1,000 lines of code. ",
            "Inside every large program, there is a small program trying to get out. ",
            "Roses are red, violets are blue. Unexpected '}' at line 32. ",
            "For the code is long and full of errors. ",
            "Java is to JavaScript as Alf is to Gandalf. ",
            "Ubuntu is an ancient African word, meaning 'can’t configure Debian'. ",
            "In programming the hard part isn't solving problems, but deciding what problems to solve. ",
            "The key to efficient development is to make interesting new mistakes. "
        ];

        var random = Math.floor(Math.random() * quotes.length);
        var randomString = quotes[random];

        insertAtCaret(inputId, randomString, 0);
    }
}

document.addEventListener('keydown', function(event) {
    var currentSlideIndex = localStorage.getItem("currentSlideIndex");
    var isLastSlide = localStorage.getItem("isLastSlide");

    if(event.key == "ArrowLeft" && event.ctrlKey) {
        if(currentSlideIndex == 1) {
            alert("This is the first slide");
        }
        else {
            currentSlideIndex--;
            localStorage.setItem("currentSlideIndex", currentSlideIndex);
            window.location.href = '/ManageSlides.html';
        }
    }
    else if(event.key == "ArrowRight" && event.ctrlKey) {
        if(isLastSlide === "true") {
            alert("This is the last slide")
        }
        else {
            currentSlideIndex++;
            localStorage.setItem("currentSlideIndex", currentSlideIndex);
            window.location.href = '/ManageSlides.html';
        }
    }
});

$("#previous-slide-btn").click(function() {
    var currentSlideIndex = localStorage.getItem("currentSlideIndex");

    if(currentSlideIndex == 1) {
        alert("This is the first Slide");
    }
    else {
        currentSlideIndex--;
        localStorage.setItem("currentSlideIndex", currentSlideIndex);
        window.location.href = '/ManageSlides.html';
    }
});

$("#next-slide-btn").click(function() {
    var currentSlideIndex = localStorage.getItem("currentSlideIndex");
    var isLastSlide = localStorage.getItem("isLastSlide");

    if(isLastSlide === "true") {
        alert("This is the last slide")
    }
    else {
        currentSlideIndex++;
        localStorage.setItem("currentSlideIndex", currentSlideIndex);
        window.location.href = '/ManageSlides.html';
    }
});

var commentBtnToggle = 0;
$("#add-comment-btn").click(function() {
    $("#previous-comment-input").toggle();
    $("#new-comment-input").toggle();

    if(commentBtnToggle === 1) {
        $(this).text("See Comments");
        commentBtnToggle = 0;
    }
    else {
        $(this).text("Add Comment");
        commentBtnToggle = 1;
    }
});

$("#delete-slide-btn").click(function() {
    var topicId = localStorage.getItem("currentTopicId");
    var currentSlideIndex = localStorage.getItem("currentSlideIndex");
    var requestData = { "topicId" : topicId, "slideIndex" : currentSlideIndex };

    sendAuthorisedPostRequest({
        url: "/api/delete/revisionSlide",
        data: requestData,
        onSuccess: function(response){
            console.log(response);
            if(currentSlideIndex != 1) {
                currentSlideIndex--;
            }

            localStorage.setItem("currentSlideIndex", currentSlideIndex);
            window.location.href = '/ManageSlides.html';
        },
        onError: function(error){
            console.log(error);
        }
    });
});

$("#insert-slide-before-btn").click(function() {
    var topicId = localStorage.getItem("currentTopicId");
    var currentSlideIndex = localStorage.getItem("currentSlideIndex");
    var requestData = { "topicId" : topicId, "slideIndex" : currentSlideIndex };

    sendAuthorisedPostRequest({
        url: "/api/insertSlide/before",
        data: requestData,
        onSuccess: function(response){
            console.log(response);
            window.location.href = '/ManageSlides.html';
        },
        onError: function(error){
            console.log(error);
        }
    });
});

$("#insert-slide-after-btn").click(function() {
    var isLastSlide = localStorage.getItem("isLastSlide");
    var currentSlideIndex = localStorage.getItem("currentSlideIndex");

    if(isLastSlide) {
        currentSlideIndex++;
        localStorage.setItem("currentSlideIndex", currentSlideIndex);
        window.location.href = '/ManageSlides.html';
    }
    else {
        var topicId = localStorage.getItem("currentTopicId");
        var requestData = { "topicId" : topicId, "slideIndex" : currentSlideIndex };

        sendAuthorisedPostRequest({
            url: "/api/insertSlide/after",
            data: requestData,
            onSuccess: function(response){
                currentSlideIndex++;
                localStorage.setItem("currentSlideIndex", currentSlideIndex);
                window.location.href = '/ManageSlides.html';
            },
            onError: function(error){
                console.log(error);
            }
        });
    }
});

$("#finish-topic-btn").click(function() {
    var r = confirm("Finish Topic?");
    if (r == true) {
        window.location.href = '/lessons.html';
    }
});

$("#skip-to-slide").change(function() {
    var slideIndex = $(this).val();

    localStorage.setItem("currentSlideIndex", slideIndex);
    window.location.href = '/ManageSlides.html';
});