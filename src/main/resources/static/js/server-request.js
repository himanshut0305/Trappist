function sendTokenRequest(username, password, onSuccess) {
    $.ajax({
        type: 'POST',
        contentType: "application/json; charset=UTF-8",
        url: '/api/auth/signin',
        dataType: "text",
        data:JSON.stringify({
               "usernameOrEmail":username,
               "password":password
             }),
        success: function(data) {
            localStorage.setItem("accessToken", JSON.parse(data).accessToken);
            onSuccess(data);
            return true;
        },
        error: function(xhr, exception) {
            onError(xhr, exception);
            return false
        }
    });
};

function sendAuthorisedRequest(arg) {
    var xhr = new XMLHttpRequest();
    xhr.withCredentials = true;

    xhr.addEventListener("readystatechange", function () {
        if (this.readyState === 4) {
            if(this.status >= 200 && this.status < 300) {
                arg.onSuccess(this.responseText);
                return true;
            }
            else {
                arg.onError(this);
                return false;
            }
        }
    });

    xhr.open("GET", arg.url, true);
    xhr.setRequestHeader("authorization", "Bearer " + localStorage.getItem("accessToken"));
    xhr.send(arg.data);
}

function sendAuthorisedPostRequest(arg) {
    var xhr = new XMLHttpRequest();
    xhr.withCredentials = true;

    xhr.addEventListener("readystatechange", function () {
        if (this.readyState === 4) {
            if(this.status >= 200 && this.status < 300) {
                arg.onSuccess(this.responseText);
                return true;
            }
            else {
                arg.onError(this);
                return false;
            }
        }
    });

    xhr.open("POST", arg.url, true);
    xhr.setRequestHeader("authorization", "Bearer " + localStorage.getItem("accessToken"));
    xhr.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
    xhr.send(JSON.stringify(arg.data));
}

function sendUserDetailRequest(arg) {
    sendAuthorisedRequest({
        url: "/api/user/me",
        onSuccess: function (data){
            localStorage.setItem("name", JSON.parse(data).name);
            localStorage.setItem("username", JSON.parse(data).username);
            localStorage.setItem("email", JSON.parse(data).email);
            localStorage.setItem("role", JSON.parse(data).authorities[0].authority);

            arg.success(data);
            return true;
        },
        onError: function (error) {
            arg.error();
            console.log(error);
            return false;
        }
    });
}

function clearUserDetails() {
    localStorage.setItem("accessToken", "");
    localStorage.setItem("name", "");
    localStorage.setItem("username", "");
    localStorage.setItem("email", "");
    localStorage.setItem("role", "");
}

function hasAccessToken() {
    var token = localStorage.getItem("accessToken");

    if(token === null || token === "") {
        return false;
    }
    else {
        return true;
    }
}

function onError(xhr, exception) {
    var msg = '';
    if (xhr.status === 0) {
        msg = 'Not connected. Verify Network.';
        notifyLogin(msg)
    }
    else if (xhr.status == 401) {
        msg = 'Incorrect LoginID or password. Please try again!';
        notifyLogin(msg)
    }
    else if (xhr.status == 404) {
        msg = 'Requested page not found. [404]';
        notifyLogin("Something went wrong. Please contact helpdesk.");
    }
    else if (xhr.status == 500) {
        msg = 'Internal Server Error [500].';
        notifyLogin("Something went wrong. Please contact helpdesk.");
    }
    else if (exception === 'parsererror') {
        msg = 'Requested JSON parse failed.';
        notifyLogin("Something went wrong. Please contact helpdesk.");
    }
    else if (exception === 'timeout') {
        msg = 'Time out error.';
        notifyLogin("Something went wrong. Please contact helpdesk.");
    }
    else if (exception === 'abort') {
        msg = 'Ajax request aborted.';
        notifyLogin("Something went wrong. Please contact helpdesk.");
    }
    else {
        msg = 'Uncaught Error.\n' + xhr.responseText;
        notifyLogin("Something went wrong. Please contact helpdesk.");
    }
};

function notifyLogin(msg) {
    $("#error-notification").html(msg);
    $("#error-notification-div").show();
}