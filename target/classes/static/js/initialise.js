function initializePage (arg) {
    $("#header").load("header.html");

    if(hasAccessToken()) {
        sendUserDetailRequest({success : function () {
            var role = localStorage.getItem("role");
            if((arg.role).constructor === Array) {
                var roles = arg.role;
                var hasRole = false;

                for(var i = 0; i < roles.length; i++) {
                    if(roles[i] == role) {
                        hasRole = true;
                        break;
                    }
                }

                if(!hasRole) {
                    window.location.href = '/error.html?error=401';
                }
            }
            else {
                if(role !== arg.role || role !== "ROLE_SUPER_ADMIN") {
                    window.location.href = '/error.html?error=401';
                }
            }

            var uname = localStorage.getItem("name");
            $("#username").html(uname);

            $(document).on("click", "#menu-handle", function() {
                $("#drawer").fadeIn(300);
                $(".qed-overlay").fadeIn(300);
            });

            $(document).on("click", "#cancel-menu-btn", function() {
                $("#drawer").fadeOut(300);
                $(".qed-overlay").fadeOut(300);
            });

            $(document).on("click", ".sub-menu-open-btn", function() {
                $("#sub-menu-" + $(this).data("sub-menu-id")).toggle(200);
            });

            $(document).on("click", "#identity-handle", function() {
                $(".profile-menu").toggle();
            });

            arg.onComplete();
        },
        error : function() {
            clearUserDetails();
            window.location.href = '/login';
        }});
    }
    else {
        clearUserDetails();
        window.location.href = '/login';
    }
}

$(document).on("click", "#logout", function() {
    clearUserDetails();
    window.location.href = '/login';
});

function notify(msg, heading, type) {
    $("#notification-text").html(msg);
    if(heading != null) {
        $("#notification-heading").html(heading);
    }

    var now = new Date();
    var h = now.getHours();
    var m = now.getMinutes();

    if(h <= 9) {
        h = "0" + h;
    }

    if(m <= 9) {
        m = "0" + m;
    }

    $("#now").html(h + ":" + m);

    if(type == "green") {
        $("#notification").css("border-color", "#5aa700");
    }
    else if(type == "red") {
        $("#notification").css("border-color", "#e0182d");
    }
    else if(type == "blue") {
        $("#notification").css("border-color", "#5aaafa");
    }
    else if(type == "orange") {
        $("#notification").css("border-color", "#efc100");
    }


    $("#notification").fadeIn();
    setTimeout(function() {
        $("#notification").fadeOut(200);
    }, 3000);
}

$("button[class^='waiter-btn-']").click(function(){
    if("" + ($(this).attr('class').indexOf("waiter-btn-grey") !== -1)){
        $(this).append("<div class='loader on-grey'></div>");
    }
    else if("" + ($(this).attr('class').indexOf("waiter-btn-red") !== -1)){
        $(this).append("<div class='loader on-red'></div>");
    }
    else if("" + ($(this).attr('class').indexOf("waiter-btn-blue") !== -1)){
        $(this).append("<div class='loader on-blue'></div>");
    }

    $(this).find(".material-icons").hide();
    $(this).prop("disabled", true);
});

function stopWaiting(btn) {
    $(btn).find(".loader").remove();
    $(btn).find(".material-icons").show();
    $(btn).prop("disabled", false);
}